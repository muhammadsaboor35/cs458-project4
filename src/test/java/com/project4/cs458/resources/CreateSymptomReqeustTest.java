package com.project4.cs458.resources;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.project4.cs458.requests.CreateSymptomRequest;
import com.project4.cs458.responses.Failure;
import com.project4.cs458.responses.Response;
import com.project4.cs458.responses.Success;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class CreateSymptomReqeustTest {
    private CreateSymptomRequest request;
    private String username;
    private Response response;

    
    public CreateSymptomReqeustTest(CreateSymptomRequest request, String username, Response response) {
        this.request = request;
        this.username = username;
        this.response = response;
    }

    @Parameters(name = "{index}: testCreateSymptom({0},{1}) = {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {null,null,new Failure("Invalid Request")},
            {null,"msaboor35@gmail.com",new Failure("Invalid Request")},
            {new CreateSymptomRequest(),"msaboor35@gmail.com",new Failure("Empty symptoms")},
            {new CreateSymptomRequest(new ArrayList<Integer>()),"msaboor35@gmail.com",new Failure("Empty symptoms")},
            {new CreateSymptomRequest(new ArrayList<>(Arrays.asList(0, 1, 4))),"msaboor35@gmail.com",new Success()},
            {new CreateSymptomRequest(new ArrayList<>(Arrays.asList(0, 1, 4))),"msaboor35@gmail.com",new Failure("Symptoms already added for today")},
        });
    }
    
    @Test
    public void testCreateSymptoms() {
        HistoryResource hr = new HistoryResource();
        Response testResponse = hr.addSymptomForUser(request,username);
        assertEquals(testResponse.getClass(), response.getClass());
        assertEquals(testResponse.isSuccess(), response.isSuccess());
        if(testResponse instanceof Failure) {
            Failure failureResponse = (Failure)testResponse;
            Failure failure = (Failure)response;
            assertEquals(failureResponse.getMessage(),failure.getMessage());
        }
    }
}
