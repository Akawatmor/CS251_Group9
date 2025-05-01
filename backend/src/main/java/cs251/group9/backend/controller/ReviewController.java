/*
 * Review Game
 */
package cs251.group9.backend.controller;

import cs251.group9.backend.entity.*;
import cs251.group9.backend.repository.*;
import cs251.group9.backend.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired 
    private ReviewRepository repo;
    
    //Add Game Review
    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody Review r) {
        return ResponseEntity.ok(repo.save(r));
    }
}*/