package com.hackerthon.leonardo.services;

import java.util.Map;

public interface UsageService {
    Map<String, Object> addUsage(String apiName, Map<String, Object> data);

    Map<String, Object> readUsage(String uid);

    Map<String, Object> readAllUsage();
}
