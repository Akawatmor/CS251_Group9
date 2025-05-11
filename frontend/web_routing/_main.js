const express = require('express');
const router = express.Router();
const path = require('path');

// Import route modules
const homeRoutes = require('./HOMEPAGE');
const loginRoutes = require('./LOGIN');
const registerRoutes = require('./REGISTER');
const libraryRoutes = require('./LIBRARY');
const csettingRoutes = require('./CSETTING');
const buypageRoutes = require('./BUYPAGE');
const logoutRoutes = require('./LOGOUT');
const paymentRoutes = require('./PAYMENT');

// Mount routes
router.use('/home', homeRoutes);
router.use('/login', loginRoutes);
router.use('/register', registerRoutes);
router.use('/library', libraryRoutes);
router.use('/csetting', csettingRoutes);
router.use('/buypage', buypageRoutes);
router.use('/logout', logoutRoutes);
router.use('/payment', paymentRoutes);

// Handle root route
router.get('/', (req, res) => {
    res.redirect('/login');
});

module.exports = router;