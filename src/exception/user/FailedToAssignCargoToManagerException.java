package exception.user;

public class FailedToAssignCargoToManagerException extends RuntimeException {
    public FailedToAssignCargoToManagerException(String message) {
        super(message);
    }
}
