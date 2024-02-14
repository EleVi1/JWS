package fr.epita.assistants.jws.presentation.rest.response;

import fr.epita.assistants.jws.domain.entity.GameEntity;

import java.util.ArrayList;
import java.util.List;

public class GameListResponse {
    List<GameEntity> games = new ArrayList<>();
    public GameListResponse(List<GameEntity> games) {
        this.games = games;
    }
}
