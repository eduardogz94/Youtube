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
			$("uploadStatus").textContent = xhr.responseText + "\nFile uploaded";
		}
	}
}


function download() {
	file = $("downfile").value + ".mp4";
	var url = "./GetFile?filename="+ file;
	console.log(url);
	var downloadWindow = open(url);
	$('stream').autostart = true;
	$('stream').controls = true;

	xhr.open("GET",url,true);
	xhr.send();

	xhr.onreadystatechange = function () {
		if (xhr.status === 200 && xhr.readyState === 4) {
			$("stream").src =  './Stream?filename='+file;
			$("stream").setAttribute("filename",file);
		}
	}
}

function vid(){
	var url1 = "./GetFile?filename="+ "5mb.mp4";

	xhr.open("GET",url1,true);
	xhr.send();

	xhr.onreadystatechange = function () {
		if (xhr.status === 200 && xhr.readyState === 4) {
			$("stream1").src =  './Stream?filename='+ "5mb.mp4";
			$("stream1").setAttribute("filename","5mb.mp4");
			console.log("Streaming -> " + $("stream1").getAttribute("filename"));
		}
	}
}

function vid1(){
	var url2 = "./GetFile?filename="+ "10mb.mp4";
	xhr.open("GET",url2,true);
	xhr.send();

	xhr.onreadystatechange = function () {
		if (xhr.status === 200 && xhr.readyState === 4) {
			$("stream2").src = './Stream?filename='+ "10mb.mp4";
			$("stream2").setAttribute("filename","10mb.mp4");
			console.log("Streaming -> " + $("stream2").getAttribute("filename"));
			vid();	
		}
	}
}

function click1(){
	var url = "./GetFile?filename="+ $('stream1').getAttribute("filename");
	xhr.open("GET",url,true);
	xhr.send();

	xhr.onreadystatechange = function () {
		if (xhr.status === 200 && xhr.readyState === 4) {
			$("stream").src =  './Stream?filename='+ "5mb.mp4";
		}
	}
	$('stream').autostart = true;
	$('stream').controls = true;
}

function click2(){
	var url = "./GetFile?filename="+ $('stream1').getAttribute("filename");
	xhr.open("GET",url,true);
	xhr.send();

	xhr.onreadystatechange = function () {
		if (xhr.status === 200 && xhr.readyState === 4) {
			$("stream").src =  './Stream?filename='+ "10mb.mp4";
		}
	}
	$('stream').autostart = true;
	$('stream').controls = true;
}


vid1();