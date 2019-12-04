#Java EE web sockets
This is a very simple web sockets example. This is a 1-1 connection only. It does not support multiple people connecting to the socket.

To run this project go to the terminal and type
    `mvn jetty:run`
   
Then run the html page (web-socket-client) to connect to the socket
For each new client connected a new session will be opened
