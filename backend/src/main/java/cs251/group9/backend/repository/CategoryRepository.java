package cs251.group9.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cs251.group9.backend.entity.Category5x;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category5x, String> {
    
    @Query(value = "SELECT * FROM category WHERE cName LIKE %:name%", nativeQuery = true)
    List<Category5x> findByCNameContaining(@Param("name") String name);
    
    @Query(value = "SELECT c.* FROM category c JOIN game_category gc ON c.cid = gc.cid WHERE gc.gameID = :gameId", nativeQuery = true)
    List<Category5x> findCategoriesByGameId(@Param("gameId") Integer gameId);
}
