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
const SPECIAL_CHARACTERS = 'E0015';
/* Error code for Mail address/アドレスAddress/アドレス */
const MAIL = 'E0009';
/* Error code for minimum/最小*/
const MINIMUM = 'E0013';
/* Error code for maximum/最大*/
const MAXIMUM = 'E0012';
/* Error code for range for minimum/最小 and maximum/最大 */
const RANGED = 'E0014';
/* Error code for date/日付*/
const DATE = 'E0008';
/* Message code for show dialog confirm*/
const CONFIRM = 'C0003';
const CONFIRM_RESTORE = 'C0002';
const CALNOTICEDATE = "通知年月日";
const GGA0110_REGISTRATION = "現地調査通知を登録";
const GGA0110_TEMPORARY_DATA = "現地調査通知内容を一時保存";
var submitType = '';
var form;
const TXTAPPENDIXNO = "様式名";
const TXTDOCUMENTNO = "文書番号";
const TXTRECIPIENTNAMEAPARTMENT = "宛先(マンション名)";
const TXTRECIPIENTNAMEUSER = "宛先(氏名等)";
const RDONOTICEDESTINATION = "通知書宛先";
const TXTSENDER = "発信者名";
const TXTTEXTADDRESS = "本文中宛先";
const TXTINVESTIMPLNUMBERPEOPLE = "調査を行う者";
const TXTNECESSARTDOCUMENT = "必要となる書類";
const TXACONTACT = "担当・連絡先";
const METHODREPORT = "通知方法";
const TEMP_SAVE_PARAM_ONE = "現地調査通知内容と通知情報、";
const TEMP_SAVE_PARAM_TWO = "一時保存、";
const REGISTER_SAVE_PARAM_ONE = "現地調査通知、";
const REGISTER_SAVE_PARAM_TWO = "登録";
const ID_MESSAGE_COMPLETE = "I0001";
const CALINVESTIMPLPLANTIME = "調査の実施予定日時";
const GGA0110_LENGTH_1 = '1';
const GGA0110_LENGTH_10 = '10';
const GGA0110_LENGTH_16 = '16';
const GGA0110_LENGTH_20 = '20';
const GGA0110_LENGTH_26 = '26';
const GGA0110_LENGTH_31 = '31';
const GGA0110_LENGTH_50 = '50';
const GGA0110_LENGTH_100 = '100';

/* Radio */
var radio1 = [0];
var radio2 = [0];

/**
 * Submit form when back button
 * @param apartmentId
 * @returns
 */
function turnBack(apartmentId) {
     $('#submitFormbutton').attr('action', baseUrl + '/GJA0120/show');
     $('input[name=applicationNo]').val("");
     $('input[name=apartmentId]').val(GG0110_GLOBAL_VAR.apartmentId);
     $('#submitFormbutton').submit();
}

function callPageSupport(linkAddress) {
    window.open( linkAddress, 'about:blank', 'width=900,height=1000,scrollbars=yes');
}

