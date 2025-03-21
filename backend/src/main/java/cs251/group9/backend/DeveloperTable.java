package cs251.group9.backend;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
public class DeveloperTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long devID;

    private String devName;
    private String email;
    private String devDesc;
    private String socialMedia;

    // Getters and Setters
}
