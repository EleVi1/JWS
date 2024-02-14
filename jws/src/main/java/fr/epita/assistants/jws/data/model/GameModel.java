package fr.epita.assistants.jws.data.model;

import org.yaml.snakeyaml.DumperOptions;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "game")
public class GameModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) public long id;

    public Timestamp starttime;
    @Column @Size(max = 255)
    public String state;

    @ManyToMany
    @JoinTable(
            name = "game_player",
            joinColumns = { @JoinColumn(name = "gamemodel_id", referencedColumnName = "id")},
            inverseJoinColumns = { @JoinColumn(name = "players_id", referencedColumnName = "id")}
    )
    public Set<PlayerModel> players_id = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "game_map", joinColumns = @JoinColumn(name = "gamemodel_id"))
    public List<String> map = new ArrayList<>();
}

