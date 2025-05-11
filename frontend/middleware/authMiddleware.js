/**
 * Authentication middleware for protecting routes based on user type
 */

// Middleware to check if user is authenticated
const isAuthenticated = (req, res, next) => {
    if (req.session.user) {
        return next();
    }
    
    res.status(401); // Unauthorized
    res.sendFile(path.join(__dirname, "..", "public", "ERROR", "401Error_Re5_FBD_EN.html"));
};

// Middleware to check if user is a customer
const isCustomer = (req, res, next) => {
    if (req.session.user && req.session.user.type === 'customer') {
        return next();
    }
    
    if (req.session.user) {
        // User is logged in but not a customer
        res.status(403); // Forbidden
        res.sendFile(path.join(__dirname, "..", "public", "ERROR", "403Error_Forbidden_EN.html"));
    } else {
        // User is not logged in
        res.status(401); // Unauthorized
        res.sendFile(path.join(__dirname, "..", "public", "ERROR", "401Error_Re5_FBD_EN.html"));
    }
};

// Middleware to check if user is a developer
const isDeveloper = (req, res, next) => {
    if (req.session.user && req.session.user.type === 'developer') {
        return next();
    }
    
    if (req.session.user) {
        // User is logged in but not a developer
        res.status(403); // Forbidden
        res.sendFile(path.join(__dirname, "..", "public", "ERROR", "403Error_Forbidden_EN.html"));
    } else {
        // User is not logged in
        res.status(401); // Unauthorized
        res.sendFile(path.join(__dirname, "..", "public", "ERROR", "401Error_Re5_FBD_EN.html"));
    }
};

// Middleware to check if user is an admin
const isAdmin = (req, res, next) => {
    if (req.session.user && req.session.user.type === 'admin') {
        return next();
    }
    
    if (req.session.user) {
        // User is logged in but not an admin
        res.status(403); // Forbidden
        res.sendFile(path.join(__dirname, "..", "public", "ERROR", "403Error_Forbidden_EN.html"));
    } else {
        // User is not logged in
        res.status(401); // Unauthorized
        res.sendFile(path.join(__dirname, "..", "public", "ERROR", "401Error_Re5_FBD_EN.html"));
    }
};

module.exports = {
    isAuthenticated,
    isCustomer,
    isDeveloper,
    isAdmin
};
