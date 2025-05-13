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
    
    @Autowired
    private GameService gameService;
    
    ///////////////////// Add new Game ////////////////////////
    @PostMapping("/")
    public ResponseEntity<Game2x> addGame(@RequestBody Game2x game) {
        game.setRating(0.0f);
        game.setgPublishDate(LocalDateTime.now());
        return ResponseEntity.ok(gameRepo.save(game));
    }
    
    ///////////////////// Search Game All ////////////////////////
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllDevelopers() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Game2x> game = gameRepo.findAll();
            response.put("success", true);
            response.put("game", game);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }


    ///////////////////// Search Game by Name ////////////////////////
    @GetMapping("/search/name={name}")
    public List<Game2x> search(@PathVariable String name) {
        return gameRepo.findByGNameContainingIgnoreCase(name);
    }
    
    ///////////////////// Search Game by GameId (gid) ////////////////////////
    @GetMapping("/id={id}")
    public ResponseEntity<Game2x> getGame(@PathVariable Long id) {
        return gameRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    ///////////////////// Search Game All by Category ////////////////////////
    @GetMapping("/search/category={cid}")
    public List<Game2x> getGamesByCategory(@PathVariable Long categoryId) {
        return gameRepo.findByCategoryId(categoryId);
    }

    ///////////////////// Search All Game by Best Rating ////////////////////////
    @GetMapping("/rating")
    public ResponseEntity<?> getGamesByRating() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Game2x> game = gameRepo.findAllByOrderByRatingDesc();
            response.put("success", true);
            response.put("game", game);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    ///////////////////// Update Game Data ////////////////////////
    @PutMapping("/id={id}")
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
                    return ResponseEntity.ok(gameRepo.save(game));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    ////////////////// Delete Game ////////////////////////
    @DeleteMapping("/id={id}")
    public ResponseEntity<?> deleteGame(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean deleted = gameService.deleteGameWithDependencies(id);
            
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                response.put("success", false);
                response.put("message", "Game not found with ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error deleting game: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /////////////////// Upload Game Picture ////////////////////////
    
    /** Add or Update Game Picture
     * 
     * This endpoint allows the user to upload a picture for a game.
     * The picture will be stored in a specific location on the server.
     * 
     * @param id The ID of the game.
     * @param position The position of the picture (1-6).
     * @param file The picture file to upload.
     * @return A response entity containing the result of the operation.
     * 
     * Rewritten by SKO
     */
    @PostMapping("/id={id}/picture={position}")
    public ResponseEntity<?> uploadPicture(@PathVariable Long id, 
                                               @PathVariable int position,
                                               @RequestParam("file") MultipartFile file) {

        Map<String, Object> response = new HashMap<>();

        // Validate position
        if (position < 1 || position > 6) {
            response.put("success", false);
            response.put("error", "picturenumber");
            response.put("message", "Failed to upload game picture. Position must be between 1 and 8 ");
            return ResponseEntity.badRequest().body(response);
        }
 
        // Validate file type only JPEG PNG
        String contentType = file.getContentType();
        if (contentType == null || (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
            response.put("success", false);
            response.put("error", "filetype");
            response.put("message", "Failed to upload game picture. Only JPEG and PNG files are allowed");
            return ResponseEntity.badRequest().body(response);
        }
        
        //try to upload the picture
        try {
            String picturePath = photoService.storeGamePicture(id, position, file);
            Game2x game = gameRepo.findById(id).orElse(null);

            // Check if the game exists
            if (game == null) {
                response.put("success", false);
                response.put("error", "gameid");
                response.put("message", "Failed to upload game picture. Game not found");
                return ResponseEntity.badRequest().body(response);
            }

            // Update the corresponding picture field
            switch (position){
                case 1: game.setPicture1(picturePath); break;
                case 2: game.setPicture2(picturePath); break;
                case 3: game.setPicture3(picturePath); break;
                case 4: game.setPicture4(picturePath); break;
                case 5: game.setPicture5(picturePath); break;
                //case 6: gameRepo.findById(id).get().setPicture6(picturePath); break;
                //case 7: gameRepo.findById(id).get().setPicture7(picturePath); break;
                //case 8: gameRepo.findById(id).get().setPicture8(picturePath); break;
            }

            // Update the game in the database
            gameRepo.save(game);

            // Return success response
            response.put("success", true);
            response.put("message", "Game picture uploaded successfully");
            response.put("path", picturePath);
            return ResponseEntity.badRequest().body(response);

        }
        catch (IOException e) {
            response.put("success", false);
            response.put("error", "upload");
            response.put("message", "Failed to upload game picture: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }


    }
    
/////////////////// Delete Game Picture ////////////////////////

    /** Delete Game Picture
     * 
     * This endpoint allows the user to delete a picture for a game.
     * The picture will be removed from the server.
     * 
     * @param id The ID of the game.
     * @param position The position of the picture (1-6).
     * @return A response entity containing the result of the operation.
     * 
     * Rewritten by SKO
     */
    @DeleteMapping("/id={id}/picture={position}")
    public ResponseEntity<?> deletePicture(@PathVariable Long id, @PathVariable int position) {

        Map<String, Object> response = new HashMap<>();

        // Validate position
        if (position < 1 || position > 6) {
            response.put("success", false);
            response.put("error", "picturenumber");
            response.put("message", "Failed to upload game picture. Position must be between 1 and 8 ");
            return ResponseEntity.badRequest().body(response);
        }

        try{
            // Get the game by ID
            Game2x game = gameRepo.findById(id).orElse(null);

            // Check if the game exists
            if (game == null) {
                response.put("success", false);
                response.put("error", "gameid");
                response.put("message", "Failed to delete game picture. Game not found");
                return ResponseEntity.badRequest().body(response);
            }

            // Get the corresponding picture field
            String picturePath = null;
            switch (position) {
                case 1: picturePath = game.getPicture1(); break;
                case 2: picturePath = game.getPicture2(); break;
                case 3: picturePath = game.getPicture3(); break;
                case 4: picturePath = game.getPicture4(); break;
                case 5: picturePath = game.getPicture5(); break;
            }

            if (picturePath == null) {
                response.put("success", false);
                response.put("error", "nofile");
                response.put("message", "Failed to delete game picture. No Picture");
                return ResponseEntity.badRequest().body(response);
            }

            // Delete the file from the server
            photoService.deleteFile(picturePath);

            // Update the corresponding picture field to null
            switch (position) {
                case 1: game.setPicture1(null); break;
                case 2: game.setPicture2(null); break;
                case 3: game.setPicture3(null); break;
                case 4: game.setPicture4(null); break;
                case 5: game.setPicture5(null); break;
            }

            // Update the game in the database
            gameRepo.save(game);

            // Return success response
            response.put("success", true);
            response.put("message", "Game picture deleted successfully");
            return ResponseEntity.ok(response);


        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "delete");
            response.put("message", "Failed to delete game picture: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
//////////////////// Upload Game Executable ////////////////////////
    @PostMapping("/id={id}/file")
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
        List<Category5x> categories = categoryRepo.findCategoriesByGameId(categoryId); // Define categories properly
        
        if (game == null || categories.isEmpty()) { // Check if the list is empty
            return ResponseEntity.notFound().build();
        }
        
        Category5x category = categories.get(0); // Use the first category from the list
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
    

/////////////////////////// Get Games by Max Price ////////////////////////////
    @GetMapping("/price/{maxPrice}")
    public List<Game2x> getGamesByPrice(@PathVariable Integer maxPrice) {
        return gameRepo.findByPriceLessThanEqual(maxPrice);
    }
    
/////////////////////////   Get Games by Rating ////////////////////////////
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
    
    ///////////////// Get Game Picture /////////////////////
    @GetMapping("/id={id}/picture={position}")
    public ResponseEntity<?> getGamePicture(@PathVariable Long id, @PathVariable int position) {
        if (position < 1 || position > 5) {
            return ResponseEntity.badRequest().build();
        }
        
        try {
            byte[] imageData = photoService.getGamePictureData(id, position);
            return ResponseEntity.ok()
                    .contentType(org.springframework.http.MediaType.IMAGE_JPEG)
                    .body(imageData);
        } catch (Exception e) {
            // Return empty JSON response with success:false when image not found
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "picture empty");
            return ResponseEntity.ok(response);
        }
    }
    
    ///////////////// Game Banner Endpoints /////////////////////
    
    // Get banner image
    @GetMapping("/banner/{position}")
    public ResponseEntity<?> getBanner(@PathVariable int position) {
        if (position < 1 || position > 25) {
            return ResponseEntity.badRequest().build();
        }
        
        try {
            byte[] imageData = photoService.getBannerPictureData(position);
            return ResponseEntity.ok()
                    .contentType(org.springframework.http.MediaType.IMAGE_JPEG) // Will work for jpg, png, webp
                    .body(imageData);
        } catch (Exception e) {
            // Return empty JSON response with success:false when image not found
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "picture empty");
            return ResponseEntity.ok(response);
        }
    }
    
    // Upload or update banner image
    @PostMapping("/banner/{position}")
    public ResponseEntity<Map<String, Object>> uploadBanner(
            @PathVariable int position,
            @RequestParam("file") MultipartFile file) {
        
        Map<String, Object> response = new HashMap<>();
        
        if (position < 1 || position > 25) {
            response.put("success", false);
            response.put("message", "Position must be between 1 and 25");
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            String picturePath = photoService.storeBannerPicture(position, file);
            
            response.put("success", true);
            response.put("message", "Banner uploaded successfully");
            response.put("path", picturePath);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to upload banner: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // Delete banner image
    @DeleteMapping("/banner/{position}")
    public ResponseEntity<Map<String, Object>> deleteBanner(@PathVariable int position) {
        Map<String, Object> response = new HashMap<>();
        
        if (position < 1 || position > 25) {
            response.put("success", false);
            response.put("message", "Position must be between 1 and 25");
            return ResponseEntity.badRequest().body(response);
        }
        
        boolean deleted = photoService.deleteBannerPicture(position);
        
        if (deleted) {
            response.put("success", true);
            response.put("message", "Banner deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Banner not found or could not be deleted");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
