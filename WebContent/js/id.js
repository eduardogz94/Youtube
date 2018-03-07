
			 var xhr = new XHR();
			 function $(id){
				 return document.getElementById(id);
			 }
			 function sendValue(){
				 var email = $('email').value;
				 var password = $('password').value;

				 xhr.post('/login',{email:email, password:password},
				 {'Content-Type':'application/JSON'}).
				 then(function(data){
					 console.log(data);
				 });
			 }
	