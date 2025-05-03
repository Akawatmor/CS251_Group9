/*
 * Customer Controller
 */
package cs251.group9.backend.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import cs251.group9.backend.entity.Customer1x;
import cs251.group9.backend.service.Customer1xService;
import cs251.group9.backend.service.PhotoService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
public class Customer1xController {
    
    @Autowired
    private Customer1xService customer1xService;
    
    @Autowired
    private PhotoService photoService;

    /**
     * Authenticate a customer
     * @param uName Username
     * @param password Password
     * @return Boolean indicating authentication success
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam String uName, @RequestParam String password) {
        Map<String, Object> response = new HashMap<>();
        
        return customer1xService.login(uName, password)
                .map(customer -> {
                    response.put("success", true);
                    response.put("message", "Login successful");
                    response.put("user", customer);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    response.put("success", false);
                    response.put("message", "Invalid credentials");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
                });
    }

    /**
     * Register a new customer
     * @param customer The customer entity to register
     * @return ResponseEntity with registered customer or error message
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Customer1x customer) {
        if (customer1xService.isUsernameTaken(customer.getuName())) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }
        try {
            customer1xService.register(customer);
            return ResponseEntity.ok("Customer registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Delete a customer account
     * @param userId ID of the customer to delete
     * @return ResponseEntity with status
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> deleteCustomer(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            customer1xService.deleteCustomer(userId);
            response.put("success", true);
            response.put("message", "Customer deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Update customer profile
     * @param userId ID of the customer to update
     * @param updated Updated customer information
     * @return ResponseEntity with updated customer or error message
     */
    @PutMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> updateProfile(@PathVariable Long userId, @RequestBody Customer1x updated) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Customer1x updatedCustomer = customer1xService.updateProfile(userId, updated);
            response.put("success", true);
            response.put("message", "Profile updated successfully");
            response.put("user", updatedCustomer);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Add money to customer account
     * @param userId ID of the customer
     * @param amount Amount to add
     * @return ResponseEntity with updated customer or error message
     */
    @PostMapping("/{userId}/add-money")
    public ResponseEntity<Map<String, Object>> addMoney(@PathVariable Long userId, @RequestParam Integer amount) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            customer1xService.addMoney(userId, amount);
            Customer1x customer = customer1xService.getCustomerById(userId);
            
            response.put("success", true);
            response.put("message", String.format("Added %d to account balance", amount));
            response.put("user", customer);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Upload a profile photo
     * @param userId ID of the customer
     * @param file Photo file to upload
     * @return ResponseEntity with file path or error message
     */
    @PostMapping("/{userId}/photo")
    public ResponseEntity<Map<String, Object>> uploadPhoto(@PathVariable Long userId, 
                                              @RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String filePath = photoService.storePhoto(userId, file);
            response.put("success", true);
            response.put("message", "Photo uploaded successfully");
            response.put("photoPath", filePath);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to upload: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Get customer's profile photo path
     * @param userId ID of the customer
     * @return ResponseEntity with photo path
     */
    @GetMapping("/{userId}/photo")
    public ResponseEntity<Map<String, Object>> getPhotoPath(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String photoPath = photoService.getPhotoPath(userId);
            response.put("success", true);
            response.put("photoPath", photoPath);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}

