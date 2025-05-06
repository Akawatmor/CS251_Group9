/*
 * Service For Order
 */
package cs251.group9.backend.service;

import cs251.group9.backend.entity.*;
import cs251.group9.backend.repository.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    /**
     * Place a new order for a game
     * @param userID Customer's ID
     * @param gameID Game's ID
     * @param receiptText Receipt text for the order
     * @return The created order
     * @throws RuntimeException if game not found, already owned, or insufficient funds
     */
    @Transactional
    public Order3x placeOrder(Long userID, Long gameID, String receiptText) {
        // Validate parameters
        if (userID == null || gameID == null) {
            throw new IllegalArgumentException("User ID and Game ID cannot be null");
        }
        
        // Find customer
        Customer1x customer = customerRepo.findByuserID(userID);
        if (customer == null) {
            System.out.println("Customer not found");
            throw new RuntimeException("Customer not found");
        }
        
        // Find game
        Game2x game = gameRepo.findBygameID(gameID).orElse(null);

        if (game == null) {
            System.out.println("Game not found");
            throw new RuntimeException("Game not found");
        }
        
        // Check if user already owns the game
        if (checkOwnership(userID, gameID)) {
            System.out.println("User already owns this game");
            throw new RuntimeException("User already owns this game");
            
        }
        
        // Check if user has enough money
        if (customer.getMoney() < game.getgPrice()) {
            System.out.println("Not enough money to purchase the game");
            throw new RuntimeException("Insufficient funds: Need " + 
                (game.getgPrice() - customer.getMoney()) + " more to purchase");
        }
        
        // Deduct money from user
        Integer remainingMoney = customer.getMoney() - game.getgPrice();
        customer.setMoney(remainingMoney);
        customerRepo.save(customer);
        
        // Create order
        Order3x order = new Order3x();
        order.setCustomer(customer);
        order.setGame(game);
        order.setReceipt(receiptText != null ? receiptText : "Order #" + System.currentTimeMillis());
        orderRepo.save(order);
        
        // Add game to user's played games
        addToPlayedGames(customer, game);
        
        return order;
    }
    
    /**
     * Check if a customer owns a particular game
     * @param userID Customer's ID
     * @param gameID Game's ID
     * @return true if customer owns the game, false otherwise
     */
    public boolean checkOwnership(Long userID, Long gameID) {
        if (userID == null || gameID == null) {
            return false;
        }
        // Make sure the repository method returns a boolean
        // If the query actually returns a Long count, we convert it to boolean
        Long count = orderRepo.countByUserIDAndGameID(userID, gameID);
        return count != null && count > 0;
    }
    
    /**
     * Get all orders for a customer
     * @param userID Customer's ID
     * @return List of orders
     */
    public List<Order3x> getCustomerOrders(Long userID) {
        if (userID == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return orderRepo.findByCustomerUserID(userID);
    }
    
    /**
     * Get a specific order by customer ID and order ID
     * @param userID Customer's ID
     * @param orderID Order's ID
     * @return Optional containing the order if found
     */
    public Optional<Order3x> getOrderById(Long userID, Long orderID) {
        if (userID == null || orderID == null) {
            return Optional.empty();
        }
        return orderRepo.findByCustomerUserIDAndOrderID(userID, orderID);
    }
    
    /**
     * Add a game to customer's played games
     * @param customer Customer entity
     * @param game Game entity
     */
    private void addToPlayedGames(Customer1x customer, Game2x game) {
        PlayedId playedId = new PlayedId();
        playedId.setUserID(customer.getUserID());
        playedId.setGameID(game.getGameID());
        
        Played played = new Played();
        played.setId(playedId);
        played.setCustomer(customer);
        played.setGame(game);
        playedRepo.save(played);
    }
}