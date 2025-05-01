package cs251.group9.backend.service;

import cs251.group9.backend.entity.*;
import cs251.group9.backend.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
    
    @Transactional
    public Wishlist addToWishlist(Long userID, Integer gameID) {
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
        Game2x game = gameRepo.findBygameID(gameID)
            .orElseThrow(() -> new RuntimeException("Game not found"));
        
        // Create wishlist entry
        Wishlist wishlist = new Wishlist();
        wishlist.setId(wishlistId);
        wishlist.setCustomer(customer);
        wishlist.setGame(game);
        wishlist.setDate(LocalDateTime.now());
        
        return wishlistRepo.save(wishlist);
    }
    
    public List<Wishlist> getUserWishlist(Long userID) {
        return wishlistRepo.findByUserId(userID);
    }
    
    public void removeFromWishlist(Long userID, Integer gameID) {
        WishlistId wishlistId = new WishlistId(userID, gameID);
        wishlistRepo.deleteById(wishlistId);
    }
}
