package cs251.group9.backend.controller;

import cs251.group9.backend.entity.*;
import cs251.group9.backend.repository.*;
import cs251.group9.backend.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {
    
    @Autowired 
    private WishlistService wishlistService;
    
    // Add to wishlist
    @PostMapping("/add")
    public ResponseEntity<Wishlist> addToWishlist(@RequestParam Long userID, @RequestParam Integer gameID) {
        try {
            Wishlist wishlist = wishlistService.addToWishlist(userID, gameID);
            return ResponseEntity.ok(wishlist);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    // Get user's wishlist
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Wishlist>> getUserWishlist(@PathVariable Long userId) {
        return ResponseEntity.ok(wishlistService.getUserWishlist(userId));
    }
    
    // Remove from wishlist
    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeFromWishlist(@RequestParam Long userID, @RequestParam Integer gameID) {
        try {
            wishlistService.removeFromWishlist(userID, gameID);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}