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
public class ValidateHasSeriousCommonSymptomsTest {
    private ArrayList<HistoryEntry> symptoms;
    private boolean expected;
    
    public ValidateHasSeriousCommonSymptomsTest(ArrayList<HistoryEntry> symptoms, boolean expected) {
        this.symptoms = symptoms;
        this.expected = expected;
    }

    @Parameters(name = "{index}: testValidateSeriousCommonSymptom({0}) = {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {null,false},
            {new ArrayList<>(Arrays.asList()),false},
            {new ArrayList<>(Arrays.asList(new HistoryEntry("2021-05-10", new ArrayList<>(Arrays.asList(0, 1, 5))), new HistoryEntry("2021-05-09", new ArrayList<>(Arrays.asList(0, 1, 4))), new HistoryEntry("2021-05-08", new ArrayList<>(Arrays.asList(0, 1))))), true},
            {new ArrayList<>(Arrays.asList(new HistoryEntry("2021-04-14", new ArrayList<>(Arrays.asList(5, 6))), new HistoryEntry("2021-04-15", new ArrayList<>(Arrays.asList(5))))), false},
            {new ArrayList<>(Arrays.asList(new HistoryEntry("2021-01-08", new ArrayList<>(Arrays.asList(0, 4))), new HistoryEntry("2021-01-09", new ArrayList<>(Arrays.asList(4, 5))))), true},
            {new ArrayList<>(Arrays.asList(new HistoryEntry("2021-03-01", new ArrayList<>(Arrays.asList(0, 6))), new HistoryEntry("2021-03-02", new ArrayList<>(Arrays.asList(0, 8))), new HistoryEntry("2021-03-03", new ArrayList<>(Arrays.asList(6, 8, 10))))), true},
            {new ArrayList<>(Arrays.asList(new HistoryEntry("2020-11-18", new ArrayList<>(Arrays.asList(1, 11))), new HistoryEntry("2020-11-19", new ArrayList<>(Arrays.asList(1, 11, 12))))), true},
            {new ArrayList<>(Arrays.asList(new HistoryEntry("2020-07-29", new ArrayList<>(Arrays.asList(10, 11))), new HistoryEntry("2020-07-30", new ArrayList<>(Arrays.asList(10, 11))), new HistoryEntry("2020-07-31", new ArrayList<>(Arrays.asList(11, 12))), new HistoryEntry("2020-08-01", new ArrayList<>(Arrays.asList(11, 12))))), false},
            {new ArrayList<>(Arrays.asList(new HistoryEntry("2020-10-22", new ArrayList<>(Arrays.asList(10))), new HistoryEntry("2020-10-23", new ArrayList<>(Arrays.asList(11, 12))))), false},
        });

    }
    
    @Test
    public void testHasSeriousCommonSymptoms() {
        assertEquals(expected, Utils.hasSeriousSymptom(symptoms));
    }
}
