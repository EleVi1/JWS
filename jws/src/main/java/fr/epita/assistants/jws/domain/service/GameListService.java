package fr.epita.assistants.jws.domain.service;

import fr.epita.assistants.jws.converter.GameConverter;
import fr.epita.assistants.jws.data.repository.GameRepository;
import fr.epita.assistants.jws.data.model.GameModel;
import fr.epita.assistants.jws.presentation.rest.response.GameListResponse;


import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class GameListService {

    GameRepository gameListRepository = new GameRepository();

    public List<GameListResponse> getAll()
    {
        List<GameListResponse> response = new ArrayList<>();
        List<GameModel> games = gameListRepository.listAll();
        for (GameModel game: games)
        {
            response.add(new GameListResponse(game.id, game.players_id.size(), game.state));
        }
        return response;
    }

}
