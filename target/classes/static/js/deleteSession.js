$(document).ready(function(){
	if (typeof(Storage) !== "undefined") {
		var screenID = sessionStorage.getItem("screenId");
		sessionStorage.clear();
		sessionStorage.setItem("screenId", screenID);
	}
})