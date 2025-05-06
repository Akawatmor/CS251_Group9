package cs251.group9.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cs251.group9.backend.entity.Developer3x;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer3x, Long> {
    boolean existsByDevName(String devName);
    boolean existsByEmail(String email);
}

