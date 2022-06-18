/* Error code for Required/必須 for input */
const REQUIRED = 'E0002';
/* Error code for Required/必須 for select */
const REQUIRED_SELECT = 'E0001';
/* Error code for SpecialCharacters */
const SPECIALCHARACTERS = 'E0015';
/* Error code for minimum/最小*/
const FORMAT_DATETIME = 'E0008';
/* Error code for maximum/最大*/
const MAXIMUM = 'E0012';
/* Error code for Half-width/半角Alphanumeric/英数字 */
const HALFWIDTH_ALPHANUMERIC = 'E0005';

/* Label error message */
const LABEL_PROGRESS_RECORD_OVERVIEW = '経過記録概要';

const LABEL_CORESPOND_TYPE_CODE = '対応種別';

const LABEL_CAL_DATETIME = '対応日時';

const LABEL_PROGRESS_RECORD_DETAIL = '経過記録詳細';

const CORESPONSE_TYPE_CODE = {
		NOTICE_DELIVERY: '05',
		TOKYO_SUPPORT: '06',
} 
/* Radio */
var radio1 = [0];

$(document).ready(function(){

/* Set event tabIndex for checkBox */
    $("input[name='chkConfirm']").addClass("checkbox-class");
    $(".checkbox-class").parent().on( "keypress", function(e) {
        $(this).children().spacePress(e);
        var checkBox = document.getElementById("cd1");
        if (checkBox.checked == true){
            $(".btn-register").prop('disabled', false);
        } else {
            $(".btn-register").prop('disabled', true);
        }
    });

	//Get radio check when screen error
	var selectList = $('select[name=lstCorrespondTypeCode]').val();
	//If progressRecordNo is not null or empty then update case
	var progressRecordNo = $('input[name=progressRecordNo]').val();
	//Check case active radio support code when has error
	if (IS_EXIST_ERROR && selectList =='06' && progressRecordNo == '') {
		$('input[name=calCorrespondDate]').removeClass('disable-item');
		$('.correspondDate').removeClass('disable-item');
		$('select[name=lstCorrespondTypeCode]').removeClass('disable-item');
		$('.supportCode').removeClass('disable-item');
	}
	//Check error message E0122, Record no latest 
	if (IS_NOT_MAP_UPDATE_DATETIME != '') {
		//Show alert confirm success
		$('#message').html(IS_NOT_MAP_UPDATE_DATETIME);
		//
		$("#alertMessage").dialog({
			modal:true, //モーダル表示
			title: dialog.title, //タイトル
			buttons: {
			"OK": function() {
					$(this).dialog("close"); 
					//Move to screen GEA0110
					turnBack(APARTMENT_ID);
				}
			}
		});
	}
	
	//Check if message when complete register
	if (MESSAGE != '') {
		//Show alert confirm success
		$('#message').html(MESSAGE);
		//
		$("#alertMessage").dialog({
			modal:true, //モーダル表示
			title: dialog.title, //タイトル
			buttons: {
			"OK": function() {
					$(this).dialog("close"); 
					//Move to screen GEA0110
					//var origin   = window.location.origin;
					turnBack(APARTMENT_ID);
				}
			}
		});
	}
	//Check disable button when not yet checked chkConfirm
	if (!$('.chkConfirm').checked) {
		$('.btn-register').prop('disabled', true);
 	}
	//Check disable/enable submit button when not yet selected confirm
	$('.chkConfirm').change(function(){
		$('.btn-register').prop('disabled', !this.checked);
	});
	
	//Check event onclick calendar icon
	$('.correspondDate').on('click', function(){
		$('#corespondDateTime').focus();
	});
	
	//Remove disabled select lstCorrespondTypeCode when onclick submit
	$('.btn-register').on('click', function(e){
		//var valUpdate = $('input[name=progressRecordNo]').val();
		//Check in case update data remove disabled attribute of select list
		
		//if (valUpdate == '' || IS_EXIST_ERROR) {
		//if (IS_EXIST_ERROR) {
			$("#lstCorrespondTypeCode option").attr("disabled", false);
		//}
		//Show mesage confirm before validation
		$('#message').html(getMessage('C0003', '経過記録登録'));
		//
		$("#alertMessage").dialog({
			modal:true, //モーダル表示
			title: dialog.title, //タイトル
			buttons: {
			"OK": function() {
					$(this).dialog("close"); 
					//Validation before submit form
					var isValid = $(".advice-register").validationEngine('validate');
					//If valid
					if (isValid) {
						$("#form").submit();
					}
				},
			"キャンセル": function() {
					$(this).dialog("close"); 
				}
			}
		});
	});
	
	//Check unfocus corespondDateTime input
	$('#corespondDateTime').blur(function() {
		var val = $(this).val();
		var dateTimeRegEx = new RegExp(patternRegex.isDateTimeFormat);
		var halfSizeAlphaRegEx = new RegExp(patternRegex.halfwidthAlphanumeric);
		var currentDate = $.datepicker.formatDate('yy/mm/dd HH:mm', new Date());
		
		//Check required date
		if (val == '' || val == undefined) {
			//hide message error and show main message
			$(this).parent().validationEngine('hide');
		}
		else if (!halfSizeAlphaRegEx.test(val)) {
			$(this).val(currentDate);
			$(this).parent().validationEngine('showPrompt', getMessage(HALFWIDTH_ALPHANUMERIC, LABEL_CAL_DATETIME), 'error', 'bottomLeft', true);
		}
		else if (!dateTimeRegEx.test(val)) {
			$(this).val(currentDate);
			$(this).parent().validationEngine('showPrompt', getMessage(FORMAT_DATETIME, LABEL_CAL_DATETIME), 'error', 'bottomLeft', true);
		} 
		//Check validate format date
		if (dateTimeRegEx.test(val) && halfSizeAlphaRegEx.test(val)) {
			$(this).parent().validationEngine('hide');
		}
	});
	
	$('#txaProgressRecordDetail').on('keydown keyup change', function (e) {
		
		var valArea = $(this).val();
		//Count no Break line with \r\n or \r or \n
		var countBreakLine = valArea.split(/\r\n|\r|\n/).length;
		//Count no character of string
		var countChar = valArea.length;
		//Plus length character with break line.
		var charLength = countBreakLine + countChar;
		
		if(charLength > 300){ 
			//allow input 300 character, because string index from 0 to 300
			$(this).val(valArea.substring(0,301 - countBreakLine));
		}
	});
	
	//Init datetime picker
	$("#corespondDateTime").datetimepicker({
		dateFormat: "yy/mm/dd"
	});

	$(".advice-register").validationEngine('attach',{
		// 以下のパラメータは任意
		promptPosition: "bottomLeft",//エラー文の表示位置
		showArrowOnRadioAndCheckbox: true,//エラー箇所の図示
		focusFirstField: true,//エラー時に一番文頭の入力フィールドにフォーカスさせるかどうか
		maxErrorsPerField:1,
		scroll: false,
		 'custom_error_messages': {
			 '#corespondDateTime': {
					'required': {
						'message': getMessage(REQUIRED, LABEL_CAL_DATETIME)
					},
					'custom[datetime]': {
						'message': getMessage(FORMAT_DATETIME, LABEL_CAL_DATETIME)
					},
					'custom[halfwidthAlphanumeric]': {
						'message': getMessage(HALFWIDTH_ALPHANUMERIC, LABEL_CAL_DATETIME)
					},
					'maxSize' : {
						'message': getMessage(MAXIMUM, [LABEL_CAL_DATETIME, 16])
					}
			 },
			 '#lstCorrespondTypeCode': {
					'required': {
						'message': getMessage(REQUIRED_SELECT, LABEL_CORESPOND_TYPE_CODE)
					}
			 },
			 '#txtProgressRecordOverview': {
					'required': {
						'message': getMessage(REQUIRED, LABEL_PROGRESS_RECORD_OVERVIEW)
					},
					'custom2[prohibitionCharacter]': {
						'message': getMessage(SPECIALCHARACTERS, LABEL_PROGRESS_RECORD_OVERVIEW)
					},
					'maxSize' : {
						'message': getMessage(MAXIMUM, [LABEL_PROGRESS_RECORD_OVERVIEW, 30])
					}
			 },
			 '#txaProgressRecordDetail': {
					'custom2[prohibitionCharacter]': {
						'message': getMessage(SPECIALCHARACTERS, LABEL_PROGRESS_RECORD_DETAIL)
					},
					'maxSize' : {
						'message': getMessage(MAXIMUM, [LABEL_PROGRESS_RECORD_DETAIL, 300])
					}
			 }
		 },
		onValidationComplete: function(form, status){
			if(status) {
				var isFile = true;
				//Call file loaded
				isFileLoaded(function(val) {
					isFile = val;
				});
				var errorDiv = $('.alert-error');
				//Check not file not found
				if (!isFile) {
					errorDiv.show();
					errorDiv.html(getMessage('E0137'));
					scrollOnTop();
					return false;
				}
				errorDiv.hide();
				
				//If validation is Ok submit form
				return true;
			}
		}
	});
});

