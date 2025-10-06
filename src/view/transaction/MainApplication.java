package view.transaction;

// Main.java 또는 애플리케이션의 시작점
import model.transaction.InboundRequestDAO;
import model.transaction.InboundRequestDAOImpl;
import model.transaction.InboundRequestItemDAO;
import model.transaction.InboundRequestItemDAOImpl;
import service.transaction.InboundService;
import service.transaction.InboundServiceImpl;
// ... Controller, View 등 ...

public class MainApplication {
    public static void main(String[] args) {

        // ===================================================
        // 1. 의존성 객체 생성 (Composition Root)
        // ===================================================
        InboundRequestDAO inboundRequestDAO = new InboundRequestDAOImpl();
        InboundRequestItemDAO inboundRequestItemDAO = new InboundRequestItemDAOImpl();

        // 2. 서비스 계층에 DAO 객체들을 주입하여 생성
        InboundService inboundService = new InboundServiceImpl(inboundRequestDAO, inboundRequestItemDAO);

        // 3. 컨트롤러에 서비스 객체 주입
        // InboundController inboundController = new InboundController(inboundService);

        // 4. 애플리케이션 실행
        // inboundController.run();

        System.out.println("의존성 주입이 완료되었습니다.");
    }
}
