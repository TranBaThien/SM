/* Check required radio button */
const REQUIRED_RADIO = 'E0001';
/* Error code for Required/必須 for input */
const REQUIRED = 'E0002';
/* Error code for SpecialCharacters */
const SPECIALCHARACTERS = 'E0015';
/* Error code for minimum/最小*/
const MINIMUM = 'E0013';
/* Error code for maximum/最大*/
const MAXIMUM = 'E0012';
/* Error code for Full-width/全角Katakana/カタカナ */
const FULLWIDTH_KATAKANA = 'E0007';
/* Error code for Half-width numbers/半角数字 */
const HALFWIDTH_NUMBERS = 'E0003';
/* Error code for Half-width/半角Alphanumeric/英数字 */
const HALFWIDTH_ALPHANUMERIC = 'E0005';
/* Error code for Full-width characters/全角文字 */
const FULLWIDTH_CHARACTERS = 'E0006';
//Message check format date
const FORMAT_DATE = 'E0008';
/* Error code for Mail address/アドレスAddress/アドレス */
const MAIL = 'E0009';

const CONFIRM = "C0003";

const LABEL_APARTMENT_NAME = 'マンション名';

const LABEL_APARTMENT_NAME_PHONETIC = 'マンション名フリガナ';

const LABEL_MANAGER_ZIP_CODE = '管理形態_郵便番号';

const LABEL_APARTMENT_ZIPCODE = 'マンションの郵便番号';

const LABEL_APARTMENT_ADDRESS2 = 'マンションの住所2';

const LABEL_NOTIFICATION_GROUP_NAME = '届出者_管理組合名';

const LABEL_NOTIFICATION_PERSON_NAME = '届出者_届出者氏名';

const LABEL_HOUSE_NUMBER = '戸数';

const LABEL_FLOOR_NUMBER = '階数';

const LABEL_CONTACT_ZIP_CODE = '連絡先の郵便番号';

const LABEL_CONTACT_ADDRESS = '連絡先の住所';

const LABEL_CONTACT_TEL_NO = '連絡先の電話番号';

const LABEL_CONTACT_NAME = '連絡先の氏名';

const LABEL_CONTACT_NAME_PHONETIC = '連絡先の氏名フリガナ';

const LABEL_OPTIONAL = '自由記述欄';

const LABEL_EMPTY_NUMBER = '空き住戸_【戸数】';

const LABEL_RENTAL_NUMBER = '賃貸化住戸_【戸数】';
/** File name of export CSV***/
const FILE_NAME_CSV_EXPORT = '届出事項登録_';

const MDA0110_REGISTER = "届出事項登録";

const LABEL_CALNOTIFICATION_DATE = '届出年月日';

const LABEL_CALBUILD_DATE = '新築年月日';

const LABEL_APARTMENT_NUMBER = "棟数";

var submitType = "";

$(window).on("scroll",function(){
    if($(window).scrollTop()>400){
        $("#asddsa").hide();
        $("#asddsa2").show();
        $("#asddsa2").attr("class","scroll-btn");
        $("#sizediv").css("margin-top","220px");
    } else if($(window).scrollTop() < 100){
        $("#asddsa2").hide();
        $("#asddsa").show();
        $("#asddsa2").removeAttr("class","scroll-btn");
        $("#sizediv").css("margin-top","10px")
    }
});

