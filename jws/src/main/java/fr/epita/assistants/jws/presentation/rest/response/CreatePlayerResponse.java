package fr.epita.assistants.jws.presentation.rest.response;

public class CreatePlayerResponse {
    public long id;
    public String name;
    public int lives;
    public int posx;
    public int posy;

    public CreatePlayerResponse(long id, String name, int lives, int posx, int posy) {
        this.id = id;
        this.name = name;
        this.lives = lives;
        this.posx = posx;
        this.posy = posy;
    }
}
