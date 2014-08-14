RoleMgt = new function () {
    this.userRole = [];

    this.listDBRoles = function () {
        console.log("calling role for listRole ");
        Util
            .makeJsonRequest(
                "GET",
                './api/api/roles.jag?action=listroles',
                null,
                data,
                function (data) {
                    if (!data.error) {
                        var rolelist = data.results;
                        console.log(rolelist);
                        var template = "<select id=\"listroletag\">{{#.}}<option value=\"{{roles_name}}\">{{roles_name}}</option>{{/.}}</select>";
                        var html = Mustache.to_html(template, rolelist);
                        console.log(html);
                        $('#listrole').html(html);
                    } else {
                        $('#listrole').html(
                            'Error Found when calling list roles');
                    }
                });

    };
    
    //adding role for DB
    this.addDBRoles = function () {
        console.log("calling add DB Roles");
        var roleData = {'rolesName':$('#rolename').val(),'rolesOut':0, 'rolesExtNo':0, 'rolesExt':0};
        Util
            .makeJsonRequest(
                "GET",
                './api/api/roles.jag?action=addroles',
                roleData,
                null,
                function (data) {
                    if (!data.error) {
                       
                        console.log(data);
                      
                    } else {
                       
                    	 console.log(data);
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
                        var template = "<select id=\"listroletag\">{{#.}}<option value=\"{{.}}\">{{.}}</option>{{/.}}</select>";
                        var html = Mustache.to_html(template, rolelist);
                        // console.log(html);
                        $('#listrole').html(html);
                    } else {
                        $('#listrole').html(
                            'Error Found when calling list roles');
                    }
                });
    };

    this.listUserRoles = function (userName) {
        console.log("calling role for listUserRole ");
        var data = {
            "username": userName
        };
        Util
            .makeJsonRequest(
                "GET",
                './api/role/getRoleListOfUser/',
                null,
                data,
                function (data) {
                    if (!data.error) {
                        var rolelist = data.data.rolelist;
                        //userRole = rolelist
                        // console.log(rolelist);
                        var template = '{{#.}}<tr><td><div class="{{.}}"> {{.}}</div></td><td><div class="{{.}}"><input type="button" id="remvoeButton" value="Remove Role" class="btn btn-info" onclick="RoleMgt.removeRole(\'{{.}}\');" /></div></td></tr>{{/.}}';
                        var html = Mustache.to_html(template, rolelist);
                        // $('#userRoleList').html(html);
                        $('#userTable > tbody > tr').eq(6).after(html);

                        // $('.schoolMember').html('');
                        // $('.Internal/everyone').html('');

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

    //adding role for system
    this.addRoleForSystem = function () {
    	//$('#msgStatus').html('');
    	var username = 'admin';
    	var roleName =  $('#rolename').val();
          //  console.log(username+ " is ading "+role name);
            var dataUser = {
                    "username": 'admin',
                    "rolename": $('#rolename').val()
                };
            Util
                .makeJsonRequest(
                    "POST",
                    './api/role/add/',
                    dataUser,dataUser,
                    function (data) {
                        if (!data.error) {
                        	 console.log(data);
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
                        	 console.log(data);
                    	$('#msgStatus').html(data.message);
                        }
                    });
        };
        
    
    this.removeRole = function (roleName) {
	$('#msgStatus').html('');
	var username = $("#susername").val();
	var roleName =  roleName;
	
      //  console.log(username+ " is ading "+role name);
        var dataUser = {
                "username": $("#susername").val(),
                "rolename": roleName
            };
        Util
            .makeJsonRequest(
                "POST",
                './api/role/removeRole/',
                null,dataUser,
                function (data) {
                    if (!data.error) {
                        //var rolelist = data.message;
                        //getting user list 
                        //RoleMgt.listUserRoles();
                	RoleMgt.listRoles(username);
                	//    var template = '<tr><td><div id="'+roleName+'" class="'+roleName+'"> '+roleName+'</div></td><<td><input type="button" id="remvoeButton" value="Remove Role" class="btn btn-info" onclick="RoleMgt.removeRole('+roleName+');" /></td></tr>';
                	$('.'+roleName+'').html('');    
                	//$('#userTable > tbody > tr').eq(6).after(template);
                	//RoleMgt.listUserRoles(username);
                	$('#msgStatus').html(data.message);
                        console.log(data.message);
                       
                    } else{
                	$('#msgStatus').html(data.message);
                    }
                });
    };

};