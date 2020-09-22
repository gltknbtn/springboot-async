package com.gltknbtn.controller;

import com.gltknbtn.data.Greeting;
import com.gltknbtn.data.GreetingResponse;
import com.gltknbtn.service.GreetingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/gltknbtn22")
public class GreetingController2 {

    private final static Logger logger = Logger.getLogger(GreetingController2.class);

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private GreetingService greetingService;

    @RequestMapping(value = "/manipulateInput22", method = RequestMethod.POST, produces = "application/json")
    public GreetingResponse method1(@RequestParam(value="input") String input) throws Exception {
        return greetingService.method1(input);
    }

}