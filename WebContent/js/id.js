var wrapper = new XHR();
	function $(id) {
		return document.getElementById(id);
	}

	function signup() {
		var email = $('email').value;
		var username = $('username').value;
		var password = $('password').value;
		var name = $('email').value;
		var lastname = $('lastname').value;
		wrapper.post('/Signup',{email:email, username:username, password:password, name:name, lastname:lastname},
			{'Content-Type':'x-www-form-urlencoded'}).then(function(data){
			if(data.status == (200)) {
				window.location.href = "./index.html";
				console.log(data);
				alert("Signup Done")
			} else {
				console.log("Wrong password or email");
			}
		}).catch(function(error) {
			console.log(error);
			});
	}	

	function login() {
		var email = $('email').value;
		var password = $('password').value;
		wrapper.post('/Login',{email:email, password:password},{'Content-Type':'x-www-form-urlencoded'}).then(function(data){
			if(data.status == (200)) {
				window.location.href = "./index.html";
				alert("Log in successful")
			}else {
				console.log("Log in unsucessfully");
			}
			console.log(data);
		}).catch(function(error) {
			console.log(error);
			});
	}

	function logout() {
		wrapper.get('/Logout',{},{}).then(function(data){
			if(data.status == 200) {
				window.location.href = "./login.html";
				console.log("You have been logged out")
			}else {
				console.log("You're not logged in" + data.status);
			}
			console.log(data);
		}).catch(function(error) {
			console.log(error);
			});
	}
	
	function is_logged() {
		wrapper.get('./Login',{},{'Content-Type':'application/x-www-form-urlencoded'}).then((data) => {
		});
	}