package com.project4.cs458.utils;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ValidateCountryTest {
    private int country;
    private boolean expected;
    
    public ValidateCountryTest(int country, boolean expected) {
        this.country = country;
        this.expected = expected;
    }

    @Parameters(name = "{index}: testValidateCountry({0}) = {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {-10, false},
            {-1, false},
            {0, true},
            {1, true},
            {5, true},
            {56, true},
            {200, true},
            {248, true},
            {249, false},
            {501, false},
        });
    }
    
    @Test
    public void testCountries() {
        assertEquals(expected, Utils.validateCountry(country));
    }
}
