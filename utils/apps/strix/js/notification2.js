$(function () {
	 		var ws;
            var log = function (data) {
			var res = data.split("d--");
alert(res);
            if(res[1] != undefined){
               $('#console2').val($('#console2').val() + res[1].split('"')[0]  + '\n');
			}
            };

            var userCheck = function () {
            	var name = $('#username').val();            	
            	if(name== ""){ 
            		alert('Please Enter a name');
            		return false;
                }
            	$('#username').prop('disabled', true);
            	$('#connect').prop('disabled', true);
            	
            	if(typeof(Storage)!=="undefined")
            	  {
            		sessionStorage.setItem("lastname", name);
            	  }
            	else
            	  {
            	  // Sorry! No Web Storage support..
            		 alert('Storage is not supported by this browser.');
                     return false;
            	  }
            	return true;
            };
             $('#disconnect2').removeClass('disabled');
            $('#connect2').click(function () {
            //	if(!userCheck()){return;}
               // var url = 'ws://localhost:9763/strix/chatroom/server.jag';
///202.69.197.118/jWebSocket/jWebSocket
			   var url = 'ws://202.69.197.118:8787/jWebSocket/jWebSocket';

                if ('WebSocket' in window) {
                    ws = new WebSocket(url);
                } else if ('MozWebSocket' in window) {
                    ws = new MozWebSocket(url);
                } else {
                    alert('WebSocket is not supported by this browser.');
                    return;
                }

                ws.onopen = function () {                	
                    //log('Connected to the server.');
                    ws.send("n--Allowed");
                    $('#connect2').addClass('disabled');
                     $('#disconnect2').removeClass('disabled');
                };
                ws.onmessage = function (event) {
                	console.log(event.data);
                    log(event.data);
                   
                };
                ws.onclose = function () {
                    log('notification closed.');
                	// ws.send(sessionStorage.getItem("lastname") +" left the chat room.");
                	$('#connect2').removeClass('disabled');
                     $('#disconnect2').addClass('disabled');
                };
            });

            $('#disconnect2').click(function () {
            	 ws.send(sessionStorage.getItem("lastname") +" left the notification service");
                ws.close();
                $('#username').prop('disabled', false);
            	$('#connect').prop('disabled', false);
            	
            });

    
        });