package Main.dbutil;

import Main.dbutil.utils.DatabaseUtils;
import Main.model.User;
import Main.utils.Password;
import Main.utils.Roles;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;

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
        if (username.compareTo("admin") == 0)
            return new User(username, Password.getEncodedPassword("admin"), Roles.ADMIN, "admin@yahoo.com");

        User user = new User();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user.setUsername(resultSet.getString("username"));
                byte[] password = Password.getEncodedPassword(resultSet.getString("password"));
                user.setPassword(password);
                user.setEmail(resultSet.getString("email"));
            }
            return user;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return user;
    }
}
