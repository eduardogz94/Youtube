"use strict";
var w3 = {};

function $(id) {
  return document.getElementById(id);
}

function includeHTML() {
  var z, i, elmnt, file, xhttp;
  /*loop through a collection of all HTML elements:*/
  z = document.getElementsByTagName("*");
  for (i = 0; i < z.length; i++) {
    elmnt = z[i];
    /*search for elements with a certain atrribute:*/
    file = elmnt.getAttribute("w3-include-html");
    if (file) {
      /*make an HTTP request using the attribute value as the file name:*/
      xhttp = new XMLHttpRequest();
      xhttp.onreadystatechange = function() {
        if (this.readyState == 4) {
          if (this.status == 200) {elmnt.innerHTML = this.responseText;}
          if (this.status == 404) {elmnt.innerHTML = "Page not found.";}
          /*remove the attribute, and call this function once more:*/
          elmnt.removeAttribute("w3-include-html");
          includeHTML();
        }
      } 
      xhttp.open("GET", file, true);
      xhttp.send();
      /*exit the function:*/
      return;
    }
  }
}

function log(){
    var xhr = new XHR();
    xhr.get('./Login',{},{}).then((data)=>{
       console.log(data.response + " email: " + data.email + " pass: " + data.password);
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

includeHTML();
log();