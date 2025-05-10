/*
Main app.js to run the website

Made by Akawat, @2025
*/

///// Configuration /////

//Config Web Service Port
const WEBPORT = process.env.PORT || 3000;



///// Import Module /////
const express = require("express");
const session = require('express-session');
const path = require("path");
const bodyParser = require('body-parser');
const fs = require("fs");
const qrcode = require("qrcode");
const socketIo = require("socket.io");
const http = require("http");

///// Import Dependencies /////
const webroutes = require(`./web_routing/_main.js`); //Doing routing work
const webctl = require(`./web_controller/_main.js`); //Doing web controller work
//require('./springmiddleware/BUYPAGE')(app); // Import route handlers

//// Runner ////
const app = express();
const server = http.createServer(app);
const io = socketIo(server);
const { userSession, devSession, adminSession } = require('./config.js');

//// App Use /////
app.use(express.static(path.join(__dirname, "public")));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(session({
  secret: 'TheSecretKeyIsNothingThatYouWantToKnow',
  resave: false,
  saveUninitialized: true,
  cookie: { secure: false }
}));

//// Handle Error ////
app.use((err, req, res, next) => {
    if (err instanceof SyntaxError) {
        return res.status(400).json({ success: false, message: 'Body of the JSON is invalid' });
    }
    else{
        return res.status(400).json({ success: false, message: 'Unexpected Error' });
    }
    
});

//// Use routes ////
app.get("/", (req, res) => {
  res.sendFile(path.join(__dirname, "public", "LOGIN.html"));
});

app.use(webroutes);
app.use("/service", webctl);


//// Running via Listening /////
app.listen(WEBPORT, () => {
    console.log(`Server running on port ${WEBPORT}`);
});