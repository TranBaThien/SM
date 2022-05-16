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

const CONFIRM_MESSAGE = 'E0113';


const DATE = 'E0008';


const FILE_NAME_CSV_SUPERVISE = "督促通知情報ファイル_";
const FILE_NAME_CSV_INFORMATION_SEARCH = "マンション検索結果一覧";

const GJA0110_LBL = {
    APARTMENT_ID: "マンションID",
    APARTMENT_NAME: "マンション名",
    APARTMENT_NAME_PHONETIC: "マンション名フリガナ",
    ADDRESS_2: "マンション住所2",
    RECEPT_NUM: "受付番号",
    NOTIFICATION_DATE_FROM: "届出日－範囲開始",
    NOTIFICATION_DATE_TO: "届出日－範囲終了",
    BUILD_DATE_TO: "新築年月日－範囲終了",
    BUILD_DATE_FROM: "新築年月日－範囲開始",
    HOUSE_NUMBER_FROM: "戸数－範囲開始",
    HOUSE_NUMBER_TO: "戸数－範囲終了",
    FLOOR_NUMBER_FROM: "階数－範囲開始",
    FLOOR_NUMBER_TO: "階数－範囲終了",

};

const IS_SEARCHING = "1";
const IS_PAGING = "2";
const IS_SORTING = "3";

