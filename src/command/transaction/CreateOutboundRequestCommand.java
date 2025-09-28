package command.transaction;

import command.Command;
import domain.transaction.InboundRequest;
import domain.transaction.OutboundRequest;
import service.transaction.InboundService;
import service.transaction.OutboundService;

/**
 * CreateInboundRequestCommand
 * A concrete Command class for submitting a new outbound request.
 * It encapsulates the request and delegates the execution to the InboundService.
 */
public class CreateOutboundRequestCommand implements Command {

  private OutboundService outboundService;
  private OutboundRequest outboundRequest;

  public CreateOutboundRequestCommand(OutboundService outboundService, OutboundRequest outboundRequest) {
    this.outboundService = outboundService;
    this.outboundRequest = outboundRequest;
  }

  @Override
  public void execute() {
    outboundService.submitOutboundRequest(outboundRequest);
  }
}
