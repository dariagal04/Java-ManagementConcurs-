package src.mpp2024.objectProtocol;

import java.io.Serializable;

public class GetParticipantByCNPRequest implements Serializable ,Request {
    public String CNP;
    public GetParticipantByCNPRequest(String CNP) {
        this.CNP = CNP;
    }
    public String getCNP() {
        return CNP;
    }
}
