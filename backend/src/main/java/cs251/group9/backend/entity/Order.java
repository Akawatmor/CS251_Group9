package cs251.group9.backend.entity;

import java.time.*;

import jakarta.persistence.*;

@Entity
public class Order {
    @Id
    private String orderID;

    @ManyToOne
    @JoinColumn(name = "userID")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "gameID")
    private Game game;

    private LocalDateTime date = LocalDateTime.now();
    private String receipt;
    
    // getters and setters
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
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
