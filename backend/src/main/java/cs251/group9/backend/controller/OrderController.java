/*
 * Order Controller
 */
package cs251.group9.backend.controller;


import cs251.group9.backend.entity.*;
import cs251.group9.backend.repository.*;
import cs251.group9.backend.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final GameRepository gameRepository;
    
    // Inner DTO class for receipt request
    public static class ReceiptRequest {
        private String receipt;
        
        public String getReceipt() {
            return receipt;
        }
        
        public void setReceipt(String receipt) {
            this.receipt = receipt;
        }
    }
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private OrderRepository orderRepo;

    OrderController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
    
/////////////////// Create Receipt ////////////////////
/// Todo -> Handle Game and Customer Money be Null
    @PostMapping("/user={userID}/game={gameID}")
    public ResponseEntity<?> buyGame(@PathVariable Long userID,
                                       @PathVariable Long gameID, 
                                       @RequestBody ReceiptRequest receiptRequest) {

        HashMap<String, Object> response = new HashMap<>();

        //Check if there is a game with the given ID
        if (gameRepository.findById(gameID).isEmpty()) {
            response.put("message", "Game not found.");
            response.put("success", false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        //Check if the user already owns the game
        if (orderRepo.countByUserIDAndGameID(userID, gameID) > 0) {
            response.put("message", "User already owns the game.");
            response.put("success", false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        
        try {
            Order3x order = orderService.placeOrder(userID, gameID, receiptRequest.getReceipt());
            response.put("message", "Order placed successfully.");
            response.put("orderID", order.getOrderID());
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            
            response.put("message", "Error placing order: " + e.getMessage());
            response.put("success", false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

///////////////////// Get Receipt ////////////////////
    @GetMapping("/user={userID}/order={OrderID}")
    public ResponseEntity<String> getReceipt(@PathVariable Long userID, @PathVariable Long OrderID) {
        return orderRepo.findByCustomerUserIDAndOrderID(userID, OrderID)
                .map(order -> ResponseEntity.ok(order.getReceipt()))
                .orElse(ResponseEntity.notFound().build());
    }
    
///////////////////// Get All Orders ////////////////////
    @GetMapping("/user={userId}")
    public ResponseEntity<List<Order3x>> getUserOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(orderRepo.findByCustomerUserID(userId));
    }
    
  //////////////////// Check if user owns game ////////////////////
    @GetMapping("/user={userID}/game={gameID}/own")
    public ResponseEntity<?> checkGameOwnership(@PathVariable Long userID, @PathVariable Long gameID) {
        HashMap<String, Object> response = new HashMap<>();
        boolean ownsGame = orderService.checkOwnership(userID, gameID);
        if (!ownsGame) {
            response.put("message", "User does not own the game.");
            response.put("success", false);
            return ResponseEntity.status(200).body(response);
        }
        response.put("receipt", orderRepo.findByUserIDAndGameID(userID, gameID).get().getReceipt());
        response.put("success", true);
        return ResponseEntity.ok(response);
    }
}