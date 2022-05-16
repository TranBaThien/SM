/* Error code for Required/必須 for select */
const REQUIRED_SELECT = 'E0001';
/* Error code for Required/必須 for input */
const REQUIRED = 'E0002';
/* Error code for Half-width/半角Alphanumeric/英数字 */
const HALFWIDTH_ALPHANUMERIC = 'E0005';
/* Error code for special characters*/
const SPECIAL_CHARACTERS = 'E0015';
/* Error code for maximum/最大*/
const MAXIMUM = 'E0012';
/* Error code for date/日付*/
const DATE = 'E0008';
/* Message code for show dialog confirm*/
const CONFIRM = 'C0003';
/* Message code for show dialog confirm restore*/
const CONFIRM_RESTORE = 'C0002';
/* Error code for check advice line*/
const ADVICE_LINE_CHECK = 'E0116';

const GFA0110_ADVICE_NOTIFICATION_REGISTRATION = '助言通知登録';
const GFA0110_TEMPORARY_STORAGE_OF_ADVICE_AND_NOTIFICATION_INFORMATION = '助言内容と通知情報を一時保存';
const GFA0110_ADVICE_LINE_BREAK = '助言内容の改行';
const TXT_APPENDIX_NO = '様式名';
const TXT_DOCUMENT_NO = '文書番号';
const CAL_NOTICE_DATE = '通知年月日';
const TXT_SENDER = '発信者名';
const EVIDENCE_NO = '根拠条文の項';
const TXT_ADVICE_DETAILS = '助言内容';
const RDO_NOTIFICATION_METHOD = '通知方法';

const GFA0110_MAX_LENGTH_TEXT = '20';
const GFA0110_MAX_LENGTH_DATE = '10';
const GFA0110_MAX_LENGTH_ADVICE_DETAIL = '1000';

const GFA0110_TXT_ADVICEDETAILS_CHECK_TYPING = 1;
const GFA0110_TXT_ADVICEDETAILS_CHECK_CLICKING = 2;

/* Radio */
var radio1 = [0];

