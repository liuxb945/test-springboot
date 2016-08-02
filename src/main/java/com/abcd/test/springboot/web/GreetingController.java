package com.abcd.test.springboot.web;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.abcd.test.springboot.model.Greeting;

@RestController  
public class GreetingController {
	private static final Logger log = LoggerFactory.getLogger(GreetingController.class);
    private static final String template = "Hello, %s";  
    private final AtomicLong counter = new AtomicLong();  
  
    @RequestMapping("/greeting")  
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "world") String name) {  
        Greeting greeting = new Greeting(counter.incrementAndGet(), String.format(template, name));  
//        GreetingController methodOn = methodOn(GreetingController.class);  
//        // GreetingController methodOn = DummyInvocationUtils.methodOn(GreetingController.class);  
//        Greeting greeting2 = methodOn.greeting(name);  
//        ControllerLinkBuilder linkTo = linkTo(greeting2);  
//        Link withSelfRel = linkTo.withSelfRel();  
//        greeting.add(withSelfRel); 
        log.debug("get greeting invoked");
  
        return greeting;  
    }  
      
    @RequestMapping("/show")  
    public Greeting show(){  
        RestTemplate template = new RestTemplate();  
        Greeting greeting = template.getForObject("http://localhost:8080/greeting?name=323", Greeting.class);  
        System.err.println(greeting);  
        return greeting;  
    }  
  
}
