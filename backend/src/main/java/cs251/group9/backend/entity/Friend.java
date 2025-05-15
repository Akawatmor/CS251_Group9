package cs251.group9.backend.entity;

import jakarta.persistence.*;

@Entity
public class Friend {
    @EmbeddedId
    private FriendId id;

    @ManyToOne
    @MapsId("userID1")
    @JoinColumn(name = "userID1")
    private Customer1x user1;

    @ManyToOne
    @MapsId("userID2")
    @JoinColumn(name = "userID2")
    private Customer1x user2;
    
    // getters and setters
    public FriendId getId() {
        return id;
    }
    
    public void setId(FriendId id) {
        this.id = id;
    }
    
    public Customer1x getUser1() {
        return user1;
    }
    
    public void setUser1(Customer1x user1) {
        this.user1 = user1;
    }
    
    public Customer1x getUser2() {
        return user2;
    }
    
    public void setUser2(Customer1x user2) {
        this.user2 = user2;
    }
}