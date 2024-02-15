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

@ApplicationScoped
public class BombService {
    @ConfigProperty(name = "JWS_TICK_DURATION", defaultValue = "2")
    String tick_duration;
    @ConfigProperty(name = "JWS_DELAY_BOMB", defaultValue = "1000")
    String delay_bomb;
    @Inject
    GameRepository gameRepository;

    @Inject
    PlayerRepository playerRepository;

    @Transactional
    public Response putBomb(long gameId, long playerId, int posX, int posY) {
        GameModel game = gameRepository.findById(gameId);
        PlayerModel player = playerRepository.findById(playerId);
        if (game == null || player == null)
        {
            return Response.status(Response.Status.NOT_FOUND).build(); // Not Found 404
        }
        if (game.state.compareTo("RUNNING") != 0 || player.lives == 0 ||
                (posY != player.posy || posX != player.posx))
        {
            return Response.status(Response.Status.BAD_REQUEST).build(); // Bad Request 400
        }

        if (player.lastbomb != null)
        {
            Timestamp time = player.lastbomb;
            long offset = (Long.parseLong(tick_duration) * Long.parseLong(delay_bomb));
            time.setTime(time.getTime() + offset);
            if (Timestamp.from(Instant.now()).before(time))
            {
                return Errors.sendTooManyRequest();
            }
        }
        GameConverter conv = new GameConverter();
        List<String> decodedMap = conv.decodeMap(game.map);

        StringBuilder line = new StringBuilder(decodedMap.get(posY));
        line.deleteCharAt(posX);
        line.insert(posX, 'B');
        decodedMap.remove(posY);
        decodedMap.add(posY, line.toString());
        game.map = conv.encodeMap(decodedMap);

        player.lastbomb = Timestamp.from(Instant.now());
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

        new Thread(() -> {
                try {
                    Thread.sleep(Long.parseLong(delay_bomb) * Long.parseLong(tick_duration));
                    explode(posX, posY, gameId);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
        }).start();

        return Response.ok(new GameDetailResponse(game.id, game.starttime, game.state, players_list,
                game.map)).build();
    }

    @Transactional
    public void explode(int posX, int posY, long gameId)
    {
        GameModel game = gameRepository.findById(gameId);
        GameConverter conv = new GameConverter();
        List<String> decoded = conv.decodeMap(game.map);

        //Retrieve life of players
        List<PlayerModel> players = game.players_id;
        for (PlayerModel p:players)
        {
            if ((p.posx == posX && p.posy == posY + 1) || (p.posx == posX + 1 && p.posy == posY) ||
                    (p.posx == posX - 1 && p.posy == posY) || (p.posx == posX && p.posy == posY - 1) ||
                    (p.posx == posX && p.posy == posY) )
            {
                p.lives--;
                gameRepository.persist(game);
                playerRepository.persist(p);
            }
        }

        StringBuilder line = new StringBuilder(decoded.get(posY));
        line.deleteCharAt(posX);
        line.insert(posX, 'G');
        if (line.charAt(posX - 1) == 'W')
        {
            line.deleteCharAt(posX - 1);
            line.insert(posX, 'G');
        }
        if (line.charAt(posX + 1) == 'W')
        {
            line.deleteCharAt(posX + 1);
            line.insert(posX, 'G');
        }
        decoded.remove(posY);
        decoded.add(posY, line.toString());

        line = new StringBuilder(decoded.get(posY + 1));
        if (line.charAt(posX) == 'W')
        {
            line.deleteCharAt(posX);
            line.insert(posX, 'G');
        }
        decoded.remove(posY + 1);
        decoded.add(posY + 1, line.toString());

        line = new StringBuilder(decoded.get(posY - 1));
        if (line.charAt(posX) == 'W')
        {
            line.deleteCharAt(posX);
            line.insert(posX, 'G');
        }
        decoded.remove(posY - 1);
        decoded.add(posY - 1, line.toString());

        game.map = conv.encodeMap(decoded);
        game = gameRepository.findById(gameId);
        gameRepository.persist(game);

    }
}
