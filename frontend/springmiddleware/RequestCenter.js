const BaseService = require('./BaseService');
const config = require('../config');

/**
 * Request Center Service for managing all communications with the Spring backend
 * This centralized service handles routing requests to the Java Spring backend (localhost:8080)
 */
class RequestCenter {
  /**
   * Send a request to the Spring backend
   * @param {string} endpoint - The API endpoint to call
   * @param {string} method - The HTTP method (GET, POST, PUT, DELETE)
   * @param {object} data - The data to send in the request body
   * @param {object} sessionData - User session data for authentication
   * @returns {Promise} - A promise resolving to the response data
   */
  static async sendRequest(endpoint, method = 'GET', data = null, sessionData = null) {
    try {
      // Add authorization headers if session data is provided
      const headers = {};
      if (sessionData && sessionData.id) {
        headers['X-User-Id'] = sessionData.id;
        headers['X-User-Type'] = sessionData.type || 'customer';
      }

      return await BaseService.makeRequest(endpoint, method, data, headers);
    } catch (error) {
      console.error(`Error in RequestCenter.sendRequest: ${error.message}`);
      return {
        success: false,
        message: 'An error occurred while communicating with the server.'
      };
    }
  }

  /**
   * Get data from the Spring backend
   * @param {string} endpoint - The API endpoint to call
   * @param {object} sessionData - User session data for authentication
   * @returns {Promise} - A promise resolving to the response data
   */
  static async getData(endpoint, sessionData = null) {
    return this.sendRequest(endpoint, 'GET', null, sessionData);
  }

  /**
   * Post data to the Spring backend
   * @param {string} endpoint - The API endpoint to call
   * @param {object} data - The data to send
   * @param {object} sessionData - User session data for authentication
   * @returns {Promise} - A promise resolving to the response data
   */
  static async postData(endpoint, data, sessionData = null) {
    return this.sendRequest(endpoint, 'POST', data, sessionData);
  }

  /**
   * Update data on the Spring backend
   * @param {string} endpoint - The API endpoint to call
   * @param {object} data - The data to update
   * @param {object} sessionData - User session data for authentication
   * @returns {Promise} - A promise resolving to the response data
   */
  static async updateData(endpoint, data, sessionData = null) {
    return this.sendRequest(endpoint, 'PUT', data, sessionData);
  }

  /**
   * Delete data on the Spring backend
   * @param {string} endpoint - The API endpoint to call
   * @param {object} sessionData - User session data for authentication
   * @returns {Promise} - A promise resolving to the response data
   */
  static async deleteData(endpoint, sessionData = null) {
    return this.sendRequest(endpoint, 'DELETE', null, sessionData);
  }
}

module.exports = RequestCenter;
