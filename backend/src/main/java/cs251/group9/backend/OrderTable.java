package cs251.group9.backend;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
public class OrderTable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderID;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private CustomerTable customer;

    @ManyToOne
    @JoinColumn(name = "gameID", nullable = false)
    private GameTable game;

    private LocalDateTime date;
    private String receipt;
    
}