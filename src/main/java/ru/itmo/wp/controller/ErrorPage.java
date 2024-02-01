package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorPage extends Page {
    @GetMapping("/error")
    public String error() {
        return "ErrorPage";
    }
}
