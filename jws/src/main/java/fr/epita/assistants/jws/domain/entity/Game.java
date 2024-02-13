package fr.epita.assistants.jws.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) public long id;

    public Timestamp starttime;
    @Column @Size(max = 255)
    public String state;
}
