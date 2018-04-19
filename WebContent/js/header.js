function search() {
	file = $("downfile").value + ".mp4";
	var url = "./GetFile?filename="+ file;

	xhr.open("GET",url,true);
	xhr.send();
	xhr.onreadystatechange = function () {
		if (xhr.status === 200 && xhr.readyState === 4) {
			
		}
	}
}