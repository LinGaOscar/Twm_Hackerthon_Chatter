package com.hackerthon.leonardo.controllers;

import com.hackerthon.leonardo.model.LoginModel;
import com.hackerthon.leonardo.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

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
            return "redirect:/";
        }
        return "/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(@RequestBody LoginModel loginModel, HttpSession httpSession) {
        Map<String, Object> resultMap = authService.login(loginModel);
        httpSession.setAttribute("userToken", resultMap.get("idToken"));
        return resultMap;
    }

    @PostMapping("/signup")
    @ResponseBody
    public Map<String, Object> resist(@RequestBody LoginModel loginModel, HttpSession httpSession) {
        Map<String, Object> resultMap = authService.signup(loginModel);
        httpSession.setAttribute("userToken", resultMap.get("idToken"));

        return resultMap;
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("userToken");
        return "redirect:/";
    }
}
