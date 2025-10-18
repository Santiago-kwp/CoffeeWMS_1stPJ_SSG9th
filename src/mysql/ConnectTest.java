package mysql;
import config.DBUtil;

import java.sql.Connection;

public class
ConnectTest {
    public static void main(String[] args) {
        Connection conn = DBUtil.getConnection();
        System.out.println(conn);
    }

}