PermmsionMgt = new function () {
    this.userRole = [];
this.allPermList = [];
    this.listDBRoles = function () {
        console.log("calling role for listRole ");
        Util
            .makeJsonRequest(
                "GET",
                '../strix/api/api/roles.jag?action=listroles',
                null,
                null,
                function (data) {
                    if (!data.error) {
                        var rolelist = data.results;
                        console.log(rolelist);
                        var template = "Select Role Name: <select id=\"listrolenametag\"  onChange=\" PermmsionMgt.rolePicker()\">{{#.}}<option value=\"{{roles_id}}\">{{roles_name}}</option>{{/.}}</select>";
                        var html = Mustache.to_html(template, rolelist);
                        console.log(html);
                        $('#listrole').html(html);
                    } else {
                        $('#listrole').html(
                            'Error Found when calling list roles');
                    }
                });

    };

    this.listRoles = function (userName) {
        console.log("calling role for listRole ");
        var dataUser = {
                "username": userName
            };
        Util
            .makeJsonRequest(
                "GET",
                './api/role/allOfUser/',
                null,dataUser,
                function (data) {
                    if (!data.error) {
                        var rolelist = data.data.rolelist;
                        //getting user list 
                        //RoleMgt.listUserRoles();
                        
                        // console.log(rolelist);
                        var template = "<select id=\"listrolenametag\"  onclick=\"location.href='./user.jag'\">{{#.}}<option value=\"{{.}}\">{{.}}</option>{{/.}}</select>";
                        var html = Mustache.to_html(template, rolelist);
                        // console.log(html);
                        $('#listrole').html(html);
                    } else {
                        $('#listrole').html(
                            'Error Found when calling list roles');
                    }
                });
    };

    this.listUserRoles = function (roleName) {
        console.log("calling role for listUserRole ");
        var data = {
            "username": roleName
        };
        Util
            .makeJsonRequest(
                "GET",
                './api/api/permissions.jag?action=listrolepermissions&rolename='+roleName,
                null,
                data,
                function (data) {
                    if (!data.error) {
                        var permList = data.results;
                        //userRole = rolelist
                        // console.log(rolelist);
                      // console.log(permList[0].permissions_name);
                       for(var i=0; i<permList.length;i++){
                       $('#'+permList[i].permissions_name+'').prop('checked', true);
                       }
                    } else {
                        $('#userRoleList')
                            .html(
                                'Error Found when calling user list roles');
                    }
                });

    };
    
    this.addRoleForUser = function () {
	$('#msgStatus').html('');
	var username = $("#susername").val();
	var roleName =  $('#listroletag').val();
      //  console.log(username+ " is ading "+role name);
        var dataUser = {
                "username": $("#susername").val(),
                "rolename": $('#listroletag').val()
            };
        Util
            .makeJsonRequest(
                "POST",
                './api/role/addRole/',
                null,dataUser,
                function (data) {
                    if (!data.error) {
                        //var rolelist = data.message;
                        //getting user list 
                        //RoleMgt.listUserRoles();
                	RoleMgt.listRoles(username);
                	    var template = '<tr><td><div class="'+roleName+'"> '+roleName+'</div></td><td><div class="'+roleName+'"><input type="button" id="remvoeButton" value="Remove Role" class="btn btn-info" onclick="RoleMgt.removeRole("'+roleName+'");" /><div></td></tr>';
                            
                	$('#userTable > tbody > tr').eq(6).after(template);
                	//RoleMgt.listUserRoles(username);
                	$('#msgStatus').html(data.message);
                       // console.log(data.message);
                       
                    } else {
                	$('#msgStatus').html(data.message);
                    }
                });
    };

    
    
    this.addPermNew = function (roleNo,perNo) {	
        console.log(roleNo+ " addPermNew "+perNo);
       
        Util
            .makeJsonRequest(
                "POST",
                '../strix/api/api/role_permission.jag?action=addrole_permission&role_permissionOut=1&role_permissionRole='+roleNo+' &role_permissionPermission='+perNo+'&',
                null,null,
                function (data) {
                 //   if (!data.error) {
                });
    };
    
    this.removePermNew = function (roleNo,perNo) {	
        console.log(roleNo+ " addPermNew "+perNo);
       
        Util
            .makeJsonRequest(
                "POST",
                '../strix/api/api/role_permission.jag?action=deletrole_permission&role_permissionRole='+roleNo+' &role_permissionPermission='+perNo+'&',
                null,null,
                function (data) {
                 //   if (!data.error) {
                });
    };
    
    this.updatePermissionRole = function () {
    	for(var i=0; i < PermmsionMgt.allPermList.length ; i++){
    		var name = PermmsionMgt.allPermList[i].permissions_name;
    		var perNo  = $('#'+name+'').val();
    		var roleNo = $('#listrolenametag option:selected').val();
    		console.log($('#'+name+'').is(':checked'));
    		if($('#'+name+'').is(':checked')){
    		PermmsionMgt.addPermNew(roleNo,perNo);
    		}else{
    			PermmsionMgt.removePermNew(roleNo,perNo);
    		}
    	}
    	$('#msgStatus').html($('#listrolenametag option:selected').text()+" is updated.");
	}
    this.rolePicker = function () {
    	// var rolex = $('#listrolenametag').val();
    	 var role = $('#listrolenametag option:selected').text();
    	 PermmsionMgt.clearCechBoxs();
    	 $('#msgStatus').html("");
    	 PermmsionMgt.listUserRoles(role);
    };
    
    this.clearCechBoxs = function () {
    	for(var i=0; i < PermmsionMgt.allPermList.length ; i++){
    		var name = PermmsionMgt.allPermList[i].permissions_name;
    		 $('#'+name+'').prop('checked', false);
    	}
  	  // alert(  $( "#listrolenametag" ).text() );
   };
    


};