package fr.epita.assistants.jws.domain.service;

import fr.epita.assistants.jws.data.repository.GamesListRepository;
import fr.epita.assistants.jws.domain.entity.Game;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class GameListService {

    private GamesListRepository gameListRepository = new GamesListRepository();

    public List<Game> getAll()
    {
        List<Game> res = gameListRepository.listAll();
        return res;
    }

}
