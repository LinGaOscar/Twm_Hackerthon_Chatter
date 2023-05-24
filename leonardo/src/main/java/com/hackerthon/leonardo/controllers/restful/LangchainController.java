package com.hackerthon.leonardo.controllers.restful;

import com.hackerthon.leonardo.services.LangchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/langchain")
public class LangchainController {

    private LangchainService langchainService;

    @Autowired
    public LangchainController(LangchainService langchainService) {
        this.langchainService = langchainService;
    }


    @GetMapping("/healthCheck")
    public Map<String, Object> healthCheck() {

        Map<String, Object> healthResultMap = langchainService.healthCheck();
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("result", healthResultMap);
        return resultMap;
    }

    @PostMapping("/makeIntroduce")
    public Map<String, Object> makeIntroduce(@RequestBody Map<String, Object> data) {

        Map<String, Object> introduceMap = langchainService.makeIntroduce(data);
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("result", introduceMap);
        return resultMap;
    }

    @PostMapping("/conversationHint")
    public Map<String, Object> conversationHint(@RequestBody Map<String, Object> data) {

        Map<String, Object> introduceMap = langchainService.conversationHint(data);
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("result", introduceMap);
        return resultMap;
    }
}
