

const express = require("express");
const session = require('express-session');
const path = require("path");
const bodyParser = require('body-parser');
const fs = require("fs");
const qrcode = require("qrcode");
const socketIo = require("socket.io");
const http = require("http");


const support = require("./support");

const app = express();
const server = http.createServer(app);
const io = socketIo(server);
const PORT = 3000;

const USER = "Example";
const PASS = "Password";

let qrcodeSession = null;

const html_redirect_unau_5sec = `
  <html>
    <head>
      <title>Unauthorized</title>
      <script>
        let seconds = 5;
        function countdown() {
          const el = document.getElementById("count");
          el.textContent = seconds;
          if (seconds === 0) {
            window.location.href = "/";
          } else {
            seconds--;
            setTimeout(countdown, 1000);
          }
        }
        window.onload = countdown;
      </script>
    </head>
    <body>
      <h1>Unauthorized Access</h1>
      <h2>This page will redirect in <span id="count">5</span> seconds...</h2>
      <a href="/">Click here if not redirected</a>
    </body>
  </html>
`;

const html_redirect_logo_5sec = `
  <html>
    <head>
      <title>Logout</title>
      <script>
        let seconds = 5;
        function countdown() {
          const el = document.getElementById("count");
          el.textContent = seconds;
          if (seconds === 0) {
            window.location.href = "/";
          } else {
            seconds--;
            setTimeout(countdown, 1000);
          }
        }
        window.onload = countdown;
      </script>
    </head>
    <body>
      <h1>Logout Successfully!</h1>
      <h2>This page will redirect to Login Page in <span id="count">5</span> seconds...</h2>
      <a href="/">Go to Home Page</a>
    </body>
  </html>
`;

//Static file on root directory
app.use(express.static(path.join(__dirname, "public")));
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({ extended: true }));
app.use(session({
  secret: 'my_secret_key',
  resave: false,
  saveUninitialized: true,
  cookie: { secure: false }
}));

//Default Route (/) to LOGIN.html
app.get('/', (requ, resp) => {
  resp.sendFile(path.join(__dirname, "public" , "LOGIN.html"));
});

//LOGIN.html Json Form Handling
app.post("/service/login", (requ, resp) => {
  const {user, pass} = requ.body;

  //Match credential => redirect
  if(user == USER && pass == PASS){
    requ.session.user = user;
    resp.json({ success: true });
  }
  else if (user == "" && pass == ""){
    resp.json({ success: false, message: 'Blank Input' });
  }
  else if (user == USER && pass == ""){
    resp.json({ success: false, message: 'Password Cannot be Blank!' });
  }
  else if (user == USER && pass != PASS){
    resp.json({ success: false, message: 'Invalid Password!' });
  }
  //else Show on
  else{
    resp.json({ success: false, message: 'No Username Exist!' });
  }
});

//qrcode generation
app.get("/generate-qrcode", (req, res) => {
  // Generate a unique session token (in real-world, this would be a secure session)
  const currentDate = new Date();
  qrCodeSession = currentDate.toString();
  
  // Create QR code that contains the session token
  qrcode.toDataURL(qrCodeSession, (err, url) => {
    if (err) {
      return res.status(500).send("Error generating QR code");
    }
    res.json({ qrCode: url }); // Send QR code data URL to client
  });
});

//HOMEPAGE REDIRECTION
app.get("/home", (requ, resp) => {
  const {user, pass} = requ.body;
  console.log(requ.body)

  if(requ.session.user){
    resp.sendFile(path.join(__dirname, "public", "HOMEPAGE.html"));
  } else{
    resp.status(401).send(html_redirect_unau_5sec);
    }
});


app.all("/service", (requ, resp) =>{
  if(requ.method != "POST"){
    resp.status(405).send(`
      <h1>Error! Method Not Allowed</h1>
      <a href="/">Go Back to Login Page</a>
      `);
  }
});
/*
//redirect
app.get('/go', (requ, resp) => {
  resp.redirect("/test.html");
});
*/

/*
//Default Route (/) to index.html
app.get("/index/", (requ, resp) => {
  resp.sendFile(path.join(__dirname, "public" , "_index.html"));
});
*/

//Logout Function
app.get("/service/logout", (req, res) => {
  req.session.destroy(() => {
    res.status(200).send(html_redirect_logo_5sec);
  });
});

//Listen Part (Connection)
app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`);
});

/*
const server = http.createServer(function (req, res) {

  if(req.url == "/"){

    res.writeHead(200, {'content-type' : 'text/html'});
    res.write(`
      <html>
      <head>
        <meta charset="utf-8">
        <title>Top Page</title>
      </head>
      <body>
        <h1 align="center">
          This is a blank top page
          <br>
          นี่คือหน้าบนสุดที่ว่างเปล่า
        </h1>
        <h2 align="center">
          Please specify the alias to this address
          <br>
          โปรดระบุชื่อต่อหลังที่อยู่เว็บนี้
        </h2>
      </body>

      </html>
      `);
    res.end();
  }

  else if (req.url = "/test"){

  }
  else{
    res.status(500).send('Error connecting to database.');
    res.end();
  }

}).listen(3000, ()=> console.log("OK. Running on port 3000"));
*/