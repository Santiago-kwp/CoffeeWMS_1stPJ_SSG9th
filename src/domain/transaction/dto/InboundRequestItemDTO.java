package domain.transaction.dto;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class InboundRequestItemDTO implements Serializable {
    private static final long serialVersionUID = 1L; // serialVersionUID 추가
    private Long inboundRequestItemId;
    private Long inboundRequestId;
    private String coffeeId;
    private Integer inboundRequestQuantity;

    public InboundRequestItemDTO() {
        // 기본 생성자
    }

    // 전체 필드 생성자
    public InboundRequestItemDTO(Long inboundRequestItemId, Long inboundRequestId, String coffeeId, Integer inboundRequestQuantity) {
        this.inboundRequestItemId = inboundRequestItemId;
        this.inboundRequestId = inboundRequestId;
        this.coffeeId = coffeeId;
        this.inboundRequestQuantity = inboundRequestQuantity;
    }

    /**
     * ResultSet으로부터 InboundRequestItemDTO 객체를 생성하는 생성자.
     * inbound_request_items 테이블의 단일 로우 데이터를 매핑합니다.
     *
     * @param rs 데이터베이스 쿼리 결과 ResultSet
     * @throws SQLException SQL 관련 예외 발생 시
     */
    public InboundRequestItemDTO(ResultSet rs) throws SQLException {
        this.inboundRequestItemId = rs.getLong("inbound_request_item_id");
        this.inboundRequestId = rs.getLong("inbound_request_id");
        this.coffeeId = rs.getString("coffee_id");
        this.inboundRequestQuantity = rs.getInt("inbound_request_quantity"); // NULL 허용 여부 확인 필요. int는 기본값이 0
    }


    // Getters and Setters
    public Long getInboundRequestItemId() {
        return inboundRequestItemId;
    }

    public void setInboundRequestItemId(Long inboundRequestItemId) {
        this.inboundRequestItemId = inboundRequestItemId;
    }

    public Long getInboundRequestId() {
        return inboundRequestId;
    }

    public void setInboundRequestId(Long inboundRequestId) {
        this.inboundRequestId = inboundRequestId;
    }

    public String getCoffeeId() {
        return coffeeId;
    }

    public void setCoffeeId(String coffeeId) {
        this.coffeeId = coffeeId;
    }

    public Integer getInboundRequestQuantity() {
        return inboundRequestQuantity;
    }

    public void setInboundRequestQuantity(Integer inboundRequestQuantity) {
        this.inboundRequestQuantity = inboundRequestQuantity;
    }

    @Override
    public String toString() {
        return "InboundRequestItemDTO{" +
                "inboundRequestItemId=" + inboundRequestItemId +
                ", inboundRequestId=" + inboundRequestId +
                ", coffeeId='" + coffeeId + '\'' +
                ", inboundRequestQuantity=" + inboundRequestQuantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InboundRequestItemDTO that = (InboundRequestItemDTO) o;
        return Objects.equals(inboundRequestItemId, that.inboundRequestItemId) &&
                Objects.equals(inboundRequestId, that.inboundRequestId) &&
                Objects.equals(coffeeId, that.coffeeId) &&
                Objects.equals(inboundRequestQuantity, that.inboundRequestQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inboundRequestItemId, inboundRequestId, coffeeId, inboundRequestQuantity);
    }
}

