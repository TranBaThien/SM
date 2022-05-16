const errorMessage_E0002 = "E0002";
const errorMessage_E0005 = "E0005";
const errorMessage_E0014 = "E0014";
const errorMessage_E0105 = "E0105";
const title_Password = "パスワード";
const title_PasswordConfirm = "パスワード（確認）";
const minDigit_Password = "8";
const maxDigit_Password = "16";

$(document).ready (function(){
	$('#pwdPassword').val(GLA0110_GLOBAL_VAR.password);
 	$('#pwdPasswordConfirm').val(GLA0110_GLOBAL_VAR.passwordConfirm);
 	
 	document.getElementById("pwdPassword").focus();
 	  if (GLA0110_GLOBAL_VAR.turnBackGJA0120 === "true")
 		 $("#div5").dialog({
 			modal:true, //モーダル表示
 			title: dialog.title, //タイトル
 			beforeClose: function( event, ui ) {
 				turnBack(GLA0110_GLOBAL_VAR.apartmentId); 				
			},
 			buttons: {
 			"OK": function() {
 				$(this).dialog("close"); 					
 				turnBack(GLA0110_GLOBAL_VAR.apartmentId); 
 				},
 			}
 		});
 	
 	if (GLA0110_GLOBAL_VAR.message !== null)
		$("#div4").dialog({
			modal:true, //モーダル表示
			title: dialog.title, //タイトル
			beforeClose: function( event, ui ) {				
				$('#submitBackGJA0110').submit();
			},
			buttons: {
			"OK": function() {
				$(this).dialog("close"); 					
				$('#submitBackGJA0110').submit();
				},
			}
		});
     
 	
	$('#btnsub').on('click', function(e){

		$("#div3").dialog({
		modal:true, //モーダル表示
		title: dialog.title, //タイトル
		buttons: { //ボタン
		"OK": function() {
			$(this).dialog("close");
			//Validation before submit form
			var isValid = $("#formUpdate").validationEngine('validate');
			//If valid
			if (isValid) {
				$("#formUpdate").submit();
			}
			},
		"キャンセル": function() {
			$(this).dialog("close");
			}
		},
	});
	});
 	
 	
     $(".formUpdate").validationEngine('attach',{

			// 以下のパラメータは任意
			promptPosition: "bottomLeft",//エラー文の表示位置
			showArrowOnRadioAndCheckbox: true,//エラー箇所の図示
			focusFirstField: true, //エラー時に一番文頭の入力フィールドにフォーカスさせるかどうか
			scroll: false,
			'custom_error_messages': {
		      "#pwdPassword": {
                    'required': {
                        'message': getMessage(errorMessage_E0002, title_Password)
                    },
                    'custom[halfsizeAlphanumeric]' : {
                        'message': getMessage(errorMessage_E0005, title_Password)
                    }, 
                    'minSize' : {
                        'message': getMessage(errorMessage_E0014, [title_Password,'8','16'])
                    },
                    'maxSize' : {
                        'message': getMessage(errorMessage_E0014, [title_Password,'8','16'])
                    }
                },
                "#pwdPasswordConfirm": {
                    'required': {
                        'message': getMessage(errorMessage_E0002 , title_PasswordConfirm)
                    },
                	'equals' : {
                		'message': getMessage(errorMessage_E0105 , [title_Password,title_PasswordConfirm])
                	}
                },
                     
            },
			maxErrorsPerField: 1,
			onValidationComplete: function(form, status){
				return status;
			}
		});
     
  	showHideEye();
  	
  	if (GLA0110_GLOBAL_VAR.turnBackGJA0120 !== null || GLA0110_GLOBAL_VAR.message !== null){
  		 $("#confirm").prop('checked', true);
  	}
  	
  	 var checkBox = document.getElementById("confirm");
     if (checkBox.checked == true){
         document.getElementById("btnsub").disabled = false;
     } else {
         document.getElementById("btnsub").disabled = true;
     }
     
    $('#confirm').click(function() {
        if (checkBox.checked == true){
            document.getElementById("btnsub").disabled = false;
        } else {
            document.getElementById("btnsub").disabled = true;
        }
   });
}); 

(function($){
    $.fn.validationEngineLanguage = function(){
    };
    $.validationEngineLanguage = {
        newLang: function(){
            $.validationEngineLanguage.allRules = {
             // validate pwdPassWord
            		"required": { 
       	             "regex": "none",
       	             "alertText": getMessage(errorMessage_E0002 , title_Password)
       	         },
       	         "halfsizeAlphanumeric": {
                        "regex":  patternRegex.halfwidthAlphanumeric,
                        "alertText": getMessage(errorMessage_E0005, title_Password),
                    },
       	         "minSize": {
       	             "regex": "none",
       	             "alertText": "*" ,
       	             "alertText2": getMessage(errorMessage_E0014, [title_Password, minDigit_Password, maxDigit_Password])
       	         },
       	         "maxSize": {
       	             "regex": "none",
       	             "alertText": "*",
       	             "alertText2": getMessage(errorMessage_E0014, [title_Password, minDigit_Password, maxDigit_Password])
       	         },
       	         "equals": {
                       "regex": "none",
                      "alertText": getMessage(errorMessage_E0105, [ title_Password, title_PasswordConfirm])
                  },      
            };  
        }
    };
    $.validationEngineLanguage.newLang();
})(jQuery);

function turnBack(apartmentId) {
	$('input[name=apartmentId]').val(GLA0110_GLOBAL_VAR.apartmentId);
	$('#submitBackbutton').submit();
}
