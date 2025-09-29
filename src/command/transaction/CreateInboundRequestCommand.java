package command.transaction;

import command.Command;
import domain.transaction.InboundRequest;
import service.transaction.InboundService;

/**
 * CreateInboundRequestCommand
 * A concrete Command class for submitting a new inbound request.
 * It encapsulates the request and delegates the execution to the InboundService.
 */
public class CreateInboundRequestCommand implements Command {

  private InboundService inboundService;
  private InboundRequest inboundRequest;

  public CreateInboundRequestCommand(InboundService inboundService, InboundRequest inboundRequest) {
    this.inboundService = inboundService;
    this.inboundRequest = inboundRequest;
  }

  @Override
  public void execute() {
    inboundService.submitInboundRequest(inboundRequest);
  }
}
