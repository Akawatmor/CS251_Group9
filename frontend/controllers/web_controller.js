const path = require('path');
const LoginService = require('../springmiddleware/LOGIN');

/**
 * Controller for handling web routes and authentication
 */
class WebController {
  /**
   * Initialize routes for the Express app
   * @param {Object} app - Express application instance
   */
  static init(app) {
    const publicPath = path.join(__dirname, '../public');
    
    // Serve static files from the public directory
    app.use(express.static(publicPath));
    
    // Handle login POST request
    app.post('/service/login/authen', async (req, res) => {
      const { user, pass } = req.body;
      
      if (!user || !pass) {
        return res.json({
          success: false,
          message: 'Username and password are required'
        });
      }
      
      try {
        // Call the login service to authenticate
        const result = await LoginService.authenticate(user, pass);
        
        if (result.success) {
          // Store user data in session if needed
          req.session.user = {
            username: user,
            type: result.userType,
            // Add any other user data from result as needed
          };
          
          return res.json({
            success: true,
            redirectUrl: result.userType === 'customer' ? '/home' : '/admin'
          });
        } else {
          return res.json({
            success: false,
            message: result.message || 'Invalid username or password'
          });
        }
      } catch (error) {
        console.error('Login error:', error);
        return res.json({
          success: false,
          message: 'An error occurred during login'
        });
      }
    });
    
    // Route for homepage
    app.get('/home', (req, res) => {
      // Check if user is logged in
      if (!req.session.user) {
        return res.redirect('/LOGIN.html');
      }
      
      // Serve the homepage
      res.sendFile(path.join(publicPath, 'HOMEPAGE.html'));
    });
    
    // Other routes can be added here
  }
}

module.exports = WebController;
