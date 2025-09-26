package command.transaction;

import command.Command;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import service.transaction.InboundService;

/**
 * 특정 회원의 미승인 입고 요청 목록을 조회하는 커맨드 클래스입니다.
 * InboundService의 getUnapprovedRequestsByMember 메소드를 실행합니다.
 */
public class ShowUnapprovedRequestsCommand implements Command {

  private final InboundService inboundService;
  private final String memberId;
  private List<Map<String, Object>> result;

  /**
   * ShowUnapprovedRequestsCommand의 생성자.
   *
   * @param inboundService 입고 관련 비즈니스 로직을 처리하는 서비스 객체
   * @param memberId 입고 요청을 조회할 회원의 ID
   */
  public ShowUnapprovedRequestsCommand(InboundService inboundService, String memberId) {
    this.inboundService = inboundService;
    this.memberId = memberId;
  }

  /**
   * 커맨드를 실행하여 미승인 입고 요청 목록을 조회합니다.
   *
   */
  @Override
  public void execute() {
    try {
      this.result = inboundService.getUnapprovedRequestsByMember(memberId);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public List<Map<String, Object>> getResult() {
    return this.result;
  }

}