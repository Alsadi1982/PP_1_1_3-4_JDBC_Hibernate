package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соединения с БД
    private static Connection connection;
    private static final String LOCAL_URL = "jdbc:postgresql://localhost:5432/users";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "123";

    public static Connection getConnection() throws SQLException {
//        if (connection == null) {
//            connection = DriverManager.getConnection(LOCAL_URL, USERNAME, PASSWORD);
//        }
//
//        return connection;
        return DriverManager.getConnection(LOCAL_URL, USERNAME, PASSWORD);
    }
}
