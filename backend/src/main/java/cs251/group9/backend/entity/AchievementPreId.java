package cs251.group9.backend.entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Embeddable
public class AchievementPreId implements Serializable {
    private Long aid;
    private Long prerequisiteAID;
    // equals and hashCode
}
