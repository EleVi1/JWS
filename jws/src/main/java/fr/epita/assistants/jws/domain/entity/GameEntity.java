package fr.epita.assistants.jws.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
@Table(name = "game")
@Getter
@Setter
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) public long id;

    public Timestamp starttime;
    @Column @Size(max = 255)
    public String state;
}
