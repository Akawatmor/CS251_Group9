const express = require('express');
const router = express.Router();
const path = require('path');

///// Function Route /////

router.get("/login", (req, res) => {
    // If user is already logged in, redirect based on user type
    if (req.session.user) {
        switch (req.session.user.type) {
            case 'developer':
                return res.redirect("/devpage");
            case 'admin':
                return res.redirect("/staffpage");
            case 'customer':
            default:
                return res.redirect("/home");
        }
    }

    // If not logged in, redirect to login page
    res.redirect("/");
});

module.exports = router;