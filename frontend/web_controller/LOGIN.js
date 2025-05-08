const express = require('express');
const router = express.Router();
const path = require('path');
const qrcode = require("qrcode");
const LoginService = require('../springmiddleware/LOGIN');

///// Function Route /////

// Remove hardcoded credentials as they will now be verified by the backend
// const USER = "Example";
// const PASS = "Password";

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
            // Set session on successful login
            requ.session.user = user;
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
  qrCodeSession = currentDate.toString();
  
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
        resp.status(405).send(`
        <h1>Error! Method Not Allowed</h1>
        <a href="/">Go Back to Login Page</a>
        `);
    }

});

module.exports = router;