$(document).ready(function() {
/* check sapceEvent for confirm checkbox */
	$("input[name='chkConfirm']").addClass("checkbox-class-confirm");
    $(".checkbox-class-confirm").parent().on( "keypress", function(e) {
    	$(this).children().spacePress(e);
        if ($("input[name='chkConfirm']:checked").length == 1){
            $("#registerNotification").prop('disabled', false);
        } else {
            $("#registerNotification").prop('disabled', true);
        }
    });

    //Is save temporary data
    if(TEMPORARY_SAVE == 'true'){
        $("#alertMessage")
        .html(MESSAGE_SUCCESS)
        .dialog({
         modal:true, //モーダル表示
         title: dialog.title, //タイトル
         buttons: {
         "OK": function() {
                $(this).dialog("close"); 
                }
             }
        });
    } else if (MESSAGE_SUCCESS != '' && MESSAGE_SUCCESS != null) {  //Check message when save notification success
        //Show alert confirm success
        $('#message').html(MESSAGE_SUCCESS);
        //
        $("#alertMessage").dialog({
            modal:true, //モーダル表示
            title: dialog.title, //タイトル
            buttons: {
            "OK": function() {
                    $(this).dialog("close");
                    if (ROLE_MANSION == '5') {
                        window.location.href = BASE_URL + '/MBA0110';
                    } else { //管理組合用
                        $('button-id').val('');
                        $('#submitGJA0110Form').submit();
                    }
                    var isUpdateFunction = $('input[name=updateNotification]').val();
                    //Open report file
                    if (NOTIFICATION_NO_RP010 != '' && NOTIFICATION_NO_RP010 != null) {
                        var strKindFunction = 'RP010Create';
                        //Check if update function the open RP010 update
                        if (isUpdateFunction == 'true') {
                            strKindFunction = 'RP010Update';
                        }

                        //window.open(BASE_URL + '/GEA0110/' + strKindFunction + '/' + NOTIFICATION_NO_RP010, '_blank');
                        $('input[name=reportName]').val(strKindFunction);
                        $('input[name=relatedNumber]').val(NOTIFICATION_NO_RP010);

                        window.open('about:blank','RP010', 'width=900,height=1000,scrollbars=yes');

                        $('#submitReportForm').submit();
                    }
                }
            }
        });
    }

    //Check message when save have error exclude
    if (MESSAGE_ERROR_EXCLUDE != '' && MESSAGE_ERROR_EXCLUDE != null) {
        //Show alert confirm success
        $('#message').html(MESSAGE_ERROR_EXCLUDE);
        //
        $("#alertMessage").dialog({
            modal:true, //モーダル表示
            title: dialog.title, //タイトル
            buttons: {
            "OK": function() {
                    $(this).dialog("close");
                        if (ROLE_MANSION == '5') {
                            window.location.href = BASE_URL + '/MBA0110';
                        } else { //管理組合用
                            var valNotification = $('#notificationNo').val();
                            var apartmentId = $('input[name=apartmentId]').val();
                            $('#apartment-id').val(apartmentId);
                            $('#notifiation-id').val(valNotification);
                            //メニュー（管理組合用)（MBA0110）/メニュー（管理組合用）（MBA0110）
                            $('#submitGJAForm').submit();
                        }
                }
            }
        });
    }

    //Calculate input required number
    calcInputRequired();

    // Check Confirm button
    if (CONFIRM_CHECKBOX == null || CONFIRM_CHECKBOX == '') {
        $("input[name='chkConfirm']").prop("checked", false);
        $('.btn-register').prop('disabled', true);
    } else {
        $("input[name='chkConfirm']").prop("checked", true);
        $('.btn-register').prop('disabled', false);
    }
    
    //Check disable/enable submit button when not yet selected confirm
    $('.chkConfirm').change(function(){
        $('.btn-register').prop('disabled', !this.checked);
    });

    onPostal();

    //Check event onclick calendar icon
    $('.notifiDate').on('click', function(){
        $('#datepicker-1').focus();
    });

    $('#datepicker-1').datetimepicker({
        format:'Y/m/d',
        timepicker:false,
         mask:false
    });

    //Check event onclick calendar CalBuildDate icon
    $('.clsCalBuildDate').on('click', function(){
        $('#calBuildDate').focus();
    });

    $('#calBuildDate').datetimepicker({
        format:'Y/m/d',
        timepicker:false,
         mask:false
    });

    //check event on blur
    $('.input-required').blur(function(e) {

        calcInputRequired();
    });

    /* append Tapindex */
    appendTapIndexMDA0110();

    /* register */
    $('#registerNotification').on('click', function(){
        $(form).attr('action', BASE_URL + '/MDA0110/save');
        $("#alertMessage")
            .html(getMessage(CONFIRM, MDA0110_REGISTER))
            .dialog({
                modal:true,
                title: dialog.title, //タイトル,
                buttons: {
                    "OK": function() {
                        $(this).dialog("close");
                        //Validation before submit form
                        var isValid = $(".notification-register").validationEngine('validate');
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
    
    $('.txaOptional').on('keydown keyup change', function (e) {
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

    $(".btn-saveTemp").on('click', function(){
        submitType = 'saveTempData';
        addAndRemoveAttrExport();
        $(form).attr('action', BASE_URL + '/MDA0110/temporary');
        $("#alertMessage")
            .html(getMessage(CONFIRM, '届出事項一時保存'))
            .dialog({
                modal:true, //モーダル表示
                title: dialog.title, //タイトル
                buttons: {
                    "OK": function() {
                        $(this).dialog("close");
                        //Validation before submit form
                        var isValid = $(".notification-register").validationEngine('validate');
                        //If valid
                        if (isValid) {
                            $("#form").submit();
                        }
                    },
                    "キャンセル": function() {
                        $(this).dialog("close");
                        addAndRemoveAttrWhenExportError();
                    }
                }
            });
    });
    
    $("#btn-export-top, #btn-export-content").on('click', function(){
        submitType = 'btn-export';
        addAndRemoveAttrExport();
        $(form).attr('action', BASE_URL + "/MDA0110/exportCsv");
        exportCsv();
    });
    
    $(".notification-register").validationEngine('attach',{
        // 以下のパラメータは任意
        promptPosition: "bottomLeft",//エラー文の表示位置
        showArrowOnRadioAndCheckbox: true,//エラー箇所の図示
        maxErrorsPerField:1,
        showArrow: true,
        focusFirstField: true,//エラー時に一番文頭の入力フィールドにフォーカスさせるかどうか
        scroll: false,
         'custom_error_messages': {
             '#basicReportInfo.txtApartmentName': {
                'required': {
                    'message': getMessage(REQUIRED, LABEL_APARTMENT_NAME)
                },
                'custom2[prohibitionCharacter]': {
                    'message': getMessage(SPECIALCHARACTERS,LABEL_APARTMENT_NAME)
                },
                'maxSize' : {
                    'message': getMessage(MAXIMUM, [LABEL_APARTMENT_NAME, 50])
                }
             },
             '#basicReportInfo.txtApartmentNamePhonetic': {
                    'required': {
                        'message': getMessage(REQUIRED, LABEL_APARTMENT_NAME_PHONETIC)
                    },
                    'custom[kana]': {
                        'message': getMessage(FULLWIDTH_KATAKANA, LABEL_APARTMENT_NAME_PHONETIC)
                    },
                    'maxSize' : {
                        'message': getMessage(MAXIMUM, [LABEL_APARTMENT_NAME_PHONETIC, 100])
                    }
                 },
             '#txtManagerZipCode1': {
                 'custom[halfwidthNumbers]': {
                     'message': getMessage(HALFWIDTH_NUMBERS, LABEL_MANAGER_ZIP_CODE)
                 },
                 'maxSize' : {
                     'message': getMessage(MAXIMUM, [LABEL_MANAGER_ZIP_CODE, 3])
                 },
                 'minSize' : {
                     'message': getMessage(MAXIMUM, [LABEL_MANAGER_ZIP_CODE, 3])
                 }
             },
             '#txtManagerZipCode2': {
                 'custom[halfwidthNumbers]': {
                     'message': getMessage(HALFWIDTH_NUMBERS, LABEL_MANAGER_ZIP_CODE)
                 },
                 'maxSize' : {
                     'message': getMessage(MAXIMUM, [LABEL_MANAGER_ZIP_CODE, 4])
                 },
                 'minSize' : {
                     'message': getMessage(MAXIMUM, [LABEL_MANAGER_ZIP_CODE, 4])
                 }
             },
             '#txtApartmentZipCode1': {
                    'required': {
                        'message': getMessage(REQUIRED, LABEL_APARTMENT_ZIPCODE)
                    },
                    'custom[halfwidthNumbers]': {
                        'message': getMessage(HALFWIDTH_NUMBERS, LABEL_APARTMENT_ZIPCODE)
                    },
                    'maxSize' : {
                        'message': getMessage(MAXIMUM, [LABEL_APARTMENT_ZIPCODE, 3])
                    },
                    'minSize' : {
                        'message': getMessage(MINIMUM, [LABEL_APARTMENT_ZIPCODE, 3])
                    }
                 },
              '#txtApartmentZipCode2': {
                    'required': {
                        'message': getMessage(REQUIRED, LABEL_APARTMENT_ZIPCODE)
                    },
                    'custom[halfwidthNumbers]': {
                        'message': getMessage(HALFWIDTH_NUMBERS, LABEL_APARTMENT_ZIPCODE)
                    },
                    'maxSize' : {
                        'message': getMessage(MAXIMUM, [LABEL_APARTMENT_ZIPCODE, 4])
                    },
                    'minSize' : {
                        'message': getMessage(MINIMUM, [LABEL_APARTMENT_ZIPCODE, 4])
                    }
                 },
             '#txtApartmentAddress2': {
                    'required': {
                        'message': getMessage(REQUIRED, LABEL_APARTMENT_ADDRESS2)
                    },
                    'custom[fullwidthCharacters]': {
                        'message': getMessage(FULLWIDTH_CHARACTERS, LABEL_APARTMENT_ADDRESS2)
                    },
                    'maxSize' : {
                        'message': getMessage(MAXIMUM, [LABEL_APARTMENT_ADDRESS2, 100])
                    }
             },
             '#datepicker-1': {
                    'required': {
                        'message': getMessage(REQUIRED, LABEL_CALNOTIFICATION_DATE)
                    }
                 },
             '#infoAreaCommon.txtNotificationGroupName': {
                    'custom2[prohibitionCharacter]': {
                        'message': getMessage(SPECIALCHARACTERS, LABEL_NOTIFICATION_GROUP_NAME)
                    },
                    'maxSize' : {
                        'message': getMessage(MAXIMUM, [LABEL_NOTIFICATION_GROUP_NAME, 50])
                    }
             },
             '#infoAreaCommon.txtNotificationPersonName': {
                    'custom2[prohibitionCharacter]': {
                        'message': getMessage(SPECIALCHARACTERS, LABEL_NOTIFICATION_PERSON_NAME)
                    },
                    'maxSize' : {
                        'message': getMessage(MAXIMUM, [LABEL_NOTIFICATION_PERSON_NAME, 20])
                    }
             },
             '#infoAreaCommon.txtHouseNumber': {
                    'custom[halfwidthNumbers]': {
                        'message': getMessage(HALFWIDTH_NUMBERS, LABEL_HOUSE_NUMBER)
                    },
                    'maxSize' : {
                        'message': getMessage(MAXIMUM, [LABEL_HOUSE_NUMBER, 6])
                    }
             },
             '#infoAreaCommon.txtFloorNumber': {
                    'custom[halfwidthNumbers]': {
                        'message': getMessage(HALFWIDTH_NUMBERS, LABEL_FLOOR_NUMBER)
                    },
                    'maxSize' : {
                        'message': getMessage(MAXIMUM, [LABEL_FLOOR_NUMBER, 2])
                    }
              },
             '#infoAreaCommon.txtEmptyNumber': {
                 'custom[halfwidthNumbers]': {
                        'message': getMessage(HALFWIDTH_NUMBERS, LABEL_EMPTY_NUMBER)
                    },
                     'maxSize' : {
                        'message': getMessage(MAXIMUM, [LABEL_EMPTY_NUMBER, 4])
                    }
             },
             '#infoAreaCommon.txtRentalNumber' : {
                 'custom[halfwidthNumbers]': {
                        'message': getMessage(HALFWIDTH_NUMBERS, LABEL_RENTAL_NUMBER)
                    },
                     'maxSize' : {
                        'message': getMessage(MAXIMUM, [LABEL_RENTAL_NUMBER, 4])
                    }
             },
             '#r-27': {
                 'minCheckbox': {
                     'message': getMessage(REQUIRED_RADIO, '管理組合')
                    }
                 },
             '#r-29': {
                 'minCheckbox': {
                     'message': getMessage(REQUIRED_RADIO, '管理者等')
                    }
                 },
             '#r-31': {
                 'minCheckbox': {
                     'message': getMessage(REQUIRED_RADIO, '管理規約')
                    }
                 },
             '#r-33': {
                 'minCheckbox': {
                     'message': getMessage(REQUIRED_RADIO, '年1回以上の開催')
                    }
                 },
             '#r-37': {
                 'minCheckbox': {
                     'message': getMessage(REQUIRED_RADIO, '管理費')
                    }
                 },
             '#r-39': {
                 'minCheckbox': {
                     'message': getMessage(REQUIRED_RADIO, '修繕積立金')
                    }
                 },
             '#r-41': {
                 'minCheckbox': {
                     'message': getMessage(REQUIRED_RADIO, '修繕の計画的な実施 （大規模な修繕工事）')
                    }
                 },
             '#r-117': {
                 'minCheckbox': {
                     'message': getMessage(REQUIRED_RADIO, '連絡先属性')
                    }
                 },
             '#txtContactZipCode1': {
                    'required': {
                        'message': getMessage(REQUIRED, LABEL_CONTACT_ZIP_CODE)
                    },
                    'custom[halfwidthNumbers]': {
                        'message': getMessage(HALFWIDTH_NUMBERS, LABEL_CONTACT_ZIP_CODE)
                    },
                    'maxSize' : {
                        'message': getMessage(MAXIMUM, [LABEL_CONTACT_ZIP_CODE, 3])
                    },
                    'minSize' : {
                        'message': getMessage(MINIMUM, [LABEL_CONTACT_ZIP_CODE, 3])
                    }
                 },
              '#txtContactZipCode2': {
                    'required': {
                        'message': getMessage(REQUIRED, LABEL_CONTACT_ZIP_CODE)
                    },
                    'custom[halfwidthNumbers]': {
                        'message': getMessage(HALFWIDTH_NUMBERS, LABEL_CONTACT_ZIP_CODE)
                    },
                    'maxSize' : {
                        'message': getMessage(MAXIMUM, [LABEL_CONTACT_ZIP_CODE, 4])
                    },
                    'minSize' : {
                        'message': getMessage(MINIMUM, [LABEL_CONTACT_ZIP_CODE, 4])
                    }
                 },
             '#txtContactAddress': {
                    'required': {
                        'message': getMessage(REQUIRED, LABEL_CONTACT_ADDRESS)
                    },
                    'custom[fullwidthCharacters]': {
                        'message': getMessage(FULLWIDTH_CHARACTERS, LABEL_CONTACT_ADDRESS)
                    },
                    'maxSize' : {
                        'message': getMessage(MAXIMUM, [LABEL_CONTACT_ADDRESS, 100])
                    }
                 },
             '#infoAreaCommon.txtContactTelno1': {
                    'required': {
                        'message': getMessage(REQUIRED, LABEL_CONTACT_TEL_NO)
                    },
                    'custom[halfwidthNumbers]': {
                        'message': getMessage(HALFWIDTH_NUMBERS, LABEL_CONTACT_TEL_NO)
                    },
                    'maxSize' : {
                        'message': getMessage(MAXIMUM, [LABEL_CONTACT_TEL_NO, 5])
                    }
                 },
             '#infoAreaCommon.txtContactTelno2': {
                    'required': {
                        'message': getMessage(REQUIRED, LABEL_CONTACT_TEL_NO)
                    },
                    'custom[halfwidthNumbers]': {
                        'message': getMessage(HALFWIDTH_NUMBERS, LABEL_CONTACT_TEL_NO)
                    },
                    'maxSize' : {
                        'message': getMessage(MAXIMUM, [LABEL_CONTACT_TEL_NO, 4])
                    }
                 },
             '#infoAreaCommon.txtContactTelno3': {
                        'required': {
                            'message': getMessage(REQUIRED, LABEL_CONTACT_TEL_NO)
                        },
                        'custom[halfwidthNumbers]': {
                            'message': getMessage(HALFWIDTH_NUMBERS, LABEL_CONTACT_TEL_NO)
                        },
                        'maxSize' : {
                            'message': getMessage(MAXIMUM, [LABEL_CONTACT_TEL_NO, 4])
                        }
                 },
             '#infoAreaCommon.txtContactName': {
                        'required': {
                            'message': getMessage(REQUIRED, LABEL_CONTACT_NAME)
                        },
                        'custom2[prohibitionCharacter]': {
                            'message': getMessage(SPECIALCHARACTERS, LABEL_CONTACT_NAME)
                        },
                        'maxSize' : {
                            'message': getMessage(MAXIMUM, [LABEL_CONTACT_NAME, 34])
                        }
                 },
             '#infoAreaCommon.txtContactNamePhonetic': {
                'required': {
                    'message': getMessage(REQUIRED, LABEL_CONTACT_NAME_PHONETIC)
                },
                'custom[kana]': {
                    'message': getMessage(FULLWIDTH_KATAKANA, LABEL_CONTACT_NAME_PHONETIC)
                },
                'maxSize' : {
                    'message': getMessage(MAXIMUM, [LABEL_CONTACT_NAME_PHONETIC, 34])
                }
            },
             '#infoAreaCommon.txaOptional': {
                'custom2[prohibitionCharacter]': {
                    'message': getMessage(SPECIALCHARACTERS, LABEL_OPTIONAL)
                },
                'maxSize' : {
                    'message': getMessage(MAXIMUM, [LABEL_OPTIONAL, 300])
                }
            }
         },
        onValidationComplete: function(form, status){
            if (status) {
                return true;
            } else if(submitType == "btn-export" || submitType == "btn-saveTempData") {
                addAndRemoveAttrWhenExportError();
                return false;
            }
            return false;
        }
    });

});

function onPostal() {
    // Generate ApartmentZipCode by zip code 自動入力
    $('#txtApartmentZipCode1').jpostal({
        click : '#validApartmentZipCode',
        postcode : [
            '#txtApartmentZipCode1',
            '#txtApartmentZipCode2'
        ],
        address : {
            '#lblApartmentAddress1'  : '%4',
            '#txtApartmentAddress2'  : '%5'
        },
        url : {
            'http'  : jpostal_url.http,
            'https' : jpostal_url.https
        }
    });

    // Generate address by zip code 自動入力
    $('#txtManagerZipCode1').jpostal({
        click : '#validManagerZipCode',
        postcode : [
            '#txtManagerZipCode1',
            '#txtManagerZipCode2'
        ],
        address : {
            '#lblManagerAddress1'  : '%3%4%5',
        },
        url : {
            'http'  : jpostal_url.http,
            'https' : jpostal_url.https
        }
    });

    // Generate address by zip code 自動入力
    $('#txtContactZipCode1').jpostal({
        click : '#validContactZipCode',
        postcode : [
            '#txtContactZipCode1',
            '#txtContactZipCode2'
        ],
        address : {
            '#txtContactAddress'  : '%3%4%5',
        },
        url : {
            'http'  : jpostal_url.http,
            'https' : jpostal_url.https
        }
    });
    // Trigger event to remove error message for lstApartmentAddress1 by zip code 自動入力
    $('#validApartmentZipCode').bind('blur', function() {
       $('#lstApartmentAddress1').trigger('change');
    });

    // Event remove validation for lstApartmentAddress1
    $("#lstApartmentAddress1").change(function () {
        if ($("#lstApartmentAddress1 option:selected").text() == "") {
            $('#lstApartmentAddress1').attr("data-validation-engine", "validate[custom[required]]");
            return true;
        } else {
            $('#lstApartmentAddress1').validationEngine('hide');
            $("#lstApartmentAddress1").removeAttr("data-validation-engine");
        }
    });
}

function calcInputRequired() {

    var countItemInput = 0;
    var totalItemRequired = 23;
    $('.input-required').each(function() {
        var type = $(this).attr('type');
        var value = $(this).val();
        //Check value not yet input
        if (type == 'text' && value != '') {
            countItemInput++;
        }
        //
        else if (type == 'radio' &&  $(this).is(':checked')) {
            countItemInput++;
        }

    });

    var countTotal = countItemInput/totalItemRequired * 100;

    var textDisplay = '　' + countItemInput + '/' + totalItemRequired + '（' + Math.round(countTotal) + '％）';

    $('.percent-numb-required').html(textDisplay);
}

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
                    "alertText": '',
                },
                /* Check "Half-width/半角Alphanumeric/英数字"  */
                // custom validationEngine
                "halfwidthAlphanumeric": {
                     "regex": patternRegex.halfwidthAlphanumeric,
                     "alertText": getMessage('E0005', ['*']),
                 },
                 /* Check Full-width characters/全角文字 */
                 "fullwidthCharacters": {
                     "regex":  patternRegex.fullwidthCharacters,
                     "alertText": '',
                 },
                 /* Check Full-width/全角    Katakana/カタカナ */
                 // custom validationEngine
                 "kana": {
                     "regex": patternRegex.kana,
                     "alertText": '',
                 },
                 /* Check Check Mail address/アドレスAddress/アドレス */
                 "email": {
                     "regex": patternRegex.email,
                     "alertText": getMessage('E0009', ['*']),
                 },
                 "maxSize": {
                     "regex": "none",
                     "alertText": " ",
                 },
                 "minSize": {
                     "regex": "none",
                     "alertText": " ",
                 },
                 "prohibitionCharacter": {
                     "regex":  patternRegex.isNotSpecialCharacter,
                     "alertText": ' '
                 },
                 "date": {
                     "regex": patternRegex.date,
                     "alertText": ' '
                 },
                 "minCheckbox": {
                     "regex": "none",
                     "alertText": " ",
                 }
            };
        }
    };
    $.validationEngineLanguage.newLang();
})(jQuery);

/**
 * Upload file method
 * @param idInput
 * @param idFile
 * @returns
 */
function uploadFileCsv(idInput, idFile) {
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
    // Check format file style is correct
    if (!isValidFileType(file)) {
        showMessage('E0135', textUpload);
        $(idInput1).val('');
        return;
    } else if ($(idFile1).get(0).files[0].size === 0) {
        $(idInput1).val('');
        showMessage('E0130', textUpload);
        return;
    } else if (!isOkfileSize(idFile)) {
        $(idInput1).val('');
        return;
    }

    if (file) {
        $(idInput1).val(textUpload);
    } else {
        $(idInput1).val('');
        showMessage('E0137', textUpload);
        return;
    }
}

/**
 * Method import csv show message dialog confirm
 *
 * @returns
 */
function importCsv() {
    var message = getMessage('C0002');
    $('#message').html(message);

    $("#alertMessage").dialog({
        modal:true, //モーダル表示
        title: dialog.title, //タイトル
        buttons: {
        "OK": function() {
            $(this).dialog("close");
                ajaxImportCsv();
            },
        "キャンセル": function() {
            $(this).dialog("close");
            }
        }
    });
}

function ajaxImportCsv() {
    var file = $('#real-file')[0].files[0];
    if (file == '' || file == undefined ) {
        return;
    }
    var form = new FormData();
    form.append('fileCsv', file);
    var errorDiv = $('.alert-error');
     $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: BASE_URL + "/MDA0110/importCsv",
            dataType: 'json',
            headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
            data: form,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 1000000,
            success: function(data, textStatus, xhr) {
                errorDiv.hide();
                setDataToItems (data);
	            //Calculate input required number
                calcInputRequired();
            },
            error: function(jqXHR, status, errorThrown) {

                errorDiv.show();
                //Check file not found
                if (jqXHR.status == 0) {
                    errorDiv.html(getMessage('E0137'));
                } else {
                    //When have error message
                    errorDiv.html(jqXHR.responseJSON.message);
                }
            }
        });
}

