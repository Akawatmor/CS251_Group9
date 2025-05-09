/**
 * Global configuration for the application
 */
const config = {
  // Base URL for the API server
  // This should be the URL where your backend API is hosted
  // Change this to production URL when deploying
  apiBaseUrl: 'http://localhost:8080',
  endpoints: {
    authentication: '/api/authentication/login'
  }
};

module.exports = config;
