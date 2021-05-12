package com.project4.cs458.utils;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ValidateAgeTest {
    private int age;
    private boolean expected;
    
    public ValidateAgeTest(int age, boolean expected) {
        this.age = age;
        this.expected = expected;
    }

    @Parameters(name = "{index}: testValidateAge({0}) = {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {0, false},
            {-4, false},
            {-3498, false},
            {1, true},
            {22, true},
            {100, true},
            {149, true},
            {150, true},
            {151, false},
            {458, false},
            {1315151, false},
            {131515123, false},
        });
    }
    
    @Test
    public void testAges() {
        assertEquals(expected, Utils.validateAge(age));
    }
}
