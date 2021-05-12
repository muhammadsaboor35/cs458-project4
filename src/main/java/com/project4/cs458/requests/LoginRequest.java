package com.project4.cs458.requests;

import lombok.Getter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginRequest {
    @Getter
    private String email;
    @Getter
    private String password;
}
