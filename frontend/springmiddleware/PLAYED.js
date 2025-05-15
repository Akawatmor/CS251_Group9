const http = require('http');
const https = require('https');
const url = require('url');

// Base URL for the Spring backend
const BASE_URL = 'http://localhost:8080/api';

/**
 * Helper function to make HTTP requests
 */
function makeRequest(requestUrl, options = {}, body = null) {
  return new Promise((resolve, reject) => {
    const parsedUrl = url.parse(requestUrl);
    const httpModule = parsedUrl.protocol === 'https:' ? https : http;
    
    const requestOptions = {
      method: options.method || 'GET',
      headers: options.headers || {},
      ...options
    };

    const req = httpModule.request(requestUrl, requestOptions, (res) => {
      const isJson = res.headers['content-type']?.includes('application/json');
      
      let data = '';
      res.on('data', (chunk) => {
        data += chunk;
      });
      
      res.on('end', () => {
        try {
          const responseData = isJson ? JSON.parse(data) : data;
          resolve({
            data: responseData,
            headers: res.headers,
            status: res.statusCode
          });
        } catch (error) {
          reject(new Error(`Error parsing response data: ${error.message}`));
        }
      });
    });
    
    req.on('error', (error) => {
      reject(error);
    });
    
    if (body && typeof body !== 'string') {
      req.write(JSON.stringify(body));
    } else if (body) {
      req.write(body);
    }
    
    req.end();
  });
}

class PlayedService {
  /**
   * Get all games played by a user
   */
  static async getPlayedGames(userId) {
    try {
      const response = await makeRequest(`${BASE_URL}/played/user/${userId}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching played games for user ${userId}:`, error.message);
      throw error;
    }
  }
  
  /**
   * Get all users who played a specific game
   */
  static async getUsersWhoPlayedGame(gameId) {
    try {
      const response = await makeRequest(`${BASE_URL}/played/game/${gameId}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching users who played game ${gameId}:`, error.message);
      throw error;
    }
  }
  
  /**
   * Update play time for a game
   */
  static async updatePlayTime(userId, gameId, additionalMinutes) {
    try {
      const response = await makeRequest(
        `${BASE_URL}/played/user/${userId}/game/${gameId}/time/${additionalMinutes}`, 
        { method: 'PUT' }
      );
      return response.data;
    } catch (error) {
      console.error(`Error updating play time for user ${userId}, game ${gameId}:`, error.message);
      throw error;
    }
  }
  
  /**
   * Mark a game as played
   */
  static async markGameAsPlayed(userId, gameId) {
    try {
      const response = await makeRequest(
        `${BASE_URL}/orders/user=${userId}/game=${gameId}/play`,
        { method: 'POST' }
      );
      return response.data;
    } catch (error) {
      console.error(`Error marking game ${gameId} as played for user ${userId}:`, error.message);
      throw error;
    }
  }
}

module.exports = {
  PlayedService
};
