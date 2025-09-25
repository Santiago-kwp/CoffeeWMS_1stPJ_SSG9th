package command.transaction;

import command.Command;
import service.transaction.InboundService;

import java.util.List;
import java.util.Map;

public class ShowMemberUnapprovedCommand implements Command {
    private final InboundService inboundService;
    private List<Map<String, Integer>> result;

    public ShowMemberUnapprovedCommand(InboundService inboundService) {
        this.inboundService = inboundService;
    }

    @Override
    public void execute() {
        this.result = inboundService.getMemberUnapprovedInboundRequests();
    }
    public List<Map<String, Integer>> getResult() {
        return this.result;
    }

}
