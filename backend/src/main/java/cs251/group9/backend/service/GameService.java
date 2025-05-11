package cs251.group9.backend.service;

import cs251.group9.backend.entity.*;
import cs251.group9.backend.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {
    
    @Autowired
    private GameRepository gameRepo;
    
    @Autowired
    private WishlistRepository wishlistRepo;
    
    @Autowired
    private OrderRepository orderRepo;
    
    @Autowired
    private PlayedRepository playedRepo;
    
    @Autowired
    private ModDLCRepository modDLCRepo;
    
    @Autowired
    private GameDeveloperRepository gameDeveloperRepo;
    
    @Autowired
    private GameCategoryRepository gameCategoryRepo;
    
    @Autowired
    private AchievementRepository achievementRepo;
    
    /**
     * Delete a game and all associated entities
     * This method handles the proper deletion order to avoid constraint violations
     * 
     * @param gameId The ID of the game to delete
     * @return true if deletion was successful, false if game was not found
     */
    @Transactional
    public boolean deleteGameWithDependencies(Long gameId) {
        if (!gameRepo.existsById(gameId)) {
            return false;
        }
        
        // Delete all wishlists containing this game
        wishlistRepo.deleteByGameId(gameId);
        
        // Delete all played records for this game
        playedRepo.deleteByGameId(gameId);
        
        // Delete all orders for this game
        orderRepo.deleteByGameId(gameId);
        
        // Delete all mods/DLC for this game
        modDLCRepo.deleteByGameId(gameId);
        
        // Delete game-developer relationships
        gameDeveloperRepo.deleteByGameId(gameId);
        
        // Delete game-category relationships
        gameCategoryRepo.deleteByGameId(gameId);
        
        // Delete achievements for this game
        achievementRepo.deleteByGameId(gameId);
        
        // Finally delete the game itself
        gameRepo.deleteById(gameId);
        
        return true;
    }
}
