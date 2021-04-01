package hu.restapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        log.debug("Hello is called");
        return "Hello World RESTful with Spring Boot";
    }

//    @GetMapping("/resources")
//    // method that returns all items
//
//    @GetMapping("/resources/{id}")
//    // method that returns a specific item
//
//    @PostMapping("/resources")
//    // method that creates a new item
//
//    @PutMapping("/resources/{id}")
//    // method that updates an item
//
//    @DeleteMapping("/resources/{id}")
//    // method that deletes an item
}
