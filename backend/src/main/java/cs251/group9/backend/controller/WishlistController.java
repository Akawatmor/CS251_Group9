package cs251.group9.backend.controller;

import cs251.group9.backend.entity.*;
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
    
///////////////////// Add to wishlist //////////////////////
    @PostMapping("/user={userID}/game={gameID}")
    public ResponseEntity<?> addToWishlist(@PathVariable Long userID, @PathVariable Long gameID) {
        try {
            Wishlist wishlist = wishlistService.addToWishlist(userID, gameID);
            return ResponseEntity.ok(wishlist);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage()));
        }
    }
    
///////////////////// Get all games in wishlist //////////////////////
    @GetMapping("/user={userId}")
    public ResponseEntity<List<Wishlist>> getUserWishlist(@PathVariable Long userId) {
        return ResponseEntity.ok(wishlistService.getUserWishlist(userId));
    }
    
///////////////////// Get all users who wishlisted a game //////////////////////
    @DeleteMapping("/user={userID}/game={gameID}")
    public ResponseEntity<Void> removeFromWishlist(@PathVariable Long userID, @PathVariable Long gameID) {
        try {
            wishlistService.removeFromWishlist(userID, gameID);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}