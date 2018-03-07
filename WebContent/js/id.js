
			 var xhr = new XHR();
			 function $(id){
				 return document.getElementById(id);
			 }
			 function sendValue(){
				 var email = $('email').value;
				 var password = $('password').value;
				 var user_id = 1;

				 xhr.post('/login',{email:email, password:password},
				 {'Content-Type':'application/JSON'}).
				 then(function(data){
					 console.log(data);
				 });
			 }
	