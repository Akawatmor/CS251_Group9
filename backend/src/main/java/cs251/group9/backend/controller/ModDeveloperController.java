package cs251.group9.backend.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import cs251.group9.backend.entity.*;
import cs251.group9.backend.repository.*;

import java.util.*;

@RestController
@RequestMapping("/api/mod-developers")
public class ModDeveloperController {
    
    @Autowired
    private ModDeveloperRepository modDeveloperRepo;
    
    @Autowired
    private ModDLCRepository modRepo;
    
    @Autowired
    private DeveloperRepository developerRepo;
    
    // Get all mod developers
    @GetMapping
    public List<ModDeveloper> getAllModDevelopers() {
        return modDeveloperRepo.findAll();
    }
    
    // Get developers for a specific mod
    @GetMapping("/mod/{modId}")
    public ResponseEntity<?> getDevelopersByMod(@PathVariable Long modId) {
        // Find mod developers by mod ID
        List<ModDeveloper> modDevelopers = modDeveloperRepo.findAll().stream()
                .filter(md -> md.getMod().getModID().equals(modId))
                .toList();
        
        if (modDevelopers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(modDevelopers);
    }
    
    // Get mods for a specific developer
    @GetMapping("/developer/{devId}")
    public ResponseEntity<?> getModsByDeveloper(@PathVariable Long devId) {
        // Find mod developers by developer ID
        List<ModDeveloper> modDevelopers = modDeveloperRepo.findAll().stream()
                .filter(md -> md.getDeveloper().getDevID().equals(devId))
                .toList();
        
        if (modDevelopers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(modDevelopers);
    }
    
    // Create a new mod developer relationship
    @PostMapping
    public ResponseEntity<?> createModDeveloper(@RequestBody Map<String, Long> payload) {
        Long modId = payload.get("modId");
        Long devId = payload.get("devId");
        
        // Check if mod exists
        ModDLC7x mod = modRepo.findById(modId)
                .orElse(null);
        if (mod == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Mod with ID " + modId + " not found");
        }
        
        // Check if developer exists
        Developer3x developer = developerRepo.findById(devId)
                .orElse(null);
        if (developer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Developer with ID " + devId + " not found");
        }
        
        // Create mod developer ID
        ModDeveloperId id = new ModDeveloperId();
        id.setModID(modId);
        id.setDevID(devId);
        
        // Check if relationship already exists
        if (modDeveloperRepo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Mod developer relationship already exists");
        }
        
        // Create new mod developer relationship
        ModDeveloper modDeveloper = new ModDeveloper();
        modDeveloper.setId(id);
        modDeveloper.setMod(mod);
        modDeveloper.setDeveloper(developer);
        
        ModDeveloper savedModDeveloper = modDeveloperRepo.save(modDeveloper);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedModDeveloper);
    }
    
    // Delete a mod developer relationship
    @DeleteMapping
    public ResponseEntity<?> deleteModDeveloper(@RequestBody Map<String, Long> payload) {
        Long modId = payload.get("modId");
        Long devId = payload.get("devId");
        
        ModDeveloperId id = new ModDeveloperId();
        id.setModID(modId);
        id.setDevID(devId);
        
        if (!modDeveloperRepo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Mod developer relationship not found");
        }
        
        modDeveloperRepo.deleteById(id);
        return ResponseEntity.ok().body("Mod developer relationship deleted successfully");
    }
}
