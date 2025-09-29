package command.transaction;

import command.Command;
import domain.transaction.InboundItem;
import service.transaction.InboundService;

import java.sql.SQLException;
import java.util.List;

public class ProcessInboundRequestCommand implements Command {
    private InboundService inboundService;
    private List<InboundItem> inboundItems;
    private String inboundRequestId;

    public ProcessInboundRequestCommand(InboundService inboundService, String inboundRequestId, List<InboundItem> inboundItems) {
        this.inboundService = inboundService;
        this.inboundRequestId = inboundRequestId;
        this.inboundItems = inboundItems;
    }
    @Override
    public void execute() {
        try {
            inboundService.processInboundRequest(inboundRequestId, inboundItems);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
