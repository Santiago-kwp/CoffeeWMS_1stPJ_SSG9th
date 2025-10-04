package domain.transaction.dto;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class InboundRequestDTO {
    private Long inboundRequestId;
    private String memberId;
    private String managerId;
    private LocalDate requestDate;
    private LocalDate approvalDate;
    private String managerName;
    private String coffeeName;
    private Integer requestQuantity;
    private String status;
    private String inboundReceipt;

    public InboundRequestDTO() {
        this.status = "대기"; // 기본 상태 '대기'
    }


    // 생성자 (조회 또는 수정 시)
    public InboundRequestDTO(Long inboundRequestId, String memberId, String managerId, Date requestDate, String status, String inboundReceipt, List<InboundRequestItemDTO> items) {
        this.inboundRequestId = inboundRequestId;
        this.memberId = memberId;
        this.managerId = managerId;
        this.requestDate = requestDate;
        this.status = status;
        this.inboundReceipt = inboundReceipt;
        this.items = items;
    }

    // Constructor for convenience (optional)
    public InboundRequestDTO(ResultSet rs) throws SQLException {
        this.inboundRequestId = rs.getLong("inbound_request_id");
        this.requestDate = rs.getDate("입고_요청_날짜").toLocalDate();
        // approval_date와 manager_name, member_company_name, inbound_receipt 는 없을 수도 있으므로 null 체크 또는 기본값
        try { this.approvalDate = rs.getDate("승인_날짜").toLocalDate(); } catch (SQLException e) { /* ignore if column not found */ }
        try { this.managerName = rs.getString("승인_관리자명"); } catch (SQLException e) { /* ignore if column not found */ }
        this.coffeeName = rs.getString("커피_이름");
        this.requestQuantity = rs.getInt("요청_수량");
        this.status = rs.getString("요청_상태");
        try { this.inboundReceipt = rs.getString("입고 고지서"); } catch (SQLException e) {  }
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public Long getInboundRequestId() {
        return inboundRequestId;
    }

    public void setInboundRequestId(Long inboundRequestId) {
        this.inboundRequestId = inboundRequestId;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getCoffeeName() {
        return coffeeName;
    }

    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
    }

    public Integer getRequestQuantity() {
        return requestQuantity;
    }

    public void setRequestQuantity(Integer requestQuantity) {
        this.requestQuantity = requestQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInboundReceipt() {
        return inboundReceipt;
    }

    public void setInboundReceipt(String inboundReceipt) {
        this.inboundReceipt = inboundReceipt;
    }
}
