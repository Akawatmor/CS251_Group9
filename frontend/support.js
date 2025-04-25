

    let seconds = 5;

    function html_redirect_unau_5sec() {
      return `This page will redirect in ${seconds} seconds...`;
    }
    
    // Export the function to make it accessible in server.js
    module.exports = {
        html_redirect_unau_5sec: html_redirect_unau_5sec,
      startCountdown: function() {
        const interval = setInterval(() => {
          if (seconds === 0) {
            clearInterval(interval);
            console.log('Redirecting...');
          } else {
            seconds--;
            console.log(seconds);
          }
        }, 1000);
      }
    };