package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Varian", "Rinn", (byte) 30);
        userService.saveUser("Djaina", "Praudmur", (byte) 20);
        userService.saveUser("John", "Sina", (byte) 40);
        userService.saveUser("Tom", "Reddle", (byte) 18);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
