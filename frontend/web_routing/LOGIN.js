const express = require('express');
const router = express.Router();
const path = require('path');

///// Function Route /////

router.get("/login", (req, res) => {

        res.redirect("/");
    }
    
);

module.exports = router;