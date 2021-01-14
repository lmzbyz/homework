package com.ante.homework.corona.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @GetMapping("/")
    public String hello(){
        return "Hello World";
    }
}
