package com.awbd.cookbase.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String redirectToMain() {
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String showMainPage() {
        return "main"; // this resolves to templates/main.html
    }
}