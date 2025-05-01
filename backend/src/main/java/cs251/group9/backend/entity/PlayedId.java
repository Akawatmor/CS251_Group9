package cs251.group9.backend.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.*;

@Embeddable
public class PlayedId implements Serializable {
    private Long userID;
    private Long gameID;
    
    // Default constructor
    public PlayedId() {}
    
    public PlayedId(Long userID, Long gameID) {
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
        PlayedId playedId = (PlayedId) o;
        return Objects.equals(userID, playedId.userID) &&
               Objects.equals(gameID, playedId.gameID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, gameID);
    }
}
