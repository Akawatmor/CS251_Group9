package cs251.group9.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
public class Review {
    @EmbeddedId
    private ReviewId id;

    @ManyToOne
    @MapsId("userID")
    @JoinColumn(name = "userID")
    private Customer1x customer;

    @ManyToOne
    @MapsId("gameID")
    @JoinColumn(name = "gameID")
    private Game2x game;

    @Column(columnDefinition = "TEXT")
    private String comment;
    
    @Column(nullable = false)
    private Integer score;
    
    private LocalDateTime reviewDate = LocalDateTime.now();
    
    // Getters and setters
    public ReviewId getId() {
        return id;
    }

    public void setId(ReviewId id) {
        this.id = id;
    }

    public Customer1x getCustomer() {
        return customer;
    }

    public void setCustomer(Customer1x customer) {
        this.customer = customer;
    }

    public Game2x getGame() {
        return game;
    }

    public void setGame(Game2x game) {
        this.game = game;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
    
    public LocalDateTime getReviewDate() {
        return reviewDate;
    }
    
    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }
}