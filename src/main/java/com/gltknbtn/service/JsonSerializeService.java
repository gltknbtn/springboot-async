package com.gltknbtn.service;

import com.gltknbtn.annotation.Init;
import com.gltknbtn.annotation.JsonElement;
import com.gltknbtn.annotation.JsonSerializable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JsonSerializeService {

    //The first step will be to check whether our object is null or not, as well as whether its type has the @JsonSerializable annotation or not:
    private void checkIfSerializable(Object obj){
        if(obj == null){
            throw new RuntimeException("an error occurred because obj is null ");
        }
        Class<?> clazz = obj.getClass();
        if(!clazz.isAnnotationPresent(JsonSerializable.class)){
            throw new RuntimeException("The class "+ clazz.getSimpleName() + " is not annotated with JsonSerializable");
        }
    }

    //Then, we look for any method with @Init annotation, and we execute it to initialize our object's fields
    private void initializeObject(Object obj) throws Exception{
        Class<?> clazz = obj.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if(method.isAnnotationPresent(Init.class)){
                method.setAccessible(true);
                method.invoke(obj);
            }
        }
    }

    //After the initialization, we iterate over our object's fields, retrieve the key and value of JSON elements, and put them in a map. Then, we create the JSON string from the map:
    private String getJsonString(Object obj) throws Exception{
        Class<?> clazz = obj.getClass();
        Map<String, String> jsonElementsMap = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if(field.isAnnotationPresent(JsonElement.class)){
                jsonElementsMap.put(getKey(field), (String)field.get(obj));
            }
        }

        String jsonString = jsonElementsMap.entrySet()
                .stream()
                .map(entry-> "\"" + entry.getKey() + "\":\"" + entry.getValue())
                .collect(Collectors.joining(","));
        return "{" + jsonString + "}";

    }

    private String getKey(Field field){
        String value = field.getAnnotation(JsonElement.class).key();
        return value.isEmpty() ? field.getName() : value;
    }

    public <T> String getObjAsJsonString(T obj) throws Exception {
        checkIfSerializable(obj);
        initializeObject(obj);
        return getJsonString(obj);
    }
}
