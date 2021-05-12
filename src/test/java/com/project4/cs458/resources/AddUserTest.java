package com.project4.cs458.resources;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import com.project4.cs458.requests.CreateUserRequest;
import com.project4.cs458.responses.Failure;
import com.project4.cs458.responses.Success;
import com.project4.cs458.responses.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import java.util.UUID;

@RunWith(value = Parameterized.class)
public class AddUserTest {
    private CreateUserRequest request;
    private Response response;
    
    public AddUserTest(CreateUserRequest request, Response response) {
        this.request = request;
        this.response = response;
    }

    @Parameters(name = "{index}: testAddUser({0}) = {1}")
    public static Collection<Object[]> data() {
        String validEmail = UUID.randomUUID().toString().replace("-","") + "@gmail.com";
        return Arrays.asList(new Object[][]{
            {new CreateUserRequest(validEmail,"passwordx2","Muhammad Saboor",23,43,103), new Success()},
            {new CreateUserRequest("msaboor35@gmail.com","passwordx2","Muhammad Saboor",23,43,103), new Failure("User Already Exists")},
            {new CreateUserRequest(null,"passwordx2","Muhammad Saboor",23,43,103), new Failure("Invalid Email")},
            {new CreateUserRequest("msaboor35@gmail.com","","Muhammad Saboor",23,43,103), new Failure("Invalid Password")},
            {new CreateUserRequest("msaboor35@gmail.com","passwsorosfewmoef3ew.","",23,43,103), new Failure("Invalid Name")},
            {new CreateUserRequest("msaboor35@gmail.com","passwsorosfewmoef3ew.","Muhammad Saboor",0,43,103), new Failure("Invalid Age")},
            {new CreateUserRequest("msaboor35@gmail.com","passwsorosfewmoef3ew.","Muhammad Saboor",10,-43,103), new Failure("Invalid Nationality")},
            {new CreateUserRequest("msaboor35@gmail.com","passwsorosfewmoef3ew.","Muhammad Saboor",10,43,-103), new Failure("Invalid Location")},
        });
    }
    
    @Test
    public void testAddUsers() {
        UserResource ur = new UserResource();
        Response testResponse = ur.add(request);
        assertEquals(testResponse.getClass(), response.getClass());
        assertEquals(testResponse.isSuccess(), response.isSuccess());
        if(testResponse instanceof Failure) {
            Failure failureResponse = (Failure)testResponse;
            Failure failure = (Failure)response;
            assertEquals(failureResponse.getMessage(),failure.getMessage());
        }
    }
}
