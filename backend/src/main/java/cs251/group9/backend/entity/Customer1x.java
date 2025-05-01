/*
 * Customer Entity
 */

package cs251.group9.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

/*
 * Entity Declaration
 */
@Entity
@Table(name = "customer")
public class Customer1x {
	
    // Auto-generated 10-digit ID starting with 1
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @SequenceGenerator(
        name = "customer_seq", 
        sequenceName = "customer_sequence", 
        initialValue = 1, 
        allocationSize = 1
    )
    @Column(length = 10)
    private Long userID;

    /* Add a @PrePersist method to set the ID value
     * This ensures we start from 1000000001 when creating new records
    */
    @PrePersist
    public void prePersist() {
        if (this.userID == null) this.userID = 1000000001L; // Get last ID from repository and add 1, or use 3000000001L if none exists
    }
    

    //Real Username - Must Be unique
    @Column(unique = true, nullable = false)
    private String uName; // Changed from UName to uName
    
    //Displayname
    private String dName;
    
    //Real Name
    private String name;
    
    //Real Surname
    private String surname;

    //Contactable User Email
    @Column(unique = true, nullable = false)
    private String uEmail; // Changed from UEmail to uEmail
    
    //User Phone Number
    private String uNumber;
    
    //User Age -> Might Use The Birthday Instead
    private Integer age;
    
    // Country Number - ISO 3 digit numeric code
    @Pattern(regexp = "^\\d{3}$", message = "Country must be a 3-digit numeric ISO code")
    private String country;

    //Money of the User
    private Integer money = 0;

    // Profile photo path (not stored in database)
    @Transient
    private String profilePhotoPath;
    
    //Getter and Setter
	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getdName() {
		return dName;
	}

	public void setdName(String dName) {
		this.dName = dName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getuEmail() {
		return uEmail;
	}

	public void setuEmail(String uEmail) {
		this.uEmail = uEmail;
	}

	public String getuNumber() {
		return uNumber;
	}

	public void setuNumber(String uNumber) {
		this.uNumber = uNumber;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public String getProfilePhotoPath() {
		return profilePhotoPath;
	}

	public void setProfilePhotoPath(String profilePhotoPath) {
		this.profilePhotoPath = profilePhotoPath;
	}
    
    
    
}
