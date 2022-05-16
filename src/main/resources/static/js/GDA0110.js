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

const LABEL_CALNOTIFICATION_DATE = '届出年月日';

const LABEL_CAL_NOTICE_DATE = '通知年月日';

const LABEL_CALBUILD_DATE = '新築年月日';

const LABEL_EMPTY_NUMBER = '空き住戸_【戸数】';

const LABEL_RENTAL_NUMBER = '賃貸化住戸_【戸数】';

const LABEL_APPENDIX_NO = "様式名";
const LABEL_DOCUMENT_NO = "文書番号";
const LABEL_SENDER = "発信者名";
const LABEL_EVIDENCE = "根拠条文";
const TXA_REMARKS = "備考";

const RDONOTIFICATIONMETHOD = "通知方法";

/* Radio */
var radio1 = [0];
var radio2 = [0];

function initBtnRegister() {
    const btnRegister = $("input[name='btnRegister']");
    if($('#cd1').is(":checked")) {
        btnRegister.removeClass("disabledItem");
    } else {
        btnRegister.addClass("disabledItem");
    }
}

function initBtnCorrection() {
    const btnCorrection = $("button[name='btnCorrection']");
    const isBtnCorrection = $("#btnCorrectionOn").val();
    if (isBtnCorrection === "true") {
        btnCorrection.click();
    }
}

function initCheckboxPolicy() {
    const policyCheckbox = $('#cd1');
    const isBtnCorrection = $("#chkConfirm").val();
    if (isBtnCorrection === "true") {
        policyCheckbox.prop("checked", true);
    } else {
        policyCheckbox.prop("checked", false);
    }
    initBtnRegister();
}

function goBackToMenu() {
    $('#message').html(getMessage("C0001"));
    $("#div3").dialog({
        modal:true,
        title:"Message",
        buttons: {
            "OK": function() {
                $(this).dialog("close");
                $("#goBackToMenu")[0].click();
            },
            "キャンセル": function() {
                $(this).dialog("close");
            }
        }
    });
}

function goBack() {
    $('#message').html(getMessage("C0001"));
    $("#div3").dialog({
        modal:true,
        title:"Message",
        buttons: {
            "OK": function() {
                $(this).dialog("close");
                document.getElementById("transitionsForm").action = contextPath + "/GJA0110/return";
                $("#transitionsForm").submit();
            },
            "キャンセル": function() {
                $(this).dialog("close");
            }
        }
    });
}

function submitRegisterForm(key, argument, isValidate) {
    $('#message').html(getMessage(key, argument));
    $("#div3").dialog({
        modal:true,
        title:"Message",
        buttons: {
            "OK": function() {
                let isValid = true;
                if (isValidate) {
                    isValid = $(".notification-acceptance-register").validationEngine('validate');
                } else {
                    $(".notification-acceptance-register").validationEngine('detach');
                }
                if (isValid) {
                    $("#form").submit();
                }
                $(this).dialog("close");
            },
            "キャンセル": function() {
                $(this).dialog("close");
            }
        },
        close: function() {
            $(".notification-acceptance-register").validationEngine('attach');
        }
    });
}

function submitAcceptanceForm() {
    document.getElementById("form").action = contextPath + "/GDA0110/acceptance";
    submitRegisterForm("C0003", ["届出受理登録"], true);
}

function saveTemporaryAcceptanceData() {
    document.getElementById("form").action = contextPath + "/GDA0110/temporarily/store";
    submitRegisterForm("C0003", ["職権訂正情報と受理通知内容情報と通知情報を一時保存"], true);
}

function restoreTemporaryAcceptanceData() {
    document.getElementById("form").action = contextPath + "/GDA0110/temporarily/restore";
    submitRegisterForm("C0002", [], false);
}

function updateLblNotificationDate() {
    const calNotificationDate = $("#datepicker-1").val();
    if (calNotificationDate) {
        $("#labelNotificationDate").text(calNotificationDate.substring(0, 10));
        $("input[name='lblNotificationDate']").val(calNotificationDate.substring(0, 10));
    }
}

