/*
 * Service For Customer
 */
package cs251.group9.backend.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.*;

import cs251.group9.backend.controller.Customer1xController;
import cs251.group9.backend.entity.Customer1x;
import cs251.group9.backend.repository.Customer1xRepository;
import cs251.group9.backend.repository.CustomerRepository;

import java.util.*;


@Service
public class Customer1xService {

    @Autowired
    private Customer1xRepository C1xR;

    public Customer1x register(Customer1x customer) {
        if (customer.getuName() == null || customer.getuName().isEmpty()) {
            throw new IllegalArgumentException("Username (UName) must not be null or empty");
        }
        if (customer.getuEmail() == null || customer.getuEmail().isEmpty()) {
            throw new IllegalArgumentException("Email (UEmail) must not be null or empty");
        }
        customer.setMoney(0);
        return C1xR.save(customer);
    }

    public Optional<Customer1x> login(String uName, String uEmail) {
        return C1xR.findByUNameAndUEmail(uName, uEmail);
    }

    public Customer1x updateProfile(Long userId, Customer1x updated) {
    	Customer1x existing = Customer1xRepository.findByuserID(userId);
        // Exclude userID and money from being copied
        BeanUtils.copyProperties(updated, existing, "userID", "money");
        return C1xR.save(existing);
    }
    
    public void deleteCustomer(Long userId) {
    	C1xR.deleteById(userId);
    }
}

