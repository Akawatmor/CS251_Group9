package cs251.group9.backend.repository;

import cs251.group9.backend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer3x, Long> {

    boolean existsByDevName(String devName);
    boolean existsByEmail(String email);

    @Query(value = "SELECT * FROM developer WHERE dev_name = :devName AND password = :password", nativeQuery = true)
    Optional<Developer3x> findByDevNameAndPassword(String devName, String password);


}

