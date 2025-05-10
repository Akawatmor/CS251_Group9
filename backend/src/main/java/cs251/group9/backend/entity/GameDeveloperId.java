package cs251.group9.backend.entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Embeddable
public class GameDeveloperId implements Serializable {
    private Long gameID;
    private Long devID;
    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameDeveloperId)) return false;
        GameDeveloperId that = (GameDeveloperId) o;
        return gameID.equals(that.gameID) && devID.equals(that.devID);
    }
    @Override
    public int hashCode() {
        return 31 * gameID.hashCode() + devID.hashCode();
    }
    // getters and setters
    public Long getGameID() {
        return gameID;
    }
    public void setGameID(Long gameID) {
        this.gameID = gameID;
    }
    public Long getDevID() {
        return devID;
    }
    public void setDevID(Long devID) {
        this.devID = devID;
    }
}
