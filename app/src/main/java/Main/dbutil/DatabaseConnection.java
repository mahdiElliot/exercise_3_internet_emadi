package Main.dbutil;

import Main.dbutil.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

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
            connection = DriverManager.getConnection(DatabaseUtils.DB_URL, DatabaseUtils.DB_USERNAME, DatabaseUtils.DB_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}
