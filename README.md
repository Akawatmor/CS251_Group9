Frontend System Installation Guide
=================================

This guide will help you set up and run the TVaporr frontend system.

## Prerequisites

- Node.js (v14 or higher recommended)
- npm (Node Package Manager)

## Installation Steps

1. **Clone the Repository**

   If you haven't already, clone the project repository to your local machine.

2. **Navigate to the Frontend Directory**

   ```
   cd frontend
   ```

3. **Install Dependencies**

   Install the required Node.js packages:

   ```
   npm install
   ```

4. **Configure Environment (Optional)**

   - By default, the system runs on port 3000.
   - To use a different port, set the `PORT` environment variable:

     ```
     export PORT=4000
     ```

5. **Start the Server**

   ```
   npm start
   ```

   Or, for development with automatic restarts:

   ```
   npm run dev
   ```

6. **Access the Application**

   Open your browser and go to:

   ```
   http://localhost:3000
   ```

   (Replace `3000` with your chosen port if different.)

## Directory Structure

- `app.js` - Main application entry point
- `public/` - Static HTML files
- `web_routing/` - Route handlers for web pages
- `web_controller/` - API and controller logic
- `middleware/` - Authentication middleware

## Troubleshooting

- Ensure Node.js and npm are installed and available in your PATH.
- If you encounter permission errors, try running the commands with `sudo` (Linux/macOS).
- For issues with missing modules, re-run `npm install`.

## Contact

For further assistance, contact the project maintainers.
