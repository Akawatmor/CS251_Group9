package cs251.group9.backend.entity;

import java.time.*;

import jakarta.persistence.*;

@Entity
@Table(name = "played")
public class Played {
    @EmbeddedId
    private PlayedId id;

    @ManyToOne
    @MapsId("userID")
    @JoinColumn(name = "userID")
    private Customer1x customer;

    @ManyToOne
    @MapsId("gameID")
    @JoinColumn(name = "gameID")
    private Game game;
    
    private LocalDateTime lastPlayed = LocalDateTime.now();
    private Long playTime = 0L; // Play time in minutes
    
    // Getters and setters
    public PlayedId getId() {
        return id;
    }

    public void setId(PlayedId id) {
        this.id = id;
    }

    public Customer1x getCustomer() {
        return customer;
    }

    public void setCustomer(Customer1x customer) {
        this.customer = customer;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
    
    public LocalDateTime getLastPlayed() {
        return lastPlayed;
    }
    
    public void setLastPlayed(LocalDateTime lastPlayed) {
        this.lastPlayed = lastPlayed;
    }
    
    public Long getPlayTime() {
        return playTime;
    }
    
    public void setPlayTime(Long playTime) {
        this.playTime = playTime;
    }
}
