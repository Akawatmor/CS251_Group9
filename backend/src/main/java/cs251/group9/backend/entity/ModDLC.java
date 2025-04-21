package cs251.group9.backend.entity;

import jakarta.persistence.*;

@Entity
public class ModDLC {
    @Id
    private String modID;
    private String modName;
    private String modInfo;
    private String modType;

    @ManyToOne
    @JoinColumn(name = "gameID")
    private Game game;
    
    // getters and setters

	public String getModID() {
		return modID;
	}

	public void setModID(String modID) {
		this.modID = modID;
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

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
    
    
}
