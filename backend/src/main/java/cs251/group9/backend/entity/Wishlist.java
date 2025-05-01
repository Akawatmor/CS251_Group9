package cs251.group9.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Wishlist {
    @EmbeddedId
    private WishlistId id;

    @ManyToOne
    @MapsId("userID")
    @JoinColumn(name = "userID")
    private Customer customer;

    @ManyToOne
    @MapsId("gameID")
    @JoinColumn(name = "gameID")
    private Game game;

    private LocalDateTime date = LocalDateTime.now();
    // getters and setters
}