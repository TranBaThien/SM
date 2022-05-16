/**
 * 
 */

/* Error code for Half-width/半角Alphanumeric/英数字 */
const HALFWIDTH_ALPHANUMERIC = 'E0005';

const FULLWIDTH_CHARACTERS = 'E0006';

/* Error code for Full-width/全角Katakana/カタカナ */
const FULLWIDTH_KATAKANA = 'E0007';

const MAXIMUM = 'E0012';

const DATE = 'E0008';

const MAX_LENGTH_APARTMENT_NAME = '100';

// Clear search condition
// 検索条件クリア
function clearsearchcondition() {
	
	$("#clear").on("click", function() {
		$(".text-input-1").val("");
		$(".checkbox-item").prop("checked", false);
	});
		
}

function validation () {
	
	//Jquery validation engine
	$("#searchform").validationEngine({
	    promptPosition : "bottomLeft",
	    showArrowOnRadioAndCheckbox : true,
	    focusFirstField : true,
	    scroll : false,
	    'custom_error_messages': {
	        "#mansion": {	            
	            'custom[halfwidthAlphanumeric]' : {
	                'message': getMessage(HALFWIDTH_ALPHANUMERIC, ['マンション名'])
	            },
	            'custom[fullwidthCharacters]' : {
	            	'message': getMessage(FULLWIDTH_CHARACTERS, ['マンション名'])
	            },
	            'custom[kana]' : {
	            	'message': getMessage(FULLWIDTH_KATAKANA, ['マンション名'])
	            },
	            'maxSize' : {
	            	'message': getMessage(MAXIMUM, ['マンション名', MAX_LENGTH_APARTMENT_NAME])
	            }
	        },
	        "#datepicker-1-noTime": {
	        	'custom[date]' : {
	        		'message': getMessage(DATE, ['登録日－開始'])
	        	}
	        },
	        "#datepicker-2-noTime": {
	        	'custom[date]' : {
	        		'message': getMessage(DATE, ['登録日－終了'])
	        	}
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
                /* Check "Half-width/半角Alphanumeric/英数字"  */
                "halfwidthAlphanumeric": {
                     "regex":  /^[ｦ-ﾟ ､0-9a-zA-Z@.]*$/,
                     "alertText": getMessage('E0005', ['*']),
                 },
                 "fullwidthCharacters": {
                     "regex":  /^[ｦ-ﾟ ､a-zA-Z@.]*$/,
                     "alertText": getMessage('E0006', ['*']),
                 },
                 /* Check Full-width/全角    Katakana/カタカナ */
                 "kana": {
                     "regex": /^[０-９－ａ-ｚＡ-Ｚぁ-んァ-ー一-龠　]+$/,
                     "alertText": getMessage('E0007', ['*']),
                 },
                 "date": {
                     "regex": /^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])$/,
                     "alertText": getMessage('E0008', ['*'])
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