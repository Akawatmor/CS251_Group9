
const express = require('express');
const router = express.Router();
const path = require('path');

///// Function Route /////

const USER = "Example";
const PASS = "Password";

router.post("/", (requ, resp) => {

    

  
});

router.all("/", (requ, resp) =>{
    if(requ.method != "POST"){
      resp.status(405); // Set HTTP status 405 first
      resp.sendFile(path.join(__dirname, "..", "public", "ERROR", "405Error_Re5_MNA_EN.html"));
    }

  });

module.exports = router;