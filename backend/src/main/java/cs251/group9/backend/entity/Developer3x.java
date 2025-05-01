/*
 * Developer Entity
 */
package cs251.group9.backend.entity;

import jakarta.persistence.*;

/*
 * Entity Declaration
 */
@Entity
@Table(name = "developer")
public class Developer3x {
	
	// Auto-generated 10-digit ID starting with 1
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "developer_seq")
    @SequenceGenerator(name = "developer_seq", sequenceName = "developer_sequence", initialValue = 3000000001, allocationSize = 1)
    @Column(length = 10)
    private Long devID;
	
	@Column(unique = true, nullable = false)
    private String devName;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    private String devDesc;
    private String socialMedia;
    private String teamName;
	
    
    //Getter and Setter
    public Long getDevID() {
		return devID;
	}
	public void setDevID(Long devID) {
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