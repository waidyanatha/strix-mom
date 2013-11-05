RoleMgt = new function() {

this.add = function() {
RoleMgt.clear();
var data = {"rolename":$('#rolename1').val()};
Util.makeJsonRequest("POST","../apis/role/add/",null,data,function(data,status, xhr) {
RoleMgt.makeout(data);
console.log(data);
console.log(status);
console.log(xhr.status);
});
}

this.remove = function() {
RoleMgt.clear();
var data = {"rolename":$('#rolename1').val()};
Util.makeJsonRequest("POST","../apis/role/remove/",null,data,function(data) {
RoleMgt.makeout(data);
console.log(data);
});
}

/*
* getting select options
*
*/


this.listUsers = function() {
	console.log("calling role for listusers ");
	$.getJSON('../apis/user/all/', function(data) {
		if(data.error) {
			console.log(data);
		} else {
			var rolelist = data.data.userlist;
			console.log("rolelist");
			console.log(rolelist);
			var template = "User Name : <select id=\"listuserstag\">{{#.}}<option value=\"{{.}}\">{{.}}</option>{{/.}}</select>";
			var html = Mustache.to_html(template, data.data.userlist);
			//console.log(html);
			$('#listuser').html(html);
		}
	});
};



this.listRoles = function () {
console.log("calling role for listRole ");
$.getJSON('../apis/role/all/', function(data) {
var rolelist = data.data.rolelist;
console.log(rolelist);
var template = "Role Name : <select id=\"listroletag\">{{#.}}<option value=\"{{.}}\">{{.}}</option>{{/.}}</select>";
var html = Mustache.to_html(template, data.data.rolelist);
console.log(html);
$('#listrole').html(html);
});
};

this.clear = function() {
RoleMgt.makeout();
$(".alert").hide();
}

this.makeout = function(html) {
$("#out").val(JSON.stringify(html));
}

}