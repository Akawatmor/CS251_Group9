package cs251.group9.backend.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cs251.group9.backend.entity.Customer;
import cs251.group9.backend.repository.CustomerRepository;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepo;

    // Register (POST)
    @PostMapping("/register")
    public ResponseEntity<Customer> register(@RequestBody Customer customer) {
        customer.setMoney(BigDecimal.ZERO);
        return ResponseEntity.ok(customerRepo.save(customer));
    }

    // Login (GET)
    @GetMapping("/login")
    public ResponseEntity<Customer> login(@RequestParam String uName, @RequestParam String uEmail) {
        return customerRepo.findByUNameAndUEmail(uName, uEmail)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    // Get All Customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    // Update Profile (PUT)
    @PutMapping("/{userId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String userId, @RequestBody Customer updateData) {
        return customerRepo.findById(userId)
            .map(existing -> {
                existing.setUName(updateData.getUName());
                existing.setUEmail(updateData.getUEmail());
                existing.setUNumber(updateData.getUNumber());
                existing.setAge(updateData.getAge());
                existing.setCountry(updateData.getCountry());
                existing.setDName(updateData.getDName());
                existing.setName(updateData.getName());
                existing.setSurname(updateData.getSurname());
                return ResponseEntity.ok(customerRepo.save(existing));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    // Delete Customer
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String userId) {
        if (customerRepo.existsById(userId)) {
            customerRepo.deleteById(userId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

