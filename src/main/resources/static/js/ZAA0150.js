function turnBack() {
	if (previousScreen) {
		$('#submitRedirect').attr('action', previousScreen);
	}
	$('#submitRedirect').submit();
}