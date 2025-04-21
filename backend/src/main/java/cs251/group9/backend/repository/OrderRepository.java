package cs251.group9.backend.repository;

import cs251.group9.backend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface OrderRepository extends JpaRepository<Order, String> {
    Optional<Order> findByCustomerUserIDAndOrderID(String userID, String orderID);
}
