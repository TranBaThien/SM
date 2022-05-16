const MAXIMUM = 'E0012';

const DATE = 'E0008';

const HALFWIDTH_ALPHANUMERIC = 'E0005';

const MAX_LENGTH_APARTMENT_NAME = '50';

const MAX_LENGTH_DATE = '10';

const LABEL_CAL_START_DATE = '登録日－開始';

const LABEL_CAL_END_DATE = '登録日－終了';

/* Error code for SpecialCharacters */
const SPECIALCHARACTERS = 'E0015';

// Clear search condition
// 検索条件クリア
function clearsearchcondition() {
	
	$("#clear").on("click", function() {
		$("#txtSelect").val("");
		$(".text-input-1").val("");
		$("#datepicker-start").val("");
		$("#datepicker-end").val("");
		$("#cb1").prop("checked", true);
		$("#cb2").prop("checked", false);
		$("#cb3").prop("checked", false);
	});
		
}

function validation () {
	
	//Jquery validation engine
	$("#searchform").validationEngine('attach',{
	    promptPosition : "bottomLeft",
	    showArrowOnRadioAndCheckbox : true,
	    focusFirstField : true,
	    scroll : false,
	    maxErrorsPerField: 1,
	    'custom_error_messages': {
	        "#apartment": {	            	        	
	            'maxSize' : {
	            	'message': getMessage(MAXIMUM, ['マンション名', MAX_LENGTH_APARTMENT_NAME])
	            },
	            'custom2[prohibitionCharacter]' : {
	            	'message' : getMessage(SPECIALCHARACTERS, 'マンション名')
	            }
	        },
	        "#datepicker-start": {
	        	'maxSize' : {
	            	'message': getMessage(MAXIMUM, [LABEL_CAL_START_DATE, MAX_LENGTH_DATE])
	            },
	            'custom[halfwidthAlphanumeric]' : {
                    'message': getMessage(HALFWIDTH_ALPHANUMERIC, [LABEL_CAL_START_DATE])
                },
//	        	'custom[date]' : {
//	        		'message': getMessage(DATE, [LABEL_CAL_START_DATE])
//	        	}
	        },
	        "#datepicker-end": {
	        	'maxSize' : {
	            	'message': getMessage(MAXIMUM, [LABEL_CAL_END_DATE, MAX_LENGTH_DATE])
	            },
	            'custom[halfwidthAlphanumeric]' : {
                    'message': getMessage(HALFWIDTH_ALPHANUMERIC, [LABEL_CAL_END_DATE])
                },
//	        	'custom[date]' : {
//	        		'message': getMessage(DATE, [LABEL_CAL_END_DATE])
//	        	}
	        }
	    },
	    
	});
	
}


(function($) {
    $.fn.validationEngineLanguage = function() {
    };
    $.validationEngineLanguage = {
        newLang: function() {
            $.validationEngineLanguage.allRules = {                            
				 "prohibitionCharacter" : {
					 // "regex": /^[０-９－ａ-ｚＡ-Ｚぁ-んァ-ー一-龠 ]+$/,
					 "regex" : patternRegex.isNotSpecialCharacter,
					 "alertText" : ''
				 },
                 "date": {
                     "regex": patternRegex.date,
                     "alertText": getMessage('E0008', ['*'])
                 },
                 "halfwidthAlphanumeric": {
                     "regex":  patternRegex.halfwidthAlphanumeric,
                     "alertText": getMessage(HALFWIDTH_ALPHANUMERIC, ['*']),
                 },
                 "maxSize": {
                     "regex": "none",
                     "alertText": "* ",
                     "alertText2": getMessage('E0012', ['*', '*'])
                 },
            };
        }
    };
    $.validationEngineLanguage.newLang();
})(jQuery);

function switchScreen (applicationNo) {
	$('input[name=applicationNo]').val(applicationNo);
	$('#submitbutton').submit();
}

