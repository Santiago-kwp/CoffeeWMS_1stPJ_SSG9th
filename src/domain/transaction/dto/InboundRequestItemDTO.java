package domain.transaction.dto;

import java.io.Serializable;

public class InboundRequestItemDTO implements Serializable {
    private Long inboundRequestItemId;
    private Long inboundRequestId;
    private String coffeeId;
    private Integer inboundRequestQuantity;

    public InboundRequestItemDTO() {
        // 기본 생성자
    }

    public InboundRequestItemDTO(Long inboundRequestItemId, Long inboundRequestId, String coffeeId, Integer inboundRequestQuantity) {
        this.inboundRequestItemId = inboundRequestItemId;
        this.inboundRequestId = inboundRequestId;
        this.coffeeId = coffeeId;
        this.inboundRequestQuantity = inboundRequestQuantity;
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
}

