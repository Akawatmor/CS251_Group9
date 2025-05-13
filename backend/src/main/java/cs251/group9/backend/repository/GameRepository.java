package cs251.group9.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cs251.group9.backend.entity.Game2x;

import java.util.*;

public interface GameRepository extends JpaRepository<Game2x, Long> {
	
    List<Game2x> findByGNameContainingIgnoreCase(String gName);
    
    Optional<Game2x> findBygameID(Long gameID);
    
    @Query("SELECT g FROM Game2x g WHERE g.gPrice <= :maxPrice")
    List<Game2x> findByPriceLessThanEqual(@Param("maxPrice") Integer maxPrice);
    
    @Query("SELECT g FROM Game2x g WHERE g.rating >= :minRating")
    List<Game2x> findByRatingGreaterThanEqual(@Param("minRating") Float minRating);
    
    @Query(value = "SELECT g.* FROM game g JOIN game_category gc ON g.gameID = gc.gameID WHERE gc.cid = :categoryId", 
           nativeQuery = true)
    List<Game2x> findByCategoryId(@Param("categoryId") Long categoryId);

    // find best rated game
    @Query("SELECT g FROM Game2x g ORDER BY g.rating DESC")
    List<Game2x> findAllByOrderByRatingDesc();
}
