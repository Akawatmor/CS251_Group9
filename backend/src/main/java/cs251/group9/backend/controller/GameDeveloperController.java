/*
 * Game Developer Controller
 */
package cs251.group9.backend.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import cs251.group9.backend.entity.*;
import cs251.group9.backend.repository.*;

import java.util.*;

@RestController
@RequestMapping("/api/game-developers")
public class GameDeveloperController {
    
    @Autowired
    private GameDeveloperRepository gameDeveloperRepo;
    
    @Autowired
    private GameRepository gameRepo;
    
    @Autowired
    private DeveloperRepository developerRepo;
    
    // Get all game developers
    @GetMapping
    public List<GameDeveloper> getAllGameDevelopers() {
        return gameDeveloperRepo.findAll();
    }
    
    // Get developers for a specific game
    @GetMapping("/game/{gameId}")
    public ResponseEntity<?> getDevelopersByGame(@PathVariable Long gameId) {
        // Find game developers by game ID
        List<GameDeveloper> gameDevelopers = gameDeveloperRepo.findAll().stream()
                .filter(gd -> gd.getGame().getGameID().equals(gameId))
                .toList();
        
        if (gameDevelopers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(gameDevelopers);
    }
    
    // Get games for a specific developer
    @GetMapping("/developer/{devId}")
    public ResponseEntity<?> getGamesByDeveloper(@PathVariable Long devId) {
        // Find game developers by developer ID
        List<GameDeveloper> gameDevelopers = gameDeveloperRepo.findAll().stream()
                .filter(gd -> gd.getDeveloper().getDevID().equals(devId))
                .toList();
        
        if (gameDevelopers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(gameDevelopers);
    }
    
    // Create a new game developer relationship
    @PostMapping
    public ResponseEntity<?> createGameDeveloper(@RequestBody Map<String, Long> payload) {
        Long gameId = payload.get("gameId");
        Long devId = payload.get("devId");
        
        // Check if game exists
        Game2x game = gameRepo.findById(gameId)
                .orElse(null);
        if (game == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Game with ID " + gameId + " not found");
        }
        
        // Check if developer exists
        Developer3x developer = developerRepo.findById(devId)
                .orElse(null);
        if (developer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Developer with ID " + devId + " not found");
        }
        
        // Create game developer ID
        GameDeveloperId id = new GameDeveloperId();
        id.setGameID(gameId);
        id.setDevID(devId);
        
        // Check if relationship already exists
        if (gameDeveloperRepo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Game developer relationship already exists");
        }
        
        // Create new game developer relationship
        GameDeveloper gameDeveloper = new GameDeveloper();
        gameDeveloper.setId(id);
        gameDeveloper.setGame(game);
        gameDeveloper.setDeveloper(developer);
        
        GameDeveloper savedGameDeveloper = gameDeveloperRepo.save(gameDeveloper);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGameDeveloper);
    }
    
    // Delete a game developer relationship
    @DeleteMapping
    public ResponseEntity<?> deleteGameDeveloper(@RequestBody Map<String, Long> payload) {
        Long gameId = payload.get("gameId");
        Long devId = payload.get("devId");
        
        GameDeveloperId id = new GameDeveloperId();
        id.setGameID(gameId);
        id.setDevID(devId);
        
        if (!gameDeveloperRepo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Game developer relationship not found");
        }
        
        gameDeveloperRepo.deleteById(id);
        return ResponseEntity.ok().body("Game developer relationship deleted successfully");
    }
    
    // Delete all developers for a specific game
    @DeleteMapping("/game/{gameId}")
    public ResponseEntity<?> deleteDevelopersByGame(@PathVariable Long gameId) {
        gameDeveloperRepo.deleteByGameId(gameId);
        return ResponseEntity.ok().body("All developers for game ID " + gameId + " deleted successfully");
    }
    
    // Add batch assignment of developers to games
    @PostMapping("/insert-batch")
    public ResponseEntity<?> insertBatchGameDevelopers(@RequestBody List<Map<String, Long>> relationships) {
        Map<String, Object> response = new HashMap<>();
        List<GameDeveloper> createdRelationships = new ArrayList<>();
        int successCount = 0;
        
        try {
            for (Map<String, Long> relation : relationships) {
                Long gameId = relation.get("gameId");
                Long devId = relation.get("devId");
                
                // Skip if either ID is missing
                if (gameId == null || devId == null) {
                    continue;
                }
                
                // Check if both game and developer exist
                Optional<Game2x> game = gameRepo.findById(gameId);
                Optional<Developer3x> developer = developerRepo.findById(devId);
                
                if (game.isPresent() && developer.isPresent()) {
                    // Create ID
                    GameDeveloperId id = new GameDeveloperId();
                    id.setGameID(gameId);
                    id.setDevID(devId);
                    
                    // Skip if relationship already exists
                    if (gameDeveloperRepo.existsById(id)) {
                        continue;
                    }
                    
                    // Create and save relationship
                    GameDeveloper gameDev = new GameDeveloper();
                    gameDev.setId(id);
                    gameDev.setGame(game.get());
                    gameDev.setDeveloper(developer.get());
                    
                    GameDeveloper saved = gameDeveloperRepo.save(gameDev);
                    createdRelationships.add(saved);
                    successCount++;
                }
            }
            
            response.put("success", true);
            response.put("message", successCount + " game developer relationships created successfully");
            response.put("createdRelationships", createdRelationships);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error creating game developer relationships: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
