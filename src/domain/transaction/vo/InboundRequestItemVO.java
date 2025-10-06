package domain.transaction.vo;

import java.util.Objects;

public final class InboundRequestItemVO { // final 클래스
    private final Long inboundRequestItemId;
    private final Long inboundRequestId;
    private final String coffeeId;
    private final Integer inboundRequestQuantity;

    public InboundRequestItemVO(Long inboundRequestItemId, Long inboundRequestId, String coffeeId, Integer inboundRequestQuantity) {
        this.inboundRequestItemId = inboundRequestItemId;
        this.inboundRequestId = inboundRequestId;
        this.coffeeId = coffeeId;
        this.inboundRequestQuantity = inboundRequestQuantity;
    }

    // Getter만 제공 (Setter 없음)
    public Long getInboundRequestItemId() {
        return inboundRequestItemId;
    }

    public Long getInboundRequestId() {
        return inboundRequestId;
    }

    public String getCoffeeId() {
        return coffeeId;
    }

    public Integer getInboundRequestQuantity() {
        return inboundRequestQuantity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InboundRequestItemVO that = (InboundRequestItemVO) o;
        return Objects.equals(inboundRequestItemId, that.inboundRequestItemId) &&
                Objects.equals(inboundRequestId, that.inboundRequestId) &&
                Objects.equals(coffeeId, that.coffeeId) &&
                Objects.equals(inboundRequestQuantity, that.inboundRequestQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inboundRequestItemId, inboundRequestId, coffeeId, inboundRequestQuantity);
    }

    @Override
    public String toString() {
        return "InboundRequestItemVO{" +
                "inboundRequestItemId=" + inboundRequestItemId +
                ", inboundRequestId=" + inboundRequestId +
                ", coffeeId='" + coffeeId + '\'' +
                ", inboundRequestQuantity=" + inboundRequestQuantity +
                '}';
    }
}