$(document).ready(function(){
	$('#btnTmpsave').on('click', function(){
		var action = $(form).attr('action');
		$("#dialog-tmpSave")
		.html(getMessage(CONFIRM, GFA0110_TEMPORARY_STORAGE_OF_ADVICE_AND_NOTIFICATION_INFORMATION))
		.dialog({
			modal:true, //モーダル表示
			title: dialog.title, //タイトル
			width: 305,
			buttons: {
			"OK": function() {
				$(this).dialog("close");
				//Validation before submit form
				var isValid = $(".advice-notification-register").validationEngine('validate');
				//If valid
				if (isValid) {
					$(form).attr('action', action + '?action=temp');
					var txtAdviceDetailsLength = $('textarea[name="txtAdviceDetails"]').val().split(/\r\n|\r|\n/).length;
					$('input[name="numRowAdvice"]').val(txtAdviceDetailsLength);
					submitFormGFA0110();
				}
			},
			"キャンセル": function() {
				$(this).dialog("close"); 
			}
			}
		});
	});
	
	$('#btn-register-GFA0110').on('click', function(){
		var action = $(form).attr('action');
		$("#dialog-register")
		.html(getMessage(CONFIRM, GFA0110_ADVICE_NOTIFICATION_REGISTRATION))
		.dialog({
			modal:true, //モーダル表示
			title: dialog.title, //タイトル
			width: 305,
			buttons: {
			"OK": function() {
				$(this).dialog("close");
				//Validation before submit form
				var isValid = $(".advice-notification-register").validationEngine('validate');
				//If valid
				if (isValid) {
					$(form).attr('action', action + '?action=register');
					var txtAdviceDetailsLength = $('textarea[name="txtAdviceDetails"]').val().split(/\r\n|\r|\n/).length;
					$('input[name="numRowAdvice"]').val(txtAdviceDetailsLength);
					submitFormGFA0110();
				}
			},
			"キャンセル": function() {
				$(this).dialog("close"); 
				}
			}
		});
	});
	
	$('#btnReStore').on('click', function(){
		$("#dialog-restore")
		.html(getMessage(CONFIRM_RESTORE))
		.dialog({
			modal:true, //モーダル表示
			title: dialog.title, //タイトル
			width: 305,
			buttons: {
			"OK": function() {
				$(this).dialog("close"); 
				$(form).attr('action', BASE_URL + '/GFA0110/ReStore');
				submitFormGFA0110();
				},
			"キャンセル": function() {
				$(this).dialog("close"); 
				}
			}
		});
    });
    
	$("input[type='radio']").click(function(e){
		if ($(this).is(':checked')){
			$(this).closest('div').focus();
		}
	});
	
	$('textarea[data-limit-rows=true]')
	.on('keypress', function (event) {
	    var text = $(this).val();
	    return checkMaxLine(text, GFA0110_TXT_ADVICEDETAILS_CHECK_TYPING);
	});
	
	$('#calNoticeDateButton').on('click', function(){
		$('#calNoticeDate').focus();
	});

    //Check unfocus radio select method report
    $("input[name='rdoNotificationMethod']").addClass("check-vertical-radio");
    $(".check-vertical-radio").parent().on( "keypress keydown click focusout", function(e) {
        checkRadioByClassId(this, e, radio1, RDO_NOTIFICATION_METHOD, true);
    });

	//Check unfocus corespondDateTime input
	$('#calNoticeDate').blur(function() {
		var val = $(this).val();
		var dateTimeRegEx = new RegExp(patternRegex.date);
		var halfSizeAlphaRegEx = new RegExp(patternRegex.halfwidthAlphanumeric);
		var currentDate = $.datepicker.formatDate('yy/mm/dd', new Date());
		//Check required date
		if (val == '' || val == undefined) {
			//hide message error and show main message
			$(this).validationEngine('hide');
		}
		else if (!halfSizeAlphaRegEx.test(val)) {
			$(this).val(currentDate);
			$(this).validationEngine('showPrompt', getMessage(HALFWIDTH_ALPHANUMERIC, CAL_NOTICE_DATE), 'error', 'bottomLeft', true);
		}
		else if (!dateTimeRegEx.test(val)) {
			$(this).validationEngine('showPrompt', getMessage(DATE, CAL_NOTICE_DATE), 'error', 'bottomLeft', true);
		}
		//Check validate format date
		if (dateTimeRegEx.test(val) && halfSizeAlphaRegEx.test(val)) {
			$(this).validationEngine('hide');
		}
	});
	
	//Init datetime picker
	$("#calNoticeDate").datetimepicker({
		timepicker:false,
		format: 'Y/m/d'
	});
	
	/* confirm check box */
	document.getElementById("btn-register-GFA0110").disabled = true;
	$('#confirm').click(function() {
	    var checkBox = document.getElementById("confirm");
	    if (checkBox.checked == true){
	        document.getElementById("btn-register-GFA0110").disabled = false;
	    } else {
	        document.getElementById("btn-register-GFA0110").disabled = true;
	    }
	});

	if (GFA0110_GLOBAL_VAR.checkOnConfirm == 'true') {
		document.getElementById("btn-register-GFA0110").disabled = false;
		document.getElementById("confirm").checked = true;
	}
    // Append max length attribute
    appendMaxLengthAttribute();

    // Apply validationEngine
	appendValidationEngineAttribute();
	
    $(".advice-notification-register").validationEngine('attach',{
		// 以下のパラメータは任意
		promptPosition: "bottomLeft",//エラー文の表示位置
		showArrowOnRadioAndCheckbox: true,//エラー箇所の図示
		focusFirstField: true,//エラー時に一番文頭の入力フィールドにフォーカスさせるかどうか
		maxErrorsPerField: 1,
		scroll: false,
		'custom_error_messages': {
			 '#txtAppendixNo': {
			 	'required': {
					'message': getMessage(REQUIRED, [TXT_APPENDIX_NO])
				},
				'custom2[prohibitionCharacter]' : {
					'message': getMessage(SPECIAL_CHARACTERS, [TXT_APPENDIX_NO])
				},
				'maxSize' : {
					'message': getMessage(MAXIMUM, [TXT_APPENDIX_NO, GFA0110_MAX_LENGTH_TEXT])
				},
			},
			 '#txtDocumentNo': {
			 	'required': {
					'message': getMessage(REQUIRED, [TXT_DOCUMENT_NO])
				},
				'custom2[prohibitionCharacter]' : {
					'message': getMessage(SPECIAL_CHARACTERS, [TXT_DOCUMENT_NO])
				},
				'maxSize' : {
					'message': getMessage(MAXIMUM, [TXT_DOCUMENT_NO, GFA0110_MAX_LENGTH_TEXT])
				},
			},
	        '#calNoticeDate': {
				'required': {
					'message': getMessage(REQUIRED, [CAL_NOTICE_DATE])
				},
				'custom[halfwidthAlphanumeric]' : {
					'message': getMessage(HALFWIDTH_ALPHANUMERIC, [CAL_NOTICE_DATE])
				},
	        	'custom[date]' : {
	        		'message': getMessage(DATE, [CAL_NOTICE_DATE])
	        	}
	        },
			 '#txtSender': {
			 	'required': {
					'message': getMessage(REQUIRED, [TXT_SENDER])
				},
				'custom2[prohibitionCharacter]' : {
					'message': getMessage(SPECIAL_CHARACTERS, [TXT_SENDER])
				},
				'maxSize' : {
					'message': getMessage(MAXIMUM, [TXT_SENDER, GFA0110_MAX_LENGTH_TEXT])
				},
			},	
			 '#evidenceNo': {
			 	'required': {
					'message': getMessage(REQUIRED_SELECT, [EVIDENCE_NO])
				},
			},	
			 '#txtAdviceDetails': {
			 	'required': {
					'message': getMessage(REQUIRED, [TXT_ADVICE_DETAILS])
				},
				'custom2[prohibitionCharacter]' : {
					'message': getMessage(SPECIAL_CHARACTERS, [TXT_ADVICE_DETAILS])
				},
				'maxSize' : {
					'message': getMessage(MAXIMUM, [TXT_ADVICE_DETAILS, GFA0110_MAX_LENGTH_ADVICE_DETAIL])
				},
			},
		 	'#radio-1': {
				'minCheckbox': {
					'message': getMessage(REQUIRED_SELECT, [RDO_NOTIFICATION_METHOD])
				}
			},
		},
		onValidationComplete: function(form, status){
			return status;
		}
    });
	if (GFA0110_GLOBAL_VAR.complete == 'true') {
		//Show alert confirm success
        $("#dialog-success")
        .html(GFA0110_GLOBAL_VAR.messageSuccess)
        .dialog({
            modal:true,
            title: dialog.title,
            buttons: {
                "OK": function() {
            		if (GFA0110_GLOBAL_VAR.callreport == 'true'){
            			/* call report RP030 */
            			report(GFA0110_GLOBAL_VAR.adviceNo);
            			turnRedirect(GFA0110_GLOBAL_VAR.apartmentId);
            		} else if (GFA0110_GLOBAL_VAR.sendEmail == 'true') {
            			turnRedirect(GFA0110_GLOBAL_VAR.apartmentId);
            		} else {
            			$(this).dialog("close"); 
            		}
                }
            }
        });
	}
	if (GFA0110_GLOBAL_VAR.changeNotification == 'true') {
        $("#dialog-error")
        .html(GFA0110_GLOBAL_VAR.messageeError)
        .dialog({
            modal:true,
            title: dialog.title,
            buttons: {
                "OK": function() {
                	turnBack(GFA0110_GLOBAL_VAR.apartmentId);
                	$(this).dialog("close");
                }
            }
        });
	}
 });
