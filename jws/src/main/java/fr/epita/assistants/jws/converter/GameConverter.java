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

    public List<String> decodeMap(List<String> map)
    {
        List<String> res = new ArrayList<>();
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < map.size(); i++) // for each line
        {
            for (int j = 0; j < map.get(i).length(); j += 2) // follow line
            {
                for(int k = 0; k < map.get(i).charAt(j) - '0'; k++) // add char
                {
                    line.append(map.get(i).charAt(j+1));
                }
            }
            res.add(i, line.toString());
            line = new StringBuilder();
        }
        return res;
    }

    public List<String> encodeMap(List<String> decodedMap) {
        List<String> res = new ArrayList<>();
        for (String ref : decodedMap)
        {
            int key = 1;
            StringBuilder line = new StringBuilder();
            for (int i = 1; i < ref.length(); i++)
            {
                if (ref.charAt(i) != ref.charAt(i - 1))
                {
                    line.append(key).append(ref.charAt(i - 1));
                    key = 1;
                }
                else {
                    key++;
                    if (key == 9)
                    {
                        line.append(key).append(ref.charAt(i - 1));
                        key = 0;
                    }
                }
            }
            line.append(key).append(ref.charAt(ref.length() - 1));
            res.add(line.toString());
        }
        return res;
    }
}
