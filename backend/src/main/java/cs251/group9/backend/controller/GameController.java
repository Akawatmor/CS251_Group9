/*
 * Game Controller
 */
package cs251.group9.backend.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import cs251.group9.backend.entity.*;
import cs251.group9.backend.repository.*;
import cs251.group9.backend.service.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/games")
public class GameController {
    @Autowired 
    private GameRepository gameRepo;
    
    @Autowired
    private PhotoService photoService;
    
    @Autowired
    private GameFileService gameFileService;
    
    @Autowired
    private CategoryRepository categoryRepo;
    
    @Autowired
    private GameCategoryRepository gameCategoryRepo;
    
    // Add new game
    @PostMapping
    public ResponseEntity<Game2x> addGame(@RequestBody Game2x game) {
        game.setRating(0.0f);
        game.setgPublishDate(LocalDateTime.now());
        return ResponseEntity.ok(gameRepo.save(game));
    }
    
    // Search game by name
    @GetMapping("/search")
    public List<Game2x> search(@RequestParam String name) {
        return gameRepo.findByGNameContainingIgnoreCase(name);
    }
    
    // Get all games
    @GetMapping
    public List<Game2x> getAllGames() {
        return gameRepo.findAll();
    }
    
    // Retrieve game data
    @GetMapping("/{id}")
    public ResponseEntity<Game2x> getGame(@PathVariable Long id) {
        return gameRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Update game info
    @PutMapping("/{id}")
    public ResponseEntity<Game2x> updateGame(@PathVariable Long id, @RequestBody Game2x updatedGame) {
        return gameRepo.findById(id)
                .map(game -> {
                    // Only update allowed fields, preserve others
                    game.setgName(updatedGame.getgName());
                    game.setgDesc(updatedGame.getgDesc());
                    game.setgPrice(updatedGame.getgPrice());
                    
                    // Don't update these automatically
                    // game.setRating(updatedGame.getRating());
                    // game.setgPublishDate(updatedGame.getgPublishDate());
                    
                    // Update file paths if provided
                    if (updatedGame.getMainExecutablePath() != null) {
                        game.setMainExecutablePath(updatedGame.getMainExecutablePath());
                    }
                    if (updatedGame.getDownloadUrl() != null) {
                        game.setDownloadUrl(updatedGame.getDownloadUrl());
                    }
                    if (updatedGame.getInstallationGuide() != null) {
                        game.setInstallationGuide(updatedGame.getInstallationGuide());
                    }
                    
                    return ResponseEntity.ok(gameRepo.save(game));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Delete game
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        return gameRepo.findById(id)
                .map(game -> {
                    // Delete associated files here if needed
                    gameRepo.delete(game);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Upload game picture
    @PostMapping("/{id}/picture/{position}")
    public ResponseEntity<String> uploadPicture(@PathVariable Long id, 
                                               @PathVariable int position,
                                               @RequestParam("file") MultipartFile file) {
        if (position < 1 || position > 5) {
            return ResponseEntity.badRequest().body("Position must be between 1 and 5");
        }
        
        try {
            return gameRepo.findById(id)
                    .map(game -> {
                        try {
                            String picturePath = photoService.storeGamePicture(id, position, file);
                            
                            // Update the corresponding picture field
                            switch (position) {
                                case 1: game.setPicture1(picturePath); break;
                                case 2: game.setPicture2(picturePath); break;
                                case 3: game.setPicture3(picturePath); break;
                                case 4: game.setPicture4(picturePath); break;
                                case 5: game.setPicture5(picturePath); break;
                            }
                            
                            gameRepo.save(game);
                            return ResponseEntity.ok(picturePath);
                        } catch (IOException e) {
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body("Failed to upload picture: " + e.getMessage());
                        }
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload: " + e.getMessage());
        }
    }
    
    // Delete game picture
    @DeleteMapping("/{id}/picture/{position}")
    public ResponseEntity<String> deletePicture(@PathVariable Long id, @PathVariable int position) {
        if (position < 1 || position > 5) {
            return ResponseEntity.badRequest().body("Position must be between 1 and 5");
        }
        
        return gameRepo.findById(id)
                .map(game -> {
                    String picturePath = null;
                    
                    // Get and clear the corresponding picture field
                    switch (position) {
                        case 1: 
                            picturePath = game.getPicture1();
                            game.setPicture1(null); 
                            break;
                        case 2: 
                            picturePath = game.getPicture2();
                            game.setPicture2(null); 
                            break;
                        case 3: 
                            picturePath = game.getPicture3();
                            game.setPicture3(null); 
                            break;
                        case 4: 
                            picturePath = game.getPicture4();
                            game.setPicture4(null); 
                            break;
                        case 5: 
                            picturePath = game.getPicture5();
                            game.setPicture5(null); 
                            break;
                    }
                    
                    if (picturePath != null) {
                        photoService.deleteFile(picturePath);
                    }
                    
                    gameRepo.save(game);
                    return ResponseEntity.ok("Picture deleted successfully");
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Upload game executable file
    @PostMapping("/{id}/executable")
    public ResponseEntity<String> uploadExecutable(@PathVariable Long id, 
                                                 @RequestParam("file") MultipartFile file) {
        try {
            return gameRepo.findById(id)
                    .map(game -> {
                        try {
                            String filePath = gameFileService.storeGameFile(id, file);
                            game.setMainExecutablePath(filePath);
                            gameRepo.save(game);
                            return ResponseEntity.ok(filePath);
                        } catch (IOException e) {
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body("Failed to upload executable: " + e.getMessage());
                        }
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload: " + e.getMessage());
        }
    }
    
    // Add category to game
    @PostMapping("/{gameId}/category/{categoryId}")
    public ResponseEntity<GameCategory> addCategoryToGame(@PathVariable Long gameId, 
                                                         @PathVariable Long categoryId) {
        Game2x game = gameRepo.findById(gameId).orElse(null);
        Category5x category = categoryRepo.findCategoriesByGameId(categoryId).stream().findFirst().orElse(null);
        
        if (game == null || category == null) {
            return ResponseEntity.notFound().build();
        }
        
        GameCategoryId id = new GameCategoryId(gameId, categoryId);
        GameCategory gameCategory = new GameCategory();
        gameCategory.setId(id);
        gameCategory.setGame(game);
        gameCategory.setCategory(category);
        
        return ResponseEntity.ok(gameCategoryRepo.save(gameCategory));
    }
    
    // Remove category from game
    @DeleteMapping("/{gameId}/category/{categoryId}")
    public ResponseEntity<Void> removeCategoryFromGame(@PathVariable Long gameId, 
                                                     @PathVariable Long categoryId) {
        GameCategoryId id = new GameCategoryId(gameId, categoryId);
        
        if (!gameCategoryRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        gameCategoryRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    // Get games by category
    @GetMapping("/category/{categoryId}")
    public List<Game2x> getGamesByCategory(@PathVariable Long categoryId) {
        return gameRepo.findByCategoryId(categoryId);
    }
    
    // Get games by price range
    @GetMapping("/price/{maxPrice}")
    public List<Game2x> getGamesByPrice(@PathVariable Integer maxPrice) {
        return gameRepo.findByPriceLessThanEqual(maxPrice);
    }
    
    // Get games by rating
    @GetMapping("/rating/{minRating}")
    public List<Game2x> getGamesByRating(@PathVariable Float minRating) {
        return gameRepo.findByRatingGreaterThanEqual(minRating);
    }
    
    @GetMapping("/{id}/categories")
    public ResponseEntity<List<Category5x>> getGameCategories(@PathVariable Long id) {
        List<Category5x> categories = categoryRepo.findCategoriesByGameId(id);
        if (categories == null || categories.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (categories.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categories);
    }

    @DeleteMapping("/{id}/categories/{categoryId}")
    public ResponseEntity<HttpStatus> removeGameCategory(@PathVariable Long id, @PathVariable Long categoryId) {
        GameCategoryId gameCategoryId = new GameCategoryId();
        if (gameCategoryRepo.existsById(gameCategoryId)) {
            gameCategoryRepo.deleteById(gameCategoryId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
