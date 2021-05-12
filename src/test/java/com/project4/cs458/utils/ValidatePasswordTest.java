package com.project4.cs458.utils;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ValidatePasswordTest {
    private String password;
    private boolean expected;
    
    public ValidatePasswordTest(String password, boolean expected) {
        this.password = password;
        this.expected = expected;
    }

    @Parameters(name = "{index}: testValidatePassword({0}) = {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {null, false},
            {"", false},
            {"1", false},
            {"pass", false},
            {"123456", false},
            {"password", true},
            {"password123", true},
            {"password123password123password123password123password123password123", true},
        });
    }
    
    @Test
    public void testPasswords() {
        assertEquals(expected, Utils.validatePassword(password));
    }
}
