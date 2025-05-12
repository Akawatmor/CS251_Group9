const express = require('express');
const router = express.Router();
const { BuyPageService } = require('../springmiddleware/BUYPAGE');

///// Function Route /////

// Get game details by ID
router.get("/details/:id", async (req, res) => {
    try {
        const gameId = req.params.id;
        
        if (!gameId) {
            return res.status(400).json({
                success: false,
                message: 'Game ID is required'
            });
        }
        
        const gameDetails = await BuyPageService.getGameById(gameId);
        return res.json(gameDetails);
    } catch (error) {
        console.error('Error fetching game details:', error);
        return res.status(500).json({
            success: false,
            message: 'Failed to fetch game details'
        });
    }
});

// Get game images by ID and type
router.get("/image/:id/:type", async (req, res) => {
    try {
        const gameId = req.params.id;
        let pictureId;
        
        // Handle main image vs. additional images
        if (req.params.type === 'main') {
            pictureId = 1;  // Banner is picture1
        } else {
            // Extract number from pictureX
            pictureId = req.params.type.replace('picture', '');
        }
        
        if (!gameId || !pictureId) {
            return res.status(400).json({
                success: false,
                message: 'Game ID and image type are required'
            });
        }
        
        const imageData = await BuyPageService.getGameImage(gameId, pictureId);
        
        if (!imageData || !imageData.data) {
            return res.status(404).json({
                success: false,
                message: 'Image not found'
            });
        }
        
        // Set content type based on the data returned
        res.setHeader('Content-Type', imageData.contentType || 'image/jpeg');
        res.send(imageData.data);
    } catch (error) {
        console.error('Error fetching game image:', error);
        return res.status(500).json({
            success: false,
            message: 'Failed to fetch game image'
        });
    }
});

// Get game reviews
router.get("/reviews/:id", async (req, res) => {
    try {
        const gameId = req.params.id;
        
        if (!gameId) {
            return res.status(400).json({
                success: false,
                message: 'Game ID is required'
            });
        }
        
        const reviews = await BuyPageService.getGameReviews(gameId);
        return res.json(reviews);
    } catch (error) {
        console.error('Error fetching game reviews:', error);
        return res.status(500).json({
            success: false,
            message: 'Failed to fetch game reviews'
        });
    }
});

// Get related games
router.get("/related/:id", async (req, res) => {
    try {
        const gameId = req.params.id;
        
        if (!gameId) {
            return res.status(400).json({
                success: false,
                message: 'Game ID is required'
            });
        }
        
        const allGamesResponse = await BuyPageService.getAllGames();
        
        // Access the "game" array from the response
        const allGames = allGamesResponse.game;

        // Filter out the current game
        const otherGames = allGames.filter(game => game.gameID != gameId);
        
        // Shuffle and get 5 random games
        const shuffled = otherGames.sort(() => 0.5 - Math.random());
        const relatedGames = shuffled.slice(0, 5); // Get 5 random games
        
        // Return only necessary fields (gameID, gName, gPrice)
        const response = relatedGames.map(game => ({
            gameID: game.gameID,
            gName: game.gName,
            gPrice: game.gPrice
        }));
        
        return res.json(response);
    } catch (error) {
        console.error('Error fetching related games:', error);
        return res.status(500).json({
            success: false,
            message: 'Failed to fetch related games'
        });
    }
});

module.exports = router;
