package com.hackerthon.leonardo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @GetMapping("/")
    public String indexPage(HttpSession httpSession) {
//        if (Objects.isNull(httpSession.getAttribute("loginCheck"))) {
//            return "/login";
//        }

        return "index";
    }
}