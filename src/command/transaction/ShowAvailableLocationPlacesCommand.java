package command.transaction;

import controller.command.Command;
import domain.transaction.LocationPlace;
import java.util.List;
import service.transaction.InboundService;

public class ShowAvailableLocationPlacesCommand implements Command {
  private final InboundService inboundService;
  private List<LocationPlace> result;

  public ShowAvailableLocationPlacesCommand(InboundService inboundService) {
    this.inboundService = inboundService;
  }

  @Override
  public void execute() {
    this.result = inboundService.getAvailableLocationPlaces();
  }

  public List<LocationPlace> getResult() {
    return this.result;
  }
}
