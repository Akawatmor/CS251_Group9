package cs251.group9.backend.entity;

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
    public AchievementPreId getId() {
        return id;
    }
    public void setId(AchievementPreId id) {
        this.id = id;
    }
    public Achievement4x getAchievement() {
        return achievement;
    }
    public void setAchievement(Achievement4x achievement) {
        this.achievement = achievement;
    }
    public Achievement4x getPrerequisite() {
        return prerequisite;
    }
    public void setPrerequisite(Achievement4x prerequisite) {
        this.prerequisite = prerequisite;
    }
}
