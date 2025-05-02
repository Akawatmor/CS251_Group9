package cs251.group9.backend.entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Embeddable
public class FriendId implements Serializable {
    private Long userID1;
    private Long userID2;
    // equals and hashCode
}
