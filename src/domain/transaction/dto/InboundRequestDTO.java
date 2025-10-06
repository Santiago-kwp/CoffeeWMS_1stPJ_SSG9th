package domain.transaction.dto;

import domain.transaction.vo.InboundStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InboundRequestDTO {
    private Long inboundRequestId;
    private String memberId;
    private String managerId;
    private LocalDate requestDate;
    private LocalDate approvalDate;
    private InboundStatus status;
    private String inboundReceipt;
    private boolean isDeleted;
    private LocalDate isDeletedAt;
    private List<InboundRequestItemDTO> items; // 입고 요청 상세 항목



    public InboundRequestDTO() {
        this.items = new ArrayList<>(); // 기본 생성 시 리스트 초기화
        this.status = InboundStatus.PENDING; // 'PENDING' 상수를 직접 사용
        this.isDeleted = false;
    }


    // 모든 필드를 포함하는 생성자
    public InboundRequestDTO(Long inboundRequestId, String memberId, String managerId, LocalDate requestDate,
                             LocalDate approvalDate, InboundStatus status, String inboundReceipt,
                             boolean isDeleted, LocalDate isDeletedAt, List<InboundRequestItemDTO> items) {
        this.inboundRequestId = inboundRequestId;
        this.memberId = memberId;
        this.managerId = managerId;
        this.requestDate = requestDate;
        this.approvalDate = approvalDate;
        this.status = status;
        this.inboundReceipt = inboundReceipt;
        this.isDeleted = isDeleted;
        this.isDeletedAt = isDeletedAt;
        this.items = (items != null) ? new ArrayList<>(items) : new ArrayList<>();
    }

    /**
     * ResultSet으로부터 InboundRequestDTO 객체를 생성하는 생성자.
     * InboundRequest 테이블의 단일 로우 데이터를 매핑합니다.
     * 연관된 InboundRequestItemDTO 리스트(items)는 이 생성자에서 채워지지 않습니다.
     * 이는 일반적으로 DAO에서 별도의 쿼리를 통해 로드됩니다.
     *
     * @param rs 데이터베이스 쿼리 결과 ResultSet
     * @throws SQLException SQL 관련 예외 발생 시
     */
    public InboundRequestDTO(ResultSet rs) throws SQLException {
        this.inboundRequestId = rs.getLong("inbound_request_id");
        this.memberId = rs.getString("member_id");
        this.managerId = rs.getString("manager_id"); // NULL 가능 필드

        // Date -> LocalDate 변환
        java.sql.Date sqlRequestDate = rs.getDate("request_date");
        this.requestDate = (sqlRequestDate != null) ? sqlRequestDate.toLocalDate() : null;

        java.sql.Date sqlApprovalDate = rs.getDate("approval_date");
        this.approvalDate = (sqlApprovalDate != null) ? sqlApprovalDate.toLocalDate() : null;

        // String -> InboundStatus Enum 변환
        String statusString = rs.getString("status");
        this.status = (statusString != null) ? InboundStatus.fromString(statusString) : InboundStatus.PENDING; // 기본값 설정 또는 예외 처리

        this.inboundReceipt = rs.getString("inbound_receipt"); // NULL 가능 필드
        this.isDeleted = rs.getBoolean("is_deleted");

        java.sql.Date sqlIsDeletedAt = rs.getDate("is_deleted_at");
        this.isDeletedAt = (sqlIsDeletedAt != null) ? sqlIsDeletedAt.toLocalDate() : null;

        this.items = new ArrayList<>(); // ResultSet 생성 시에는 아이템 리스트는 비어있게 초기화 (별도 로딩 필요)
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


    public List<InboundRequestItemDTO> getItems() {
        return items;
    }

    public void setItems(List<InboundRequestItemDTO> items) {
        this.items = (items != null) ? new ArrayList<>(items) : new ArrayList<>(); // 방어적 복사
    }

    public InboundStatus getStatus() {
        return status;
    }

    public void setStatus(InboundStatus status) {
        this.status = status;
    }

    public String getInboundReceipt() {
        return inboundReceipt;
    }

    public void setInboundReceipt(String inboundReceipt) {
        this.inboundReceipt = inboundReceipt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public LocalDate getIsDeletedAt() {
        return isDeletedAt;
    }

    public void setIsDeletedAt(LocalDate isDeletedAt) {
        this.isDeletedAt = isDeletedAt;
    }

    public void addItem(InboundRequestItemDTO item) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.add(item);
    }
}
