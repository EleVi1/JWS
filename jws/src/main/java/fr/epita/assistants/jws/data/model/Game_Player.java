package fr.epita.assistants.jws.data.model;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor

public class Game_Player {
    @Column (name = "gamemodel_id")
    public long gamemodel_id;

    @Column (name = "players_id")
    public long players_id;
}
