/*
* Nodejs Web Controller
*
* Main API router for the web application
* This file serves as the entry point for all web-related routes.
*/
const express = require('express');
const router = express.Router();
const path = require('path');

// Import controller modules
const loginController = require('./LOGIN');
const homeController = require('./HOMEPAGE');
const buyPageController = require('./BUYPAGE');
const libraryController = require('./LIBRARY');
const csettingController = require('./CSETTING');
const logoutController = require('./LOGOUT');
const paymentController = require('./PAYMENT');
const registerController = require('./REGISTER');
const useraccountController = require('./USERACCOUNT');
const staffpageController = require('./STAFFPAGE');
const devpageController = require('./DEVPAGE');


// Mount controllers
router.use('/login', loginController);
router.use('/home', homeController);
router.use('/buypage', buyPageController);
router.use('/library', libraryController);
router.use('/csetting', csettingController);
router.use('/logout', logoutController);
router.use('/payment', paymentController);
router.use('/register', registerController);
router.use('/useraccount', useraccountController);
router.use('/staffpage', staffpageController);
router.use('/devpage', devpageController);


// Default handler for unmatched routes
router.all('*', (req, res) => {
    res.status(404).json({
        success: false,
        message: 'API endpoint not found'
    });
});

module.exports = router;


