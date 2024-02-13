package fr.epita.assistants.jws.presentation.rest.response;

import fr.epita.assistants.jws.data.model.Game;

import java.util.ArrayList;
import java.util.List;

public class GameListResponse {
    List<Game> games = new ArrayList<>();
    public GameListResponse() {
        //this.games = games.list;
    }
}
