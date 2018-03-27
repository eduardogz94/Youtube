var wrapper = new XHR();
var w3 = {};

function $(id) {
	return document.getElementById(id);
}

function validateLogin(input){
	var email = /[a-z]/.test($('iemail').value);
	var password = /[0-9]/.test($('ipassword').value);
	
	email && password ? login() : alert("Not valid");
}

function validateSignup(input){
	var email = /[a-z]/.test($('iemail').value);
	var username = /[a-z]/.test($('iusername').value);
	var password = /[0-9]/.test($('ipassword').value);
	var name = /[a-z]/.test($('iname').value);
	var lastname = /[a-z]/.test($('ilastname').value);
	
	(email && username && password && name && lastname) ? signup() : alert("Not valid");
}

function signup() {
	var email = $('iemail').value;
	var username = $('iusername').value;
	var password = $('ipassword').value;
	var name = $('iname').value;
	var lastname = $('ilastname').value;
	
	wrapper.post('/Signup',{email:email, username:username, password:password, name:name, lastname:lastname},
		{'Content-Type':'application/json'}).then(function(data){
		if(data.status == (200)) {
			console.log(data);
			window.location.href = "./index.html";
			alert("Signup Done")
		} else {
			console.log(data);
			alert("Email already used");
		}
	}).catch(function(error) {
		console.log(error);
		});
}	

function login() {
	var email = $('iemail').value;
	var password = $('ipassword').value;
	
	wrapper.post('/Login',{email:email, password:password},{'Content-Type':'application/json'}).then(function(data){
		if(data.status == (200)) {
			console.log(data);
			window.location.href = "./index.html";
			alert("Log in successful")
		}else {
			console.log(data);
			alert("Log in unsucessfully");
		} 
	}).catch(function(error) {
		console.log(error);
		});
}

function logout() {
	wrapper.get('/Logout',{},{}).then(function(data){
		if(data.status == (200)) {
			console.log(data);
			window.location.href = "./index.html";
			alert("You have been logged out")
		}else {
			console.log(data);
			alert("You're not logged in: " + data.status + " " + data.email);
		}
	}).catch(function(error) {
		console.log(error);
		});
}

function log(){
    var xhr = new XHR();
    xhr.get('./Login',{},{}).then((data)=>{
     if (data.response != "not logged in"){
         $('login').style.display = "none";
         $('register').style.display = "none";
         $('user').textContent = "User ->"+data.email;
         $('logout').addEventListener('click', function() {
          xhr.get('./Logout',{},{}).then((data)=> {
            window.location.href = "./index.html";
              alert("you have logged out");
          }) 
          });
      } else {
         $('logout').style.display = "none";
         $('comment').style.display = "none";
         $('upfile').style.display = "none";
         $('up-but').style.display = "none";      
     }
  });
};

log();