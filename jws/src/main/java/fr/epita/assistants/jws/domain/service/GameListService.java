package fr.epita.assistants.jws.domain.service;

import fr.epita.assistants.jws.converter.GameConverter;
import fr.epita.assistants.jws.data.model.GameModel;
import fr.epita.assistants.jws.data.repository.GamesListRepository;
import fr.epita.assistants.jws.domain.entity.GameEntity;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class GameListService {

    private GamesListRepository gameListRepository = new GamesListRepository();

    public List<GameEntity> getAll()
    {
        List<GameModel> res = gameListRepository.listAll();
        return GameConverter.convertToEntityList(res);
    }

}
