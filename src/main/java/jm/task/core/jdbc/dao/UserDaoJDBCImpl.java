package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    Connection connection;

    public UserDaoJDBCImpl() {
        connection = Util.getConnection();
    }

    public void createUsersTable() {
        Statement statement;
        try {
            statement = connection.createStatement();
            String sql = "CREATE TABLE Users "
                    + "(Id BIGINT PRIMARY KEY AUTO_INCREMENT, "
                    + "Name VARCHAR(255), "
                    + "LastName VARCHAR(255), "
                    + "Age TINYINT);";
            statement.execute(sql);
        } catch (SQLException e) {
            //ignore exception
        }
    }

    public void dropUsersTable() {
        Statement statement;
        try {
            statement = connection.createStatement();
            String sql = "DROP TABLE users;";
            statement.execute(sql);
        } catch (SQLException e) {
            //ignore exception
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String sql = "INSERT INTO users (Name, LastName, Age) VALUES ('" + name + "', '" + lastName + "', " + age + ");";
//            String sql = "INSERT INTO users (Name, lastName, Age) VALUES ('Nikita', 'Skornyakov', 23);";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            //ignore exception
        }
    }

    public void removeUserById(long id) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String sql = "DELETE FROM users WHERE id=" + id + ";";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            //ignore exception
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            String sql = "SELECT * FROM Users;";
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User temp = new User();
                temp.setId(resultSet.getLong(1));
                temp.setName(resultSet.getString(2));
                temp.setLastName(resultSet.getString(3));
                temp.setAge(resultSet.getByte(4));
                list.add(temp);
            }
        } catch (SQLException e) {
            //ignore exception
        }
        return list;
    }

    public void cleanUsersTable() {
        Statement statement;
        try {
            statement = connection.createStatement();
            String sql = "TRUNCATE TABLE users;";
            statement.execute(sql);
        } catch (SQLException e) {
            //ignore exception
        }
    }
}
