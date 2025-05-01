/*
 * Service For Order
 */
package cs251.group9.backend.service;

import cs251.group9.backend.entity.*;
import cs251.group9.backend.repository.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    @Autowired 
    private OrderRepository orderRepo;
    
    @Autowired 
    private Customer1xRepository customerRepo;
    
    @Autowired 
    private GameRepository gameRepo;
    
    @Autowired
    private PlayedRepository playedRepo;

    @Transactional
    public Order placeOrder(Long userID, Integer gameID, String receiptText) {
        // Find customer and game
        Customer1x customer = customerRepo.findByuserID(userID);
        Game game = gameRepo.findBygameID(gameID)
            .orElseThrow(() -> new RuntimeException("Game not found"));
        
        // Check if user already owns the game
        if (orderRepo.existsByUserIDAndGameID(userID, gameID)) {
            throw new RuntimeException("User already owns this game");
        }
        
        // Check if user has enough money
        if (customer.getMoney() < game.getgPrice()) {
            throw new RuntimeException("User does not have enough money");
        }
        
        // Deduct money from user
        Integer remainingMoney = customer.getMoney() - game.getgPrice();
        customer.setMoney(remainingMoney);
        customerRepo.save(customer);
        
        // Create order
        Order order = new Order();
        order.setCustomer(customer);
        order.setGame(game);
        order.setReceipt(receiptText);
        orderRepo.save(order);
        
        // Add game to user's played games
        PlayedId playedId = new PlayedId();
        playedId.setUserID(userID.intValue());
        playedId.setGameID(gameID);
        
        Played played = new Played();
        played.setId(playedId);
        played.setCustomer(customer);
        played.setGame(game);
        playedRepo.save(played);
        
        return order;
    }
    
    public boolean checkOwnership(Long userID, Integer gameID) {
        return orderRepo.existsByUserIDAndGameID(userID, gameID);
    }
}