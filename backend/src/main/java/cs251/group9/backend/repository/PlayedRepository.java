package cs251.group9.backend.repository;

import cs251.group9.backend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface PlayedRepository extends JpaRepository<Played, PlayedId> {
    
    @Query(value = "SELECT * FROM played WHERE gameID = :gameID", nativeQuery = true)
    List<Played> findByGameGameID(@Param("gameID") Long gameId);
    
    @Query(value = "SELECT * FROM played WHERE userID = :userID", nativeQuery = true)
    List<Played> findByUserID(@Param("userID") Long userId);
    
    @Query(value = "SELECT COUNT(*) > 0 FROM played WHERE userID = :userID AND gameID = :gameID", nativeQuery = true)
    boolean existsByUserIDAndGameID(@Param("userID") Long userID, @Param("gameID") Long gameID);
    
    @Query(value = "SELECT * FROM played WHERE userID = :userID AND gameID = :gameID", nativeQuery = true)
    Optional<Played> findByUserIDAndGameID(@Param("userID") Long userID, @Param("gameID") Long gameID);
    
    @Modifying
    @Query(value = "DELETE FROM played WHERE gameID = :gameID", nativeQuery = true)
    void deleteByGameId(@Param("gameID") Long gameID);
}