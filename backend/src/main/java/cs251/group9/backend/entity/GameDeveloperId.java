package cs251.group9.backend.entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Embeddable
public class GameDeveloperId implements Serializable {
    private Long gameID;
    private Long devID;
    // equals and hashCode
}
