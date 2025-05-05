package cs251.group9.backend.entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Embeddable
public class AchievementUserId implements Serializable {
    private Long userID;
    private Long aid;
    // equals and hashCode
}