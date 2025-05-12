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

class PaymentService {
  /**
   * Make an order (user owns game)
   */
  static async createOrder(userId, gameId, receiptInfo) {
    try {
      const endpoint = `${BASE_URL}/orders/user=${userId}/game=${gameId}/receipt=${receiptInfo}`;
      const response = await makeRequest(endpoint, { 
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        }
      });
      
      return response.data;
    } catch (error) {
      console.error(`Error creating order for user ${userId}, game ${gameId}:`, error.message);
      throw error;
    }
  }
  
  /**
   * Check if a user owns a game
   */
  static async checkGameOwnership(userId, gameId) {
    try {
      const endpoint = `${BASE_URL}/orders/user=${userId}/game=${gameId}/own`;
      const response = await makeRequest(endpoint);
      return response.data; // This will be true or false
    } catch (error) {
      console.error(`Error checking ownership for user ${userId}, game ${gameId}:`, error.message);
      throw error;
    }
  }
}

module.exports = {
  PaymentService
};
