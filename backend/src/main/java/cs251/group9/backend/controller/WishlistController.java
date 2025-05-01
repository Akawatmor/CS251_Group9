package cs251.group9.backend.controller;

import cs251.group9.backend.entity.*;
import cs251.group9.backend.repository.*;
import cs251.group9.backend.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {
    @Autowired private WishlistRepository repo;
    
    //Add Wishlist
    @PostMapping
    public ResponseEntity<Wishlist> addToWishlist(@RequestBody Wishlist w) {
        return ResponseEntity.ok(repo.save(w));
    }
}