$(document).ready(function(){

/* Set event tabIndex for radio */
    $("input[name='rdoNoticeDestination']").addClass("check-radio");
    $(".check-radio").parent().on( "keypress keydown click focusout", function(e) {
        checkRadioByClassId(this, e, radio1, RDONOTICEDESTINATION, false);
        if($("input[name='rdoNoticeDestination']").is(":checked")){
            if($("input[name='rdoNoticeDestination']:last").is(":checked")){
                $("input[name='txtRecipientNameApartment']").removeClass('disabledItem');
                $("input[name='txtRecipientNameUser']").removeClass('disabledItem');
                $( "#txtRecipientNameApartment" ).prop( "readonly", false );
                $( "#txtRecipientNameUser" ).prop( "readonly", false );
            } else {
                $("input[name='txtRecipientNameApartment']").addClass('disabledItem');
                $("input[name='txtRecipientNameUser']").addClass('disabledItem');
                $( "#txtRecipientNameApartment" ).prop( "readonly", true );
                $( "#txtRecipientNameUser" ).prop( "readonly", true );
            }
        }
        checkNoticeDestination();
    });
    $("input[name='rdoNotificationMethod']").addClass("check-vertical-radio");
    $(".check-vertical-radio").parent().on( "keypress keydown click focusout", function(e) {
        checkRadioByClassId(this, e, radio2, METHODREPORT, true);
    });

/* Set event tabIndex for checkBox */
    $("input[name='checkBoxConfirm']").addClass("checkbox-class");
    $(".checkbox-class").parent().on( "keypress", function(e) {
        $(this).children().spacePress(e);
        var checkBox = document.getElementById("confirm");
        if (checkBox.checked == true){
            $("#registerSurvey").prop('disabled', false);
        } else {
            $("#registerSurvey").prop('disabled', true);
        }
    });

/* Set time format */
    $("#calNoticeDate").blur(function () {
        handleShowErrorDateTimePickerOnBlur($(this), CALNOTICEDATE, "yy/MM/dd", true);
    });

    $("#calInvestImplPlanTime").blur(function () {
        handleShowErrorDateTimePickerOnBlur($(this), CALINVESTIMPLPLANTIME, "yy/MM/dd HH:mm", false);
    });

    checkFormatDateTime();
/* Initialize display */
    $("#txtDocumentNo").focus();
    if( GG0110_GLOBAL_VAR.rdoNoticeDestination == null ||
        GG0110_GLOBAL_VAR.rdoNoticeDestination == '1' ||
        GG0110_GLOBAL_VAR.rdoNoticeDestination == '2' ||
        GG0110_GLOBAL_VAR.rdoNoticeDestination == '3'){
        $("#txtRecipientNameApartment").addClass("disabledItem");
        $("#txtRecipientNameApartment").prop( "readonly", true );
        $("#txtRecipientNameUser").addClass("disabledItem");
        $("#txtRecipientNameUser").prop( "readonly", true );
        $("#txtTextAdress").addClass("disabledItem");
        $("#txtTextAdress").prop( "readonly", true );
    }else{
        $("#txtRecipientNameApartment").removeClass("disabledItem");
        $("#txtRecipientNameApartment").prop( "readonly", false );
        $("#txtRecipientNameUser").removeClass("disabledItem");
        $("#txtRecipientNameUser").prop( "readonly", false );
        $("#txtTextAdress").removeClass("disabledItem");
        $("#txtTextAdress" ).prop( "readonly", false );
    }

    if (GG0110_GLOBAL_VAR.redirecToGJA0120 == 'true') {
        $("#dialog-register")
        .html(getMessage(ID_MESSAGE_COMPLETE, [REGISTER_SAVE_PARAM_ONE, REGISTER_SAVE_PARAM_TWO]))
        .dialog({
         modal:true, //モーダル表示
         title: dialog.title, //タイトル
         buttons: {
         "OK": function() {
                $(this).dialog("close"); 
                    if(GG0110_GLOBAL_VAR.callReport == 'true'){
                        /* call report RP040 */
                       $('input[name=inveseNo]').val(GG0110_GLOBAL_VAR.inveseNo);
                       window.open('', 'report','width=900,height=1000,scrollbars=yes');
                       $('#submitFormReport').submit();
                     }
                     //Move to screen GJA0110
                     $('#submitFormbutton').attr('action', baseUrl + '/GJA0110/show');
                     $('#submitFormbutton').submit();
                }
             }
        });
    }

    /* NO6  完了メッセージ */
    if(GG0110_GLOBAL_VAR.temporaryMessage == 'true'){
        $("#dialog-register")
        .html(getMessage(ID_MESSAGE_COMPLETE, [TEMP_SAVE_PARAM_ONE, TEMP_SAVE_PARAM_TWO]))
        .dialog({
         modal:true, //モーダル表示
         title: dialog.title, //タイトル
         buttons: {
         "OK": function() {
                $(this).dialog("close"); 
                }
             }
        });
    }

/* Event submit*/
    form = document.getElementsByName('GGA0110form');
    $('#registerSurvey').on('click', function(){
             $("#dialog-register")
             .html(getMessage(CONFIRM, GGA0110_REGISTRATION))
             .dialog({
            modal:true, //モーダル表示
            title: dialog.title, //タイトル
            width: 305,
            buttons: {
            "OK": function() {
                $(this).dialog("close");
                $( ".formError" ).remove();
                //Validation before submit form
                var isValid = $(".advice-register").validationEngine('validate');
                //If valid
                if (isValid) {
                    $(form).attr('action', baseUrl + '/GGA0110/save');
                    registrationGGA0110();
                }
                },
            "キャンセル": function() {
                $(this).dialog("close"); 
                }
            }
        });
    });
    
    $('#temporarySave').on('click', function(){
        $("#dialog-register")
             .html(getMessage(CONFIRM, GGA0110_TEMPORARY_DATA))
             .dialog({
            modal:true, //モーダル表示
            title: dialog.title, //タイトル
            width: 305,
            buttons: {
            "OK": function() {
                $(this).dialog("close");
                $( ".formError" ).remove();
                //Validation before submit form
                var isValid = $(".advice-register").validationEngine('validate');
                //If valid
                if (isValid) {
                    $(form).attr('action', baseUrl + '/GGA0110/TemporarySave');
                    registrationGGA0110();
                    }
                },
            "キャンセル": function() {
                $(this).dialog("close"); 
                }
            }
        });
    });
    
    $('#ReStore').click(function() {
         $("#dialog-register")
          .html(getMessage(CONFIRM_RESTORE))
          .dialog({
            modal:true, //モーダル表示
            title: dialog.title, //タイトル
            width: 305,
            buttons: {
            "OK": function() {
                $(form).attr('action', baseUrl + '/GGA0110/ReStore');
                form[0].submit();
                },
            "キャンセル": function() {
                $(this).dialog("close"); 
                }
            }
        });
     });

/* Event check */
    /* txtRecipientNameApartment 宛先(マンション名) */
    /* txtRecipientNameUser 宛先(氏名等) */
    $('input[name="rdoNoticeDestination"]').click(function() {
        var rdoNoticeDestination4 = document.getElementById("rdoNoticeDestination-4");
        $("#txtRecipientNameApartment").addClass("disabledItem");
        $("#txtRecipientNameUser").addClass("disabledItem");
        $( "#txtRecipientNameApartment" ).prop( "readonly", true );
        $( "#txtRecipientNameUser" ).prop( "readonly", true );
        if (rdoNoticeDestination4.checked){
            $("#txtRecipientNameApartment").removeClass("disabledItem");
            $("#txtRecipientNameUser").removeClass("disabledItem");
           $( "#txtRecipientNameApartment" ).prop( "readonly", false );
           $( "#txtRecipientNameUser" ).prop( "readonly", false );
        }
    });
    /*rdoNotificationMethod 通知方法*/
    if(GG0110_GLOBAL_VAR.mailAddress == null ||GG0110_GLOBAL_VAR.mailAddress == ''){
        document.getElementById("rdoNotificationMethod-1").disabled = true;
        $("#rdoNotificationMethod-1").parent().addClass("disabledRadioCustom");
    }
    /* rdoMailingCode 通知書宛先 */
    $('input[name="rdoNoticeDestination"]').click(function() {
        checkNoticeDestination();
    });

    /* btnRestore 復元 */
    if (GG0110_GLOBAL_VAR.isDataStore == "false") {
        $("#ReStore").addClass("disabledItem");
    } else {
        $("#ReStore").removeClass("disabledItem");
    }

    /* btnRegist 登録 */
    if(GG0110_GLOBAL_VAR.checkBoxConfirm != "" && GG0110_GLOBAL_VAR.checkBoxConfirm != null){
        $("#confirm").prop("checked", true);
        $("#registerSurvey").prop('disabled', false);
    }else{
        $("#registerSurvey").prop('disabled', true);
    }
    $('#confirm').click(function() {
        var checkBox = document.getElementById("confirm");
        if (checkBox.checked == true){
            $("#registerSurvey").prop('disabled', false);
        } else {
            $("#registerSurvey").prop('disabled', true);
        }
    });

/* Check single Item */
    // Apply validationEngine
    appendValidationEngineAttribute();
    // Apply max length
    appendMaxLengthAttribute();
    validationCustom();
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
                    "alertText": " ",
                },
                "halfwidthAlphanumeric": {
                    "regex": patternRegex.halfwidthAlphanumeric,
                    "alertText": " ",
                },
                "halfwidthNumbers" : {
                   "regex":  patternRegex.halfwidthNumbers,
                   "alertText": " ",
                },
                "prohibitionCharacter": {
                     "regex":  patternRegex.isNotSpecialCharacter,
                     "alertText": " ",
                },
                "maxSize": {
                    "regex": "none",
                    "alertText": " ",
                },
                "minCheckbox": {
                   "regex": "none",
                   "alertText": " ",
                },
                "date": {
                    "regex": patternRegex.isDateFormat,
                    "alertText": " ",
                },
            };
        }
    };
    $.validationEngineLanguage.newLang();
})(jQuery);

