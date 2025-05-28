package src.mpp2024.objectProtocol;

import src.mpp2024.domain.Participant;

import java.io.Serializable;
import java.util.List;

public class GetParticipantsByProbaAndCategorieResponse implements Serializable ,Response{
    private final List<Participant> participants;
    public GetParticipantsByProbaAndCategorieResponse(final List<Participant> participants) {
        this.participants = participants;
    }
    public List<Participant> getParticipants() {
        return participants;
    }
}
