/*
 * Service For Customer
 */
package cs251.group9.backend.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import cs251.group9.backend.entity.*;
import cs251.group9.backend.repository.Customer1xRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.*;

@Service
public class Customer1xService {

    @Autowired
    private Customer1xRepository customerRepo;
    
    @PersistenceContext
    private EntityManager entityManager;

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
        // Validate input
        if (userId == null || updated == null) {
            throw new IllegalArgumentException("UserId and updated customer details cannot be null");
        }
        
        // Find existing customer
        Customer1x existing = customerRepo.findByuserID(userId);
        if (existing == null) {
            throw new RuntimeException("User not found");
        }

        System.out.println("Existing customer: " + existing);
        System.out.println("Updated customer: " + updated);

        // Handle username updates with proper null checks
        if (updated.getuName() != null) {
            // Keep existing username regardless of what was submitted
            updated.setuName(existing.getuName());
        }

        // Handle name updates with proper null checks
        if (updated.getName() != null) {
            // Allow name to be updated
            existing.setName(updated.getName());
        }

        // Handle surname updates with proper null checks
        if (updated.getSurname() != null) {
            // Allow surname to be updated
            existing.setSurname(updated.getSurname());
        }

        // Handle email updates with proper null checks
        if (updated.getuEmail() != null) {
            // Keep existing email regardless of what was submitted
            updated.setuEmail(existing.getuEmail());
        }

        // Handle display name updates with proper null checks
        if (updated.getdName() != null) {
            // Allow display name to be updated
            existing.setdName(updated.getdName());
        }

        // Handle password updates with proper null checks
        if (updated.getPassword() != null) {
            // Allow password to be updated
            existing.setPassword(updated.getPassword());
        }
        // Handle money updates with proper null checks
        if (updated.getMoney() != null) {
            // Allow money to be updated
            existing.setMoney(updated.getMoney());
        }
        //country
        if (updated.getCountry() != null) {
            // Allow country to be updated
            existing.setCountry(updated.getCountry());
        }
        if (updated.getAge() != null) {
            // Allow number to be updated
            existing.setAge((updated.getAge()));
        }
        if (updated.getuNumber() != null) {
            // Allow number to be updated
            existing.setuNumber(updated.getuNumber());
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
     * @throws RuntimeException if deletion fails due to constraint violations
     */
    @Transactional
    public void deleteCustomer(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        
        try {
            // Execute custom query to remove all related records first
            String cleanupQuery = "DELETE FROM Friend f WHERE f.user1.userID = :userId OR f.user2.userID = :userId";
            entityManager.createQuery(cleanupQuery).setParameter("userId", userId).executeUpdate();
            
            // Delete Wishlist entries
            entityManager.createQuery("DELETE FROM Wishlist w WHERE w.customer.userID = :userId")
                         .setParameter("userId", userId).executeUpdate();
            
            // Delete Review entries
            entityManager.createQuery("DELETE FROM Review r WHERE r.customer.userID = :userId")
                         .setParameter("userId", userId).executeUpdate();
            
            // Delete Played entries
            entityManager.createQuery("DELETE FROM Played p WHERE p.customer.userID = :userId")
                         .setParameter("userId", userId).executeUpdate();
            
            // Delete Achievement User entries
            entityManager.createQuery("DELETE FROM AchievementUser au WHERE au.customer.userID = :userId")
                         .setParameter("userId", userId).executeUpdate();
            
            // Delete Order entries
            entityManager.createQuery("DELETE FROM Order3x o WHERE o.customer.userID = :userId")
                         .setParameter("userId", userId).executeUpdate();
            
            // Now it's safe to delete the customer
            customerRepo.deleteById(userId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete customer: " + e.getMessage(), e);
        }
    }

    /**
     * Check if username is already taken
     * @param uName Username
     * @return true if username exists, false otherwise
     */
    public boolean isUsernameTaken(String uName) {
        return customerRepo.existsByUName(uName);
    }

    /**
     * Get all customers
     * @return List of all customers
     */
    public List<Customer1x> getAllCustomers() {
        return customerRepo.findAll();
    }
}

