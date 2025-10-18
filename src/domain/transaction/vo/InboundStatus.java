package domain.transaction.vo;

public enum InboundStatus {
    PENDING("대기"),
    APPROVED("승인완료"),
    REJECTED("승인거절"),
    COMPLETED("입고완료");

    private final String displayName;

    InboundStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    // DB에서 String을 가져와 Enum으로 변환하는 팩토리 메서드
    public static InboundStatus fromString(String text) {
        for (InboundStatus b : InboundStatus.values()) {
            if (b.displayName.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
