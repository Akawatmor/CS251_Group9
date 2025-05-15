package cs251.group9.backend.service;

import cs251.group9.backend.entity.*;
import cs251.group9.backend.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepo;
    
    @Autowired
    private GameRepository gameRepo;
    
    @Autowired
    private Customer1xRepository customerRepo;
    
    @Autowired
    private OrderService orderService;
    
    @Transactional
    public Review addOrUpdateReview(Long userID, Long gameID, String comment, Integer score) {
        // Validate score
        if (score < 0 || score > 5) {
            throw new IllegalArgumentException("Score must be between 0 and 5");
        }
        
        // Check if user owns the game
        if (!orderService.checkOwnership(userID, gameID)) {
            throw new RuntimeException("User must own the game to review it");
        }
        
        // Get customer and game
        Customer1x customer = customerRepo.findByuserID(userID);
            if (customer == null) throw new RuntimeException("Customer not found"); 
        Game2x game = gameRepo.findBygameID(gameID)
            .orElseThrow(() -> new RuntimeException("Game not found"));
        
        // Create or update review
        ReviewId reviewId = new ReviewId(userID, gameID);
        Review review = reviewRepo.findById(reviewId)
            .orElse(new Review());
        
        if (review.getId() == null) {
            review.setId(reviewId);
            review.setCustomer(customer);
            review.setGame(game);
        }
        
        review.setComment(comment);
        review.setScore(score);
        review.setReviewDate(LocalDateTime.now());
        
        // Save review
        Review savedReview = reviewRepo.save(review);
        
        // Update game rating
        updateGameRating(gameID);
        
        return savedReview;
    }
      private void updateGameRating(Long gameID) {
        Float avgRating = reviewRepo.calculateAverageRatingByGameId(gameID);
        if (avgRating != null) {
            Game2x game = gameRepo.findBygameID(gameID)
                .orElseThrow(() -> new RuntimeException("Game not found"));
            game.setRating(avgRating);
            gameRepo.save(game);
        }
    }
    
    /**
     * Delete a review
     * @param userID User's ID
     * @param gameID Game's ID
     * @return true if deleted, false if not found
     */
    @Transactional
    public boolean deleteReview(Long userID, Long gameID) {
        ReviewId reviewId = new ReviewId(userID, gameID);
        Optional<Review> existingReview = reviewRepo.findById(reviewId);
        
        if (existingReview.isPresent()) {
            reviewRepo.deleteById(reviewId);
            
            // Update game rating after deleting the review
            updateGameRating(gameID);
            return true;
        }
        return false;
    }
    
    /**
     * Get a specific review
     * @param userID User's ID
     * @param gameID Game's ID
     * @return Optional containing the review if found
     */
    public Optional<Review> getReview(Long userID, Long gameID) {
        ReviewId reviewId = new ReviewId(userID, gameID);
        return reviewRepo.findById(reviewId);
    }
}
