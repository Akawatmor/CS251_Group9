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
}
