package cs251.group9.backend.entity;

import jakarta.persistence.*;

/*
 * Entity Declaration
 */
@Entity
public class ModDLC {
	
	//Integer ID ModDLC XX
    @Id
    private Integer modID;
    
    //Join 1 Column (GameID)
    @ManyToOne
    @JoinColumn(name = "gameID")
    private Game game;
    
    //Fixed Info
    private String modName;
    private String modInfo;
    private String modType;
    
    /*
     * Getters and Setters
     */
	public Integer getModID() {
		return modID;
	}
	public void setModID(Integer modID) {
		this.modID = modID;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
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

    
    
    
}
