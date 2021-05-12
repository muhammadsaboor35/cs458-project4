package com.project4.cs458.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Arrays;
import java.util.Collection;

import com.project4.cs458.requests.LoginRequest;
import com.project4.cs458.responses.Failure;
import com.project4.cs458.responses.Response;
import com.project4.cs458.responses.Success;
import com.project4.cs458.responses.Success.TokenSucess;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class CreateTokenTest {
    private LoginRequest request;
    private Response response;
    
    public CreateTokenTest(LoginRequest request, Response response) {
        this.request = request;
        this.response = response;
    }

    @Parameters(name = "{index}: testCreateToken({0}) = {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {new LoginRequest("msaboor35@gmail.com","passwordx"), new TokenSucess("a token")},
            {new LoginRequest("msaboor35@gmail.com","passwordx2"), new Failure("username and/or password is incorrect")},
            {new LoginRequest(null,"passwordx2"), new Failure("username and/or password is incorrect")},
            {new LoginRequest("msaboor35@gmail.com",null), new Failure("username and/or password is incorrect")},
            {new LoginRequest(null,null), new Failure("username and/or password is incorrect")},
            {new LoginRequest("msaboor35@gmail.com",""), new Failure("username and/or password is incorrect")},
            {new LoginRequest("","passwordx"), new Failure("username and/or password is incorrect")},
            {new LoginRequest("usman@abcd.com","passwordx"), new Failure("username and/or password is incorrect")},
        });
    }
    
    @Test
    public void testCreateTokens() {
        UserResource ur = new UserResource();
        Response testResponse = ur.createToken(request);
        assertEquals(testResponse.getClass(), response.getClass());
        assertEquals(testResponse.isSuccess(), response.isSuccess());
        if(testResponse instanceof Failure) {
            Failure failureResponse = (Failure)testResponse;
            Failure failure = (Failure)response;
            assertEquals(failureResponse.getMessage(),failure.getMessage());
        }
        else{
            Success.TokenSucess successResponse = (Success.TokenSucess)testResponse;
            assertNotEquals("", successResponse.getToken());
        }
    }
}