(function($) {
    $.fn.validationEngineLanguage = function() {
    };
    $.validationEngineLanguage = {
        newLang: function() {
            $.validationEngineLanguage.allRules = {
                /* Check Required/必須   for input */
                "required": {
                    "regex": "none",
                    "alertText": ' ',
                },
                 "maxSize": {
                     "regex": "none",
                     "alertText": "* ",
                     "alertText2": getMessage('E0012', ['*', '*'])
                 },
                  "date": {
                      "regex": patternRegex.isDateFormat,
                      "alertText": getMessage('E0008', ['*'])
                  },
                  "prohibitionCharacter": {
                       "regex": patternRegex.isNotSpecialCharacter,
                       "alertText": getMessage('E0015', ['*'])
                  },
                  "minCheckbox": {
                 	 "regex": "none",
                     "alertText": " ",
                  },
                  "halfwidthAlphanumeric": {
                      "regex":  patternRegex.halfwidthAlphanumeric,
                      "alertText": getMessage('E0005', ['*']),
                  }
            };
        }
    };
    $.validationEngineLanguage.newLang();
})(jQuery);

function submitFormGFA0110() {
	var form = document.getElementsByName('formAdviceNotification');
	form[0].submit();
}

function GetSelectedValue(){
	var e = document.getElementById("country");
	var result = e.options[e.selectedIndex].value;
	var txtAdviceDetail = $('#txtAdviceDetails').val();

	if (result === '') {
		return false;
	}
	
	if (!checkMaxLine(txtAdviceDetail, GFA0110_TXT_ADVICEDETAILS_CHECK_CLICKING)) {
		return false;
	}

	if(txtAdviceDetail === '') {
		$('#txtAdviceDetails').val(txtAdviceDetail + result);	
	} else {
		$('#txtAdviceDetails').val(txtAdviceDetail + '\n' + result);
	}

	$('#txtAdviceDetails').blur();
}

