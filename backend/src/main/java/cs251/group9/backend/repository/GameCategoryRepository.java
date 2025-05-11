package cs251.group9.backend.repository;

import cs251.group9.backend.entity.GameCategory;
import cs251.group9.backend.entity.GameCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GameCategoryRepository extends JpaRepository<GameCategory, GameCategoryId> {
    // Your existing methods
    
    @Modifying
    @Query(value = "DELETE FROM game_category WHERE gameID = :gameID", nativeQuery = true)
    void deleteByGameId(@Param("gameID") Long gameID);
}
