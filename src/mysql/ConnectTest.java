package mysql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectTest {
    public static void main(String[] args) {
        // Railway 서버 URL, 사용자 이름, 비밀번호
        String url = "jdbc:mysql://[Railway_MySQL_URL]:[Port]/[Database_Name]";
        String username = "[User]";
        String password = "[Password]";

        // MySQL 8.0.x 부터 드라이버 클래스가 변경됨
        String driver = "com.mysql.cj.jdbc.Driver";

        try {
            // 드라이버 로드
            Class.forName(driver);

            // 데이터베이스 연결
            Connection conn = DriverManager.getConnection(url, username, password);

            System.out.println("데이터베이스 연결 성공!");

            // 연결 닫기
            conn.close();

        } catch (ClassNotFoundException e) {
            System.err.println("JDBC 드라이버를 찾을 수 없습니다.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("데이터베이스 연결 실패!");
            e.printStackTrace();
        }
    }
}