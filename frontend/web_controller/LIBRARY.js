const express = require('express');
const router = express.Router();
const { LibraryService } = require('../springmiddleware/LIBRARY');

// Get all games
router.get('/games', async (req, res) => {
  try {
    const games = await LibraryService.getAllGames();
    return res.json(games);
  } catch (error) {
    console.error('Error fetching all games:', error);
    return res.status(500).json({
      success: false,
      message: 'Failed to fetch games'
    });
  }
});

// Get all banners
router.get('/banners', async (req, res) => {
  try {
    const banners = await LibraryService.getAllBanners();
    return res.json(banners);
  } catch (error) {
    console.error('Error fetching banners:', error);
    return res.status(500).json({
      success: false,
      message: 'Failed to fetch banners'
    });
  }
});

// Get user's owned games
router.get('/user/:userId/owned', async (req, res) => {
  try {
    const userId = req.params.userId;
    
    if (!userId) {
      return res.status(400).json({
        success: false,
        message: 'User ID is required'
      });
    }
    
    const ownedGames = await LibraryService.getUserOwnedGames(userId);
    return res.json(ownedGames);
  } catch (error) {
    console.error('Error fetching owned games:', error);
    return res.status(500).json({
      success: false,
      message: 'Failed to fetch owned games'
    });
  }
});

// Get user's played games
router.get('/user/:userId/played', async (req, res) => {
  try {
    const userId = req.params.userId;
    
    if (!userId) {
      return res.status(400).json({
        success: false,
        message: 'User ID is required'
      });
    }
    
    const playedGames = await LibraryService.getUserPlayedGames(userId);
    return res.json(playedGames);
  } catch (error) {
    console.error('Error fetching played games:', error);
    return res.status(500).json({
      success: false,
      message: 'Failed to fetch played games'
    });
  }
});

// Get user's wishlist
router.get('/user/:userId/wishlist', async (req, res) => {
  try {
    const userId = req.params.userId;
    
    if (!userId) {
      return res.status(400).json({
        success: false,
        message: 'User ID is required'
      });
    }
    
    const wishlist = await LibraryService.getUserWishlist(userId);
    return res.json(wishlist);
  } catch (error) {
    console.error('Error fetching wishlist:', error);
    return res.status(500).json({
      success: false,
      message: 'Failed to fetch wishlist'
    });
  }
});

// Get best rated games
router.get('/best-rated', async (req, res) => {
  try {
    const bestRatedGames = await LibraryService.getBestRatedGames();
    return res.json(bestRatedGames);
  } catch (error) {
    console.error('Error fetching best rated games:', error);
    return res.status(500).json({
      success: false,
      message: 'Failed to fetch best rated games'
    });
  }
});

module.exports = router;
