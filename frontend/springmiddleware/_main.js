/**
 * Spring middleware main module
 * Centralizes all Spring backend communication services
 */

const RequestCenter = require('./RequestCenter');
const LoginService = require('./LOGIN');
const RegisterService = require('./REGISTER');
const HomepageService = require('./HOMEPAGE');
const LibraryService = require('./LIBRARY');
const BuypageService = require('./BUYPAGE');
const PaymentService = require('./PAYMENT');
const UseraccountService = require('./USERACCOUNT');
const GamesService = require('./GAMES');

// Export all services
module.exports = {
  RequestCenter,
  LoginService,
  RegisterService,
  HomepageService,
  LibraryService,
  BuypageService,
  PaymentService,
  UseraccountService,
  GamesService,
  
  // Helper method to initialize all middleware
  initializeAll: () => {
    console.log('Spring middleware services initialized');
    return true;
  }
};
