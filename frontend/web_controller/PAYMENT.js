const express = require('express');
const router = express.Router();
const { PaymentService } = require('../springmiddleware/PAYMENT');

// Confirm payment and create order in the backend
router.post("/confirm", async (req, res) => {
    try {
        const { userId, gameId, receiptInfo } = req.body;
        
        if (!userId || !gameId || !receiptInfo) {
            return res.status(400).json({
                success: false,
                message: 'User ID, Game ID and receipt information are required'
            });
        }
        
        // Create the order in the backend
        const orderResult = await PaymentService.createOrder(userId, gameId, receiptInfo);
        
        return res.json({
            success: true,
            message: 'Purchase confirmed successfully',
            data: orderResult
        });
    } catch (error) {
        console.error('Error confirming payment:', error);
        return res.status(500).json({
            success: false,
            message: 'Failed to confirm payment and create order'
        });
    }
});

// Check if a user owns a specific game
router.get("/ownership/:userId/:gameId", async (req, res) => {
    try {
        const userId = req.params.userId;
        const gameId = req.params.gameId;
        
        if (!userId || !gameId) {
            return res.status(400).json({
                success: false,
                message: 'User ID and Game ID are required'
            });
        }
        
        const ownsGame = await PaymentService.checkGameOwnership(userId, gameId);
        
        return res.json({
            success: true,
            ownsGame: ownsGame
        });
    } catch (error) {
        console.error('Error checking game ownership:', error);
        return res.status(500).json({
            success: false,
            message: 'Failed to check game ownership',
            ownsGame: false
        });
    }
});

module.exports = router;