const express = require('express');
const router = express.Router();
const path = require('path');


///// Function Route /////
const HOMEPAGE = require("./HOMEPAGE");
const LOGIN = require("./LOGIN");
router.use("/home", HOMEPAGE);
router.use("/login", LOGIN);




module.exports = router;


