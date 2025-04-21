package cs251.group9.backend.repository;

import cs251.group9.backend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface ModDLCRepository extends JpaRepository<ModDLC, String> {
    List<ModDLC> findByGameGameID(String gameId);
}

