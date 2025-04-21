package cs251.group9.backend.entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
public class GameCategory {
    @Id
    private String cid;

    @ManyToOne
    @JoinColumn(name = "gameID")
    private Game game;

    private String cName;
    // getters and setters
}
