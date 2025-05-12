const express = require('express');
const router = express.Router();
const path = require('path');

///// Function Route /////

router.get("/", (req, res) => {
    // Check if the request has game and user IDs
    const gameId = req.query.gameId;
    const receipt = req.query.receipt;
    
    if (!gameId || !receipt) {
        return res.status(400).send('Missing required parameters');
    }

    // Check for user session
    if (req.session.user) {
        res.sendFile(path.join(__dirname, "../public/SERVICE/PAYMENT_SUCCESS.html"));
    } 
    // Session Error. Reload to Login
    else {
        res.status(401); // Set HTTP status 401 first
        res.sendFile(path.join(__dirname, "..", "public", "SERVICE", "401Error_Re5_FBD_EN.html"));
    }
});

module.exports = router;