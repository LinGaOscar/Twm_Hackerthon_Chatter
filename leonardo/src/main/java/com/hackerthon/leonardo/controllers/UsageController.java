package com.hackerthon.leonardo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UsageController {
    @GetMapping("/usage")
    public String loginPage(HttpSession httpSession) {
        if (httpSession.getAttribute("idToken") != null) {
            return "redirect:/";
        }
        return "/usage";
    }
}
