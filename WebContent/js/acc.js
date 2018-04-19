var wrapper = new XHR();

function $(id) {
  return document.getElementById(id);
}


function indexDOM(){
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

indexDOM();