package Main.dbutil;

import Main.model.User;
import Main.model.Roles;
import java.sql.*;
import java.util.ArrayList;

public class UserDbUtil {
    private final Connection connection;

    private PreparedStatement preparedStatement;
    private final String TABLE_NAME = "site_user";
    private final String USERNAME_COLUMN = "username";
    private final String PASSWORD_COLUMN = "password";
    private final String EMAIL_COLUMN = "email";
    private final String ROLE_COLUMN = "role";

    public UserDbUtil() {
        connection = DatabaseConnection.getConnection();
    }

    public User getUser(String username) {
        User user = new User();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user.setUsername(resultSet.getString(USERNAME_COLUMN));
                String password = resultSet.getString(PASSWORD_COLUMN);
                user.setPassword(password);
                Roles role = Roles.valueOf(resultSet.getString(ROLE_COLUMN));
                user.setRole(role);
                user.setEmail(resultSet.getString(EMAIL_COLUMN));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return user;
    }

    public  ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();

        String query = "SELECT * FROM " + TABLE_NAME;

        try {
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString(USERNAME_COLUMN);
                String password = resultSet.getString(PASSWORD_COLUMN);
                String email = resultSet.getString(EMAIL_COLUMN);
                Roles role = Roles.valueOf(resultSet.getString(ROLE_COLUMN));

                User user = new User(username, password, role, email);
                users.add(user);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public boolean createUser(User user) {
        String username = getUser(user.getUsername()).getUsername();
        if (!(username.isEmpty() || username.isBlank())) return false;

        String columns = "(" + USERNAME_COLUMN + ", "
                + PASSWORD_COLUMN + ", "
                + EMAIL_COLUMN + ", "
                + ROLE_COLUMN + ")";
        String query = "INSERT INTO " + TABLE_NAME + columns + " VALUES (?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getRole().toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePassword(User user) {
        String query = "UPDATE " + TABLE_NAME +
                " set " + PASSWORD_COLUMN + "=? WHERE " + USERNAME_COLUMN + "=?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(String username) {
        String query = "DELETE FROM " + TABLE_NAME +
                " WHERE " + USERNAME_COLUMN + "=?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
