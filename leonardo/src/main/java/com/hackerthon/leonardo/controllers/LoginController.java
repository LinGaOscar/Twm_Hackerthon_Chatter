package com.hackerthon.leonardo.controllers;

import com.hackerthon.leonardo.model.LoginModel;
import com.hackerthon.leonardo.services.AuthService;
import com.hackerthon.leonardo.services.UserService;
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
    private final UserService userService;

    @Autowired
    public LoginController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }


    @GetMapping("/login")
    public String loginPage(HttpSession httpSession) {
        if (httpSession.getAttribute("idToken") != null) {
            return "redirect:/";
        }else{
            return "login";
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(@RequestBody LoginModel loginModel, HttpSession httpSession) {
        Map<String, Object> resultMap = authService.login(loginModel);
        if (resultMap.get("idToken") != null) {
            Map<String, Object> userData = userService.readUserWithEmail(loginModel.getEmail());
            httpSession.setAttribute("idToken", resultMap.get("idToken"));
            httpSession.setAttribute("localId", (String) resultMap.get("localId"));
            httpSession.setAttribute("key", userData.get("key"));
        }
        return resultMap;
    }

    @PostMapping("/signup")
    @ResponseBody
    public Map<String, Object> register(@RequestBody LoginModel loginModel, HttpSession httpSession) {
        Map<String, Object> resultMap = authService.signup(loginModel);
        if (resultMap.get("idToken") != null) {
            String UID = (String) resultMap.get("localId");
            System.out.println(UID);
            Map<String, Object> userData = userService.addUser(UID, loginModel);

            httpSession.setAttribute("idToken", resultMap.get("idToken"));
            httpSession.setAttribute("localId", UID);
            httpSession.setAttribute("key", userData.get("key"));
        }
        return resultMap;
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("idToken");
        httpSession.removeAttribute("localId");
        httpSession.removeAttribute("key");
        return "redirect:/";
    }
}
