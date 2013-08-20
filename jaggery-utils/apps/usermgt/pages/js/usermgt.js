UserMgt = new function() {

this.login = function() {
UserMgt.clear();
var data = {"username":$('#username').val(),"password":$('#password').val()};
Util.makeJsonRequest("POST","../apis/user/login/",null,data,function(html) {
UserMgt.makeout(html);
});
}

this.logout = function() {
UserMgt.clear();
Util.makeJsonRequest("GET","../apis/user/logout/",null,null,function(html) {
UserMgt.makeout(html);
});
}

this.register = function() {
UserMgt.clear();
var data = {"username":$('#susername').val(),"password":$('#spassword').val()};
Util.makeJsonRequest("POST","../apis/user/register/",null,data,function(data,status, xhr) {
UserMgt.makeout(data);
console.log(data);
console.log(status);
console.log(xhr.status);
});
}

this.remove = function() {
UserMgt.clear();
var data = {"username":$('#srusername').val()};
Util.makeJsonRequest("POST","../apis/user/remove/",null,data,function(data) {
UserMgt.makeout(data);
console.log(data);
});
}

this.signup = function() {
UserMgt.clear();
var data = {"username":$('#susername').val(),"password":$('#spassword').val()};
Util.makeJsonRequest("POST","../apis/user/signup/",null,data,function(html) {
UserMgt.makeout(html);
});
}

this.clear = function() {
UserMgt.makeout();
$(".alert").hide();
}

this.makeout = function(html) {
$("#out").val(JSON.stringify(html));
}

}