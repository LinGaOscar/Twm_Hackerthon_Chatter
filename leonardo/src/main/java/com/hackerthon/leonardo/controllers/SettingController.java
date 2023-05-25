package com.hackerthon.leonardo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hackerthon.leonardo.services.UserService;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/setting")
public class SettingController {

    private final UserService userService;

    @Autowired
    public SettingController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String loginPage(HttpSession httpSession) {
        if (httpSession.getAttribute("idToken") != null) {
            return "setting";
        }else{
            return "index";
        }
    }

    @PostMapping("/getData")
    @ResponseBody
    public Map<String, Object> getData(HttpSession httpSession) {
        String key = (String) httpSession.getAttribute("key");
        Map<String, Object> user = new HashMap<>();
        if (!key.isEmpty()) {
            user = userService.readUserWithKey(key);
        }
        return user;
    }

    @PostMapping("/updateData")
    @ResponseBody
    public Map<String, Object> updateData(@RequestBody Map<String, Object> data, HttpSession httpSession) {
        Map<String, Object> user = new HashMap<>();
        String key = (String) httpSession.getAttribute("key");
        if (!key.isEmpty()) {
            user = userService.updateUser(key, data);
            user.put("key", key);
        }
        return user;
    }


}
