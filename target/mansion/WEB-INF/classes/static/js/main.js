$( function() {

	$("#datepicker-1").datetimepicker({
		dateFormat: "yy/mm/dd"

	});
	$("#datepicker-2").datetimepicker({
		dateFormat: "yy/mm/dd"

	});
	$("#datepicker-3").datetimepicker({
		dateFormat: "yy/mm/dd"

	});
	$("#datepicker-4").datetimepicker({
		dateFormat: "yy/mm/dd"
	});

	$('#button-datepicker-1').on('click', function(){
		$('#datepicker-1').focus();
	});

	$('#button-datepicker-2').on('click', function(){
		$('#datepicker-2').focus();
	});

	$('#button-datepicker-3').on('click', function(){
		$('#datepicker-3').focus();
	});

	$('#button-datepicker-4').on('click', function(){
		$('#datepicker-4').focus();
	});


	$("#datepicker-1-noTime").datepicker({
		showOn: "button",
		buttonImage: "images/icon-calendar.svg",
		buttonImageOnly: true,
		buttonText: "Choose",
		showAnim: "fade",
		dateFormat: "yy/mm/dd"

	});
	$("#datepicker-2-noTime").datepicker({
		showOn: "button",
		buttonImage: "images/icon-calendar.svg",
		buttonImageOnly: true,
		buttonText: "Choose",
		showAnim: "fade",
		dateFormat: "yy/mm/dd"

	});

	$("#datepicker-3-noTime").datepicker({
		showOn: "button",
		buttonImage: "images/icon-calendar.svg",
		buttonImageOnly: true,
		buttonText: "Choose",
		showAnim: "fade",
		dateFormat: "yy/mm/dd"

	});
	$("#datepicker-4-noTime").datepicker({
		showOn: "button",
		buttonImage: "images/icon-calendar.svg",
		buttonImageOnly: true,
		buttonText: "Choose",
		showAnim: "fade",
		dateFormat: "yy/mm/dd"

	});
	/** Show/hide table common GDA0110, MDA0110 */
	// closeAll();
	// clickButton();
	clickBtnTable();
	moveTable();
	/* reset checkbox not checked and button register disable */
	$("#cb1").prop('checked',false);
	$('#btn-register').prop("disabled",true);

	/* calender datepicker */
	$.datetimepicker.setLocale('ja');
	calendarDatepicker();

} );

/** function click show are contents */
// function clickButton() {
// 	$("#btn-1").click(function() {
// 		$("#area-1").toggle();
// 	});

// 	$("#btn-2").click(function() {
// 		$("#area-2").toggle();
// 		reset();
// 	});
// 	$("#btn-3").click(function() {
// 		$("#area-3").toggle();
// 		reset();
// 	});
// 	$("#btn-4").click(function() {
// 		$("#area-4").toggle();
// 	});
// 	$("#btn-5").click(function() {
// 		$("#area-5").toggle();
// 	});

// 	$("#btn-6").click(function() {
// 		$("#area-6").toggle();
// 	});
// }

/** function click hide show tbody of table */
function clickBtnTable() {
	$("#btn-table-2").click(function() {
		$("#tbody-2").toggle();
		changeButton("#btn-table-2","#tbody-2");
	});

	$("#btn-table-3").click(function() {
		$("#tbody-3").toggle();
		changeButton("#btn-table-3","#tbody-3");
	});

	$("#btn-table-4").click(function() {
		$("#tbody-4").toggle();
		changeButton("#btn-table-4","#tbody-4");
	});

	$("#btn-table-5").click(function() {
		$("#tbody-5").toggle();
		changeButton("#btn-table-5","#tbody-5");
	});

	$("#btn-table-6").click(function() {
		$("#tbody-6").toggle();
		changeButton("#btn-table-6","#tbody-6");
	});

	$("#btn-table-7").click(function() {
		$("#tbody-7").toggle();
		changeButton("#btn-table-7","#tbody-7");
	});

	$("#btn-table-8").click(function() {
		$("#tbody-8").toggle();
		changeButton("#btn-table-8","#tbody-8");
	});

}

/** funtion move to table with link */
function showForm(idBtn, id) {			
	var id1 = "#" + id;
	var idBtn1 = "#" + idBtn;
	$(id1).show();
	changeButton(idBtn1, id1);

}

