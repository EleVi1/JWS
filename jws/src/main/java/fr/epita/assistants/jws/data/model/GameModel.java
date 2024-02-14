package fr.epita.assistants.jws.data.model;

import java.sql.Timestamp;

public class GameModel {
    public long id;
    public Timestamp starttime;
    public String state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getStarttime() {
        return starttime;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}