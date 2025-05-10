const http = require('http');
const https = require('https');
const url = require('url');
const path = require('path');
const GamesService = require('./GAMES');

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
      const isImage = res.headers['content-type']?.includes('image');
      
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
    
    if (body) {
      req.write(body);
    }
    
    req.end();
  });
}

class BuyPageService {
    /**
     * Get game details by ID
     */
    static async getGameById(gameId) {
        try {
            const response = await makeRequest(`${BASE_URL}/games/id=${gameId}`);
            return response.data;
        } catch (error) {
            console.error(`Error fetching game with ID ${gameId}:`, error.message);
            throw error;
        }
    }
    
    /**
     * Get game image by game ID and image type
     * @param {string} gameId - Game ID
     * @param {number} pictureId - Picture ID (1-5)
     */
    static async getGameImage(gameId, pictureId) {
        try {
            // For main image (banner/cover) or additional images
            const endpoint = `${BASE_URL}/games/id=${gameId}/picture=${pictureId}`;
            
            const response = await makeRequest(endpoint, { 
                method: 'GET',
                responseType: 'arraybuffer'
            });
            
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
     * Get all games for related/recommended section
     */
    static async getAllGames() {
        try {
            const response = await makeRequest(`${BASE_URL}/games/search/all`);
            return response.data;
        } catch (error) {
            console.error(`Error fetching all games:`, error.message);
            throw error;
        }
    }
    
    /**
     * Get user wishlist
     */
    static async getUserWishlist(userId) {
        try {
            const response = await makeRequest(`${BASE_URL}/wishlist/user=${userId}`);
            return response.data;
        } catch (error) {
            console.error(`Error fetching wishlist for user ${userId}:`, error.message);
            throw error;
        }
    }
    
    /**
     * Add game to wishlist
     */
    static async addToWishlist(userId, gameId) {
        try {
            const response = await makeRequest(
                `${BASE_URL}/wishlist/user=${userId}/game=${gameId}`,
                { method: 'POST' }
            );
            return response.data;
        } catch (error) {
            console.error(`Error adding game ${gameId} to user ${userId} wishlist:`, error.message);
            throw error;
        }
    }
    
    /**
     * Remove game from wishlist
     */
    static async removeFromWishlist(userId, gameId) {
        try {
            const response = await makeRequest(
                `${BASE_URL}/wishlist/user=${userId}/game=${gameId}`,
                { method: 'DELETE' }
            );
            return response.data;
        } catch (error) {
            console.error(`Error removing game ${gameId} from user ${userId} wishlist:`, error.message);
            throw error;
        }
    }
    
    /**
     * Get reviews for a game
     */
    static async getGameReviews(gameId) {
        try {
            const response = await makeRequest(`${BASE_URL}/reviews/game=${gameId}`);
            return response.data;
        } catch (error) {
            console.error(`Error fetching reviews for game ${gameId}:`, error.message);
            throw error;
        }
    }
    
    /**
     * Get achievements for a game
     */
    static async getGameAchievements(gameId) {
        try {
            const response = await makeRequest(`${BASE_URL}/achievements/game=${gameId}`);
            return response.data;
        } catch (error) {
            console.error(`Error fetching achievements for game ${gameId}:`, error.message);
            throw error;
        }
    }
}

// Export both the service class and the route handler function
module.exports = {
    BuyPageService,
    
    // Express route handlers
    registerRoutes: function(app) {
        // Game details endpoint
        app.get('/service/buypage/details/:gameId', async (req, res) => {
            try {
                const gameId = req.params.gameId;
                const gameData = await BuyPageService.getGameById(gameId);
                res.json(gameData);
            } catch (error) {
                console.error('Error in game details endpoint:', error);
                res.status(500).json({ error: 'Failed to fetch game details' });
            }
        });
    
        // Game image endpoint
        app.get('/service/buypage/image/:gameId/:imageType', async (req, res) => {
            try {
                const gameId = req.params.gameId;
                let pictureId;
    
                // Handle main image vs. additional images
                if (req.params.imageType === 'main') {
                    pictureId = 1;  // Banner is picture1
                } else {
                    // Extract number from pictureX
                    pictureId = req.params.imageType.replace('picture', '');
                }
    
                const imageData = await BuyPageService.getGameImage(gameId, pictureId);
                
                if (imageData && imageData.data) {
                    res.set('Content-Type', imageData.contentType || 'image/jpeg');
                    res.send(imageData.data);
                } else {
                    res.status(404).send('Image not found');
                }
            } catch (error) {
                console.error('Error in game image endpoint:', error);
                res.status(500).send('Failed to fetch game image');
            }
        });
        
        // Game reviews endpoint
        app.get('/service/buypage/reviews/:gameId', async (req, res) => {
            try {
                const gameId = req.params.gameId;
                const reviews = await BuyPageService.getGameReviews(gameId);
                res.json(reviews);
            } catch (error) {
                console.error('Error in game reviews endpoint:', error);
                res.status(500).json({ error: 'Failed to fetch game reviews' });
            }
        });
        
        // Related games endpoint
        app.get('/service/buypage/related/:gameId', async (req, res) => {
            try {
                const currentGameId = req.params.gameId;
                const allGames = await BuyPageService.getAllGames();
                
                // Filter out current game
                const otherGames = allGames.filter(game => game.gameID != currentGameId);
                
                // Shuffle and get random games
                const shuffled = otherGames.sort(() => 0.5 - Math.random());
                const relatedGames = shuffled.slice(0, 4); // Get 4 random games
                
                res.json(relatedGames);
            } catch (error) {
                console.error('Error in related games endpoint:', error);
                res.status(500).json({ error: 'Failed to fetch related games' });
            }
        });
    }
};
