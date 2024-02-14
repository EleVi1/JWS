package fr.epita.assistants.jws.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.security.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "player")
@Getter
@Setter
@NoArgsConstructor
public class PlayerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) public long id;

    public Timestamp lastbomb;
    public Timestamp lastmovement;
    public int lives;
    @Column
    @Size(max = 255) public String name;
    public int posx;
    public int posy;
    public int position;

    @ManyToOne
    @JoinColumn(name = "game_id")//, referencedColumnName = "id")
    public GameModel game_id;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "game_player", joinColumns = @JoinColumn(name = "id"))
    public Set<Game_Player> game_players = new HashSet<>();
}
