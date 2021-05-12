package com.project4.cs458.utils;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ValidateNameTest {
    private String name;
    private boolean expected;
    
    public ValidateNameTest(String name, boolean expected) {
        this.name = name;
        this.expected = expected;
    }

    @Parameters(name = "{index}: testValidateName({0}) = {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {null, false},
            {"923", false},
            {"", false},
            {"A", true},
            {"1Musa Kurt", false},
            {"Musa Kurt", true},
            {"Ahmet Osman Galip John Alesger", true},
            {"Ahmet Osman Galip John Alesger 12", false},
            {"Ahmet Osman Galip John Alesger Valid Ayaz Muhammed", false},
        });
    }
    
    @Test
    public void testNames() {
        assertEquals(expected, Utils.validateName(name));
    }
}