function getPaginationValueShow (param) {

	var page;
	var size;
	
	$('#btnsubmit').attr('action', BASE_URL + '/GCA0110')
	
	if(param == "First") {
		page = 1;
		size = parseInt($("#sIZE").val());
	}
	else if(param == "Previous") {
		page = parseInt($("#pAGE").val()) - 1;
		size = parseInt($("#sIZE").val());
	}
	else if(param == "Next") {
		page = parseInt($("#pAGE").val()) + 1;
		size = parseInt($("#sIZE").val());
	}
	else if(param == "Last") {
		page = parseInt($("#total").val());
		size = parseInt($("#sIZE").val());
	}
	else {
		page = parseInt(param);
		size = parseInt($("#sIZE").val());
	}
		
	$('input[name=page]').val(page);
	$('input[name=size]').val(size);
	$('#btnsubmit').submit();
}

function getPaginationValue (param) {

	var page;
	var size;
	
	$('#btnsubmit').attr('action', BASE_URL + '/GCA0110/search')
	
	if(param == "First") {
		page = 1;
		size = parseInt($("#sIZE").val());
	}
	else if(param == "Previous") {
		page = parseInt($("#pAGE").val()) - 1;
		size = parseInt($("#sIZE").val());
	}
	else if(param == "Next") {
		page = parseInt($("#pAGE").val()) + 1;
		size = parseInt($("#sIZE").val());
	}
	else if(param == "Last") {
		page = parseInt($("#total").val());
		size = parseInt($("#sIZE").val());
	}
	else {
		page = parseInt(param);
		size = parseInt($("#sIZE").val());
	}
		
	$('input[name=page]').val(page);
	$('input[name=size]').val(size);
	$('#btnsubmit').submit();
}

function CheckBox() {
	
	var unexamined = $("#txtUnexamined").val();
	var approval = $("#txtApproval").val();
	var reject = $("#txtReject").val();
	
	if(unexamined == "on") {
		$('#cb1').prop('checked', true);
	}
	else {
		$('#cb1').prop('checked', false);
	}
	
	if(approval == "on") {
		$('#cb2').prop('checked', true);
	}
	else {
		$('#cb2').prop('checked', false);
	}
	
	if(reject == "on") {
		$('#cb3').prop('checked', true);
	}
	else {
		$('#cb3').prop('checked', false);
	}
}

function fillDate () {
	var startdate = $("#txtStartTimeApply").val();
	$("#datepicker-start").val(startdate);
	
	var enddate = $("#txtEndTimeApply").val();
	$("#datepicker-end").val(enddate);
}

function turnBack () {
	window.location.href = BASE_URL + '/GBA0110';
}


$(document).ready(function () {

	$('#datepicker-start').blur(function() {
		handleShowErrorDateTimePickerOnBlur($(this), LABEL_CAL_START_DATE);
	});
	
	$('#datepicker-end').blur(function() {
		handleShowErrorDateTimePickerOnBlur($(this), LABEL_CAL_END_DATE);
	});
	
	$("#datepicker-start").datetimepicker({
		format:'Y/m/d',
		  timepicker:false
	});
	
	$("#datepicker-end").datetimepicker({
		format:'Y/m/d',
		  timepicker:false
	});
	
	$("#button-datepicker-start").on('click', function() {
		$("#datepicker-start").focus();
	});
	$("#button-datepicker-end").on('click', function() {
		$("#datepicker-end").focus();
	});
	
	clearsearchcondition();
	validation();
	CheckBox();
	fillDate();
});

function handleShowErrorDateTimePickerOnBlur(selector, param) {
    const $this = selector;
    const val = $this.val();
    const dateTimeRegEx = new RegExp(patternRegex.isDateFormat);;
    const halfSizeAlphaRegEx = new RegExp(patternRegex.halfwidthAlphanumeric);
    const currentDate = $.datepicker.formatDate('yy/mm/dd', new Date());

    //Check required date
    if (val == '' || val == undefined) {
        // remove custom error
        $this.removeError();
    }
    else if (!halfSizeAlphaRegEx.test(val)) {
        $this.val(currentDate);
        // custom show error
        $this.showError(getMessage(HALFWIDTH_ALPHANUMERIC, param));
    }
    else if (!dateTimeRegEx.test(val)) {
        $this.val(currentDate);
        // custom show error
        $this.showError(getMessage(DATE, param));
    }
    //Check validate format date
    if (dateTimeRegEx.test(val) && halfSizeAlphaRegEx.test(val)) {
        $this.validationEngine('hide');
        // remove custom error
        $this.removeError();
    }
}