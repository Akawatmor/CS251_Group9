package cs251.group9.backend.repository;

import cs251.group9.backend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, ReviewId> {
    
    @Query(value = "SELECT * FROM review WHERE gameID = :gameID", nativeQuery = true)
    List<Review> findByGameId(@Param("gameID") Long gameID);
    
    @Query(value = "SELECT * FROM review WHERE userID = :userID", nativeQuery = true)
    List<Review> findByUserId(@Param("userID") Long userID);
    
    @Query(value = "SELECT * FROM review WHERE userID = :userID AND gameID = :gameID", nativeQuery = true)
    Optional<Review> findByUserIdAndGameId(@Param("userID") Long userID, @Param("gameID") Long gameID);
    
    @Query(value = "SELECT AVG(score) FROM review WHERE gameID = :gameID", nativeQuery = true)
    Float calculateAverageRatingByGameId(@Param("gameID") Long gameID);
}