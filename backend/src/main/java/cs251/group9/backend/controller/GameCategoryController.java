package cs251.group9.backend.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import cs251.group9.backend.entity.*;
import cs251.group9.backend.repository.*;

import java.util.*;

@RestController
@RequestMapping("/api/game-categories")
public class GameCategoryController {
    
    @Autowired
    private GameCategoryRepository gameCategoryRepo;
    
    @Autowired
    private GameRepository gameRepo;
    
    @Autowired
    private CategoryRepository categoryRepo;
      // Insert 20 default game categories
    @PostMapping("/insert-defaults")
    public ResponseEntity<?> insertDefaultGameCategories() {
        Map<String, Object> response = new HashMap<>();
        List<GameCategory> createdCategories = new ArrayList<>();
        
        try {
            // Get all available categories from the database
            List<Category5x> availableCategories = (List<Category5x>) categoryRepo.findAll();
            
            // Check if we have categories
            if (availableCategories.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No categories found in the database. Please use /api/categories/generate-defaults first.");
            }
            
            // Get available games
            List<Game2x> games = gameRepo.findAll();
            if (games.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No games found in the database. Please add games first.");
            }
            
            Random random = new Random();
            int successCount = 0;
            
            // For each game, assign 1-3 random categories
            for (Game2x game : games) {
                // Limit to 20 assignments total
                if (successCount >= 20) break;
                
                // Randomly determine how many categories to assign (1 to 3)
                int categoriesToAssign = random.nextInt(3) + 1;
                  // Get random categories for this game
                List<Category5x> shuffledCategories = new ArrayList<>(availableCategories);
                Collections.shuffle(shuffledCategories);
                List<Category5x> selectedCategories = shuffledCategories.subList(0, 
                        Math.min(categoriesToAssign, shuffledCategories.size()));
                
                for (Category5x category : selectedCategories) {
                    // Create a game-category relationship
                    GameCategoryId id = new GameCategoryId(game.getGameID(), category.getCid());
                    
                    // Check if relationship already exists
                    if (!gameCategoryRepo.existsById(id)) {
                        GameCategory gameCategory = new GameCategory();
                        gameCategory.setId(id);
                        gameCategory.setGame(game);
                        gameCategory.setCategory(category);
                        
                        GameCategory savedCategory = gameCategoryRepo.save(gameCategory);
                        createdCategories.add(savedCategory);
                        successCount++;
                        
                        // Limit to 20 assignments total
                        if (successCount >= 20) break;
                    }
                }
            }
            
            response.put("success", true);
            response.put("message", successCount + " default game categories created successfully");
            response.put("createdCategories", createdCategories);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error creating default game categories: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
      // The createDefaultCategories method has been moved to CategoryController
    // Use '/api/categories/generate-defaults' endpoint instead
    
    // Get all game categories
    @GetMapping
    public List<GameCategory> getAllGameCategories() {
        return gameCategoryRepo.findAll();
    }
    
    // Get categories for a specific game
    @GetMapping("/game/{gameId}")
    public ResponseEntity<?> getCategoriesByGame(@PathVariable Long gameId) {
        // Find game categories by game ID
        List<GameCategory> gameCategories = gameCategoryRepo.findAll().stream()
                .filter(gc -> gc.getGame().getGameID().equals(gameId))
                .toList();
        
        if (gameCategories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(gameCategories);
    }
    
    // Get games for a specific category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getGamesByCategory(@PathVariable Long categoryId) {
        // Find game categories by category ID
        List<GameCategory> gameCategories = gameCategoryRepo.findAll().stream()
                .filter(gc -> gc.getCategory().getCid().equals(categoryId))
                .toList();
        
        if (gameCategories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(gameCategories);
    }
    
    // Create a new game category relationship
    @PostMapping
    public ResponseEntity<?> createGameCategory(@RequestBody Map<String, Long> payload) {
        Long gameId = payload.get("gameId");
        Long categoryId = payload.get("categoryId");
        
        // Check if game exists
        Game2x game = gameRepo.findById(gameId)
                .orElse(null);
        if (game == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Game with ID " + gameId + " not found");
        }
        
        // Check if category exists
        Category5x category = categoryRepo.findById(categoryId)
                .orElse(null);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Category with ID " + categoryId + " not found");
        }
        
        // Create game category ID
        GameCategoryId id = new GameCategoryId();
        id.setGameID(gameId);
        id.setCid(categoryId);
        
        // Check if relationship already exists
        if (gameCategoryRepo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Game category relationship already exists");
        }
        
        // Create new game category relationship
        GameCategory gameCategory = new GameCategory();
        gameCategory.setId(id);
        gameCategory.setGame(game);
        gameCategory.setCategory(category);
        
        GameCategory savedGameCategory = gameCategoryRepo.save(gameCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGameCategory);
    }
    
    // Delete a game category relationship
    @DeleteMapping
    public ResponseEntity<?> deleteGameCategory(@RequestBody Map<String, Long> payload) {
        Long gameId = payload.get("gameId");
        Long categoryId = payload.get("categoryId");
        
        GameCategoryId id = new GameCategoryId();
        id.setGameID(gameId);
        id.setCid(categoryId);
        
        if (!gameCategoryRepo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Game category relationship not found");
        }
        
        gameCategoryRepo.deleteById(id);
        return ResponseEntity.ok().body("Game category relationship deleted successfully");
    }
    
    // Delete all categories for a specific game
    @DeleteMapping("/game/{gameId}")
    public ResponseEntity<?> deleteCategoriesByGame(@PathVariable Long gameId) {
        gameCategoryRepo.deleteByGameId(gameId);
        return ResponseEntity.ok().body("All categories for game ID " + gameId + " deleted successfully");
    }
}
