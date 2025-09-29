package exception.transaction;

public class OutboundRequestException extends RuntimeException {
    public OutboundRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
