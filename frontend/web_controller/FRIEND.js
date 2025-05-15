const express = require('express');
const router = express.Router();
const { FriendService } = require('../springmiddleware/FRIEND');

// Get all friends of a user
router.get('/user/:userId', async (req, res) => {
  try {
    const userId = req.params.userId;
    
    if (!userId) {
      return res.status(400).json({
        success: false,
        message: 'User ID is required'
      });
    }
    
    const friends = await FriendService.getUserFriends(userId);
    return res.json(friends);
  } catch (error) {
    console.error('Error fetching friends:', error);
    return res.status(500).json({
      success: false,
      message: 'Failed to fetch friends'
    });
  }
});

// Check if two users are friends
router.get('/user1/:user1Id/user2/:user2Id', async (req, res) => {
  try {
    const user1Id = req.params.user1Id;
    const user2Id = req.params.user2Id;
    
    if (!user1Id || !user2Id) {
      return res.status(400).json({
        success: false,
        message: 'Both user IDs are required'
      });
    }
    
    const areFriends = await FriendService.checkFriendship(user1Id, user2Id);
    return res.json({
      success: true,
      areFriends: areFriends
    });
  } catch (error) {
    console.error('Error checking friendship:', error);
    return res.status(500).json({
      success: false,
      message: 'Failed to check friendship'
    });
  }
});

// Add a friend
router.post('/add', async (req, res) => {
  try {
    const { user1Id, user2Id } = req.body;
    
    if (!user1Id || !user2Id) {
      return res.status(400).json({
        success: false,
        message: 'Both user IDs are required'
      });
    }
    
    const result = await FriendService.addFriend(user1Id, user2Id);
    return res.json({
      success: true,
      data: result,
      message: 'Friend added successfully'
    });
  } catch (error) {
    console.error('Error adding friend:', error);
    return res.status(500).json({
      success: false,
      message: 'Failed to add friend'
    });
  }
});

// Remove a friend
router.delete('/user1/:user1Id/user2/:user2Id', async (req, res) => {
  try {
    const user1Id = req.params.user1Id;
    const user2Id = req.params.user2Id;
    
    if (!user1Id || !user2Id) {
      return res.status(400).json({
        success: false,
        message: 'Both user IDs are required'
      });
    }
    
    const success = await FriendService.removeFriend(user1Id, user2Id);
    
    if (success) {
      return res.json({
        success: true,
        message: 'Friend removed successfully'
      });
    } else {
      return res.status(500).json({
        success: false,
        message: 'Failed to remove friend'
      });
    }
  } catch (error) {
    console.error('Error removing friend:', error);
    return res.status(500).json({
      success: false,
      message: 'Failed to remove friend'
    });
  }
});

module.exports = router;
