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
var form;
const RDOUSERTYPE = "種別";
const TXTUSERNAME = "氏名";
const TXTUSERNAMEPHONETIC = "氏名フリガナ";
const TXTMAIL = "メールアドレス";
const TXTMAILCONFIRM = "メールアドレス（確認）";
const PHONENUMBER1 = "電話番号";
const PHONENUMBER2 = "電話番号";
const PHONENUMBER3 = "電話番号";
const TXTLOGINID = "ログインID";
const PWDPASSWORD = "パスワード";
const PWDPASSWORDCONFIRM = "パスワード（確認）";
const TXTCONTACT = "備考";
const CONFIRM = 'C0003';
const GGA0110_REGISTRATION = "ユーザ情報を登録";
const ABB0110_LENGTH_6 = '6';
const ABB0110_MAX_LENGTH_16 = '16';
const ABB0110_MAX_LENGTH_120 = '120';
const ABB0110_MAX_LENGTH_5 = '5';
const ABB0110_MAX_LENGTH_4 = '4';
const ABB0110_MAX_LENGTH_30 = '30';

/* Radio */
var radio1 = [0];

function turnBack() {
    $('#submitFormbutton').attr('action', baseUrl + '/ABA0110')
    $('input[name=error]').val("false");
    $('#submitFormbutton').submit();
}
$("#btnNewRegistration").on('click', function(){
    $("#dialog-register").html(getMessage(CONFIRM, GGA0110_REGISTRATION))
    .dialog({
        modal:true, //モーダル表示
        title:"確認ダイアログ", //タイトル
        width: 305,
        buttons: {
        "OK": function() {
            $( "input[name='rdoUserType']" ).removeAttr("disabled");
            $( "#lstCity" ).attr("disabled", false); 
            form = document.getElementsByName('ABB0110form');
            let isValid = $(".user-city-register").validationEngine('validate');
            if(isValid){
            	$(".user-city-register").submit();
            }
            $(this).dialog("close"); 
        },
        "キャンセル": function() {
            $(this).dialog("close"); 
            }
        }
    });
});
$(document).ready(function() {
/* Set event tabIndex for radio */
	$("input[name='rdoUserType']").addClass("check-radio");
    $(".check-radio").parent().on( "keypress keydown click focusout", function(e) {
    	checkRadioByClassId(this, e, radio1, RDOUSERTYPE, false);
    	 if($("input[name='rdoUserType']").is(":checked")){
             if($("#radio-2").is(":checked")){
                 $("#lstCity").removeClass("disabledItem");
                 $( "#lstCity" ).attr("disabled", false); 
             } else {
                 $("#lstCity").addClass("disabledItem");
                 $("#lstCity").val("");
                 $("#lstCity" ).attr("disabled", true);
             }
         }
    });
/* Set event tabIndex for checkBox */
	$("input[name='chkAvailability']").addClass("checkbox-class");
    $(".checkbox-class").parent().on( "keypress", function(e) {
    	$(this).children().spacePress(e);
    });
	$("input[name='chkTermsConditions']").addClass("checkbox-class-confirm");
    $(".checkbox-class-confirm").parent().on( "keypress", function(e) {
    	$(this).children().spacePress(e);
        var checkBox = document.getElementById("chkTermsConditions");
        if (checkBox.checked == true){
            $("#btnNewRegistration").prop('disabled', false);
        } else {
            $("#btnNewRegistration").prop('disabled', true);
        }
    });
/* Initialize display */
    $("#txtUserName").focus();
/* Check active C1045 */
    if(ABB110_GLOBAL_VAR.redirectToABA == "true"){
        $("#dialog-register")
        .html(ABB110_GLOBAL_VAR.messageSuccess)
        .dialog({
         modal:true, //モーダル表示
         title: dialog.title, //タイトル
         buttons: {
         "OK": function() {
                    $(this).dialog("close"); 
                    $('#submitFormbutton').attr('action', baseUrl + '/ABA0110')
                    $('input[name=error]').val("false");
                    $('#submitFormbutton').submit();
                }
             }
        });
    }

    if(ABB110_GLOBAL_VAR.chkAvailability == 2){
       // Check
       $("#cb5").prop("checked", true);
    }
    $("#pwdPassword").val(ABB110_GLOBAL_VAR.pwdPassword);
    $("#pwdPasswordConfirm").val(ABB110_GLOBAL_VAR.pwdPasswordConfirm);

/* Event check */
    if(ABB110_GLOBAL_VAR.userId == null || ABB110_GLOBAL_VAR.userId == '') {
        /* is add user */
        /* rdoUserType 種別 */
        $( "input[name='rdoUserType']" ).removeAttr("disabled");
        /* txtLoginid ログインID */
        $("#txtLoginid").removeClass("disabledItem");
        $( "#txtLoginid" ).prop( "readonly", false );
    }else{
        $( "input[name='rdoUserType']" ).attr( "disabled", true );
        $( "input[name='rdoUserType']" ).parent().addClass("disabledItem");
       /* pwdPassword パスワード */
        $("#pwdPassword").val("123456789");
        $("#pwdPasswordConfirm").val("123456789");
       var rows = $('table.password-sd tr');
       rows.filter('.password').hide();
       /* txtLoginid ログインID */
        $("#txtLoginid").addClass("disabledItem");
        $( "#txtLoginid" ).prop( "readonly", true );
    }
    /* lstCity 区市町村 */
    if(ABB110_GLOBAL_VAR.rdoUserType == 2){
        $("#lstCity").removeClass("disabledItem");
        $( "#lstCity" ).attr("disabled", false); 
    }else if(ABB110_GLOBAL_VAR.rdoUserType == 1 || ABB110_GLOBAL_VAR.rdoUserType == 3 || ABB110_GLOBAL_VAR.rdoUserType == 4){
        $("#lstCity").addClass("disabledItem");
       $( "#lstCity" ).attr("disabled", true); 
    }
    /* lstCity 区市町村 */
    $('input[name="rdoUserType"]').click(function() {
        var radio = document.getElementById("radio-2");
        if (radio.checked){
            $("#lstCity").removeClass("disabledItem");
            $( "#lstCity" ).attr("disabled", false); 
        }else{
            $("#lstCity").addClass("disabledItem");
            $("#lstCity").val("");
            $( "#lstCity" ).attr("disabled", true);
        }
    });

    /* btnRegist 登録 */
    if(ABB110_GLOBAL_VAR.errorMessage != "" && ABB110_GLOBAL_VAR.errorMessage != null){
        // Check
        $("#chkTermsConditions").prop("checked", true);
        $("input[name='btnNewRegistration']").prop('disabled', false);
    }else{
        $("#chkTermsConditions").prop('checked', false);
        $("input[name='btnNewRegistration']").prop('disabled', true);
    }
    $("#chkTermsConditions").change(function() {
        if($("#chkTermsConditions").is(':checked')) {
            $("input[name='btnNewRegistration']").prop('disabled', false);
        } else {
            $("input[name='btnNewRegistration']").prop('disabled', true);
        }
    });

/* Check single items */

    // Apply validationEngine
    appendValidationEngineAttribute();

    // Apply max length
    appendLengthAttribute();

    // Error message for validationEngine
    $(".user-city-register").validationEngine({
        // 以下のパラメータは任意
         promptPosition: "bottomLeft",
         showArrowOnRadioAndCheckbox: true,
         focusFirstField: true,
         maxErrorsPerField: 1,
         scroll: false,
         'custom_error_messages': {
            '#radio-1': {
             'minCheckbox': {
                   'message': getMessage(REQUIRED1, [RDOUSERTYPE])
                }
            },
            '#txtUserName': {
                'required': {
                    'message': getMessage(REQUIRED, [TXTUSERNAME])
                },
                'custom[fullwidthCharacters]' : {
                    'message': getMessage(FULLWIDTH_CHARACTERS, [TXTUSERNAME])
                },
                'maxSize' : {
                    'message': getMessage(MAXIMUM, [TXTUSERNAME, ABB0110_MAX_LENGTH_16])
                },
            },
            '#txtUserNamePhonetic': {
                'required': {
                    'message': getMessage(REQUIRED, [TXTUSERNAMEPHONETIC])
                },
                'custom[kana]' : {
                    'message': getMessage(FULLWIDTH_KATAKANA, [TXTUSERNAMEPHONETIC])
                },
                'maxSize' : {
                    'message': getMessage(MAXIMUM, [TXTUSERNAMEPHONETIC, ABB0110_MAX_LENGTH_16])
                },
            },
            '#txtMail': {
                'required': {
                    'message': getMessage(REQUIRED, [TXTMAIL])
                },
                'custom[email]' : {
                    'message': getMessage(MAIL, [TXTMAIL])
                },
                'custom[halfwidthAlphanumeric]' : {
                    'message': getMessage(HALFWIDTH_ALPHANUMERIC, [TXTMAIL])
                },
                'maxSize' : {
                    'message': getMessage(MAXIMUM, [TXTMAIL, ABB0110_MAX_LENGTH_120])
                },
            },
            '#txtMailConfirm': {
                'required': {
                    'message': getMessage(REQUIRED, [TXTMAILCONFIRM])
                },
            },
            '#txtPhonenumber1': {
                'required': {
                    'message': getMessage(REQUIRED, [PHONENUMBER1])
                },
                'custom[halfwidthNumbers]' : {
                    'message': getMessage(HALFWIDTH_NUMBERS, [PHONENUMBER1])
                },
                'maxSize' : {
                    'message': getMessage(MAXIMUM, [PHONENUMBER1, ABB0110_MAX_LENGTH_5])
                },
            },
            '#txtPhonenumber2': {
                'required': {
                    'message': getMessage(REQUIRED, [PHONENUMBER2])
                },
                'custom[halfwidthNumbers]' : {
                    'message': getMessage(HALFWIDTH_NUMBERS, [PHONENUMBER2])
                },
                'maxSize' : {
                    'message': getMessage(MAXIMUM, [PHONENUMBER2, ABB0110_MAX_LENGTH_5])
                },
            },
            '#txtPhonenumber3': {
                'required': {
                    'message': getMessage(REQUIRED, [PHONENUMBER3])
                },
                'custom[halfwidthNumbers]' : {
                    'message': getMessage(HALFWIDTH_NUMBERS, [PHONENUMBER3])
                },
                'maxSize' : {
                    'message': getMessage(MAXIMUM, [PHONENUMBER3, ABB0110_MAX_LENGTH_5])
                },
            },
            '#txtLoginid': {
                'required': {
                    'message': getMessage(REQUIRED, [TXTLOGINID])
                },
                'custom[halfwidthNumbers]' : {
                    'message': getMessage(HALFWIDTH_NUMBERS, [TXTLOGINID])
                },
                'maxSize' : {
                    'message': getMessage(MAXIMUM, [TXTLOGINID, ABB0110_LENGTH_6])
                },
                'minSize' : {
                    'message': getMessage(MINIMUM, [TXTLOGINID, ABB0110_LENGTH_6])
                },
            },
            '#pwdPassword': {
                'required': {
                    'message': getMessage(REQUIRED, [PWDPASSWORD])
                },
                'custom[halfwidthAlphanumeric]' : {
                    'message': getMessage(HALFWIDTH_ALPHANUMERIC, [PWDPASSWORD])
                },
                // instead of maxSize to checkRangeForSingleItem
            },
            '#pwdPasswordConfirm': {
                'required': {
                    'message': getMessage(REQUIRED, [PWDPASSWORDCONFIRM])
                },
            },
            '#txtContact': {
                'custom2[prohibitionCharacter]' : {
                    'message': getMessage(SPECIALCHARACTERS, [TXTCONTACT])
                },
                'maxSize' : {
                    'message': getMessage(MAXIMUM, [TXTCONTACT, ABB0110_MAX_LENGTH_30])
                },
            },
        },
        onValidationComplete: function(form, status){
        	return status;
        }
    });

    showHideEye();
});
(function($) {
    $.fn.validationEngineLanguage = function() {
    };
    $.validationEngineLanguage = {
        newLang: function() {
            $.validationEngineLanguage.allRules = {
                "required": {
                    "regex": "none",
                    "alertText": ' ',
                },
                "halfwidthNumbers" : {
                    "regex": patternRegex.halfwidthNumbers,
                    "alertText": " ",
                },
                "halfwidthAlphanumeric": {
                     "regex": patternRegex.halfwidthAlphanumeric,
                     "alertText": " ",
                 },
                 "fullwidthCharacters": {
                     "regex":  patternRegex.fullwidthCharacters,
                     "alertText": " ",
                 },
                 "kana": {
                     "regex": patternRegex.kana,
                     "alertText": " ",
                 },
                 "email": {
                     "regex": patternRegex.email,
                     "alertText": " ",
                 },
                 "maxSize": {
                     "regex": "none",
                     "alertText": "* ",
                 },
                 "minSize": {
                     "regex": "none",
                     "alertText": " ",
                 },
                 "prohibitionCharacter": {
                     "regex":  patternRegex.isNotSpecialCharacter,
                     "alertText": " ",
                 },
                 "minCheckbox": {
                    "regex": "none",
                     "alertText": " ",
                 },
                 "phone": {
                     "regex":  patternRegex.phone,
                     "alertText": " ",
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

function appendLengthAttribute() {
    // Append max length attribute
   $('#txtUserName').attr("maxlength", ABB0110_MAX_LENGTH_16);
   $('#txtUserNamePhonetic').attr("maxlength", ABB0110_MAX_LENGTH_16);
   $('#txtMail').attr("maxlength", ABB0110_MAX_LENGTH_120);
   $('#txtPhonenumber1').attr("maxlength", ABB0110_MAX_LENGTH_5);
   $('#txtPhonenumber2').attr("maxlength", ABB0110_MAX_LENGTH_4);
   $('#txtPhonenumber3').attr("maxlength", ABB0110_MAX_LENGTH_4);
   $('#txtContact').attr("maxlength", ABB0110_MAX_LENGTH_30);
   $('#txtLoginid').attr("maxlength", ABB0110_LENGTH_6);
   $('#pwdPassword').attr("maxlength", ABB0110_MAX_LENGTH_16);
}

function appendValidationEngineAttribute() {
    // Append custom
    $('#txtLoginid').attr("custom","ログインID");
    $('#pwdPassword').attr("custom","パスワード");

    // Append data-validation-enginedata
    $('input[name^="rdoUserType"]').attr("data-validation-engine", "validate[minCheckbox[1]");
    $('#txtUserName').attr("data-validation-engine", "validate[required, custom[fullwidthCharacters], maxSize[16]]");
    $('#txtUserNamePhonetic').attr("data-validation-engine", "validate[required, custom[kana], maxSize[16]]");
    $('#txtMail').attr("data-validation-engine", "validate[required, custom[halfwidthAlphanumeric], custom[email], maxSize[120]]");
    $('#txtMailConfirm').attr("data-validation-engine", "validate[required]");
    $('#txtPhonenumber1').attr("data-validation-engine", "validate[required, custom[halfwidthNumbers], phone, maxSize[5]]");
    $('#txtPhonenumber2').attr("data-validation-engine", "validate[required, custom[halfwidthNumbers], phone, maxSize[4]]");
    $('#txtPhonenumber3').attr("data-validation-engine", "validate[required, custom[halfwidthNumbers], phone, maxSize[4]]");
    $('#txtContact').attr("data-validation-engine", "validate[maxSize[30], custom2[prohibitionCharacter]]");
    $('#txtLoginid').attr("data-validation-engine", "validate[required, custom[halfwidthNumbers] , minSize[6], maxSize[6]]");
    $('#pwdPassword').attr("data-validation-engine", "validate[required, custom[halfwidthAlphanumeric], funcCall[checkRangeForSingleItem][8, 16]]");
    $('#pwdPasswordConfirm').attr("data-validation-engine", "validate[required]");
}

