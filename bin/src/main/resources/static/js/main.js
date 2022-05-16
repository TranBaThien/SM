$( function() {

	$("#datepicker-1").datetimepicker({
	  formatDate:'yy/MM/dd'
	});
	
	$("#datepicker-2").datepicker({
		showOn: "button",
		buttonImage: "/images/icon-calendar.svg",
		buttonImageOnly: true,
		buttonText: "Choose",
		showAnim: "fade",
		dateFormat: "yy/mm/dd",

	});
	//GJA0110
	$("#datepicker-3").datepicker({
		showOn: "button",
		buttonImage: "/images/icon-calendar.svg",
		buttonImageOnly: true,
		buttonText: "Choose",
		showAnim: "fade",
		dateFormat: "yy/mm/dd",
		timeFormat: "HH:mm:ss"

	});
	$("#datepicker-4").datepicker({
		showOn: "button",
		buttonImage: "/images/icon-calendar.svg",
		buttonImageOnly: true,
		buttonText: "Choose",
		showAnim: "fade",
		dateFormat: "yy/mm/dd"

	});

	/** Show/hide table common GDA0110, MDA0110 */
	closeAll();
	clickButton();
	clickBtnTable();

	/* reset checkbox not checked and button register disable */
	$("#cb1").prop('checked',false);
	$('#btn-register').prop("disabled",true);

	/* calender datepicker */
	$.datetimepicker.setLocale('ja');
	calendarDatepicker();
} );

/** function click show are contents */
function clickButton() {
	$("#btn-1").click(function() {
		$("#area-1").toggle();
	});

	$("#btn-2").click(function() {
		$("#area-2").toggle();
		reset();
	});
	$("#btn-3").click(function() {
		$("#area-3").toggle();
		reset();
	});
	$("#btn-4").click(function() {
		$("#area-4").toggle();
	});
	$("#btn-5").click(function() {
		$("#area-5").toggle();
	});

	$("#btn-6").click(function() {
		$("#area-6").toggle();
	});
}

/** function click hide show tbody of table */
function clickBtnTable() {
	$("#btn-table-2").click(function() {
		$("#tbody-2").toggle();
		changeButton("#btn-table-2");
	});

	$("#btn-table-3").click(function() {
		$("#tbody-3").toggle();
		changeButton("#btn-table-3");
	});

	$("#btn-table-4").click(function() {
		$("#tbody-4").toggle();
		changeButton("#btn-table-4");
	});

	$("#btn-table-5").click(function() {
		$("#tbody-5").toggle();
		changeButton("#btn-table-5");
	});

	$("#btn-table-6").click(function() {
		$("#tbody-6").toggle();
		changeButton("#btn-table-6");
	});

	$("#btn-table-7").click(function() {
		$("#tbody-7").toggle();
		changeButton("#btn-table-7");
	});

	$("#btn-table-8").click(function() {
		$("#tbody-8").toggle();
		changeButton("#btn-table-8");
	});

	$("#btn-table-9").click(function() {
		$("#tbody-9").toggle();
		changeButton("#btn-table-9");
	});
}

/** funtion move to table with link */
let isOpen = true;
function showForm(id, idBtn) {			
	var id1 = "#" + id;
	var idBtn1 = "#" + idBtn;
	if (isOpen) {
		$(id1).show();
		$(idBtn1).show();
	}
}

/** function show all area */
function showAll() {
	$('#area-1').show();
	$('#area-2').show();
	$('#area-3').show();
	$('#area-4').show();
	$('#area-5').show();
	$('#area-6').show();
}

/** function hide all area */
function closeAll() {
	$('#area-1').hide();
	$('#area-2').hide();
	$('#area-3').hide();
	$('#area-4').hide();
	$('#area-5').hide();
	$('#area-6').hide();
}

/** function reset all table show */
function reset() {
	$("#tbody-2").show();
	$("#tbody-3").show();
	$("#tbody-4").show();
	$("#tbody-5").show();
	$("#tbody-6").show();
	$("#tbody-7").show();
	$("#tbody-8").show();
}

/** function change value button  "非表示" <-> "表示" */
function changeButton(id) {
	if($(id).html()=="非表示") {

		$(id).html("表示");
	} else {
		$(id).html("非表示");
	}
}

/** function upload file */
function uploadFile(idInput, idFile) {
	idInput1 = "#" + idInput;
	idFile1 = "#" + idFile;
	$(idFile1).click();
	$(idFile1).change(function() {
		if($(idFile1).val()) {
			let textUpload = $(idFile1).val().match(/[\/\\]([\w\d\s\.\-\(\)]+)$/)[1];
			$(idInput1).val(textUpload);
		}
	});
}

function clearText(idInput,idFile) {
	idInput1 = "#" + idInput;
	idFile1 = "#" + idFile;
	$(idInput1).val("");
	$(idFile1).val("");
}

/** function check checkbox to button register disabled */
function checkbox() {
	if($("#cb1").prop('checked')) {
		$('#btn-register').prop("disabled",false);
	} else {
		$('#btn-register').prop("disabled",true);
	}
}

function calendarDatepicker() {
	$('#datepicker').datetimepicker({
		timepicker:false,
		format:'Y/m/d'
	  });
	$('#btn-calender').click(function() {
		$('#datepicker').datetimepicker('show');
	});
}

$("input[type='radio']").click(function(e){
	if ($(this).is(':checked')){
		$(this).closest('div').focus();
	}
});
