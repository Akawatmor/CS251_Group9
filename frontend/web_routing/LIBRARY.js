const express = require('express');
const router = express.Router();
const path = require('path');

///// Function Route /////

router.get("/", (req, res) => {
    //Session OK. Load the page
    if (req.session.user) {
      res.sendFile(path.join(__dirname, "../public/LIBRARY.html"));
    } 
    //Session Error. Reload to Login
    else {
        res.status(401); // Set HTTP status 401 first
        res.sendFile(path.join(__dirname, "..", "public", "ERROR", "401Error_Re5_login_EN.html"));
    }
  });

module.exports = router;
