/*
 * Game Controller
 */
package cs251.group9.backend.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import cs251.group9.backend.entity.Game;
import cs251.group9.backend.repository.GameRepository;
import cs251.group9.backend.service.*;
import jakarta.persistence.*;

import java.util.*;

@RestController
@RequestMapping("/api/games")
public class GameController {
    @Autowired 
    private GameRepository repo;
    
    //Add Game?
    
    
    //Search Game by Name
    @GetMapping("/search")
    public List<Game> search(@RequestParam Integer name) {
        return repo.findByGNameContainingIgnoreCase(name);
    }
    
    //Retrive Game Data
    @GetMapping("/{id}")
    public ResponseEntity<Game> get(@PathVariable Integer Id) {
        return repo.findBygameID(Id)
        		.map(ResponseEntity::ok)
        		.orElse(ResponseEntity.notFound().build());
    }
    
    //Update Game Info
    @PutMapping("/{id}")
    public ResponseEntity<Game> update(@PathVariable Integer id, @RequestBody Game g) {
        Game game = repo.findBygameID(id).orElseThrow();
        // set fields
        return ResponseEntity.ok(repo.save(game));
    }
    
    //Delete Game?
}

