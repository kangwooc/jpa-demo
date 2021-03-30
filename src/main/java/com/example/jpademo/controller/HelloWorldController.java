package com.example.jpademo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/hello-world")
    // h2 => test 용이나 db relation
    public String helloWorld() {
        return "Hello World";
    }
}
