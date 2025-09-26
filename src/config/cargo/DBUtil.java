package config.cargo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/testdb1?serverTimezone=Asia/Seoul";

    private static final String USER = "root";
    private static final String PASSWORD = "mysql1234";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb1", "root", "mysql1234");
    }
}
