package src.mpp2024.objectProtocol;

import src.mpp2024.domain.Participant;
import src.mpp2024.dto.ParticipantDTO;

import java.io.Serializable;

public class NewParticipantResponse implements UpdateResponse, Serializable {
    private final Participant participantDTO;
    public NewParticipantResponse(final Participant participantDTO) {
        this.participantDTO = participantDTO;
    }
    public Participant getParticipantDTO() {return participantDTO;}
}
