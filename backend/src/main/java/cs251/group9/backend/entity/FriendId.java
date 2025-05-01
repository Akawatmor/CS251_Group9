package cs251.group9.backend.entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Embeddable
public class FriendId implements Serializable {
    private String userID1;
    private String userID2;
    // equals and hashCode
}
