package cs251.group9.backend.entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
public class AchievementUser {
    @EmbeddedId
    private AchievementUserId id;

    @ManyToOne
    @MapsId("userID")
    @JoinColumn(name = "userID")
    private Customer customer;

    @ManyToOne
    @MapsId("aid")
    @JoinColumn(name = "aid")
    private Achievement achievement;
    // getters and setters
}