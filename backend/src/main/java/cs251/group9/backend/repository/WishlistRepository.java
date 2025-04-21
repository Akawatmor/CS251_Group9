package cs251.group9.backend.repository;

import cs251.group9.backend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WishlistRepository extends JpaRepository<Wishlist, WishlistId> {
	
}
