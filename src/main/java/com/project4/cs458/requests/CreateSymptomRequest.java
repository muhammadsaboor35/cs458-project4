package com.project4.cs458.requests;

import java.util.List;
import lombok.Getter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateSymptomRequest {

    public CreateSymptomRequest() {

    }
    
    @Getter
    private List<Integer> symptoms;
}
