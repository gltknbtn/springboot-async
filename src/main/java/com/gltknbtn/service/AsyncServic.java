package com.gltknbtn.service;

import com.gltknbtn.model.Department;
import com.gltknbtn.model.Person;
import com.gltknbtn.repository.DepartmentRepository;
import com.gltknbtn.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

@Component
@Transactional
public class AsyncServic {

    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private PersonRepository personRepository;

    @Autowired private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Async    public Future<Person> asyncMethodWithReturnType(Person person) {
        System.out.println("Execute method asynchronously - "+ Thread.currentThread().getName());
        try{
            Thread.sleep(1000);
            String threadName = Thread.currentThread().getName();
            Department department = new Department();
            department.setDepartmentName(person.getName() +" - department created by "+threadName);
            departmentRepository.save(department);

            person.setName(person.getName() + "- manipulated by "+ threadName);
            personRepository.save(person);
            return new AsyncResult<>(person);

            /*
            if(true){
                throw new RuntimeException("runtime aldık");

            }
            */

        }catch (Exception e){
            person.setName(person.getName() + "- sıkıntılı");
            personRepository.save(person);
            e.printStackTrace();
        }

        return null;
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void asyncMethodWithReturnType2(Person person) {
          try{
              Thread.sleep(5000);
              String threadName = Thread.currentThread().getName();
              Department department = new Department();
              department.setDepartmentName(person.getName() +" - department created by "+threadName);
              departmentRepository.save(department);

              person.setName(person.getName() + "- manipulated by "+ threadName);
              personRepository.save(person);
              if(true){
                  throw new RuntimeException("runtime aldık");

              }

          }catch (Exception e){
              person.setName(person.getName() + "- sıkıntılı");
              personRepository.save(person);
              e.printStackTrace();
          }
    }
    @Async("threadPoolTaskExecutor")
    @Transactional// çağıran sınıfın transaction ı devam ettirilmiyor.
    public void asyncMethodWithReturnType3(Person person) {
          try{
              Thread.sleep(1000);
              String threadName = Thread.currentThread().getName();
              Department department = new Department();
              department.setDepartmentName(person.getName() +" - department created by "+threadName);
              departmentRepository.save(department);

              person.setName(person.getName() + "- manipulated by "+ threadName);
              personRepository.save(person);
              if(true){
//                  throw new RuntimeException("runtime aldık1");

              }

          }catch (Exception e){
              //personRepository.delete(person);
              throw new RuntimeException("runteime aldık2", e);
          }
    }


    public void doWork(Person person){


        Runnable task = () -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String threadName = Thread.currentThread().getName();
            Department department = new Department();
            department.setDepartmentName(person.getName() +" - department created by "+threadName);
            departmentRepository.save(department);

            person.setName(person.getName() + "- manipulated by "+ threadName);
            personRepository.save(person);
        };

        threadPoolTaskExecutor.execute(task, 1);
    }
}
