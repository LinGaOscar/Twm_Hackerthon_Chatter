package com.hackerthon.leonardo.services;

import com.hackerthon.leonardo.model.LoginModel;

import java.util.Map;

public interface AuthService {
    Map<String, Object> login(LoginModel loginModel);
    Map<String, Object> signup(LoginModel loginModel);
}
