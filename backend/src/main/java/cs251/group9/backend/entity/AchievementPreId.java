package cs251.group9.backend.entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Embeddable
public class AchievementPreId implements Serializable {
    private Long aid;
    private Long prerequisiteAID;
    
    
    //Getters and Setters
    public Long getAid() {
        return aid;
    }
    public void setAid(Long aid) {
        this.aid = aid;
    }
    public Long getPrerequisiteAID() {
        return prerequisiteAID;
    }
    public void setPrerequisiteAID(Long prerequisiteAID) {
        this.prerequisiteAID = prerequisiteAID;
    }
}
