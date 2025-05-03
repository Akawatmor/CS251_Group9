/*
 * Service For Customer
 */
package cs251.group9.backend.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.*;

import cs251.group9.backend.entity.Customer1x;
import cs251.group9.backend.repository.Customer1xRepository;

import java.util.*;

@Service
public class Customer1xService {

    @Autowired
    private Customer1xRepository customerRepo;

    /**
     * Register a new customer
     * @param customer The customer entity to register
     * @return The registered customer
     * @throws IllegalArgumentException if validation fails
     */
    public Customer1x register(Customer1x customer) {
        validateCustomer(customer);
        
        // Check if username or email already exists
        if (isUsernameTaken(customer.getuName())) {
            throw new IllegalArgumentException("Username already exists");
        }
        
        if (customerRepo.existsByUEmail(customer.getuEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        // Initialize money to 0
        customer.setMoney(0);
        return customerRepo.save(customer);
    }
    
    /**
     * Validate customer details
     * @param customer The customer to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateCustomer(Customer1x customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        
        if (customer.getuName() == null || customer.getuName().isEmpty()) {
            throw new IllegalArgumentException("Username (uName) must not be null or empty");
        }
        
        if (customer.getuEmail() == null || customer.getuEmail().isEmpty()) {
            throw new IllegalArgumentException("Email (uEmail) must not be null or empty");
        }
        
        if (customer.getPassword() == null || customer.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password must not be null or empty");
        }
    }

    /**
     * Authenticate a customer
     * @param uName Username
     * @param password Password
     * @return Optional containing the customer if login is successful
     */
    public Optional<Customer1x> login(String uName, String password) {
        if (uName == null || password == null) {
            return Optional.empty();
        }
        return customerRepo.findByUNameAndPassword(uName, password);
    }

    /**
     * Expose findByUNameAndPassword method
     * @param username Username
     * @param password Password
     * @return Optional containing the customer if found
     */
    public Optional<Customer1x> findByUNameAndPassword(String username, String password) {
        return customerRepo.findByUNameAndPassword(username, password);
    }

    /**
     * Update customer profile
     * @param userId ID of the customer to update
     * @param updated Updated customer information
     * @return Updated customer entity
     * @throws RuntimeException if customer not found
     */
    public Customer1x updateProfile(Long userId, Customer1x updated) {
        if (userId == null || updated == null) {
            throw new IllegalArgumentException("UserId and updated customer details cannot be null");
        }
        
        Customer1x existing = customerRepo.findByuserID(userId);
        if (existing == null) {
            throw new RuntimeException("User not found");
        }
        
        // Exclude userID and money from being copied
        BeanUtils.copyProperties(updated, existing, "userID", "money", "password");
        
        // Update password only if provided
        if (updated.getPassword() != null && !updated.getPassword().isEmpty()) {
            existing.setPassword(updated.getPassword());
        }
        
        return customerRepo.save(existing);
    }
    
    /**
     * Add money to customer account
     * @param userId ID of the customer
     * @param amount Amount to add
     * @throws IllegalArgumentException if amount is not positive
     * @throws RuntimeException if customer not found
     */
    public void addMoney(Long userId, Integer amount) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        
        Customer1x customer = customerRepo.findByuserID(userId);
        if (customer == null) {
            throw new RuntimeException("User not found");
        }
        
        customer.setMoney(customer.getMoney() + amount);
        customerRepo.save(customer);
    }
    
    /**
     * Get customer by ID
     * @param userId ID of the customer
     * @return Customer entity if found, null otherwise
     */
    public Customer1x getCustomerById(Long userId) {
        if (userId == null) {
            return null;
        }
        return customerRepo.findByuserID(userId);
    }
    
    /**
     * Delete a customer
     * @param userId ID of the customer to delete
     * @throws IllegalArgumentException if userId is null
     */
    public void deleteCustomer(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        customerRepo.deleteById(userId);
    }

    /**
     * Check if username is already taken
     * @param uName Username
     * @return true if username exists, false otherwise
     */
    public boolean isUsernameTaken(String uName) {
        return customerRepo.existsByUName(uName);
    }
}

