package fr.epita.assistants.jws.domain.service;

import fr.epita.assistants.jws.data.model.GameModel;
import fr.epita.assistants.jws.data.model.PlayerModel;
import fr.epita.assistants.jws.data.repository.GameRepository;
import fr.epita.assistants.jws.data.repository.PlayerRepository;
import fr.epita.assistants.jws.presentation.rest.response.CreatePlayerResponse;
import fr.epita.assistants.jws.presentation.rest.response.GameInfoResponse;
import org.hibernate.Hibernate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class GameInfoService {
    @Inject
    GameRepository gameRepository;

    @Transactional
    public GameInfoResponse getInfo(long game_id)
    {
        GameModel game = gameRepository.findById(game_id);
        if (game == null)
        {
            return null;
        }

        List<PlayerModel> players = game.players_id;
        List<CreatePlayerResponse> players_list = new ArrayList<>();
        for (PlayerModel player:players)
        {
            players_list.add(new CreatePlayerResponse(player.id, player.name, player.lives, player.posx, player.posy));
        }

        return new GameInfoResponse(game.id, game.starttime, game.state, players_list, game.map);
    }
}
