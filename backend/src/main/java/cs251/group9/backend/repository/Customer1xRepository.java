package cs251.group9.backend.repository;

import cs251.group9.backend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface Customer1xRepository extends JpaRepository<Customer1x, Long> {
	
    //Find by Username and Password

    Customer1x findByUNameAndPassword(String uName, String password);

    // Find by userID
    @Query(value = "SELECT * FROM customer WHERE userID = :userID", nativeQuery = true)
    Customer1x findByuserID(@Param("userID") Long userID);
    
    // Find by username and email (for login)
    @Query(value = "SELECT * FROM customer WHERE u_name = :uName AND u_email = :uEmail LIMIT 1", nativeQuery = true)
    Optional<Customer1x> findByUNameAndUEmail(@Param("uName") String uName, @Param("uEmail") String uEmail);
    
    // Find by username and password (for authentication)
    //@Query(value = "SELECT * FROM customer WHERE u_name = :uName AND password = :password LIMIT 1", nativeQuery = true)
    //Optional<Customer1x> findByUNameAndPassword(@Param("uName") String uName, @Param("password") String password);
    
    // Check if email already exists - fixed to return boolean
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM customer WHERE u_email = :uEmail", nativeQuery = true)
    boolean existsByUEmail(@Param("uEmail") String uEmail);
    
    // Check if username already exists - fixed to return boolean
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM customer WHERE u_name = :uName", nativeQuery = true)
    boolean existsByUName(@Param("uName") String uName);


}