(function($){
    $.fn.validationEngineLanguage = function(){
    };
    $.validationEngineLanguage = {
        newLang: function(){
            $.validationEngineLanguage.allRules = {
                "required": { 
                    "regex": "none",
                    "alertText": '',
                },
                "prohibitionCharacter": {
                     "regex":  patternRegex.isNotSpecialCharacter,
                     "alertText": ''
                 },
                "datetime": {
                	"regex": patternRegex.isDateTimeFormat,
                	"alertText": ''
                },
                "maxSize": {
                    "regex": "none",
                    "alertText": '',
                },
                "halfwidthAlphanumeric": {
	            	 "regex":  patternRegex.halfwidthAlphanumeric,
	                 "alertText": ''
                }
            };
            
        }
    };
    $.validationEngineLanguage.newLang();
})(jQuery);

/**
 * Check validate  response type code
 * @param item
 * @returns
 */
function changeResponseTypeCode(item) {
	var selectedVal = item.value;
	switch(selectedVal) {
		case CORESPONSE_TYPE_CODE.TOKYO_SUPPORT:
				$('.lstNoticeTypeCode').addClass('disable-item');
				$('.supportCode').removeClass('disable-item');
				//Add attributed disabled dropdown list 
				$('.lstNoticeTypeCode').attr('disabled','true');
				//Set blank item list notice type code
				$('.lstNoticeTypeCode').val('');
			break;
		case CORESPONSE_TYPE_CODE.NOTICE_DELIVERY:
				$('.lstNoticeTypeCode').removeClass('disable-item');
				$('.supportCode').addClass('disable-item');
				$("input[name='rdoSupportCode']").prop('checked', false);
				//Remove attributed disabled dropdown list 
				$('.lstNoticeTypeCode').removeAttr('disabled');
			break;
		default:
				$('.lstNoticeTypeCode').addClass('disable-item');
				$('.supportCode').addClass('disable-item');
				$("input[name='rdoSupportCode']").prop('checked', false);
				//Add attributed disabled dropdown list 
				$('.lstNoticeTypeCode').attr('disabled','true');
				//Set blank item list notice type code
				$('.lstNoticeTypeCode').val('');
			break;
	}
}

