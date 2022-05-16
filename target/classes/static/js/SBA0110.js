const SBA0110_FIELD_NAME_PASSWORD_NOW = '現在のパスワード';
const SBA0110_FIELD_NAME_PASSWORD = '新しいパスワード';
const SBA0110_FIELD_PASSWORD_CONFIRM = '新しいパスワード（確認）';
const SBA0110_FIELD_MAIL = 'メールアドレス';
const SBA0110_FIELD_MAIL_CONFIRM = 'メールアドレス（確認）';
const SBA0110_MIN_LENGTH_PASSWORD = '8';
const SBA0110_MAX_LENGTH_PASSWORD = '16';
const SBA0110_MAX_LENGTH_MAIL_ADDRESS = '120';

/* Error code for Required/必須 for input */
const REQUIRED_FOR_INPUT = 'E0002';
/* Error code for Half-width/半角Alphanumeric/英数字  */
const HALFWIDTH_ALPHANUMERIC = 'E0005';
/* Error code for email /メール アドレス */
const MAIL_ADDRESS = 'E0009';
/* Error code for size /最小 最大 */
const SIZE = 'E0014';
/* Error code for minimum/最小*/
const MINIMUM = 'E0013';
/* Error code for maximum/最大*/
const MAXIMUM = 'E0012';
/*Error code for EQUALS*/
const EQUALS = 'E0105';

$(document).ready(function() {
	
	//Remove disabled select lstCorrespondTypeCode when onclick submit
	$('.btnRegister').on('click', function(){
		
        $("#div3")
        .html(getMessage('C0003', 'パスワード変更'))
        .dialog({
            modal:true, //モーダル表示
            title: dialog.title,//タイトル
            buttons: { //ボタン
            "OK": function() {
                $(this).dialog("close");
				var isValid = $(".formUpdate").validationEngine('validate');
				//If valid
				if (isValid) {
					var valUserTypeCode = $('input[name=userTypeCode]').val();
					
					var biginingPasswordChangeFlag = $('input[name=biginingPasswordChangeFlag]').val();
					
					if (valUserTypeCode == 5 && biginingPasswordChangeFlag == 0) {
						$("#formUpdate").submit();
					} else {
						var action = BASE_URL + '/SBA0110/save2';
						
						$("#formUpdate").attr('action', action);
						$("#formUpdate").submit();
					}
				}
                },
            "キャンセル": function() {
                $(this).dialog("close");
                }
            }
        });
	});
	
	
	// Event Biding message success from server
	if (MESSAGE_SUCCESS === 'GBA0110') {
		$("#div4").html(getMessage('I0001', [ 'パスワード', '変更' ])).dialog(
				{
					modal : true,
					title : dialog.title,
					beforeClose : function(event, ui) {
						window.location = BASE_URL + "/GBA0110";
					},
					buttons : {
						"OK" : function() {
							window.location = BASE_URL + "/GBA0110";
						}
					}
				});
	}
	if (MESSAGE_SUCCESS === 'MBA0110') {
		$("#div4").html(getMessage('I0001', [ 'パスワード', '変更' ])).dialog(
				{
					modal : true,
					title : dialog.title,
					beforeClose : function(event, ui) {
						window.location = BASE_URL + "/MBA0110";
					},
					buttons : {
						"OK" : function() {
							window.location = BASE_URL + "/MBA0110";
						}
					}
				});
	}

    $(".formUpdate").validationEngine('attach',{
        // 以下のパラメータは任意
        promptPosition: "bottomLeft",//エラー文の表示位置
        showArrowOnRadioAndCheckbox: true,//エラー箇所の図示
        focusFirstField: true,//エラー時に一番文頭の入力フィールドにフォーカスさせるかどうか
        scroll: false,
        maxErrorsPerField: 1,
                'custom_error_messages' : {
                    "#pwdPasswordNow" : {
                        'required' : {
                            'message' : getMessage(REQUIRED_FOR_INPUT,SBA0110_FIELD_NAME_PASSWORD_NOW)
                        },
                        'custom[halfsizeAlphanumeric]' : {
                            'message' : getMessage(HALFWIDTH_ALPHANUMERIC,SBA0110_FIELD_NAME_PASSWORD_NOW)
                        },
                        'minSize' : {
                            'message' : getMessage(SIZE, [SBA0110_FIELD_NAME_PASSWORD_NOW, SBA0110_MIN_LENGTH_PASSWORD,SBA0110_MAX_LENGTH_PASSWORD ])
                        },
                        'maxSize' : {
                            'message' : getMessage(SIZE, [SBA0110_FIELD_NAME_PASSWORD_NOW, SBA0110_MIN_LENGTH_PASSWORD, SBA0110_MAX_LENGTH_PASSWORD ])
                        }
                    },
                    "#pwdPassword" : {
                        'required' : {
                            'message' : getMessage(REQUIRED_FOR_INPUT,SBA0110_FIELD_NAME_PASSWORD)
                        },
                        'custom[halfsizeAlphanumeric]' : {
                            'message' : getMessage(HALFWIDTH_ALPHANUMERIC,SBA0110_FIELD_NAME_PASSWORD)
                        },
                        'minSize' : {
                            'message' : getMessage(SIZE, [SBA0110_FIELD_NAME_PASSWORD, SBA0110_MIN_LENGTH_PASSWORD ,SBA0110_MAX_LENGTH_PASSWORD])
                        },
                        'maxSize' : {
                            'message' : getMessage(SIZE, [SBA0110_FIELD_NAME_PASSWORD, SBA0110_MIN_LENGTH_PASSWORD, SBA0110_MAX_LENGTH_PASSWORD ])
                        }
                    },
                    "#pwdPasswordConfirm" : {
                        'required' : {
                            'message' : getMessage(REQUIRED_FOR_INPUT, SBA0110_FIELD_PASSWORD_CONFIRM)
                        },
                        'equals' : {
                            'message' : getMessage(EQUALS, [ SBA0110_FIELD_PASSWORD_CONFIRM,SBA0110_FIELD_NAME_PASSWORD ])
                        }
                    },
                    '#txtMail' : {
                        'required' : {
                            'message' : getMessage(REQUIRED_FOR_INPUT, SBA0110_FIELD_MAIL)
                        },
                        'custom[halfsizeAlphanumeric]' : {
                            'message' : getMessage(HALFWIDTH_ALPHANUMERIC,SBA0110_FIELD_MAIL)
                        },
                        'custom[email]' : {
                            'message' : getMessage(MAIL_ADDRESS, SBA0110_FIELD_MAIL)
                        },
                        'maxSize' : {
                            'message' : getMessage(MAXIMUM, [SBA0110_FIELD_MAIL, SBA0110_MAX_LENGTH_MAIL_ADDRESS ])
                        }
                    },
                    '#txtMailConfirm' : {
                        'required' : {
                            'message' : getMessage(REQUIRED_FOR_INPUT,SBA0110_FIELD_MAIL_CONFIRM)
                        },
                        'custom[email]' : {
                            'message' : getMessage(MAIL_ADDRESS,SBA0110_FIELD_MAIL_CONFIRM)
                        },
                        'equals' : {
                            'message' : getMessage(EQUALS, [SBA0110_FIELD_MAIL_CONFIRM, SBA0110_FIELD_MAIL ])
                        }
                    },
                },
        onValidationComplete: function(form, status){
        	return status;
        }
    });
    
 // Get the text field that we're going to track
    let pw_now = document.getElementById("pwdPasswordNow");
    let pw = document.getElementById("pwdPassword");
    let pw_confirm = document.getElementById("pwdPasswordConfirm");
     
    // See if we have an autosave value
    // (this will only happen if the page is accidentally refreshed)
    if (sessionStorage.getItem("pw_now") || sessionStorage.getItem("pw") || sessionStorage.getItem("pw_confirm")) {
      // Restore the contents of the text field
    	pw_now.value = sessionStorage.getItem("pw_now");
    	pw.value = sessionStorage.getItem("pw");
    	pw_confirm.value = sessionStorage.getItem("pw_confirm");
    }
     
    // Listen for changes in the text field
    pw_now.addEventListener("change", function() {
      // And save the results into the session storage object
      sessionStorage.setItem("pw_now", pw_now.value);
    });
    
    // Listen for changes in the text field
    pw.addEventListener("change", function() {
      // And save the results into the session storage object
      sessionStorage.setItem("pw", pw.value);
    });
    
    // Listen for changes in the text field
    pw_confirm.addEventListener("change", function() {
      // And save the results into the session storage object
      sessionStorage.setItem("pw_confirm", pw_confirm.value);
    });
    showHideEye();
});

