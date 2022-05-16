function redirectLoginZAA0130() {
	var idScreen = sessionStorage.getItem('screenId');
	var url = BASE_URL + "/MAA0110";
	if (idScreen === 'GAA0110') {
		url = BASE_URL + "/GAA0110";
	} 
	window.location.href= url;
}