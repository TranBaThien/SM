/* Error code for Required/必須 for select */
const REQUIRED1 = 'E0001';
/* Error code for Required/必須 for input */
const REQUIRED = 'E0002';
/* Error code for Half-width numbers/半角数字 */
const HALFWIDTH_NUMBERS = 'E0003';
/* Error code for Half-width/半角Alphanumeric/英数字 */
const HALFWIDTH_ALPHANUMERIC = 'E0005';
/* Error code for Full-width characters/全角文字 */
const FULLWIDTH_CHARACTERS = 'E0006';
/* Error code for Full-width/全角Katakana/カタカナ */
const FULLWIDTH_KATAKANA = 'E0007';
/* Error code for Mail address/アドレスAddress/アドレス */
const MAIL = 'E0009';
/* Error code for minimum/最小*/
const MINIMUM = 'E0013';
/* Error code for maximum/最大*/
const MAXIMUM = 'E0012';
/* Error code for range for minimum/最小 and maximum/最大 */
const RANGED = 'E0014';
	
$(document).ready(function() {
	const MCA0110_MAX_LENGTH_APARTMENT_NAME = '50';
	const MCA0110_MAX_LENGTH_APARTMENT_NAME_PHONETIC = '100';
	const MCA0110_MIN_LENGTH_APARTMENT_ZIP_CODE1 = '3';
	const MCA0110_MAX_LENGTH_APARTMENT_ZIP_CODE1 = '3';
	const MCA0110_MIN_LENGTH_APARTMENT_ZIP_CODE2 = '4';
	const MCA0110_MAX_LENGTH_APARTMENT_ZIP_CODE2 = '4';
	const MCA0110_MAX_LENGTH_APARTMENT_ADDRESS_2 = '100';
	const MCA0110_MAX_LENGTH_CONTACT_PROPERTY_ELSE = '30';
	const MCA0110_MIN_LENGTH_CONTACT_ZIP_CODE1 = '3';
	const MCA0110_MAX_LENGTH_CONTACT_ZIP_CODE1 = '3';
	const MCA0110_MIN_LENGTH_CONTACT_ZIP_CODE2 = '4';
	const MCA0110_MAX_LENGTH_CONTACT_ZIP_CODE2 = '4';
	const MCA0110_MAX_LENGTH_CONTACT_ADDRESS = '100';
	const MCA0110_MIN_LENGTH_CONTACT_TEL_NO1 = '3';
	const MCA0110_MAX_LENGTH_CONTACT_TEL_NO1 = '5';
	const MCA0110_MIN_LENGTH_CONTACT_TEL_NO2 = '4';
	const MCA0110_MAX_LENGTH_CONTACT_TEL_NO2 = '4';
	const MCA0110_MIN_LENGTH_CONTACT_TEL_NO3 = '4';
	const MCA0110_MAX_LENGTH_CONTACT_TEL_NO3 = '4';
	const MCA0110_MAX_LENGTH_CONTACT_NAME = '34';
	const MCA0110_MAX_LENGTH_CONTACT_NAME_PHONETIC = '34';
	const MCA0110_MAX_LENGTH_CONTACT_MAIL = '120';
	const MCA0110_MAX_LENGTH_CONTACT_MAIL_RE = '120';
	const MCA0110_MIN_LENGTH_TEMP_PASSWORD = '8';
	const MCA0110_MAX_LENGTH_TEMP_PASSWORD = '16';
	const MCA0110_MIN_LENGTH_TEMP_PASSWORD_RE = '8';
	const MCA0110_MAX_LENGTH_TEMP_PASSWORD_RE = '16';
	
    // Initial focus
    $('#txtApartmentName').focus();
    // Initial 都道府県 = 東京都
    $('#lblPrefectures').text("東京都");
    
    // Event click button back previous screen
    $('.back-previous-screen').on('click', function() {
        var previousScreen = sessionStorage.getItem('previousScreen');
        window.location.href = '/' + previousScreen;
    });
    
    // Generate ApartmentZipCode by zip code 自動入力
	$('#txtApartmentZipCode1').jpostal({
		click : '#validApartmentZipCode',
		postcode : [
			'#txtApartmentZipCode1',
			'#txtApartmentZipCode2'
		],
		address : {
			'#lstApartmentAddress1'  : '%4',
		},
		url : {
			'http'  : '/json/',
			'https' : '/json/',
		}
	});
	
    // Generate address by zip code 自動入力
	$('#txtContactZipCode1').jpostal({
		click : '#validContactAddress',
		postcode : [
			'#txtContactZipCode1',
			'#txtContactZipCode2'
		],
		address : {
			'#txtContactAddress'  : '%3%4%5',
		},
		url : {
			'http'  : '/json/',
			'https' : '/json/',
		}
	});
    
    // Disable btnNewRegistration 登録
    const currentScreenId = "MCA0110";
	const chkTermsConditionsKey = "MCA0110_chkTermsConditions";
	const screenId = "screenId";

	$("#chkTermsConditions").prop('checked', false);
	$("button[name='btnNewRegistration']").prop('disabled', true);
	if (window.sessionStorage) {
		sessionStorage.clear();
		sessionStorage.setItem(screenId, currentScreenId);
	}
    
	$("#chkTermsConditions").change(function() {
		if($("#chkTermsConditions").is(':checked') && sessionStorage.getItem(chkTermsConditionsKey) === "ON") {
			$("button[name='btnNewRegistration']").prop('disabled', false);
		} else {
			$("button[name='btnNewRegistration']").prop('disabled', true);
		}
	});
	
	$("#termsConditionsUrl").click(function () {
		sessionStorage.setItem(chkTermsConditionsKey, "ON");
		if($("#chkTermsConditions").is(':checked')) {
			$("button[name='btnNewRegistration']").prop('disabled', false);
		}
		window.open("SAA0110", "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=100,left=100,width=1000,height=700");
	});
	
	// Error message for validationEngine
	$(".user-register").validationEngine({
    	// 以下のパラメータは任意
		 promptPosition: "bottomLeft",//エラー文の表示位置
		 showArrowOnRadioAndCheckbox: true,//エラー箇所の図示
		 focusFirstField: true,//エラー時に一番文頭の入力フィールドにフォーカスさせるかどうか
		 scroll: false,
		 'custom_error_messages': {
			 '#txtApartmentName': {
					'required': {
						'message': getMessage(REQUIRED, ['基本情報のマンション名'])
					},
					'custom[halfwidthAlphanumeric]' : {
						'message': getMessage(HALFWIDTH_ALPHANUMERIC, ['基本情報のマンション名'])
					},
					'custom[fullwidthCharacters]' : {
						'message': getMessage(FULLWIDTH_CHARACTERS, ['基本情報のマンション名'])
					},
					'custom[kana]' : {
						'message': getMessage(FULLWIDTH_KATAKANA, ['基本情報のマンション名'])
					},
					'maxSize' : {
						'message': getMessage(MAXIMUM, ['基本情報のマンション名', MCA0110_MAX_LENGTH_APARTMENT_NAME])
					},
				},
				'#txtApartmentNamePhonetic': {
					'required': {
						'message': getMessage(REQUIRED, ['基本情報のマンション名フリガナ'])
					},
					'custom[kana]' : {
						'message': getMessage(FULLWIDTH_KATAKANA, ['基本情報のマンション名フリガナ'])
					},
					'maxSize' : {
						'message': getMessage(MAXIMUM, ['基本情報のマンション名フリガナ', MCA0110_MAX_LENGTH_APARTMENT_NAME_PHONETIC])
					},
				},
				'#txtApartmentZipCode1': {
					'required': {
						'message': getMessage(REQUIRED, ['基本情報の郵便番号1'])
					},
					'custom[halfwidthNumbers]' : {
						'message': getMessage(HALFWIDTH_NUMBERS, ['基本情報の郵便番号1'])
					},
					// instead of maxSize to checkRangeForSingleItem
				},
				'#txtApartmentZipCode2': {
					'required': {
						'message': getMessage(REQUIRED, ['基本情報の郵便番号2'])
					},
					 'custom[halfwidthNumbers]' : {
						'message': getMessage(HALFWIDTH_NUMBERS, ['基本情報の郵便番号2'])
					},
					// instead of maxSize to checkRangeForSingleItem
				},
				'#lstApartmentAddress1': {
					'custom[required]': {
						'message': getMessage(REQUIRED1, ['基本情報の住所1'])
					},
				},
				'#txtApartmentAddress2': {
					'required': {
						'message': getMessage(REQUIRED, ['基本情報の住所2'])
					},
					'custom[fullwidthCharacters]' : {
						'message': getMessage(FULLWIDTH_CHARACTERS, ['基本情報の住所2'])
					},
					'maxSize' : {
						'message': getMessage(MAXIMUM, ['基本情報の住所2', MCA0110_MAX_LENGTH_APARTMENT_ADDRESS_2])
					},
				},
				// TODO map with HTML
				'#rdoContactProperty': {
					'custom[required]': {
						'message': getMessage(REQUIRED1, ['連絡先の属性'])
					},					
					//TODO error message for group required
				},
				'#txtContactPropertyElse': {
					'custom[halfwidthAlphanumeric]' : {
						'message': getMessage(HALFWIDTH_ALPHANUMERIC, ['連絡先の属性－その他'])
					},
					'custom[fullwidthCharacters]' : {
						'message': getMessage(FULLWIDTH_CHARACTERS, ['連絡先の属性－その他'])
					},
					'custom[kana]' : {
						'message': getMessage(FULLWIDTH_KATAKANA, ['連絡先の属性－その他'])
					},
					'maxSize' : {
						'message': getMessage(MAXIMUM, ['連絡先の属性－その他', MCA0110_MAX_LENGTH_CONTACT_PROPERTY_ELSE])
					},
				},
				'#txtContactZipCode1': {
					'required': {
						'message': getMessage(REQUIRED, ['連絡先の郵便番号1'])
					},
					'custom[halfwidthNumbers]' : {
						'message': getMessage(HALFWIDTH_NUMBERS, ['連絡先の郵便番号1'])
					},
					// instead of maxSize to checkRangeForSingleItem
				},
				'#txtContactZipCode2': {
					'required': {
						'message': getMessage(REQUIRED, ['連絡先の郵便番号2'])
					},
					'custom[halfwidthNumbers]' : {
						'message': getMessage(HALFWIDTH_NUMBERS, ['連絡先の郵便番号2'])
					},
					// instead of maxSize to checkRangeForSingleItem
				},
				'#txtContactAddress': {
					'required': {
						'message': getMessage(REQUIRED, ['連絡先の住所'])
					},
					'custom[fullwidthCharacters]' : {
						'message': getMessage(FULLWIDTH_CHARACTERS, ['連絡先の住所'])
					},
					'maxSize' : {
						'message': getMessage(MAXIMUM, ['連絡先の住所', MCA0110_MAX_LENGTH_CONTACT_ADDRESS])
					},
				},
				'#txtContactTelno1': {
					'required': {
						'message': getMessage(REQUIRED, ['連絡先の電話番号1'])
					},
					'custom[halfwidthNumbers]' : {
						'message': getMessage(HALFWIDTH_NUMBERS, ['連絡先の電話番号1'])
					},
					// instead of maxSize to checkRangeForSingleItem
				},
				'#txtContactTelno2': {
					'required': {
						'message': getMessage(REQUIRED, ['連絡先の電話番号2'])
					},
					'custom[halfwidthNumbers]' : {
						'message': getMessage(HALFWIDTH_NUMBERS, ['連絡先の電話番号2'])
					},
					// instead of maxSize to checkRangeForSingleItem
				},
				'#txtContactTelno3': {
					'required': {
						'message': getMessage(REQUIRED, ['連絡先の電話番号3'])
					},
					'custom[halfwidthNumbers]' : {
						'message': getMessage(HALFWIDTH_NUMBERS, ['連絡先の電話番号3'])
					},
					// instead of maxSize to checkRangeForSingleItem
				},
				'#txtContactName': {
					'required': {
						'message': getMessage(REQUIRED, ['連絡先の氏名'])
					},
					'custom[halfwidthAlphanumeric]' : {
						'message': getMessage(HALFWIDTH_ALPHANUMERIC, ['連絡先の氏名'])
					},
					'custom[fullwidthCharacters]' : {
						'message': getMessage(FULLWIDTH_CHARACTERS, ['連絡先の氏名'])
					},
					'maxSize' : {
						'message': getMessage(MAXIMUM, ['連絡先の氏名', MCA0110_MAX_LENGTH_CONTACT_NAME])
					},
				},
				'#txtContactNamePhonetic': {
					'required': {
						'message': getMessage(REQUIRED, ['連絡先の氏名フリガナ'])
					},
					'custom[kana]' : {
						'message': getMessage(FULLWIDTH_KATAKANA, ['連絡先の氏名フリガナ'])
					},
					'maxSize' : {
						'message': getMessage(MAXIMUM, ['連絡先の氏名フリガナ', MCA0110_MAX_LENGTH_CONTACT_NAME_PHONETIC])
					},
				},
				'#txtContactMail': {
					'required': {
						'message': getMessage(REQUIRED, ['連絡先のメールアドレス'])
					},
					'custom[email]' : {
						'message': getMessage(MAIL, ['連絡先のメールアドレス'])
					},
					'maxSize' : {
						'message': getMessage(MAXIMUM, ['連絡先のメールアドレス', MCA0110_MAX_LENGTH_CONTACT_MAIL])
					},
				},
				'#txtContactMailRe': {
					'required': {
						'message': getMessage(REQUIRED, ['連絡先のメールアドレス（確認）'])
					},
					'custom[email]' : {
						'message': getMessage(MAIL, ['連絡先のメールアドレス（確認）'])
					},
					'maxSize' : {
						'message': getMessage(MAXIMUM, ['連絡先のメールアドレス（確認）', MCA0110_MAX_LENGTH_CONTACT_MAIL_RE])
					},
				},
				'#txtTempPassword': {
					'required': {
						'message': getMessage(REQUIRED, ['ログイン情報－仮パスワード'])
					},
					'custom[halfwidthAlphanumeric]' : {
						'message': getMessage(HALFWIDTH_ALPHANUMERIC, ['ログイン情報－仮パスワード'])
					},
					// instead of maxSize to checkRangeForSingleItem
				},
				'#txtTempPasswordRe': {
					'required': {
						'message': getMessage(REQUIRED, ['ログイン情報－仮パスワード（確認）'])
					},
					'custom[halfwidthAlphanumeric]' : {
						'message': getMessage(HALFWIDTH_ALPHANUMERIC, ['ログイン情報－仮パスワード（確認）'])
					},
					// instead of maxSize to checkRangeForSingleItem
				},
		}
	});
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
//                    "alertText": getMessage('E0002', ['*']), // Remove default error message for select
                    "alertText": ' ',
                },
                /* Check Half-width numbers/半角数字  */
                // TODO custom validationEngine
                "halfwidthNumbers" : {
                    "regex":  /^[0-9\ ]+$/,
                    "alertText": getMessage('E0003', ['*']),                	
                },
                /* Check "Half-width/半角Alphanumeric/英数字"  */
                // custom validationEngine
                "halfwidthAlphanumeric": {
                     "regex":  /^[ｦ-ﾟ ､0-9a-zA-Z@.]*$/,
                     "alertText": getMessage('E0005', ['*']),
                 },
                 /* Check Full-width characters/全角文字 */
                 // TODO custom validationEngine
                 "fullwidthCharacters": {
                     "regex":  /^[ｦ-ﾟ ､a-zA-Z@.]*$/,
                     "alertText": getMessage('E0006', ['*']),
                 },
                 /* Check Full-width/全角	Katakana/カタカナ */
                 // custom validationEngine
                 "kana": {
                     "regex": /^[０-９－ａ-ｚＡ-Ｚぁ-んァ-ー一-龠　]+$/,
                     "alertText": getMessage('E0007', ['*']),
                 },
                 /* Check Check Mail address/アドレスAddress/アドレス */
                 "email": {
                    // Shamelessly lifted from Scott Gonzalez via the Bassistance Validation plugin http://projects.scottsplayground.com/email_address_validation/
                    "regex": /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i,
                    "alertText": getMessage('E0009', ['*']),
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




function checkRangeForSingleItem(field, rules, i, options) {
	var min = rules[i + 3];
	var max = rules[i + 4];
	var label = field.attr("custom");
	var len = field.val().length;
	if (max != 0 && min != 0) {
		// Check minimum/最小
		if (min == max && len < min) {
			 return getMessage(MINIMUM, [String(label), String(min)]);
		}
		// Check maximum/最大
		if (min == max && len > max) {
			return getMessage(MAXIMUM, [String(label), String(max)]);
		}
		// Check range minimum and maximum
		if (min != max && (len < min || len > max)) {
			return getMessage(RANGED, [String(label), String(min), String(max)]);
		}
	} else if (min == 0 && len > max) {
		// Check maximum/最大
		return getMessage(MAXIMUM, [String(label), String(max)]);
	}
}