(function($){
    $.fn.validationEngineLanguage = function(){
    };
    $.validationEngineLanguage = {
        newLang: function(){
            $.validationEngineLanguage.allRules = {
                "required": { 
                    "regex": "none",
                    "alertText": getMessage(REQUIRED_FOR_INPUT, ['*']),
                },
                "halfsizeAlphanumeric": {
                     "regex":  patternRegex.halfwidthAlphanumeric,
                     "alertText": getMessage(HALFWIDTH_ALPHANUMERIC, ['*']),
                 },
                "maxSize": {
                    "regex": "none",
                    "alertText": "* ",
                    "alertText2": getMessage(MAXIMUM, ['* ','*']),
                },
                "minSize": {
                    "regex": "none",
                    "alertText": "* ",
                    "alertText2": getMessage(MINIMUM, ['*','*']),
                },
                "email": {
                    "regex": patternRegex.email,
                    "alertText": getMessage(MAIL_ADDRESS, ['*']),
                },
                "equals": {
                    "regex": "none",
                    "alertText": getMessage(EQUALS, ['*','*']),
                },
            };
            
        }
    };
    $.validationEngineLanguage.newLang();
})(jQuery);


function turnBack() {
	var userTypeCode = $('input[name=userTypeCode]').val();
	if (userTypeCode != '5') {
		window.location.href = BASE_URL + "/GBA0110";
	} else {
		window.location.href = BASE_URL + "/MBA0110";
	}
}
