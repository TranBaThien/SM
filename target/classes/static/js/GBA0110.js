    function submitMoveFormABA0110() {
        $('input[name=error]').val("false");
        $('#submitRedirect').submit();
	}

	function submitMoveForm(url) {
		$('#submitRedirect_3').attr('action', BASE_URL + url);
		$('#submitRedirect_3').submit();
	}

	function submitMoveFormSearch(clickedId){
		$("#submitRedirect_3 input[name=buttonId]").val(clickedId);
		$('#submitRedirect_3').attr('action',BASE_URL + '/GJA0110/show');
		$('#submitRedirect_3').submit();
	}