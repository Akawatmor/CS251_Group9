const https = require('https');
const http = require('http');
const url = require('url');
const config = require('../config');

/**
 * Authentication middleware for handling user login
 */
class LoginService {
  /**
   * Authenticate a user with the backend API
   * @param {string} username - The user's username
   * @param {string} password - The user's password
   * @returns {Promise} - A promise resolving to the authentication response
   */
  static async authenticate(username, password) {
    try {
      // Construct the full URL for authentication
      const authUrl = `${config.apiBaseUrl}${config.endpoints.authentication}`;
      
      // Create request data
      const postData = JSON.stringify({
        username: username,
        password: password
      });
      
      // Parse the URL to get components
      const parsedUrl = url.parse(authUrl);
      
      // Determine protocol to use
      const protocol = parsedUrl.protocol === 'https:' ? https : http;
      
      // Configure request options
      const options = {
        hostname: parsedUrl.hostname,
        port: parsedUrl.port || (parsedUrl.protocol === 'https:' ? 443 : 80),
        path: parsedUrl.path,
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Content-Length': Buffer.byteLength(postData)
        }
      };
      
      // Return a new promise for the HTTP request
      return new Promise((resolve, reject) => {
        const req = protocol.request(options, (res) => {
          let data = '';
          
          // Collect data as it comes in
          res.on('data', (chunk) => {
            data += chunk;
          });
          
          // Process the complete response
          res.on('end', () => {
            try {
              const parsedData = JSON.parse(data);
              resolve(parsedData);
            } catch (e) {
              resolve({
                success: false,
                message: 'Failed to parse server response'
              });
            }
          });
        });
        
        // Handle request errors
        req.on('error', (error) => {
          resolve({
            success: false,
            message: 'No response from server. Please try again later.'
          });
        });
        
        // Write data to request body and send
        req.write(postData);
        req.end();
      });
    } catch (error) {
      // Handle any other errors
      return {
        success: false,
        message: 'An error occurred during authentication.'
      };
    }
  }
}

module.exports = LoginService;
