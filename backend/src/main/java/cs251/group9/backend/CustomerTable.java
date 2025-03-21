package cs251.group9.backend;

import jakarta.persistence.*;

@Entity
public class CustomerTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    @Column(unique = true, nullable = false)
    private String uEmail;

    private String uNumber;
    private String country;
    private int age;

    @Column(unique = true, nullable = false)
    private String uName;

    private String dName;
    private String name;
    private String surname;
    private int money;

}
