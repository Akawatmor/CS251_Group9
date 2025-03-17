var http = require('http');

var express = require('express');
var path = require('path');
var app = express();

const PORT = process.env.port || 3000;

app.use(express.static(path.join(__dirname, 'public')));

app.get("/app", (req, res) => {
  res.sendFile(path.join(__dirname, 'public', 'login.html'))
});

app.listen(PORT, () => console.log(`Server running on port ${PORT}`))

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