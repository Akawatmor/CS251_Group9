package cs251.group9.backend;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
public class ReviewTable {
    @EmbeddedId
    private ReviewTableID id;

    private String comment;
    private int score;

    // Getters and Setters
}

