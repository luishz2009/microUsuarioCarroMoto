package com.microservice.user;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=bd_users;encrypt=true;trustServerCertificate=true;";
        String username = "sa";
        String password = "serway2009";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Conexión exitosa");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
