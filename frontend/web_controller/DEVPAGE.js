const express = require('express');
const router = express.Router();
const path = require('path');

///// Function Route /////

// Developer-specific API endpoints could be added here
router.get("/profile", async (req, res) => {
    try {
        // Get developer ID from session
        const devId = req.session.user?.id;
        
        if (!devId) {
            return res.status(401).json({
                success: false,
                message: 'Not authenticated as a developer'
            });
        }
        
        // Here you would fetch developer profile from your API
        // For now just return session data as an example
        res.json({
            success: true,
            profile: {
                id: devId,
                username: req.session.user.username,
                type: req.session.user.type
            }
        });
    } catch (error) {
        console.error('Error fetching developer profile:', error);
        res.status(500).json({
            success: false,
            message: 'Failed to fetch developer profile'
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