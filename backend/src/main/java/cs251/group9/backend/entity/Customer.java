/*
 * Customer Entity
 */

package cs251.group9.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

/*
 * Entity Declaration
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "customer")
public class Customer {
	
    // Auto-generated 10-digit ID starting with 1
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @SequenceGenerator(name = "customer_seq", sequenceName = "customer_sequence", initialValue = 1000000001, allocationSize = 1)
    @Column(length = 10)
    private Integer userID;

    //Real Username - Must Be unique
    @Column(unique = true, nullable = false)
    private String uName; // Changed from UName to uName
    
    //Displayname
    private String DName;
    
    //Real Name
    private String name;
    
    //Real Surname
    private String surname;

    //Contactable User Email
    @Column(unique = true, nullable = false)
    private String uEmail; // Changed from UEmail to uEmail
    
    //User Phone Number
    private String UNumber;
    
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

    // Explicit getter and setter for UName
    public String getUName() {
        return uName; // Return the renamed field
    }
    public void setUName(String UName) {
        this.uName = UName; // Set the renamed field
    }

    // Explicit getter and setter for UEmail
    public String getUEmail() {
        return uEmail; // Return the renamed field
    }
    public void setUEmail(String UEmail) {
        this.uEmail = UEmail; // Set the renamed field
    }

    // Explicit getter and setter for money
    public Integer getMoney() {
        return money;
    }
    public void setMoney(Integer money) {
        this.money = money;
    }
    
}
