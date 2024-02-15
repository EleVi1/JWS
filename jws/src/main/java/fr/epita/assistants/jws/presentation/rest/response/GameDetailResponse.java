package fr.epita.assistants.jws.presentation.rest.response;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class GameDetailResponse {
    public Timestamp startTime;
    public String state;
    public List<CreatePlayerResponse> players;

    public List<String> map = new ArrayList<>();
    public long id;
    public GameDetailResponse(long id, Timestamp starttime, String state, List<CreatePlayerResponse> players,
                              List<String> content) {
        this.id = id;
        this.startTime = starttime;
        this.state = state;
        this.map = content;
        this.players = players;
    }
}
