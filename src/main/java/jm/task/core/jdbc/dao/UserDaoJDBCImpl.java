package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection;

    public UserDaoJDBCImpl() {
        Util util = new Util();
        try {
            connection = util.getConnection();
            connection.setAutoCommit(false);
            System.out.println("Есть контакт");
        } catch (SQLException throwables) {
            System.out.println("Что-то с соединением");
            throwables.printStackTrace();
        }
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE `usersdatabase`.`userstable` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NULL,\n" +
                "  `lastName` VARCHAR(45) NULL,\n" +
                "  `age` TINYINT NULL,\n" +
                "  PRIMARY KEY (`id`));";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            System.out.println("Такая таблица уже существует");
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                System.out.println("Не удалось внести изменения в базу данных");
                throwables.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE `usersdatabase`.`userstable`;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            System.out.println("Такой таблицы не существует");
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                System.out.println("Не удалось внести изменения в базу данных");
                throwables.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO `usersdatabase`.`userstable` (`name`, `lastName`, `age`) VALUES (?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (Exception e) {
            System.out.println("Не удалось внести пользователя в базу данных");
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                System.out.println("Не удалось внести изменения в базу данных");
                throwables.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM `usersdatabase`.`userstable` WHERE (`id` = ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            System.out.println("Не удалось удалить пользователя из базы данных");
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                System.out.println("Не удалось внести изменения в базу данных");
                throwables.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT name, lastname, age FROM usersdatabase.userstable;")){
            while(resultSet.next()) {

                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                users.add(new User(name, lastName, age));
            }
        } catch (SQLException e) {
            System.out.println("Не удалось предоставить информацию обо всех пользователях из базы данных");
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE usersdatabase.userstable;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Не удалось очистить таблицу");
        }
    }
}
