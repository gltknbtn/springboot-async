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
@RequestMapping("/gltknbtn")
public class GreetingController {

    private final static Logger logger = Logger.getLogger(GreetingController.class);

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private GreetingService greetingService;

    @RequestMapping(value = "/greeting", method = RequestMethod.POST, produces = "application/json")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }

    @RequestMapping(value = "/manipulateInput", method = RequestMethod.POST, produces = "application/json")
    public GreetingResponse method1(@RequestParam(value="input") String input) throws Exception {
        return greetingService.method1(input);
    }

    @RequestMapping(value = "/doSomethingWithAsyncTask", method = RequestMethod.POST, produces = "application/json")
    public GreetingResponse doSomethingWithAsyncTask(@RequestParam(value="input") String input) {

        return greetingService.doSomething(input);
    }

    @RequestMapping(value = "/doSomethingWithAsyncTask2", method = RequestMethod.POST, produces = "application/json")
    public GreetingResponse doSomethingWithAsyncTask2(@RequestParam(value="input") String input) throws Exception {

        return greetingService.doSomething2(input);
    }

    @RequestMapping(value = "/doWork", method = RequestMethod.POST, produces = "application/json")
    public GreetingResponse doWork(@RequestParam(value="input") String input) throws Exception {
        logger.fatal("This FATAL message been printed by Log4j2 without passing through SLF4J");

        return greetingService.doWork(input);
    }

}