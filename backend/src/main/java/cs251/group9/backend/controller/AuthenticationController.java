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
    
    @Autowired
    private Customer1xService customer1xService;

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
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        Map<String, Object> response = new HashMap<>();
        
        // Extract username and password from the request body
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // Use the service to authenticate
        if (customer1xService.findByUNameAndPassword(username, password).isPresent()) {
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("type", "user");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}