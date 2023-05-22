package com.hackerthon.leonardo.model;


import lombok.Data;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Data
public class CustomerData {

    private String name;
    private String password;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(this));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return map;
    }
}
