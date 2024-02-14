package fr.epita.assistants.jws.converter;

import fr.epita.assistants.jws.data.model.GameModel;
import fr.epita.assistants.jws.domain.entity.GameEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class GameConverter {

    public static GameEntity convertToEntity(GameModel gameModel)
    {
        GameEntity entity = new GameEntity();
        entity.setId(gameModel.getId());
        entity.setState(gameModel.getState());
        entity.setStarttime(gameModel.getStarttime());
        return entity;
    }

    public static List<GameEntity> convertToEntityList(List<GameModel> gameModels) {
        List<GameEntity> res = new ArrayList<>();
        for (GameModel model: gameModels)
        {
            res.add(convertToEntity(model));
        }
        return res;
    }
}
