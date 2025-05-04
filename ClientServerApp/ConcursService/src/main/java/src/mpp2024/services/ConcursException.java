package src.mpp2024.services;

public class ConcursException extends Exception {
    public ConcursException(){}

    public ConcursException(String message) {
        super(message);
    }

    public ConcursException(String message, Throwable cause) {super(message, cause);}
}
