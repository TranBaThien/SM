/*<![CDATA[*/

    function submitMoveForm(url, activeBtn) {
        //activeBtn = 0: inactive Register and Change buttons, activeBtn = 1: Register button, activeBtn = 2: Change button
        $('#submitRedirect').attr('action',BASE_URL + url)
        $('input[name=apartmentId]').val(MBA_GLOBAL_VAR.apartmentId);
        $('input[name=activeBtn]').val(activeBtn);
        $('#submitRedirect').submit();
	}

/*]]>*/
    function submitMoveFormSBA0110() {
		$('#submitRedirect_3').submit();
	}