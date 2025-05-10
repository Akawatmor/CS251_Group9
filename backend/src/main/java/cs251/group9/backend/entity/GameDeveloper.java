package cs251.group9.backend.entity;

import jakarta.persistence.*;


@Entity
public class GameDeveloper {
    @EmbeddedId
    private GameDeveloperId id;

    @ManyToOne
    @MapsId("gameID")
    @JoinColumn(name = "gameID")
    private Game2x game;

    @ManyToOne
    @MapsId("devID")
    @JoinColumn(name = "devID")
    private Developer3x developer;
    // getters and setters
    public GameDeveloperId getId() {
        return id;
    }
    public void setId(GameDeveloperId id) {
        this.id = id;
    }
    public Game2x getGame() {
        return game;
    }
    public void setGame(Game2x game) {
        this.game = game;
    }
    public Developer3x getDeveloper() {
        return developer;
    }
    public void setDeveloper(Developer3x developer) {
        this.developer = developer;
    }
}
