$(document).ready(function(){
	

/*  	$(".formUpdate").validationEngine({
    	// 以下のパラメータは任意
		 promptPosition: "bottomLeft",//エラー文の表示位置
		 showArrowOnRadioAndCheckbox: true,//エラー箇所の図示
		 focusFirstField: true,//エラー時に一番文頭の入力フィールドにフォーカスさせるかどうか
		 scroll: false,
	});  */
 	
 	
		$(".uploadMansion").validationEngine({
			// 以下のパラメータは任意
			promptPosition: "bottomLeft",//エラー文の表示位置
			showArrowOnRadioAndCheckbox: true,//エラー箇所の図示
			focusFirstField: true,//エラー時に一番文頭の入力フィールドにフォーカスさせるかどうか
			scroll: false,
			onValidationComplete: function(form, status){
				if(status) {
					$("#div3").dialog({
						modal:true, //モーダル表示
						title: "テストダイアログ",//タイトル
						buttons: { //ボタン
						"OK": function() {
							$(this).dialog("close");
							form[0].submit();
							},
						"キャンセル": function() {
							$(this).dialog("close");
							event.preventdefault();
							}
						},
					});
				}
			}
		});
 	
});