package com.hackerthon.leonardo.controllers;

import com.hackerthon.leonardo.services.UsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/usage")
public class UsageController {

    private UsageService usageService;

    @Autowired
    public UsageController(UsageService usageService) {
        this.usageService = usageService;
    }

    @GetMapping()
    public String loginPage(HttpSession httpSession) {
        if (httpSession.getAttribute("idToken") != null) {
            return "usage";
        }
        return "redirect:/";
    }

    @PostMapping()
    @ResponseBody
    public Map<String, Object> getUsage(HttpSession httpSession) {
        String UID = (String) httpSession.getAttribute("localId");
        return usageService.readUsage(UID);
    }
}
