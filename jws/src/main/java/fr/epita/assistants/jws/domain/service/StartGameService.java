package fr.epita.assistants.jws.domain.service;

import fr.epita.assistants.jws.data.model.GameModel;
import fr.epita.assistants.jws.data.model.PlayerModel;
import fr.epita.assistants.jws.data.repository.GameRepository;
import fr.epita.assistants.jws.presentation.rest.response.CreatePlayerResponse;
import fr.epita.assistants.jws.presentation.rest.response.GameDetailResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class StartGameService {
    @Inject
    GameRepository gameRepository;

    @Transactional
    public GameDetailResponse startGame(long game_id)
    {
        GameModel game = gameRepository.findById(game_id);
        if (game == null || game.state.compareTo("FINISHED") == 0)
        {
            return null;
        }
        if (game.state.compareTo("STARTING") == 0)
        {
            if (game.players_id.size() > 1)
            {
                game.state = "RUNNING";
            }
            else {
                game.state = "FINISHED";
            }
            gameRepository.persist(game);
        }
        List<PlayerModel> players = game.players_id;
        List<CreatePlayerResponse> players_list = new ArrayList<>();
        for (PlayerModel player:players)
        {
            players_list.add(new CreatePlayerResponse(player.id, player.name, player.lives, player.posx, player.posy));
        }
        return new GameDetailResponse(game.id, game.starttime, game.state, players_list, game.map);
    }
}
