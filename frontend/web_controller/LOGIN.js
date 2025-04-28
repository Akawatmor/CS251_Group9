const express = require('express');
const router = express.Router();
const path = require('path');
const qrcode = require("qrcode");

///// Function Route /////

const USER = "Example";
const PASS = "Password";

router.post("/authen", (requ, resp) => {
    const {user, pass} =(requ.body);
    
    //Match credential => redirect
    if(user == USER && pass == PASS){
        requ.session.user = user;
        resp.json({ success: true });
    }
    else if (user == "" && pass == ""){
        resp.json({ success: false, message: 'Blank Input' });
    }
    else if (user == USER && pass == ""){
        resp.json({ success: false, message: 'Password Cannot be Blank!' });
    }
    else if (user == USER && pass != PASS){
        resp.json({ success: false, message: 'Invalid Password!' });
    }
    //else Show on
    else{
        resp.json({ success: false, message: 'No Username Exist!' });
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



router.all("/", (requ, resp) =>{
    if(requ.method != "POST"){
        resp.status(405).send(`
        <h1>Error! Method Not Allowed</h1>
        <a href="/">Go Back to Login Page</a>
        `);
    }

    });

module.exports = router;