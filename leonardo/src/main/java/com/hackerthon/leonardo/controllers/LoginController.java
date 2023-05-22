package com.hackerthon.leonardo.controllers;

import com.hackerthon.leonardo.model.LoginModel;
import com.hackerthon.leonardo.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final AuthService authService;

    @Autowired
    public LoginController(AuthService authService) {
        this.authService = authService;
    }


    @GetMapping("/login")
    public String loginPage(HttpSession httpSession) {
        if (httpSession.getAttribute("userToken") != null) {
            return "redirect:/index";
        }
        return "/login";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginModel loginModel, HttpSession httpSession) {
        System.out.println("login");
        System.out.println(authService.login(loginModel));
        return "/login";
    }

    @PostMapping("/signup")
    public String resist(@RequestBody LoginModel loginModel, HttpSession httpSession) {
        System.out.println("resist");
        System.out.println(authService.signup(loginModel));
        return "/login";
    }
}
