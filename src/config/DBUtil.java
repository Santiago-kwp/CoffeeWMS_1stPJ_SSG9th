package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBUtil {
  private static ResourceBundle bundle;

  static {
    bundle = ResourceBundle.getBundle("config.dbinfo");

    try {
      Class.forName(bundle.getString("driver")); // 리플렉션으로 드라이버 로딩
//      System.out.println("JDBC 드라이버 로딩 성공");

    } catch (ClassNotFoundException e) {
//      System.out.println("JDBC 드라이버 로딩 실패");
      e.printStackTrace();
    }
  }

  public static Connection getConnection() {

    try {
      return DriverManager.getConnection(
          bundle.getString("url"),
          bundle.getString("username"),
          bundle.getString("password"));

    } catch (SQLException e) {
      System.out.println("JDBC 연결 실패");
      e.printStackTrace();
      return null;
    }
  }

}
