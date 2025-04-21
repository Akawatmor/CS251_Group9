package cs251.group9.backend.entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
public class ModDeveloper {
    @EmbeddedId
    private ModDeveloperId id;

    @ManyToOne
    @MapsId("modID")
    @JoinColumn(name = "modID")
    private ModDLC mod;

    @ManyToOne
    @MapsId("devID")
    @JoinColumn(name = "devID")
    private Developer developer;
    // getters and setters
}
