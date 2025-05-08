package cs251.group9.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cs251.group9.backend.entity.*;

import java.util.List;

public interface AchievementPreRepository extends JpaRepository<AchievementPre, AchievementPreId> {
    // Find prerequisites by achievement ID
    @Query("SELECT ap FROM AchievementPre ap WHERE ap.id.aid = :achievementId")
    List<AchievementPre> findByIdAid(@Param("achievementId") Long achievementId);
}