function updateLblRecipientNameApartment() {
    const lblRecipientNameApartment = $("input[name='basicReportInfo.txtApartmentName']").val();
    $("#lblRecipientNameApartment").text(lblRecipientNameApartment);
    $("input[name='lblRecipientNameApartment']").val(lblRecipientNameApartment);

}

function updateLblRecipientNameUser() {
    const rdoContactProperty = $("input[name='infoAreaCommon.rdoContactProperty']:checked").val();
    if(rdoContactProperty){
        if (rdoContactProperty === '2') {
            const txtNotificationPersonName = $("input[name='infoAreaCommon.txtNotificationPersonName']").val();
            $("#labelRecipientNameUser").text('区分所有者　' + txtNotificationPersonName);
            $("input[name='lblRecipientNameUser']").val('区分所有者　' + txtNotificationPersonName);
        } else {
            $("#labelRecipientNameUser").text('管理組合理事長');
            $("input[name='lblRecipientNameUser']").val('管理組合理事長');
        }
    } else {
        $("#labelRecipientNameUser").text($("input[name='lblRecipientNameUser']").val());
    }
}

function updateLblEvidenceBar() {
    const rdoNotificationType = $("input[name='infoAreaCommon.rdoNotificationType']:checked").val();
    if (rdoNotificationType === '1') {
        $("#lblEvidenceBar").val(15);
        $("#lblEvidenceBar").text("15");
        $("#lblEvidenceBarSpan").text("15");
        $("#lstEvidence2_1").prop("disabled", false).show();
        $("#lstEvidence2_2").prop("disabled", true).hide();
    } else if (rdoNotificationType === '2') {
        $("#lblEvidenceBar").val(16);
        $("#lblEvidenceBar").text("16");
        $("#lblEvidenceBarSpan").text("16");
        $("#lstEvidence2_1").prop("disabled", true).hide();
        $("#lstEvidence2_2").prop("disabled", false).show();
    }
}

function updateRadio121() {
    const txtEmail = $("input[name='infoAreaCommon.txtContactMail']").val();
    if (txtEmail) {
        $("#radio-121").prop("disabled", false);
        $("#radio-121").parent().children().removeClass("disable-item");
    } else {
        $("#radio-121").prop("checked", false);
        $("#radio-121").prop("disabled", true);
        $("#radio-121").parent().children().addClass("disable-item");
    }
}

function showError() {
	if (errorExclusiveVal.length > 2) {
        $('#message').html(getMessage("E0112"));
        $("#div3").dialog({
            modal:true,
            title:"Message",
            buttons: {
                "OK": function() {
                    $(this).dialog("close");
                    document.getElementById("transitionsForm").action = contextPath + "/GJA0110/show";
                    $("#transitionsForm").submit();
                }
            }
        });
    }
}

