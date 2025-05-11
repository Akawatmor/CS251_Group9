const express = require('express');
const router = express.Router();
const path = require('path');

///// Function Route /////

router.get("/", (req, res) => {
    // Clear the user session
    req.session.destroy((err) => {
        if (err) {
            console.error("Error destroying session:", err);
        }
        // Redirect to login page
        else{
          res.status(401); // Set HTTP status 401 first
          res.sendFile(path.join(__dirname, "..", "public", "SERVICE", "200Logout_Re5_login_EN.html"));
        }
        
    });
});

module.exports = router;