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

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    
    @Autowired 
    private ReviewRepository reviewRepo;
    
    @Autowired
    private ReviewService reviewService;
    
////////////////////// Add review //////////////////////
    @PostMapping("/user={userID}/game={gameID}/comment={comment}/score={score}")
    public ResponseEntity<Review> addReview(@PathVariable Long userID, 
                                          @PathVariable Long gameID,
                                          @PathVariable String comment,
                                          @PathVariable Integer score) {
        try {
            Review review = reviewService.addOrUpdateReview(userID, gameID, comment, score);
            return ResponseEntity.ok(review);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
        }
    }
    
  ///////////////////// Get all reviews for a game //////////////////////
    @GetMapping("/game={gameId}")
    public ResponseEntity<List<Review>> getGameReviews(@PathVariable Long gameId) {
        return ResponseEntity.ok(reviewRepo.findByGameId(gameId));
    }
    
////////////////////// Get all reviews by a user //////////////////////
    @GetMapping("/user={userId}")
    public ResponseEntity<List<Review>> getUserReviews(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewRepo.findByUserId(userId));
    }
    
////////////////////// Get specific review by user and game //////////////////////
    @GetMapping("/user={userId}/game={gameId}")
    public ResponseEntity<Review> getSpecificReview(@PathVariable Long userId, 
                                                 @PathVariable Long gameId) {
        return reviewRepo.findByUserIdAndGameId(userId, gameId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}