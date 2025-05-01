/*
 * Customer Entity
 */

package cs251.group9.backend.entity;

import jakarta.persistence.*;
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
    
}
