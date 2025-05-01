package cs251.group9.backend.controller;

import cs251.group9.backend.entity.Category5x;
import cs251.group9.backend.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    
    @Autowired
    private CategoryRepository categoryRepo;
    
    // Get all categories
    @GetMapping
    public List<Category5x> getAllCategories() {
        return categoryRepo.findAll();
    }
    
    // Get category by ID
    @GetMapping("/{id}")
    public ResponseEntity<Category5x> getCategoryById(@PathVariable String id) {
        return categoryRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
    public ResponseEntity<Category5x> updateCategory(@PathVariable String id, @RequestBody Category5x category) {
        return categoryRepo.findById(id)
                .map(existingCategory -> {
                    category.setCid(id);
                    return ResponseEntity.ok(categoryRepo.save(category));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Delete category
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        return categoryRepo.findById(id)
                .map(category -> {
                    categoryRepo.delete(category);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Get categories for a game
    @GetMapping("/game/{gameId}")
    public List<Category5x> getCategoriesByGameId(@PathVariable Integer gameId) {
        return categoryRepo.findCategoriesByGameId(gameId);
    }
}
