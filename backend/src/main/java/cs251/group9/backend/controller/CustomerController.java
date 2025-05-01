/*
 * Customer Controller
 */
package cs251.group9.backend.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import cs251.group9.backend.entity.Customer;
import cs251.group9.backend.service.*;
import jakarta.persistence.*;

import java.util.*;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private PhotoService photoService;

    //To register User
    @PostMapping("/register")
    public ResponseEntity<Customer> register(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.register(customer));
    }
    
    //To Login User
    @PostMapping("/login")
    public ResponseEntity<Customer> login(@RequestParam String uName, @RequestParam String uEmail) {
        return customerService.login(uName, uEmail)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    //To update some part of user (Like deduct money)
    @PutMapping("/{userId}")
    public ResponseEntity<Customer> updateProfile(@PathVariable Integer userId, @RequestBody Customer updated) {
        return ResponseEntity.ok(customerService.updateProfile(userId, updated));
    }

    // To delete a user
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer userId) {
        customerService.deleteCustomer(userId);
        return ResponseEntity.noContent().build();
    }
    
    // To upload a profile photo
    @PostMapping("/{userId}/photo")
    public ResponseEntity<String> uploadPhoto(@PathVariable Integer userId, 
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
    public ResponseEntity<String> getPhotoPath(@PathVariable Integer userId) {
        String photoPath = photoService.getPhotoPath(userId);
        return ResponseEntity.ok(photoPath);
    }
}

