package src.mpp2024.objectProtocol;

public class ErrorResponse implements Response {
    private final String message;

    public ErrorResponse(final String message) {this.message = message;}

    public String getMessage() {return message;}
}
