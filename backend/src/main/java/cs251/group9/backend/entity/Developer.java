/*
 * Developer Entity
 */
package cs251.group9.backend.entity;

import jakarta.persistence.*;

/*
 * Entity Declaration
 */
@Entity
public class Developer {
	
	//Integer ID Developer 3X
	@Id
    @Column(length = 10)
    private Integer devID;
	
	@Column(unique = true, nullable = false)
    private String devName;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    private String devDesc;
    private String socialMedia;
    private String teamName;
	
    
    /*
     * Getters and Setters
     */
    public Integer getDevID() {
		return devID;
	}
	public void setDevID(Integer devID) {
		this.devID = devID;
	}
	public String getDevName() {
		return devName;
	}
	public void setDevName(String devName) {
		this.devName = devName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDevDesc() {
		return devDesc;
	}
	public void setDevDesc(String devDesc) {
		this.devDesc = devDesc;
	}
	public String getSocialMedia() {
		return socialMedia;
	}
	public void setSocialMedia(String socialMedia) {
		this.socialMedia = socialMedia;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
    
    
}