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
/* Error code for SpecialCharacters */
const SPECIALCHARACTERS = 'E0015';
/* Error code for Mail address/アドレスAddress/アドレス */
const MAIL = 'E0009';
/* Error code for minimum/最小*/
const MINIMUM = 'E0013';
/* Error code for maximum/最大*/
const MAXIMUM = 'E0012';
/* Error code for range for minimum/最小 and maximum/最大 */
const RANGED = 'E0014';
/* Code for confirm message */
const CONFIRM_MESSAGE = 'C0003';

const MCA0110_CONFIRM_MESSAGE = '新規ユーザを登録';

const LOGIN_TERM_AND_CONDITION = "MAA0110_chkTermsConditions";
const LOGIN_PERIOD_SCREEN_ID = "MAA0110_periodScreenId";

/* Error format 入力可能桁数.最大※ */
const MCA0110_LENGTH = {
    MAX_APARTMENT_NAME : '50',
    MAX_APARTMENT_NAME_PHONETIC : '100',
    MIN_APARTMENT_ZIP_CODE1 : '3',
    MAX_APARTMENT_ZIP_CODE1 : '3',
    MIN_APARTMENT_ZIP_CODE2 : '4',
    MAX_APARTMENT_ZIP_CODE2 : '4',
    MAX_APARTMENT_ADDRESS_2 : '100',
    MAX_CONTACT_PROPERTY_ELSE : '30',
    MIN_CONTACT_ZIP_CODE1 : '3',
    MAX_CONTACT_ZIP_CODE1 : '3',
    MIN_CONTACT_ZIP_CODE2 : '4',
    MAX_CONTACT_ZIP_CODE2 : '4',
    MAX_CONTACT_ADDRESS : '100',
    MAX_CONTACT_TEL_NO1 : '5',
    MAX_CONTACT_TEL_NO2 : '4',
    MAX_CONTACT_TEL_NO3 : '4',
    MAX_CONTACT_NAME : '34',
    MAX_CONTACT_NAME_PHONETIC : '34',
    MAX_CONTACT_MAIL : '120',
    MIN_TEMP_PASSWORD : '8',
    MAX_TEMP_PASSWORD : '16'
}
const MCA0110_LBL = {
    APARTMENT_NAME : '基本情報のマンション名',
    APARTMENT_NAME_PHONETIC : '基本情報のマンション名フリガナ',
    APARTMENT_ZIP_CODE1 : '基本情報の郵便番号1',
    APARTMENT_ZIP_CODE2 : '基本情報の郵便番号2',
    APARTMENT_ADDRESS_1 : '基本情報の住所1',
    APARTMENT_ADDRESS_2 : '基本情報の住所2',
    RDO_CONTACT_PROPERTY_ELSE : '連絡先の属性',
    TXT_CONTACT_PROPERTY_ELSE : '連絡先の属性－その他',
    CONTACT_ZIP_CODE1 : '連絡先の郵便番号1',
    CONTACT_ZIP_CODE2 : '連絡先の郵便番号2',
    CONTACT_ADDRESS : '連絡先の住所',
    CONTACT_TEL_NO1 : '連絡先の電話番号1',
    CONTACT_TEL_NO2 : '連絡先の電話番号2',
    CONTACT_TEL_NO3 : '連絡先の電話番号3',
    CONTACT_NAME : '連絡先の氏名',
    CONTACT_NAME_PHONETIC : '連絡先の氏名フリガナ',
    CONTACT_MAIL : '連絡先のメールアドレス',
    CONTACT_MAIL_RE : '連絡先のメールアドレス（確認）',
    TEMP_PASSWORD : 'ログイン情報－仮パスワード',
    TEMP_PASSWORD_RE : 'ログイン情報－仮パスワード（確認）'
}
/* Radio */
var radio1 = [0];

