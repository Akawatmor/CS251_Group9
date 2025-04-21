package cs251.group9.backend.entity;

import java.time.*;

import jakarta.persistence.*;

@Entity
public class Game {
    @Id
    private String gameID;
    
    private String gName;
    private Integer gPrice;
    private String gDesc;
    private LocalDateTime gPublishDate;
    private Float rating;
	
    // getters and setters
    public String getGameID() {
		return gameID;
	}
	public void setGameID(String gameID) {
		this.gameID = gameID;
	}
	public String getgName() {
		return gName;
	}
	public void setgName(String gName) {
		this.gName = gName;
	}
	public Integer getgPrice() {
		return gPrice;
	}
	public void setgPrice(Integer gPrice) {
		this.gPrice = gPrice;
	}
	public String getgDesc() {
		return gDesc;
	}
	public void setgDesc(String gDesc) {
		this.gDesc = gDesc;
	}
	public LocalDateTime getgPublishDate() {
		return gPublishDate;
	}
	public void setgPublishDate(LocalDateTime gPublishDate) {
		this.gPublishDate = gPublishDate;
	}
	public Float getRating() {
		return rating;
	}
	public void setRating(Float rating) {
		this.rating = rating;
	}
    
    
}
