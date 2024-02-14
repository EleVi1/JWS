package fr.epita.assistants.jws.domain.service;

import fr.epita.assistants.jws.converter.GameConverter;
import fr.epita.assistants.jws.data.model.GameModel;
import fr.epita.assistants.jws.data.model.PlayerModel;
import fr.epita.assistants.jws.data.repository.GameRepository;
import fr.epita.assistants.jws.data.repository.PlayerRepository;
import fr.epita.assistants.jws.presentation.rest.response.CreateGameResponse;
import fr.epita.assistants.jws.presentation.rest.response.CreatePlayerResponse;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CreateGameService {

    @ConfigProperty(name = "JWS_MAP_PATH",
            defaultValue = "src/test/resources/map1.rle")
    String map_path;
//    @ConfigProperty(name = "JWS_TICK_DURATION")
//    TimeUnit tick_duration;
//    @ConfigProperty(name = "JWS_DELAY_MOMENT")
//    TimeUnit delay_moment;
//    @ConfigProperty(name = "JWS_DELAY_FREE")
//    TimeUnit delay_free;
//    @ConfigProperty(name = "JWS_DELAY_SHRINK")
//    TimeUnit delay_shrink;

    @Inject
    GameRepository gameRepository;
    @Inject
    PlayerRepository playerRepository;

    @Transactional
    public CreateGameResponse create(String player_name)
    {
        GameModel game = new GameModel();
        game.state = "STARTING";
        game.starttime = Timestamp.from(Instant.now());

        PlayerModel player = new PlayerModel();
        player.name = player_name;
        player.lives = 3;
        player.posx = 1;
        player.posy = 1;
        player.game_id = game;

        List<String> content = new ArrayList<>();
        try {
            content = Files.lines(Paths.get(map_path)).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        game.map = content;
        game.players_id.add(player);

        gameRepository.persist(game);
        playerRepository.persist(player);

        List<CreatePlayerResponse> players = new ArrayList<>();
        players.add(new CreatePlayerResponse(player.id, player.name, player.lives, player.posx, player.posy));
        CreateGameResponse response = new CreateGameResponse(game.id, game.starttime, game.state, players, content);

        return response;
    }
}
