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
    
/////////////////// Create Receipt ////////////////////
/// Todo -> Handle Game and Customer Money be Null
    @PostMapping("/user={userID}/game={gameID}/receipt={receipt}")
    public ResponseEntity<Order3x> buyGame(@PathVariable Long userID,
                                       @PathVariable Long gameID, 
                                       @PathVariable String receipt) {
        try {
            Order3x order = orderService.placeOrder(userID, gameID, receipt);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
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
    public ResponseEntity<Boolean> checkGameOwnership(@PathVariable Long userID, @PathVariable Long gameID) {
        boolean ownsGame = orderService.checkOwnership(userID, gameID);
        return ResponseEntity.ok(ownsGame);
    }
}