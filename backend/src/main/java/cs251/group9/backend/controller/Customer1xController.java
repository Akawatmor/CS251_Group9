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
import cs251.group9.backend.repository.Customer1xRepository;
import cs251.group9.backend.service.Customer1xService;
import cs251.group9.backend.service.PhotoService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
public class Customer1xController {

////////////////// Autowired Services and Repositories /////////////
    @Autowired private Customer1xRepository customer1xRepository; // Repository for customer data access
    @Autowired private Customer1xService customer1xService; // Service for customer operations
    @Autowired private PhotoService photoService; // Service for photo operations

/////////////////////// DTO ///////////////////

    //Login Request Body
    static class amountRequest {
        private int amount;
        
        public int getAmount() {
            return amount;
        }
        
        public void setAmount(int amount) {
            this.amount = amount;
        }
    }


////////////////////// Login //////////////
    // Move to AuthenticationController

///////////////////// Register /////////////
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

///////////////////// Check Customer by ID /////////////////////
    public Customer1x findCustomer1xById(Long userId) {
        return customer1xRepository.findByuserID(userId);
    }

///////////////////////// Get Customer by ID /////////////////////////
    @GetMapping("/id={userId}")
    public ResponseEntity<Map<String, Object>> getCustomerById(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Customer1x customer = customer1xService.getCustomerById(userId);
            response.put("success", true);
            response.put("user", customer);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

///////////////////////// Delete Customer by CustomerID /////////////////////////
    @DeleteMapping("/id={userId}")
    public ResponseEntity<Map<String, Object>> deleteCustomer(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
    
        // Check if the customer exists
        if(findCustomer1xById(userId) == null) {
            response.put("success", false);
            response.put("message", "User not found");
            return ResponseEntity.badRequest().body(response);
        }
        else{
            customer1xService.deleteCustomer(userId);
            response.put("success", true);
            response.put("message", "Customer deleted successfully");
            return ResponseEntity.ok(response);
        }
    
    }

///////////////////////// Update Customer Profile by CustomerID /////////////////////////
    @PutMapping("/id={userId}")
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

////////////////////// Add Money to Customer Account /////////////////////////
    @PostMapping("/id={userId}/money")
    public ResponseEntity<Map<String, Object>> addMoney(@PathVariable Long userId, @RequestBody amountRequest am) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validate amount is positive
            if (am.getAmount() <= 0) {
                response.put("success", false);
                response.put("message", "Amount must be greater than 0");
                return ResponseEntity.badRequest().body(response);
            }
            
            Customer1x customer = customer1xService.getCustomerById(userId);
            if (customer != null) {
                customer.setMoney(customer.getMoney() + am.getAmount());
                customer1xRepository.save(customer);
                
                response.put("success", true);
                response.put("message", "Money added successfully");
                response.put("money", customer.getMoney());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", "Error adding money: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

/////////////////////// Upload and Get Customer Profile Photo /////////////////////////
    @PostMapping("/id={userId}/photo")
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
    
///////////////////// Get Customer Profile Photo /////////////////////////
    @GetMapping("/id={userId}/photo")
    public ResponseEntity<?> getPhotoPath(@PathVariable Long userId) {
        try {
            byte[] photoData = photoService.getPhotoData(userId);
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

    

    
}

