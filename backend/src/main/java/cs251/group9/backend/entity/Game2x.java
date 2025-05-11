/*
 * Game Entity
 */
package cs251.group9.backend.entity;

import java.time.*;
import java.util.*;

import jakarta.persistence.*;

/*
 * Entity Declaration
 */
@Entity
@Table(name = "game")
public class Game2x {
	
	//Integer ID Game 2X
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_seq")
    @SequenceGenerator(
        name = "game_seq", 
        sequenceName = "game_sequence", 
        initialValue = 1, 
        allocationSize = 1
    )
    @Column(length = 10)
    private Long gameID;
    
    /* Add a @PrePersist method to set the ID value
     * This ensures we start from 2000000001 when creating new records
    */
    @PrePersist
    public void prePersist() {
        if (this.gameID == null) this.gameID = 2000000001L;
    }
    
    //Game Basic Info
    private String gName; //Name
    
    @Column(columnDefinition = "TEXT")
    private String gDesc; //Description (Text)
    
    private Integer gPrice; //Game Fixed Price
    private LocalDateTime gPublishDate; //Game Datetime publish
    
    // File hosting paths
    private String mainExecutablePath;
    
    // Pictures (up to 5)
    private String picture1;
    private String picture2;
    private String picture3;
    private String picture4;
    private String picture5;
    
    //Calculate Automatically
    private Float rating;
	
	
    /*
     * Getters and setters
     */
    public Long getGameID() {
		return gameID;
	}
	public void setGameID(Long gameID) {
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
	public String getMainExecutablePath() {
		return mainExecutablePath;
	}
	public void setMainExecutablePath(String mainExecutablePath) {
		this.mainExecutablePath = mainExecutablePath;
	}
	public String getPicture1() {
		return picture1;
	}
	public void setPicture1(String picture1) {
		this.picture1 = picture1;
	}
	public String getPicture2() {
		return picture2;
	}
	public void setPicture2(String picture2) {
		this.picture2 = picture2;
	}
	public String getPicture3() {
		return picture3;
	}
	public void setPicture3(String picture3) {
		this.picture3 = picture3;
	}
	public String getPicture4() {
		return picture4;
	}
	public void setPicture4(String picture4) {
		this.picture4 = picture4;
	}
	public String getPicture5() {
		return picture5;
	}
	public void setPicture5(String picture5) {
		this.picture5 = picture5;
	}
    
    @Transient
    public List<String> getAllPictures() {
        List<String> pics = new ArrayList<>();
        if (picture1 != null) pics.add(picture1);
        if (picture2 != null) pics.add(picture2);
        if (picture3 != null) pics.add(picture3);
        if (picture4 != null) pics.add(picture4);
        if (picture5 != null) pics.add(picture5);
        return pics;
    }
}
