package cs251.group9.backend.entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Embeddable
public class AchievementUserId implements Serializable {
    private Long userID;
    private Long aid;
    // equals and hashCode

    // Getters and Setters
    public Long getUserID() {
        return userID;
    }
    public void setUserID(Long userID) {
        this.userID = userID;
    }
    public Long getAid() {
        return aid;
    }
    public void setAid(Long aid) {
        this.aid = aid;
    }

}