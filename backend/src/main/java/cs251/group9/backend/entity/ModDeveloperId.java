package cs251.group9.backend.entity;

import java.io.Serializable;

import jakarta.persistence.*;


@Embeddable
public class ModDeveloperId implements Serializable {
    private Long modID;
    private Long devID;
    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModDeveloperId)) return false;
        ModDeveloperId that = (ModDeveloperId) o;
        return modID.equals(that.modID) && devID.equals(that.devID);
    }
    @Override
    public int hashCode() {
        return 31 * modID.hashCode() + devID.hashCode();
    }
    // getters and setters
    public Long getModID() {
        return modID;
    }
    public void setModID(Long modID) {
        this.modID = modID;
    }
    public Long getDevID() {
        return devID;
    }
    public void setDevID(Long devID) {
        this.devID = devID;
    }
}
