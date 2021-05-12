package com.project4.cs458.utils;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ValidateSymptomsTest {
    private int symptom;
    private boolean expected;
    
    public ValidateSymptomsTest(int symptom, boolean expected) {
        this.symptom = symptom;
        this.expected = expected;
    }

    @Parameters(name = "{index}: testValidateSymptom({0}) = {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {-10, false},
            {-1, false},
            {0, true},
            {1, true},
            {5, true},
            {10, true},
            {12, true},
            {13, false},
            {150, false},
        });
    }
    
    @Test
    public void testSymptoms() {
        assertEquals(expected, Utils.validateSymptom(symptom));
    }
}
