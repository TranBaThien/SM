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
	$("#b13").addClass('disabledItem');
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
		addHideShowRemoveAll();
	});

	$("#btn-table-3").click(function() {
		$("#tbody-3").toggle();
		changeButton("#btn-table-3","#tbody-3");
		addHideShowRemoveAll();
	});

	$("#btn-table-4").click(function() {
		$("#tbody-4").toggle();
		changeButton("#btn-table-4","#tbody-4");
		addHideShowRemoveAll();
	});

	$("#btn-table-5").click(function() {
		$("#tbody-5").toggle();
		changeButton("#btn-table-5","#tbody-5");
		addHideShowRemoveAll();
	});

	$("#btn-table-6").click(function() {
		$("#tbody-6").toggle();
		changeButton("#btn-table-6","#tbody-6");
		addHideShowRemoveAll();
	});

	$("#btn-table-7").click(function() {
		$("#tbody-7").toggle();
		changeButton("#btn-table-7","#tbody-7");
		addHideShowRemoveAll();
	});

	$("#btn-table-8").click(function() {
		$("#tbody-8").toggle();
		changeButton("#btn-table-8","#tbody-8");
		addHideShowRemoveAll();
	});

}

function addHideShowRemoveAll(){
	$("#b13").removeClass('disabledItem');
	$("#b14").removeClass('disabledItem');
	if($("#tbody-2").is(":hidden")
	&& $("#tbody-3").is(":hidden")
	&& $("#tbody-4").is(":hidden")
	&& $("#tbody-5").is(":hidden")
	&& $("#tbody-6").is(":hidden")
	&& $("#tbody-7").is(":hidden")
	&& $("#tbody-8").is(":hidden")){
		$("#b14").addClass('disabledItem');
	}
	if($("#tbody-2").is(":visible")
	&& $("#tbody-3").is(":visible")
	&& $("#tbody-4").is(":visible")
	&& $("#tbody-5").is(":visible")
	&& $("#tbody-6").is(":visible")
	&& $("#tbody-7").is(":visible")
	&& $("#tbody-8").is(":visible")){
		$("#b13").addClass('disabledItem');
	}
}

/** funtion move to table with link */
function showForm(idBtn, id) {			
	var id1 = "#" + id;
	var idBtn1 = "#" + idBtn;
	$(id1).show();
	changeButton(idBtn1, id1);

}

/** function scroll  line */
function scrollingTable(id, event) {
	var offsetHeight = 0;
	if (document.getElementById('asddsa')) {
		offsetHeight = document.getElementById('asddsa').offsetHeight; 
	} else if (event) {
		const $menuGJA0120 = $(event.target).closest('.GJA0120-menu');
		if ($menuGJA0120 && $menuGJA0120.length > 0) {
			offsetHeight = $(event.target).closest('.GJA0120-menu').outerHeight();
			offsetHeight = -offsetHeight + 30;
		}
	}
	$('html,body').animate({
	    scrollTop: $(id).offset().top - 160 - offsetHeight },
	 	'slow');     
}
function moveTable() {

	$("#b0").click(function(event) {
		scrollingTable("#a0", event);
	});

	$("#b2").click(function(event) {
		scrollingTable("#a2", event);
		showForm('btn-table-2','tbody-2');
	});

	$("#b3").click(function(event) {
		scrollingTable("#a3", event);
		showForm('btn-table-3','tbody-3');
	});

	$("#b4").click(function(event) {
		scrollingTable("#a4", event);
		showForm('btn-table-4','tbody-4');
	});

	$("#b5").click(function(event) {
		scrollingTable("#a5", event);
		showForm('btn-table-5','tbody-5');
	});

	$("#b6").click(function(event) {
		scrollingTable("#a6", event);
		showForm('btn-table-6','tbody-6');
	});

	$("#b7").click(function(event) {
		scrollingTable("#a7", event);
		showForm('btn-table-7','tbody-7');
	});

	$("#b8").click(function(event) {
		scrollingTable("#a8", event);
		showForm('btn-table-8','tbody-8');
	});

	$("#b9").click(function(event) {
		scrollingTable("#a9", event);
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
	$("#b13").addClass('disabledItem');
	$("#b14").removeClass('disabledItem');
}

/** function change value button  "非表示" <-> "表示" */
function changeButton(id,idtbody) {
	if($(idtbody).css('display') == 'none') {
		$(id).val("表示");
	} else {
		$(id).val("非表示");
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
	$("#b14").addClass('disabledItem');
	$("#b13").removeClass('disabledItem');
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
	
	var idScreen = sessionStorage.getItem('screenId');			  
	var infoMsg1 = "<br>"
		+"<h3> 各画面の操作方法等のヘルプページ（HTML）（管理組合用 </h3> "
		+ "<ul>"
		+ "<li>テスト1</li>"
		+ "<li>テスト2</li>"
		+ "<li>テスト3</li>"
		+ "<li>テスト4</li>"
		+ "<li>テスト5</li>"
		+ "</ul>";
	var infoMsg2 = "<br>"
		+"<h3> 各画面の操作方法等のヘルプページ（HTML）（区市町村用） </h3> "
		+ "<ul>"
		+ "<li>テスト1</li>"
		+ "<li>テスト2</li>"
		+ "<li>テスト3</li>"
		+ "<li>テスト4</li>"
		+ "<li>テスト5</li>"
		+ "</ul>";
	
	if(url != null && url != "") {
		if (idScreen ==='MAA0110')
			$("#panel").html(infoMsg1);
		else if (idScreen === 'GAA0110')
			$("#panel").html(infoMsg2);
	}		

	$("body:parent").animate({'margin-left':'+=380px'},400);
	$("#panel").animate({'margin-left':'+=380px'},400,function(){
		});
	}
}

/** HBThinh add code for session timeout  **/
$(document).ready(function () {
    var tilteScreen = document.title ;
    document.title = tilteScreen + 'マンション管理状況届出システム';

    //Increment the idle time counter every minute.
    var idleInterval = setInterval(timerIncrement, 60000); 
    
    //reset session count time to 0 when execute click action  in screen
    $('#amLogout').click(function() {
        sessionStorage.clear();
    });
    
  //reset session count time to 0 when execute key up action  in screen
    $('#gsLogout').click(function() {
        sessionStorage.clear();
    });
});

/**
 * Scroll on top screen
 * @returns
 */
function scrollOnTop() {
	document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
}

