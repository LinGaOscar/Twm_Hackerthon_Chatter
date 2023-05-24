package com.hackerthon.leonardo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class UsageController {
    @GetMapping("/usage")
    public String loginPage(HttpSession httpSession) {
        if (httpSession.getAttribute("idToken") != null) {
            return "usage";
        }
        return "redirect:/";
    }

    @PostMapping("/usage")
    @ResponseBody
    public String getUsage(HttpSession httpSession) {
        String key = (String) httpSession.getAttribute("localId");

        return "redirect:/";
    }
}
