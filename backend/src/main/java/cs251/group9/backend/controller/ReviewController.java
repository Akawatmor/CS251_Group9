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

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    
    // DTO class for review request
    public static class ReviewRequest {
        private String comment;
        private Integer score;
        
        public String getComment() {
            return comment;
        }
        
        public void setComment(String comment) {
            this.comment = comment;
        }
        
        public Integer getScore() {
            return score;
        }
        
        public void setScore(Integer score) {
            this.score = score;
        }
    }
    
    @Autowired 
    private ReviewRepository reviewRepo;
    
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private Customer1xRepository userRepo;

    @Autowired
    private OrderService orderRepo;
    
////////////////////// Add / Update review //////////////////////
    @PostMapping("/user={userID}/game={gameID}")
    public ResponseEntity<?> addReview(@PathVariable Long userID, 
                                          @PathVariable Long gameID,
                                          @RequestBody ReviewRequest reviewRequest) {
        HashMap<String, Object> response = new HashMap<>();

        // Check if the game exists
        if (gameRepo.findBygameID(gameID).isEmpty()) {
            response.put("message", "Game not found.");
            response.put("success", false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
        }
        // Check if the user exists
        if (userRepo.findById(userID).isEmpty()) {
            response.put("message", "User not found.");
            response.put("success", false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
        }
        // Check if the user not owns the game
        if (!orderRepo.checkOwnership(userID, gameID)) {
            response.put("message", "User not own the game.");
            response.put("success", false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
        }

        try {
            Review review = reviewService.addOrUpdateReview(userID, gameID, 
                                reviewRequest.getComment(), reviewRequest.getScore());
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