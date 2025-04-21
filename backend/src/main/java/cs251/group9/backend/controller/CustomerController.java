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

    @PostMapping("/register")
    public ResponseEntity<Customer> register(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.register(customer));
    }

    @PostMapping("/login")
    public ResponseEntity<Customer> login(@RequestParam String uName, @RequestParam String uEmail) {
        return customerService.login(uName, uEmail)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Customer> updateProfile(@PathVariable String userId, @RequestBody Customer updated) {
        return ResponseEntity.ok(customerService.updateProfile(userId, updated));
    }
}

