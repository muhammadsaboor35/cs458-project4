package com.project4.cs458.requests;

import lombok.Getter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateUserRequest {
    @Getter
    private String email;
    @Getter
    private String password;
    @Getter
    private String name;
    @Getter
    private int age;
    @Getter
    private int nationality;
    @Getter
    private int location;
}
