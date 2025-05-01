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
    private Game2x game;

    @ManyToOne
    @MapsId("cid")
    @JoinColumn(name = "cid")
    private Category5x category;
    
    // Getters and setters
    public GameCategoryId getId() {
        return id;
    }

    public void setId(GameCategoryId id) {
        this.id = id;
    }

    public Game2x getGame() {
        return game;
    }

    public void setGame(Game2x game) {
        this.game = game;
    }

    public Category5x getCategory() {
        return category;
    }

    public void setCategory(Category5x category) {
        this.category = category;
    }
}