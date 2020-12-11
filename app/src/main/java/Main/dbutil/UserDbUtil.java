package Main.dbutil;

import Main.model.User;
import Main.utils.Password;
import Main.model.Roles;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;

public class UserDbUtil {
    private Connection connection;
//    private PreparedStatement preparedStatement;

    private final String TABLE_NAME = "site_user";

    public UserDbUtil() {
        connection = DatabaseConnection.getConnection();
//        try {
//            preparedStatement = connection.prepareStatement();
//            preparedStatement.setQueryTimeout(10);
//        } catch (SQLException throwable) {
//            throwable.printStackTrace();
//        }
    }

    public User getUser(String username) throws InvalidKeySpecException, NoSuchAlgorithmException {
        User user = new User();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user.setUsername(resultSet.getString("username"));
                String password = resultSet.getString("password");
                user.setPassword(password);
                Roles role = Roles.valueOf(resultSet.getString("role"));
                user.setRole(role);
                user.setEmail(resultSet.getString("email"));
            }
            return user;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return user;
    }

    public  ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();

        String query = "SELECT * FROM " + TABLE_NAME;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                Roles role = Roles.valueOf(resultSet.getString("role"));

                User user = new User(username, password, role, email);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return users;
    }
}
