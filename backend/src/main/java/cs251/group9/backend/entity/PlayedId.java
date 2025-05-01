package cs251.group9.backend.entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Embeddable
public class PlayedId implements Serializable {
    private Integer userID;
    private Integer gameID;
    // equals and hashCode
    public Integer getUserID() {
        return userID;
    }
    public void setUserID(Integer userID) {
        this.userID = userID;
    }
    public Integer getGameID() {
        return gameID;
    }
    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }
}
