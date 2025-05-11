const express = require('express');
const router = express.Router();
const path = require('path');

///// Function Route /////

// Admin-specific API endpoints
router.get("/dashboard", async (req, res) => {
    try {
        // Get admin ID from session
        const adminId = req.session.user?.id;
        
        if (!adminId) {
            return res.status(401).json({
                success: false,
                message: 'Not authenticated as an admin'
            });
        }
        
        // Here you would fetch admin dashboard data from your API
        // For now just return session data as an example
        res.json({
            success: true,
            admin: {
                id: adminId,
                username: req.session.user.username,
                type: req.session.user.type
            },
            // Example dashboard data
            stats: {
                users: 145,
                developers: 12,
                games: 67,
                sales: 1245
            }
        });
    } catch (error) {
        console.error('Error fetching admin dashboard data:', error);
        res.status(500).json({
            success: false,
            message: 'Failed to fetch admin dashboard data'
        });
    }
});

router.all("/", (req, res) => {
    if (req.method != "GET") {
        res.status(405); // Method Not Allowed
        res.sendFile(path.join(__dirname, "..", "public", "ERROR", "405Error_Re5_MNA_EN.html"));
    }
});

module.exports = router;