function checkNoticeDestination(){
    var rdoNoticeDestination1 = document.getElementById("rdoNoticeDestination-1");
    var rdoNoticeDestination2 = document.getElementById("rdoNoticeDestination-2");
    var rdoNoticeDestination3 = document.getElementById("rdoNoticeDestination-3");
    var rdoNoticeDestination4 = document.getElementById("rdoNoticeDestination-4");
    if (rdoNoticeDestination1.checked){
        $("#txtRecipientNameUser").val("管理組合理事長")
        $("#txtTextAdress").val("貴管理組合が管理する")
    }else if (rdoNoticeDestination2.checked){
        $("#txtRecipientNameUser").val("区分所有者" + (GG0110_GLOBAL_VAR.notificationPersonName == null ? "" : "　" + GG0110_GLOBAL_VAR.notificationPersonName))
        $("#txtTextAdress").val("条例第15条第６項に基づく認定を受けた貴殿が区分所有権を有する")
    }else  if (rdoNoticeDestination3.checked){
       $("#txtRecipientNameUser").val("区分所有者")
       $("#txtTextAdress").val("貴殿が区分所有権を有する")
    }else  if (rdoNoticeDestination4.checked){
       $("#txtRecipientNameUser").val("")
       $("#txtTextAdress").val("")
       $("input[name='txtTextAdress']").removeClass('disabledItem');
       $( "#txtTextAdress" ).prop( "readonly", false );
    }
    if (!rdoNoticeDestination4.checked){
        $("#txtRecipientNameApartment").val(GG0110_GLOBAL_VAR.txtRecipientNameApartment);
        $("input[name='txtTextAdress']").addClass('disabledItem');
        $( "#txtTextAdress" ).prop( "readonly", true );
    }
}

