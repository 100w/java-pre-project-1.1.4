package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;

import jm.task.core.jdbc.util.Util;




public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserDaoHibernateImpl userDao = new UserDaoHibernateImpl();
        //UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();
        userDao.saveUser("Василий", "Семенов", (byte)40);
        userDao.saveUser("Максим", "Тарасов", (byte)25);
        userDao.saveUser("Тарас", "Савельев", (byte)38);
        userDao.saveUser("Ирина", "Иванова", (byte)35);
        userDao.getAllUsers();
        userDao.removeUserById(2);
        userDao.cleanUsersTable();
        userDao.dropUsersTable();

        Util.shutdown();
    }
}

