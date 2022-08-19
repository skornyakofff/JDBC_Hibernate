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

    public void createUsersTable() throws SQLException {
        Connection connection = Util.getConnection();
        try (connection; Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE Users "
                    + "(Id BIGINT PRIMARY KEY AUTO_INCREMENT, "
                    + "Name VARCHAR(255), "
                    + "LastName VARCHAR(255), "
                    + "Age TINYINT);";
            statement.execute(sql);
            connection.commit();
        } catch (SQLException e) {
            if (!connection.isClosed()) {
                connection.rollback();
            }
        }
    }

    public void dropUsersTable() throws SQLException {
        Connection connection = Util.getConnection();
        try (connection; Statement statement = connection.createStatement()) {
            String sql = "DROP TABLE users;";
            statement.execute(sql);
            connection.commit();
        } catch (SQLException e) {
            if (!connection.isClosed()) {
                connection.rollback();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String sql = "INSERT INTO users (Name, LastName, Age) VALUES (?, ?, ?);";
        Connection connection = Util.getConnection();
        try (connection; PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            if (!connection.isClosed()) {
                connection.rollback();
            }
        }
    }

    public void removeUserById(long id) throws SQLException {
        String sql = "DELETE FROM users WHERE id=?;";
        Connection connection = Util.getConnection();
        try (connection; PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            if (!connection.isClosed()) {
                connection.rollback();
            }
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users;";
        Connection connection = Util.getConnection();

        try (connection; Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {

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
            if (!connection.isClosed()) {
                connection.rollback();
            }
        }
        return list;
    }

    public void cleanUsersTable() throws SQLException {
        Connection connection = Util.getConnection();
        try (connection; Statement statement = connection.createStatement()) {
            String sql = "TRUNCATE TABLE users;";
            statement.execute(sql);
            connection.commit();
        } catch (SQLException e) {
            if (!connection.isClosed()) {
                connection.rollback();
            }
        }
    }
}