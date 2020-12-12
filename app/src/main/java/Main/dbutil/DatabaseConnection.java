package Main.dbutil;

import Main.dbutil.config.DatabaseConstants;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DatabaseConnection {
    private final static String DRIVER = "org.postgresql.Driver";
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null) return connection;
        return connect();
    }

    private static Connection connect() {

        try {
            Driver driver = (Driver) Class.forName(DRIVER).newInstance();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(DatabaseConstants.DB_URL, DatabaseConstants.DB_USERNAME, DatabaseConstants.DB_PASSWORD);

//            User admin = new User("admin", Password.getEncodedPassword("admin"), Roles.ADMIN, "admin@yahoo.com");
//            PreparedStatement query = connection.prepareStatement("INSERT INTO site_user (username, password, email, role) values (?,?,?,?)");
//            query.setString(1, admin.getUsername());
//            query.setString(2, admin.getPassword());
//            query.setString(3, admin.getEmail());
//            query.setString(4, "ADMIN");
//            query.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}
