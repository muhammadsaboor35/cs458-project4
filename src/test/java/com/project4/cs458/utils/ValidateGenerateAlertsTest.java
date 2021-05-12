package com.project4.cs458.utils;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.project4.cs458.models.HistoryEntry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ValidateGenerateAlertsTest {
    private ArrayList<HistoryEntry> symptoms;
    private String expected;
    
    public ValidateGenerateAlertsTest(ArrayList<HistoryEntry> symptoms, String expected) {
        this.symptoms = symptoms;
        this.expected = expected;
    }

    @Parameters(name = "{index}: testGenerateAlert({0}) = {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {null, "No Alerts"},
            {new ArrayList<>(Arrays.asList(new HistoryEntry("2021-05-10", new ArrayList<>(Arrays.asList(0, 1, 5))), new HistoryEntry("2021-05-09", new ArrayList<>(Arrays.asList(0, 1, 4))), new HistoryEntry("2021-05-08", new ArrayList<>(Arrays.asList(0, 1))))), "Serious Symptom in 2 weeks"},
            {new ArrayList<>(Arrays.asList()), "No Alerts"},
            {new ArrayList<>(Arrays.asList(new HistoryEntry("2021-01-08", new ArrayList<>(Arrays.asList(3, 4))), new HistoryEntry("2021-01-09", new ArrayList<>(Arrays.asList(4, 5))))), "All Most Common Symptoms in 2 weeks"},
            {new ArrayList<>(Arrays.asList(new HistoryEntry("2021-03-01", new ArrayList<>(Arrays.asList(0, 6))), new HistoryEntry("2021-03-02", new ArrayList<>(Arrays.asList(0, 8))), new HistoryEntry("2021-03-03", new ArrayList<>(Arrays.asList(6, 8, 10))))), "Serious Symptom in 2 weeks"},
            {new ArrayList<>(Arrays.asList(new HistoryEntry("2020-11-18", new ArrayList<>(Arrays.asList(5, 11))), new HistoryEntry("2020-11-19", new ArrayList<>(Arrays.asList(11, 12))))), "Atleast 2 Less Common Symptoms in 2 weeks"},
            {new ArrayList<>(Arrays.asList(new HistoryEntry("2020-07-29", new ArrayList<>(Arrays.asList(10, 11))), new HistoryEntry("2020-07-30", new ArrayList<>(Arrays.asList(10, 11))), new HistoryEntry("2020-07-31", new ArrayList<>(Arrays.asList(11, 12))), new HistoryEntry("2020-08-01", new ArrayList<>(Arrays.asList(11, 12))))), "Atleast 2 Less Common Symptoms in 2 weeks"},
            {new ArrayList<>(Arrays.asList(new HistoryEntry("2020-10-22", new ArrayList<>(Arrays.asList(10))), new HistoryEntry("2020-10-23", new ArrayList<>(Arrays.asList(11, 12))))), "Atleast 2 Less Common Symptoms in 2 weeks"},
        });

    }
    
    @Test
    public void testGenerateAlerts() {
        assertEquals(expected, Utils.generateAlert(symptoms));
    }
}
