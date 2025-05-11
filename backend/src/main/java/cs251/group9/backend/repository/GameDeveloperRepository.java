package cs251.group9.backend.repository;

import cs251.group9.backend.entity.GameDeveloper;
import cs251.group9.backend.entity.GameDeveloperId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GameDeveloperRepository extends JpaRepository<GameDeveloper, GameDeveloperId> {
    
    @Modifying
    @Query(value = "DELETE FROM game_developer WHERE gameID = :gameID", nativeQuery = true)
    void deleteByGameId(@Param("gameID") Long gameID);
}








