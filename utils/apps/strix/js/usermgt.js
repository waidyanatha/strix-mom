UserMgt = new function() {
//var URL ='../../../usermgt/apis/';
var URL ='./api/';
this.login = function() {
    $('#message').html('');
    if($('#username').val() != "" &&  $('#password').val() != ""){
UserMgt.clear();
var data = {"username":$('#username').val(),"password":$('#password').val()};
Util.makeJsonRequest("POST",URL+"user/login/",null,data,function(html) {
if(!html.error){
     $('#message').html('<strong>'+ html.message +'!</strong> to strix.');
   document.location ='./';
}else{
    $('#message').html('<strong>Login Failed!</strong>'+ html.message );
     
}
});}else{
    $('#message').html('<strong>Warning!</strong> Please enter username and password' );
}

};

this.getRolesCurrentUser = function() {
UserMgt.clear();
var data = {"username":$('#username').val()};
Util.makeJsonRequest("POST",URL+"user/getRoles/",null,data,function(html) {
UserMgt.makeout(html);
});
};

this.logout = function() {
UserMgt.clear();
Util.makeJsonRequest("GET",URL+"user/logout/",null,null,function(html) {
document.location ='./';
});
};

this.register = function() {
UserMgt.clear();
var data = {"username":$('#susername').val(),"password":$('#spassword').val()};
Util.makeJsonRequest("POST",URL+"user/register/",null,data,function(data,status, xhr) {
UserMgt.makeout(data);
console.log(data);
console.log(status);
console.log(xhr.status);
});
};

this.remove = function() {
UserMgt.clear();
var data = {"username":$('#srusername').val()};
Util.makeJsonRequest("POST",URL+"user/remove/",null,data,function(data) {
UserMgt.makeout(data);
console.log(data);
});
};

this.signup = function() {
UserMgt.clear();
var data = {"username":$('#susername').val(),"password":$('#spassword').val()};
Util.makeJsonRequest("POST",URL+"user/signup/",null,data,function(html) {
UserMgt.makeout(html);
});
};

this.clear = function() {
UserMgt.makeout();
$(".alert").hide();
};

this.makeout = function(html) {
$("#out").val(JSON.stringify(html));
};

};