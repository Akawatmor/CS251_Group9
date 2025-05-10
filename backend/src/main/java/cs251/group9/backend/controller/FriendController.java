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

//import java.util.*;


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