function checkFormatDateTime(){
    //Check unfocus corespondDateTime input
    var dateRegEx = new RegExp(patternRegex.date);
    var dateTimeRegEx = new RegExp(patternRegex.dateTime);
    var halfSizeAlphaRegEx = new RegExp(patternRegex.halfwidthAlphanumeric);
    var currentDate = $.datepicker.formatDate('yy/MM/dd', new Date());
    var currentDateTime = $.datepicker.formatDate('yy/MM/dd HH:mm', new Date());

    $("#calNoticeDate").datetimepicker({
        format: "Y/m/d",
        timepicker:false
    });
    $('#button-datepicker-calNoticeDate').on('click', function(){
        $('#calNoticeDate').focus();
    });
    $("#calInvestImplPlanTime").datetimepicker({
        dateFormat: "Y/m/d"
    });
    $('#button-datepicker-calInvestImplPlanTime').on('click', function(){
        $('#calInvestImplPlanTime').focus();
    });
}

function appendValidationEngineAttribute() {
    // Append data-validation-engine
    $('input[name^="rdoNotificationMethod"]').attr("data-validation-engine", "validate[minCheckbox[1]");
    $('input[name^="rdoNoticeDestination"]').attr("data-validation-engine", "validate[minCheckbox[1]");

    $('#txtAppendixNo').attr("data-validation-engine", "validate[required, maxSize[20], custom2[prohibitionCharacter]]");
    $('#txtDocumentNo').attr("data-validation-engine", "validate[required, maxSize[20], custom2[prohibitionCharacter]]");
    $('#calNoticeDate').attr("data-validation-engine", "validate[required]");
    $('#rdoNoticeDestination').attr("data-validation-engine", "validate[required]");
    $('#txtRecipientNameApartment').attr("data-validation-engine", "validate[required, maxSize[50], custom2[prohibitionCharacter]]");
    $('#txtRecipientNameUser').attr("data-validation-engine", "validate[required, maxSize[26], custom2[prohibitionCharacter]]");
    $('#txtSender').attr("data-validation-engine",  "validate[required, maxSize[20], custom2[prohibitionCharacter]]");
    $('#txtTextAdress').attr("data-validation-engine", "validate[required, maxSize[31], custom2[prohibitionCharacter]]");
    $('#calInvestImplPlanTime').attr("data-validation-engine", "validate[required]");
    $('#txtInvestImplNumberPeople').attr("data-validation-engine", "validate[required, custom[halfwidthNumbers], maxSize[1]]");
    $('#txtNecessaryDocument').attr("data-validation-engine", "validate[required, maxSize[100], custom2[prohibitionCharacter]]");
    $('#txaContact').attr("data-validation-engine", "validate[required, maxSize[300], custom2[prohibitionCharacter]]");
    $('#rdoNotificationMethod').attr("data-validation-engine", "validate[required]");
}

