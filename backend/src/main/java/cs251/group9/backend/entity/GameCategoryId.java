package cs251.group9.backend.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.*;

@Embeddable
public class GameCategoryId implements Serializable {
    private Long gameID;
    private Long cid;
    
    // Default constructor
    public GameCategoryId() {}
    
    public GameCategoryId(Long gameID, Long cid) {
        this.gameID = gameID;
        this.cid = cid;
    }
    
    // Getters and setters
    public Long getGameID() {
        return gameID;
    }
    
    public void setGameID(Long gameID) {
        this.gameID = gameID;
    }
    
    public Long getCid() {
        return cid;
    }
    
    public void setCid(Long cid) {
        this.cid = cid;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameCategoryId that = (GameCategoryId) o;
        return Objects.equals(gameID, that.gameID) && 
               Objects.equals(cid, that.cid);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(gameID, cid);
    }
}
