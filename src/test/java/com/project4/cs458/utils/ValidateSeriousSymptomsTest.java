package com.project4.cs458.utils;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ValidateSeriousSymptomsTest {
    private int symptom;
    private boolean expected;
    
    public ValidateSeriousSymptomsTest(int symptom, boolean expected) {
        this.symptom = symptom;
        this.expected = expected;
    }

    @Parameters(name = "{index}: testValidateSeriousSymptom({0}) = {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {-10, false},
            {-1, false},
            {0, true},
            {1, true},
            {2, true},
            {3, false},
            {10, false},
            {12, false},
            {13, false},
            {150, false},
        });
    }
    
    @Test
    public void testSeriousSymptoms() {
        assertEquals(expected, Utils.isSeriousSymptom(symptom));
    }
}
