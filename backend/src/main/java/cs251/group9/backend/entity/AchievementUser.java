package cs251.group9.backend.entity;

import jakarta.persistence.*;

@Entity
public class AchievementUser {
    @EmbeddedId
    private AchievementUserId id;

    @ManyToOne
    @MapsId("userID")
    @JoinColumn(name = "userID")
    private Customer1x customer;

    @ManyToOne
    @MapsId("aid")
    @JoinColumn(name = "aid")
    private Achievement4x achievement;
    // getters and setters
}