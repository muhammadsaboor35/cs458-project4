package com.project4.cs458.resources;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import com.project4.cs458.responses.Failure;
import com.project4.cs458.responses.Response;
import com.project4.cs458.responses.Success;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class GetHistoryTest {
    private String startDate;
    private String endDate;
    private String username;
    private Response response;

    
    public GetHistoryTest(String startDate, String endDate, String username, Response response) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.username = username;
        this.response = response;
    }

    @Parameters(name = "{index}: testGetHistory({0},{1},{2}) = {3}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {null,null,null,new Failure("Invalid Request")},
            {null,null,"msaboor35@gmail.com",new Success.HistorySuccess()},
            {null,"2020-03-23","msaboor35@gmail.com",new Failure("Empty date field")},
            {"2020-03-23",null,"msaboor35@gmail.com",new Failure("Empty date field")},
            {"2020-03-23","2018-03-23","msaboor35@gmail.com",new Failure("Invalid dates")},
            {"2021-05-08","2021-05-11","msaboor35@gmail.com",new Success.HistorySuccess()}
        });
    }
    
    @Test
    public void testGetHistory() {
        HistoryResource hr = new HistoryResource();
        Response testResponse = hr.getHistoryForUser(startDate,endDate,username);
        assertEquals(testResponse.getClass(), response.getClass());
        assertEquals(testResponse.isSuccess(), response.isSuccess());
        if(testResponse instanceof Failure) {
            Failure failureResponse = (Failure)testResponse;
            Failure failure = (Failure)response;
            assertEquals(failureResponse.getMessage(),failure.getMessage());
        }
    }
}
