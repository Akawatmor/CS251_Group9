package cs251.group9.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cs251.group9.backend.entity.*;
import cs251.group9.backend.service.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {
    
    // Autowired Services
    @Autowired private Customer1xService customer1xService;
    @Autowired private DeveloperService developerService;

    /////////////////// DTO ////////////////////
    //Login Request Body
    static class LoginRequest {
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
        
        //Get username and password from request
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        //Static Admin Login
        List<String> adminUsernames = List.of("SKOAdmin");
        List<String> adminPasswords = List.of("petchsko123");

        //Check if username and password are provided
        Customer1x cs = customer1xService.findByUNameAndPassword(username, password).orElse(null);
        Developer3x dev = developerService.findByDevNameAndPassword(username, password).orElse(null);

        // Check if username and password are provided
        if (username == null || password == null) {
            response.put("success", false);
            response.put("message", "Username and password are required");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Check if it's an admin
        if (adminUsernames.contains(username) && adminPasswords.contains(password)) {
            response.put("success", true);
            response.put("type", "admin");
            return ResponseEntity.ok(response);
        }
         
        // Check if it's a developer
        else if (dev != null) {
            response.put("success", true);
            response.put("type", "developer");
            response.put("id", dev.getDevID());
            return ResponseEntity.ok(response);
        }

        // Check if it's a customer
        else if (cs != null) {
            response.put("success", true);
            response.put("type", "customer");
            response.put("id", cs.getUserID());
            return ResponseEntity.ok(response);
        }

        // Neither customer nor developer found
        else {
            response.put("success", false);
            response.put("message", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}