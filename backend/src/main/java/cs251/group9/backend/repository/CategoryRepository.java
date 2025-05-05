package cs251.group9.backend.repository;

import cs251.group9.backend.entity.Category5x;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category5x, Long> {
    // Fix the query syntax
    @Query("SELECT c FROM Category5x c WHERE c.cName LIKE %:keyword%")
    List<Category5x> searchCategories(@Param("keyword") String keyword);

    @Query(value = "SELECT c.* FROM category c JOIN game_category gc ON c.cid = gc.cid WHERE gc.gameID = :gameId", nativeQuery = true)
    List<Category5x> findCategoriesByGameId(@Param("gameId") Long gameId);

    Category5x findByCid(Long cid);

    List<Category5x> findByCNameContaining(String cName);
    List<Category5x> findByCName(String cName);
}
