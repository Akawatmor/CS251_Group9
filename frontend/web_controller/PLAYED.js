const express = require('express');
const router = express.Router();
const { PlayedService } = require('../springmiddleware/PLAYED');

// Get all games played by a user
router.get('/user/:userId', async (req, res) => {
  try {
    const userId = req.params.userId;
    
    if (!userId) {
      return res.status(400).json({
        success: false,
        message: 'User ID is required'
      });
    }
    
    const playedGames = await PlayedService.getPlayedGames(userId);
    return res.json(playedGames);
  } catch (error) {
    console.error('Error fetching played games:', error);
    return res.status(500).json({
      success: false,
      message: 'Failed to fetch played games'
    });
  }
});

// Get all users who played a specific game
router.get('/game/:gameId', async (req, res) => {
  try {
    const gameId = req.params.gameId;
    
    if (!gameId) {
      return res.status(400).json({
        success: false,
        message: 'Game ID is required'
      });
    }
    
    const users = await PlayedService.getUsersWhoPlayedGame(gameId);
    return res.json(users);
  } catch (error) {
    console.error('Error fetching users who played game:', error);
    return res.status(500).json({
      success: false,
      message: 'Failed to fetch users who played game'
    });
  }
});

// Update play time for a game
router.put('/user/:userId/game/:gameId/time/:minutes', async (req, res) => {
  try {
    const userId = req.params.userId;
    const gameId = req.params.gameId;
    const minutes = parseInt(req.params.minutes);
    
    if (!userId || !gameId || isNaN(minutes)) {
      return res.status(400).json({
        success: false,
        message: 'User ID, Game ID, and minutes are required'
      });
    }
    
    const updatedPlayTime = await PlayedService.updatePlayTime(userId, gameId, minutes);
    return res.json({
      success: true,
      data: updatedPlayTime,
      message: 'Play time updated successfully'
    });
  } catch (error) {
    console.error('Error updating play time:', error);
    return res.status(500).json({
      success: false,
      message: 'Failed to update play time'
    });
  }
});

// Mark a game as played
router.post('/user/:userId/game/:gameId/play', async (req, res) => {
  try {
    const userId = req.params.userId;
    const gameId = req.params.gameId;
    
    if (!userId || !gameId) {
      return res.status(400).json({
        success: false,
        message: 'User ID and Game ID are required'
      });
    }
    
    const result = await PlayedService.markGameAsPlayed(userId, gameId);
    return res.json({
      success: true,
      data: result,
      message: 'Game marked as played successfully'
    });
  } catch (error) {
    console.error('Error marking game as played:', error);
    return res.status(500).json({
      success: false,
      message: 'Failed to mark game as played'
    });
  }
});

module.exports = router;
