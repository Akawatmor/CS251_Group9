package cs251.group9.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import cs251.group9.backend.entity.Game2x;

import java.util.*;

public interface GameRepository extends JpaRepository<Game2x, Integer> {
	
    List<Game2x> findByGNameContainingIgnoreCase(String gName);
    Optional<Game2x> findBygameID(Integer gameID);
}
