package com.project4.cs458.utils;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ValidateMostCommonSymptomsTest {
    private int symptom;
    private boolean expected;
    
    public ValidateMostCommonSymptomsTest(int symptom, boolean expected) {
        this.symptom = symptom;
        this.expected = expected;
    }

    @Parameters(name = "{index}: testValidateMostCommonSymptom({0}) = {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {-10, false},
            {-1, false},
            {0, false},
            {2, false},
            {3, true},
            {5, true},
            {6, false},
            {12, false},
            {13, false},
            {150, false},
        });
    }
    
    @Test
    public void testMostCommonSymptoms() {
        assertEquals(expected, Utils.isMostCommonSymptom(symptom));
    }
}
