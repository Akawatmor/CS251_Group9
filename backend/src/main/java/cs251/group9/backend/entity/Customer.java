/*
 * Customer Entity
 */

package cs251.group9.backend.entity;

import jakarta.persistence.*;

/*
 * Entity Declaration
 */
@Entity
public class Customer {
	
	//Integer ID Customer 1X
    @Id
    @Column(length = 10)
    private Integer userID;

    //Real Username - Must Be unique
    @Column(unique = true, nullable = false)
    private String UName;
    
    //Displayname
    private String DName;
    
    //Real Name
    private String name;
    
    //Real Surname
    private String surname;

    //Contactable User Email
    @Column(unique = true, nullable = false)
    private String UEmail;
    
    //User Phone Number
    private String UNumber;
    
    //User Age -> Might Use The Birthday Instead
    private Integer age;
    
    //Country Number
    private String country;

    //Money of the User
    private Integer money = 0;
    
    
    /*
     * Getters and Setters
     */
    
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	
	
	public String getUName() {
		return UName;
	}
	public void setUName(String UName) {
		this.UName = UName;
	}
	public String getUEmail() {
		return UEmail;
	}
	public void setUEmail(String UEmail) {
		this.UEmail = UEmail;
	}
	public String getUNumber() {
		return UNumber;
	}
	public void setUNumber(String UNumber) {
		this.UNumber = UNumber;
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
	public String getDName() {
		return DName;
	}
	public void setDName(String DName) {
		this.DName = DName;
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
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}

    // Getters & Setters
    
    
}
