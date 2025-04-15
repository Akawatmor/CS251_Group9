/*
 * Customer Entity
 */

package cs251.group9.backend.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "Customer")
public class Customer {

    @Id
    private String userId;

    @Column(unique = true, nullable = false)
    private String uName;

    @Column(unique = true, nullable = false)
    private String uEmail;

    private String uNumber;
    private Integer age;
    private String country;
    private String dName;
    private String name;
    private String surname;

    @Column(precision = 10, scale = 2)
    private BigDecimal money = BigDecimal.ZERO;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUName() {
		return uName;
	}

	public void setUName(String uName) {
		this.uName = uName;
	}

	public String getUEmail() {
		return uEmail;
	}

	public void setUEmail(String uEmail) {
		this.uEmail = uEmail;
	}

	public String getUNumber() {
		return uNumber;
	}

	public void setUNumber(String uNumber) {
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

	public String getDName() {
		return dName;
	}

	public void setDName(String dName) {
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

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

    // Getters and Setters
    
}
