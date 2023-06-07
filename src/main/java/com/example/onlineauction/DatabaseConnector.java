package com.example.onlineauction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    public static Connection ConnectDb() throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auction?serverTimezone=Europe/Moscow", "root", "12345");
        return connection;
    }
}
