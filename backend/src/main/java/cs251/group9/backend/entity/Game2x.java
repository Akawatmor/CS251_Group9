/*
 * Game Entity
 */
package cs251.group9.backend.entity;

import java.time.*;

import jakarta.persistence.*;

/*
 * Entity Declaration
 */
@Entity
public class Game2x {
	
	//Integer ID Developer 2X
    @Id
    @Column(length = 10)
    private Integer gameID;
    
    //Game Basic Info
    private String gName; //Name
    private String gDesc; //Description (Text)
    private Integer gPrice; //Game Fixed Price
    private LocalDateTime gPublishDate; //Game Datetime publish
    
    //Calculate Automatically
    private Float rating;
	
	
    /*
     * Getters and setters
     */
    public Integer getGameID() {
		return gameID;
	}
	public void setGameID(Integer gameID) {
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
