package cs251.group9.backend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import cs251.group9.backend.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByUNameAndUEmail(String uName, String uEmail);
    Optional<Customer> findByUName(String uName);
    Optional<Customer> findByuserId(String userId);
}