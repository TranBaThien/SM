/* Error code for Required/必須 for select */
const REQUIRED1 = "E0001";
/* Error code for Required/必須 for input */
const REQUIRED = "E0002";
/* Error code for Half-width numbers/半角数字 */
const HALFWIDTH_NUMBERS = "E0003";
/* Error code for Half-width/半角Alphanumeric/英数字 */
const HALFWIDTH_ALPHANUMERIC = "E0005";
/* Error code for Full-width characters/全角文字 */
const FULLWIDTH_CHARACTERS = "E0006";
/* Error code for Full-width/全角Katakana/カタカナ */
const FULLWIDTH_KATAKANA = "E0007";
/* Error code for SpecialCharacters */
const SPECIALCHARACTERS = "E0015";
/* Error code for Mail address/アドレスAddress/アドレス */
const MAIL = "E0009";
/* Error code for minimum/最小*/
const MINIMUM = "E0013";
/* Error code for maximum/最大*/
const MAXIMUM = "E0012";
/* Error code for range for minimum/最小 and maximum/最大 */
const RANGED = "E0014";
/* Error code for Date */
const DATE = "E0008";

/*Error for exclusive check (report status)*/
const REPORT_STATUS1 = "E0118";
/*Exclusive check (dunning notification count)*/
const REPORT_STATUS2 = "E0119";

const CONFIRM_MESSAGE = "C0003";

const GIA0110_MAX_LENGTH_APPENDIX_NO = "20";
const GIA0110_MAX_LENGTH_DOCUMENT_NO = "20";
const GIA0110_MAX_LENGTH_NOTICE_DATE = "10";
const GIA0110_MAX_LENGTH_RECIPIENT_NAME_APARTNAME = "50";
const GIA0110_MAX_LENGTH_RECIPIENT_NAME_USER = "26";
const GIA0110_MAX_LENGTH_SENDER = "20";
const GIA0110_MAX_LENGTH_ADDRESS_1 = "31";
const GIA0110_MAX_LENGTH_ADDRESS_2 = "6";
const GIA0110_MAX_LENGTH_NOTIFICATION_TIME_LIMIT = "10";
const GIA0110_CONFIRM_MESSAGE = "督促通知登録";
const MAILING_CODE_1 = "管理組合理事長";
const MAILING_CODE_3 = "区分所有者";
const MAILING_CODE_4 = "";
const TEXTADDRESS_1_1 = "貴管理組合が管理する";
const TEXTADDRESS_1_2 =
  "条例第15条第６項に基づく認定を受けた貴殿が区分所有権を有する";
const TEXTADDRESS_1_3 = "貴殿が区分所有権を有する";
const TEXTADDRESS_1_4 = "";
const TEXTADDRESS_2_1 = "貴管理組合";
const TEXTADDRESS_2_2 = "貴殿";
const TEXTADDRESS_2_3 = "各区分所有者";
const TEXTADDRESS_2_4 = "";

const DATE_REGEX = "[0-9]{4}/(0[1-9]|1[0-2])/(0[1-9]|[1-2][0-9]|3[0-1])";

const GIA0110_LBL = {
  TXT_APPENDIX_NO: "様式名",
  TXT_DOCUMENT_NO: "文書番号",
  TXT_RECIPIENT_NAME_APARTMENT: "宛先(マンション名)",
  TXT_RECIPIENT_NAME_USER: "宛先(氏名等)",
  TXT_SENDER: "発信者名",
  TXT_ADDRESS1: "本文中宛先1",
  TXT_ADDRESS2: "本文中宛先2",
  TXA_CONTACT: "担当・連絡先",
  CAL_NOTICE_DATE: "通知年月日",
  CAL_NOTIFICATION_TIMELIMIT: "届出期限",
  RDO_MAILING_CODE: "通知書宛先",
  LST_EVIDENCE_NO: "根拠条文（項）"
};
/* Radio */
var radio1 = [0];

