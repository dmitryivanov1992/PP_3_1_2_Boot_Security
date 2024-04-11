package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/")
@Controller
public class RootController {

    @GetMapping("")
    public String showDefaultPage() {
        return "user";
    }

    @GetMapping("index")
    public String showIndexPage(){
        return "index";
    }
}
