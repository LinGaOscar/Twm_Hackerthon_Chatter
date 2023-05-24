package com.hackerthon.leonardo.services;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface LangchainService {
    Map<String, Object> healthCheck();
    Map<String, Object> makeIntroduce(Map<String, Object> data);
    Map<String, Object> conversationHint(Map<String, Object> data);
}
