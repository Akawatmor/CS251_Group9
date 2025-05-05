package cs251.group9.backend.repository;

import cs251.group9.backend.entity.GameCategory;
import cs251.group9.backend.entity.GameCategoryId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameCategoryRepository extends CrudRepository<GameCategory, GameCategoryId> {
    // Your existing methods
}
