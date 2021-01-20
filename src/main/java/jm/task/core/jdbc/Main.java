package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();
        userDaoJDBC.dropUsersTable();
        userDaoJDBC.createUsersTable();
        userDaoJDBC.saveUser("Alexey", "Kireev", (byte) 26);
        userDaoJDBC.saveUser("Alexey", "Kireev", (byte) 26);
        List<User> users = userDaoJDBC.getAllUsers();
        for (User user : users) {
            System.out.println(user.toString());
        }
        userDaoJDBC.cleanUsersTable();
        userDaoJDBC.dropUsersTable();
    }
}
