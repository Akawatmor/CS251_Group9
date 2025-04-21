package cs251.group9.backend.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import cs251.group9.backend.entity.Customer;
import cs251.group9.backend.repository.CustomerRepository;

import java.util.*;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer register(Customer customer) {
        customer.setMoney(0);
        return customerRepository.save(customer);
    }

    public Optional<Customer> login(String uName, String uEmail) {
        return customerRepository.findByUNameAndUEmail(uName, uEmail);
    }

    public Customer updateProfile(String userId, Customer updated) {
        Customer existing = customerRepository.findById(userId).orElseThrow();
        existing.setUName(updated.getUName());
        existing.setUEmail(updated.getUEmail());
        existing.setUNumber(updated.getUNumber());
        existing.setAge(updated.getAge());
        existing.setCountry(updated.getCountry());
        existing.setDName(updated.getDName());
        existing.setName(updated.getName());
        existing.setSurname(updated.getSurname());
        return customerRepository.save(existing);
    }
}

