package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь

        UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();
        userDao.createUsersTable();
        userDao.saveUser("Василий", "Семенов", (byte)40);
        userDao.saveUser("Максим", "Тарасов", (byte)25);
        userDao.saveUser("Тарас", "Савельев", (byte)38);
        userDao.saveUser("Ирина", "Иванова", (byte)35);
        userDao.getAllUsers();
        userDao.removeUserById(1);
        userDao.cleanUsersTable();
        //userDao.dropUsersTable();

    }
}
