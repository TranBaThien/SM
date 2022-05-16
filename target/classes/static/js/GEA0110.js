/*
 * @(#) GEA0110.js
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author pdquang
 * Create Date: 2019/12/26
 * Version: 1.0
 */

/* file name of csv */
const FILE_NAME = '経過記録詳細_';

$(document).ready(function(){ 
	if (countRecord == '0') {
		$('#exportCsv').attr("disabled", true);
	} else {
		$('#exportCsv').removeAttr("disabled");
	}
});

/**
 * Handle Export file
 * @param data
 * @param filename
 * @returns
 */
function exportToCsv(data, filename) {

    var blob = new Blob([data], { type: 'text/csv;charset=utf-8;' });
    if (navigator.msSaveBlob) { // IE 10+
        navigator.msSaveBlob(blob, filename);
    } else {
        var link = document.createElement("a");
        if (link.download !== undefined) { // feature detection
            // Browsers that support HTML5 download attribute
            var url = URL.createObjectURL(blob);
            link.setAttribute("href", url);
            link.setAttribute("download", filename);
            link.style.visibility = 'hidden';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }
    }
}

// export data to file csv
$("#exportCsv").click(function() {
	var lengthOfList = listProgressRecordNo.length - 1;
	var listNo = listProgressRecordNo.substring(1, lengthOfList);
	$.ajax ({
		type: "GET",
		url : BASE_URL + "/GEA0110/csv",
		data : {"listProgressRecordNo" : listNo },
		success : function(data) {
			exportToCsv(data, FILE_NAME + currentDateForCsv() + ".csv");
        }
	});
});

/**
 * Redirect page with submit form post method
 * @param appartmentId
 * @param progressRecordNo
 */
function registerProgressRecord(appartmentId, progressRecordNo) {
	$('input[name=apartmentId]').val(appartmentId);
	$('input[name=progressRecordNo]').val(progressRecordNo);
	$('#submitForm').submit();
}

/**
 * Oppen window when submit form report with post method
 */
$('.report_submit').on('click', function(e){
    var reportName = $(this).attr('data');
    var relatedNumber = $(this).attr('data2');
    $('input[name=reportName]').val(reportName);
    $('input[name=relatedNumber]').val(relatedNumber);
    $('#submitFormReport').attr('target', relatedNumber);
    window.open('about:blank', relatedNumber, 'width=900,height=1000,scrollbars=yes');
    $('#submitFormReport').submit();
});

/**
 * Turn back to page before with submit form post method
 */
function goBack() {
    $("#transitionsForm").submit();
}