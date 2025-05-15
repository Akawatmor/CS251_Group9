const https = require('https');
const http = require('http');
const url = require('url');
const config = require('../config');

/**
 * Base service class for backend API communication
 */
class BaseService {
  /**
   * Make an HTTP request to the backend API
   * @param {string} endpoint - The API endpoint to call
   * @param {string} method - The HTTP method (GET, POST, PUT, DELETE)
   * @param {object} data - The data to send in the request body
   * @param {object} headers - Additional headers to send
   * @returns {Promise} - A promise resolving to the response data
   */
  static async makeRequest(endpoint, method = 'GET', data = null, headers = {}) {
    try {
      // Construct the full URL
      const apiUrl = `${config.apiBaseUrl}${endpoint}`;
      
      // Parse the URL to get components
      const parsedUrl = url.parse(apiUrl);
      
      // Determine protocol to use
      const protocol = parsedUrl.protocol === 'https:' ? https : http;
      
      // Prepare request data if provided
      const postData = data ? JSON.stringify(data) : null;
      
      // Configure request options
      const options = {
        hostname: parsedUrl.hostname,
        port: parsedUrl.port || (parsedUrl.protocol === 'https:' ? 443 : 80),
        path: parsedUrl.path,
        method: method,
        headers: {
          'Content-Type': 'application/json',
          ...headers
        }
      };
      
      // Add Content-Length if sending data
      if (postData) {
        options.headers['Content-Length'] = Buffer.byteLength(postData);
      }
      
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
              const parsedData = data ? JSON.parse(data) : {};
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
        
        // Write data to request body if provided
        if (postData) {
          req.write(postData);
        }
        
        req.end();
      });
    } catch (error) {
      // Handle any other errors
      return {
        success: false,
        message: 'An error occurred during the request.'
      };
    }
  }
}

module.exports = BaseService;
