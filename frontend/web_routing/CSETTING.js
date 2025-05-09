const express = require('express');
const router = express.Router();
const path = require('path');

///// Function Route /////

router.get("/", (req, res) => {
    //Session OK. Load the page
    if (req.session.user) {
      res.sendFile(path.join(__dirname, "../public/USERACCOUNT.html"));
    } 
    //Session Error. Reload to Login
    else {
        res.status(401); // Set HTTP status 401 first
        res.sendFile(path.join(__dirname, "..", "public", "SERVICE", "401Error_Re5_FBD_EN.html"));
    }
  });

module.exports = router;
