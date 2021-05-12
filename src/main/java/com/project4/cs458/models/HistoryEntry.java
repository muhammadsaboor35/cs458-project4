package com.project4.cs458.models;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class HistoryEntry {
    @Getter
    private String date;

    @Getter
    private ArrayList<Integer> symptoms;
}
