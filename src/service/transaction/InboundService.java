package service.transaction;

import domain.transaction.Coffee;
import domain.transaction.InboundItem;
import domain.transaction.InboundRequest;
import domain.transaction.LocationPlace;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import view.transaction.InboundView;

/**
 * InboundService
 * Interface defining the business logic for inbound requests.
 */
public interface InboundService {
  void submitInboundRequest(InboundRequest request);
  void updateInboundRequest(InboundRequest request);
  void deleteInboundRequest(String requestId, String memberId);

  List<Coffee> getAllCoffees();

  List<LocationPlace> getAvailableLocationPlaces();

  List<InboundItem> getInboundRequestItems(String memberId, String requestId) throws SQLException;

  List<InboundItem> getUnapprovedRequestsByMember(String memberId) throws SQLException;

  List<InboundItem> getApprovedRequestsByMember(String memberId) throws SQLException;

  Map<String, Integer> getMemberUnapprovedInboundRequests();
  Map<String, Integer> getMemberApprovedInboundRequests();

  void processInboundRequest(String inboundRequestId, List<InboundItem> items) throws SQLException;

  List<InboundItem> showInboundPeriod(String memberId, Date startDate, Date endDate) throws SQLException;
  List<InboundItem> showInboundMonth(String memberId, int month) throws SQLException;

}