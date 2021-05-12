package com.project4.cs458.resources;

import java.time.LocalDate;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.QueryParam;
import javax.inject.Inject;
import javax.annotation.security.RolesAllowed;
import com.project4.cs458.requests.CreateSymptomRequest;
import com.project4.cs458.database.SQLiteConn;
import com.project4.cs458.models.HistoryEntry;
import com.project4.cs458.models.User;
import com.project4.cs458.responses.Success;
import com.project4.cs458.utils.Utils;
import com.project4.cs458.responses.Failure;
import com.project4.cs458.responses.Response;
import io.quarkus.security.identity.SecurityIdentity;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

@Path("/v1/history/")
@Produces(MediaType.APPLICATION_JSON)
public class HistoryResource {

    @Inject
    SecurityIdentity securityIdentity; 

    public HistoryResource() {
    }

    Response getHistoryForUser(String startDate, String endDate, String username) {
        if(username == null) {
            return new Failure("Invalid Request");
        }
        int DEFAULT_DAYS = 15;
        String defaultStartDate = LocalDate.now().minusDays(DEFAULT_DAYS).toString();
        String defaultEndDate = LocalDate.now().toString();

        if(startDate == null && endDate == null) {
            startDate = defaultStartDate;
            endDate = defaultEndDate;
        }
        else if(startDate == null || endDate == null) {
            return new Failure("Empty date field");
        }
        else {
            if(!Utils.validateOrderOfDates(startDate, endDate)) {
                return new Failure("Invalid dates");
            }
        }

        String getUserQuery = "SELECT email, name, age, nationality, location FROM users WHERE email='" + username + "';";
        SQLiteConn db = new SQLiteConn();
        Connection conn = db.connect();
        ResultSet rs = null;
        Statement stmt = null;
        User user = null;
        String alert = "";
        ArrayList<HistoryEntry> historyResults = new ArrayList<>();
        ArrayList<HistoryEntry> latestHistoryResults = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(getUserQuery);
            user = new User(rs.getString("email"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getInt("nationality"),
                rs.getInt("location")
            );
        }
        catch(Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        finally {
            try {
                stmt.close();
            }
            catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }

        String getHistoryQuery = "SELECT s_date, symptom FROM symptoms WHERE email='" + username + "' AND s_date BETWEEN '" + startDate + "' AND '" + endDate + "';";

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(getHistoryQuery);
            while(rs.next()) {
                String tmp_date = rs.getString("s_date");
                int tmp_symptom = rs.getInt("symptom");
                boolean found = false;
                for(int i = 0; i < historyResults.size(); i++) {
                    if(historyResults.get(i).getDate().equals(tmp_date)) {
                        historyResults.get(i).getSymptoms().add(tmp_symptom);
                        found = true;
                    }
                }
                if(!found) {
                    ArrayList<Integer> tmp_array = new ArrayList<>();
                    tmp_array.add(tmp_symptom);
                    historyResults.add(new HistoryEntry(tmp_date, tmp_array));
                }

            }
        }
        catch(Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        finally {
            try {
                stmt.close();
            }
            catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
        
        String getLatestHistoryQuery = "SELECT s_date, symptom FROM symptoms WHERE email='" + username + "' AND s_date BETWEEN '" + defaultStartDate + "' AND '" + defaultEndDate + "';";

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(getLatestHistoryQuery);
            while(rs.next()) {
                String tmp_date = rs.getString("s_date");
                int tmp_symptom = rs.getInt("symptom");
                boolean found = false;
                for(int i = 0; i < latestHistoryResults.size(); i++) {
                    if(latestHistoryResults.get(i).getDate().equals(tmp_date)) {
                        latestHistoryResults.get(i).getSymptoms().add(tmp_symptom);
                        found = true;
                    }
                }
                if(!found) {
                    ArrayList<Integer> tmp_array = new ArrayList<>();
                    tmp_array.add(tmp_symptom);
                    latestHistoryResults.add(new HistoryEntry(tmp_date, tmp_array));
                }

            }
        }
        catch(Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        finally {
            try {
                stmt.close();
            }
            catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }

        alert = Utils.generateAlert(latestHistoryResults);

        return new Success.HistorySuccess(user,historyResults,alert);
    }

    @GET
    @RolesAllowed("user")
    public Response getHistory(@QueryParam("start_date") String startDate, @QueryParam("end_date") String endDate) {
        String username = securityIdentity.getPrincipal().getName();

        return getHistoryForUser(startDate, endDate, username);
    }

    Response addSymptomForUser(CreateSymptomRequest request, String username) {

        if(username == null || request == null) {
            return new Failure("Invalid Request");
        }

        LocalDate lt = LocalDate.now();
        SQLiteConn db = new SQLiteConn();
        Connection conn = db.connect();
        ResultSet rs = null;
        boolean result = false;
        Statement stmt = null;

        if(request.getSymptoms() == null) {
            return new Failure("Empty symptoms");
        }

        if(request.getSymptoms().size() == 0) {
            return new Failure("Empty symptoms");
        }

        String checkDateQuery = "SELECT s_date FROM symptoms WHERE email=\'" + username + "\' AND s_date=\'" + lt + "';";

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(checkDateQuery);
            String date = rs.getString("s_date");
            if(date.equals(lt.toString())) {
                return new Failure("Symptoms already added for today");
            }
        }
        catch(Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        finally {
            try {
                stmt.close();
            }
            catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
        
        boolean allWorking = true;
        for(int i = 0; i < request.getSymptoms().size(); i++) {
            String addSymptomQuery = "INSERT INTO symptoms(email,s_date,symptom) VALUES ("
                + "\'" + username + "\',"
                + "\'" + lt + "\',"
                + request.getSymptoms().get(i)
                + ");";

                try {
                    stmt = conn.createStatement();
                    stmt.execute(addSymptomQuery);
                }
                catch(Exception e2) {
                    System.out.println("Exception: " + e2.getMessage());
                    allWorking = false;
                }
                finally {
                    try {
                        stmt.close();
                    }
                    catch(Exception e3) {
                        System.out.println(e3.getMessage());
                        allWorking = false;
                    }
                }
            if(!allWorking) {
                break;
            }
        }
        if(allWorking) {
            result = true;
        }
        
        if(result) {
            return new Success();
        }
        else {
            return new Failure("Database Error");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    public Response add(CreateSymptomRequest request) {
        String username = securityIdentity.getPrincipal().getName();
        return addSymptomForUser(request, username);
    }
    
}
