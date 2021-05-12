package com.project4.cs458.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class User {
    @Getter
    private String email;
    @Getter
    private String name;
    @Getter
    private int age;
    @Getter
    private int nationality;
    @Getter
    private int location;

    public User() {

    }
}