/**
 * Upload file method
 * @param idInput
 * @param idFile
 * @returns
 */
function uploadFileImg(idInput, idFile) {
    idInput1 = "#" + idInput;
    idFile1 = "#" + idFile;
    $(idFile1).click();
}
 
/**
 * Event change file selected
 * @param idFile
 * @returns
 */
function changeFile(idFile) {
	 idFile1 = "#" + idFile;
	 var file = $(idFile1).val();
	 
	 let textUpload = file.replace(/^.*[\\\/]/, '');
	 //Check format file style is correct
	 if (file != '' && !isValidFileType(file)) {
		 showMessage('E0109', [textUpload]);
		 $(idInput1).val('');
		 $(idFile1).val('');
		 return;
	 }
	 else if (!isOkfileSize(idFile, textUpload)) {
		$(idFile1).val('');
		 
		return;
	}
	 
    $(idInput1).val(textUpload);
}

function isFileLoaded(val) {
	var file = $('#real-file-1')[0].files[0];
	var file2 = $('#real-file-2')[0].files[0];
	var file3 = $('#real-file-3')[0].files[0];
    if (file != '' && file != undefined) {
    	callCheckFile(file, val); 
    } 
    
    if (file2 != '' && file2 != undefined) {
    	callCheckFile(file2, val); 
    } 
    if (file3 != '' && file3 != undefined) {
    	callCheckFile(file3, val); 
    }
  
}