// Initial txtTempPassword is textbox to keep previous data
$("#txtTempPassword").attr('type', 'text');
// Initial txtTempPasswordRe is textbox to keep previous data
$("#txtTempPasswordRe").attr('type', 'text');
$(document).ready(function() {

    /* Set event tabIndex for checkBox */
    $("input[name='chkTermsConditions']").addClass("checkbox-class");
    $(".checkbox-class").parent().on( "keypress", function(e) {
        $(this).children().spacePress(e);
        var checkBox = document.getElementById("chkTermsConditions");
        if (checkBox.checked == true){
            $("#btnNewRegistration").prop('disabled', false);
        } else {
            $("#btnNewRegistration").prop('disabled', true);
        }
    });

    // Initial focus
    $('#txtApartmentName').focus();
    // Initial 都道府県 = 東京都
    $('#lblPrefectures').text("東京都");
    // Initial display text box for その他 radio
    $("#txtContactPropertyElse").attr("disabled","true");
    // Initial btnNewRegistration 登録
    $("#chkTermsConditions").prop('checked', false);
    // Initial txtTempPassword is password to keep previous data
    $("#txtTempPassword").attr('type', 'password');
    // Initial txtTempPasswordRe is password to keep previous data
    $("#txtTempPasswordRe").attr('type', 'password');

    // Apply previousScreen
    previousScreen();
    // Apply ChkTermsConditions
    onChkTermsConditions();
    // Apply postal jquery for ApartmentZipCode by zip code 自動入力 and address by zip code 自動入力
    onPostal();
    // Append max length attribute
    appendMaxLengthAttribute();
    // Apply attribute for validationEngine
    appendValidationEngineAttr();
    // Apply event for validationEngine
    appendValidationEngineEvent();
    // Apply onValidContactPropertyElse
    onValidContactPropertyElse();
    // Apply eye for password fields
    showHideEye();

    // Error message for validationEngine
    $(".user-register").validationEngine({
        // 以下のパラメータは任意
         promptPosition: "bottomLeft",//エラー文の表示位置
         showArrowOnRadioAndCheckbox: true,//エラー箇所の図示
         focusFirstField: true,//エラー時に一番文頭の入力フィールドにフォーカスさせるかどうか
         scroll: false,
         maxErrorsPerField: 1,
         onValidationComplete : function(form, status) {
        	 return status;
        },
         'custom_error_messages': {
             '#txtApartmentName': {
                    'required': {
                        'message': getMessage(REQUIRED, [MCA0110_LBL.APARTMENT_NAME])
                    },
                    'custom2[prohibitionCharacter]' : {
                        'message': getMessage(SPECIALCHARACTERS, [MCA0110_LBL.APARTMENT_NAME])
                    },
                    'maxSize' : {
                        'message': getMessage(MAXIMUM, [MCA0110_LBL.APARTMENT_NAME, MCA0110_LENGTH.MAX_APARTMENT_NAME])
                    },
                },
                '#txtApartmentNamePhonetic': {
                    'required': {
                        'message': getMessage(REQUIRED, [MCA0110_LBL.APARTMENT_NAME_PHONETIC])
                    },
                    'custom[kana]' : {
                        'message': getMessage(FULLWIDTH_KATAKANA, [MCA0110_LBL.APARTMENT_NAME_PHONETIC])
                    },
                    'maxSize' : {
                        'message': getMessage(MAXIMUM, [MCA0110_LBL.APARTMENT_NAME_PHONETIC, MCA0110_LENGTH.MAX_APARTMENT_NAME_PHONETIC])
                    },
                },
                '#txtApartmentZipCode1': {
                    'required': {
                        'message': getMessage(REQUIRED, [MCA0110_LBL.APARTMENT_ZIP_CODE1])
                    },
                    'custom[halfwidthNumbers]' : {
                        'message': getMessage(HALFWIDTH_NUMBERS, [MCA0110_LBL.APARTMENT_ZIP_CODE1])
                    },
                    // instead of maxSize to checkRangeForSingleItem
                },
                '#txtApartmentZipCode2': {
                    'required': {
                        'message': getMessage(REQUIRED, [MCA0110_LBL.APARTMENT_ZIP_CODE2])
                    },
                     'custom[halfwidthNumbers]' : {
                        'message': getMessage(HALFWIDTH_NUMBERS, [MCA0110_LBL.APARTMENT_ZIP_CODE2])
                    },
                    // instead of maxSize to checkRangeForSingleItem
                },
                '#lstApartmentAddress1': {
                    'custom[required]': {
                        'message': getMessage(REQUIRED1, [MCA0110_LBL.APARTMENT_ADDRESS_1])
                    },
                },
                '#txtApartmentAddress2': {
                    'required': {
                        'message': getMessage(REQUIRED, [MCA0110_LBL.APARTMENT_ADDRESS_2])
                    },
                    'custom[fullwidthCharacters]' : {
                        'message': getMessage(FULLWIDTH_CHARACTERS, [MCA0110_LBL.APARTMENT_ADDRESS_2])
                    },
                    'maxSize' : {
                        'message': getMessage(MAXIMUM, [MCA0110_LBL.APARTMENT_ADDRESS_2, MCA0110_LENGTH.MAX_APARTMENT_ADDRESS_2])
                    },
                },
                '#radio-1': {
                    'custom[required]': {
                        'message': getMessage(REQUIRED1, [MCA0110_LBL.RDO_CONTACT_PROPERTY_ELSE])
                    },
                    'minCheckbox': {
                     'message': getMessage(REQUIRED1, [MCA0110_LBL.RDO_CONTACT_PROPERTY_ELSE])
                    }
                },
                '#txtContactPropertyElse': {
                    'condRequired': {
                        'message': getMessage(REQUIRED, [MCA0110_LBL.TXT_CONTACT_PROPERTY_ELSE])
                    },
                    'custom2[prohibitionCharacter]' : {
                        'message': getMessage(SPECIALCHARACTERS, [MCA0110_LBL.TXT_CONTACT_PROPERTY_ELSE])
                    },
                    'maxSize' : {
                        'message': getMessage(MAXIMUM, [MCA0110_LBL.TXT_CONTACT_PROPERTY_ELSE, MCA0110_LENGTH.MAX_CONTACT_PROPERTY_ELSE])
                    },
                },
                '#txtContactZipCode1': {
                    'required': {
                        'message': getMessage(REQUIRED, [MCA0110_LBL.CONTACT_ZIP_CODE1])
                    },
                    'custom[halfwidthNumbers]' : {
                        'message': getMessage(HALFWIDTH_NUMBERS, [MCA0110_LBL.CONTACT_ZIP_CODE1])
                    },
                    // instead of maxSize to checkRangeForSingleItem
                },
                '#txtContactZipCode2': {
                    'required': {
                        'message': getMessage(REQUIRED, [MCA0110_LBL.CONTACT_ZIP_CODE2])
                    },
                    'custom[halfwidthNumbers]' : {
                        'message': getMessage(HALFWIDTH_NUMBERS, [MCA0110_LBL.CONTACT_ZIP_CODE2])
                    },
                    // instead of maxSize to checkRangeForSingleItem
                },
                '#txtContactAddress': {
                    'required': {
                        'message': getMessage(REQUIRED, [MCA0110_LBL.CONTACT_ADDRESS])
                    },
                    'custom[fullwidthCharacters]' : {
                        'message': getMessage(FULLWIDTH_CHARACTERS, [MCA0110_LBL.CONTACT_ADDRESS])
                    },
                    'maxSize' : {
                        'message': getMessage(MAXIMUM, [MCA0110_LBL.CONTACT_ADDRESS, MCA0110_LENGTH.MAX_CONTACT_ADDRESS])
                    },
                },
                '#txtContactTelno1': {
                    'required': {
                        'message': getMessage(REQUIRED, [MCA0110_LBL.CONTACT_TEL_NO1])
                    },
                    'custom[halfwidthNumbers]' : {
                        'message': getMessage(HALFWIDTH_NUMBERS, [MCA0110_LBL.CONTACT_TEL_NO1])
                    },
                    'maxSize' : {
                        'message': getMessage(MAXIMUM, [MCA0110_LBL.CONTACT_TEL_NO1, MCA0110_LENGTH.MAX_CONTACT_TEL_NO1])
                    },
                },
                '#txtContactTelno2': {
                    'required': {
                        'message': getMessage(REQUIRED, [MCA0110_LBL.CONTACT_TEL_NO2])
                    },
                    'custom[halfwidthNumbers]' : {
                        'message': getMessage(HALFWIDTH_NUMBERS, [MCA0110_LBL.CONTACT_TEL_NO2])
                    },
                    'maxSize' : {
                        'message': getMessage(MAXIMUM, [MCA0110_LBL.CONTACT_TEL_NO2, MCA0110_LENGTH.MAX_CONTACT_TEL_NO2])
                    },
                },
                '#txtContactTelno3': {
                    'required': {
                        'message': getMessage(REQUIRED, [MCA0110_LBL.CONTACT_TEL_NO3])
                    },
                    'custom[halfwidthNumbers]' : {
                        'message': getMessage(HALFWIDTH_NUMBERS, [MCA0110_LBL.CONTACT_TEL_NO3])
                    },
                    'maxSize' : {
                        'message': getMessage(MAXIMUM, [MCA0110_LBL.CONTACT_TEL_NO3, MCA0110_LENGTH.MAX_CONTACT_TEL_NO3])
                    },
                },
                '#txtContactName': {
                    'required': {
                        'message': getMessage(REQUIRED, [MCA0110_LBL.CONTACT_NAME])
                    },
                    'custom2[prohibitionCharacter]' : {
                        'message': getMessage(SPECIALCHARACTERS, [MCA0110_LBL.CONTACT_NAME])
                    },
                    'maxSize' : {
                        'message': getMessage(MAXIMUM, [MCA0110_LBL.CONTACT_NAME, MCA0110_LENGTH.MAX_CONTACT_NAME])
                    },
                },
                '#txtContactNamePhonetic': {
                    'required': {
                        'message': getMessage(REQUIRED, [MCA0110_LBL.CONTACT_NAME_PHONETIC])
                    },
                    'custom[kana]' : {
                        'message': getMessage(FULLWIDTH_KATAKANA, [MCA0110_LBL.CONTACT_NAME_PHONETIC])
                    },
                    'maxSize' : {
                        'message': getMessage(MAXIMUM, [MCA0110_LBL.CONTACT_NAME_PHONETIC, MCA0110_LENGTH.MAX_CONTACT_NAME_PHONETIC])
                    },
                },
                '#txtContactMail': {
                    'required': {
                        'message': getMessage(REQUIRED, [MCA0110_LBL.CONTACT_MAIL])
                    },
                    'custom[halfwidthAlphanumeric]' : {
                        'message': getMessage(HALFWIDTH_ALPHANUMERIC, [MCA0110_LBL.CONTACT_MAIL])
                    },
                    'custom[email]' : {
                        'message': getMessage(MAIL, [MCA0110_LBL.CONTACT_MAIL])
                    },
                    'maxSize' : {
                        'message': getMessage(MAXIMUM, [MCA0110_LBL.CONTACT_MAIL, MCA0110_LENGTH.MAX_CONTACT_MAIL])
                    },
                },
                '#txtContactMailRe': {
                    'required': {
                        'message': getMessage(REQUIRED, [MCA0110_LBL.CONTACT_MAIL_RE])
                    }
                },
                '#txtTempPassword': {
                    'required': {
                        'message': getMessage(REQUIRED, [MCA0110_LBL.TEMP_PASSWORD])
                    },
                    'custom[halfwidthAlphanumeric]' : {
                        'message': getMessage(HALFWIDTH_ALPHANUMERIC, [MCA0110_LBL.TEMP_PASSWORD])
                    }
                },
                '#txtTempPasswordRe': {
                    'required': {
                        'message': getMessage(REQUIRED, [MCA0110_LBL.TEMP_PASSWORD_RE])
                    }
                },
        }
    });
    
	  $('.btnNewRegistration').on('click', function() {
	  $("#div3")
	  .html(getMessage(CONFIRM_MESSAGE, MCA0110_CONFIRM_MESSAGE))
	  .dialog({
	      modal: true,
	      title: dialog.title,
	      width: 305,
	      buttons: {
	          "OK": function() {
	              $(this).dialog("close");
	              var result = $(".user-register").validationEngine('validate');
	              if (result) {
	            	$("#form").submit();
	              }
	          },
	          "キャンセル": function() {
	              $(this).dialog("close"); 
	              event.preventdefault();
	          }
	      }
	  });
	});
	  if (checkOnConfirm == 'true') {
		  document.getElementById("btnNewRegistration").disabled = false;
		  document.getElementById("chkTermsConditions").checked = true;
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
                    "alertTextCheckboxMultiple": '',
                    "alertTextCheckboxe": '',
                },
                /* Check Half-width numbers/半角数字  */
                "halfwidthNumbers" : {
                    "regex":  patternRegex.halfwidthNumbers,
                    "alertText": getMessage('E0003', ['*']),
                },
                /* Check "Half-width/半角Alphanumeric/英数字"  */
                // custom validationEngine
                "halfwidthAlphanumeric": {
                     "regex":  patternRegex.halfwidthAlphanumeric,
                     "alertText": getMessage('E0005', ['*']),
                 },
                 /* Check Full-width characters/全角文字 */
                 "fullwidthCharacters": {
                     "regex":  patternRegex.fullwidthCharacters,
                     "alertText": getMessage('E0006', ['*']),
                 },
                 /* Check Full-width/全角    Katakana/カタカナ */
                 // custom validationEngine
                 "kana": {
                     "regex": patternRegex.kana,
                     "alertText": getMessage('E0007', ['*']),
                 },
                 /* Check Check Mail address/アドレスAddress/アドレス */
                 "email": {
                     "regex": patternRegex.email,
                     "alertText": getMessage('E0009', ['*']),
                 },
                 "maxSize": {
                     "regex": "none",
                     "alertText": "* ",
                     "alertText2": getMessage('E0012', ['*', '*'])
                 },
                 "prohibitionCharacter": {
                     "regex": patternRegex.isNotSpecialCharacter,
                     "alertText": getMessage('E0015', '*')
                 },
                 "minCheckbox": {
                     "regex": "none",
                     "alertText": ' ',
                 }
            };
        }
    };
    $.validationEngineLanguage.newLang();
})(jQuery);
/**
 * Function check range for single item
 */
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
/**
 * Event postal code automation button
 */
