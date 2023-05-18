package com.hackerthon.leonardo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/exception")
    public String testException() {
        throw new RuntimeException("測試異常");
    }
}