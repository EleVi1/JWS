package fr.epita.assistants.jws.domain.service;

import fr.epita.assistants.jws.data.model.GameModel;
import fr.epita.assistants.jws.data.model.PlayerModel;
import fr.epita.assistants.jws.data.repository.GameRepository;
import fr.epita.assistants.jws.data.repository.PlayerRepository;
import fr.epita.assistants.jws.presentation.rest.response.CreatePlayerResponse;
import fr.epita.assistants.jws.presentation.rest.response.GameDetailResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class JoinGameService {
    @Inject
    GameRepository gameRepository;

    @Inject
    PlayerRepository playerRepository;

    @Transactional
    public Response joinGame(long game_id, String player_name)
    {
        GameModel game = gameRepository.findById(game_id);
        if (game == null)
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if(game.state.compareTo("RUNNING") == 0 || game.state.compareTo("FINISHED") == 0
                || game.players_id.size() == 4)
        {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        PlayerModel addedPlayer = new PlayerModel();
        addedPlayer.name = player_name;
        addedPlayer.lives = 3;
        if (game.players_id.size() == 1)
        {
            addedPlayer.posx = 15;
            addedPlayer.posy = 1;
        }
        if (game.players_id.size() == 2)
        {
            addedPlayer.posx = 15;
            addedPlayer.posy = 13;
        }
        if (game.players_id.size() == 3)
        {
            addedPlayer.posx = 1;
            addedPlayer.posy = 13;
        }
        addedPlayer.game_id = game;

        game.players_id.add(addedPlayer);
        gameRepository.persist(game);
        playerRepository.persist(addedPlayer);

        List<PlayerModel> players = game.players_id;
        List<CreatePlayerResponse> players_list = new ArrayList<>();
        for (PlayerModel player:players)
        {
            players_list.add(new CreatePlayerResponse(player.id, player.name, player.lives, player.posx, player.posy));
        }

        return Response.ok(new GameDetailResponse(game.id, game.starttime, game.state, players_list, game.map)).build();
    }
}
