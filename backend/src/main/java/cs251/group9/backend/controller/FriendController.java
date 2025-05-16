/*
 * FriendController.java
 * 
 * This class is a Spring Boot REST controller that handles requests related to
 * friends.
 * It provides endpoints to add a friend.
 * 
 */
package cs251.group9.backend.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.*;

import cs251.group9.backend.entity.*;
//import cs251.group9.backend.service.*;
import cs251.group9.backend.repository.*;

import java.util.*;


@RestController
@RequestMapping("/api/friends")
public class FriendController {
	
    @Autowired private FriendRepository repo;
    
    @Autowired private Customer1xRepository customerRepo;  // Added repository for Customer entities

    // Get all friends of a user
    @GetMapping("/user={userId}")
    public ResponseEntity<List<Friend>> getFriendsByUserId(@PathVariable Long userId) {
        List<Friend> friends = repo.findByUser1IdOrUser2Id(userId);
        return ResponseEntity.ok(friends);
    }
    
    // Get a specific friendship
    @GetMapping("/user={user1Id}/friend={user2Id}")
    public ResponseEntity<?> getFriendship(@PathVariable Long user1Id, @PathVariable Long user2Id) {
        Optional<Friend> friendship = repo.findFriendshipBetweenUsers(user1Id, user2Id);
        
        if (friendship.isPresent()) {
            return ResponseEntity.ok(friendship.get());
        }
        return ResponseEntity.notFound().build();
    }

    // Add Friend using IDs
    @PostMapping("/user={user1Id}/friend={user2Id}")
    public ResponseEntity<?> addFriendship(@PathVariable Long user1Id, @PathVariable Long user2Id) {
        // Prevent self-friendship
        if (user1Id.equals(user2Id)) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Cannot add yourself as a friend");
        }
        
        // Check if friendship already exists (in either direction)
        if (repo.findFriendshipBetweenUsers(user1Id, user2Id).isPresent()) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Friendship already exists");
        }
        
        // Find both customer entities
        Optional<Customer1x> user1Opt = customerRepo.findById(user1Id);
        Optional<Customer1x> user2Opt = customerRepo.findById(user2Id);
        
        if (!user1Opt.isPresent() || !user2Opt.isPresent()) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("One or both users not found");
        }
        
        // Create composite key
        FriendId friendId = new FriendId();
        // Ensure smaller ID is always userID1 to maintain consistency
        if (user1Id < user2Id) {
            friendId.setUserID1(user1Id);
            friendId.setUserID2(user2Id);
        } else {
            friendId.setUserID1(user2Id);
            friendId.setUserID2(user1Id);
        }
        
        // Create friend entity
        Friend newFriendship = new Friend();
        newFriendship.setId(friendId);
        // Set users according to the userID order we determined above
        if (user1Id < user2Id) {
            newFriendship.setUser1(user1Opt.get());
            newFriendship.setUser2(user2Opt.get());
        } else {
            newFriendship.setUser1(user2Opt.get());
            newFriendship.setUser2(user1Opt.get());
        }
        
        try {
            Friend savedFriendship = repo.save(newFriendship);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFriendship);
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error creating friendship: " + e.getMessage());
        }
    }
    
    // Standard add friend method (kept for backward compatibility)
    @PostMapping
    public ResponseEntity<Friend> addFriend(@RequestBody Friend f) {
        return ResponseEntity.ok(repo.save(f));
    }
    
    // Remove friendship
    @DeleteMapping("/user={user1Id}/friend={user2Id}")
    public ResponseEntity<?> deleteFriendship(@PathVariable Long user1Id, @PathVariable Long user2Id) {
        Optional<Friend> friendship = repo.findFriendshipBetweenUsers(user1Id, user2Id);
        
        if (friendship.isPresent()) {
            try {
                repo.delete(friendship.get());
                return ResponseEntity.ok("Friendship deleted successfully");
            } catch (Exception e) {
                return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting friendship: " + e.getMessage());
            }
        }
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body("Friendship not found");
    }
}