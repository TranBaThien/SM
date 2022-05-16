$(document).ready(function(){

	// Event click button back
	$(".back-previous-screen").on('click', function(){
		window.location.href = baseUrl + '/MBA0110';
	});

	// Event click button report
	$(".btn-report").on('click', function() {
		var reportName = $(this).attr('reportName');
		var relatedNumber = $(this).attr('relatedNumber');
		$('input[name=reportName]').val(reportName);
		$('input[name=relatedNumber]').val(relatedNumber);
		window.open('about:blank', 'report', 'width=900,height=1000,scrollbars=yes');
		$('#submitFormReport').submit();
	});
})