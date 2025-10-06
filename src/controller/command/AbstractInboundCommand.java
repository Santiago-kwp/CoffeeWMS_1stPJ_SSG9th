package controller.command;

import domain.user.User;
import service.transaction.InboundService;
import view.transaction.InboundView;


/**
 * 입고 관리와 관련된 모든 커맨드들의 공통 기능을 제공하는 추상 클래스입니다.
 * InboundService와 InboundView의 의존성을 공통으로 가집니다.
 */
public abstract class AbstractInboundCommand implements Command {
    protected final InboundService inboundService;
    protected final InboundView inboundView;

    public AbstractInboundCommand(InboundService inboundService, InboundView inboundView) {
        this.inboundService = inboundService;
        this.inboundView = inboundView;
    }

    @Override
    public abstract void execute(User user) throws Exception;

}