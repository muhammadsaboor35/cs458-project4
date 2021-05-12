package com.project4.cs458.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import com.project4.cs458.requests.CreateUserRequest;
import com.project4.cs458.requests.LoginRequest;
import com.project4.cs458.database.SQLiteConn;
import com.project4.cs458.responses.Success;

import com.project4.cs458.responses.Failure;
import com.project4.cs458.responses.Response;
import com.project4.cs458.authclient.KeycloackClient;
import com.project4.cs458.utils.Utils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Path("/v1/users/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    public UserResource() {
    }

    @POST
    public Response add(CreateUserRequest request) {
        SQLiteConn db = new SQLiteConn();
        Connection conn = db.connect();
        ResultSet rs = null;
        boolean result = false;
        Statement stmt = null;

        if(!Utils.validateEmail(request.getEmail())) {
            return new Failure("Invalid Email");
        }
        if(!Utils.validateName(request.getName())) {
            return new Failure("Invalid Name");
        }
        if(!Utils.validatePassword(request.getPassword())) {
            return new Failure("Invalid Password");
        }
        if(!Utils.validateAge(request.getAge())) {
            return new Failure("Invalid Age");
        }
        if(!Utils.validateCountry(request.getNationality())) {
            return new Failure("Invalid Nationality");
        }
        if(!Utils.validateCountry(request.getLocation())) {
            return new Failure("Invalid Location");
        }

        String checkUserQuery = "SELECT email FROM users WHERE email=\'" + request.getEmail() + "\';";
        String addUserQuery = "INSERT INTO users(email,name,age,location,nationality) VALUES ("
        + "\'" + request.getEmail() + "\',"
        + "\'" + request.getName() + "\',"
        + request.getAge() + ","
        + request.getLocation() + ","
        + request.getNationality()
        + ");";

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(checkUserQuery);
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
        try {
            rs.getString("email");
        }
        catch(Exception e) {
            try {
                stmt = conn.createStatement();
                stmt.execute(addUserQuery);
                result = true;
            }
            catch(Exception e2) {
                System.out.println("Exception: " + e2.getMessage());
            }
            finally {
                try {
                    stmt.close();
                }
                catch(Exception e3) {
                    System.out.println(e3.getMessage());
                }
            }
        }
        if(result) {
            try {
                KeycloackClient.addUser(request.getEmail(), request.getPassword());
            } catch (Exception e) {
                e.printStackTrace();
                String deleteQuery = "DELETE FROM users WHERE email='"  + request.getEmail() + "';";
                try {
                    stmt = conn.createStatement();
                    stmt.execute(deleteQuery);
                    result = false;
                }
                catch(Exception e2) {
                    result = false;
                }
                finally {
                    try {
                        stmt.close();
                    }
                    catch(Exception e3) {
                        System.out.println(e3.getMessage());
                    }
                }
                return new Failure("Authentication Server Error");
            }
            return new Success();
        }
        else {
            return new Failure("User Already Exists");
        }
    }

    @POST
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createToken(LoginRequest request) {
        if(request.getEmail() == null || request.getPassword() == null){
            return new Failure("username and/or password is incorrect");
        }

        String token = KeycloackClient.getToken(request.getEmail(), request.getPassword());

        if(token != null) {
            return new Success.TokenSucess(token);
        } else {
            return new Failure("username and/or password is incorrect");
        }
    }
}
