$(function () {
	 		var ws;
            var log = function (data) {
			var res = data.split("b--");
			if(res[1] != undefined){
                $('#console').val($('#console').val() + res[1] + '\n');
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
            
            $('#connect').click(function () {
            //	if(!userCheck()){return;}
                var url = 'ws://localhost:9763/strix/chatroom/server.jag';

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
                    ws.send("b--Allowed");
                    $('#connect').addClass('disabled');
                     $('#disconnect').removeClass('disabled');
                };
                ws.onmessage = function (event) {
                	console.log(event.data);
                    log(event.data);
                    Util
                    .makeJsonRequest(
                        "GET",
                        './api/api/resources.jag?action=addresourcesbymatafile&resourcesName='+event.data,
                        null,
                        null,
                        function (datax) {
                        	console.log(datax);
                        });
                };
                ws.onclose = function () {
                    log('notification closed.');
                	// ws.send(sessionStorage.getItem("lastname") +" left the chat room.");
                	$('#connect').removeClass('disabled');
                     $('#disconnect').addClass('disabled');
                };
            });

            $('#disconnect').click(function () {
            	 ws.send(sessionStorage.getItem("lastname") +" left the chat room.");
                ws.close();
                $('#username').prop('disabled', false);
            	$('#connect').prop('disabled', false);
            	
            });

            $('#send').click(function () {
                if (!ws) {
                    alert('Please connect to the server fist');
                    return;
                }
                ws.send(sessionStorage.getItem("lastname") +": "+ $('#content').val());
                $('#content').val('');
                
            });
        });