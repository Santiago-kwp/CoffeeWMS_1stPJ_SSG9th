package service.transaction;

import domain.transaction.InboundItem;
import domain.transaction.OutboundItem;
import domain.transaction.OutboundRequest;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface OutboundService {

    void submitOutboundRequest(OutboundRequest outboundRequest);

    List<OutboundItem> getUnapprovedRequestsByMember(String memberId) throws SQLException;
    List<OutboundItem> getApprovedRequestsByMember(String memberId) throws SQLException;

    Map<String, Integer> getUnapprovedRequests();
    void processOutboundRequest(String outboundRequestId, String jsonItems) throws SQLException;
    List<OutboundItem> getOutboundRequestItems(String memberId, String requestId) throws SQLException;




    Map<String, Integer> getMemberApprovedOutboundRequests();
}
