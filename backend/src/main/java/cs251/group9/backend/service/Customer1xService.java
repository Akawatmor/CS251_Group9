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

    public Customer1x register(Customer1x customer) {
        validateCustomer(customer);
        
        // Check if username or email already exists
        if (customerRepo.existsByUName(customer.getuName())) {
            throw new IllegalArgumentException("Username already exists");
        }
        
        if (customerRepo.existsByUEmail(customer.getuEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        customer.setMoney(0);
        return customerRepo.save(customer);
    }
    
    private void validateCustomer(Customer1x customer) {
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

    public Optional<Customer1x> login(String uName, String password) {
        return customerRepo.findByUNameAndPassword(uName, password);
    }

    public Customer1x updateProfile(Long userId, Customer1x updated) {
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
    
    public void addMoney(Long userId, Integer amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        
        Customer1x customer = customerRepo.findByuserID(userId);
        if (customer == null) {
            throw new RuntimeException("User not found");
        }
        
        customer.setMoney(customer.getMoney() + amount);
        customerRepo.save(customer);
    }
    
    public void deleteCustomer(Long userId) {
        customerRepo.deleteById(userId);
    }
}

