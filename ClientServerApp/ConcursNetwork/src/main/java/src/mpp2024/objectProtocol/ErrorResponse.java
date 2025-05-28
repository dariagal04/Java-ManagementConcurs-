package src.mpp2024.objectProtocol;

import java.io.Serializable;

public class ErrorResponse implements Response, Serializable {
    private final String message;

    public ErrorResponse(final String message) {this.message = message;}

    public String getMessage() {return message;}
}
