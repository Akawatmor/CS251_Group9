/*
 * Customer Controller
 */
package cs251.group9.backend.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import cs251.group9.backend.entity.*;
import cs251.group9.backend.service.*;
import cs251.group9.backend.repository.*;

import java.util.*;

@RestController
@RequestMapping("/api/customers")
public class Customer1xController {

////////////////// Autowired Services and Repositories /////////////
    @Autowired private Customer1xRepository customer1xRepository; // Repository for customer data access
    @Autowired private Customer1xService customer1xService; // Service for customer operations
    @Autowired private PhotoService photoService; // Service for photo operations

/////////////////////// DTO ///////////////////

    //Login Request Body
    static class amountRequest {
        private int amount;
        
        public int getAmount() {
            return amount;
        }
        
        public void setAmount(int amount) {
            this.amount = amount;
        }
    }

////////////////////// Response /////////////////
/// 
/// 
    private String findObjectType(Object data) {
        if (data instanceof Customer1x || data instanceof List && ((List<?>) data).get(0) instanceof Customer1x) {
            return "Customer";
        } else if (data instanceof Game2x || data instanceof List && ((List<?>) data).get(0) instanceof Game2x) {
            return "Game";
        } else if (data instanceof Developer3x) {
            return "String";
        } else if (data instanceof String) {
            return "message";
        } else {
            return null;
        }
    }

    private ResponseEntity<Map<String, Object>> successResponse(Object data1) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);

        String o1 = findObjectType(data1);
        if (o1 != null) response.put(o1, data1);

        return ResponseEntity.ok(response);
    }
     private ResponseEntity<Map<String, Object>> successResponse(Object data1, Object data2) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);

        String o1 = findObjectType(data1); String o2 = findObjectType(data2);
        if (o1 != null) response.put(o1, data1); if (o2 != null) response.put(o2, data2);
        return ResponseEntity.ok(response);
    }
     private ResponseEntity<Map<String, Object>> successResponse(Object data1, Object data2, Object data3) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);

        String o1 = findObjectType(data1); String o2 = findObjectType(data2); String o3 = findObjectType(data3);
        if (o1 != null) response.put(o1, data1); if (o2 != null) response.put(o2, data2); if (o3 != null) response.put(o3, data3);

        return ResponseEntity.ok(response);
    }


    private ResponseEntity<Map<String, Object>> errorResponse(String cause, Integer causecode){
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", cause);

        switch (causecode) {
            case 1:
                response.put("error", "nodata");
                break;
            case 2:
                response.put("error", "not_found");
                break;
            case 3:
                response.put("error", "validation_error");
                break;
            case 4:
                response.put("error", "update_failed");
                break;
            case 5:
                response.put("error", "transaction_failed");
                break;
            default:
                response.put("error", "unknown_error");
        }
        return ResponseEntity.badRequest().body(response);
    }


////////////////////// Login //////////////
    // Use AuthenticationController Instead

///////////////////// Register /////////////
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Customer1x customer) {
        
        if (customer1xService.isUsernameTaken(customer.getuName())) {
            return errorResponse("Username is already taken", 3);
        }
        try {
            Customer1x registered = customer1xService.register(customer);
            return successResponse(registered);
        } catch (IllegalArgumentException e) {
            return errorResponse(e.getMessage(), 3);
        }
    }

///////////////////// Check Customer by ID /////////////////////
    public Customer1x findCustomer1xById(Long userId) {
        return customer1xRepository.findByuserID(userId);
    }

///////////////////////// Get Customer by ID /////////////////////////
    @GetMapping("/id={userId}")
    public ResponseEntity<Map<String, Object>> getCustomerById(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Customer1x customer = customer1xService.getCustomerById(userId);
            if (customer == null) return errorResponse("No customer found", 2);
            return successResponse(customer);
        } catch (RuntimeException e) {
            return errorResponse("Runtime Error", 99);
        }
    }

///////////////////////// Get All Customers /////////////////////////
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllCustomers() {
        
        try {
            List<Customer1x> customers = customer1xService.getAllCustomers();
            if (customers.isEmpty()) {
                return errorResponse("No customers found", 1);
            }
            return successResponse(customers);
        } catch (RuntimeException e) {
            return errorResponse("Error retrieving customers: " + e.getMessage(), 1);
        }
    }

///////////////////////// Delete Customer by CustomerID /////////////////////////
    @DeleteMapping("/id={userId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long userId) {
    
        // Check if the customer exists
        if(findCustomer1xById(userId) == null) {
            return errorResponse("Customer not found", 2);
        }
    
        try{
            customer1xService.deleteCustomer(userId);
            return successResponse("Customer deleted successfully");
        } catch (IllegalArgumentException e) {
            return errorResponse("Error deleting customer: " + e.getMessage(), 99);
        }

        
    
    }

///////////////////////// Update Customer Profile by CustomerID /////////////////////////
    @PutMapping("/id={userId}")
    public ResponseEntity<Map<String, Object>> updateProfile(@PathVariable Long userId, @RequestBody Customer1x updated) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Customer1x updatedCustomer = customer1xService.updateProfile(userId, updated);
            response.put("success", true);
            response.put("message", "Profile updated successfully");
            response.put("user", updatedCustomer);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            response.put("error", "update_failed");
            return ResponseEntity.badRequest().body(response);
        }
    }

////////////////////// Add Money to Customer Account /////////////////////////
    @PostMapping("/id={userId}/money")
    public ResponseEntity<Map<String, Object>> addMoney(@PathVariable Long userId, @RequestBody amountRequest am) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validate amount is positive
            if (am.getAmount() <= 0) {
                response.put("success", false);
                response.put("message", "Amount must be greater than 0");
                response.put("error", "invalid_amount");
                return ResponseEntity.badRequest().body(response);
            }
            
            Customer1x customer = customer1xService.getCustomerById(userId);
            if (customer != null) {
                customer.setMoney(customer.getMoney() + am.getAmount());
                customer1xRepository.save(customer);
                
                response.put("success", true);
                response.put("message", "Money added successfully");
                response.put("money", customer.getMoney());
                response.put("user", customer);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "User not found");
                response.put("error", "not_found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", "Error adding money: " + e.getMessage());
            response.put("error", "transaction_failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

/////////////////////// Upload and Get Customer Profile Photo /////////////////////////
    @PostMapping("/id={userId}/photo")
    public ResponseEntity<Map<String, Object>> uploadPhoto(@PathVariable Long userId, 
                                              @RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String filePath = photoService.storePhoto(userId, file);
            response.put("success", true);
            response.put("message", "Photo uploaded successfully");
            response.put("photoPath", filePath);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to upload: " + e.getMessage());
            response.put("error", "upload_failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
///////////////////// Get Customer Profile Photo /////////////////////////
    @GetMapping("/id={userId}/photo")
    public ResponseEntity<?> getPhotoPath(@PathVariable Long userId) {
        try {
            byte[] photoData = photoService.getPhotoData(userId);
            return ResponseEntity
                .ok()
                .contentType(org.springframework.http.MediaType.IMAGE_JPEG)
                .body(photoData);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            response.put("error", "photo_retrieval_failed");
            return ResponseEntity.badRequest().body(response);
        }
    }

    

    
}

