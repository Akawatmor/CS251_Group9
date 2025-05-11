const express = require('express');
const router = express.Router();
const path = require('path');
//const { isCustomer } = require('../middleware/authMiddleware');

///// Function Route /////

router.get("/", (req, res) => {
   if (req.session.user) {
    // Session is already verified as a customer by middleware
    res.sendFile(path.join(__dirname, "../public/LIBRARY.html"));
   }
    else {
            res.status(401); // Set HTTP status 401 first
            res.sendFile(path.join(__dirname, "..", "public", "SERVICE", "401Error_Re5_FBD_EN.html"));
    }
});

module.exports = router;
