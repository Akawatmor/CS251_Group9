package cs251.group9.backend.repository;

import cs251.group9.backend.entity.Friend;
import cs251.group9.backend.entity.FriendId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, FriendId> {
    @Query("SELECT f FROM Friend f WHERE f.user1.id = :userId OR f.user2.id = :userId")
    List<Friend> findByUser1IdOrUser2Id(@Param("userId") Long userId);

    @Query("SELECT f FROM Friend f WHERE f.user1.id = :userId")
    List<Friend> findByUser1Id(@Param("userId") Long userId);
    
    @Query("SELECT f FROM Friend f WHERE f.user1.id = :user1Id AND f.user2.id = :user2Id")
    Optional<Friend> findByUser1IdAndUser2Id(@Param("user1Id") Long user1Id, @Param("user2Id") Long user2Id);
    
    // Custom query to find friendships regardless of which user is user1 or user2
    @Query("SELECT f FROM Friend f WHERE (f.user1.id = :user1Id AND f.user2.id = :user2Id) OR (f.user1.id = :user2Id AND f.user2.id = :user1Id)")
    Optional<Friend> findFriendshipBetweenUsers(@Param("user1Id") Long user1Id, @Param("user2Id") Long user2Id);
    
    // Query to get all friends of a user with their IDs
    @Query("SELECT f.user2.id FROM Friend f WHERE f.user1.id = :userId UNION SELECT f.user1.id FROM Friend f WHERE f.user2.id = :userId")
    List<Long> findAllFriendIdsByUserId(@Param("userId") Long userId);
}