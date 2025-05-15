const express = require('express');
const router = express.Router();
const path = require('path');
const RequestCenter = require('../springmiddleware/RequestCenter');

/**
 * General web controller for common operations
 */
class WebController {
  /**
   * Check if the user is authenticated
   * @param {object} req - Express request object
   * @param {object} res - Express response object
   * @param {function} next - Express next function
   */
  static checkAuthentication(req, res, next) {
    if (!req.session || !req.session.user) {
      return res.status(401).json({
        success: false,
        message: 'Authentication required'
      });
    }
    next();
  }

  /**
   * Check if the user has specific role
   * @param {string} role - Required role (customer, developer, admin)
   * @returns {function} - Middleware function
   */
  static checkRole(role) {
    return (req, res, next) => {
      if (!req.session || !req.session.user || req.session.user.type !== role) {
        return res.status(403).json({
          success: false,
          message: 'Access denied'
        });
      }
      next();
    };
  }

  /**
   * Render an HTML page
   * @param {string} page - The name of the HTML page
   * @returns {function} - Express route handler
   */
  static renderPage(page) {
    return (req, res) => {
      res.sendFile(path.join(process.cwd(), 'public', `${page}.html`));
    };
  }

  /**
   * Handle API requests to the Spring backend
   * @param {string} endpoint - The backend API endpoint
   * @param {string} method - HTTP method (GET, POST, PUT, DELETE)
   * @returns {function} - Express route handler
   */
  static handleApiRequest(endpoint, method = 'GET') {
    return async (req, res) => {
      try {
        let result;
        
        switch (method.toUpperCase()) {
          case 'GET':
            result = await RequestCenter.getData(endpoint, req.session?.user);
            break;
          case 'POST':
            result = await RequestCenter.postData(endpoint, req.body, req.session?.user);
            break;
          case 'PUT':
            result = await RequestCenter.updateData(endpoint, req.body, req.session?.user);
            break;
          case 'DELETE':
            result = await RequestCenter.deleteData(endpoint, req.session?.user);
            break;
          default:
            return res.status(405).json({
              success: false,
              message: 'Method not allowed'
            });
        }
        
        return res.json(result);
      } catch (error) {
        console.error(`Error in handleApiRequest: ${error.message}`);
        return res.status(500).json({
          success: false,
          message: 'An error occurred while processing your request'
        });
      }
    };
  }
}

module.exports = WebController;
