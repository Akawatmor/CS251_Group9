package cs251.group9.backend.repository;

import cs251.group9.backend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    
    @Query(value = "SELECT * FROM orders WHERE userID = :userID AND orderID = :orderID", nativeQuery = true)
    Optional<Order> findByCustomerUserIDAndOrderID(@Param("userID") Long userID, @Param("orderID") Integer orderID);
    
    @Query(value = "SELECT * FROM orders WHERE userID = :userID", nativeQuery = true)
    List<Order> findByCustomerUserID(@Param("userID") Long userID);
    
    @Query(value = "SELECT * FROM orders WHERE gameID = :gameID", nativeQuery = true)
    List<Order> findByGameGameID(@Param("gameID") Integer gameID);
    
    @Query(value = "SELECT COUNT(*) > 0 FROM orders WHERE userID = :userID AND gameID = :gameID", nativeQuery = true)
    boolean existsByUserIDAndGameID(@Param("userID") Long userID, @Param("gameID") Integer gameID);
}