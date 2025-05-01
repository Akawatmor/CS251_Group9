/*
 * ModDLC Controller
 */
package cs251.group9.backend.controller;

import cs251.group9.backend.entity.*;
import cs251.group9.backend.repository.*;
import cs251.group9.backend.service.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/mods")
public class ModDLCController {
    @Autowired
    private ModDLCRepository modRepo;
    
    @Autowired
    private GameRepository gameRepo;
    
    @Autowired
    private GameFileService fileService;
    
    // Get all mods
    @GetMapping
    public List<ModDLC7x> getAllMods() {
        return modRepo.findAll();
    }
    
    // Get mod by ID
    @GetMapping("/{id}")
    public ResponseEntity<ModDLC7x> getModById(@PathVariable Long id) {
        return modRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Find mods by game
    @GetMapping("/game/{gameId}")
    public List<ModDLC7x> getModsByGame(@PathVariable Long gameId) {
        return modRepo.findByGame(gameId);
    }
    
    // Add new mod
    @PostMapping
    public ResponseEntity<ModDLC7x> createMod(@RequestBody ModDLC7x mod) {
        return ResponseEntity.ok(modRepo.save(mod));
    }
    
    // Update mod
    @PutMapping("/{id}")
    public ResponseEntity<ModDLC7x> updateMod(@PathVariable Long id, @RequestBody ModDLC7x updatedMod) {
        return modRepo.findById(id)
                .map(mod -> {
                    updatedMod.setModID(id);
                    return ResponseEntity.ok(modRepo.save(updatedMod));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Delete mod
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMod(@PathVariable Long id) {
        return modRepo.findById(id)
                .map(mod -> {
                    // Also delete the file if it exists
                    if (mod.getDownloadPath() != null) {
                        fileService.deleteFile(mod.getDownloadPath());
                    }
                    modRepo.delete(mod);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Upload mod file
    @PostMapping("/{id}/file")
    public ResponseEntity<String> uploadModFile(@PathVariable Long id, 
                                             @RequestParam("file") MultipartFile file) {
        try {
            return modRepo.findById(id)
                    .map(mod -> {
                        try {
                            Game2x game = mod.getGame();
                            String filePath = fileService.storeGameFile(game.getGameID(), file);
                            mod.setDownloadPath(filePath);
                            modRepo.save(mod);
                            return ResponseEntity.ok(filePath);
                        } catch (Exception e) {
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body("Failed to upload file: " + e.getMessage());
                        }
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload: " + e.getMessage());
        }
    }
    
    // Search mods by name
    @GetMapping("/search")
    public List<ModDLC7x> searchModsByName(@RequestParam String name) {
        return modRepo.findByModNameContaining(name);
    }
}