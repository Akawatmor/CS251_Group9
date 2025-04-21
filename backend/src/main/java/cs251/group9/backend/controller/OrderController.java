package cs251.group9.backend.controller;


import cs251.group9.backend.entity.*;
import cs251.group9.backend.repository.*;
import cs251.group9.backend.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired private OrderService orderService;

    @PostMapping("/buy")
    public ResponseEntity<Order> buyGame(@RequestParam String orderID, @RequestParam String userID,
                                         @RequestParam String gameID, @RequestParam String receipt) {
        try {
            Order order = orderService.placeOrder(orderID, userID, gameID, receipt);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/receipt")
    public ResponseEntity<String> getReceipt(@RequestParam String userID, @RequestParam String orderID) {
        return orderService.orderRepo.findByCustomerUserIDAndOrderID(userID, orderID)
                .map(order -> ResponseEntity.ok(order.getReceipt()))
                .orElse(ResponseEntity.notFound().build());
    }
}