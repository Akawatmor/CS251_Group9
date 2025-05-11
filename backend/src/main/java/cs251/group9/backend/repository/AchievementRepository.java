package cs251.group9.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cs251.group9.backend.entity.*;

import java.util.List;

public interface AchievementRepository extends JpaRepository<Achievement4x, Long> {
    // Find achievements by game ID
    @Query("SELECT a FROM Achievement4x a WHERE a.game.gameID = :gameId")
    List<Achievement4x> findByGameGameID(@Param("gameId") Long gameId);

    @Modifying
    @Query(value = "DELETE FROM achievement WHERE gameID = :gameID", nativeQuery = true)
    void deleteByGameId(@Param("gameID") Long gameID);
}
