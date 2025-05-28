package src.mpp2024.objectProtocol;

import src.mpp2024.domain.Participant;

import java.io.Serializable;

public class GetParticipantByCNPResponse implements Serializable ,Response{

    public Participant participant;
    public GetParticipantByCNPResponse(Participant participant) {
        this.participant = participant;
    }
    public Participant getParticipant() {
        return participant;
    }
}