appendValidationEngineAttribute();
$(document).ready(function () {
    const GJA0110_MAX_LENGTH_APARTMENT_ID = '10';
    const GJA0110_MAX_LENGTH_APARTMENT_NAME = '50';
    const GJA0110_MAX_LENGTH_APARTMENT_NAME_PHONETIC = '100';
    const GJA0110_MAX_LENGTH_APARTMENT_ADDRESS_2 = '100';
    const GJA0110_MAX_LENGTH_RECEPT_NUM = '10';
    const GJA0110_MAX_LENGTH_NOTIFICATION_DATE_FROM = '10';
    const GJA0110_MAX_LENGTH_NOTIFICATION_DATE_TO = '10';
    const GJA0110_MAX_LENGTH_BUILD_DATE_FROM = '10';
    const GJA0110_MAX_LENGTH_BUILD_DATE_TO = '10';
    const GJA0110_MAX_LENGTH_HOUSE_NUMBER_FROM = '6';
    const GJA0110_MAX_LENGTH_HOUSE_NUMBER_TO = '6';
    const GJA0110_MAX_LENGTH_FLOOR_NUMBER_FROM = '2';
    const GJA0110_MAX_LENGTH_FLOOR_NUMBER_TO = '2';

    $("input[name='rdoNotificationStatus']").addClass("check-radio1");
    $("input[name='rdoAcceptStatus']").addClass("check-radio2");
    $("input[name='rdoAdviceStatus']").addClass("check-radio3");
    $("input[name='rdoSuperviseStatus']").addClass("check-radio4");

    $(".check-radio1, .check-radio2, .check-radio3, .check-radio4").parent().on( "keypress", function(e) {
        $(this).children().spacePressRadio(e);
    });


    $("#calNotificationDateFrom").blur(function () {
        handleShowErrorDateTimePickerOnBlur($(this), GJA0110_LBL.NOTIFICATION_DATE_FROM);
    });

    $('#calNotificationDateTo').blur(function () {
        handleShowErrorDateTimePickerOnBlur($(this), GJA0110_LBL.NOTIFICATION_DATE_TO);
    });

    $('#calBuiltDateFrom').blur(function () {
        handleShowErrorDateTimePickerOnBlur($(this), GJA0110_LBL.BUILD_DATE_FROM);
    });

    $('#calBuiltDateTo').blur(function () {
        handleShowErrorDateTimePickerOnBlur($(this), GJA0110_LBL.BUILD_DATE_TO);
    });


    $("#calNotificationDateTo").datetimepicker({
        format: 'Y/m/d',
        timepicker: false
    });


    $("#calBuiltDateTo").datetimepicker({
        format: 'Y/m/d',
        timepicker: false
    });


    $("#calNotificationDateFrom").datetimepicker({
        format: 'Y/m/d',
        timepicker: false
    });
    $("#calBuiltDateFrom").datetimepicker({
        format: 'Y/m/d',
        timepicker: false
    });

    $("#calNotificationDateToButton").on('click', function () {
        $("#calNotificationDateTo").focus();
    });

    $('#calNotificationDateFromButton').on('click', function () {
        $('#calNotificationDateFrom').focus();
    });

    $("#calBuiltDateFromButton").on('click', function () {
        $("#calBuiltDateFrom").focus();
    });

    $("#calBuiltDateToButton").on('click', function () {
        $("#calBuiltDateTo").focus();
    });

    // Initial focus
    $('#txtAparmentId').focus();

    // auto fill , character on load screen
    autoFillCommand();

    $("#clearView").on("click", function (e) {
        e.preventDefault();
        $.ajax({
            type: "POST",
            url: baseUrl + '/GJA0110/resetSearchCondition',
            headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
            timeout: 600000,
            success: function (data) {
                if (data && data.searchInformationMansion) {
                    var oldConditionSearch = data.searchInformationMansion;
                    $('input[name=txtApartmentId]').val(oldConditionSearch.txtApartmentId);
                    $('input[name=txtApartmentName]').val(oldConditionSearch.txtApartmentName);
                    $('input[name=txtApartmentNamePhonetic]').val(oldConditionSearch.txtApartmentNamePhonetic);
                    $('input[name=txtApartmentAddress2]').val(oldConditionSearch.txtApartmentAddress2);
                    $('input[name=txtReceptNum]').val(oldConditionSearch.txtReceptNum);
                    $('input[name=calNotificationDateFrom]').val(oldConditionSearch.calNotificationDateFrom);
                    $('input[name=calNotificationDateTo]').val(oldConditionSearch.calNotificationDateTo);
                    $('input[name=calBuiltDateTo]').val(oldConditionSearch.calBuiltDateTo);
                    $('input[name=calBuiltDateFrom]').val(oldConditionSearch.calBuiltDateFrom);
                    $('input[name=txtHouseNumberFrom]').val(oldConditionSearch.txtHouseNumberFrom);
                    $('input[name=txtHouseNumberTo]').val(oldConditionSearch.txtHouseNumberTo);
                    $('input[name=txtFloorNumberTo]').val(oldConditionSearch.txtFloorNumberTo);
                    $('input[name=txtFloorNumberFrom]').val(oldConditionSearch.txtFloorNumberFrom);

                    $('select[name=txtCityCode]').val(oldConditionSearch.txtCityCode);
                    $('input[name=rdoNotificationStatus][value=' + oldConditionSearch.rdoNotificationStatus + ']').prop('checked', true);
                    $('input[name=rdoAcceptStatus][value=' + oldConditionSearch.rdoAcceptStatus + ']').prop('checked', true);
                    $('input[name=rdoAdviceStatus][value=' + oldConditionSearch.rdoAdviceStatus + ']').prop('checked', true);
                    $('input[name=rdoSuperviseStatus][value=' + oldConditionSearch.rdoSuperviseStatus + ']').prop('checked', true);
                    $('input[name=chkApartmentLost]').prop('checked', oldConditionSearch.chkApartmentLost);
                }
            }
        });
        return false;
    });

    $(".user-register").validationEngine({
        // 以下のパラメータは任意
        promptPosition: "bottomLeft",//エラー文の表示位置
        showArrowOnRadioAndCheckbox: true,//エラー箇所の図示
        focusFirstField: true,//エラー時に一番文頭の入力フィールドにフォーカスさせるかどうか
        scroll: false,
        maxErrorsPerField: 1,
        'custom_error_messages': {
            '#txtApartmentId': {
                'custom[halfwidthAlphanumeric]': {

                    'message': getMessage(HALFWIDTH_ALPHANUMERIC, [GJA0110_LBL.APARTMENT_ID])
                },
                maxSize: {
                    'message': getMessage(MAXIMUM, [GJA0110_LBL.APARTMENT_ID, GJA0110_MAX_LENGTH_APARTMENT_ID])
                },
            },
            '#txtApartmentName': {

                'custom2[prohibitionCharacter]': {
                    'message': getMessage(SPECIALCHARACTERS, [GJA0110_LBL.APARTMENT_NAME])
                },
                'maxSize': {
                    'message': getMessage(MAXIMUM, [GJA0110_LBL.APARTMENT_NAME, GJA0110_MAX_LENGTH_APARTMENT_NAME])
                },
            },
            '#txtApartmentNamePhonetic': {
                'custom[kana]': {
                    'message': getMessage(FULLWIDTH_KATAKANA, [GJA0110_LBL.APARTMENT_NAME_PHONETIC])
                },
                'maxSize': {
                    'message': getMessage(MAXIMUM, [GJA0110_LBL.APARTMENT_NAME_PHONETIC, GJA0110_MAX_LENGTH_APARTMENT_NAME_PHONETIC])
                },
            },
            '#txtApartmentAddress2': {
                'custom2[prohibitionCharacter]': {
                    'message': getMessage(SPECIALCHARACTERS, [GJA0110_LBL.ADDRESS_2])
                },

                'maxSize': {
                    'message': getMessage(MAXIMUM, [GJA0110_LBL.ADDRESS_2, GJA0110_MAX_LENGTH_APARTMENT_ADDRESS_2])
                },
            },
            '#txtReceptNum': {
                'custom[halfwidthAlphanumeric]': {
                    'message': getMessage(HALFWIDTH_ALPHANUMERIC, [GJA0110_LBL.RECEPT_NUM])
                },
                'maxSize': {
                    'message': getMessage(MAXIMUM, [GJA0110_LBL.RECEPT_NUM, GJA0110_MAX_LENGTH_RECEPT_NUM])
                },
            },
            '#calNotificationDateFrom': {
                'custom[halfwidthAlphanumeric]': {
                    'message': getMessage(HALFWIDTH_ALPHANUMERIC, [GJA0110_LBL.NOTIFICATION_DATE_FROM])
                },
                'maxSize': {
                    'message': getMessage(MAXIMUM, [GJA0110_LBL.NOTIFICATION_DATE_FROM, GJA0110_MAX_LENGTH_NOTIFICATION_DATE_FROM])
                },
            },
            '#calNotificationDateTo': {
                'custom[halfwidthAlphanumeric]': {
                    'message': getMessage(HALFWIDTH_ALPHANUMERIC, [GJA0110_LBL.NOTIFICATION_DATE_TO])
                },
                'custom[date]': {
                    'message': getMessage(DATE, [GJA0110_LBL.NOTIFICATION_DATE_TO])
                },
            },
            '#calBuiltDateFrom': {
                'custom[halfwidthAlphanumeric]': {
                    'message': getMessage(HALFWIDTH_ALPHANUMERIC, [GJA0110_LBL.BUILD_DATE_FROM])
                },

                'maxSize': {
                    'message': getMessage(MAXIMUM, [GJA0110_LBL.BUILD_DATE_FROM, GJA0110_MAX_LENGTH_BUILD_DATE_FROM])
                },
            },
            '#calBuiltDateTo': {
                'custom[halfwidthAlphanumeric]': {
                    'message': getMessage(HALFWIDTH_ALPHANUMERIC, [GJA0110_LBL.BUILD_DATE_TO])
                },
                'maxSize': {
                    'message': getMessage(MAXIMUM, [GJA0110_LBL.BUILD_DATE_TO, GJA0110_MAX_LENGTH_BUILD_DATE_TO])
                },
            },
            '#txtHouseNumberFromId': {


                // 'maxSize': {
                //     'message': getMessage(MAXIMUM, [GJA0110_LBL.HOUSE_NUMBER_FROM, GJA0110_MAX_LENGTH_HOUSE_NUMBER_FROM])
                // },
                // instead of maxSize to checkRangeForSingleItem
            },
            '#txtHouseNumberToId': {
                // 'maxSize': {
                //     'message': getMessage(MAXIMUM, [GJA0110_LBL.HOUSE_NUMBER_TO, GJA0110_MAX_LENGTH_HOUSE_NUMBER_TO])
                // },
                // instead of maxSize to checkRangeForSingleItem
            },
            '#txtFloorNumberFrom': {
                'maxSize': {
                    'message': getMessage(MAXIMUM, [GJA0110_LBL.FLOOR_NUMBER_FROM, GJA0110_MAX_LENGTH_FLOOR_NUMBER_FROM])
                },
                // instead of maxSize to checkRangeForSingleItem
            },
            '#txtFloorNumberTo': {
                'maxSize': {
                    'message': getMessage(MAXIMUM, [GJA0110_LBL.FLOOR_NUMBER_TO, GJA0110_MAX_LENGTH_FLOOR_NUMBER_TO])
                },
                // instead of maxSize to checkRangeForSingleItem
            },
        },
        onValidationComplete : function(form, status) {
            if (status) {
                // replace , character after validation complete
                autoReplaceCommand();
                form[0].submit();
            }
        }
    });
});


