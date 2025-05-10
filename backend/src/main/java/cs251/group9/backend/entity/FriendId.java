package cs251.group9.backend.entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Embeddable
public class FriendId implements Serializable {
    private Long userID1;
    private Long userID2;
    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FriendId)) return false;
        FriendId friendId = (FriendId) o;
        return userID1.equals(friendId.userID1) && userID2.equals(friendId.userID2);
    }
    @Override
    public int hashCode() {
        return 31 * userID1.hashCode() + userID2.hashCode();
    }
    // getters and setters
    public Long getUserID1() {
        return userID1;
    }
    public void setUserID1(Long userID1) {
        this.userID1 = userID1;
    }
    public Long getUserID2() {
        return userID2;
    }
    public void setUserID2(Long userID2) {
        this.userID2 = userID2;
    }
}
