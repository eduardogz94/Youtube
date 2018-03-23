function $ (id) {
	return document.getElementById(id);
}

var xhr = new XMLHttpRequest();
var file = "";


function upload () {
	var formData = new FormData($('upfile'));
	formData.append("file", $("upfile").files[0]);
	file = $("upfile").files[0].name;
	
	xhr.onreadystatechange = function () {
		if (xhr.status === 200 && xhr.readyState === 4) {
			$("uploadStatus").textContent = xhr.responseText + "\nFile uploaded";
		}
	}
	xhr.open("POST", "./FileUp", true);	
	xhr.send(formData);
	
}


function download () {
	file = $("downfile").value;
	var url = "./GetFile?filename="+ file;
	console.log(url);

	xhr.open("GET",url,true);			
	xhr.send();

	xhr.onreadystatechange = function () {
		if (xhr.status === 200 && xhr.readyState === 4) {
			$("stream").src =  './videos/'+file;
	}
	}
}