function applyValidationEngine() {
    $(".notification-acceptance-register").validationEngine('attach',{
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
                    'message': getMessage(MINIMUM, [LABEL_MANAGER_ZIP_CODE, 3])
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
                    'message': getMessage(MINIMUM, [LABEL_MANAGER_ZIP_CODE, 4])
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
                },
                'custom[halfwidthAlphanumeric]': {
                    'message': getMessage(HALFWIDTH_ALPHANUMERIC, LABEL_CALNOTIFICATION_DATE)
                },
                'custom[date]': {
                    'message': getMessage(FORMAT_DATE, LABEL_CALNOTIFICATION_DATE)
                },
                'maxSize' : {
                    'message': getMessage(MAXIMUM, [LABEL_CALNOTIFICATION_DATE, 10])
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
            '#calBuildDate': {
                'custom[halfwidthAlphanumeric]': {
                    'message': getMessage(HALFWIDTH_ALPHANUMERIC, LABEL_CALBUILD_DATE)
                },
                'custom[date]': {
                    'message': getMessage(FORMAT_DATE, LABEL_CALBUILD_DATE)
                },
                'maxSize' : {
                    'message': getMessage(MAXIMUM, [LABEL_CALBUILD_DATE, 10])
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
            },
            '#radio-121': {
                'minCheckbox': {
                    'message': getMessage(REQUIRED_RADIO, '通知方法')
                }
            },
            '#calNoticeDate': {
                'required': {
                    'message': getMessage(REQUIRED, LABEL_CAL_NOTICE_DATE)
                }
            },
            '#txtAppendixNo': {
                'required': {
                    'message': getMessage(REQUIRED, LABEL_APPENDIX_NO)
                },
                'custom2[prohibitionCharacter]' : {
                    'message': getMessage(SPECIALCHARACTERS, LABEL_APPENDIX_NO)
                },
                'maxSize' : {
                    'message': getMessage(MAXIMUM, [LABEL_APPENDIX_NO, 20])
                }
            },
            '#txtDocumentNo': {
                'required': {
                    'message': getMessage(REQUIRED, LABEL_DOCUMENT_NO)
                },
                'custom2[prohibitionCharacter]' : {
                    'message': getMessage(SPECIALCHARACTERS, LABEL_DOCUMENT_NO)
                },
                'maxSize' : {
                    'message': getMessage(MAXIMUM, [LABEL_DOCUMENT_NO, 20])
                }
            },
            '#txtSender': {
                'required': {
                    'message': getMessage(REQUIRED, LABEL_SENDER)
                },
                'custom2[prohibitionCharacter]' : {
                    'message': getMessage(SPECIALCHARACTERS, LABEL_SENDER)
                },
                'maxSize' : {
                    'message': getMessage(MAXIMUM, [LABEL_SENDER, 25])
                }
            },
            '#lstEvidence2_2': {
                'required': {
                    'message': getMessage(REQUIRED_RADIO, LABEL_EVIDENCE)
                }
            },
            '#lstEvidence2_1': {
                'required': {
                    'message': getMessage(REQUIRED_RADIO, LABEL_EVIDENCE)
                }
            },
            '#txaRemarks': {
                'custom2[prohibitionCharacter]' : {
                    'message': getMessage(SPECIALCHARACTERS, TXA_REMARKS)
                },
            }
        },
        onValidationComplete: function(form, status){
            return status;
        }
    });
}

$(document).ready(function() {
	/* Append tab index */
	appendTapIndexGDA0110();
	/* Check event radio */
    $("input[name='rdoNotificationMethod']").addClass("check-radio100");
    $(".check-radio100").parent().on( "keypress keydown click focusout", function(e) {
        checkRadioByClassId(this, e, radio1, RDONOTIFICATIONMETHOD, true);
    });
	
	/* check spaceEvent for confirm checkbox */
	$("input[name='csample']").parent().addClass("checkbox-confirm");
    $(".checkbox-confirm").on( "keypress", function(e) {
    	$(this).children().spacePress(e);
        if ($("input[name='csample']:checked").length == 1){
            $("#btnRegister").prop('disabled', false);
            $("#btnRegister").removeClass("disabledItem");
        } else {
            $("#btnRegister").prop('disabled', true);
            $("#btnRegister").addClass("disabledItem");
        }
    });

	$("#calNoticeDate").datetimepicker({
        format: "Y/m/d",
        timepicker:false
	});
    onPostal();
    updateLblEvidenceBar();
    // disableCommonArea();
    showError();
    updateLblRecipientNameUser();

    $("#receiptNumber").attr("hidden", true);

    initCheckboxPolicy();
    // initBtnCorrection();

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

    if(isStoreTemporarilyComplete) {
        $('#message').html(getMessage("I0001", ["職権訂正情報と受理通知内容情報と通知情報、", "一時保存"]));
        $("#div3").dialog({
            modal:true,
            title:"Message",
            buttons: {
                "OK": function() {
                    $(this).dialog("close");
                }
            }
        });
    }

    if(isAcceptanceComplete) {
        $('#message').html(getMessage("I0001", ["届出受理、", "登録"]));
        $("#div3").dialog({
            modal:true,
            title:"Message",
            buttons: {
                "OK": function() {
                    $(this).dialog("close");
                    if (isSendEMail !== "true") {
                        const relatedNumber = $("input[name='relatedNumber']").val();
                        window.open(contextPath + "/GDA0110/" + relatedNumber + "/report", "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=100,left=100,width=1000,height=700");
                    }
                    document.getElementById("transitionsForm").action = contextPath + "/GJA0110/show";
                    $("#transitionsForm").submit();
                }
            }
        });
    }

    applyValidationEngine();
});

