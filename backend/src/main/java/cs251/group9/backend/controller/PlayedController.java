/*
 * Played Controller
 */
package cs251.group9.backend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cs251.group9.backend.entity.Played;
import cs251.group9.backend.repository.PlayedRepository;

@RestController
@RequestMapping("/api/played")
public class PlayedController {
    
    @Autowired 
    private PlayedRepository playedRepo;
    
    // Get Players that play a Game
    @GetMapping("/game/{gameID}")
    public List<String> getPlayers(@PathVariable Integer gameID) {
        return playedRepo.findByGameGameID(gameID).stream()
                .map(p -> p.getCustomer().getuName())
                .collect(Collectors.toList());
    }
    
    // Get games played by a user
    @GetMapping("/user/{userID}")
    public List<Played> getUserGames(@PathVariable Integer userID) {
        return playedRepo.findByUserID(userID);
    }
    
    // Update play time for a game
    @PutMapping("/update-time")
    public ResponseEntity<Played> updatePlayTime(@RequestParam Integer userID, 
                                             @RequestParam Integer gameID,
                                             @RequestParam Long additionalMinutes) {
        return playedRepo.findByUserIDAndGameID(userID, gameID)
            .map(played -> {
                played.setPlayTime(played.getPlayTime() + additionalMinutes);
                return ResponseEntity.ok(playedRepo.save(played));
            })
            .orElse(ResponseEntity.notFound().build());
    }
}