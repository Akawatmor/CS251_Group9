/*
 * Developer Controller
 */
package cs251.group9.backend.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import cs251.group9.backend.entity.*;
import cs251.group9.backend.service.*;
import cs251.group9.backend.repository.*;

import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/api/developers")
public class DeveloperController {

    @Autowired 
    private DeveloperRepository developerRepository;
    
    @Autowired 
    private DeveloperService developerService;

    @Autowired 
    private PhotoService photoService;
    
    // Get all developers
    @GetMapping
    public ResponseEntity<List<Developer3x>> getAllDevelopers() {
        List<Developer3x> developers = developerRepository.findAll();
        return ResponseEntity.ok(developers);
    }
    
/////////////////////// Get developer by ID //////////////////////
    @GetMapping("/id={id}")
    public ResponseEntity<Map<String, Object>> getDeveloperById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Developer3x developer = developerService.getDeveloperById(id);
            response.put("success", true);
            response.put("developer", developer);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    
////////////////////// Register developer //////////////////////
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerDeveloper(@RequestBody Developer3x developer) {
        Map<String, Object> response = new HashMap<>();
        
        if (developerService.isDeveloperNameTaken(developer.getDevName())) {
            response.put("success", false);
            response.put("message", "Developer name is already taken");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        
        try {
            Developer3x savedDeveloper = developerService.registerDeveloper(developer);
            response.put("success", true);
            response.put("message", "Developer registered successfully");
            response.put("developer", savedDeveloper);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    // Update developer
    @PutMapping("/id={id}")
    public ResponseEntity<Map<String, Object>> updateDeveloper(@PathVariable Long id, @RequestBody Developer3x updatedDeveloper) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Developer3x result = developerService.updateDeveloper(id, updatedDeveloper);
            response.put("success", true);
            response.put("message", "Developer updated successfully");
            response.put("developer", result);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    // Update team name
    @PutMapping("/id={id}/team")
    public ResponseEntity<Map<String, Object>> updateTeamName(@PathVariable Long id, @RequestParam String teamName) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Developer3x developer = developerService.getDeveloperById(id);
            developer.setTeamName(teamName);
            Developer3x result = developerRepository.save(developer);
            
            response.put("success", true);
            response.put("message", "Team name updated successfully");
            response.put("developer", result);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
///////////////////// Delete developer //////////////////////
    @DeleteMapping("/id={id}")
    public ResponseEntity<Map<String, Object>> deleteDeveloper(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            developerService.deleteDeveloper(id);
            response.put("success", true);
            response.put("message", "Developer deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    
///////////////////// Upload Dev picture //////////////////////
    @PostMapping("/id={id}/picture")
    public ResponseEntity<Map<String, Object>> uploadTeamPicture(@PathVariable Long id, 
                                                  @RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Developer3x developer = developerService.getDeveloperById(id);
            
            // Use photo service to store the picture
            String filePath = photoService.storeDeveloperTeamPhoto(id, file);
            developer.setTeamPicture(filePath);
            developerRepository.save(developer);
            
            response.put("success", true);
            response.put("message", "Team picture uploaded successfully");
            response.put("picturePath", filePath);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            response.put("success", false);
            response.put("message", "Failed to upload picture: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    
/////////////////// Get Dev picture  ///////////////////
    @GetMapping("/id={id}/picture")
    public ResponseEntity<?> getTeamPicture(@PathVariable Long id) {
        try {
            Developer3x developer = developerService.getDeveloperById(id);
            
            if (developer.getTeamPicture() == null || developer.getTeamPicture().isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "No team picture found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            byte[] photoData = photoService.getDeveloperTeamPhotoData(id);
            return ResponseEntity
                .ok()
                .contentType(org.springframework.http.MediaType.IMAGE_JPEG)
                .body(photoData);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
/////////////////// Delete Dev picture //////////////////////
    @DeleteMapping("/id={id}/picture")
    public ResponseEntity<Map<String, Object>> deleteTeamPicture(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Developer3x developer = developerService.getDeveloperById(id);
            
            if (developer.getTeamPicture() == null || developer.getTeamPicture().isEmpty()) {
                response.put("success", false);
                response.put("message", "No team picture to delete");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            // Delete the photo file
            photoService.deleteFile(developer.getTeamPicture());
            
            // Update developer record
            developer.setTeamPicture(null);
            developerRepository.save(developer);
            
            response.put("success", true);
            response.put("message", "Team picture deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
