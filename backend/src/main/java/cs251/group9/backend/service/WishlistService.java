package cs251.group9.backend.service;

import cs251.group9.backend.entity.*;
import cs251.group9.backend.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for managing customer wishlists
 */
@Service
public class WishlistService {
    @Autowired
    private WishlistRepository wishlistRepo;
    
    @Autowired
    private Customer1xRepository customerRepo;
    
    @Autowired
    private GameRepository gameRepo;
    
    @Autowired
    private OrderService orderService;
    
    /**
     * Add a game to customer's wishlist
     * @param userID Customer ID
     * @param gameID Game ID
     * @return The created wishlist entry
     * @throws RuntimeException if user already owns the game or game is already in wishlist
     */
    @Transactional
    public Wishlist addToWishlist(Long userID, Long gameID) {
        // Validate parameters
        if (userID == null || gameID == null) {
            throw new IllegalArgumentException("User ID and Game ID cannot be null");
        }
        
        // Check if user already owns the game
        if (orderService.checkOwnership(userID, gameID)) {
            throw new RuntimeException("User already owns this game");
        }
        
        // Check if game is already in wishlist
        WishlistId wishlistId = new WishlistId(userID, gameID);
        if (wishlistRepo.findById(wishlistId).isPresent()) {
            throw new RuntimeException("Game is already in wishlist");
        }
        
        // Get customer and game
        Customer1x customer = customerRepo.findByuserID(userID);
        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }
        
        Game2x game = gameRepo.findBygameID(gameID)
            .orElseThrow(() -> new RuntimeException("Game not found"));
        
        // Create wishlist entry
        Wishlist wishlist = new Wishlist();
        // Ensure ID is properly set with both userID and gameID
        WishlistId newWishlistId = new WishlistId();
        newWishlistId.setUserID(userID);
        newWishlistId.setGameID(gameID);
        wishlist.setId(newWishlistId);
        wishlist.setCustomer(customer);
        wishlist.setGame(game);
        wishlist.setDate(LocalDateTime.now());
        
        try {
            return wishlistRepo.save(wishlist);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save wishlist: " + e.getMessage(), e);
        }
    }
    
    /**
     * Get all games in customer's wishlist
     * @param userID Customer ID
     * @return List of wishlist entries
     * @throws IllegalArgumentException if userID is null
     */
    public List<Wishlist> getUserWishlist(Long userID) {
        if (userID == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return wishlistRepo.findByUserId(userID);
    }
    
    /**
     * Remove a game from customer's wishlist
     * @param userID Customer ID
     * @param gameID Game ID
     * @throws IllegalArgumentException if parameters are null
     */
    @Transactional
    public void removeFromWishlist(Long userID, Long gameID) {
        if (userID == null || gameID == null) {
            throw new IllegalArgumentException("User ID and Game ID cannot be null");
        }
        
        WishlistId wishlistId = new WishlistId(userID, gameID);
        wishlistRepo.deleteById(wishlistId);
    }
    
    /**
     * Check if a game is in customer's wishlist
     * @param userID Customer ID
     * @param gameID Game ID
     * @return true if game is in wishlist, false otherwise
     */
    public boolean isInWishlist(Long userID, Long gameID) {
        if (userID == null || gameID == null) {
            return false;
        }
        
        WishlistId wishlistId = new WishlistId(userID, gameID);
        return wishlistRepo.existsById(wishlistId);
    }
}
