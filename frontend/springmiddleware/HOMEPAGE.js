const BaseService = require('./BaseService');

/**
 * Service for handling homepage data
 */
class HomepageService {
  /**
   * Get featured games for the homepage
   * @returns {Promise} - A promise resolving to the featured games data
   */
  static async getFeaturedGames() {
    try {
      return await BaseService.makeRequest('/api/games/featured', 'GET');
    } catch (error) {
      return {
        success: false,
        message: 'An error occurred while fetching featured games.'
      };
    }
  }

  /**
   * Get new releases for the homepage
   * @returns {Promise} - A promise resolving to the new releases data
   */
  static async getNewReleases() {
    try {
      return await BaseService.makeRequest('/api/games/new-releases', 'GET');
    } catch (error) {
      return {
        success: false,
        message: 'An error occurred while fetching new releases.'
      };
    }
  }

  /**
   * Get popular games for the homepage
   * @returns {Promise} - A promise resolving to the popular games data
   */
  static async getPopularGames() {
    try {
      return await BaseService.makeRequest('/api/games/popular', 'GET');
    } catch (error) {
      return {
        success: false,
        message: 'An error occurred while fetching popular games.'
      };
    }
  }
}

module.exports = HomepageService;
