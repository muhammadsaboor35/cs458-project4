package com.project4.cs458.responses;

import lombok.Getter;

public class Failure extends Response {
    @Getter
    private String message;

    public Failure(String message) {
        super(false);

        this.message = message;
    }
}