function onPostal() {
    // Generate ApartmentZipCode by zip code 自動入力
    $('#txtApartmentZipCode1').jpostal({
        click : '#btnApartmentAutofill',
        postcode : [
            '#txtApartmentZipCode1',
            '#txtApartmentZipCode2'
        ],
        address : {
            '#lstApartmentAddress1'  : '%4',
            '#txtApartmentAddress2'  : '%5',
        },
        url : {
            'http'  : jpostal_url.http,
            'https' : jpostal_url.https,
        }
    });

    // Generate address by zip code 自動入力
    $('#txtContactZipCode1').jpostal({
        click : '#btnContactAutofill',
        postcode : [
            '#txtContactZipCode1',
            '#txtContactZipCode2'
        ],
        address : {
            '#txtContactAddress'  : '%3%4%5',
        },
        url : {
            'http'  : jpostal_url.http,
            'https' : jpostal_url.https,
        }
    });
}
/**
 * Event append validation engine attribute
 */
function appendValidationEngineAttr() {
    // Append custom
    $('#txtApartmentZipCode1').attr("custom", "基本情報の郵便番号1");
    $('#txtApartmentZipCode2').attr("custom", "基本情報の郵便番号2");
    $('#txtContactZipCode1').attr("custom", "連絡先の郵便番号1");
    $('#txtContactZipCode2').attr("custom", "連絡先の郵便番号2");
    $('#txtContactTelno1').attr("custom","連絡先の電話番号1");
    $('#txtContactTelno2').attr("custom","連絡先の電話番号2");
    $('#txtContactTelno3').attr("custom","連絡先の電話番号3");
    $('#txtTempPassword').attr("custom","ログイン情報－仮パスワード");
    $('#txtTempPasswordRe').attr("custom","ログイン情報－仮パスワード（確認）");

    // Append data-validation-engine
    $('#txtApartmentName').attr("data-validation-engine", "validate[required, maxSize[50]], custom2[prohibitionCharacter]");
    $('#txtApartmentNamePhonetic').attr("data-validation-engine", "validate[required, custom[kana], maxSize[100]]");
    $('#txtApartmentZipCode1').attr("data-validation-engine", "validate[required, custom[halfwidthNumbers], funcCall[checkRangeForSingleItem][3, 3]]");
    $('#txtApartmentZipCode2').attr("data-validation-engine", "validate[required, custom[halfwidthNumbers], funcCall[checkRangeForSingleItem][4, 4]]");

    appendDropDownAttr();
    $('#txtApartmentAddress2').attr("data-validation-engine", "validate[required, custom[fullwidthCharacters], maxSize[100]]");
    $('input[name^="rdoContactProperty"]').attr("data-validation-engine", "validate[minCheckbox[1]]");

    $('#txtContactZipCode1').attr("data-validation-engine", "validate[required, custom[halfwidthNumbers], funcCall[checkRangeForSingleItem][3, 3]]");
    $('#txtContactZipCode2').attr("data-validation-engine", "validate[required, custom[halfwidthNumbers], funcCall[checkRangeForSingleItem][4, 4]]");
    $('#txtContactAddress').attr("data-validation-engine", "validate[required, custom[fullwidthCharacters], maxSize[100]]");
    $('#txtContactTelno1').attr("data-validation-engine", "validate[required, custom[halfwidthNumbers], maxSize[5]]");
    $('#txtContactTelno2').attr("data-validation-engine", "validate[required, custom[halfwidthNumbers], maxSize[4]]");
    $('#txtContactTelno3').attr("data-validation-engine", "validate[required, custom[halfwidthNumbers], maxSize[4]]");
    $('#txtContactName').attr("data-validation-engine", "validate[required, maxSize[34]], custom2[prohibitionCharacter]");
    $('#txtContactNamePhonetic').attr("data-validation-engine", "validate[required, custom[kana], maxSize[34]]");
    $('#txtContactMail').attr("data-validation-engine", "validate[required, custom[halfwidthAlphanumeric], custom[email], maxSize[120]]");
    $('#txtContactMailRe').attr("data-validation-engine", "validate[required]");
    $('#txtTempPassword').attr("data-validation-engine", "validate[required, custom[halfwidthAlphanumeric], funcCall[checkRangeForSingleItem][8, 16]]");
    $('#txtTempPasswordRe').attr("data-validation-engine", "validate[required]");
}
/**
 * Event append max length attribute
 */
