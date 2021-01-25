package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session session;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try {
            session = Util.getSessionFactory().openSession();
            SQLQuery sqlQuery = session.createSQLQuery("CREATE TABLE `usersdatabase`.`userstable` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NULL,\n" +
                    "  `lastName` VARCHAR(45) NULL,\n" +
                    "  `age` TINYINT NULL,\n" +
                    "  PRIMARY KEY (`id`));");
            sqlQuery.executeUpdate();
            System.out.println("Создана таблица пользователей");
        } catch (Exception e) {
            System.out.println("Такая таблица уже существует");
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            Util.shutdown();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            session = Util.getSessionFactory().openSession();
            SQLQuery sqlQuery = session.createSQLQuery("DROP TABLE IF EXISTS userstable");
            sqlQuery.executeUpdate();
            System.out.println("Таблица пользователей удалена");
        } catch (Exception e) {
            System.out.println("Такой таблицы не существует");
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            Util.shutdown();
        }
    }


    public void saveUser(String name, String lastName, byte age) {
        try {
            session = Util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (Exception e) {
            System.out.println("Не удалось внести пользователя в базу данных");
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            Util.shutdown();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session = Util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            session.delete(user);
            transaction.commit();
            System.out.println("Пользователь с id=" + id + " удален");
        } catch (Exception e) {
            System.out.println("Не удалось удалить пользователя из базы данных");
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            Util.shutdown();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try {
            session = Util.getSessionFactory().openSession();
            users = session.createCriteria(User.class).list();
            for(User user : users) {
                System.out.println(user);
            }
        } catch (Exception e) {
            System.out.println("Не удалось предоставить информацию обо всех пользователях из базы данных");
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            Util.shutdown();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session = Util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("truncate table userstable");
            sqlQuery.executeUpdate();
            transaction.commit();
            System.out.println("Таблица пользователей очищена");
        } catch (Exception e) {
            System.out.println("Не удалось очистить таблицу");
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            Util.shutdown();
        }
    }
}