function $ (id) {
	return document.getElementById(id);
}

var xhr = new XHR();

function comment() {
var comment = $('comment').value;
var filename = $("stream").getAttribute("filename");
console.log(comment + " " + filename);

	wrapper.post('/Comments',{comment:comment, filename:filename},{'Content-Type':'application/json'}).then(function(data){
		if(data.status == 200) {
			console.log(data);
		}else {
			console.log(data);
		}
	}).catch(function(error) {
		console.log(error);
		});
}