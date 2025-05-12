package cs251.group9.backend.entity;

import jakarta.persistence.*;

@Entity
public class ModDeveloper {
    @EmbeddedId
    private ModDeveloperId id;

    @ManyToOne
    @MapsId("modID")
    @JoinColumn(name = "modID")
    private ModDLC7x mod;

    @ManyToOne
    @MapsId("devID")
    @JoinColumn(name = "devID")
    private Developer3x developer;
    // getters and setters
    public ModDeveloperId getId() {
        return id;
    }
    public void setId(ModDeveloperId id) {
        this.id = id;
    }
    public ModDLC7x getMod() {
        return mod;
    }
    public void setMod(ModDLC7x mod) {
        this.mod = mod;
    }
    public Developer3x getDeveloper() {
        return developer;
    }
    public void setDeveloper(Developer3x developer) {
        this.developer = developer;
    }
    
}
