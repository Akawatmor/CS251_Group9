const express = require('express');
const router = express.Router();
const path = require('path');

/**
 * General web routing controller
 * Handles URL mapping and routing to appropriate pages
 */
class WebRouting {
  /**
   * Create a basic route for an HTML page
   * @param {object} router - Express router object
   * @param {string} route - The URL route
   * @param {string} page - The HTML page name
   * @param {function} middleware - Optional middleware function(s)
   */
  static createPageRoute(router, route, page, middleware = null) {
    if (middleware) {
      router.get(route, middleware, (req, res) => {
        res.sendFile(path.join(process.cwd(), 'public', `${page}.html`));
      });
    } else {
      router.get(route, (req, res) => {
        res.sendFile(path.join(process.cwd(), 'public', `${page}.html`));
      });
    }
  }

  /**
   * Create a route that requires authentication
   * @param {object} router - Express router object
   * @param {string} route - The URL route
   * @param {string} page - The HTML page name
   * @param {function} authCheck - Authentication check middleware
   */
  static createAuthenticatedRoute(router, route, page, authCheck) {
    router.get(route, authCheck, (req, res) => {
      res.sendFile(path.join(process.cwd(), 'public', `${page}.html`));
    });
  }

  /**
   * Create a route that redirects based on user role
   * @param {object} router - Express router object
   * @param {string} route - The URL route
   * @param {object} roleRedirects - Object mapping roles to redirect URLs
   * @param {string} defaultRedirect - Default URL if no matching role
   */
  static createRoleBasedRoute(router, route, roleRedirects, defaultRedirect) {
    router.get(route, (req, res) => {
      if (req.session && req.session.user && req.session.user.type) {
        const redirectUrl = roleRedirects[req.session.user.type];
        if (redirectUrl) {
          return res.redirect(redirectUrl);
        }
      }
      
      res.redirect(defaultRedirect);
    });
  }
}

module.exports = WebRouting;