function appendMaxLengthAttribute() {
     // Append max length attribute
    $('#txtAppendixNo').attr("maxlength", GGA0110_LENGTH_20);
    $('#txtDocumentNo').attr("maxlength", GGA0110_LENGTH_20);
    $('#calNoticeDate').attr("maxlength", GGA0110_LENGTH_10);
    $('#txtRecipientNameApartment').attr("maxlength", GGA0110_LENGTH_50);
    $('#txtRecipientNameUser').attr("maxlength", GGA0110_LENGTH_26);
    $('#txtSender').attr("maxlength", GGA0110_LENGTH_20);
    $('#txtTextAdress').attr("maxlength", GGA0110_LENGTH_31);
    $('#calInvestImplPlanTime').attr("maxlength", GGA0110_LENGTH_16);
    $('#txtInvestImplNumberPeople').attr("maxlength", GGA0110_LENGTH_1);
    $('#txtNecessaryDocument').attr("maxlength", GGA0110_LENGTH_100);
    $('#txaContact').attr("maxlength", GG0110_GLOBAL_VAR.contactMax);
}

function registrationGGA0110() {
    $('#GGA0110form').submit();
}

function validationCustom(){
    /* Jquery validation engine */
    $(".survay-register").validationEngine({
        promptPosition : "bottomLeft",
        showArrowOnRadioAndCheckbox : true,
        focusFirstField : true,
        maxErrorsPerField: 1,
        scroll : false,
        'custom_error_messages': {
            "#txtAppendixNo": {
                'required': {
                    'message': getMessage(REQUIRED, TXTAPPENDIXNO)
                },
                'custom2[prohibitionCharacter]' : {
                    'message': getMessage(SPECIAL_CHARACTERS, [TXTAPPENDIXNO])
                },
                'maxSize' : {
                    'message': getMessage(MAXIMUM, [TXTAPPENDIXNO, GGA0110_LENGTH_20])
                }
            },
            "#txtDocumentNo": {
                'required': {
                    'message': getMessage(REQUIRED, TXTDOCUMENTNO)
                },
                'custom2[prohibitionCharacter]' : {
                    'message': getMessage(SPECIAL_CHARACTERS, [TXTDOCUMENTNO])
                },
                'maxSize' : {
                    'message': getMessage(MAXIMUM, [TXTDOCUMENTNO, GGA0110_LENGTH_20])
                }
            },
            "#calNoticeDate": {
                'required': {
                    'message': getMessage(REQUIRED, CALNOTICEDATE)
                }
            },
            '#rdoNoticeDestination-1': {
                'minCheckbox': {
                    'message': getMessage(REQUIRED1, [RDONOTICEDESTINATION])
                 }
            },
            "#txtRecipientNameApartment": {
                'required': {
                    'message': getMessage(REQUIRED, TXTRECIPIENTNAMEAPARTMENT)
                },
                'custom2[prohibitionCharacter]' : {
                    'message': getMessage(SPECIAL_CHARACTERS, [TXTRECIPIENTNAMEAPARTMENT])
                },
                'maxSize' : {
                    'message': getMessage(MAXIMUM, [TXTRECIPIENTNAMEAPARTMENT, GGA0110_LENGTH_50])
                }
            },
            "#txtRecipientNameUser": {
                'required': {
                    'message': getMessage(REQUIRED, TXTRECIPIENTNAMEUSER)
                },
                'custom2[prohibitionCharacter]' : {
                    'message': getMessage(SPECIAL_CHARACTERS, [TXTRECIPIENTNAMEUSER])
                },
                'maxSize' : {
                    'message': getMessage(MAXIMUM, [TXTRECIPIENTNAMEUSER, GGA0110_LENGTH_26])
                }
            },
            "#txtSender": {
                'required': {
                    'message': getMessage(REQUIRED, TXTSENDER)
                },
                'custom2[prohibitionCharacter]' : {
                    'message': getMessage(SPECIAL_CHARACTERS, [TXTSENDER])
                },
                'maxSize' : {
                    'message': getMessage(MAXIMUM, [TXTSENDER, GGA0110_LENGTH_20])
                }
            },
            "#txtTextAdress": {
                'required': {
                    'message': getMessage(REQUIRED, TXTTEXTADDRESS)
                },
                'custom2[prohibitionCharacter]' : {
                    'message': getMessage(SPECIAL_CHARACTERS, [TXTTEXTADDRESS])
                },
                'maxSize' : {
                    'message': getMessage(MAXIMUM, [TXTTEXTADDRESS, GGA0110_LENGTH_31])
                }
            },
            "#calInvestImplPlanTime": {
                'required': {
                    'message': getMessage(REQUIRED, CALINVESTIMPLPLANTIME)
                }
            },
            "#txtInvestImplNumberPeople": {
                'required': {
                    'message': getMessage(REQUIRED, TXTINVESTIMPLNUMBERPEOPLE)
                },
                'custom[halfwidthNumbers]' : {
                    'message': getMessage(HALFWIDTH_NUMBERS, [TXTINVESTIMPLNUMBERPEOPLE])
                },
                'maxSize' : {
                    'message': getMessage(MAXIMUM, [TXTINVESTIMPLNUMBERPEOPLE, GGA0110_LENGTH_1])
                }
            },
            "#txtNecessaryDocument": {
                'required': {
                    'message': getMessage(REQUIRED, TXTNECESSARTDOCUMENT)
                },
                'custom2[prohibitionCharacter]' : {
                    'message': getMessage(SPECIAL_CHARACTERS, [TXTNECESSARTDOCUMENT])
                },
                'maxSize' : {
                    'message': getMessage(MAXIMUM, [TXTNECESSARTDOCUMENT, GGA0110_LENGTH_100])
                }
            },
            "#txaContact": {
                'required': {
                    'message': getMessage(REQUIRED, TXACONTACT)
                },
                'custom2[prohibitionCharacter]' : {
                    'message': getMessage(SPECIAL_CHARACTERS, [TXACONTACT])
                },
                'maxSize' : {
                    'message': getMessage(MAXIMUM, [TXACONTACT, GG0110_GLOBAL_VAR.contactMax])
                }
            },
            '#rdoNotificationMethod-1': {
                'minCheckbox': {
                    'message': getMessage(REQUIRED1, [METHODREPORT])
                 }
            },
        },
        onValidationComplete: function(form, status){
            return status;
        }
    });
}

function removeAllShowFormError(form){
    var elements = form.find('.formError');
    while(elements.length > 0){
        elements[0].parentNode.removeChild(elements[0]);
    }
}

$("#helpPathID").click(function () {
    window.open(window.location.origin + GG0110_GLOBAL_VAR.linkAddress, "_blank", 'width=900,height=1000,scrollbars=yes');
});