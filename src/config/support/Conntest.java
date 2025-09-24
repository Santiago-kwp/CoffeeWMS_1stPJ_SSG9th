package config.support;
import config.DBUtil;

import java.sql.Connection;

public class Conntest {
    public static void main(String[] args) {
        Connection conn = DBUtil.getConnection();
        System.out.println(conn);
    }

}