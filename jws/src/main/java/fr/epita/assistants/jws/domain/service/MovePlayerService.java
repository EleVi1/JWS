package fr.epita.assistants.jws.domain.service;

import fr.epita.assistants.jws.converter.GameConverter;
import fr.epita.assistants.jws.data.model.GameModel;
import fr.epita.assistants.jws.data.model.PlayerModel;
import fr.epita.assistants.jws.data.repository.GameRepository;
import fr.epita.assistants.jws.data.repository.PlayerRepository;
import fr.epita.assistants.jws.errors.Errors;
import fr.epita.assistants.jws.presentation.rest.response.CreatePlayerResponse;
import fr.epita.assistants.jws.presentation.rest.response.GameDetailResponse;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class MovePlayerService {

    @ConfigProperty(name = "JWS_TICK_DURATION",
    defaultValue = "1")
    String tick_duration;
    @ConfigProperty(name = "JWS_DELAY_MOMENT",
    defaultValue = "1")
    String delay_moment;

    @Inject
    GameRepository gameRepository;

    @Inject
    PlayerRepository playerRepository;

    @Transactional
    public Response move(long gameId, long playerId, int posX, int posY) {
        GameModel game = gameRepository.findById(gameId);
        PlayerModel player = playerRepository.findById(playerId);
        if (game == null || player == null)
        {
            return Errors.sendNotFound(); // Not Found 404
        }
        if (game.state.compareTo("RUNNING") != 0 || player.lives == 0 ||
                (!isValid(posX, posY, player)))
        {
            return Errors.sendBadRequest(); // Bad Request 400
        }
        if (player.lastmovement != null)
        {
            Timestamp time = player.lastmovement;
            long offset = (Long.parseLong(tick_duration) * Long.parseLong(delay_moment));
            time.setTime(time.getTime() + offset);
            if (Timestamp.from(Instant.now()).before(time))
            {
                return Errors.sendTooManyRequest();
            }
        }

        GameConverter conv = new GameConverter();
        List<String> decodedMap = conv.decodeMap(game.map);
        if (decodedMap.get(posY).charAt(posX) == 'M' ||
                decodedMap.get(posY).charAt(posX) == 'B' ||
                decodedMap.get(posY).charAt(posX) == 'W')
        {
            return Errors.sendBadRequest();
        }

        player.posx = posX;
        player.posy = posY;
        player.lastmovement = Timestamp.from(Instant.now());
        gameRepository.persist(game);
        playerRepository.persist(player);

        game = gameRepository.findById(gameId);
        gameRepository.persist(game);

        List<PlayerModel> players = game.players_id;
        List<CreatePlayerResponse> players_list = new ArrayList<>();
        for (PlayerModel p:players)
        {
            players_list.add(new CreatePlayerResponse(p.id, p.name, p.lives, p.posx, p.posy));
        }
        return Response.ok(new GameDetailResponse(game.id, game.starttime, game.state, players_list, game.map)).build();
    }

    public boolean isValid(int posx, int posy, PlayerModel player)
    {
        return ((posx == player.posx && posy == player.posy + 1)
                || (posx == player.posx - 1 && posy == player.posy)
        || (posx == player.posx + 1 && posy == player.posy)
        || (posx == player.posx && posy == player.posy - 1));
    }
}
