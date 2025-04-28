
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
      resp.status(405).send(`
        <h1>Error! Method Not Allowed</h1>
        <a href="/">Go Back to Login Page</a>
        `);
    }

  });

module.exports = router;