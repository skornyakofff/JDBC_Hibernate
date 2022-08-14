package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Nikita", "Skornyakov", (byte) 23);
        userService.saveUser("Bob", "Smith", (byte) 23);
        userService.saveUser("Tom", "Hanks", (byte) 23);
        userService.saveUser("Freddy", "Mercury", (byte) 23);

        List<User> list = userService.getAllUsers();
        for (User user : list) {
            System.out.println(user);
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
