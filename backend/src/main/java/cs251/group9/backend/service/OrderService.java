/*
 * Service For Order
 */
package cs251.group9.backend.service;

import cs251.group9.backend.entity.*;
import cs251.group9.backend.repository.*;
import cs251.group9.backend.service.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
/*
@Service
public class OrderService {
    @Autowired public OrderRepository orderRepo;
    @Autowired private CustomerRepository customerRepo;
    @Autowired private GameRepository gameRepo;
    @Autowired private PlayedRepository playedRepo;
    
    //Repository Entity Search
    @Autowired
    private CustomerRepository CR;
    private GameRepository GR;
    private PlayedRepository PR;
    private OrderRepository OR;

    public Order placeOrder(Integer orderID, Integer userID, Integer gameID, String receiptText) {
    	
    	Customer CS = CR.findByuserID(userID);
    	Game GM = GR.findBygameID(gameID).orElse(null);
    	
    	//Not Enought Money To Buy Game
    	if(CS.getMoney() < GM.getgPrice()) {
    		throw new RuntimeException("User Money is not Enough");
    		//return null;
    	}
    	
    	//User Having Enough money -> deduct the money from the user
    	Integer RemainMoney = CS.getMoney() - GM.getgPrice();
    	
    	//Resave money to user
    	CS.setMoney(RemainMoney);
    	CR.save(CS);
    	
    	//Making New Order Information
    	Order OD = new Order();
    	OD.setCustomer(CS);
    	OD.setGame(GM);
    	OD.setReceipt(receiptText);
    	OD.setOrderID(orderID); //TODO -> Find the way to auto generate ID
    	OR.save(OD);
    	
    	
    	//Played Repo
    	//TODO -> Find the way to add played game
    	
    	
    	
    	
    	return OD;
    }
    
}*/