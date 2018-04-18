var wrapper = new XHR();

function $(id) {
  return document.getElementById(id);
}


function is_log1(){
    wrapper.get('./Login',{},{}).then((data)=>{
     if (data.response != 'not logged in'){
      } else{
         $('like').style.display = "none";
         $('deletelike').style.display = "none";
         $('com').style.display = "none";
         $('comment').style.display = "none";
     }
  });
}

is_log1();