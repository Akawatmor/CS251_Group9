package cs251.group9.backend.entity;

import jakarta.persistence.*;

@Entity
public class Developer {
    @Id
    private String devID;
    private String devName;
    private String email;
    private String devDesc;
    private String socialMedia;
    private String teamName;
    // getters and setters
}