(function ($) {
    $.fn.validationEngineLanguage = function () {
    };
    $.validationEngineLanguage = {
        newLang: function () {
            $.validationEngineLanguage.allRules = {
                /* Check Required/必須   for input */
                "required": {
                    "regex": "none",
                    "alertText": ' ',
                },
                /* Check Half-width numbers/半角数字  */
                // TODO custom validationEngine
                "halfwidthNumbers": {
                    "regex": patternRegex.halfwidthNumbers,
                    "alertText": getMessage('E0003', ['*']),
                },
                /* Check "Half-width/半角Alphanumeric/英数字"  */
                // custom validationEngine
                "halfwidthAlphanumeric": {
                    "regex": patternRegex.halfwidthAlphanumeric,
                    "alertText": getMessage('E0005', ['*']),
                },
                /* Check Full-width characters/全角文字 */
                // TODO custom validationEngine
                "fullwidthCharacters": {
                    "regex": patternRegex.fullwidthCharacters,
                    "alertText": getMessage('E0006', ['*']),
                },
                /* Check Full-width/全角	Katakana/カタカナ */
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
                "date": {
                    "regex": patternRegex.date,
                    "alertText": getMessage('E0008', '対応日時')
                },
            };
        }
    };
    $.validationEngineLanguage.newLang();
})(jQuery);

