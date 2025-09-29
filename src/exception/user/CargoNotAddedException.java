package exception.user;

public class CargoNotAddedException extends RuntimeException {
    public CargoNotAddedException(String message) {
        super(message);
    }
}
