package cs251.group9.backend.entity;

import java.io.Serializable;

import jakarta.persistence.*;


@Entity
public class AchievementPre {
    @EmbeddedId
    private AchievementPreId id;

    @ManyToOne
    @MapsId("aid")
    @JoinColumn(name = "aid")
    private Achievement4x achievement;

    @ManyToOne
    @MapsId("prerequisiteAID")
    @JoinColumn(name = "prerequisiteAID")
    private Achievement4x prerequisite;
    // getters and setters
}
