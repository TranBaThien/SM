(function($){
    $.fn.validationEngineLanguage = function(){
    };
    $.validationEngineLanguage = {
        newLang: function(){
            $.validationEngineLanguage.allRules = {
             // validate pwdPassWord
			 "required": { 
	             "regex": "none",
	             "alertText": getMessage('E0002', 'パスワード')
	         },
	        "prohibitionCharacter": {
	              "regex": "/^[ｦ-ﾟ ､0-9a-zA-Z@.]*$/" ,
	              "alertText": "getMessage('E0005', 'パスワード')",
	              "alertText2": getMessage('E0005', 'パスワード（確認）')
	          },
	         "maxSize": {
	             "regex": "none",
	             "alertText": "* ",
	             "alertText2": getMessage('E0014', 'パスワード')
	         },
	         "minSize": {
	             "regex": "none",
	             "alertText": "* ",
	             "alertText2": getMessage('E0005', 'パスワード）')
	         },
	         
	         // validate pwdPassWordConfirm
			 "requiredPassWordConfirm": { 
	             "regex": "none",
	             "alertText": getMessage('E0002', 'パスワード（確認）')
	         },
	 
	         "maxSizePassWordConfirm": {
	             "regex": "none",
	             "alertText": "* ",
	             "alertText2": getMessage('E0014', 'パスワード（確認）')
	         },
	         "minSizePassWordConfirm": {
	             "regex": "none",
	             "alertText": "* ",
	             "alertText2": getMessage('E0014', 'パスワード（確認）')
	         },
	         "equals": {
                 "regex": "none",
                 "alertText": getMessage('E0105', ['パスワード', 'パスワード（確認）'])
             },
	         
	};
            
        }
    };
    $.validationEngineLanguage.newLang();
})(jQuery);
