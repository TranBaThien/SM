/* Confirm message code */
const CONFIRM_MESSAGE_ID = 'C0003';
const REAL_FILE = 'real-file';
const MSG_CODE_NOT_FOUND_FILE = 'E0137';
const MSG_CODE_CHECK_CSV_FILE = 'E0109';
const MSG_CODE_CHECK_ZERO_FILE_SIZE = 'E0130';
const MSG_CODE_CHECK_MAX_FILE_SIZE = 'E0110';

$(document).ready(function() {
	
	$(".uploadProgress").validationEngine({
		// 以下のパラメータは任意
		promptPosition : "bottomLeft",// エラー文の表示位置
		showArrowOnRadioAndCheckbox : true,// エラー箇所の図示
		focusFirstField : true,// エラー時に一番文頭の入力フィールドにフォーカスさせるかどうか
		scroll : false,
		maxErrorsPerField : 1,
		onValidationComplete : function(form, status) {
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
	});
	
	$('#file').on('click', function(){
		$("#div3")
		.html( getMessage(CONFIRM_MESSAGE_ID, '経過記録一括登録'))
		.dialog({
			modal : true, // モーダル表示
			title : dialog.title,// タイトル
			buttons : { // ボタン
				"OK" : function() {
					var form = document.getElementsByName('uploadProgress');
					$(this).dialog("close");
					// valid file before submit
					var isValid = $("#uploadProgress").validationEngine('validate');
					//If valid
					if (isValid) {
						validateFileBeforeSubmit(form);
					}
				},
				"キャンセル" : function() {
					$(this).dialog("close");
				}
			},
		});
	});
});

(function($) {
	$.fn.validationEngineLanguage = function() {
	};
	$.validationEngineLanguage = {
		newLang : function() {
			$.validationEngineLanguage.allRules = {
				"required" : {
					"regex" : "none",
					"alertText" : getMessage('E0001', 'アップロードファイル')
				}
			};

		}
	};
	$.validationEngineLanguage.newLang();
	$('#real-file').on('change', function(e) {
		// validate file when change file.
		validateFileBeforeSubmit();
	});
})(jQuery);

function isFileLoaded(val) {
	var file = $('#real-file')[0].files[0];
    if (file != '' && file != undefined) {
    	callCheckFile(file, val); 
    }
}

function callCheckFile(file, callbackVal) {
	   var form = new FormData();
	    form.append('file', file);
	     $.ajax({
	            type: "POST",
	            enctype: 'multipart/form-data',
	            url: baseUrl + "/GEC0110/checkFile",
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
	                    callbackVal(false);
	                } 
	            }
	        });
}

/**
 * Upload file method
 * 
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
 * check File max size
 * 
 * @param idFile
 * @returns
 */
// ファイルのアップロードサイズの上限チェック
function isOkfileSize(idFile) {
	var sizeinbytes;
	if (window.ActiveXObject) {
		var fso = new ActiveXObject("Scripting.FileSystemObject");
		var filepath = document.getElementById(idFile).value;
		var thefile = fso.getFile(filepath);
		sizeinbytes = thefile.size;
	} else {
		sizeinbytes = document.getElementById(idFile).files[0].size;
	}

	var fSExt = new Array('B', 'K', 'M', 'G');
	var fSize = sizeinbytes;
	var i = 0;
	while (fSize > 900) {
		fSize /= 1024;
		i++;
	}


	var str = $('#fileMaxSize').val();

	var fszById = str.slice(0, -1);

	var strRealFile = fSize + ' ' + fSExt[i];
	// Get last char str get file size.
	var lastChar = strRealFile.substr(strRealFile.length - 1);
	// Get last character str config max file
	var lastCharSys = str.substr(str.length - 1);
	// Check filesize
	if (lastChar == lastCharSys && fSize > fszById) {
		return false;
	} else {
		return true;
	}
}

/**
 * Check file size = 0 ?
 * 
 * @param idFile
 * @returns
 */
function isfileSizeNotIsZero(idFile) {
	var sizeinbytes;
	if (window.ActiveXObject) {
		var fso = new ActiveXObject("Scripting.FileSystemObject");
		var filepath = document.getElementById(idFile).value;
		var thefile = fso.getFile(filepath);
		sizeinbytes = thefile.size;
	} else {
		sizeinbytes = document.getElementById(idFile).files[0].size;
	}

	var fSize = sizeinbytes;
	var i = 0;
	while (fSize > 900) {
		fSize /= 1024;
		i++;
	}

	var fileSize = (Math.round(fSize * 100) / 100);

	// Check filesize
	return fileSize != 0;
}

/**
 * Event change file selected
 * 
 * @param fileName
 * @returns
 */
function getExtension(fileName) {
	var parts = fileName.split('.');
	return parts[parts.length - 1];
}

/**
 * Check file type is CSV?
 * 
 * @param idFile
 * @returns
 */
function isCSV(fileName) {
	var ext = getExtension(fileName);
	if (ext && ext.toLowerCase() === 'csv') {
		return true;
	}
	return false;
}

/**
 * Check file type is CSV?
 * 
 * @param idFile
 * @returns
 */
// ファイルの形式が以下に該当しない場合、エラー。
function isCsvFile(idFile) {
	var file = $('#real-file');
	return isCSV(file.val());
}

/**
 * Turn back to page before with submit form post method
 */
function turnBack() {
	window.location.href = baseUrl + '/GBA0110';
}

/**
 * Check file is valid before submit
 * @returns
 */
function validateFileBeforeSubmit(form) {
	var idFile1 = "#" + REAL_FILE;
	if ($(idFile1).val() === '') {
		return;
	}
	let textUpload = $(idFile1).val().replace(/^.*[\\\/]/, '');
    // ファイルの形式が以下に該当しない場合、エラー。 テキストファイル（.csv）
	if (!isCsvFile(REAL_FILE)) {
		showDialogMessage(getMessage(MSG_CODE_CHECK_CSV_FILE, [ textUpload ]));
		$(idInput1).val('');
		return;
	}
	// ファイル存在チェック ・参照ファイルが存在しない場合はエラー。
	// 空ファイルチェック
	// ・ファイルのサイズは0場合はエラー。
	if (!isfileSizeNotIsZero(REAL_FILE)) {
		showDialogMessage(getMessage(MSG_CODE_CHECK_ZERO_FILE_SIZE, [ textUpload ]));
		$(idInput1).val('');
		return;
	}
	// ファイルのアップロードサイズの上限チェック
	// ・ファイルのサイズをチェックする、ファイルのサイズは＞セッション情報のGEC0110_FILE_SIZE_MAXの場合はエラー。
	if (!isOkfileSize(REAL_FILE)) {
		var str = $('#fileMaxSize').val();
		showDialogMessage(getMessage(MSG_CODE_CHECK_MAX_FILE_SIZE, [textUpload, str]));
		$(idInput1).val('');
		return;
	}
	// validate success
	if (form) {
		form[0].submit();
	}
	// set file name
	if ($(idFile1).val()) {
		let textUpload = $(idFile1).val().replace(/^.*[\\\/]/, '');
		$(idInput1).val(textUpload);
	}
}

function showDialogMessage(message) {
	$("#div3")
	.html(message)
	.dialog({
		modal : true, // モーダル表示
		title : dialog.title, // タイトル
		buttons : {
			"OK" : function() {
				$(this).dialog("close");
			}
		}
	});
}