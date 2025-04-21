package cs251.group9.backend.service;

import cs251.group9.backend.entity.*;
import cs251.group9.backend.repository.*;
import cs251.group9.backend.service.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired public OrderRepository orderRepo;
    @Autowired private CustomerRepository customerRepo;
    @Autowired private GameRepository gameRepo;
    @Autowired private PlayedRepository playedRepo;

    public Order placeOrder(String orderID, String userID, String gameID, String receipt) {
        Customer customer = customerRepo.findById(userID).orElseThrow();
        Game game = gameRepo.findById(gameID).orElseThrow();

        //Not Enough Money
        if (customer.getMoney().compareTo(game.getgPrice()) < 0) {
            throw new RuntimeException("Insufficient funds");
        }
        
        //Deduct Money
        Integer Moneyset = customer.getMoney() - game.getgPrice();
        customer.setMoney(Moneyset);
        customerRepo.save(customer);

        Order order = new Order();
        order.setOrderID(orderID);
        order.setCustomer(customer);
        order.setGame(game);
        order.setReceipt(receipt);
        orderRepo.save(order);

        Played played = new Played();
        PlayedId pid = new PlayedId();
        pid.setUserID(userID);
        pid.setGameID(gameID);
        played.setId(pid);
        played.setCustomer(customer);
        played.setGame(game);
        playedRepo.save(played);

        return order;
    }
}
