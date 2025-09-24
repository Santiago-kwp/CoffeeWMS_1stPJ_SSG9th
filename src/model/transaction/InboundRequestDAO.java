package model.transaction;

import config.transaction.DBUtil;
import java.sql.Connection;

public class InboundRequestDAO {
  private Connection conn;

  public static void main(String[] args) {
    Connection conn = DBUtil.getConnection();
    System.out.println(conn);
  }

}
