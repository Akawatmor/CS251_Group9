const session = require('express-session');

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

//Session storage key for user authentication
const userSession = session({
  name: "user_session",
  secret : "ThisIsAUserSessionSecretKey",
  resave: false,
  saveUninitialized: true,
  cookie: {
    maxAge: 1000 * 60 * 60 * 24, // 1 day
    secure: false, // Set to true if using HTTPS
    httpOnly: true, // Prevents client-side JavaScript from accessing the cookie
  }
});

//Session storage key for dev authentication
const devSession = session({
  name: "dev_session",
  secret : "ThisIsADevSessionSecretKey",
  resave: false,
  saveUninitialized: true,
  cookie: {
    maxAge: 1000 * 60 * 60 * 24, // 1 day
    secure: false, // Set to true if using HTTPS
    httpOnly: true, // Prevents client-side JavaScript from accessing the cookie
  }
});

//Session storage key for dev authentication
const adminSession = session({
  name: "admin_session",
  secret : "ThisIsAAdminSessionSecretKey",
  resave: false,
  saveUninitialized: true,
  cookie: {
    maxAge: 1000 * 60 * 60 * 24, // 1 day
    secure: false, // Set to true if using HTTPS
    httpOnly: true, // Prevents client-side JavaScript from accessing the cookie
  }
});


module.exports = config;
