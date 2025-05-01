package cs251.group9.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cs251.group9.backend.entity.Developer;

public interface DeveloperRepository extends JpaRepository<Developer, Integer> {
	
}

