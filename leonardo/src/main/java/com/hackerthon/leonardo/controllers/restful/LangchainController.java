package com.hackerthon.leonardo.controllers.restful;

import com.hackerthon.leonardo.services.LangchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> healthResultMap = langchainService.healthCheck();


        resultMap.put("result", healthResultMap);
        return resultMap;
    }

    @PostMapping("/makeIntroduce")
    public Map<String, Object> makeIntroduce(@RequestBody Map<String, Object> data, HttpSession httpSession) {
        Map<String, Object> resultMap = new HashMap<>();
        String UID = (String) data.get("localId");
        if (UID == null || UID.isEmpty()) {
            resultMap.put("err", "lost UID");
            return resultMap;
        }
//        String apiName = "makeIntroduce";
//        Map<String, Object> introduceMap = langchainService.makeIntroduce(data);
//
//
//        resultMap.put("result", introduceMap);
        return resultMap;
    }

    @PostMapping("/conversationHint")
    public Map<String, Object> conversationHint(@RequestBody Map<String, Object> data) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> introduceMap = langchainService.conversationHint(data);


        resultMap.put("result", introduceMap);
        return resultMap;
    }
}
