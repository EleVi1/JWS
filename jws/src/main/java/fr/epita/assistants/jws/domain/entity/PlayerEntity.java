package fr.epita.assistants.jws.domain.entity;

import java.sql.Timestamp;

public class PlayerEntity {

    public long id;
    public String name;
    public int posx;
    public int posy;
    public int position;

    public Timestamp lastbomb;
    public Timestamp lastmovement;
    public int lives;
    public long game_id;
}
