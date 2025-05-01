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
import cs251.group9.backend.service.*;

import java.util.*;


@RestController
@RequestMapping("/api/customers")
public class Customer1xController {
    
    @Autowired
    private Customer1xService customerService;
    
    @Autowired
    private PhotoService photoService;

    // To register User
    @PostMapping("/register")
    public ResponseEntity<Customer1x> register(@RequestBody Customer1x customer) {
        try {
            return ResponseEntity.ok(customerService.register(customer));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    // To Login User
    @PostMapping("/login")
    public ResponseEntity<Customer1x> login(@RequestParam String uName, @RequestParam String password) {
        return customerService.login(uName, password)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    // To update user profile
    @PutMapping("/{userId}")
    public ResponseEntity<Customer1x> updateProfile(@PathVariable Long userId, @RequestBody Customer1x updated) {
        try {
            return ResponseEntity.ok(customerService.updateProfile(userId, updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Add money to user account
    @PostMapping("/{userId}/add-money")
    public ResponseEntity<Customer1x> addMoney(@PathVariable Long userId, @RequestParam Integer amount) {
        try {
            customerService.addMoney(userId, amount);
            return ResponseEntity.ok(customerService.updateProfile(userId, new Customer1x()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // To delete a user
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long userId) {
        customerService.deleteCustomer(userId);
        return ResponseEntity.noContent().build();
    }
    
    // To upload a profile photo
    @PostMapping("/{userId}/photo")
    public ResponseEntity<String> uploadPhoto(@PathVariable Long userId, 
                                              @RequestParam("file") MultipartFile file) {
        try {
            String filePath = photoService.storePhoto(userId, file);
            return ResponseEntity.ok(filePath);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to upload: " + e.getMessage());
        }
    }
    
    // To get a profile photo
    @GetMapping("/{userId}/photo")
    public ResponseEntity<String> getPhotoPath(@PathVariable Long userId) {
        String photoPath = photoService.getPhotoPath(userId);
        return ResponseEntity.ok(photoPath);
    }
}

