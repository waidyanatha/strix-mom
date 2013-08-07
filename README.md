strix-mom
=========

Strix receiver-server

To Connect with the Server below json messages are required through the websocket

TO LOGIN TO THE SYSTEM

  Authentication Request:
  "ns":"org.jwebsocket.plugins.system","type":"login","username":"root","password":"123445","encoding":null,"pool":null,"utid":2
  
  Authentication success Response:
  "ns":"org.jwebsocket.plugins.system","type":"login","utid":"2","username":"root","data":"1","action":"login"
  
  Authentication failed Response:
  "ns":"org.jwebsocket.plugins.system","type":"login","utid":"2","username":"root","data":"-1","action":"login"
  
TO GET THE DIRECTORY LISTING
   Request:
