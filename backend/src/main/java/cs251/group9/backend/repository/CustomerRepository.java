package cs251.group9.backend.repository;

import cs251.group9.backend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface CustomerRepository extends JpaRepository<Customer1x, Long> {
	
	//find by userID
	Customer1x findByuserID(Long userID);
	
	
    Optional<Customer1x> findByUNameAndUEmail(String UName, String UEmail);
    
}
