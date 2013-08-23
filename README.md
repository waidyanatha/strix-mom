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

IN SYNCRONOUS COMMUNITCATION MODE

  Note that in this mode bytes will be send to client.
  Response
  "ns":"org.jwebsocket.plugins.system","type":"broadcast","data":<byte_data>,"action":"streamReceived"
  
[21/8/13 12:38:32 PM] Rakesh Raghavan K:Plese read below comments on the <byte_data> being received and the rate of data that comes out will be a max of 128 Kbps in our Satellite case
[21/8/13 12:39:32 PM] Rakesh Raghavan K: Sending Video App (AAC+ H264 Codec) -> Receiving Video App (AAC+ H264)
[21/8/13 12:39:46 PM] Rakesh Raghavan K: So, when yo're broadcasting it, your html5 solution should be able to render it
[21/8/13 12:39:58 PM] Rakesh Raghavan K: We are currently using MX player in Android to interpret it
[21/8/13 12:40:12 PM] Rakesh Raghavan K: In your case, end device will have to use HTML5 to interpret it


  
