package cs251.group9.backend.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import cs251.group9.backend.entity.*;
//import cs251.group9.backend.service.*;
import cs251.group9.backend.repository.*;

import java.util.*;

@RestController
@RequestMapping("/api/user-achievements")
public class AchievementUserController {
    
    @Autowired
    private AchievementUserRepository achievementUserRepo;
    
    @Autowired
    private AchievementRepository achievementRepo;
    
    @Autowired
    private Customer1xRepository customerRepo;
    
    // Get all user achievements
    @GetMapping
    public List<AchievementUser> getAllUserAchievements() {
        return achievementUserRepo.findAll();
    }
    
    // Get achievements for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserAchievements(@PathVariable Long userId) {
        // Assuming you have a method to find by user ID
        // You might need to add this method to your repository
        List<AchievementUser> userAchievements = achievementUserRepo.findByIdUserID(userId);
        
        if (userAchievements.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userAchievements);
    }
    
    // Get users who have earned a specific achievement
    @GetMapping("/achievement/{achievementId}")
    public ResponseEntity<?> getUsersByAchievement(@PathVariable Long achievementId) {
        // Assuming you have a method to find by achievement ID
        // You might need to add this method to your repository
        List<AchievementUser> achievementUsers = achievementUserRepo.findByIdAid(achievementId);
        
        if (achievementUsers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(achievementUsers);
    }
    
    // Award an achievement to a user
    @PostMapping("/user/{userId}/achievement/{achievementId}")
    public ResponseEntity<?> awardAchievement(@PathVariable Long userId, @PathVariable Long achievementId) {
        try {
            // Check if user exists
            Customer1x customer = customerRepo.findByuserID(userId);
            if (customer == null) {
                return ResponseEntity.badRequest().body("User not found with ID: " + userId);
            }
            
            // Check if achievement exists
            Optional<Achievement4x> achievementOpt = achievementRepo.findById(achievementId);
            if (!achievementOpt.isPresent()) {
                return ResponseEntity.badRequest().body("Achievement not found with ID: " + achievementId);
            }
            
            // Check if user already has this achievement
            AchievementUserId id = new AchievementUserId();
            id.setUserID(userId);
            id.setAid(achievementId);
            
            if (achievementUserRepo.existsById(id)) {
                return ResponseEntity.badRequest().body("User already has this achievement");
            }
            
            // Award the achievement
            AchievementUser userAchievement = new AchievementUser();
            userAchievement.setId(id);
            userAchievement.setCustomer(customer);
            userAchievement.setAchievement(achievementOpt.get());
            
            AchievementUser savedAchievement = achievementUserRepo.save(userAchievement);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAchievement);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error awarding achievement: " + e.getMessage());
        }
    }
    
    // Check if a user has a specific achievement
    @GetMapping("/check/user/{userId}/achievement/{achievementId}")
    public ResponseEntity<?> hasAchievement(@PathVariable Long userId, @PathVariable Long achievementId) {
        AchievementUserId id = new AchievementUserId();
        id.setUserID(userId);
        id.setAid(achievementId);
        
        boolean hasAchievement = achievementUserRepo.existsById(id);
        return ResponseEntity.ok(hasAchievement);
    }
    
    // Remove an achievement from a user
    @DeleteMapping("/user/{userId}/achievement/{achievementId}")
    public ResponseEntity<?> revokeAchievement(@PathVariable Long userId, @PathVariable Long achievementId) {
        try {
            AchievementUserId id = new AchievementUserId();
            id.setUserID(userId);
            id.setAid(achievementId);
            
            if (!achievementUserRepo.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            
            achievementUserRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error revoking achievement: " + e.getMessage());
        }
    }
}
