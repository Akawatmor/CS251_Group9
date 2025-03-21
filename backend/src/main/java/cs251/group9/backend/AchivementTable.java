package cs251.group9.backend;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
public class AchivementTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aID;

    private String aName;
    private String aDesc;

    // Getters and Setters
}

