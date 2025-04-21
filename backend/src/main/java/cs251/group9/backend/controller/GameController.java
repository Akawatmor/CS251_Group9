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
    @Autowired private GameRepository repo;

    @GetMapping("/search")
    public List<Game> search(@RequestParam String name) {
        return repo.findByGNameContainingIgnoreCase(name);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> get(@PathVariable String id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> update(@PathVariable String id, @RequestBody Game g) {
        Game game = repo.findById(id).orElseThrow();
        // set fields
        return ResponseEntity.ok(repo.save(game));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

