package com.project4.cs458.responses;

import java.util.ArrayList;

import com.project4.cs458.models.HistoryEntry;
import com.project4.cs458.models.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Success extends Response {
    public Success() {
        super(true);
    }

    @AllArgsConstructor
    public static class TokenSucess extends Success {

        @Getter
        private String token;
    }

    @AllArgsConstructor
    public static class HistorySuccess extends Success {
        public HistorySuccess() {
            super();
        }

        @Getter
        private User user;

        @Getter
        private ArrayList<HistoryEntry> history_results;

        @Getter
        private String alert;
    }
}