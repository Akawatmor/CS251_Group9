package cs251.group9.backend.entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Embeddable
public class AchievementPreId implements Serializable {
    private String aid;
    private String prerequisiteAID;
    // equals and hashCode
}
