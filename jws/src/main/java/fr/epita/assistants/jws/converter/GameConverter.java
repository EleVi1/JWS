package fr.epita.assistants.jws.converter;

import fr.epita.assistants.jws.data.model.GameModel;
import fr.epita.assistants.jws.domain.entity.GameEntity;

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
}
