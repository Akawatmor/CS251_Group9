const express = require('express');
const router = express.Router();
const path = require('path');
const qrcode = require("qrcode");
const LoginService = require('../springmiddleware/LOGIN');

///// Function Route /////

router.post("/authen", async (requ, resp) => {
    const {user, pass} = requ.body;
    
    // Input validation
    if (user == "" && pass == "") {
        return resp.json({ success: false, message: 'Blank Input' });
    }
    
    if (user && pass == "") {
        return resp.json({ success: false, message: 'Password Cannot be Blank!' });
    }
    
    // Call the authentication service
    try {
        const authResult = await LoginService.authenticate(user, pass);
        
        if (authResult.success) {
            // Set session based on user type
            requ.session.user = {
                id: authResult.id,
                username: user,
                type: authResult.type
            };
            
            // Return user type and redirect URL for client-side redirect
            let redirectUrl = '/home'; // Default for customer
            
            if (authResult.type === 'developer') {
                redirectUrl = '/devpage';
            } else if (authResult.type === 'admin') {
                redirectUrl = '/staffpage';
            }
            
            return resp.json({
                success: true,
                id: authResult.id,
                type: authResult.type,
                redirectUrl: redirectUrl
            });
        }
        
        // Return the result from the authentication service
        return resp.json(authResult);
    } catch (error) {
        console.error('Authentication error:', error);
        return resp.json({ 
            success: false, 
            message: 'An error occurred during authentication.' 
        });
    }
});

router.get("/qrcode-login", (req, res) => {

    let qrCodeSession = null;

  // Generate a unique session token (in real-world, this would be a secure session)
  const currentDate = new Date();
  qrCodeSession = "https://shorturl.asia/oGR7p";
  
  // Create QR code that contains the session token
  qrcode.toDataURL(qrCodeSession, (err, url) => {
    if (err) {
      return res.status(500).send("Error generating QR code");
    }
    res.json({ qrCode: url }); // Send QR code data URL to client
  });
});

// Add a route to redirect to registration page
router.get("/register", (req, res) => {
    res.redirect("/register");
});

router.all("/", (requ, resp) =>{
    if(requ.method != "POST"){
        resp.status(405); // Set HTTP status 405 first
        resp.sendFile(path.join(__dirname, "..", "public", "ERROR", "405Error_Re5_MNA_EN.html"));
    }
});

module.exports = router;