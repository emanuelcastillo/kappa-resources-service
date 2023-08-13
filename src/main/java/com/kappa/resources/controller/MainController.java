package com.kappa.resources.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController{
    @GetMapping("/welcome")
    public String welcome(){
        return "Bienvenido";
    }
}
