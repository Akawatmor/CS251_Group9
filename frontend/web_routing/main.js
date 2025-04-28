const express = require('express');
const router = express.Router();
const path = require('path');

///// Function Route /////
const HOMEPAGE = require("./HOMEPAGE");
const LIBRARY = require("./LIBRARY");
const LOGOUT = require("./LOGOUT");
const LOGIN = require("./LOGIN");

router.use("/home", HOMEPAGE);
router.use("/library", LIBRARY);
router.use("/logout", LOGOUT);
router.use(LOGIN);


module.exports = router;