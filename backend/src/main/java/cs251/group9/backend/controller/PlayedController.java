/*
 * Played Controller
 */
package cs251.group9.backend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
@RestController
@RequestMapping("/api/played")
public class PlayedController {
    @Autowired 
    private PlayedRepository playedRepo;
    
    //Get Player that play Game
    @GetMapping("/game/{gameID}")
    public List<String> getPlayers(@PathVariable Integer gameID) {
        return playedRepo.findByGameGameID(gameID).stream()
                .map(p -> p.getCustomer().getUName())
                .collect(Collectors.toList());
    }
}
*/