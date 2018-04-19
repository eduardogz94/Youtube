var wrapper = new XHR();

function $(id) {
  return document.getElementById(id);
}


function user_dom(){
    wrapper.get('./Login',{},{}).then((data)=>{
     if (data.response != 'not logged in'){
      } else{  
         $('upfile').style.display = "none";
         $('up-but').style.display = "none";  
      }
  });
}

user_dom();