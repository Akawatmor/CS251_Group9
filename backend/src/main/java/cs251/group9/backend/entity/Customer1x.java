/*
 * Customer Entity
 */

package cs251.group9.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Customer entity class representing a customer in the system
 */
@Entity
@Table(name = "customer")
public class Customer1x {
	
    /**
     * Auto-generated 10-digit ID starting with 1
     * This is the primary key for the customer
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @SequenceGenerator(
        name = "customer_seq", 
        sequenceName = "customer_sequence", 
        initialValue = 1000000001,
        allocationSize = 1
    )
    @Column(nullable = false, updatable = false, length = 10)
    private Long userID;

    /**
     * Username - Must be unique
     * This is used for authentication and identification
     */
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(name = "u_name", unique = true, nullable = false)
    private String uName;
    
    /**
     * Display name
     * This is shown publicly to other users
     */
    @Column(name = "d_name")
    private String dName;
    
    /**
     * Real first name
     */
    @Column(name = "name")
    private String name;
    
    /**
     * Real last name
     */
    @Column(name = "surname")
    private String surname;

    /**
     * User email
     * Must be unique and valid email format
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Column(name = "u_email", unique = true, nullable = false)
    private String uEmail;
    
    /**
     * User phone number
     */
    @Column(name = "u_number")
    private String uNumber;
    
    /**
     * User age
     * May be replaced with birthday in the future
     */
    @Column(name = "age")
    private Integer age;
    
    /**
     * Country ISO code
     * Must be a 3-digit numeric ISO code
     */
    @Pattern(regexp = "^\\d{3}$", message = "Country must be a 3-digit numeric ISO code")
    @Column(name = "country")
    private String country;

    /**
     * Account balance
     * Initialized to 0 when account is created
     */
    @Column(name = "money", nullable = false)
    private Integer money = 0;
    
    /**
     * Password for authentication
     */
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Profile photo path
     * Not stored in database, retrieved from file system
     */
    @Transient
    private String profilePhotoPath;
    
    /**
     * Default constructor required by JPA
     */
    public Customer1x() {
    }
    
    /**
     * Constructor with essential fields
     */
    public Customer1x(String uName, String uEmail, String password) {
        this.uName = uName;
        this.uEmail = uEmail;
        this.password = password;
        this.money = 0;
    }

    // Getter and Setter methods
    
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }
    
    @Override
    public String toString() {
        return "Customer{" +
                "userID=" + userID +
                ", username='" + uName + '\'' +
                ", email='" + uEmail + '\'' +
                '}';
    }
}
