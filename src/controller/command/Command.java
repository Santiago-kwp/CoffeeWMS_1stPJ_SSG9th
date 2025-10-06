package controller.command;

import domain.user.User; // User 모델 import

  /**
   * 모든 커맨드 객체가 구현해야 할 공통 인터페이스입니다.
   * execute 메서드는 커맨드 실행 시 발생할 수 있는 모든 예외를 던질 수 있도록
   * 포괄적인 Exception을 선언합니다.
   *
   * 커맨드 패턴을 사용하면 요청을 보내는 객체(Controller)와 요청을 처리하는 객체(Service)를 완전히 분리할 수 있습니다.
   * 컨트롤러는 어떤 커맨드가 실행되는지만 알면 되고, 커맨드 객체 내부에서 실제 비즈니스 로직(서비스 호출)과 사용자 인터페이스(뷰 호출)가 처리됩니다.
   */
public interface Command {
  void execute(User user) throws Exception;
}