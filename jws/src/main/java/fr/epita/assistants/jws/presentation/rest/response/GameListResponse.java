package fr.epita.assistants.jws.presentation.rest.response;

import fr.epita.assistants.jws.domain.entity.GameEntity;
import fr.epita.assistants.jws.domain.service.GameListService;


import java.util.ArrayList;
import java.util.List;

public class GameListResponse {
    public List<GameEntity> games = new ArrayList<>();
    public GameListResponse() {
        games = new GameListService().getAll();
    }
}
