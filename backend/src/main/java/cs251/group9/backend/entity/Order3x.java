/*
 * Order Entity
 */

package cs251.group9.backend.entity;

import java.time.*;

import jakarta.persistence.*;

/*
 * Entity Declaration
 */

@Entity
@Table(name = "orders")
public class Order3x {
	
    // Integer ID Order XX
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderID;
    
    // Join 2 Column (User and Game to make relation)
    @ManyToOne
    @JoinColumn(name = "userID")
    private Customer1x customer;

    @ManyToOne
    @JoinColumn(name = "gameID")
    private Game2x game;
    
    // Auto Generated Date from object created
    private LocalDateTime date = LocalDateTime.now();
    
    // Receipt Info
    private String receipt;

    
    // Getters and Setters
    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public Customer1x getCustomer() {
        return customer;
    }

    public void setCustomer(Customer1x customer) {
        this.customer = customer;
    }

    public Game2x getGame() {
        return game;
    }

    public void setGame(Game2x game) {
        this.game = game;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }
}