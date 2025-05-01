package cs251.group9.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import cs251.group9.backend.entity.Game;

import java.util.*;

public interface GameRepository extends JpaRepository<Game, Integer> {
    List<Game> findByGNameContainingIgnoreCase(String gName);
    
    Game findBygameID(Integer gameID);
}
