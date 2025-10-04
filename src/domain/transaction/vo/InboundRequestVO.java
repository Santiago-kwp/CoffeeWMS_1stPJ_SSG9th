package domain.transaction.vo;


import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects; // Objects.hash() 사용을 위해 추가

public final class InboundRequestVO { // final 클래스로 선언하여 상속 방지
    private final Long inboundRequestId;
    private final String memberId;
    private final String managerId;
    private final LocalDate requestDate; // LocalDate는 불변 객체이므로 방어적 복사 필요 없음!
    private final LocalDate approvalDate; //
    private final String status;
    private final String inboundReceipt;
    private final boolean isDeleted;
    private final LocalDate isDeletedAt; //

    public InboundRequestVO(Long inboundRequestId, String memberId, String managerId, LocalDate requestDate, LocalDate approvalDate, String status, String inboundReceipt, boolean isDeleted, LocalDate isDeletedAt) {
        this.inboundRequestId = inboundRequestId;
        this.memberId = memberId;
        this.managerId = managerId;
        this.requestDate = requestDate;
        this.approvalDate = approvalDate;
        this.status = status;
        this.inboundReceipt = inboundReceipt;
        this.isDeleted = isDeleted;
        this.isDeletedAt = isDeletedAt;
    }

    public Long getInboundRequestId() {
        return inboundRequestId;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getManagerId() {
        return managerId;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public String getStatus() {
        return status;
    }

    public String getInboundReceipt() {
        return inboundReceipt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public LocalDate getIsDeletedAt() {
        return isDeletedAt;
    }

    // 불변 객체는 equals()와 hashCode()를 제대로 구현하는 것이 중요합니다.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InboundRequestVO that = (InboundRequestVO) o;
        return isDeleted == that.isDeleted &&
                Objects.equals(inboundRequestId, that.inboundRequestId) &&
                Objects.equals(memberId, that.memberId) &&
                Objects.equals(managerId, that.managerId) &&
                Objects.equals(requestDate, that.requestDate) &&
                Objects.equals(approvalDate, that.approvalDate) &&
                Objects.equals(status, that.status) &&
                Objects.equals(inboundReceipt, that.inboundReceipt) &&
                Objects.equals(isDeletedAt, that.isDeletedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inboundRequestId, memberId, managerId, requestDate, approvalDate, status, inboundReceipt, isDeleted, isDeletedAt);
    }

    @Override
    public String toString() {
        return "InboundRequestVO{" +
                "inboundRequestId=" + inboundRequestId +
                ", memberId='" + memberId + '\'' +
                ", managerId='" + managerId + '\'' +
                ", requestDate=" + requestDate +
                ", approvalDate=" + approvalDate +
                ", status='" + status + '\'' +
                ", inboundReceipt='" + inboundReceipt + '\'' +
                ", isDeleted=" + isDeleted +
                ", isDeletedAt=" + isDeletedAt +
                '}';
    }
}