package service.transaction;

import domain.transaction.OutboundItem;
import domain.transaction.OutboundRequest;

import java.sql.SQLException;
import java.util.List;

public interface OutboundService {

    void submitOutboundRequest(OutboundRequest outboundRequest);

    List<OutboundItem> getUnapprovedRequestsByMember(String memberId) throws SQLException;

}
