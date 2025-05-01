/*
 * Customer Controller
 */
package cs251.group9.backend.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import cs251.group9.backend.entity.Customer;
import cs251.group9.backend.service.*;
import jakarta.persistence.*;

import java.util.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	
    @Autowired
    private CustomerService customerService;

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
}