$("#datepicker-1").change(function () {
    updateLblNotificationDate();
});

$("input[name='basicReportInfo.txtApartmentName']").change(function () {
    updateLblRecipientNameApartment();
});

$("input[name='infoAreaCommon.rdoContactProperty']").change(function () {
    updateLblRecipientNameUser();
});

$("input[name='infoAreaCommon.rdoNotificationType']").change(function () {
    updateLblEvidenceBar();
});

function disableCommonArea() {
    for (let i = 2; i <=10 ; i ++) {
        $("#a" + i).addClass("disable-item");
    }
}

function enableCommonArea() {
    for (let i = 2; i <=10 ; i ++) {
        $("#a" + i).removeClass("disable-item");
    }
}

const btnCorrection = $("input[name='btnCorrection']");
btnCorrection.click(function () {
    if (btnCorrection.val() === "職権訂正をキャンセルする") {
        $('#message').html(getMessage("C0004", ["変更した内容", "取り消されます"]));
        $("#div3").dialog({
            modal:true,
            title:"Message",
            buttons: {
                "OK": function() {
                    $(this).dialog("close");
                    document.getElementById("transitionsForm").action = contextPath + "/GDA0110/show";
                    $("#transitionsForm").submit();
                },
                "キャンセル": function() {
                    $(this).dialog("close");
                }
            }
        });
    } else {
        $(".notification-acceptance-register").validationEngine('detach');
        document.getElementById("form").action = contextPath + "/GDA0110/active";
        $("#form").submit();
    }
});


$("input[name='infoAreaCommon.txtContactMail']").change(function () {
    updateRadio121();
});

$("#cd1").change(function () {
    if($('#cd1').is(":checked")) {
        $("#chkConfirm").val("true");
    } else {
        $("#chkConfirm").val("false");
    }
    initBtnRegister();
});

$('#calNoticeDateButton').on('click', function(){
    $('#calNoticeDate').focus();
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
            '#lblApartmentAddress1'  : '%3%4%5',//'%4',
        },
        url : {
            'http'  : jpostal_url.http,
            'https' : jpostal_url.https,
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
            'https' : jpostal_url.https,
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
            'https' : jpostal_url.https,
        }
    });
    // Trigger event to remove error message for lstApartmentAddress1 by zip code 自動入力
    $('#validApartmentZipCode').bind('blur', function() {
        $('#lstApartmentAddress1').trigger('change');
    });

    // Event remove validation for lstApartmentAddress1
    $("#lstApartmentAddress1").change(function () {
        if ($("#lstApartmentAddress1 option:selected").text() === "") {
            $('#lstApartmentAddress1').attr("data-validation-engine", "validate[custom[required]]");
            return true;
        } else {
            $('#lstApartmentAddress1').validationEngine('hide');
            $("#lstApartmentAddress1").removeAttr("data-validation-engine");
        }
    });
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
                /* Check Full-width/全角	Katakana/カタカナ */
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
 * Append tap index
 */
function appendTapIndexGDA0110(){
    $("#txaOrrectionDetails").attr("tabindex", "790");
    $("#btnTmpSave").attr("tabindex", "791");
    $("#btnRestore").attr("tabindex", "792");
    $("#txtAppendixNo").attr("tabindex", "800");
    $("#txtDocumentNo").attr("tabindex", "801");
    $("#calNoticeDate").attr("tabindex", "802");
    $("#txtSender").attr("tabindex", "803");
    $("#lstEvidence2_1").attr("tabindex", "804");
    $("#txaRemarks").attr("tabindex", "805");
    $("#radio-121").parent().attr("tabindex", "806");
    $("#radio-122").parent().attr("tabindex", "807");
    $("input[name='csample']").parent().attr("tabindex", function(index, attr) { return index + 808;});
    $("#btnRegister").attr("tabindex", "809");
    
}