/** function scroll  line */
function scrollingTable(id) {
	$('html,body').animate({
	    scrollTop: $(id).offset().top-175},
	 	'slow');     
}
function moveTable() {

	$("#b2").click(function() {
		scrollingTable("#a2");
		showForm('btn-table-2','tbody-2');
	});

	$("#b3").click(function() {
		scrollingTable("#a3");
		showForm('btn-table-3','tbody-3');
	});

	$("#b4").click(function() {
		scrollingTable("#a4");
		showForm('btn-table-4','tbody-4');
	});

	$("#b5").click(function() {
		scrollingTable("#a5");
		showForm('btn-table-5','tbody-5');
	});

	$("#b6").click(function() {
		scrollingTable("#a6");
		showForm('btn-table-6','tbody-6');
	});

	$("#b7").click(function() {
		scrollingTable("#a7");
		showForm('btn-table-7','tbody-7');
	});

	$("#b8").click(function() {
		scrollingTable("#a8");
		showForm('btn-table-8','tbody-8');
	});

	$("#b9").click(function() {
		scrollingTable("#a9");
	});

}



/** function show all table */
function showAll() {
	$("#tbody-2").show();
	changeButton("#btn-table-2","#tbody-2");
	$("#tbody-3").show();
	changeButton("#btn-table-3","#tbody-3");
	$("#tbody-4").show();
	changeButton("#btn-table-4","#tbody-4");
	$("#tbody-5").show();
	changeButton("#btn-table-5","#tbody-5");
	$("#tbody-6").show();
	changeButton("#btn-table-6","#tbody-6");
	$("#tbody-7").show();
	changeButton("#btn-table-7","#tbody-7");
	$("#tbody-8").show();
	changeButton("#btn-table-8","#tbody-8");
}

/** function change value button  "非表示" <-> "表示" */
function changeButton(id,idtbody) {
	if($(idtbody).css('display') == 'none') {
		$(id).html("表示");
	} else {
		$(id).html("非表示");
	}
}

/** function hide all area */
function closeAll() {
	$("#tbody-2").hide();
	changeButton("#btn-table-2","#tbody-2");
	$("#tbody-3").hide();
	changeButton("#btn-table-3","#tbody-3");
	$("#tbody-4").hide();
	changeButton("#btn-table-4","#tbody-4");
	$("#tbody-5").hide();
	changeButton("#btn-table-5","#tbody-5");
	$("#tbody-6").hide();
	changeButton("#btn-table-6","#tbody-6");
	$("#tbody-7").hide();
	changeButton("#btn-table-7","#tbody-7");
	$("#tbody-8").hide();
	changeButton("#btn-table-8","#tbody-8");
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

/** function upload file */
//function uploadFile(idInput, idFile) {
//	idInput1 = "#" + idInput;
//	idFile1 = "#" + idFile;
//	$(idFile1).click();
//	$(idFile1).change(function() {
//		if($(idFile1).val()) {
//			let textUpload = $(idFile1).val().match(/[\/\\]([\w\d\s\.\-\(\)]+)$/)[1];
//			$(idInput1).val(textUpload);
//		}
//	});
//}

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

function openHelp(url) {
	if($("#panel").css("margin-left")=='0px'){
		$("body:parent").animate({'margin-left':'-=380px'},400);
		$("#panel").animate({'margin-left':'-=380px'},400,function(){
			$("#panel").remove();
		});
	
		  $("#lnkQA1").animate({'margin-left':'-=48px'},400,function(){
			  $("#lnkQA1").remove();
		  });
		
	}else if( typeof $("#panel").css("margin-left") === "undefined"){
		
		var txt1 = "<a href='#' name='lnkQA1' id='lnkQA1'>≪ ヘルプを隠す</a>";
	
		txt1 += "<div class = 'panel' id = 'panel'></div>";
		
	$("body").append(txt1);

	$("#lnkQA1").css("position", "absolute")
	  .css("margin-left", "-150px")
	  .css("top","10px")
	  .css("z-index",1)
	  .click(function() {
		  $("#btnHowToUse").click();
	  });

	$("#panel").css("position", "absolute")
				  .css("margin-left", "-380px")
				  .css("top", "0%")
				  .css("left", "0%")
				  .css("width", "380px")
				  .css("height", "100%");
				  
	var infoMsg = "<ul>"
		+ "<li>テスト1</li>"
		+ "<li>テスト2</li>"
		+ "<li>テスト3</li>"
		+ "<li>テスト4</li>"
		+ "<li>テスト5</li>"
		+ "</ul>";
	if(url != null && url != ""){
		$("#panel").html(infoMsg);
	}
	$("body:parent").animate({'margin-left':'+=380px'},400);
	$("#panel").animate({'margin-left':'+=380px'},400,function(){
		});
	}
}
