/*
 * Friend controller
 */
package cs251.group9.backend.controller;

import cs251.group9.backend.entity.*;
import cs251.group9.backend.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/friends")
public class FriendController {
	
	
    @Autowired private FriendRepository repo;

    //Add Friend
    @PostMapping
    public ResponseEntity<Friend> addFriend(@RequestBody Friend f) {
        return ResponseEntity.ok(repo.save(f));
    }
}