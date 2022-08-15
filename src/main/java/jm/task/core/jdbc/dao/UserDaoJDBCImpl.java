package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            try {
                String sql = "CREATE TABLE Users "
                        + "(Id BIGINT PRIMARY KEY AUTO_INCREMENT, "
                        + "Name VARCHAR(255), "
                        + "LastName VARCHAR(255), "
                        + "Age TINYINT);";
                statement.execute(sql);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            //ignore exception
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            try {
                String sql = "DROP TABLE users;";
                statement.execute(sql);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            //ignore exception
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (Name, LastName, Age) VALUES (?, ?, ?);";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            connection.setAutoCommit(false);

            try {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setByte(3, age);
                preparedStatement.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            //ignore exception
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id=?;";

        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);

            try {
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            //ignore exception
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users;";

        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            connection.setAutoCommit(false);

            try {
                while (resultSet.next()) {
                    User temp = new User();
                    temp.setId(resultSet.getLong(1));
                    temp.setName(resultSet.getString(2));
                    temp.setLastName(resultSet.getString(3));
                    temp.setAge(resultSet.getByte(4));
                    list.add(temp);
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            //ignore exception
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);

            try {
                String sql = "TRUNCATE TABLE users;";
                statement.execute(sql);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            //ignore exception
        }
    }
}