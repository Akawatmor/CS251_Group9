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
    public AchievementUserId getId() {
        return id;
    }

    public void setId(AchievementUserId id) {
        this.id = id;
    }

    public Customer1x getCustomer() {
        return customer;
    }

    public void setCustomer(Customer1x customer) {
        this.customer = customer;
    }

    public Achievement4x getAchievement() {
        return achievement;
    }
    public void setAchievement(Achievement4x achievement) {
        this.achievement = achievement;
    }
    
}