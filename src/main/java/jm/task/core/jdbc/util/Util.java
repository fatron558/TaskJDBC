package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
    final static String url="jdbc:mysql://localhost:3306/usersdatabase?serverTimezone=Europe/Moscow";
    final static String username="root";
    final static String password="root";
    private static ServiceRegistry serviceRegistry;


    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Что-то не так с драйвером");
            e.printStackTrace();
        }
        return DriverManager.getConnection(url, username, password);
    }


    public static SessionFactory getSessionFactory() throws HibernateException {
        SessionFactory sessionFactory = null;
        try {
            Configuration configuration = new Configuration()
                    .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                    .setProperty("hibernate.connection.url", url)
                    .setProperty("hibernate.connection.username", username)
                    .setProperty("hibernate.connection.password", password)
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                    .addAnnotatedClass(User.class);
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                    configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            System.out.println("Не удалось создать сессию");
        }
        return sessionFactory;
    }
    public static void shutdown() {
        if (serviceRegistry != null) {
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
        }
    }
}