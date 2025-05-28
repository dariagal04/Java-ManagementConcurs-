package src.mpp2024.objectProtocol;

import java.io.Serializable;
import java.util.Map;

public class GetCompetitionsWithParticipantsResponse implements Response, Serializable {
    private final Map<String, Map<String, Integer>> data;

    public GetCompetitionsWithParticipantsResponse(Map<String, Map<String, Integer>> data) {
        this.data = data;
    }

    public Map<String, Map<String, Integer>> getData() {
        return data;
    }
}