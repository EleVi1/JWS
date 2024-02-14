package fr.epita.assistants.jws.presentation.rest.response;


public class GameListResponse {
    public long id;
    public int players;
    public String state;
    public GameListResponse(long id, int players, String state) {
        this.id = id;
        this.players = players;
        this.state = state;
    }
}
