package cs251.group9.backend.repository;

import cs251.group9.backend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByUNameAndUEmail(String UName, String UEmail);
    
    Customer findByuserID(Integer userID);
}