/**
 * Check file size import
 * @param idFile
 * @returns
 */
function isOkfileSize(idFile) {
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

        showMessage('E0133', [fszById + ' ' + fSExt[i]]);
        return false;
    } else {
        return true;
    }
}

/**
 * Check file type upload
 * @param file
 * @returns
 */
function isValidFileType(file) {

    var exts = ["csv"];
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

function setDataToItems(data) {
    $('input[name="basicReportInfo.txtApartmentName"]').val(data.apartmentName);
    $('input[name="basicReportInfo.txtApartmentNamePhonetic"]').val(data.apartmentNamePhonetic);
    $('input[name="basicReportInfo.txtApartmentZipCode1"]').val(data.apartmentZipCode1);
    $('input[name="basicReportInfo.txtApartmentZipCode2"]').val(data.apartmentZipCode2);
    $('input[name="basicReportInfo.txtApartmentAddress2"]').val(data.apartmentAddress2);
    $('input[name="infoAreaCommon.calNotificationDate"]').val(data.notificationDate);
    $('input[name="infoAreaCommon.txtNotificationGroupName"]').val(data.notificationGroupName);
    $('input[name="infoAreaCommon.txtNotificationPersonName"]').val(data.notificationPersonName);
    if (data.changeReason == null || data.changeReason == "") {
        $("input[name='infoAreaCommon.rdoChangeReason']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoChangeReason'][value='" + data.changeReason + "']").prop('checked', true);
    }
    if (data.lostElseReasonCode == null || data.lostElseReasonCode == "") {
        $("input[name='infoAreaCommon.rdoLostElseReasonCode']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoLostElseReasonCode'][value='" + data.lostElseReasonCode + "']").prop('checked', true);
    }
    $('input[name="infoAreaCommon.txtLostElseReasonElse"]').val(data.lostElseReasonElse);
    if (data.groupYesno == null || data.groupYesno == "") {
        $("input[name='infoAreaCommon.rdoGroupYesno']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoGroupYesno'][value='" + data.groupYesno + "']").prop('checked', true);
    }
    $('input[name="infoAreaCommon.txtApartmentNumber"]').val(data.apartmentNumber);
    if (data.groupForm == null || data.groupForm == "") {
        $("input[name='infoAreaCommon.rdoGroupForm']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoGroupForm'][value='" + data.groupForm + "']").prop('checked', true);
    }
    $('input[name="infoAreaCommon.txtGroupFormElse"]').val(data.groupFormElse);
    $('input[name="infoAreaCommon.txtHouseNumber"]').val(data.houseNumber);
    $('input[name="infoAreaCommon.txtFloorNumber"]').val(data.floorNumber);
    $('input[name="infoAreaCommon.calBuiltDate"]').val(data.builtDate);
    if (data.landRights == null || data.landRights == "") {
        $("input[name='infoAreaCommon.rdoLandRights']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoLandRights'][value='" + data.landRights + "']").prop('checked', true);
    }
    $('input[name="infoAreaCommon.txtLandRightsElse"]').val(data.landRightsElse);
    if (data.userfor == null || data.userfor == "") {
        $("input[name='infoAreaCommon.rdoUsefor']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoUsefor'][value='" + data.userfor + "']").prop('checked', true);
    }
    $('input[name="infoAreaCommon.txtUseforElse"]').val(data.userforElse);
    if (data.managementForm == null || data.managementForm == "") {
        $("input[name='infoAreaCommon.rdoManagementForm']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoManagementForm'][value='" + data.managementForm + "']").prop('checked', true);
    }
    $('input[name="infoAreaCommon.txtManagementFormElse"]').val(data.managementFormElse);
    $('input[name="infoAreaCommon.txtManagerName"]').val(data.managerName);
    $('input[name="infoAreaCommon.txtManagerNamePhonetic"]').val(data.managerNamePhonetic);
    $('input[name="infoAreaCommon.txtManagerZipCode1"]').val(data.managerZipCode1);
    $('input[name="infoAreaCommon.txtManagerZipCode2"]').val(data.managerZipCode2);
    $('input[name="infoAreaCommon.txtManagerAddress"]').val(data.managerAddress);
    $('input[name="infoAreaCommon.txtManagerTelno1"]').val(data.managerTelno1);
    $('input[name="infoAreaCommon.txtManagerTelno2"]').val(data.managerTelno2);
    $('input[name="infoAreaCommon.txtManagerTelno3"]').val(data.managerTelno3);
    if (data.group == null || data.group == "") {
        $("input[name='infoAreaCommon.rdoGroup']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoGroup'][value='"+ data.group +"']").prop('checked', true);
    }
    if (data.manager == null || data.manager == "") {
        $("input[name='infoAreaCommon.rdoManager']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoManager'][value='"+ data.manager +"']").prop('checked', true);
    }
    if (data.manager == null || data.manager == "") {
        $("input[name='infoAreaCommon.rdoManager']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoManager'][value='"+ data.manager +"']").prop('checked', true);
    }
    if (data.rule == null || data.rule == "") {
        $("input[name='infoAreaCommon.rdoRule']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoRule'][value='"+ data.rule +"']").prop('checked', true);
    }
    $('input[name="infoAreaCommon.txtRuleChangeYear"]').val(data.ruleChangeYear);
    if (data.oneyearOver == null || data.oneyearOver == "") {
        $("input[name='infoAreaCommon.rdoOneyearOver']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoOneyearOver'][value='"+ data.oneyearOver +"']").prop('checked', true);
    }
    if (data.minutes == null || data.minutes == "") {
        $("input[name='infoAreaCommon.rdoMinutes']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoMinutes'][value='"+ data.minutes +"']").prop('checked', true);
    }
    if (data.managementCost == null || data.managementCost == "") {
        $("input[name='infoAreaCommon.rdoManagementCost']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoManagementCost'][value='"+ data.managementCost +"']").prop('checked', true);
    }
    if (data.repairCost == null || data.repairCost == "") {
        $("input[name='infoAreaCommon.rdoRepairCost']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoRepairCost'][value='"+ data.repairCost +"']").prop('checked', true);
    }
    $('input[name="infoAreaCommon.txtRepairMonthlycost"]').val(data.repairMonthlycost);
    if (data.repairPlan == null || data.repairPlan == "") {
        $("input[name='infoAreaCommon.rdoRepairPlan']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoRepairPlan'][value='"+ data.repairPlan +"']").prop('checked', true);
    }
    $('input[name="infoAreaCommon.txtRepairNearestYear"]').val(data.repairNearestYear);
    if (data.longRepairPlan == null || data.longRepairPlan == "") {
        $("input[name='infoAreaCommon.rdoLongRepairPlan']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoLongRepairPlan'][value='"+ data.longRepairPlan +"']").prop('checked', true);
    }
    $('input[name="infoAreaCommon.txtLongRepairPlanYear"]').val(data.longRepairPlanYear);
    $('input[name="infoAreaCommon.txtLongRepairPlanPeriod"]').val(data.longRepairPlanPeriod);
    $('input[name="infoAreaCommon.txtLongRepairPlanYearFrom"]').val(data.longRepairPlanYearFrom);
    $('input[name="infoAreaCommon.txtLongRepairPlanYearTo"]').val(data.longRepairPlanYearTo);
    if (data.arrearageRule == null || data.arrearageRule == "") {
        $("input[name='infoAreaCommon.rdoArrearageRule']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoArrearageRule'][value='"+ data.arrearageRule +"']").prop('checked', true);
    }
    if (data.segment == null || data.segment == "") {
        $("input[name='infoAreaCommon.rdoSegment']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoSegment'][value='"+ data.segment +"']").prop('checked', true);
    }
    if (data.emptyPercent == null || data.emptyPercent == "") {
        $("input[name='infoAreaCommon.rdoEmptyPercent']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoEmptyPercent'][value='"+ data.emptyPercent +"']").prop('checked', true);
    }
    $('input[name="infoAreaCommon.txtEmptyNumber"]').val(data.emptyNumber);
    if (data.rentalPercent == null || data.rentalPercent == "") {
        $("input[name='infoAreaCommon.rdoRentalPercent']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoRentalPercent'][value='"+ data.rentalPercent +"']").prop('checked', true);
    }
    $('input[name="infoAreaCommon.txtRentalNumber"]').val(data.rentalNumber);
    if (data.seismicDiagnosis == null || data.seismicDiagnosis == "") {
        $("input[name='infoAreaCommon.rdoSeismicDiagnosis']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoSeismicDiagnosis'][value='"+ data.seismicDiagnosis +"']").prop('checked', true);
    }
    if (data.earthquakeResistance == null || data.earthquakeResistance == "") {
        $("input[name='infoAreaCommon.rdoEarthquakeResistance']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoEarthquakeResistance'][value='"+ data.earthquakeResistance +"']").prop('checked', true);
    }
    if (data.seismicRetrofit == null || data.seismicRetrofit == "") {
        $("input[name='infoAreaCommon.rdoSeismicRetrofit']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoSeismicRetrofit'][value='"+ data.seismicRetrofit +"']").prop('checked', true);
    }
    if (data.designDocument == null || data.designDocument == "") {
        $("input[name='infoAreaCommon.rdoDesignDocument']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoDesignDocument'][value='"+ data.designDocument +"']").prop('checked', true);
    }
    if (data.repairHistory == null || data.repairHistory == "") {
        $("input[name='infoAreaCommon.rdoRepairHistory']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoRepairHistory'][value='"+ data.repairHistory +"']").prop('checked', true);
    }
    if (data.voluntaryOrganization == null || data.voluntaryOrganization == "") {
        $("input[name='infoAreaCommon.rdoVoluntaryOrganization']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoVoluntaryOrganization'][value='"+ data.voluntaryOrganization +"']").prop('checked', true);
    }
    if (data.disasterPreventionManual == null || data.disasterPreventionManual == "") {
        $("input[name='infoAreaCommon.rdoDisasterPreventionManual']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoDisasterPreventionManual'][value='"+ data.disasterPreventionManual +"']").prop('checked', true);
    }
    if (data.disasterPreventionStockpile == null || data.disasterPreventionStockpile == "") {
        $("input[name='infoAreaCommon.rdoDisasterPreventionStockpile']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoDisasterPreventionStockpile'][value='"+ data.disasterPreventionStockpile +"']").prop('checked', true);
    }
    if (data.needSupportList == null || data.needSupportList == "") {
        $("input[name='infoAreaCommon.rdoNeedSupportList']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoNeedSupportList'][value='"+ data.needSupportList +"']").prop('checked', true);
    }
    if (data.disasterPreventionRegular == null || data.disasterPreventionRegular == "") {
        $("input[name='infoAreaCommon.rdoDisasterPreventionRegular']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoDisasterPreventionRegular'][value='"+ data.disasterPreventionRegular +"']").prop('checked', true);
    }
    if (data.slope == null || data.slope == "") {
        $("input[name='infoAreaCommon.rdoSlope']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoSlope'][value='"+ data.slope +"']").prop('checked', true);
    }
    if (data.railing == null || data.railing == "") {
        $("input[name='infoAreaCommon.rdoRailing']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoRailing'][value='"+ data.railing +"']").prop('checked', true);
    }
    if (data.elevator == null || data.elevator == "") {
        $("input[name='infoAreaCommon.rdoElevator']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoElevator'][value='"+ data.elevator +"']").prop('checked', true);
    }
    if (data.led == null || data.led == "") {
        $("input[name='infoAreaCommon.rdoLed']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoLed'][value='"+ data.led +"']").prop('checked', true);
    }
    if (data.heatShielding == null || data.heatShielding == "") {
        $("input[name='infoAreaCommon.rdoHeatShielding']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoHeatShielding'][value='"+ data.heatShielding +"']").prop('checked', true);
    }
    if (data.equipmentCharge == null || data.equipmentCharge == "") {
        $("input[name='infoAreaCommon.rdoEquipmentCharge']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoEquipmentCharge'][value='"+ data.equipmentCharge +"']").prop('checked', true);
    }
    if (data.community == null || data.community == "") {
        $("input[name='infoAreaCommon.rdoCommunity']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoCommunity'][value='"+ data.community +"']").prop('checked', true);
    }
    if (data.contactProperty == null || data.contactProperty == "") {
        $("input[name='infoAreaCommon.rdoContactProperty']").prop('checked', false);
    } else {
        $("input[name='infoAreaCommon.rdoContactProperty'][value='"+ data.contactProperty +"']").prop('checked', true);
    }
    $('input[name="infoAreaCommon.txtContactPropertyElse"]').val(data.contactPropertyElse);
    $('input[name="infoAreaCommon.txtContactZipCode1"]').val(data.contactZipCode1);
    $('input[name="infoAreaCommon.txtContactZipCode2"]').val(data.contactZipCode2);
    $('input[name="infoAreaCommon.txtContactAddress"]').val(data.contactAddress);
    $('input[name="infoAreaCommon.txtContactTelno1"]').val(data.contactTelno1);
    $('input[name="infoAreaCommon.txtContactTelno2"]').val(data.contactTelno2);
    $('input[name="infoAreaCommon.txtContactTelno3"]').val(data.contactTelno3);
    $('input[name="infoAreaCommon.txtContactName"]').val(data.contactName);
    $('input[name="infoAreaCommon.txtContactNamePhonetic"]').val(data.contactNamePhonetic);
    $('input[name="infoAreaCommon.txtContactMail"]').val(data.contactMail);
    $('input[name="infoAreaCommon.txtContactMailConfirm"]').val(data.contactMailConfirm);
    $('textarea[name="infoAreaCommon.txaOptional"]').text(data.optional);
    showHideCommonArea();
}

/**
 * Restore temporary data
 * @param apartmentId
 * @param notificationNo
 * @returns
 */
function restoreTemporary(apartmentId, notificationNo) {
    var form = new FormData();
    form.append('apartmentId', null != apartmentId ? apartmentId : "");
    form.append('newestNotificationNo', null != notificationNo ? notificationNo : "");

    var message = getMessage('C0002');
    $('#message').html(message);

    var errorDiv = $('.alert-error');
    $("#alertMessage").dialog({
        modal:true, //モーダル表示
        title: dialog.title, //タイトル
        buttons: {
        "OK": function() {
            $(this).dialog("close");
            //Call ajax to restore data
                 $.ajax({
                        type: "POST",
                        url: BASE_URL + "/MDA0110/restore",
                        dataType: 'json',
                        headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
                        data: form,
                        processData: false,
                        contentType: false,
                        cache: false,
                        timeout: 1000000,
                        success: function (data, textStatus, xhr) {
                            data.errorMessages == undefined ? setTemporaryDataToItems(data) : undefined;
                            $( "#validApartmentZipCode" ).click();
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            errorDiv.show();
                            var errors = jqXHR.responseJSON.errorMessages;
                            var fullMessage = "";
                            for(var i = 0; i< errors.length ; i++ ){
                            	fullMessage += errors[i] + '<br>';
                            }
                            errorDiv.html(fullMessage);
                        }
                    });
            },
        "キャンセル": function() {
            $(this).dialog("close");
            }
        }
    });
}

function setTemporaryDataToItems(data) {
    $('input[name="mansionInfo.lblApartmentName"]').val(data.apartmentName);
    $('input[name="mansionInfo.lblApartmentNamePhonetic"]').val(data.apartmentNamePhonetic);
    $('input[name="mansionInfo.lblApartmentAddress"]').val(data.address);

    $('input[name="basicReportInfo.txtApartmentName"]').val(data.registrationVo.basicReportInfo.txtApartmentName);
    $('input[name="basicReportInfo.txtApartmentNamePhonetic"]').val(data.registrationVo.basicReportInfo.txtApartmentNamePhonetic);
    $('input[name="basicReportInfo.txtApartmentZipCode1"]').val(data.registrationVo.basicReportInfo.txtApartmentZipCode1);
    $('input[name="basicReportInfo.txtApartmentZipCode2"]').val(data.registrationVo.basicReportInfo.txtApartmentZipCode2);
    $('input[name="basicReportInfo.lblApartmentAddress1"]').val(data.registrationVo.basicReportInfo.lblApartmentAddress1);
    $('input[name="basicReportInfo.txtApartmentAddress2"]').val(data.registrationVo.basicReportInfo.txtApartmentAddress2);
    $('input[name="infoAreaCommon.calNotificationDate"]').val(data.registrationVo.infoAreaCommon.calNotificationDate);
    $('input[name="infoAreaCommon.txtNotificationGroupName"]').val(data.registrationVo.infoAreaCommon.txtNotificationGroupName);
    $('input[name="infoAreaCommon.txtNotificationPersonName"]').val(data.registrationVo.infoAreaCommon.txtNotificationPersonName);
    $("input[name='infoAreaCommon.rdoChangeReason'][value='" + data.registrationVo.infoAreaCommon.rdoChangeReason + "']").prop('checked', true);
    $("input[name='infoAreaCommon.rdoLostElseReasonCode'][value='" + data.registrationVo.infoAreaCommon.rdoLostElseReasonCode + "']").prop('checked', true);
    $('input[name="infoAreaCommon.txtLostElseReasonElse"]').val(data.registrationVo.infoAreaCommon.txtLostElseReasonElse);
    $("input[name='infoAreaCommon.rdoGroupYesno'][value='" + data.registrationVo.infoAreaCommon.rdoGroupYesno + "']").prop('checked', true);
    $('input[name="infoAreaCommon.txtApartmentNumber"]').val(data.registrationVo.infoAreaCommon.txtApartmentNumber);
    $("input[name='infoAreaCommon.rdoGroupForm'][value='" + data.registrationVo.infoAreaCommon.rdoGroupForm + "']").prop('checked', true);
    $('input[name="infoAreaCommon.txtGroupFormElse"]').val(data.registrationVo.infoAreaCommon.txtGroupFormElse);
    $('input[name="infoAreaCommon.txtHouseNumber"]').val(data.registrationVo.infoAreaCommon.txtHouseNumber);
    $('input[name="infoAreaCommon.txtFloorNumber"]').val(data.registrationVo.infoAreaCommon.txtFloorNumber);
    $('input[name="infoAreaCommon.calBuiltDate"]').val(data.registrationVo.infoAreaCommon.calBuiltDate);
    $("input[name='infoAreaCommon.rdoLandRights'][value='" + data.registrationVo.infoAreaCommon.rdoLandRights + "']").prop('checked', true);
    $('input[name="infoAreaCommon.txtLandRightsElse"]').val(data.registrationVo.infoAreaCommon.txtLandRightsElse);
    $("input[name='infoAreaCommon.rdoUsefor'][value='" + data.registrationVo.infoAreaCommon.rdoUsefor + "']").prop('checked', true);
    $('input[name="infoAreaCommon.txtUseforElse"]').val(data.registrationVo.infoAreaCommon.txtUseforElse);
    $("input[name='infoAreaCommon.rdoManagementForm'][value='" + data.registrationVo.infoAreaCommon.rdoManagementForm + "']").prop('checked', true);
    $('input[name="infoAreaCommon.txtManagementFormElse"]').val(data.registrationVo.infoAreaCommon.txtManagementFormElse);
    $('input[name="infoAreaCommon.txtManagerName"]').val(data.registrationVo.infoAreaCommon.txtManagerName);
    $('input[name="infoAreaCommon.txtManagerNamePhonetic"]').val(data.registrationVo.infoAreaCommon.txtManagerNamePhonetic);

    $('input[name="infoAreaCommon.txtManagerZipCode1"]').val(data.registrationVo.infoAreaCommon.txtManagerZipCode1);
    $('input[name="infoAreaCommon.txtManagerZipCode2"]').val(data.registrationVo.infoAreaCommon.txtManagerZipCode2);
    $('input[name="infoAreaCommon.txtManagerAddress"]').val(data.registrationVo.infoAreaCommon.txtManagerAddress);
    $('input[name="infoAreaCommon.txtManagerTelno1"]').val(data.registrationVo.infoAreaCommon.txtManagerTelno1);
    $('input[name="infoAreaCommon.txtManagerTelno2"]').val(data.registrationVo.infoAreaCommon.txtManagerTelno2);
    $('input[name="infoAreaCommon.txtManagerTelno3"]').val(data.registrationVo.infoAreaCommon.txtManagerTelno3);
    $("input[name='infoAreaCommon.rdoGroup'][value='" + data.registrationVo.infoAreaCommon.rdoGroup + "']").prop('checked', true);
    $("input[name='infoAreaCommon.rdoManager'][value='" + data.registrationVo.infoAreaCommon.rdoManager + "']").prop('checked', true);
    $("input[name='infoAreaCommon.rdoRule'][value='" + data.registrationVo.infoAreaCommon.rdoRule + "']").prop('checked', true);
    $('input[name="infoAreaCommon.txtRuleChangeYear"]').val(data.registrationVo.infoAreaCommon.txtRuleChangeYear);
    $("input[name='infoAreaCommon.rdoOneyearOver'][value='" + data.registrationVo.infoAreaCommon.rdoOneyearOver + "']").prop('checked', true);
    $("input[name='infoAreaCommon.rdoMinutes'][value='" + data.registrationVo.infoAreaCommon.rdoMinutes + "']").prop('checked', true);
    $("input[name='infoAreaCommon.rdoManagementCost'][value='" + data.registrationVo.infoAreaCommon.rdoManagementCost + "']").prop('checked', true);
    $("input[name='infoAreaCommon.rdoRepairCost'][value='" + data.registrationVo.infoAreaCommon.rdoRepairCost + "']").prop('checked', true);
    $('input[name="infoAreaCommon.txtRepairMonthlycost"]').val(data.registrationVo.infoAreaCommon.txtRepairMonthlycost);
    $("input[name='infoAreaCommon.rdoRepairPlan'][value='" + data.registrationVo.infoAreaCommon.rdoRepairPlan + "']").prop('checked', true);
    $('input[name="infoAreaCommon.txtRepairNearestYear"]').val(data.registrationVo.infoAreaCommon.txtRepairNearestYear);
    $("input[name='infoAreaCommon.rdoLongRepairPlan'][value='" + data.registrationVo.infoAreaCommon.rdoLongRepairPlan + "']").prop('checked', true);
    $('input[name="infoAreaCommon.txtLongRepairPlanYear"]').val(data.registrationVo.infoAreaCommon.txtLongRepairPlanYear);
    $('input[name="infoAreaCommon.txtLongRepairPlanPeriod"]').val(data.registrationVo.infoAreaCommon.txtLongRepairPlanPeriod);
    $('input[name="infoAreaCommon.txtLongRepairPlanYearFrom"]').val(data.registrationVo.infoAreaCommon.txtLongRepairPlanYearFrom);
    $('input[name="infoAreaCommon.txtLongRepairPlanYearTo"]').val(data.registrationVo.infoAreaCommon.txtLongRepairPlanYearTo);
    $("input[name='infoAreaCommon.rdoArrearageRule'][value='" + data.registrationVo.infoAreaCommon.rdoArrearageRule + "']").prop('checked', true);
    $("input[name='infoAreaCommon.rdoSegment'][value='" + data.registrationVo.infoAreaCommon.rdoSegment + "']").prop('checked', true);
    $("input[name='infoAreaCommon.rdoEmptyPercent'][value='" + data.registrationVo.infoAreaCommon.rdoEmptyPercent + "']").prop('checked', true);
    $('input[name="infoAreaCommon.txtEmptyNumber"]').val(data.registrationVo.infoAreaCommon.txtEmptyNumber);
    $("input[name='infoAreaCommon.rdoRentalPercent'][value='" + data.registrationVo.infoAreaCommon.rdoRentalPercent + "']").prop('checked', true);
    $('input[name="infoAreaCommon.txtRentalNumber"]').val(data.registrationVo.infoAreaCommon.txtRentalNumber);
    $("input[name='infoAreaCommon.rdoSeismicDiagnosis'][value='" + data.registrationVo.infoAreaCommon.rdoSeismicDiagnosis + "']").prop('checked', true);
    $("input[name='infoAreaCommon.rdoEarthquakeResistance'][value='" + data.registrationVo.infoAreaCommon.rdoEarthquakeResistance + "']").prop('checked', true);
    $("input[name='infoAreaCommon.rdoSeismicRetrofit'][value='" + data.registrationVo.infoAreaCommon.rdoSeismicRetrofit + "']").prop('checked', true);
    $("input[name='infoAreaCommon.rdoDesignDocument'][value='" + data.registrationVo.infoAreaCommon.rdoDesignDocument + "']").prop('checked', true);
    $("input[name='infoAreaCommon.rdoRepairHistory'][value='" + data.registrationVo.infoAreaCommon.rdoRepairHistory + "']").prop('checked', true);
    $("input[name='infoAreaCommon.rdoVoluntaryOrganization'][value='" + data.registrationVo.infoAreaCommon.rdoVoluntaryOrganization + "']").prop('checked', true);
    $("input[name='infoAreaCommon.rdoDisasterPreventionManual'][value='" + data.registrationVo.infoAreaCommon.rdoDisasterPreventionManual + "']").prop('checked', true);
    $("input[name='infoAreaCommon.rdoDisasterPreventionStockpile'][value='" + data.registrationVo.infoAreaCommon.rdoDisasterPreventionStockpile + "']").prop('checked', true);
    $("input[name='infoAreaCommon.rdoNeedSupportList'][value='" + data.registrationVo.infoAreaCommon.rdoNeedSupportList + "']").prop('checked', true);
    $("input[name='infoAreaCommon.rdoDisasterPreventionRegular'][value='" + data.registrationVo.infoAreaCommon.rdoDisasterPreventionRegular + "']").prop('checked', true);
    $("input[name='infoAreaCommon.rdoSlope'][value='" + data.registrationVo.infoAreaCommon.rdoSlope + "']").prop('checked', true);
    $("input[name='infoAreaCommon.rdoRailing'][value='" + data.registrationVo.infoAreaCommon.rdoRailing + "']").prop('checked', true);
    $("input[name='infoAreaCommon.rdoElevator'][value='" + data.registrationVo.infoAreaCommon.rdoElevator + "']").prop('checked', true);
    $("input[name='infoAreaCommon.rdoLed'][value='" + data.registrationVo.infoAreaCommon.rdoLed + "']").prop('checked', true);
    $("input[name='infoAreaCommon.rdoHeatShielding'][value='" + data.registrationVo.infoAreaCommon.rdoHeatShielding + "']").prop('checked', true);
    $("input[name='infoAreaCommon.rdoEquipmentCharge'][value='" + data.registrationVo.infoAreaCommon.rdoEquipmentCharge + "']").prop('checked', true);
    $("input[name='infoAreaCommon.rdoCommunity'][value='" + data.registrationVo.infoAreaCommon.rdoCommunity + "']").prop('checked', true);
    $("input[name='infoAreaCommon.rdoContactProperty'][value='" + data.registrationVo.infoAreaCommon.rdoContactProperty + "']").prop('checked', true);
    $('input[name="infoAreaCommon.txtContactPropertyElse"]').val(data.registrationVo.infoAreaCommon.txtContactPropertyElse);
    $('input[name="infoAreaCommon.txtContactZipCode1"]').val(data.registrationVo.infoAreaCommon.txtContactZipCode1);
    $('input[name="infoAreaCommon.txtContactZipCode2"]').val(data.registrationVo.infoAreaCommon.txtContactZipCode2);
    $('input[name="infoAreaCommon.txtContactAddress"]').val(data.registrationVo.infoAreaCommon.txtContactAddress);
    $('input[name="infoAreaCommon.txtContactTelno1"]').val(data.registrationVo.infoAreaCommon.txtContactTelno1);
    $('input[name="infoAreaCommon.txtContactTelno2"]').val(data.registrationVo.infoAreaCommon.txtContactTelno2);
    $('input[name="infoAreaCommon.txtContactTelno3"]').val(data.registrationVo.infoAreaCommon.txtContactTelno3);
    $('input[name="infoAreaCommon.txtContactName"]').val(data.registrationVo.infoAreaCommon.txtContactName);
    $('input[name="infoAreaCommon.txtContactNamePhonetic"]').val(data.registrationVo.infoAreaCommon.txtContactNamePhonetic);
    $('input[name="infoAreaCommon.txtContactMail"]').val(data.registrationVo.infoAreaCommon.txtContactMail);
    $('input[name="infoAreaCommon.txtContactMailConfirm"]').val(data.registrationVo.infoAreaCommon.txtContactMailConfirm);
    $('textarea[name="infoAreaCommon.txaOptional"]').text(data.registrationVo.infoAreaCommon.txaOptional);
}

//export data to file csv
function exportCsv() {
    var errorDiv = $('.alert-error');
    $.ajax ({
        type: "POST",
        url : BASE_URL + "/MDA0110/exportCsv",
        data : $('#form').serialize(),
        success: function(data, textStatus, xhr) {
            errorDiv.hide();
            exportToCsv(data, FILE_NAME_CSV_EXPORT + currentDateForCsv() + ".csv");
            addAndRemoveAttrWhenExportError();
        },
        error: function(jqXHR, status, errorThrown) {
            errorDiv.show();
            //Check file not found
            //When have error message
            var errors = jqXHR.responseJSON.errorMessages;
            var fullMessage = "";
            for(var i = 0; i< errors.length ; i++ ){
            	fullMessage += errors[i] + '<br>';
            }
            errorDiv.html(fullMessage);
            addAndRemoveAttrWhenExportError();
            scrollOnTop();
        }
    });
}

/**
 * Handle Export file
 * @param data
 * @param filename
 * @returns
 */
function exportToCsv(data, filename) {
    var blob = new Blob([data], { type: 'text/csv;charset=utf-8;' });
    if (navigator.msSaveBlob) { // IE 10+
        navigator.msSaveBlob(blob, filename);
    } else {
        var link = document.createElement("a");
        if (link.download !== undefined) { // feature detection
            // Browsers that support HTML5 download attribute
            var url = URL.createObjectURL(blob);
            link.setAttribute("href", url);
            link.setAttribute("download", filename);
            link.style.visibility = 'hidden';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }
    }
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

/**
 * Click back button
 * @param apartmentId
 * @returns
 */
function turnBack(apartmentId) {
    //確認メッセージ (C0001).
    var message = getMessage('C0001');
    $('#message').html(message);

    $("#alertMessage").dialog({
        modal:true, //モーダル表示
        title: dialog.title, //タイトル
        buttons: {
        "OK": function() {
            $(this).dialog("close");
                if (ROLE_MANSION == '5') {
                    window.location.href = BASE_URL + '/MBA0110';
                } else { //管理組合用
                    var valNotification = $('#notificationNo').val();
                    $('input[id=apartment-id]').val(apartmentId);
                    $('#notification-id').val(valNotification);
                    //メニュー（管理組合用)（MBA0110）/メニュー（管理組合用）（MBA0110）
                    $('#submitGJAForm').submit();
                }
            },
        "キャンセル": function() {
            $(this).dialog("close");
            }
        }
    });
}

/**
 * Click back link
 * @param
 * @returns
 */
function backToMenu() {
    //確認メッセージ (C0001).
    var message = getMessage('C0001');
    $('#message').html(message);

    $("#alertMessage").dialog({
        modal: true, //モーダル表示
        title: dialog.title, //タイトル
        buttons: {
            "OK": function () {
                $(this).dialog("close");
                if (ROLE_MANSION == '5') {
                    window.location.href = BASE_URL + '/MBA0110';
                } else {
                    // $('button-id').val('');
                    // $('#submitGJA0110Form').submit();
                    window.location.href = BASE_URL + '/GBA0110';
                }
            },
            "キャンセル": function () {
                $(this).dialog("close");
            }
        }
    });
}

/**
 * Append tap index
 */
function appendTapIndexMDA0110(){
    $("input[name='chkConfirm']").parent().attr("tabindex", function(index, attr) { return index + 718;});
    $("#registerNotification").attr("tabindex", "719");
}

function addAndRemoveAttrExport(){
    /*マンション名*/
    $("input[name='basicReportInfo.txtApartmentName']").removeAttr("data-validation-engine");
    $("input[name='basicReportInfo.txtApartmentName']").attr("data-validation-engine", "validate[custom2[prohibitionCharacter], maxSize[50]]");
    /*マンション名フリガナ*/
    $("input[name='basicReportInfo.txtApartmentNamePhonetic']").removeAttr("data-validation-engine");
    $("input[name='basicReportInfo.txtApartmentNamePhonetic']").attr("data-validation-engine", "validate[custom[kana]]");
    /* マンション郵便番号１ */
    $('#txtApartmentZipCode1').removeAttr("data-validation-engine");
    $('#txtApartmentZipCode1').attr("data-validation-engine", "validate[minSize[3], maxSize[3], custom[halfwidthNumbers]]");
    /* マンション郵便番号２ */
    $('#txtApartmentZipCode2').removeAttr("data-validation-engine");
    $('#txtApartmentZipCode2').attr("data-validation-engine", "validate[minSize[4],  maxSize[4], custom[halfwidthNumbers]]");
    /* 住所2 */
    $('#txtApartmentAddress2').removeAttr("data-validation-engine");
    $('#txtApartmentAddress2').attr("data-validation-engine", "validate[custom[fullwidthCharacters], maxSize[100]]");
    /*届出年月日*/
    $('#datepicker-1').removeAttr("data-validation-engine");
    /* 管理組合 */
    $("input[name='infoAreaCommon.rdoGroup']").removeAttr("data-validation-engine");
    /* 管理者等 */
    $("input[name='infoAreaCommon.rdoManager']").removeAttr("data-validation-engine");
    /* 管理規約 */
    $("input[name='infoAreaCommon.rdoRule']").removeAttr("data-validation-engine");
    /* 年1回以上の開催 */
    $("input[name='infoAreaCommon.rdoOneyearOver']").removeAttr("data-validation-engine");
    /* 管理費 */
    $("input[name='infoAreaCommon.rdoManagementCost']").removeAttr("data-validation-engine");
    /* 修繕積立金 */
    $("input[name='infoAreaCommon.rdoRepairCost']").removeAttr("data-validation-engine");
    /* 修繕の計画的な実施
          （大規模な修繕工事） */
    $("input[name='infoAreaCommon.rdoRepairPlan']").removeAttr("data-validation-engine");
    /* 連絡先属性 */
    $("input[name='infoAreaCommon.rdoContactProperty']").removeAttr("data-validation-engine");
    /* 連絡先郵便番号1 */
    $('#txtContactZipCode1').removeAttr("data-validation-engine");
    $('#txtContactZipCode1').attr("data-validation-engine", "validate[custom[halfwidthNumbers], minSize[3], maxSize[3]]");
    /* 連絡先郵便番号2 */
    $('#txtContactZipCode2').removeAttr("data-validation-engine");
    $('#txtContactZipCode2').attr("data-validation-engine", "validate[custom[halfwidthNumbers], minSize[4], maxSize[4]]");
    /* 連絡先住所 */
    $('#txtContactAddress').removeAttr("data-validation-engine");
    $('#txtContactAddress').attr("data-validation-engine", "validate[custom[fullwidthCharacters], maxSize[100]]");
    /* 連絡先電話番号1 */
    $("input[name='infoAreaCommon.txtContactTelno1']").removeAttr("data-validation-engine");
    $("input[name='infoAreaCommon.txtContactTelno1']").attr("data-validation-engine", "validate[maxSize[5], custom[halfwidthNumbers]]");
    /* 連絡先電話番号2 */
    $("input[name='infoAreaCommon.txtContactTelno2']").removeAttr("data-validation-engine");
    $("input[name='infoAreaCommon.txtContactTelno2']").attr("data-validation-engine", "validate[maxSize[4], custom[halfwidthNumbers]]");
    /* 連絡先電話番号3 */
    $("input[name='infoAreaCommon.txtContactTelno3']").removeAttr("data-validation-engine");
    $("input[name='infoAreaCommon.txtContactTelno3']").attr("data-validation-engine", "validate[maxSize[4], custom[halfwidthNumbers]]");
    /* 連絡先氏名 */
    $("input[name='infoAreaCommon.txtContactName']").removeAttr("data-validation-engine");
    $("input[name='infoAreaCommon.txtContactName']").attr("data-validation-engine", "validate[maxSize[34], custom2[prohibitionCharacter]]");
    /* 連絡先氏名フリガナ */
    $("input[name='infoAreaCommon.txtContactNamePhonetic']").removeAttr("data-validation-engine");
    $("input[name='infoAreaCommon.txtContactNamePhonetic']").attr("data-validation-engine", "validate[maxSize[34], custom[kana]]");
}

function addAndRemoveAttrWhenExportError(){
    /*マンション名*/
    $("input[name='basicReportInfo.txtApartmentName']").removeAttr("data-validation-engine");
    $("input[name='basicReportInfo.txtApartmentName']").attr("data-validation-engine", "validate[required, custom2[prohibitionCharacter], maxSize[50]]");
    /*マンション名フリガナ*/
    $("input[name='basicReportInfo.txtApartmentNamePhonetic']").removeAttr("data-validation-engine");
    $("input[name='basicReportInfo.txtApartmentNamePhonetic']").attr("data-validation-engine", "validate[required, custom[kana]]");
    /* マンション郵便番号１ */
    $('#txtApartmentZipCode1').removeAttr("data-validation-engine");
    $('#txtApartmentZipCode1').attr("data-validation-engine", "validate[required, minSize[3], maxSize[3], custom[halfwidthNumbers]]");
    /* マンション郵便番号２ */
    $('#txtApartmentZipCode2').removeAttr("data-validation-engine");
    $('#txtApartmentZipCode2').attr("data-validation-engine", "validate[required, minSize[4],  maxSize[4], custom[halfwidthNumbers]]");
    /* 住所2 */
    $('#txtApartmentAddress2').removeAttr("data-validation-engine");
    $('#txtApartmentAddress2').attr("data-validation-engine", "validate[required, custom[fullwidthCharacters], maxSize[100]]");
    /*届出年月日*/
    $('#datepicker-1').removeAttr("data-validation-engine");
    $('#datepicker-1').attr("data-validation-engine", "validate[required]");

    /* 管理組合 */
    $("input[name='infoAreaCommon.rdoGroup']").removeAttr("data-validation-engine");
    $("input[name='infoAreaCommon.rdoGroup']").attr("data-validation-engine", "validate[minCheckbox[1]]");
    /* 管理者等 */
    $("input[name='infoAreaCommon.rdoManager']").removeAttr("data-validation-engine");
    $("input[name='infoAreaCommon.rdoManager']").attr("data-validation-engine", "validate[minCheckbox[1]]");
    
    /* 管理規約 */
    $("input[name='infoAreaCommon.rdoRule']").removeAttr("data-validation-engine");
    $("input[name='infoAreaCommon.rdoRule']").attr("data-validation-engine", "validate[minCheckbox[1]]");
    /* 年1回以上の開催 */
    $("input[name='infoAreaCommon.rdoOneyearOver']").removeAttr("data-validation-engine");
    $("input[name='infoAreaCommon.rdoOneyearOver']").attr("data-validation-engine", "validate[minCheckbox[1]]");
    /* 管理費 */
    $("input[name='infoAreaCommon.rdoManagementCost']").removeAttr("data-validation-engine");
    $("input[name='infoAreaCommon.rdoManagementCost']").attr("data-validation-engine", "validate[minCheckbox[1]]");
    /* 修繕積立金 */
    $("input[name='infoAreaCommon.rdoRepairCost']").removeAttr("data-validation-engine");
    $("input[name='infoAreaCommon.rdoRepairCost']").attr("data-validation-engine", "validate[minCheckbox[1]]");
    /* 修繕の計画的な実施
          （大規模な修繕工事） */
    $("input[name='infoAreaCommon.rdoRepairPlan']").removeAttr("data-validation-engine");
    $("input[name='infoAreaCommon.rdoRepairPlan']").attr("data-validation-engine", "validate[minCheckbox[1]]");
    /* 連絡先属性 */
    $("input[name='infoAreaCommon.rdoContactProperty']").removeAttr("data-validation-engine");
    $("input[name='infoAreaCommon.rdoContactProperty']").attr("data-validation-engine", "validate[minCheckbox[1]]");
    /* 連絡先郵便番号1 */
    $('#txtContactZipCode1').removeAttr("data-validation-engine");
    $('#txtContactZipCode1').attr("data-validation-engine", "validate[required, custom[halfwidthNumbers], minSize[3], maxSize[3]]");
    /* 連絡先郵便番号2 */
    $('#txtContactZipCode2').removeAttr("data-validation-engine");
    $('#txtContactZipCode2').attr("data-validation-engine", "validate[required, custom[halfwidthNumbers], minSize[4], maxSize[4]]");
    /* 連絡先住所 */
    $('#txtContactAddress').removeAttr("data-validation-engine");
    $('#txtContactAddress').attr("data-validation-engine", "validate[required, custom[fullwidthCharacters], maxSize[100]]");
    /* 連絡先電話番号1 */
    $("input[name='infoAreaCommon.txtContactTelno1']").removeAttr("data-validation-engine");
    $("input[name='infoAreaCommon.txtContactTelno1']").attr("data-validation-engine", "validate[required, maxSize[5], custom[halfwidthNumbers]]");
    /* 連絡先電話番号2 */
    $("input[name='infoAreaCommon.txtContactTelno2']").removeAttr("data-validation-engine");
    $("input[name='infoAreaCommon.txtContactTelno2']").attr("data-validation-engine", "validate[required, maxSize[4], custom[halfwidthNumbers]]");
    /* 連絡先電話番号3 */
    $("input[name='infoAreaCommon.txtContactTelno3']").removeAttr("data-validation-engine");
    $("input[name='infoAreaCommon.txtContactTelno3']").attr("data-validation-engine", "validate[required, maxSize[4], custom[halfwidthNumbers]]");
    /* 連絡先氏名 */
    $("input[name='infoAreaCommon.txtContactName']").removeAttr("data-validation-engine");
    $("input[name='infoAreaCommon.txtContactName']").attr("data-validation-engine", "validate[required, maxSize[34], custom2[prohibitionCharacter]]");
    /* 連絡先氏名フリガナ */
    $("input[name='infoAreaCommon.txtContactNamePhonetic']").removeAttr("data-validation-engine");
    $("input[name='infoAreaCommon.txtContactNamePhonetic']").attr("data-validation-engine", "validate[required, maxSize[34], custom[kana]]");
}
