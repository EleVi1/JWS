package fr.epita.assistants.jws.data.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "game")
public class GameModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) public long id;

    public Timestamp starttime;
    @Column @Size(max = 255)
    public String state;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "game_player", joinColumns = @JoinColumn(name = "id"))
    public Set<Game_Player> game_players = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "game_map", joinColumns = @JoinColumn(name = "id"))
    public Set<Game_Map> game_map = new HashSet<>();
}