function callCheckFile(file, callbackVal) {
	   var form = new FormData();
	    form.append('file', file);
	     $.ajax({
	            type: "POST",
	            enctype: 'multipart/form-data',
	            url: BASE_URL + "/GEB0110/checkFile",
	            dataType: 'json',
	            headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
	            data: form,
	            async: false,
	            processData: false,
	            contentType: false,
	            cache: false,
	            success: function(data, textStatus, xhr) {
	            	callbackVal(true);
	            },
	            error: function(jqXHR, status, errorThrown) {
	                //Check file not found
	                if (jqXHR.status == 0) {
	                   // errorDiv.html(getMessage('E0137'));
	                    callbackVal(false);
	                } 
	            }
	        });
}

/**
 * Show alert message with dialog
 * @param idMessage
 * @param labelMessage
 * @returns
 */
function showMessage(idMessage, labelMessage) {

	var message = getMessage(idMessage, labelMessage);
	$('#message').html(message);
	
	$("#alertMessage").dialog({
		modal:true, //モーダル表示
		title: dialog.title, //タイトル
		buttons: {
		"OK": function() {
			$(this).dialog("close"); 
			}
		}
	});
}

function isOkfileSize(idFile, textUpload) {
	var sizeinbytes;
	if (window.ActiveXObject) {
		var fso = new ActiveXObject("Scripting.FileSystemObject");
		var filepath = document.getElementById(idFile).value;
		var thefile = fso.getFile(filepath);
		sizeinbytes = thefile.size;
	} else {
		var file =  document.getElementById(idFile).files[0];
		 sizeinbytes = file != undefined ? file.size : '';
	}

	var fSExt = new Array('B', 'K', 'M', 'G');
	var fSize = sizeinbytes;
	var i = 0;
	while (fSize > 900) {
		fSize /= 1024;
		i++;
	}
	
	var fileSize = (Math.round(fSize * 100) / 100);
	
	var str = $('#fileMaxSize').val();
	
	var fszById = str.slice(0, -1);
	
	var strRealFile = fileSize + ' ' + fSExt[i];
	//Get last char str get file size.
	var lastChar = strRealFile.substr(strRealFile.length - 1);
	//Get last character str config max file
	var lastCharSys = str.substr(str.length - 1);
	//Check filesize
	if (lastChar == lastCharSys && fileSize > fszById) {
		
		showMessage('E0110', [textUpload, fszById + ' ' + fSExt[i]]);
		return false;
	} else {
		return true;
	}
}

/**
 * Submit form when back button
 * @param apartmentId
 * @returns
 */
function turnBack(apartmentId) {
	$('input[name=apartmentId]').val(apartmentId);
	$('#submitBackbutton').submit();
}

/**
 * Check file type upload
 * @param file
 * @returns
 */
function isValidFileType(file) {

	var exts = [ "txt", "csv", "png", "jpg", "gif", "bmp" ];
	// first check if file field has any value
	if (file) {
		// split file name at dot
		var get_ext = file.split('.');
		// reverse name to check extension
		get_ext = get_ext.reverse();
		// check file type is valid as given in 'exts' array
		if ($.inArray(get_ext[0].toLowerCase(), exts) > -1) {
			return true;
		}
	}
	return false;
}

/**
 * 
 * @param attachNo
 * @returns
 */
function downloadFile(attachNo) {
	window.open(BASE_URL + '/GEB0110/downloadFile/'+attachNo, '_blank');
}