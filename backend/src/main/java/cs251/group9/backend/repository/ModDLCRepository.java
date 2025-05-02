package cs251.group9.backend.repository;

import cs251.group9.backend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface ModDLCRepository extends JpaRepository<ModDLC7x, Long> {
    
    @Query(value = "SELECT * FROM mod_dlc WHERE gameID = :gameId", nativeQuery = true)
    List<ModDLC7x> findByGame(@Param("gameId") Long gameId);
    
    @Query(value = "SELECT * FROM mod_dlc WHERE modName LIKE %:name%", nativeQuery = true)
    List<ModDLC7x> findByModNameContaining(@Param("name") String name);

    @Query("SELECT m FROM ModDLC m WHERE m.modName LIKE %:keyword%")
    List<ModDLC> searchModDLCByName(@Param("keyword") String keyword);
}

