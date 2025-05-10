const http = require('http');
const https = require('https');
const url = require('url');

// Base URL for the Spring backend
const BASE_URL = 'http://localhost:8080/api/games';

/**
 * Helper function to make HTTP requests using native http/https modules
 * @param {string} requestUrl - URL to make the request to
 * @param {Object} options - Request options
 * @param {Buffer|string|null} body - Request body
 * @returns {Promise<Object>} - Response data
 */
function makeRequest(requestUrl, options = {}, body = null) {
  return new Promise((resolve, reject) => {
    // Parse URL to determine which module to use
    const parsedUrl = url.parse(requestUrl);
    const httpModule = parsedUrl.protocol === 'https:' ? https : http;
    
    // Set default options
    const requestOptions = {
      method: options.method || 'GET',
      headers: options.headers || {},
      ...options
    };

    const req = httpModule.request(requestUrl, requestOptions, (res) => {
      const isJson = res.headers['content-type']?.includes('application/json');
      const isImage = res.headers['content-type']?.includes('image');
      
      // Handle binary data (for images)
      if (options.responseType === 'arraybuffer' || isImage) {
        const chunks = [];
        res.on('data', (chunk) => chunks.push(chunk));
        res.on('end', () => {
          const buffer = Buffer.concat(chunks);
          resolve({
            data: buffer,
            headers: res.headers,
            status: res.statusCode
          });
        });
        return;
      }
      
      // Handle text/JSON data
      let data = '';
      res.on('data', (chunk) => {
        data += chunk;
      });
      
      res.on('end', () => {
        if (res.statusCode >= 400) {
          reject(new Error(`Request failed with status code ${res.statusCode}`));
          return;
        }
        
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
    
    // Send request body if provided
    if (body) {
      req.write(body);
    }
    
    req.end();
  });
}

class GamesService {
    /**
     * Get game details by ID
     * @param {string} gameId - Game ID
     * @returns {Promise<Object>} - Game details
     */
    static async getGameById(gameId) {
        try {
            const response = await makeRequest(`${BASE_URL}/id=${gameId}`);
            return response.data;
        } catch (error) {
            console.error(`Error fetching game with ID ${gameId}:`, error.message);
            throw error;
        }
    }
    
    /**
     * Get game image by game ID and image type
     * @param {string} gameId - Game ID
     * @param {string} imageType - Type of image (picture1, picture2, etc.)
     * @returns {Promise<Object>} - Image data with content type
     */
    static async getGameImage(gameId, imageType) {
        try {
            // Define valid image types
            const validTypes = ['picture1', 'picture2', 'picture3', 'picture4', 'picture5', 'main'];
            
            if (!validTypes.includes(imageType)) {
                throw new Error('Invalid image type');
            }
            
            // For main image (banner/cover)
            let endpoint = `${BASE_URL}/banner/${gameId}`;
            
            // For other images
            if (imageType !== 'main') {
                endpoint = `${BASE_URL}/image/${gameId}/${imageType}`;
            }
            
            const response = await makeRequest(endpoint, { 
                responseType: 'arraybuffer'
            });
            
            // Get content type from response headers
            const contentType = response.headers['content-type'];
            
            return {
                data: response.data,
                contentType
            };
        } catch (error) {
            console.error(`Error fetching image for game ${gameId}:`, error.message);
            throw error;
        }
    }
    
    /**
     * Search games based on query
     * @param {string} query - Search query
     * @returns {Promise<Array>} - List of games matching the query
     */
    static async searchGames(query) {
        try {
            const response = await makeRequest(`${BASE_URL}/search/all`);
            return response.data;
        } catch (error) {
            console.error(`Error searching games:`, error.message);
            throw error;
        }
    }
}

module.exports = GamesService;
