const express = require('express');
const router = express.Router();

// Import UserAccount service
const { UserAccountService } = require('../springmiddleware/USERACCOUNT');

// Get user profile data
router.get('/profile/:userId', async (req, res) => {
  try {
    const userId = req.params.userId;
    
    if (!userId) {
      return res.status(400).json({
        success: false,
        message: 'User ID is required'
      });
    }
    
    const userProfile = await UserAccountService.getUserProfile(userId);
    return res.json(userProfile);
  } catch (error) {
    console.error('Error fetching user profile:', error);
    return res.status(500).json({
      success: false,
      message: 'Failed to fetch user profile'
    });
  }
});

// Update user profile data
router.put('/profile/:userId', async (req, res) => {
  try {
    const userId = req.params.userId;
    const profileData = req.body;
    
    if (!userId || !profileData) {
      return res.status(400).json({
        success: false,
        message: 'User ID and profile data are required'
      });
    }
    
    const updatedProfile = await UserAccountService.updateUserProfile(userId, profileData);
    return res.json({
      success: true,
      data: updatedProfile,
      message: 'Profile updated successfully'
    });
  } catch (error) {
    console.error('Error updating user profile:', error);
    return res.status(500).json({
      success: false,
      message: 'Failed to update user profile'
    });
  }
});

// Add money to user account
router.put('/money/:userId', async (req, res) => {
  try {
    const userId = req.params.userId;
    const { amount } = req.body;
    
    if (!userId || amount === undefined) {
      return res.status(400).json({
        success: false,
        message: 'User ID and amount are required'
      });
    }
    
    const response = await UserAccountService.addMoney(userId, amount);
    return res.json({
      success: true,
      data: response,
      message: 'Money added successfully'
    });
  } catch (error) {
    console.error('Error adding money to account:', error);
    return res.status(500).json({
      success: false,
      message: 'Failed to add money to account'
    });
  }
});

// Delete user account
router.delete('/account/:userId', async (req, res) => {
  try {
    const userId = req.params.userId;
    
    if (!userId) {
      return res.status(400).json({
        success: false,
        message: 'User ID is required'
      });
    }
    
    const response = await UserAccountService.deleteUserAccount(userId);
    
    if (response.status === 200) {
      return res.json({
        success: true,
        message: 'Account deleted successfully'
      });
    } else {
      return res.status(response.status).json({
        success: false,
        message: 'Failed to delete account'
      });
    }
  } catch (error) {
    console.error('Error deleting account:', error);
    return res.status(500).json({
      success: false,
      message: 'Failed to delete account'
    });
  }
});

module.exports = router;