function appendValidationEngineAttribute() {
    $('#txtHouseNumberFromId').attr("custom", GJA0110_LBL.HOUSE_NUMBER_FROM);
    $('#txtHouseNumberToId').attr("custom", GJA0110_LBL.HOUSE_NUMBER_TO);
    $('#txtFloorNumberFrom').attr("custom", GJA0110_LBL.FLOOR_NUMBER_FROM);
    $('#txtFloorNumberTo').attr("custom", GJA0110_LBL.FLOOR_NUMBER_TO);
    $('#txtApartmentId').attr("data-validation-engine", "validate[custom[halfwidthAlphanumeric], maxSize[10]]");
    $('#txtApartmentName').attr("data-validation-engine", "validate[custom2[prohibitionCharacter], maxSize[50]]");
    $('#txtApartmentNamePhonetic').attr("data-validation-engine", "validate[custom[kana], maxSize[100]]");
    $('#txtApartmentAddress2').attr("data-validation-engine", "validate[custom2[prohibitionCharacter], maxSize[100]]");
    $('#txtReceptNum').attr("data-validation-engine", "validate[custom[halfwidthAlphanumeric], maxSize[10]]");
    //Date
    $('#calNotificationDateFrom').attr("data-validation-engine", "validate[ maxSize[10], custom[date]");
    $('#calNotificationDateTo').attr("data-validation-engine", "validate[ maxSize[10],custom[date]]");
    $('#calBuiltDateFrom').attr("data-validation-engine", "validate[ maxSize[10], custom[date]]");
    $('#calBuiltDateTo').attr("data-validation-engine", "validate[ maxSize[10], custom[date]]");
    //Half Number
    $('#txtHouseNumberFromId').attr("data-validation-engine", "validate[funcCall[customHalfwidthNumbers], funcCall[customMaxSizeNumber][6]]");
    $('#txtHouseNumberToId').attr("data-validation-engine", "validate[funcCall[customHalfwidthNumbers], funcCall[customMaxSizeNumber][6]]");
    $('#txtFloorNumberFrom').attr("data-validation-engine", "validate[funcCall[customHalfwidthNumbers], maxSize[2]]");
    $('#txtFloorNumberTo').attr("data-validation-engine", "validate[funcCall[customHalfwidthNumbers], maxSize[2]]");
}

