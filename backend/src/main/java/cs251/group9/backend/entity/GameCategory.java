package cs251.group9.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "game_category")
public class GameCategory {
    @EmbeddedId
    private GameCategoryId id;

    @ManyToOne
    @MapsId("gameID")
    @JoinColumn(name = "gameID")
    private Game game;

    @ManyToOne
    @MapsId("cid")
    @JoinColumn(name = "cid")
    private Category category;
    
    // Getters and setters
    public GameCategoryId getId() {
        return id;
    }

    public void setId(GameCategoryId id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}