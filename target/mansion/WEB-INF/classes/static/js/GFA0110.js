$(document).ready(function(){
	var submitType = '';
	$('#btnTmpsave').on('click', function(){
		submitType = 'btnTmpsave';
	});
	$('#btn-register').on('click', function(){
		submitType = 'btn-register';
	});
	$('#btnRestore').on('click', function(){
		submitType = 'btnRestore';
	});
    $(".advice-notification-register").validationEngine('attach',{
		// 以下のパラメータは任意
		promptPosition: "bottomLeft",//エラー文の表示位置
		showArrowOnRadioAndCheckbox: true,//エラー箇所の図示
		focusFirstField: true,//エラー時に一番文頭の入力フィールドにフォーカスさせるかどうか
		scroll: false,
		onValidationComplete: function(form, status){
			var action = $(form).attr('action');
		    if (submitType =='btnRestore') {
				$(form).attr('action', action + '?action=restore');
				registrationGFA0110();
			}
			if(status == true) {
				if (submitType =='btn-register') {
					$(form).attr('action', action + '?action=register');
					$("#div3").dialog({
    					modal:true, //モーダル表示
    					title:"確認ダイアログ", //タイトル
    					width: 305,
    					buttons: {
    					"OK": function() {
    						var txtAdviceDetailsLength = $('textarea[name="txtAdviceDetails"]').val().split(/\r\n|\r|\n/).length;
    						$('input[name="numRowAdvice"]').val(txtAdviceDetailsLength);
    						registrationGFA0110();
    						},
    					"キャンセル": function() {
    						$(this).dialog("close"); 
    						}
    					}
    				});
				} else if (submitType =='btnTmpsave') {
					$(form).attr('action', action + '?action=temp');
					$("#div4").dialog({
    					modal:true, //モーダル表示
    					title:"確認ダイアログ", //タイトル
    					width: 305,
    					buttons: {
    					"OK": function() {
    						var txtAdviceDetailsLength = $('textarea[name="txtAdviceDetails"]').val().split(/\r\n|\r|\n/).length;
    						$('input[name="numRowAdvice"]').val(txtAdviceDetailsLength);
    						registrationGFA0110();
    						},
    					"キャンセル": function() {
    						$(this).dialog("close"); 
    						}
    					}
    				});
				}
			}
		}
	});
	    	
	$("input[type='radio']").click(function(e){
		if ($(this).is(':checked')){
			$(this).closest('div').focus();
		}
	});

	$('.correspondDate').on('click', function(){
		$('#datepicker').focus();
	});
 });
/*(function($){
    $.fn.validationEngineLanguage = function(){
    };
    $.validationEngineLanguage = {
        newLang: function(){
            $.validationEngineLanguage.allRules = {
                "required": { 
                    "regex": "none",
                    "alertText": getMessage('E0002', '対応日時'),
                    "alertTextCheckboxMultiple": "* 選択してください",
                    "alertTextCheckboxe": "* チェックボックスをチェックしてください"
                },
                "prohibitionCharacter": {
                    //"regex": /^[０-９－ａ-ｚＡ-Ｚぁ-んァ-ー一-龠　]+$/,
                     "regex":  /["'&<>,\\%]+$/,
                     "alertText": getMessage('E0015', '経過記録概要')
                 },
                "maxSize": {
                    "regex": "none",
                    "alertText": "* ",
                    "alertText2": "文字以下にしてください"
                },
            };
            
        }
    };
    $.validationEngineLanguage.newLang();
})(jQuery);*/

function registrationGFA0110() {
	var form = document.getElementsByName('formAdviceNotification');
	form[0].submit();
}

function GetSelectedValue(){
	var e = document.getElementById("country");
	var result = e.options[e.selectedIndex].value;
	
	if($('#result').val() == '') {
		$('#result').val($('#result').val() + result);	
	} else {
		$('#result').val($('#result').val() + '\n' + result);
	}
}

var numberOfLines = 0;
$('textarea[data-limit-rows=true]')
.on('keypress', function (event) {
    var textarea = $(this);
    var text = textarea.val();
    var numberOfLines = (text.match(/\n/g) || []).length + 1;
    if(event.which === 13 && numberOfLines == 6) {
      return false;
    }
});
