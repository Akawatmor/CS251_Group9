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

class FriendService {
  /**
   * Get all friends of a user
   */
  static async getUserFriends(userId) {
    try {
      const response = await makeRequest(`${BASE_URL}/friends/user/${userId}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching friends for user ${userId}:`, error.message);
      throw error;
    }
  }
  
  /**
   * Check if two users are friends
   */
  static async checkFriendship(user1Id, user2Id) {
    try {
      const response = await makeRequest(`${BASE_URL}/friends/user1/${user1Id}/user2/${user2Id}`);
      return response.status === 200;
    } catch (error) {
      console.error(`Error checking friendship between users ${user1Id} and ${user2Id}:`, error.message);
      return false;
    }
  }
  
  /**
   * Add a friend
   */
  static async addFriend(user1Id, user2Id) {
    try {
      const response = await makeRequest(
        `${BASE_URL}/friends/add`, 
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          }
        },
        {
          user1Id: user1Id,
          user2Id: user2Id
        }
      );
      return response.data;
    } catch (error) {
      console.error(`Error adding friend ${user2Id} for user ${user1Id}:`, error.message);
      throw error;
    }
  }
  
  /**
   * Remove a friend
   */
  static async removeFriend(user1Id, user2Id) {
    try {
      const response = await makeRequest(
        `${BASE_URL}/friends/user1/${user1Id}/user2/${user2Id}`,
        { method: 'DELETE' }
      );
      return response.status === 200;
    } catch (error) {
      console.error(`Error removing friend ${user2Id} for user ${user1Id}:`, error.message);
      throw error;
    }
  }
}

module.exports = {
  FriendService
};
