package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.dropUsersTable();
        userService.createUsersTable();
        userService.dropUsersTable();
        userService.dropUsersTable();
        userService.dropUsersTable();
        userService.dropUsersTable();
        userService.dropUsersTable();
        userService.createUsersTable();
        userService.createUsersTable();
        userService.saveUser("Alexey", "Kireev", (byte) 26);
        userService.saveUser("Anduin", "Rinn", (byte) 30);
        userService.removeUserById(1);
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user.toString());
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