function appendMaxLengthAttribute() {
    // Append max length attribute
    $('#txtApartmentName').attr("maxlength", MCA0110_LENGTH.MAX_APARTMENT_NAME);
    $('#txtApartmentNamePhonetic').attr("maxlength", MCA0110_LENGTH.MAX_APARTMENT_NAME_PHONETIC);
    $('#txtApartmentZipCode1').attr("minlength", MCA0110_LENGTH.MIN_APARTMENT_ZIP_CODE1);
    $('#txtApartmentZipCode1').attr("maxlength", MCA0110_LENGTH.MAX_APARTMENT_ZIP_CODE1);
    $('#txtApartmentZipCode2').attr("minlength", MCA0110_LENGTH.MIN_APARTMENT_ZIP_CODE2);
    $('#txtApartmentZipCode2').attr("maxlength", MCA0110_LENGTH.MAX_APARTMENT_ZIP_CODE2);
    $('#txtApartmentAddress2').attr("maxlength", MCA0110_LENGTH.MAX_APARTMENT_ADDRESS_2);
    $("#txtContactPropertyElse").attr("maxlength", MCA0110_LENGTH.MAX_CONTACT_PROPERTY_ELSE);
    $('#txtContactZipCode1').attr("minlength", MCA0110_LENGTH.MIN_CONTACT_ZIP_CODE1);
    $('#txtContactZipCode1').attr("maxlength", MCA0110_LENGTH.MAX_CONTACT_ZIP_CODE1);
    $('#txtContactZipCode2').attr("minlength", MCA0110_LENGTH.MIN_CONTACT_ZIP_CODE2);
    $('#txtContactZipCode2').attr("maxlength", MCA0110_LENGTH.MAX_CONTACT_ZIP_CODE2);
    $('#txtContactAddress').attr("maxlength", MCA0110_LENGTH.MAX_CONTACT_ADDRESS);
    $('#txtContactTelno1').attr("maxlength", MCA0110_LENGTH.MAX_CONTACT_TEL_NO1);
    $('#txtContactTelno2').attr("maxlength", MCA0110_LENGTH.MAX_CONTACT_TEL_NO2);
    $('#txtContactTelno3').attr("maxlength", MCA0110_LENGTH.MAX_CONTACT_TEL_NO3);
    $('#txtContactName').attr("maxlength", MCA0110_LENGTH.MAX_CONTACT_NAME);
    $('#txtContactNamePhonetic').attr("maxlength", MCA0110_LENGTH.MAX_CONTACT_NAME_PHONETIC);
    $('#txtContactMail').attr("maxlength", MCA0110_LENGTH.MAX_CONTACT_MAIL);
    $('#txtTempPassword').attr("minlength", MCA0110_LENGTH.MIN_TEMP_PASSWORD);
    $('#txtTempPassword').attr("maxlength", MCA0110_LENGTH.MAX_TEMP_PASSWORD);
    $('#txtTempPasswordRe').attr("minlength", MCA0110_LENGTH.MIN_TEMP_PASSWORD);
    $('#txtTempPasswordRe').attr("maxlength", MCA0110_LENGTH.MAX_TEMP_PASSWORD);

}
/**
 * Event append validation engine for dropdown
 */
