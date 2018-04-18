var wrapper = new XHR();

function $(id) {
  return document.getElementById(id);
}

function is_log(){
    wrapper.get('./Login',{},{}).then((data)=>{
     if (data.response != 'not logged in'){
         $('login').style.display = "none";
         } else{
      	 $('account').style.display = "none";
     }
  });
}


is_log();