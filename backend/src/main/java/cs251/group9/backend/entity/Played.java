package cs251.group9.backend.entity;

import java.time.*;

import jakarta.persistence.*;

@Entity
public class Played {
    @EmbeddedId
    private PlayedId id;

    @ManyToOne
    @MapsId("userID")
    @JoinColumn(name = "userID")
    private Customer customer;

    @ManyToOne
    @MapsId("gameID")
    @JoinColumn(name = "gameID")
    private Game game;
    // getters and setters

	public PlayedId getId() {
		return id;
	}

	public void setId(PlayedId id) {
		this.id = id;
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
    
    
    
}