function appendDropDownAttr() {
    const _lstApartmentAddress1 = $("#lstApartmentAddress1");

    if ($("#lstApartmentAddress1 option:selected").text().length != 0) {
        disabledValidationEngine(_lstApartmentAddress1);
    } else {
        _lstApartmentAddress1.attr("data-validation-engine", "validate[custom[required]]");
    }
}
/**
 * Event append validation engine event
 */
function appendValidationEngineEvent() {
    const _validApartmentZipCode = $("#btnApartmentAutofill");
    const _validContactAddress = $("#btnContactAutofill");
    const _lstApartmentAddress1 = $("#lstApartmentAddress1");
    const _txtContactAddress = $("#txtContactAddress");
    const _rdoContactProperty = $('input[name^="rdoContactProperty"]');
    const _txtApartmentAddress2 = $('#txtApartmentAddress2');

    // Trigger event to remove error message for lstApartmentAddress1 by zip code 自動入力
     // Use trick timeout to trigger after jpostalcode event
    _validApartmentZipCode.bind("click", function() {
        setTimeout(function() {
            // Event postal code automation button for lstApartmentAddress1
            if ($("#lstApartmentAddress1 option:selected").text().length != 0) {
                disabledValidationEngine(_lstApartmentAddress1);
                _lstApartmentAddress1.removeAttr("data-validation-engine");
            } else {
                _lstApartmentAddress1.attr("data-validation-engine", "validate[custom[required]]");
            }
            // Event postal code automation button for txtApartmentAddress2
            if ($("#txtApartmentAddress2").val().length != 0) {
                disabledValidationEngine(_txtApartmentAddress2);
            } else {
                _txtApartmentAddress2.attr("data-validation-engine", "validate[required, custom[fullwidthCharacters], maxSize[100]]");
            }
        }, 500);
    });
    _validContactAddress.bind("click", function() {
        setTimeout(function() {
            if (_txtContactAddress.val().length != 0) {
                disabledValidationEngine(_txtContactAddress);
            } else {
                _txtContactAddress.attr("data-validation-engine", "validate[required, custom[fullwidthCharacters], maxSize[100]]");
            }
        }, 100);
    });
    // Event postal code automation button for lstApartmentAddress1
    _lstApartmentAddress1.bind("change", function() {
        if ($("#lstApartmentAddress1 option:selected").text().length != 0) {
            disabledValidationEngine(_lstApartmentAddress1);
            _lstApartmentAddress1.removeAttr("data-validation-engine");
        } else {
            _lstApartmentAddress1.attr("data-validation-engine", "validate[custom[required]]");
        }
    });
    // Event postal code automation button for txtApartmentAddress2
    _txtApartmentAddress2.bind("change", function() {
        _txtApartmentAddress2.attr("data-validation-engine", "validate[required, custom[fullwidthCharacters], maxSize[100]]");
    });
    // Event postal code automation button for txtContactAddress
    _txtContactAddress.bind("change", function() {
        _txtContactAddress.attr("data-validation-engine", "validate[required, custom[fullwidthCharacters], maxSize[100]]");
    });
    // Event validation for rdoContactProperty
    _rdoContactProperty.bind("change", function() {
        // Event for text
        onValidContactPropertyElse();
    });
}
/**
 * Function disabled validation engine
 */
