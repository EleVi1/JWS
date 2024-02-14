package fr.epita.assistants.jws.data.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor

public class Game_Map {
    @Column (name = "gamemodel_id")
    public long gamemodel_id;

    @Column (name = "map")
    public String map;
}
