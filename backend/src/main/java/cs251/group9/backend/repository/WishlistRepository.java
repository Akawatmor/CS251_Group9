package cs251.group9.backend.repository;

import cs251.group9.backend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, WishlistId> {
    
    @Query(value = "SELECT * FROM wishlist WHERE userID = :userID", nativeQuery = true)
    List<Wishlist> findByUserId(@Param("userID") Long userID);
    
    @Query(value = "SELECT * FROM wishlist WHERE gameID = :gameID", nativeQuery = true)
    List<Wishlist> findByGameId(@Param("gameID") Long gameID);
    
    @Query(value = "SELECT * FROM wishlist WHERE userID = :userID AND gameID = :gameID", nativeQuery = true)
    Optional<Wishlist> findByUserIdAndGameId(@Param("userID") Long userID, @Param("gameID") Long gameID);
    
    @Query(value = "SELECT COUNT(*) FROM wishlist WHERE userID = :userID AND gameID = :gameID", nativeQuery = true)
    Long countByUserIdAndGameId(@Param("userID") Long userID, @Param("gameID") Long gameID);
    
    @Query(value = "DELETE FROM wishlist WHERE userID = :userID AND gameID = :gameID", nativeQuery = true)
    void deleteByUserIdAndGameId(@Param("userID") Long userID, @Param("gameID") Long gameID);
}