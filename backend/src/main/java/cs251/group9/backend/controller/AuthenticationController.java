package cs251.group9.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cs251.group9.backend.entity.Customer1x;
import cs251.group9.backend.service.Customer1xService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {
    
    /////////////////// DTO ////////////////////
    //Login Request Body
    class LoginRequest {
        private String username;
        private String password;
        
        // Getters and Setters
        public String getUsername() {return username;}
        public String getPassword()  {return password;}

        public void setUsername(String uName) {this.username = uName;}
        public void setPassword(String password) {this.password = password;}

    }
    
    /////////////////// Login Authentication Check ////////////////////////
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody String username, @RequestBody String password) {
        Map<String, Object> response = new HashMap<>();
        
        // Check if username and password are provided
            Customer1xController C1xC = new Customer1xController();
            if (C1xC.findByUNameAndPassword(username, password).isPresent()) {
                System.out.print("TEST");

            return null;
            
            /*
            
            throw new IllegalArgumentException("Invalid username or password");

            //return type user if find true
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("type", "user");
            return ResponseEntity.ok(response);

            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
            */
    }
}