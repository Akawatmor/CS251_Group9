package cs251.group9.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cs251.group9.backend.entity.*;

import java.util.List;

public interface AchievementUserRepository extends JpaRepository<AchievementUser, AchievementUserId> {
    // Find user achievements by user ID
    @Query("SELECT au FROM AchievementUser au WHERE au.id.userID = :userId")
    List<AchievementUser> findByIdUserID(@Param("userId") Long userId);
    
    // Find users who have a specific achievement
    @Query("SELECT au FROM AchievementUser au WHERE au.id.aid = :achievementId")
    List<AchievementUser> findByIdAid(@Param("achievementId") Long achievementId);
}