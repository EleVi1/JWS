package fr.epita.assistants.jws.data.repository;

import fr.epita.assistants.jws.domain.entity.Game;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GamesListRepository implements PanacheRepository<Game> {}
