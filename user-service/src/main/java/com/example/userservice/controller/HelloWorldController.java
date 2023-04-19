package com.example.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class HelloWorldController {

    @GetMapping("/hello")
    ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello World");
    }
}
