package cs251.group9.backend;

import java.io.Serializable;
import java.time.LocalDateTime;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class ReviewTableID implements Serializable {
    @ManyToOne
    @JoinColumn(name = "userID")
    private CustomerTable customer;

    @ManyToOne
    @JoinColumn(name = "gameID")
    private GameTable game;

    private LocalDateTime date;
}
