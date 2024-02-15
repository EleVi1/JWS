package fr.epita.assistants.jws.domain.service;

import fr.epita.assistants.jws.converter.GameConverter;
import fr.epita.assistants.jws.data.model.GameModel;
import fr.epita.assistants.jws.data.model.PlayerModel;
import fr.epita.assistants.jws.data.repository.GameRepository;
import fr.epita.assistants.jws.data.repository.PlayerRepository;
import fr.epita.assistants.jws.presentation.rest.response.CreatePlayerResponse;
import fr.epita.assistants.jws.presentation.rest.response.GameDetailResponse;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class StartGameService {
    @ConfigProperty(name = "JWS_TICK_DURATION",
            defaultValue = "20")
    String tick_duration;
    @ConfigProperty(name = "JWS_DELAY_SHRINK",
            defaultValue = "1000")
    String delay_shrink;

    @ConfigProperty(name = "JWS_DELAY_FREE",
            defaultValue = "1000")
    String delay_free;

    @Inject
    GameRepository gameRepository;

    @Inject
    PlayerRepository playerRepository;

    @Transactional
    public GameDetailResponse startGame(long game_id) {
        GameModel game = gameRepository.findById(game_id);
        if (game == null || game.state.compareTo("FINISHED") == 0) {
            return null;
        }
        if (game.state.compareTo("STARTING") == 0) {
            if (game.players_id.size() > 1) {
                game.state = "RUNNING";
            } else {
                game.state = "FINISHED";
            }
            game.starttime = Timestamp.from(Instant.now());
            gameRepository.persist(game);
        }
        List<PlayerModel> players = game.players_id;
        List<CreatePlayerResponse> players_list = new ArrayList<>();
        for (PlayerModel player : players) {
            players_list.add(new CreatePlayerResponse(player.id, player.name, player.lives, player.posx, player.posy));
        }

        new Thread(() -> {
            try {
                Thread.sleep(Long.parseLong(delay_free) * Long.parseLong(tick_duration));
                shrink(game.id, 1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        return new GameDetailResponse(game.id, game.starttime, game.state, players_list, game.map);
    }

    @Transactional
    public void shrink(long gameId, int level) {
        GameModel game = gameRepository.findById(gameId);
        GameConverter conv = new GameConverter();
        List<String> decoded = conv.decodeMap(game.map);
        int count;
        int width = 17;
        StringBuilder line;

        if (game.state.compareTo("FINISHED") == 0) {
            return;
        }

        count = game.players_id.size();
        for (PlayerModel p : game.players_id) {
            if (p.posy == level || p.posx == level || p.posx == width - level - 1
                    || p.posy == 15 - level - 1) {
                p.lives = 0;
                count--;
                playerRepository.persist(p);
            }
        }
        if (count <= 1) {
            game.state = "FINISHED";
        }
        gameRepository.persist(game);


        for (int i = level; i < decoded.size(); i++) {
            if (i == level || i == decoded.size() - level - 1) {
                line = new StringBuilder("MMMMMMMMMMMMMMMMM");
                decoded.remove(i);
                decoded.add(i, line.toString());
            } else {
                line = new StringBuilder(decoded.get(i));
                line.deleteCharAt(level);
                line.insert(level, 'M');
                line.deleteCharAt(width - level - 1);
                line.insert(width - level - 1, 'M');
                decoded.remove(i);
                decoded.add(i, line.toString());
            }
        }
        game.map = conv.encodeMap(decoded);
        gameRepository.persist(game);

        new Thread(() -> {
            try {
                Thread.sleep(Long.parseLong(delay_shrink) * Long.parseLong(tick_duration));
                shrink(gameId, level + 1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
