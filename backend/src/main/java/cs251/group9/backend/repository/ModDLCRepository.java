package cs251.group9.backend.repository;

import cs251.group9.backend.entity.ModDLC7x;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModDLCRepository extends JpaRepository<ModDLC7x, Long> {
    
    @Query(value = "SELECT * FROM mod_dlc WHERE gameID = :gameId", nativeQuery = true)
    List<ModDLC7x> findByGame(@Param("gameId") Long gameId);
    
    @Query(value = "SELECT * FROM mod_dlc WHERE modName LIKE %:name%", nativeQuery = true)
    List<ModDLC7x> findByModNameContaining(@Param("name") String name);

    @Query("SELECT m FROM ModDLC7x m WHERE m.modName LIKE %:name%")
    List<ModDLC7x> searchModDLCByName(String name);

    @Modifying
    @Query(value = "DELETE FROM mod_dlc WHERE gameID = :gameID", nativeQuery = true)
    void deleteByGameId(@Param("gameID") Long gameID);
}

