package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() throws SQLException {
        Statement statement = null;

        String sql = "CREATE TABLE IF NOT EXISTS User(id BIGINT  AUTO_INCREMENT," +
                " name VARCHAR(20), lastName VARCHAR(20), age TINYINT, PRIMARY KEY(id))";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    @Override
    public void dropUsersTable() throws SQLException {
        Statement statement = null;
        String sql = "DROP TABLE IF EXISTS User";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) throws SQLException {

        PreparedStatement preparedstatement = null;
        User user = new User(name, lastName, age);
        //User user = new User();
        String sql1 = "INSERT INTO User(name, lastName, age)  VALUES (?, ?, ?)";

        try {
            preparedstatement = connection.prepareStatement(sql1);
            preparedstatement.setString(1, user.getName());
            preparedstatement.setString(2, user.getLastName());
            preparedstatement.setByte(3, user.getAge());
            preparedstatement.executeUpdate();
            System.out.println("User с именем \"" + user.getName() + "\" добавлен в базу данных");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedstatement != null) {
                preparedstatement.close();
            }
        }
    }


    @Override
    public void removeUserById(long id) throws SQLException {
        String sql = "SELECT id, name, lastName, age FROM User";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                if (id == user.getId()) {
                    PreparedStatement preparedstatement = null;
                    String sql1 = "DELETE FROM User WHERE id = ?";
                    try {
                        preparedstatement = connection.prepareStatement(sql1);
                        preparedstatement.setLong(1, user.getId());
                        preparedstatement.executeUpdate();
                        break;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        if (preparedstatement != null) {
                            preparedstatement.close();
                        }

                        statement.close();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT id, name, lastName, age FROM User";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
                //System.out.println(user);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        System.out.println(userList);
        return userList;
    }

    @Override
    public void cleanUsersTable() throws SQLException {

        String sql = "DELETE FROM User";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }

        }

    }
}

