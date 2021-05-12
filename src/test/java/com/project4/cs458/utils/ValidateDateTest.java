package com.project4.cs458.utils;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ValidateDateTest {
    private String date;
    private boolean expected;
    
    public ValidateDateTest(String date, boolean expected) {
        this.date = date;
        this.expected = expected;
    }

    @Parameters(name = "{index}: testValidateDate({0}) = {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {null, false},
            {"", false},
            {"1231243", false},
            {"adasd adas", false},
            {"asd32nkd/**/", false},
            {"20201010", false},
            {"2020-1010", false},
            {"2020-12-10", true},
            {"2021-06-22", true},
            {"2020-10-30", true},
            {"2020-15-13", false},
            {"2020-12-32", false},
            {"2020-02-30", false},
            {"2020-11-31", false},
            {"2020-330", false},
            {"2020330", false},
            {"2020-12-03T10:15:30Z", false},
            {"2020-12-03T25:15:30Z", false},
            {"2020-12-03T20:65:30Z", false},
            {"2020-12-03T20:20:60Z", false},
            {"6 May 2021 11:05:30", false},
        });
    }
    
    @Test
    public void testDates() {
        assertEquals(expected, Utils.validateDate(date));
    }
}
