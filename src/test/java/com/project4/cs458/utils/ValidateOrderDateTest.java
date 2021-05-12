package com.project4.cs458.utils;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ValidateOrderDateTest {
    private String startDate;
    private String endDate;
    private boolean expected;
    
    public ValidateOrderDateTest(String startDate, String endDate, boolean expected) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.expected = expected;
    }

    @Parameters(name = "{index}: testValidateOrderDate({0},{1}) = {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {null, null, false},
            {"",null, false},
            {"2020-12-10",null, false},
            {null, "", false},
            {null, "2020-12-10", false},
            {"", "", false},
            {"1231243", "2020-12-10", false},
            {"2020-12-10", "adasd adas", false},
            {"asd32nkd/*/", "asd32nkd/*/", false},
            {"20201010", "20201210", false},
            {"2020-1010", "2020-10-10", false},
            {"2020-06-07", "2020-12-03T20:20:60Z", false},
            {"2020-12-32", "2020-11-30", false},
            {"2020-12-10", "2021-01-15", true},
            {"2021-06-22", "2021-06-28", true},
            {"2020-10-30", "2021-01-10", true},
            {"2020-10-30", "2020-09-12", false},
            {"2020-12-05", "2020-11-29", false},
        });
    }
    
    @Test
    public void testOrderDates() {
        assertEquals(expected, Utils.validateOrderOfDates(startDate,endDate));
    }
}
