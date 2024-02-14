package fr.epita.assistants.jws.data.repository;

import fr.epita.assistants.jws.data.model.GameModel;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GamesListRepository implements PanacheRepository<GameModel> {}
