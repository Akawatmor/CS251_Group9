/*
 * Played Controller
 */
package cs251.group9.backend.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

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
    
////////////// Get all games played by a user //////////////////////
    @GetMapping("/game={gameID}")
    public List<String> getPlayers(@PathVariable Long gameID) {
        return playedRepo.findByGameGameID(gameID).stream()
                .map(p -> p.getCustomer().getuName())
                .collect(Collectors.toList());
    }
    
/////////////// Get all games played by a user //////////////////////
    @GetMapping("/user={userID}")
    public List<Played> getUserGames(@PathVariable Long userID) {
        return playedRepo.findByUserID(userID);
    }
    
///////////////// Get all games played by a user //////////////////////
    @PutMapping("/user={userID}/game={gameID}/addtime={additionalMinutes}")
    public ResponseEntity<Played> updatePlayTime(@PathVariable Long userID, 
                                             @PathVariable Long gameID,
                                             @PathVariable Long additionalMinutes) {
        return playedRepo.findByUserIDAndGameID(userID, gameID)
            .map(played -> {
                played.setPlayTime(played.getPlayTime() + additionalMinutes);
                return ResponseEntity.ok(playedRepo.save(played));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
///////////////// Update last play time and increment play count //////////////////////
    @PutMapping("/user={userID}/game={gameID}/played")
    public ResponseEntity<Played> updateLastPlayTimeAndCount(@PathVariable Long userID, 
                                                           @PathVariable Long gameID) {
        return playedRepo.findByUserIDAndGameID(userID, gameID)
            .map(played -> {
                played.setLastPlayed(LocalDateTime.now());
                played.setPlayTime(played.getPlayTime() + 1);
                return ResponseEntity.ok(playedRepo.save(played));
            })
            .orElse(ResponseEntity.notFound().build());
    }
}