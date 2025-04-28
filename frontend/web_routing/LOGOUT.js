const express = require('express');
const router = express.Router();
const path = require('path');

///// Function Route /////

router.get("/", (req, res) => {

    if (req.session.user){
        req.session.destroy(() => {
            res.status(200); // Set HTTP status 200 first
            res.sendFile(path.join(__dirname, "..", "public", "SERVICE", "200Logout_Re5_login_EN.html"));
          });
    }
    else{
        res.redirect("/");
    }
    

  });

module.exports = router;