$(document).ready(function() {
	/* Set event tabIndex for radio */
    $("input[name='rdoMailingCode']").addClass("check-radio");
    $(".check-radio").parent().on( "keypress keydown click focusout", function(e) {
        checkRadioByClassId(this, e, radio1, GIA0110_LBL.RDO_MAILING_CODE, false);
        if($("input[name='rdoMailingCode']:last").is(":checked")){
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
        checkMailingCode();
    });

    /* Set event tabIndex for checkBox */
    $("input[name='chkTermsConditions']").addClass("checkbox-class");
    $(".checkbox-class").parent().on( "keypress", function(e) {
        $(this).children().spacePress(e);
        if (document.getElementById("chkTermsConditions").checked == true){
            $("#btnNewRegistration").prop('disabled', false);
        } else {
            $("#btnNewRegistration").prop('disabled', true);
        }
    });

  appendMaxLengthAttribute();

  $("#directReport")
    .html(getMessage("I0001", ["督促通知", "登録"]))
    .dialog({
      modal: true, // モーダル表示
      title: dialog.title, // タイトル
      open: function() {
        $(this)
          .closest(".ui-dialog")
          .find(".ui-dialog-titlebar-close")
          .hide();
        $(this)
          .closest(".ui-dialog")
          .find(".ui-icon ui-icon-closethick")
          .hide();
      },
      buttons: {
        // ボタン
        OK: function() {
          $(this).dialog("close");
          getAndShowReport();
          $("#nextScreen").attr('action', baseUrl + '/GJA0110/show');
          $("#nextScreen").submit();
          // window.open("/GJA0110/show", "_self" );
        }
      }
    });

  //Apply change whenever EvidenceNo changed
  $("#lstEvidenceNo").change(function() {
    var yourSelect = document.getElementById("lstEvidenceNo");
    $.ajax({
      type: "GET",
      contentType: "application/json; charset=utf-8",
      url: baseUrl + "/GIA0110/handleChange",
      data: {
        evidenceBar: $("#lblEvidenceBar").val(),
        evidenceNo: yourSelect.options[yourSelect.selectedIndex].index
      },
      dataType: "text",
      timeout: 100000,
      success: function(data) {
        var result = data.split(",");
        $("#lblPeriodEvidenceLabel").text(result[0]);
        $("#lblPeriodEvidenceInput").val(result[0]);
        $("#lblNotificationFormatNameLabel").text(result[1]);
        $("#lblNotificationFormatNameInput").val(result[1]);
      },
      error: function(e) {
      }
    });
  });

  //handle validate calNoticeDate onBlur
  $("#calNoticeDate").blur(function() {
    var _calNoticeDate = $("#calNoticeDate");
    var re = new RegExp(DATE_REGEX);

    if (!re.test(_calNoticeDate.val())) {
      var today = new Date();
      var date =
        today.getFullYear() +
        "/" +
        (today.getMonth() + 1) +
        "/" +
        today.getDate();

      _calNoticeDate.val(date);
    }
  });

  //handle validate calNotificationTimeLimit onBlur
  $("#calNotificationTimelimit").blur(function() {
    var _calNotificationTimelimit = $("#calNotificationTimelimit");
    var re = new RegExp(DATE_REGEX);

    if (!re.test(_calNotificationTimelimit.val())) {
      _calNotificationTimelimit.val("2020/09/30");
    }
  });

  //Remove disable and hide lblLastNoticeDate and LblLastDocumentNo

  var findKey = $('input[name^="lblSupervisedNoticeTypeCode"]')
    .val()
    .indexOf("1");

  if (findKey !== -1) {
    $("#lstEvidenceNo").removeClass("disabledItem");
    $("#lblLastNoticeDate").text("");
    $("#lblLastDocumentNo").text("");
  }

  //handle set value for relating field according to rdoMailing Code value
  $('input[name^="rdoMailingCode"]').change(function() {
	  console.log("0601");
    if ($('input[id="radio-4"]').is(":checked")) {
      $("#txtRecipientNameApartment").removeClass("disabledItem");
      $("#txtRecipientNameUser").removeClass("disabledItem");
      $("#txtRecipientNameUser").val(MAILING_CODE_4);
      $("#txtTextAdress1").val(TEXTADDRESS_1_4);
      $("#txtTextAdress2").val(TEXTADDRESS_2_4);
    } else {
      $("#txtRecipientNameApartment").addClass("disabledItem");
      $("#txtRecipientNameUser").addClass("disabledItem");

      if ($('input[id="radio-1"]').is(":checked")) {
        $("#txtRecipientNameUser").val(MAILING_CODE_1);
        $("#txtTextAdress1").val(TEXTADDRESS_1_1);
        $("#txtTextAdress2").val(TEXTADDRESS_2_1);
      } else if ($('input[id="radio-2"]').is(":checked")) {
        var recipient = $("#recipientNameInCaseAddressIsIndividualOwner").val();
        $("#txtRecipientNameUser").val(recipient);
        $("#txtTextAdress1").val(TEXTADDRESS_1_2);
        $("#txtTextAdress2").val(TEXTADDRESS_2_2);
      } else if ($('input[id="radio-3"]').is(":checked")) {
        $("#txtRecipientNameUser").val(MAILING_CODE_3);
        $("#txtTextAdress1").val(TEXTADDRESS_1_3);
        $("#txtTextAdress2").val(TEXTADDRESS_2_3);
      }
    }
  });

  // Event click button back previous screen
  $(".back-previous-screen").on("click", function() {
    $("#backToPreviousScreen").attr('action', baseUrl + '/GJA0120/show');
    $("input[name=apartmentId]").val(APARTMENT_ID);
    $("#backToPreviousScreen").submit();
  });

  // Initial focus
  $("#txtAppendixNo").focus();

  // Disable btnNewRegistration 登録
  const currentScreenId = "GIA0110";
  const screenId = "screenId";

  $("#chkTermsConditions").prop("checked", false);
  $("input[name='btnNewRegistration']").prop("disabled", true);
  if (window.sessionStorage) {
    sessionStorage.clear();
    sessionStorage.setItem(screenId, currentScreenId);
  }

  $("#chkTermsConditions").change(function() {
    if ($("#chkTermsConditions").is(":checked")) {
      $("input[name='btnNewRegistration']").prop("disabled", false);
    } else {
      $("input[name='btnNewRegistration']").prop("disabled", true);
    }
  });

  // Apply validationEngine
  appendValidationEngineAttribute();

  $("#calNotificationTimelimit").datetimepicker({
    timepicker: false,
    dateFormat: "yyyy/mm/dd"
  });

  $("#calNotificationTimelimit").on("change", function() {
    var dateValue = $(this).val();
    var dateLength = dateValue.length;
    if (dateLength > 10) {
      $(this).val(dateValue.substring(0, dateLength - 6));
    }
  });

  $("#calNoticeDate").datetimepicker({
    timepicker: false,
    dateFormat: "yyyy/mm/dd"
  });

  $("#calNoticeDateButton").on("click", function() {
    $("#calNoticeDate").focus();
  });

  $("#calNotificationTimelimitButton").on("click", function() {
    $("#calNotificationTimelimit").focus();
  });

  $("#calNoticeDate").on("change", function() {
    var dateValue = $(this).val();
    var dateLength = dateValue.length;
    if (dateLength > 10) {
      $(this).val(dateValue.substring(0, dateLength - 6));
    }
  });

  $("#popup-error1")
    .html(getMessage(REPORT_STATUS1, "排他チェック（届出状況）"))
    .dialog({
      modal: true, // モーダル表示
      title: dialog.title, // タイトル
      open: function() {
        $(this)
          .closest(".ui-dialog")
          .find(".ui-dialog-titlebar-close")
          .hide();
        $(this)
          .closest(".ui-dialog")
          .find(".ui-icon ui-icon-closethick")
          .hide();
      },
      buttons: {
        // ボタン
        OK: function() {
          $(this).dialog("close");
          $("#backToPreviousScreen").attr('action', baseUrl + '/GJA0120/show');
          $("input[name=apartmentId]").val(APARTMENT_ID);
          $("#backToPreviousScreen").submit();
        }
      }
    });
  
  $("#popup-error2")
  .html(getMessage(REPORT_STATUS2, "排他チェック（督促通知回数）"))
  .dialog({
    modal: true, // モーダル表示
    title: dialog.title, // タイトル
    open: function() {
      $(this)
        .closest(".ui-dialog")
        .find(".ui-dialog-titlebar-close")
        .hide();
      $(this)
        .closest(".ui-dialog")
        .find(".ui-icon ui-icon-closethick")
        .hide();
    },
    buttons: {
      // ボタン
      OK: function() {
        $(this).dialog("close");
        $("input[name=apartmentId]").val(APARTMENT_ID);
        $("#backToPreviousScreen").attr('action', baseUrl + '/GJA0120/show');
        $("#backToPreviousScreen").submit();
      }
    }
  });

  //Error message for validationEngine
  $(".user-register").validationEngine({
    // 以下のパラメータは任意
    promptPosition: "bottomLeft", //エラー文の表示位置
    showArrowOnRadioAndCheckbox: true, //エラー箇所の図示
    focusFirstField: true, //エラー時に一番文頭の入力フィールドにフォーカスさせるかどうか
    maxErrorsPerField: 1,
    scroll: false,
    onValidationComplete: function(form, status) {
        return status;
    },

    custom_error_messages: {
      "#txtAppendixNo": {
        required: {
          message: getMessage(REQUIRED, [GIA0110_LBL.TXT_APPENDIX_NO])
        },
        "custom2[prohibitionCharacter]": {
          message: getMessage(SPECIALCHARACTERS, [GIA0110_LBL.TXT_APPENDIX_NO])
        },
        maxSize: {
          message: getMessage(MAXIMUM, [
            GIA0110_LBL.TXT_APPENDIX_NO,
            GIA0110_MAX_LENGTH_APPENDIX_NO
          ])
        }
      },
      "#txtDocumentNo": {
        required: {
          message: getMessage(REQUIRED, [GIA0110_LBL.TXT_DOCUMENT_NO])
        },
        "custom2[prohibitionCharacter]": {
          message: getMessage(SPECIALCHARACTERS, [GIA0110_LBL.TXT_DOCUMENT_NO])
        },
        maxSize: {
          message: getMessage(MAXIMUM, [
            GIA0110_LBL.TXT_DOCUMENT_NO,
            GIA0110_MAX_LENGTH_DOCUMENT_NO
          ])
        }
      },
      "#txtRecipientNameApartment": {
        required: {
          message: getMessage(REQUIRED, [
            GIA0110_LBL.TXT_RECIPIENT_NAME_APARTMENT
          ])
        },
        "custom2[prohibitionCharacter]": {
          message: getMessage(SPECIALCHARACTERS, [
            GIA0110_LBL.TXT_RECIPIENT_NAME_APARTMENT
          ])
        },
        maxSize: {
          message: getMessage(MAXIMUM, [
            GIA0110_LBL.TXT_RECIPIENT_NAME_APARTMENT,
            GIA0110_MAX_LENGTH_RECIPIENT_NAME_APARTNAME
          ])
        }
      },
      "#txtRecipientNameUser": {
        required: {
          message: getMessage(REQUIRED, [GIA0110_LBL.TXT_RECIPIENT_NAME_USER])
        },
        "custom2[prohibitionCharacter]": {
          message: getMessage(SPECIALCHARACTERS, [
            GIA0110_LBL.TXT_RECIPIENT_NAME_USER
          ])
        },
        " maxSize": {
          message: getMessage(MAXIMUM, [
            GIA0110_LBL.TXT_RECIPIENT_NAME_USER,
            GIA0110_MAX_LENGTH_RECIPIENT_NAME_USER
          ])
        }
      },
      "#txtSender": {
        required: {
          message: getMessage(REQUIRED, [GIA0110_LBL.TXT_SENDER])
        },
        "custom2[prohibitionCharacter]": {
          message: getMessage(SPECIALCHARACTERS, [GIA0110_LBL.TXT_SENDER])
        },
        maxSize: {
          message: getMessage(MAXIMUM, [
            GIA0110_LBL.TXT_SENDER,
            GIA0110_MAX_LENGTH_SENDER
          ])
        }
      },
      "#txtTextAdress1": {
        required: {
          message: getMessage(REQUIRED, [GIA0110_LBL.TXT_ADDRESS1])
        },
        "custom2[prohibitionCharacter]": {
          message: getMessage(SPECIALCHARACTERS, [GIA0110_LBL.TXT_ADDRESS1])
        },
        maxSize: {
          message: getMessage(MAXIMUM, [
            GIA0110_LBL.TXT_ADDRESS1,
            GIA0110_MAX_LENGTH_ADDRESS_1
          ])
        }
      },
      "#txtTextAdress2": {
        required: {
          message: getMessage(REQUIRED, [GIA0110_LBL.TXT_ADDRESS2])
        },
        "custom2[prohibitionCharacter]": {
          message: getMessage(SPECIALCHARACTERS, [GIA0110_LBL.TXT_ADDRESS2])
        },
        maxSize: {
          message: getMessage(MAXIMUM, [
            GIA0110_LBL.TXT_ADDRESS2,
            GIA0110_MAX_LENGTH_ADDRESS_2
          ])
        }
      },
      "#txaContact": {
        required: {
          message: getMessage(REQUIRED, [GIA0110_LBL.TXA_CONTACT])
        },
        "custom2[prohibitionCharacter]": {
          message: getMessage(SPECIALCHARACTERS, [GIA0110_LBL.TXA_CONTACT])
        },
        maxSize: {
          message: getMessage(MAXIMUM, [
            GIA0110_LBL.TXA_CONTACT,
            $("#txaContactMaxLength").val()
          ])
        }
      },
      "#calNoticeDate": {
        required: {
          message: getMessage(REQUIRED, [GIA0110_LBL.CAL_NOTICE_DATE])
        },
        "custom[datetime]": {
          message: getMessage(DATE, [GIA0110_LBL.CAL_NOTICE_DATE])
        },
        "custom[halfwidthAlphanumeric]": {
          message: getMessage(HALFWIDTH_ALPHANUMERIC, [
            GIA0110_LBL.CAL_NOTICE_DATE
          ])
        },
        maxSize: {
          message: getMessage(MAXIMUM, [
            GIA0110_LBL.CAL_NOTICE_DATE,
            GIA0110_MAX_LENGTH_NOTICE_DATE
          ])
        }
      },
      "#calNotificationTimelimit": {
        required: {
          message: getMessage(REQUIRED, [
            GIA0110_LBL.CAL_NOTIFICATION_TIMELIMIT
          ])
        },
        "custom2[fullwidthCharacters]": {
          message: getMessage(FULLWIDTH_CHARACTERS, [
            GIA0110_LBL.CAL_NOTIFICATION_TIMELIMIT
          ])
        },
        "custom[datetime]": {
          message: getMessage(DATE, [GIA0110_LBL.CAL_NOTIFICATION_TIMELIMIT])
        },
        maxSize: {
          message: getMessage(MAXIMUM, [
            GIA0110_LBL.CAL_NOTIFICATION_TIMELIMIT,
            GIA0110_MAX_LENGTH_NOTIFICATION_TIME_LIMIT
          ])
        }
      },
      "#rdomailingcode": {
        required: {
          message: getMessage(REQUIRED1, [GIA0110_LBL.RDO_MAILING_CODE])
        }
      },
      "#radio-1": {
        "custom[required]": {
          message: getMessage(REQUIRED1, [GIA0110_LBL.RDO_MAILING_CODE])
        },
        minCheckbox: {
          message: getMessage(REQUIRED1, [GIA0110_LBL.RDO_MAILING_CODE])
        }
      },
      "#lstEvidenceNo": {
        required: {
          message: getMessage(REQUIRED1, [GIA0110_LBL.LST_EVIDENCE_NO])
        }
      }
    }
  });
  
  if(chkTermsConditionsVal != "" && chkTermsConditionsVal != null){
      $("#chkTermsConditions").prop("checked", true);
      $("#btnNewRegistration").prop('disabled', false);
  }else{
  	$("#btnNewRegistration").prop('disabled', true);
  }
});

(function($) {
  $.fn.validationEngineLanguage = function() {};
  $.validationEngineLanguage = {
    newLang: function() {
      $.validationEngineLanguage.allRules = {
        /* Check Required/必須   for input */
        required: {
          regex: "none",
          alertText: " "
        },
        /* Check Half-width numbers/半角数字  */
        halfwidthNumbers: {
          regex: patternRegex.halfwidthNumbers,
          alertText: getMessage("E0003", ["*"])
        },
        /* Check "Half-width/半角Alphanumeric/英数字"  */
        halfwidthAlphanumeric: {
          regex: patternRegex.halfwidthAlphanumeric,
          alertText: getMessage("E0005", ["*"])
        },
        /* Check Full-width characters/全角文字 */
        fullwidthCharacters: {
          regex: patternRegex.fullwidthCharacters,
          alertText: getMessage("E0006", ["*"])
        },
        /* Check Full-width/全角	Katakana/カタカナ */
        kana: {
          regex: patternRegex.kana,
          alertText: getMessage("E0007", ["*"])
        },
        maxSize: {
          regex: "none",
          alertText: "* ",
          alertText2: getMessage("E0012", ["*", "*"])
        },
        prohibitionCharacter: {
          regex: patternRegex.isNotSpecialCharacter,
          alertText: getMessage("E0015", "*")
        },
        datetime: {
          regex: patternRegex.isDateFormat,
          alertText: getMessage("E0008", "対応日時")
        }
      };
    }
  };
  $.validationEngineLanguage.newLang();
})(jQuery);

function disabledValidationEngine(_method) {
  if (_method.attr("data-validation-engine")) {
    // Prevent issue undefined
    _method.validationEngine("hide");
    _method.removeAttr("data-validation-engine");
  }
}

/**
 * Event append validation engine for radios
 */
function appendRadioAttr() {
  const _rdoMailingCode = $('input[name^="rdoMailingCode"]');

  if (_rdoMailingCode.is(":checked")) {
    disabledValidationEngine(_rdoMailingCode);
  } else {
    _rdoMailingCode.attr(
      "data-validation-engine",
      "validate[custom[required]]"
    );
  }
}

function appendMaxLengthAttribute() {
  const GIA0110_TXA_CONTACT_MAX_LENGTH = $("#txaContactMaxLength").val();

  $("#txtAppendixNo").attr("maxlength", GIA0110_MAX_LENGTH_APPENDIX_NO);
  $("#txtDocumentNo").attr("maxlength", GIA0110_MAX_LENGTH_DOCUMENT_NO);
  $("#calNoticeDate").attr("maxlength", GIA0110_MAX_LENGTH_NOTICE_DATE);
  $("#txtSender").attr("maxlength", GIA0110_MAX_LENGTH_SENDER);
  $("#txtRecipientNameApartment").attr(
    "maxlength",
    GIA0110_MAX_LENGTH_RECIPIENT_NAME_APARTNAME
  );
  $("#txtRecipientNameUser").attr(
    "maxlength",
    GIA0110_MAX_LENGTH_RECIPIENT_NAME_USER
  );
  $("#txtTextAdress1").attr("maxlength", GIA0110_MAX_LENGTH_ADDRESS_1);
  $("#txtTextAdress2").attr("maxlength", GIA0110_MAX_LENGTH_ADDRESS_2);
  $("#calNotificationTimelimit").attr(
    "maxlength",
    GIA0110_MAX_LENGTH_NOTIFICATION_TIME_LIMIT
  );
  $("#txaContact").attr("maxlength", GIA0110_TXA_CONTACT_MAX_LENGTH);
}

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

function appendValidationEngineAttribute() {
  const GIA0110_TXA_CONTACT_MAX_LENGTH = $("#txaContactMaxLength").val();
  // Append data-validation-engine
  $("#txtAppendixNo").attr(
    "data-validation-engine",
    "validate[required, maxSize[" +
      GIA0110_MAX_LENGTH_APPENDIX_NO +
      "]]," +
      " custom2[prohibitionCharacter]"
  );

  $("#txtDocumentNo").attr(
    "data-validation-engine",
    "validate[required, maxSize[" +
      GIA0110_MAX_LENGTH_DOCUMENT_NO +
      "]]" +
      ", custom2[prohibitionCharacter]"
  );

  $("#txtSender").attr(
    "data-validation-engine",
    "validate[required, maxSize[" +
      GIA0110_MAX_LENGTH_SENDER +
      "]]" +
      ", custom2[prohibitionCharacter]"
  );

  $("#txtTextAdress1").attr(
    "data-validation-engine",
    "validate[required, maxSize[" +
      GIA0110_MAX_LENGTH_ADDRESS_1 +
      "]]" +
      ", custom2[prohibitionCharacter]"
  );

  $("#txtTextAdress2").attr(
    "data-validation-engine",
    "validate[required, maxSize[" +
      GIA0110_MAX_LENGTH_ADDRESS_2 +
      "]]" +
      ", custom2[prohibitionCharacter]"
  );

  $("#txaContact").attr(
    "data-validation-engine",
    "validate[required, maxSize[" +
      GIA0110_TXA_CONTACT_MAX_LENGTH +
      "]]" +
      ", custom2[prohibitionCharacter]"
  );

  $("#txtRecipientNameApartment").attr(
    "data-validation-engine",
    "validate[required, maxSize[" +
      GIA0110_MAX_LENGTH_RECIPIENT_NAME_APARTNAME +
      "]]" +
      ", custom2[prohibitionCharacter]"
  );

  $("#txtRecipientNameUser").attr(
    "data-validation-engine",
    "validate[required, maxSize[" +
      GIA0110_MAX_LENGTH_RECIPIENT_NAME_USER +
      "]]" +
      ", custom2[prohibitionCharacter]"
  );

  $("#calNoticeDate").attr(
    "data-validation-engine",
    "validate[required, custom[halfwidthAlphanumeric], custom[datetime], maxSize[" +
      GIA0110_MAX_LENGTH_NOTICE_DATE +
      "]]"
  );
  $("#calNotificationTimelimit").attr(
    "data-validation-engine",
    "validate[required, custom2[fullwidthCharacters], custom[datetime], maxSize[" +
      GIA0110_MAX_LENGTH_NOTIFICATION_TIME_LIMIT +
      "]]"
  );
  $("#rdomailingcode").attr("data-validation-engine", "validate[minCheckbox]");
  $("#lstEvidenceNo").attr("data-validation-engine", "validate[required]");

  appendRadioAttr();

  $('input[name^="rdoMailingCode"]').bind("change", function() {
    // Event for radio
    appendRadioAttr();
  });
}

function getAndShowReport() {
	var parameter1 = $("input[name=supervisedNoticeNo]").val()
	$('input[name=supervisedNoticeNoForReport]').val(parameter1);
	window.open('', 'report','width=900,height=1000,scrollbars=yes');
	$('#submitFormReport').submit();
}

function checkMailingCode(){
    if ($('input[id="radio-4"]').is(":checked")) {
        $("#txtRecipientNameApartment").removeClass("disabledItem");
        $("#txtRecipientNameUser").removeClass("disabledItem");
        $("#txtRecipientNameUser").val(MAILING_CODE_4);
        $("#txtTextAdress1").val(TEXTADDRESS_1_4);
        $("#txtTextAdress2").val(TEXTADDRESS_2_4);
      } else {
        $("#txtRecipientNameApartment").addClass("disabledItem");
        $("#txtRecipientNameUser").addClass("disabledItem");

        if ($('input[id="radio-1"]').is(":checked")) {
          $("#txtRecipientNameUser").val(MAILING_CODE_1);
          $("#txtTextAdress1").val(TEXTADDRESS_1_1);
          $("#txtTextAdress2").val(TEXTADDRESS_2_1);
        } else if ($('input[id="radio-2"]').is(":checked")) {
          var recipient = $("#recipientNameInCaseAddressIsIndividualOwner").val();
          $("#txtRecipientNameUser").val(recipient);
          $("#txtTextAdress1").val(TEXTADDRESS_1_2);
          $("#txtTextAdress2").val(TEXTADDRESS_2_2);
        } else if ($('input[id="radio-3"]').is(":checked")) {
          $("#txtRecipientNameUser").val(MAILING_CODE_3);
          $("#txtTextAdress1").val(TEXTADDRESS_1_3);
          $("#txtTextAdress2").val(TEXTADDRESS_2_3);
        }
      }
}

function submitNewRegistration() {
    $("#div3")
        .html(getMessage(CONFIRM_MESSAGE, GIA0110_CONFIRM_MESSAGE))
        .dialog({
            modal: true, // モーダル表示
            title: dialog.title, // タイトル
            buttons: {
                // ボタン
                OK: function() {
                    const isValid = $(".user-register").validationEngine("validate");
                    if (isValid) {
                        $(".user-register").submit();
                    }
                    $(this).dialog("close");
                },
                キャンセル: function() {
                    $(this).dialog("close");
                    event.preventdefault();
                }
            }
        });
}

$("#helpPathID").click(function () {
    window.open(window.location.origin + linkAddress, "_blank", 'width=900,height=1000,scrollbars=yes');
});