package cs251.group9.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cs251.group9.backend.entity.Developer3x;
import cs251.group9.backend.repository.DeveloperRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing developers
 */
@Service
public class DeveloperService {
    
    @Autowired
    private DeveloperRepository developerRepo;
    
    /**
     * Register a new developer
     * @param developer The developer entity to register
     * @return The registered developer
     * @throws IllegalArgumentException if validation fails
     */
    @Transactional
    public Developer3x registerDeveloper(Developer3x developer) {
        validateDeveloper(developer);
        
        // Check if developer name or email already exists
        if (isDeveloperNameTaken(developer.getDevName())) {
            throw new IllegalArgumentException("Developer name already exists");
        }
        
        if (isEmailTaken(developer.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        return developerRepo.save(developer);
    }
    
    /**
     * Validate developer details
     * @param developer The developer to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateDeveloper(Developer3x developer) {
        if (developer == null) {
            throw new IllegalArgumentException("Developer cannot be null");
        }
        
        if (developer.getDevName() == null || developer.getDevName().isEmpty()) {
            throw new IllegalArgumentException("Developer name must not be null or empty");
        }
        
        if (developer.getEmail() == null || developer.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email must not be null or empty");
        }
    }
    
    /**
     * Get developer by ID
     * @param devId ID of the developer
     * @return Developer entity if found
     * @throws RuntimeException if developer not found
     */
    public Developer3x getDeveloperById(Long devId) {
        if (devId == null) {
            throw new IllegalArgumentException("Developer ID cannot be null");
        }
        
        Optional<Developer3x> developer = developerRepo.findById(devId);
        if (developer.isEmpty()) {
            throw new RuntimeException("Developer not found");
        }
        
        return developer.get();
    }
    
    /**
     * Get all developers
     * @return List of all developers
     */
    public List<Developer3x> getAllDevelopers() {
        return developerRepo.findAll();
    }
    
    /**
     * Update developer details
     * @param devId ID of the developer to update
     * @param updated Updated developer information
     * @return Updated developer entity
     * @throws RuntimeException if developer not found or validation fails
     */
    @Transactional
    public Developer3x updateDeveloper(Long devId, Developer3x updated) {
        if (devId == null || updated == null) {
            throw new IllegalArgumentException("Developer ID and updated details cannot be null");
        }
        
        Developer3x existing = getDeveloperById(devId);
        
        /*
        // Check if trying to change to an existing developer name
        if (!existing.getDevName().equals(updated.getDevName()) && 
                isDeveloperNameTaken(updated.getDevName())) {
            throw new RuntimeException("Developer name already taken");
        }
        
        // Check if trying to change to an existing email
        if (!existing.getEmail().equals(updated.getEmail()) && 
                isEmailTaken(updated.getEmail())) {
            throw new RuntimeException("Email already taken");
        }*/
        
        // Copy properties from updated to existing
        if (updated.getDevName() != null) {
            existing.setDevName(updated.getDevName());
        }
        if (updated.getEmail() != null) {
            existing.setEmail(updated.getEmail());
        }
        if (updated.getPassword() != null) {
            existing.setPassword(updated.getPassword());
        }
        if (updated.getDevDesc() != null) {
            existing.setDevDesc(updated.getDevDesc());
        }

        if (updated.getSocialMedia() != null) {
            existing.setSocialMedia(updated.getSocialMedia());
        }
        
        return developerRepo.save(existing);
    }
    
    /**
     * Delete a developer
     * @param devId ID of the developer to delete
     * @throws RuntimeException if developer not found
     */
    @Transactional
    public void deleteDeveloper(Long devId) {
        if (devId == null) {
            throw new IllegalArgumentException("Developer ID cannot be null");
        }
        
        // Check if developer exists
        if (!developerRepo.existsById(devId)) {
            throw new RuntimeException("Developer not found");
        }
        
        developerRepo.deleteById(devId);
    }
    
    /**
     * Check if developer name is already taken
     * @param devName Developer name
     * @return true if name exists, false otherwise
     */
    public boolean isDeveloperNameTaken(String devName) {
        return devName != null && developerRepo.existsByDevName(devName);
    }
    
    /**
     * Check if email is already taken
     * @param email Email
     * @return true if email exists, false otherwise
     */
    public boolean isEmailTaken(String email) {
        return email != null && developerRepo.existsByEmail(email);
    }
    
    /**
     * Check if username is taken
     * This is for compatibility with the controller
     */
    public boolean isUsernameTaken(String username) {
        return isDeveloperNameTaken(username);
    }

    /**
     * Find developer by username and password
     * @param username Developer username
     * @param password Developer password
     * @return Optional of Developer3x if found
     */
    public Optional<Developer3x> findByDevNameAndPassword(String username, String password) {
        return developerRepo.findByDevNameAndPassword(username, password);
    }
}
