package cs251.group9.backend.entity;

import jakarta.persistence.*;

/*
 * Entity Declaration
 */
@Entity
@Table(name = "mod_dlc")
public class ModDLC7x {
	
	//Long ID ModDLC 7X
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mod_seq")
    @SequenceGenerator(
        name = "mod_seq", 
        sequenceName = "mod_sequence", 
        initialValue = 1, 
        allocationSize = 1
    )
    @Column(length = 10)
    private Long modID;
    
    /* Add a @PrePersist method to set the ID value
     * This ensures we start from 7000000001 when creating new records
    */
    @PrePersist
    public void prePersist() {
        if (this.modID == null) this.modID = 7000000001L;
    }
    
    //Join 1 Column (GameID)
    @ManyToOne
    @JoinColumn(name = "gameID")
    private Game2x game;
    
    //Fixed Info
    private String modName;
    
    @Column(columnDefinition = "TEXT")
    private String modInfo;
    
    private String modType;
    
    // File path
    private String downloadPath;
    
    /*
     * Getters and Setters
     */
	public Long getModID() {
		return modID;
	}
	public void setModID(Long modID) {
		this.modID = modID;
	}
	public Game2x getGame() {
		return game;
	}
	public void setGame(Game2x game) {
		this.game = game;
	}
	public String getModName() {
		return modName;
	}
	public void setModName(String modName) {
		this.modName = modName;
	}
	public String getModInfo() {
		return modInfo;
	}
	public void setModInfo(String modInfo) {
		this.modInfo = modInfo;
	}
	public String getModType() {
		return modType;
	}
	public void setModType(String modType) {
		this.modType = modType;
	}
	public String getDownloadPath() {
		return downloadPath;
	}
	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}
}
