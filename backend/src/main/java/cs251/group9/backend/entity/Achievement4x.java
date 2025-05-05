package cs251.group9.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "achievement")
public class Achievement4x {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "achievement_seq")
    @SequenceGenerator(
        name = "achievement_seq", 
        sequenceName = "achievement_sequence", 
        initialValue = 1, 
        allocationSize = 1
    )
    @Column(length = 10)
    private Long aid;
    
    /* Add a @PrePersist method to set the ID value
     * This ensures we start from 4000000001 when creating new records
    */
    @PrePersist
    public void prePersist() {
        if (this.aid == null) this.aid = 4000000001L;
    }
    
    private String aName;
    
    @Column(columnDefinition = "TEXT")
    private String aDesc;
    
    @ManyToOne
    @JoinColumn(name = "gameID")
    private Game2x game;
    
    // getters and setters
    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public String getaDesc() {
        return aDesc;
    }

    public void setaDesc(String aDesc) {
        this.aDesc = aDesc;
    }
    
    public Game2x getGame() {
        return game;
    }
    
    public void setGame(Game2x game) {
        this.game = game;
    }
}
