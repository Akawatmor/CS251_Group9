const express = require('express');
const router = express.Router();
const path = require('path');

// Import controller modules
const loginController = require('./LOGIN');
const homeController = require('./HOMEPAGE');
const buyPageController = require('./BUYPAGE');

// Mount controllers
router.use('/login', loginController);
router.use('/home', homeController);
router.use('/buypage', buyPageController);

// Default handler for unmatched routes
router.all('*', (req, res) => {
    res.status(404).json({
        success: false,
        message: 'API endpoint not found'
    });
});

module.exports = router;


