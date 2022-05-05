package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    //Session session = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        String sql = "CREATE TABLE IF NOT EXISTS User(id BIGINT  AUTO_INCREMENT," +
                " name VARCHAR(20), lastName VARCHAR(20), age TINYINT, PRIMARY KEY(id))";
        // auto close session object
        try (Session session = Util.getSessionFactory().openSession()) {

            // start the transaction
            transaction = session.beginTransaction();

            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            // commit transaction
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        String sql = "DROP TABLE IF EXISTS User";
        // auto close session object
        try (Session session = Util.getSessionFactory().openSession()) {

            // start the transaction
            transaction = session.beginTransaction();

            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            // commit transaction
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        User user = new User(name, lastName, age);


        // auto close session object
        try (Session session = Util.getSessionFactory().openSession()) {

            // start the transaction
            transaction = session.beginTransaction();

            // save user object
            session.save(user);

            // commit transaction
            transaction.commit();
            System.out.println("User с именем \"" + user.getName() + "\" добавлен в базу данных");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        // auto close session object
        try (Session session = Util.getSessionFactory().openSession()) {

            // start the transaction
            transaction = session.beginTransaction();

            String hql = "DELETE FROM User WHERE id = :id";
            session.createQuery(hql).setParameter("id", id).executeUpdate();

            // commit transaction
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

        }

    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> userList = new ArrayList<>();
        User user = new User();

        // auto close session object
        try (Session session = Util.getSessionFactory().openSession()) {

            // start the transaction
            transaction = session.beginTransaction();

            String sql = "SELECT * FROM User";
            Query query = session.createNativeQuery(sql).addEntity(User.class);
            userList = query.list();
            // commit transaction
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        System.out.println(userList);
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;

        // auto close session object
        try (Session session = Util.getSessionFactory().openSession()) {

            // start the transaction
            transaction = session.beginTransaction();

            String hql = "DELETE FROM User";
            session.createQuery(hql).executeUpdate();

            // commit transaction
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

        }
    }
}
