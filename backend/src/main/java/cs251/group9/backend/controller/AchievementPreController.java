package cs251.group9.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cs251.group9.backend.entity.Achievement4x;
import cs251.group9.backend.entity.AchievementPre;
import cs251.group9.backend.entity.AchievementPreId;
import cs251.group9.backend.repository.AchievementPreRepository;
import cs251.group9.backend.repository.AchievementRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/achievement-prerequisites")
public class AchievementPreController {
    
    @Autowired
    private AchievementPreRepository achievementPreRepo;
    
    @Autowired
    private AchievementRepository achievementRepo;
    
    // Get all achievement prerequisites
    @GetMapping
    public List<AchievementPre> getAllPrerequisites() {
        return achievementPreRepo.findAll();
    }
    
    // Get prerequisites for a specific achievement
    @GetMapping("/achievement/{achievementId}")
    public ResponseEntity<List<AchievementPre>> getPrerequisitesForAchievement(@PathVariable Long achievementId) {
        // Assuming you have a method to find by achievement ID
        // You might need to add this method to your repository
        List<AchievementPre> prerequisites = achievementPreRepo.findByIdAid(achievementId);
        
        if (prerequisites.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(prerequisites);
    }
    
    // Add a prerequisite to an achievement
    @PostMapping("/id={achievementId}/pre={prerequisiteId}")
    public ResponseEntity<?> addPrerequisite(@PathVariable Long achievementId, @PathVariable Long prerequisiteId) {
        try {
            // Check if both achievements exist
            Optional<Achievement4x> achievementOpt = achievementRepo.findById(achievementId);
            Optional<Achievement4x> prerequisiteOpt = achievementRepo.findById(prerequisiteId);
            
            if (!achievementOpt.isPresent()) {
                return ResponseEntity.badRequest().body("Achievement not found with ID: " + achievementId);
            }
            
            if (!prerequisiteOpt.isPresent()) {
                return ResponseEntity.badRequest().body("Prerequisite achievement not found with ID: " + prerequisiteId);
            }
            
            // Create the prerequisite ID to check if it already exists
            AchievementPreId id = new AchievementPreId();
            id.setAid(achievementId);
            id.setPrerequisiteAID(prerequisiteId);
            
            // Check if the prerequisite relationship already exists
            if (achievementPreRepo.existsById(id)) {
                return ResponseEntity.badRequest().body("This prerequisite relationship already exists");
            }
            
            // Create the prerequisite relationship
            AchievementPre achievementPre = new AchievementPre();
            
            // Use the correct method names based on your AchievementPre class
            // If these setters don't exist, you need to add them to the AchievementPre class
            achievementPre.setId(id);
            achievementPre.setAchievement(achievementOpt.get());
            achievementPre.setPrerequisite(prerequisiteOpt.get());
            
            AchievementPre savedPrerequisite = achievementPreRepo.save(achievementPre);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPrerequisite);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding prerequisite: " + e.getMessage());
        }
    }
    
    // Delete a prerequisite from an achievement
    @DeleteMapping("/id={achievementId}/pre={prerequisiteId}")
    public ResponseEntity<?> removePrerequisite(@PathVariable Long achievementId, @PathVariable Long prerequisiteId) {
        try {
            AchievementPreId id = new AchievementPreId();
            id.setAid(achievementId);
            id.setPrerequisiteAID(prerequisiteId);
            
            if (!achievementPreRepo.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            
            achievementPreRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error removing prerequisite: " + e.getMessage());
        }
    }
    
    // Check if all prerequisites are met for an achievement by a user
    @GetMapping("/check/user/{userId}/achievement/{achievementId}")
    public ResponseEntity<?> checkPrerequisitesMet(@PathVariable Long userId, @PathVariable Long achievementId) {
        // This would need additional functionality from your repositories
        // to check if the user has earned all prerequisite achievements
        // This is a placeholder for the implementation
        return ResponseEntity.ok("Prerequisites check not implemented yet");
    }
}
