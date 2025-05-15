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

class UserAccountService {
  /**
   * Get user profile data
   */
  static async getUserProfile(userId) {
    try {
      const response = await makeRequest(`${BASE_URL}/customers/${userId}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching profile for user ${userId}:`, error.message);
      throw error;
    }
  }
  
  /**
   * Update user profile data
   */
  static async updateUserProfile(userId, profileData) {
    try {
      const response = await makeRequest(
        `${BASE_URL}/customers/${userId}`,
        {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json'
          }
        },
        profileData
      );
      return response.data;
    } catch (error) {
      console.error(`Error updating profile for user ${userId}:`, error.message);
      throw error;
    }
  }
  
  /**
   * Add money to user account
   */
  static async addMoney(userId, amount) {
    try {
      const response = await makeRequest(
        `${BASE_URL}/customers/${userId}/money`,
        {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json'
          }
        },
        { amount: amount }
      );
      return response.data;
    } catch (error) {
      console.error(`Error adding money for user ${userId}:`, error.message);
      throw error;
    }
  }
  
  /**
   * Delete user account
   */
  static async deleteUserAccount(userId) {
    try {
      const response = await makeRequest(
        `${BASE_URL}/customers/${userId}`,
        { method: 'DELETE' }
      );
      return response;
    } catch (error) {
      console.error(`Error deleting account for user ${userId}:`, error.message);
      throw error;
    }
  }
}

module.exports = {
  UserAccountService
};
