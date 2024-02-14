package fr.epita.assistants.jws.presentation.rest.response;

import java.sql.Timestamp;

public class GameListResponse {
    public long id;
    public Timestamp starttime;
    public String state;
    public GameListResponse(long id, Timestamp starttime, String state) {
        this.id = id;
        this.starttime = starttime;
        this.state = state;
    }
}
