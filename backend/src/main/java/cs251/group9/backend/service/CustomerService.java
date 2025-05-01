/*
 * Service For Customer
 */
package cs251.group9.backend.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.*;

import cs251.group9.backend.entity.Customer;
import cs251.group9.backend.repository.CustomerRepository;

import java.util.*;


@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer register(Customer customer) {
        if (customer.getUName() == null || customer.getUName().isEmpty()) {
            throw new IllegalArgumentException("Username (UName) must not be null or empty");
        }
        if (customer.getUEmail() == null || customer.getUEmail().isEmpty()) {
            throw new IllegalArgumentException("Email (UEmail) must not be null or empty");
        }
        customer.setMoney(0);
        return customerRepository.save(customer);
    }

    public Optional<Customer> login(String uName, String uEmail) {
        return customerRepository.findByUNameAndUEmail(uName, uEmail);
    }

    public Customer updateProfile(Integer userId, Customer updated) {
        Customer existing = customerRepository.findByuserID(userId);
        // Exclude userID and money from being copied
        BeanUtils.copyProperties(updated, existing, "userID", "money");
        return customerRepository.save(existing);
    }
    
    public void deleteCustomer(Integer userId) {
        customerRepository.deleteById(userId);
    }
}

