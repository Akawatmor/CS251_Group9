package cs251.group9.backend.repository;

import cs251.group9.backend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface PlayedRepository extends JpaRepository<Played, PlayedId> {
    List<Played> findByGameGameID(String gameId);
}