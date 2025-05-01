/*
 * Service For Customer
 */
package cs251.group9.backend.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.*;

import cs251.group9.backend.entity.Customer1X;
import cs251.group9.backend.repository.CustomerRepository;

import java.util.*;


@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer1X register(Customer1X customer) {
        if (customer.getuName() == null || customer.getuName().isEmpty()) {
            throw new IllegalArgumentException("Username (UName) must not be null or empty");
        }
        if (customer.getuEmail() == null || customer.getuEmail().isEmpty()) {
            throw new IllegalArgumentException("Email (UEmail) must not be null or empty");
        }
        customer.setMoney(0);
        return customerRepository.save(customer);
    }

    public Optional<Customer1X> login(String uName, String uEmail) {
        return customerRepository.findByUNameAndUEmail(uName, uEmail);
    }

    public Customer1X updateProfile(Long userId, Customer1X updated) {
        Customer1X existing = customerRepository.findByuserID(userId);
        // Exclude userID and money from being copied
        BeanUtils.copyProperties(updated, existing, "userID", "money");
        return customerRepository.save(existing);
    }
    
    public void deleteCustomer(Long userId) {
        customerRepository.deleteById(userId);
    }
}

