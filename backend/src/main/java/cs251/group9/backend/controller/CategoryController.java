package cs251.group9.backend.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import cs251.group9.backend.entity.*;
//import cs251.group9.backend.service.*;
import cs251.group9.backend.repository.*;

import java.util.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    
    @Autowired
    private CategoryRepository categoryRepo;
    
    // Generate default categories
    @PostMapping("/generate-defaults")
    public ResponseEntity<?> generateDefaultCategories() {
        Map<String, Object> response = new HashMap<>();
        List<Category5x> createdCategories = new ArrayList<>();
        
        try {
            // Define standard game categories
            String[] categoryNames = {
                "Action", "Adventure", "RPG", "Strategy", "Simulation", 
                "Sports", "Racing", "Puzzle", "Shooter", "Platformer",
                "Horror", "Survival", "Open World", "Fighting", "Music",
                "Educational", "Card Game", "Board Game", "Family", "Casual"
            };
            
            // For each category name, check if it exists, if not create it
            for (int i = 0; i < categoryNames.length; i++) {
                String name = categoryNames[i];
                List<Category5x> existing = categoryRepo.findByCName(name);
                
                if (existing.isEmpty()) {
                    // Create new category with auto-generated ID (starting from 5000000001)
                    Category5x category = new Category5x();
                    category.setCid(5000000001L + i);
                    category.setcName(name);
                    createdCategories.add(categoryRepo.save(category));
                } else {
                    // Include existing category in the response
                    createdCategories.add(existing.get(0));
                }
            }
            
            response.put("success", true);
            response.put("message", createdCategories.size() + " default categories available");
            response.put("categories", createdCategories);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error generating default categories: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // Get all categories
    @GetMapping
    public List<Category5x> getAllCategories() {
        return (List<Category5x>) categoryRepo.findAll();
    }
    
    // Get category by ID
    @GetMapping("/{id}")
    public ResponseEntity<Category5x> getCategoryById(@PathVariable Long id) {
        Optional<Category5x> category = categoryRepo.findById(id);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // Create new category
    @PostMapping
    public Category5x createCategory(@RequestBody Category5x category) {
        return categoryRepo.save(category);
    }
    
    // Search categories by name
    @GetMapping("/search")
    public List<Category5x> searchCategories(@RequestParam String name) {
        return categoryRepo.findByCNameContaining(name);
    }
    
    // Update category
    @PutMapping("/{id}")
    public ResponseEntity<Category5x> updateCategory(@PathVariable Long id, @RequestBody Category5x categoryDetails) {
        Optional<Category5x> categoryData = categoryRepo.findById(id);
        return categoryData.map(existingCategory -> {
            categoryDetails.setCid(id);
            return ResponseEntity.ok(categoryRepo.save(categoryDetails));
        }).orElse(ResponseEntity.notFound().build());
    }
    
    // Delete category
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Long id) {
        Optional<Category5x> categoryData = categoryRepo.findById(id);
        return categoryData.map(category -> {
            categoryRepo.delete(category);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }).orElse(ResponseEntity.notFound().build());
    }
    
    // Get categories for a game
    @GetMapping("/game/{gameId}")
    public List<Category5x> getCategoriesByGameId(@PathVariable Long gameId) {
        return categoryRepo.findCategoriesByGameId(gameId);
    }
}
