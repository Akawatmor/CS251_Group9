package cs251.group9.backend.repository;

import cs251.group9.backend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface OrderRepository extends JpaRepository<Order3x, Long> {
    
    @Query(value = "SELECT COUNT(*) FROM orders WHERE userID = :userID AND gameID = :gameID", nativeQuery = true)
    Long countByUserIDAndGameID(@Param("userID") Long userID, @Param("gameID") Long gameID);
    
    @Query(value = "SELECT * FROM orders WHERE userID = :userID", nativeQuery = true)
    List<Order3x> findByCustomerUserID(@Param("userID") Long userID);
    
    @Query(value = "SELECT * FROM orders WHERE userID = :userID AND orderID = :orderID", nativeQuery = true)
    Optional<Order3x> findByCustomerUserIDAndOrderID(@Param("userID") Long userID, @Param("orderID") Long orderID);

    @Query(value = "SELECT * FROM orders WHERE gameID = :gameID", nativeQuery = true)
    List<Order3x> findByGameID(@Param("gameID") Long gameID);

    @Query(value = "SELECT * FROM orders WHERE userID = :userID AND gameID = :gameID", nativeQuery = true)
    Optional<Order3x> findByUserIDAndGameID(@Param("userID") Long userID, @Param("gameID") Long gameID);

    @Modifying
    @Query(value = "DELETE FROM orders WHERE gameID = :gameID", nativeQuery = true)
    void deleteByGameId(@Param("gameID") Long gameID);
}