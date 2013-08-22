strix-mom
=========

Strix receiver-server

To Connect with the Server below json messages are required through the websocket

TO LOGIN TO THE SYSTEM

  HANDSHAKE REQUEST:
  "ns":"org.jwebsocket.plugins.system","type":"header","clientType":"browser","clientName":"Chrome","clientVersion":"28.0.1500.95","clientInfo":"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36","jwsType":"javascript","jwsVersion":"1.0 RC1 (build 30518)","jwsInfo":"native","encodingFormats":["base64","zipBase64"],"utid":1
  
  HANDSHAKE RESPONSE:
  "ns":"org.jwebsocket.plugins.system","type":"header","clientType":"browser","clientName":"Chrome","clientVersion":"28.0.1500.95","clientInfo":"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36","jwsType":"javascript","jwsVersion":"1.0 RC1 (build 30518)","jwsInfo":"native","utid":"1"

  Authentication Request:
  "ns":"org.jwebsocket.plugins.system","type":"login","username":"root","password":"123445","encoding":null,"pool":null,"utid":2
  
  Authentication success Response:
  "ns":"org.jwebsocket.plugins.system","type":"login","utid":"2","username":"root","data":"1","action":"login"
  
  Authentication failed Response:
  "ns":"org.jwebsocket.plugins.system","type":"login","utid":"2","username":"root","data":"-1","action":"login"
  
TO GET THE DIRECTORY LISTING
   Request:
   "ns":"org.jwebsocket.plugins.samples","type":"getDirectoryListing","utid":3
   
   Response:
   "ns":"org.jwebsocket.plugins.samples","type":"getDirectoryListing","utid":"3","data":<data_part>,"action":"getDirectoryListing"

TO DO MESSAGE BROADCASTING

  Request
  "ns":"org.jwebsocket.plugins.system","type":"broadcast","sourceId":"01.49811.0","sender":"anonymous","pool":"","data":"Your Message","senderIncluded":false,"responseRequested":true,"utid":4

  Response:
  "ns":"org.jwebsocket.plugins.system","type":"broadcast","utid":"4","pool":"","data":"Your Message"
  
 TO GET ASYNCHORONOUS CALL BACKS ON FILE REVCEIVING
 
  Response
  "ns":"org.jwebsocket.plugins.system","type":"broadcast","data":<file_name>,"action":"fileReceived"
