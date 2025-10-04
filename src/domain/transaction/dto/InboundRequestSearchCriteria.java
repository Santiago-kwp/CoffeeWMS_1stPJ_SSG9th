package domain.transaction.dto;

import java.sql.Date; // 또는 java.time.LocalDate

public class InboundRequestSearchCriteria {
    private String memberId;
    private String managerId;
    private String status; // "대기", "승인완료", "입고완료", "승인거절" 등
    private Date startDate; // 요청/승인 시작일
    private Date endDate;   // 요청/승인 종료일
    private String coffeeId; // 특정 커피 ID로 필터링
    private String managerName; // 관리자 이름으로 검색 (LIKE 검색)
    private Boolean isDeleted; // 논리 삭제 여부 (null이면 삭제 여부 무관)

    // 생성자 (선택 사항: 빌더 패턴을 사용하면 더 편리)
    public InboundRequestSearchCriteria() {
        this.isDeleted = false; // 기본적으로 삭제되지 않은 것만 조회
    }

    // Getters and Setters
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
    public String getManagerId() { return managerId; }
    public void setManagerId(String managerId) { this.managerId = managerId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public String getCoffeeId() { return coffeeId; }
    public void setCoffeeId(String coffeeId) { this.coffeeId = coffeeId; }
    public String getManagerName() { return managerName; }
    public void setManagerName(String managerName) { this.managerName = managerName; }
    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }

    // (선택 사항) 빌더 패턴 구현 예시
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String memberId;
        private String managerId;
        private String status;
        private Date startDate;
        private Date endDate;
        private String coffeeId;
        private String managerName;
        private Boolean isDeleted = false; // 기본값

        public Builder memberId(String memberId) { this.memberId = memberId; return this; }
        public Builder managerId(String managerId) { this.managerId = managerId; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder startDate(Date startDate) { this.startDate = startDate; return this; }
        public Builder endDate(Date endDate) { this.endDate = endDate; return this; }
        public Builder coffeeId(String coffeeId) { this.coffeeId = coffeeId; return this; }
        public Builder managerName(String managerName) { this.managerName = managerName; return this; }
        public Builder isDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; return this; }

        public InboundRequestSearchCriteria build() {
            InboundRequestSearchCriteria criteria = new InboundRequestSearchCriteria();
            criteria.setMemberId(this.memberId);
            criteria.setManagerId(this.managerId);
            criteria.setStatus(this.status);
            criteria.setStartDate(this.startDate);
            criteria.setEndDate(this.endDate);
            criteria.setCoffeeId(this.coffeeId);
            criteria.setManagerName(this.managerName);
            criteria.setIsDeleted(this.isDeleted);
            return criteria;
        }
    }


}