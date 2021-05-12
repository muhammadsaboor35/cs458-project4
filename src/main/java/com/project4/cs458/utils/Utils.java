package com.project4.cs458.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import com.project4.cs458.models.Countries;
import com.project4.cs458.models.HistoryEntry;
import com.project4.cs458.models.Symptoms;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

public class Utils {

    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private final static int DEFAULT_THRESHOLD = 2;

    public static boolean validateEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

    public static boolean validateName(String name) {
        if(name == null) {
            return false;
        }
        if(name.length() <= 0) {
            return false;
        }
        if(name.length() >= 40) {
            return false;
        }
        if(!StringUtils.isAlphaSpace(name)) {
            return false;
        }
        return true;
    }

    public static boolean validatePassword(String password) {
        if(password == null) {
            return false;
        }
        if(password.length() <= 6) {
            return false;
        }
        return true;
    }

    public static boolean validateAge(int age) {
        if(age <= 0 || age > 150) {
            return false;
        }
        return true;
    }

    public static boolean validateDate(String date) {
        if(date == null) {
            return false;
        }
        try{
            DATE_FORMATTER.parse(date);
        }
        catch(DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    public static boolean validateOrderOfDates(String startDate, String endDate) {
        if(startDate == null || endDate == null) {
            return false;
        }
        if(!Utils.validateDate(startDate) || !Utils.validateDate(endDate)) {
            return false;
        }

        LocalDate startDate1 = Utils.parseDate(startDate);
        LocalDate endDate1 = Utils.parseDate(endDate);
        
        if(startDate1.isAfter(endDate1)) {
            return false;
        }

        return true;
    }

    public static boolean validateSymptom(int symptom) {
        if(symptom >= 0 && symptom <= Symptoms.values().length - 1) {
            return true;
        }
        return false;
    }

    public static boolean validateCountry(int country) {
        if(country >= 0 && country <= Countries.values().length - 1) {
            return true;
        }
        return false;
    }

    public static boolean isSeriousSymptom(int symptom) {
        if(symptom >= 0 && symptom <= 2) {
            return true;
        }
        return false;
    }

    public static boolean isMostCommonSymptom(int symptom) {
        if(symptom >= 3 && symptom <= 5) {
            return true;
        }
        return false;
    }

    public static boolean isLessCommonSymptom(int symptom) {
        if(symptom >= 6 && symptom <= 12) {
            return true;
        }
        return false;
    }

    public static boolean hasSeriousSymptom(ArrayList<HistoryEntry> historyList) {
        if(historyList == null) {
            return false;
        }
        for(int i = 0; i < historyList.size(); i++) {
            ArrayList<Integer> symptoms = historyList.get(i).getSymptoms();
            for(int j = 0; j < symptoms.size(); j++) {
                int symptom = symptoms.get(j);
                if(isSeriousSymptom(symptom)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean hasMostCommonSymptoms(ArrayList<HistoryEntry> historyList) {
        if(historyList == null) {
            return false;
        }
        boolean seen1 = false;
        boolean seen2 = false;
        boolean seen3 = false;

        for(int i = 0; i < historyList.size(); i++) {
            ArrayList<Integer> symptoms = historyList.get(i).getSymptoms();
            for(int j = 0; j < symptoms.size(); j++) {
                int symptom = symptoms.get(j);
                if(symptom == 3) {
                    seen1 = true;
                }
                if(symptom == 4) {
                    seen2 = true;
                }
                if(symptom == 5) {
                    seen3 = true;
                }
            }
        }

        if(seen1) {
            if(seen2) {
                if(seen3) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean hasLessCommonSymptoms(ArrayList<HistoryEntry> historyList, int threshold) {
        if(historyList == null || threshold <= 0) {
            return false;
        }
        int count = 0;
        for(int i = 0; i < historyList.size(); i++) {
            ArrayList<Integer> symptoms = historyList.get(i).getSymptoms();
            for(int j = 0; j < symptoms.size(); j++) {
                int symptom = symptoms.get(j);
                if(isLessCommonSymptom(symptom)) {
                    count = count + 1;
                }
            }
        }
        if(count >= threshold) {
            return true;
        }
        return false;
    }

    public static String generateAlert(ArrayList<HistoryEntry> historyList) {
        String defaultAlert = "No Alerts";

        if(historyList == null) {
            return defaultAlert;
        }

        if(hasSeriousSymptom(historyList)) {
            return "Serious Symptom in 2 weeks";
        }

        if(hasMostCommonSymptoms(historyList)) {
            return "All Most Common Symptoms in 2 weeks";
        }

        if(hasLessCommonSymptoms(historyList,DEFAULT_THRESHOLD)) {
            return "Atleast " + DEFAULT_THRESHOLD + " Less Common Symptoms in 2 weeks";
        }        

        return defaultAlert;
    }
}