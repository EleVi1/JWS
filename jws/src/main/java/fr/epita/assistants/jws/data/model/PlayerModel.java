package fr.epita.assistants.jws.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "player")
@Getter
@Setter
@NoArgsConstructor
public class PlayerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) public long id;

    public Timestamp lastbomb;
    public Timestamp lastmovement;
    public int lives;
    @Column
    @Size(max = 255) public String name;
    public int posx;
    public int posy;
    public int position;

    @ManyToOne
    @JoinColumn(name = "game_id")
    public GameModel game_id;
}
