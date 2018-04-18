var wrapper = new XHR();

function $(id) {
  return document.getElementById(id);
}


function is_log1(){
    wrapper.get('./Login',{},{}).then((data)=>{
     if (data.response != 'not logged in'){
      } else{  
         $('upfile').style.display = "none";
         $('up-but').style.display = "none";  
      }
  });
}

is_log1();