function clearValidationEngineAttribute() {
    disabledValidationEngine($('#txtHouseNumberFromId'));
    disabledValidationEngine($('#txtHouseNumberToId'));
    disabledValidationEngine($('#txtFloorNumberFrom'));
    disabledValidationEngine($('#txtFloorNumberTo'));
    disabledValidationEngine($('#txtApartmentId'));
    disabledValidationEngine($('#txtApartmentName'));
    disabledValidationEngine($('#txtApartmentNamePhonetic'));
    disabledValidationEngine($('#txtApartmentAddress2'));
    disabledValidationEngine($('#txtReceptNum'));
    //Date
    disabledValidationEngine($('#calNotificationDateFrom'));
    disabledValidationEngine($('#calNotificationDateTo'));
    disabledValidationEngine($('#calBuiltDateFrom'));
    disabledValidationEngine($('#calBuiltDateTo'));
    //Half Number
    disabledValidationEngine($('#txtHouseNumberFromId'));
    disabledValidationEngine($('#txtHouseNumberToId'));
    disabledValidationEngine($('#txtFloorNumberFrom'));
    disabledValidationEngine($('#txtFloorNumberTo'));
}



function sortSearchResult(item) {
    // prepare sort on js
    prepareSortOnJs();
    $('#searchInformationMansion').attr('action', baseUrl + '/GJA0110/search');
    $('#searchInformationMansion').submit();

}


$(".lnkApartmentName").on("click", function (e) {
    e.preventDefault();
    const parameter1 = $(this).attr('dataApartmentIdRef');
    $('input[name=apartmentId]').val(parameter1);
    $('#submitToGJA0120').submit();
    return false;
});


$(".GDA0110_button").on("click", function (e) {
    e.preventDefault();
    const parameter1 = $(e.target).prev('input[name="apartmentIdGDA"]').val();


    $.ajax({
        type: "POST",
        url: baseUrl + "/GJA0110/checkNewestNotification",
        headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
        data: {apartmentId: parameter1},
        success: function (data) {
            if (data == "checked") {
                $('#submitToGDA0110').attr('action', baseUrl + '/GDA0110/show');
                $('#submitToGDA0110 input[name=apartmentId]').val(parameter1);
                $('#submitToGDA0110').submit();

            } else {
                $("#div3")
                    .html(getMessage(CONFIRM_MESSAGE, CONFIRM_MESSAGE))
                    .dialog({
                        modal: true, // モーダル表示
                        title: dialog.title, //タイトル
                        buttons: { // ボタン
                            "OK": function () {
                                window.location.href = baseUrl + '/GBA0110';
                            },
                            "キャンセル": function () {
                                $(this).dialog("close");
                                event.preventdefault();
                            }
                        },
                    });
            }
        }

    });


    return false;
});

$(".btnGEA0110_button").on("click", function (e) {
    e.preventDefault();
    $('#submitToGEA0110').attr('action', baseUrl + '/GEA0110/show');
    const parameter1 = $(e.target).prev('input[name="apartmentIdGEA"]').val();
    $('#submitToGEA0110 input[name=apartmentId]').val(parameter1);
    $('#submitToGEA0110').submit();

    return false;
});

$(".submitToGCA0120_button").on("click", function (e) {
    e.preventDefault();
    const parameter1 = $(e.target).prev('input[name="apartmentIdGCA0120"]').val();
    var temp = $('input[name=applicationNo]').val();
    $('#submitToGCA0120_choose').attr('action', baseUrl + '/GCA0120/show');
    $("#submitToGCA0120_choose input[name=applicationNo]").val(temp);
    $("#submitToGCA0120_choose input[name=apartmentId]").val(parameter1);
    $('#submitToGCA0120_choose').submit();
    return false;
});

function autoFillCommand() {
    var x = document.getElementById("txtHouseNumberFromId");
    var y = document.getElementById("txtHouseNumberToId");
    if (x.value) x.value = x.value.replace(/[\s,]+/g, '').replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,');
    if (y.value) y.value = y.value.replace(/[\s,]+/g, '').replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,');
}

function autoReplaceCommand() {
    var x = document.getElementById("txtHouseNumberFromId");
    var y = document.getElementById("txtHouseNumberToId");
    if (x.value) x.value = x.value.replace(/,/g, "");
    if (y.value) y.value = y.value.replace(/,/g, "");
}

