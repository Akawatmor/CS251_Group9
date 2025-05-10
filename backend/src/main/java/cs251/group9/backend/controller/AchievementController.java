/*
 * Achievement Controller
 * 
 * This controller handles HTTP requests related to achievements.
 * It provides endpoints to create, read, update, and delete achievements.
 * It also allows fetching achievements by game ID.
 */

package cs251.group9.backend.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import cs251.group9.backend.entity.*;
//import cs251.group9.backend.service.*;
import cs251.group9.backend.repository.*;

import java.util.*;

@RestController
@RequestMapping("/api/achievements")
public class AchievementController {
    
    @Autowired
    private AchievementRepository achievementRepo;
    
    @Autowired
    private GameRepository gameRepo;
    
    // Get all achievements
    @GetMapping
    public List<Achievement4x> getAllAchievements() {
        return achievementRepo.findAll();
    }
    
    // Get achievement by ID
    @GetMapping("/id={id}")
    public ResponseEntity<Achievement4x> getAchievementById(@PathVariable Long id) {
        Optional<Achievement4x> achievement = achievementRepo.findById(id);
        return achievement.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Get achievements by game ID
    @GetMapping("/game={gameId}")
    public ResponseEntity<List<Achievement4x>> getAchievementsByGame(@PathVariable Long gameId) {
        // Assuming you have a method in your repository for this
        // You might need to add this method to your repository
        List<Achievement4x> achievements = achievementRepo.findByGameGameID(gameId);
        if (achievements.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(achievements);
    }
    
    // Create new achievement
    @PostMapping("/game={gameId}")
    public ResponseEntity<?> createAchievement(@PathVariable Long gameId, @RequestBody Achievement4x achievement) {
        try {
            Optional<Game2x> gameOpt = gameRepo.findBygameID(gameId);
            if (!gameOpt.isPresent()) {
                return ResponseEntity.badRequest().body("Game not found with ID: " + gameId);
            }
            
            Game2x game = gameOpt.get();
            achievement.setGame(game);
            Achievement4x savedAchievement = achievementRepo.save(achievement);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAchievement);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating achievement: " + e.getMessage());
        }
    }
    
    // Update achievement
    @PutMapping("/id={id}")
    public ResponseEntity<?> updateAchievement(@PathVariable Long id, @RequestBody Achievement4x achievementDetails) {
        try {
            Optional<Achievement4x> achievementOpt = achievementRepo.findById(id);
            if (!achievementOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            Achievement4x achievement = achievementOpt.get();
            achievement.setaName(achievementDetails.getaName());
            achievement.setaDesc(achievementDetails.getaDesc());
            
            // Don't update game relationship through this endpoint
            Achievement4x updatedAchievement = achievementRepo.save(achievement);
            
            return ResponseEntity.ok(updatedAchievement);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating achievement: " + e.getMessage());
        }
    }
    
    // Delete achievement
    @DeleteMapping("/id={id}")
    public ResponseEntity<?> deleteAchievement(@PathVariable Long id) {
        try {
            if (!achievementRepo.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            
            achievementRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting achievement: " + e.getMessage());
        }
    }
}