function disabledValidationEngine(_method) {
    if (_method.attr("data-validation-engine")) {    // Prevent issue undefined
        _method.validationEngine("hide");
//        _method.removeAttr("data-validation-engine");
    }
}
/**
 * Event valid txtContactPropertyElse
 */
function onValidContactPropertyElse() {
    const _txtContactPropertyElseId = $("#txtContactPropertyElse");
    const _rdoContactPropertyId = $('input[name^="rdoContactProperty"]');

    // Event for textbox contactPropertyElse
    // その他
    if ($('input[id="radio-9"]').is(":checked")) {
        _txtContactPropertyElseId.attr("data-validation-engine", "validate[condRequired[radio-9], maxSize[30]], custom2[prohibitionCharacter]");
        _txtContactPropertyElseId.prop('disabled', false);
    } else if (_rdoContactPropertyId.is(":checked")) {
        _txtContactPropertyElseId.prop('disabled', true);
        disabledValidationEngine(_txtContactPropertyElseId);
        _txtContactPropertyElseId.val("")
    }
}
/**
 * Event 登録
 */
function onChkTermsConditions() {
    const __currentScreenId = "MAA0110";
    const __screenId = "screenId";
    const __chkTermsConditionsKey = "MCA0110_chkTermsConditions";
    const _btnNewRegistration = $("input[name='btnNewRegistration']");
    const _chkTermsConditions = $("#chkTermsConditions");
    const _termsConditionsUrl = $("#lnkTermsConditions");

    _btnNewRegistration.attr('disabled', true);

    if (window.sessionStorage) {
        const loginPeriodScreenId = sessionStorage.getItem(LOGIN_PERIOD_SCREEN_ID);
        const isLoginCheckedTermsConditions = sessionStorage.getItem(LOGIN_TERM_AND_CONDITION);
        sessionStorage.clear();
        sessionStorage.setItem(__screenId, __currentScreenId);
        sessionStorage.setItem(LOGIN_PERIOD_SCREEN_ID, loginPeriodScreenId);
        sessionStorage.setItem(LOGIN_TERM_AND_CONDITION, isLoginCheckedTermsConditions);
    }
    _chkTermsConditions.bind("change", function() {
        if(_chkTermsConditions.is(':checked') && sessionStorage.getItem(__chkTermsConditionsKey) === "ON") {
            _btnNewRegistration.prop('disabled', false);
        } else {
            _btnNewRegistration.prop('disabled', true);
        }
    });
    _termsConditionsUrl.bind("click", function() {
        sessionStorage.setItem(__chkTermsConditionsKey, "ON");
        if(_chkTermsConditions.is(':checked')) {
            _btnNewRegistration.prop('disabled', false);
        }
        window.open(baseUrl + "/SAA0110", "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=100,left=100,width=1000,height=700");
    });
}
/**
 * Go to previous screen
 */
function previousScreen() {
    // Event click button back previous screen
    $('.back-previous-screen').on('click', function() {
        window.location.href = baseUrl + '/MAA0110';
    });
}