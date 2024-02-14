package fr.epita.assistants.jws.converter;

import fr.epita.assistants.jws.data.model.GameModel;
import fr.epita.assistants.jws.data.model.PlayerModel;
import fr.epita.assistants.jws.domain.entity.GameEntity;
import fr.epita.assistants.jws.domain.entity.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class GameConverter {
    public List<GameEntity> convertToEntityList(List<GameModel> models)
    {
        List<GameEntity> result = new ArrayList<>();
        for (GameModel model: models)
        {
            result.add(convertToEntity(model));
        }
        return result;
    }

    public GameEntity convertToEntity(GameModel model)
    {
        GameEntity entity = new GameEntity();
        entity.starttime = model.starttime;
        entity.id = model.id;
        entity.state = model.state;
        return entity;
    }

    public GameModel convertToModel(GameEntity entity)
    {
        GameModel model = new GameModel();
        model.starttime = entity.starttime;
        model.id = entity.id;
        model.state = entity.state;
        return model;
    }

    public PlayerModel convertToModel(PlayerEntity player, GameModel game)
    {
        PlayerModel model = new PlayerModel();
        model.name = player.name;
        model.id = player.id;
        model.position = player.position;
        model.posx = player.posx;
        model.posy = player.posy;
        model.lives = player.lives;
        model.lastbomb = player.lastbomb;
        model.lastmovement = player.lastmovement;
        model.game_id = game;
        return model;
    }
}