function turnBack(apartmentId) {
	$('#submitRedirect').attr('action', BASE_URL + '/GJA0120/show');
	$('#submitRedirect input[name=apartmentId]').val(apartmentId);
	$('#submitRedirect').submit();
}

function turnRedirect(apartmentId) {
	$('#submitRedirect').attr('action', BASE_URL + '/GJA0110/show');
	$('#submitRedirect input[name=apartmentId]').val(apartmentId);
	$('#submitRedirect').submit();
}

function checkMaxLine(text, type) {
    var numberOfLines = countNewLineCharacter(text);
    switch (type) {
		case GFA0110_TXT_ADVICEDETAILS_CHECK_TYPING:
			return !(event.which === 13 && numberOfLines == GFA0110_GLOBAL_VAR.adviceDetailsIndentionMax);
		case GFA0110_TXT_ADVICEDETAILS_CHECK_CLICKING:
			return !(numberOfLines == GFA0110_GLOBAL_VAR.adviceDetailsIndentionMax);
		default:
			return true;
		}
}

function checkMaxLineForSingleItem(field, rules, i, options) {
	var numberOfLines = countNewLineCharacter(field.val());
	if (numberOfLines > GFA0110_GLOBAL_VAR.adviceDetailsIndentionMax) {
		return getMessage(ADVICE_LINE_CHECK, [GFA0110_ADVICE_LINE_BREAK, GFA0110_GLOBAL_VAR.adviceDetailsIndentionMax]);
	}
}

function countNewLineCharacter(text) {
	return (text.match(/\n/g) || []).length + 1;
}

function report(adviceNo) {
	$('input[name=adviceNo]').val(adviceNo);
	window.open('about:blank', 'report','width=900,height=1000,scrollbars=yes');
	$('#submitFormReport').submit();
}

function appendMaxLengthAttribute() {
	// Append max length attribute
	$('#txtAppendixNo').attr("maxlength", GFA0110_MAX_LENGTH_TEXT);
	$('#txtDocumentNo').attr("maxlength", GFA0110_MAX_LENGTH_TEXT);
	$('#calNoticeDate').attr("maxlength", GFA0110_MAX_LENGTH_DATE);
	$('#txtSender').attr("maxlength", GFA0110_MAX_LENGTH_TEXT);
	$('#txtAdviceDetails').attr("maxlength", GFA0110_MAX_LENGTH_ADVICE_DETAIL);
}

function appendValidationEngineAttribute() {
	// Append data-validation-engine
	$('#txtAppendixNo').attr("data-validation-engine", 'validate[required,maxSize['+GFA0110_MAX_LENGTH_TEXT+'],custom2[prohibitionCharacter]]');
	$('#txtDocumentNo').attr("data-validation-engine", 'validate[required,maxSize['+GFA0110_MAX_LENGTH_TEXT+'],custom2[prohibitionCharacter]]');
	$('#calNoticeDate').attr("data-validation-engine",'validate[required,maxSize['+GFA0110_MAX_LENGTH_DATE+'],custom[halfwidthAlphanumeric],custom[date]]');
	$('#txtSender').attr("data-validation-engine", 'validate[required,maxSize['+GFA0110_MAX_LENGTH_TEXT+'],custom2[prohibitionCharacter]]');
	$('#evidenceNo').attr("data-validation-engine", 'validate[required]');
	$('#txtAdviceDetails').attr("data-validation-engine", 'validate[required,funcCall[checkMaxLineForSingleItem],maxSize['+GFA0110_MAX_LENGTH_ADVICE_DETAIL+'],custom2[prohibitionCharacter]]');
	$('#radio-1').attr("data-validation-engine", 'validate[minCheckbox[1]');
}

$("#calNoticeDate").blur(function () {
    handleShowErrorDateTimePickerOnBlur($(this), CAL_NOTICE_DATE);
});

function handleShowErrorDateTimePickerOnBlur(selector, param) {
    const $this = selector;
    const val = $this.val();
    const dateTimeRegEx = new RegExp(patternRegex.isDateFormat);
    const halfSizeAlphaRegEx = new RegExp(patternRegex.halfwidthAlphanumeric);
    const currentDate = $.datepicker.formatDate('yy/mm/dd', new Date());

    //Check required date
    if (val == '' || val == undefined) {
        // remove custom error
        $this.removeError();
    } else if (!halfSizeAlphaRegEx.test(val)) {
        $this.val(currentDate);
        // custom show error
        $this.showError(getMessage(HALFWIDTH_ALPHANUMERIC, param));
    } else if (!dateTimeRegEx.test(val)) {
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
