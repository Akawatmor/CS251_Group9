package cs251.group9.backend.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.*;

@Embeddable
public class ReviewId implements Serializable {
    private Long userID;
    private Long gameID;
    
    // Default constructor
    public ReviewId() {}
    
    public ReviewId(Long userID, Long gameID) {
        this.userID = userID;
        this.gameID = gameID;
    }
    
    // Getters and setters
    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getGameID() {
        return gameID;
    }

    public void setGameID(Long gameID) {
        this.gameID = gameID;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewId reviewId = (ReviewId) o;
        return Objects.equals(userID, reviewId.userID) &&
               Objects.equals(gameID, reviewId.gameID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, gameID);
    }
}