function $ (id) {
	return document.getElementById(id);
}

var xhr = new XHR();

function comment() {
var comment = $('comment').value;
var filename = $("stream").getAttribute("filename");
console.log("Comment:" +comment + " Into: " + filename);

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

function like(){
	var filename = $("stream").getAttribute("filename"); 
	wrapper.post('/Likes',{filename:filename},{'Content-Type':'application/json'}).then(function(data){
		if(data.status == 200) {
			console.log(data);
		}else {
			console.log(data);
		}
	}).catch(function(error) {
		console.log(error);
		});
}