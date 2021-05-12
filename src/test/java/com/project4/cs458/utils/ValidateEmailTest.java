package com.project4.cs458.utils;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ValidateEmailTest {
    private String email;
    private boolean expected;
    
    public ValidateEmailTest(String email, boolean expected) {
        this.email = email;
        this.expected = expected;
    }

    @Parameters(name = "{index}: testValidateEmail({0}) = {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {"msaboor35@gmail.com", true},
            {"m@gmail.com", true},
            {"523s3@gmail.com", true},
            {"52.3@gmail.com", true},
            {"52_3@gmail.com", true},
            {"52-3@gmail.com", true},
            {"5555@gmail.com", true},
            {"5555@yahoo.com", true},
            {"5555@mail.ru", true},
            {"5555@mail", false},
            {"5555@.com", false},
            {"5555.com", false},
            {"@.com", false},
            {"@gmail.com", false},
            {"mail@gmail.", false},
            {"mail@.com", false},
            {"mail@.", false},
            {"mail", false},
            {".com", false},
            {"@com", false}
        });
    }
    
    @Test
    public void testEmails() {
        assertEquals(expected, Utils.validateEmail(email));
    }
}
