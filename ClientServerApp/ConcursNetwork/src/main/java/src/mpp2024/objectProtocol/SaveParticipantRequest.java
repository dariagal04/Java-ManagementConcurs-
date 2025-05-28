package src.mpp2024.objectProtocol;

import src.mpp2024.domain.Participant;

import java.io.Serializable;

public class SaveParticipantRequest implements Serializable ,Request {
    public Participant participant;
    public SaveParticipantRequest(Participant participant) {
        this.participant = participant;
    }

    public Participant getParticipant() {
        return participant;
    }
}
