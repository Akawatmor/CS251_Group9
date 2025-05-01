package cs251.group9.backend.entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Embeddable
public class ReviewId implements Serializable {
    private Integer userID;
    private Integer gameID;
    // equals and hashCode
}