const express = require('express');
const router = express.Router();
const path = require('path');

///// Function Route /////

router.get("/", (req, res) => {

    res.sendFile(path.join(__dirname, "../public/REGISTER.html"));

    

  });

module.exports = router;