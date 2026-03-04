package com.example.orders.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    // Этот метод будет обрабатывать GET-запросы на корень сайта "/"
    @GetMapping("/")
    public String home() {
        return "Приложение работает!";
    }
}