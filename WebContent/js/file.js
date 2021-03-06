function $(id) {
	return document.getElementById(id);
}

var xhr = new XMLHttpRequest();
var file = "";

function upload() {
	var formData = new FormData($('upfile'));
	formData.append("file", $("upfile").files[0]);
	file = $("upfile").files[0].name;
	
	xhr.open("POST", "./FileUp", true);	
	xhr.send(formData);

	xhr.onreadystatechange = function () {
		if (xhr.status === 200 && xhr.readyState === 4) {
			$("uploadStatus").textContent = xhr.responseText;
		}
	}
}

function download() {
	file = $("downfile").value + ".mp4";
	var url = "./GetFile?filename="+ file;
	open(url);
	$('stream').autostart = true;
	$('stream').controls = true;

	xhr.open("GET",url,true);
	xhr.send();
	xhr.onreadystatechange = function () {
		if (xhr.status === 200 && xhr.readyState === 4) {
			$("stream").src =  './Stream?filename='+file;
			$("stream").setAttribute("filename",file);
			getComment(file);
		}
	}
}

function search() {
	file = $("downfile").value + ".mp4";
	var url = "./GetFile?filename="+ file;

	$('stream').autostart = true;
	$('stream').controls = true;

	xhr.open("GET",url,true);
	xhr.send();
	xhr.onreadystatechange = function () {
		if (xhr.status === 200 && xhr.readyState === 4) {
			$("stream").src =  './Stream?filename='+file;
			$("stream").setAttribute("filename",file);
			getComment(file);
		}	
	}
}

function getComment(file){
	var comment = "./Comments?filename=" + file;
	xhr.open("GET",comment,true);
	xhr.send();

		xhr.onreadystatechange = function () {
			if (xhr.status === 200 && xhr.readyState === 4) {
				console.log(JSON.parse(xhr.response));
				$("comment1").innerHTML = '';
				var json = (JSON.parse(xhr.response));
				for(var i=0;i<json.comment.length;i++){
					$("comment1").innerHTML += 'Comment: ' + json.comment[i] + '<br>';
				}
				getLikes(file);
			}	
		}
}


function getLikes(file){
	var likes = "./Likes?filename=" + file;
	xhr.open("GET",likes,true);
	xhr.send();

		xhr.onreadystatechange = function () {
			if (xhr.status === 200 && xhr.readyState === 4) {
				$("likes").innerHTML = xhr.responseText;
			}	
		}
}























/*
function streaming(file){
	var url1 = "./GetFile?filename="+ file;

	xhr.open("GET",url1,true);
	xhr.send();

	xhr.onreadystatechange = function () {
		if (xhr.status === 200 && xhr.readyState === 4) {
			$("stream1").src =  './Stream?filename='+ file;
			$("stream1").setAttribute("filename",file);
			console.log("Streaming -> " + $("stream1").getAttribute("filename"));
		}
	}
}

function click1(){
	var file = $('stream1').getAttribute("filename");
	var url = "./GetFile?filename="+ file;
	xhr.open("GET",url,true);
	xhr.send();

	xhr.onreadystatechange = function () {
		if (xhr.status === 200 && xhr.readyState === 4) {
			$("stream").src =  './Stream?filename='+ file;
			$("stream").setAttribute("filename",file);
			console.log("Streaming -> " + $("stream1").getAttribute("filename"));
			getComment($('stream1').getAttribute("filename"));
		}
	}
	$('stream').autostart = true;
	$('stream').controls = true;
}

function click2(){
	var file = $('stream2').getAttribute("filename")
	var url = "./GetFile?filename="+ $('stream2').getAttribute("filename");
	xhr.open("GET",url,true);
	xhr.send();

	xhr.onreadystatechange = function () {
		if (xhr.status === 200 && xhr.readyState === 4) {
			$("stream").src =  './Stream?filename='+ file;
			$("stream").setAttribute("filename",file);
			console.log("Streaming -> " + $("stream2").getAttribute("filename"));
			getComment($('stream2').getAttribute("filename"));
		}
	}
	$('stream').autostart = true;
	$('stream').controls = true;
}*/
