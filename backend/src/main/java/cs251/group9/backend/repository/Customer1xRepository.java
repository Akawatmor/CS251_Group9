package cs251.group9.backend.repository;

import cs251.group9.backend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface Customer1xRepository extends JpaRepository<Customer1x, Long> {
	
    //Find by Username and Password

    Optional<Customer1x> findByUNameAndPassword(String username, String password);

    // Find by userID
    @Query(value = "SELECT * FROM customer WHERE userID = :userID", nativeQuery = true)
    Customer1x findByuserID(@Param("userID") Long userID);
    
    // Find by username and email (for login)
    @Query(value = "SELECT * FROM customer WHERE uName = :uName AND uEmail = :uEmail LIMIT 1", nativeQuery = true)
    Optional<Customer1x> findByUNameAndUEmail(@Param("uName") String uName, @Param("uEmail") String uEmail);
    
    // Find by username
    @Query("SELECT c FROM Customer1x c WHERE c.uName = :uName") // Use the correct attribute name
    Optional<Customer1x> findByUName(@Param("uName") String uName);
    
    // Check if email already exists
    boolean existsByUEmail(String uEmail);
    
    // Check if username already exists
    Boolean existsByUName(String uName);

}
