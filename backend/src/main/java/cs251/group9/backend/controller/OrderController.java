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

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private OrderRepository orderRepo;
    
    // Buy Game
    @PostMapping("/buy")
    public ResponseEntity<Order3x> buyGame(@RequestParam Long userID,
                                       @RequestParam Long gameID, 
                                       @RequestParam String receipt) {
        try {
            Order3x order = orderService.placeOrder(userID, gameID, receipt);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Get receipt
    @GetMapping("/receipt")
    public ResponseEntity<String> getReceipt(@RequestParam Long userID, @RequestParam Integer orderID) {
        return orderRepo.findByCustomerUserIDAndOrderID(userID, orderID)
                .map(order -> ResponseEntity.ok(order.getReceipt()))
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Get all user orders
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order3x>> getUserOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(orderRepo.findByCustomerUserID(userId));
    }
    
    // Check if user owns a game
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkGameOwnership(@RequestParam Long userID, @RequestParam Long gameID) {
        boolean ownsGame = orderService.checkOwnership(userID, gameID);
        return ResponseEntity.ok(ownsGame);
    }
}