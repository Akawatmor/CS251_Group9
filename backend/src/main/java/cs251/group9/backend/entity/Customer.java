package cs251.group9.backend.entity;

import jakarta.persistence.*;


@Entity
public class Customer {
    @Id
    @Column(length = 10)
    private String userID;

    @Column(unique = true, nullable = false)
    private String UName;

    @Column(unique = true, nullable = false)
    private String UEmail;

    private String UNumber;
    private Integer age;
    private String country;
    private String DName;
    private String name;
    private String surname;
    private Integer money = 0;
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
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