function customHalfwidthNumbers(field, rules, i, options) {
    var label = field.attr("custom");
    var ex = patternRegex.halfwidthNumbers;
    var pattern = new RegExp(ex);
    var value = field.val().replace(/[\s,]+/g, '');
    if (!pattern.test(value)) {
        return getMessage(HALFWIDTH_NUMBERS, label);
    } else {
        // Do nothing.
    }
}

function customMaxSizeNumber(field, rules, i, options) {
    var label = field.attr("custom");
    var max = rules[i + 3];
    var value = field.val().replace(/[\s,]+/g, '');
    var len = value.length;
    if (len > max) {
        return getMessage(MAXIMUM, [label, max]);
    } else {
        // Do nothing.
    }
}

// Event click button back previous screen
$('.back-previous-screen').on('click', function (e) {
	var previousScreen = $(this).data('previous-screen');
    if (previousScreen === 'GBA0110') {
        window.location.href = baseUrl + '/GBA0110';
    } else {
        e.preventDefault();
        var temp = $('input[name=applicationNo]').val();
        $('#submitToGCA0120').attr('action', baseUrl + '/GCA0120/show');
        $('input[name=applicationNo]').val(temp);
        $('#submitToGCA0120').submit();
    }
});

function exportToCsv(data, filename) {

    var blob = new Blob([data], {type: 'text/csv;charset=utf-8;'});
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

function handleShowErrorDateTimePickerOnBlur(selector, param) {
    const $this = selector;
    const val = $this.val();
    const dateTimeRegEx = new RegExp(patternRegex.isDateFormat);
    const halfSizeAlphaRegEx = new RegExp(patternRegex.halfwidthAlphanumeric);
    const currentDate = $.datepicker.formatDate('yy/mm/dd', new Date());

    //Check required date
    if (val == '' || val == undefined) {
        // remove custom error
        $this.removeError();
    } else if (!halfSizeAlphaRegEx.test(val)) {
        $this.val(currentDate);
        // custom show error
        $this.showError(getMessage(HALFWIDTH_ALPHANUMERIC, param));
    } else if (!dateTimeRegEx.test(val)) {
        $this.val(currentDate);
        // custom show error
        $this.showError(getMessage(DATE, param));
    }
    //Check validate format date
    if (dateTimeRegEx.test(val) && halfSizeAlphaRegEx.test(val)) {
        $this.validationEngine('hide');
        // remove custom error
        $this.removeError();
    }
}

function disabledValidationEngine(_method) {
    if (_method.attr("data-validation-engine")) {
        _method.validationEngine("hide");
        _method.removeAttr("data-validation-engine");
    }
}

function onSearching() {
    prepareSearchOnJs();
    $("#searchInformationMansion").submit();
}

function onPaging(page) {
    if (page && page !== '#') {
        preparePagingOnJs(page);
        $("#searchInformationMansion").submit();
    }
}

function prepareSortOnJs() {
    clearValidationEngineAttribute();
    $("input[name=action").val(IS_SORTING);
    $("input[name=page]").val(1);
}

function prepareSearchOnJs() {
    appendValidationEngineAttribute();
    $("input[name=action").val(IS_SEARCHING);
    $("input[name=page]").val(1);
}

function preparePagingOnJs(page) {
    clearValidationEngineAttribute();
    $("input[name=action").val(IS_PAGING);
    $("input[name=page]").val(page);
}

$("#exportcsv1").on("click", function (e) {
    const listApartmentIds = $("#exportcsv1").attr("data");
    $.ajax({
        type: "GET",
        url: baseUrl + "/GJA0110/csvInformation/",
        data: {"apartmentIds": listApartmentIds},
        success: function (data) {
            exportToCsv(data, FILE_NAME_CSV_INFORMATION_SEARCH + currentDateForCsv() + ".csv");
        }
    });
});

$("#csvSupervise").on("click", function (e) {
    const listApartmentIds = $("#csvSupervise").attr("data");
    $.ajax({
        type: "GET",
        url: baseUrl + "/GJA0110/csvSuperVise/",
        data: {"apartmentIds": listApartmentIds},
        success: function (data) {
            exportToCsv(data, FILE_NAME_CSV_SUPERVISE + currentDateForCsv() + ".csv");
        }
    });

});