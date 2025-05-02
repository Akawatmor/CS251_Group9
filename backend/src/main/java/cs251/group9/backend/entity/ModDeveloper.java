package cs251.group9.backend.entity;

import jakarta.persistence.*;

@Entity
public class ModDeveloper {
    @EmbeddedId
    private ModDeveloperId id;

    @ManyToOne
    @MapsId("modID")
    @JoinColumn(name = "modID")
    private ModDLC7x mod;

    @ManyToOne
    @MapsId("devID")
    @JoinColumn(name = "devID")
    private Developer3x developer;
    // getters and setters
}
