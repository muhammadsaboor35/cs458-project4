package com.project4.cs458.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteConn {

    public SQLiteConn() {

    }

    public boolean initDatabase() {
        Connection conn = this.connect();
        Statement stmt = null;
        boolean success = false;
        String CreateUserTableSQL = "CREATE TABLE IF NOT EXISTS users (\n"
                + "	email text PRIMARY KEY,\n"
                + "	name text,\n"
                + "	age integer,\n"
                + "	location integer,\n"
                + "	nationality integer\n"
                + ");";
        String CreateSymptomsTableSQL = "CREATE TABLE IF NOT EXISTS symptoms (\n"
                + "	email text,\n"
                + "	s_date text,\n"
                + "	symptom integer,\n"
                + " PRIMARY KEY (email, s_date, symptom)"
                + ");";
        try {
            stmt = conn.createStatement();
            stmt.execute(CreateUserTableSQL);
            stmt.execute(CreateSymptomsTableSQL);
            success = true;
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                stmt.close();
            }
            catch(Exception e) {
                System.out.println(e.getMessage());
                success = false;
            }
        }
        return success;
    }

    public Connection connect() {
        // SQLite connection string
        String classPath = SQLiteConn.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String dbPath = classPath + "../../database/test.db";

        String url = "jdbc:sqlite:" + dbPath;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

}
