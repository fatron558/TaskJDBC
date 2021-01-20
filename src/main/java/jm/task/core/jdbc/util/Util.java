package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
    final String url="jdbc:mysql://localhost:3306/usersdatabase?serverTimezone=Europe/Moscow";
    final String username="root";
    final String password="root";


    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Что-то не так с драйвером");
            e.printStackTrace();
        }
        return DriverManager.getConnection(url, username, password);
    }
}
