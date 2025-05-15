const BaseService = require('./BaseService');

/**
 * Service for handling user library data
 */
class LibraryService {
  /**
   * Get user's owned games
   * @param {string} userId - The user ID
   * @returns {Promise} - A promise resolving to the user's library
   */
  static async getUserLibrary(userId) {
    try {
      return await BaseService.makeRequest(`/api/library/${userId}`, 'GET');
    } catch (error) {
      return {
        success: false,
        message: 'An error occurred while fetching user library.'
      };
    }
  }

  /**
   * Get game details for a specific game in the library
   * @param {string} gameId - The game ID
   * @returns {Promise} - A promise resolving to the game details
   */
  static async getGameDetails(gameId) {
    try {
      return await BaseService.makeRequest(`/api/games/${gameId}`, 'GET');
    } catch (error) {
      return {
        success: false,
        message: 'An error occurred while fetching game details.'
      };
    }
  }
}

module.exports = LibraryService;
