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

const FILE_NAME_CSV = "集計結果一覧";

const GKA0110_LBL = {
        SUMMARY_ITEM: "集計項目",
        AGGREGATE_CREDIT: "集計単位",
        PERIOD_FROM: "集計期間開始",
        PERIOD_TO: "集計期間終了",
        NEW_BULIDING_FROM: "新築年月日開始",
        NEW_BULIDING_TO: "新築年月日終了",
        HOUSE_NUMBER_FROM: "建物戸数開始",
        HOUSE_NUMBER_TO: "建物戸数終了",
};

appendValidationEngineAttribute();
$(document).ready(function() {
    const GKA0110_MAX_LENGTH_PERIOD_FROM = '10';
    const GKA0110_MAX_LENGTH_PERIOD_TO = '10';
    const GKA0110_MAX_LENGTH_NEW_BULIDING_FROM = '10';
    const GKA0110_MAX_LENGTH_NEW_BULIDING_TO = '10';
    const GKA0110_MAX_LENGTH_HOUSE_NUMBER_FROM = '6';
    const GKA0110_MAX_LENGTH_HOUSE_NUMBER_TO = '6';

    var dateNow = new Date();
    var dateString = dateNow.getFullYear() + "/" + ("0"+(dateNow.getMonth()+1)).slice(-2) + "/" + ("0" + dateNow.getDate()).slice(-2);
    const oldConditionSearch = {
            'cldPeriodFrom': "2020/04/01",
            'cldPeriodTo': dateString,
            'cldNewBulidingFrom': "",
            'cldNewBulidingTo': "",
            'txtHouseNumberFrom': "",
            'txtHouseNumberTo': "",
            'txtCityCode': "",
            'rdoNotificationStatus': 3,
            'rdoAcceptanceStatusNew': 3,
            'rdoAcceptanceStatusChange': 3,
            'rdoSupportTarget': 3,
            'rdoGroup': 9,
            'rdoManager': 9,
            'rdoRule': 9,
            'rdoOneyearOver': 9,
            'rdoMinutes': 9,
            'rdoManagementCost': 9,
            'rdoRepairCost': 9,
            'rdoRepairPlan': 9,
            'chkInadequateManagement': "",
    };

    /**  limit the number of selected checkboxes */
    $('input[name=chkSummaryItem]').change(function(e) {
        if ($('input[name=chkSummaryItem]:checked').length > 7) {
             $(this).prop('checked', false);
             // alert("最大7件まで選択可能");
        }
     });

    $("#cldPeriodFrom").blur(function() {
        handleShowErrorDateTimePickerOnBlur($(this), GKA0110_LBL.PERIOD_FROM);
    });

    $('#cldPeriodTo').blur(function() {
        handleShowErrorDateTimePickerOnBlur($(this), GKA0110_LBL.PERIOD_TO);
    });

    $('#cldNewBulidingFrom').blur(function() {
        handleShowErrorDateTimePickerOnBlur($(this), GKA0110_LBL.NEW_BULIDING_FROM);
    });

    $('#cldNewBulidingTo').blur(function() {
        handleShowErrorDateTimePickerOnBlur($(this), GKA0110_LBL.NEW_BULIDING_TO);
    });

    $("#cldPeriodTo").datetimepicker({
        format: 'Y/m/d',
        timepicker: false
    });

    $("#cldNewBulidingTo").datetimepicker({
        format: 'Y/m/d',
        timepicker: false
    });

    $("#cldPeriodFrom").datetimepicker({
        format: 'Y/m/d',
        timepicker: false
    });

    $("#cldNewBulidingFrom").datetimepicker({
        format: 'Y/m/d',
        timepicker: false
    });

    $("#cldPeriodToButton").on('click', function() {
        $("#cldPeriodTo").focus();
    });

    $('#cldPeriodFromButton').on('click', function() {
        $('#cldPeriodFrom').focus();
    });

    $("#cldNewBulidingFromButton").on('click', function() {
        $("#cldNewBulidingFrom").focus();
    });

    $("#cldNewBulidingToButton").on('click', function() {
        $("#cldNewBulidingTo").focus();
    });

    // Initial focus
    $('#chkSummary1').focus();

    var houseNumberFrom =  $('#txtHouseNumberFromId');
    var houseNumberTo =  $('#txtHouseNumberToId');
    houseNumberFrom.val(houseNumberFrom.val().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,'));
    houseNumberTo.val(houseNumberTo.val().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,'));

    $("#btnAggregateClear").on("click", function() {
        $('input[name=chkSummaryItem]').prop("checked", false);
        $('input[name=rdoAggregateCredit]').prop("checked", false);
    });

    $("#btnSearchClear").on("click", function(e) {
        e.preventDefault();
        $('input[name=cldPeriodFrom]').val(oldConditionSearch.cldPeriodFrom);
        $('input[name=cldPeriodTo]').val(oldConditionSearch.cldPeriodTo);
        $('input[name=cldNewBulidingFrom]').val(oldConditionSearch.cldNewBulidingFrom);
        $('input[name=cldNewBulidingTo]').val(oldConditionSearch.cldNewBulidingTo);
        $('input[name=txtHouseNumberFrom]').val(oldConditionSearch.txtHouseNumberFrom);
        $('input[name=txtHouseNumberTo]').val(oldConditionSearch.txtHouseNumberTo);
        var userType = $('input[name=userType]').val();
        if (userType != 2) {
            $('select[name=cityCode]').val(oldConditionSearch.txtCityCode);
        }
        $('input[name=rdoNotificationStatus][value=' + oldConditionSearch.rdoNotificationStatus + ']').prop('checked', true);
        $('input[name=rdoAcceptanceStatusNew][value=' + oldConditionSearch.rdoAcceptanceStatusNew + ']').prop('checked', true);
        $('input[name=rdoAcceptanceStatusChange][value=' + oldConditionSearch.rdoAcceptanceStatusChange + ']').prop('checked', true);
        $('input[name=rdoSupportTarget][value=' + oldConditionSearch.rdoSupportTarget + ']').prop('checked', true);
        $('input[name=rdoGroup][value=' + oldConditionSearch.rdoGroup + ']').prop('checked', true);
        $('input[name=rdoManager][value=' + oldConditionSearch.rdoManager + ']').prop('checked', true);
        $('input[name=rdoRule][value=' + oldConditionSearch.rdoRule + ']').prop('checked', true);
        $('input[name=rdoOneyearOver][value=' + oldConditionSearch.rdoOneyearOver + ']').prop('checked', true);
        $('input[name=rdoMinutes][value=' + oldConditionSearch.rdoMinutes + ']').prop('checked', true);
        $('input[name=rdoManagementCost][value=' + oldConditionSearch.rdoManagementCost + ']').prop('checked', true);
        $('input[name=rdoRepairCost][value=' + oldConditionSearch.rdoRepairCost + ']').prop('checked', true);
        $('input[name=rdoRepairPlan][value=' + oldConditionSearch.rdoRepairPlan + ']').prop('checked', true);
        $('input[name=chkInadequateManagement]').prop('checked', oldConditionSearch.chkInadequateManagement === 'on');
 
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
            '#cldPeriodFrom': {
                'custom[halfwidthAlphanumeric]': {
                    'message': getMessage(HALFWIDTH_ALPHANUMERIC, [GKA0110_LBL.PERIOD_FROM])
                },
                'maxSize': {
                    'message': getMessage(MAXIMUM, [GKA0110_LBL.PERIOD_FROM, GKA0110_MAX_LENGTH_PERIOD_FROM])
                },
                'custom[date]': {
                    'message': getMessage(DATE, [GKA0110_LBL.PERIOD_FROM])
                },
            },
            '#cldPeriodTo': {
                'custom[halfwidthAlphanumeric]': {
                    'message': getMessage(HALFWIDTH_ALPHANUMERIC, [GKA0110_LBL.PERIOD_TO])
                },
                'maxSize': {
                    'message': getMessage(MAXIMUM, [GKA0110_LBL.PERIOD_TO, GKA0110_MAX_LENGTH_PERIOD_TO])
                },
                'custom[date]': {
                    'message': getMessage(DATE, [GKA0110_LBL.PERIOD_TO])
                },
            },
            '#cldNewBulidingFrom': {
                'custom[halfwidthAlphanumeric]': {
                    'message': getMessage(HALFWIDTH_ALPHANUMERIC, [GKA0110_LBL.NEW_BULIDING_FROM])
                },

                'maxSize': {
                    'message': getMessage(MAXIMUM, [GKA0110_LBL.NEW_BULIDING_FROM, GKA0110_MAX_LENGTH_NEW_BULIDING_FROM])
                },
                'custom[date]': {
                    'message': getMessage(DATE, [GKA0110_LBL.NEW_BULIDING_FROM])
                },
            },
            '#cldNewBulidingTo': {
                'custom[halfwidthAlphanumeric]': {
                    'message': getMessage(HALFWIDTH_ALPHANUMERIC, [GKA0110_LBL.NEW_BULIDING_TO])
                },
                'maxSize': {
                    'message': getMessage(MAXIMUM, [GKA0110_LBL.NEW_BULIDING_TO, GKA0110_MAX_LENGTH_NEW_BULIDING_TO])
                },
                'custom[date]': {
                    'message': getMessage(DATE, [GKA0110_LBL.NEW_BULIDING_TO])
                },
            },
            '#txtHouseNumberFromId': {},
            '#txtHouseNumberToId': {},
        },
        onValidationComplete : function(form, status) {
            // replace , character after validation complete
            if (status) {
                autoReplaceCommand();
            }
            return status;
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
                /* Check Full-width/全角    Katakana/カタカナ */
                // custom validationEngine
                "kana": {
                    "regex": patternRegex.kana,
                    "alertText": getMessage('E0007', ['*']),
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
    $('#txtHouseNumberFromId').attr("custom", GKA0110_LBL.HOUSE_NUMBER_FROM);
    $('#txtHouseNumberToId').attr("custom", GKA0110_LBL.HOUSE_NUMBER_TO);

    //Date
    $('#cldPeriodFrom').attr("data-validation-engine", "validate[ maxSize[10], custom[date]");
    $('#cldPeriodTo').attr("data-validation-engine", "validate[ maxSize[10],custom[date]]");
    $('#cldNewBulidingFrom').attr("data-validation-engine", "validate[ maxSize[10], custom[date]]");
    $('#cldNewBulidingTo').attr("data-validation-engine", "validate[ maxSize[10], custom[date]]");
    //Half Number
    $('#txtHouseNumberFromId').attr("data-validation-engine", "validate[funcCall[customHalfwidthNumbers], funcCall[customMaxSizeNumber][6]]");
    $('#txtHouseNumberToId').attr("data-validation-engine", "validate[funcCall[customHalfwidthNumbers], funcCall[customMaxSizeNumber][6]]");
}

function clearValidationEngineAttribute() {
    //Date
    disabledValidationEngine($('#cldPeriodFrom'));
    disabledValidationEngine($('#cldPeriodTo'));
    disabledValidationEngine($('#cldNewBulidingFrom'));
    disabledValidationEngine($('#cldNewBulidingTo'));
    //Half Number
    disabledValidationEngine($('#txtHouseNumberFromId'));
    disabledValidationEngine($('#txtHouseNumberToId'));
}

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
    var value = field.val();
    var len = value.length;
    if (len > max) {
        return getMessage(MAXIMUM, [label, max]);
    } else {
        // Do nothing.
    }
}

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

$("#exportCsv").click(function() {
    const fileName = "集計結果一覧";
    $.ajax({
        type: "GET",
        url: baseUrl + "/GKA0110/csvSuperVise/",
        success: function (data) {
            exportToCsv(data, fileName + currentDateForCsv() + ".csv");
        }
    });
});

function handleShowErrorDateTimePickerOnBlur(selector, param) {
    const $this = selector;
    const val = $this.val();
    const dateTimeRegEx = new RegExp(patternRegex.isDateFormat);
    const halfSizeAlphaRegEx = new RegExp(patternRegex.halfwidthAlphanumeric);
    const currentDate = $.datepicker.formatDate('yy/mm/dd', new Date());

    // Check required date
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
    // Check validate format date
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
    appendValidationEngineAttribute();
    $("#dataAggregate").submit();
}

function goBack() {
    window.location.href = baseUrl + '/GBA0110';
}

function summaryMansion(aggregationUnit, summaryCd) {
    event.preventDefault();
    const $form = $("#submitToGKA0120");
    $form.find("input[name=apartmentIds]").val(getAparatmentIdList(aggregationUnit, summaryCd));
    $form.submit();
}

if (!Array.prototype.find) {
    Object.defineProperty(Array.prototype, 'find', {
        value : function(predicate) {
            // 1. Let O be ? ToObject(this value).
            if (this == null) {
                throw new TypeError('"this" is null or not defined');
            }

            var o = Object(this);

            // 2. Let len be ? ToLength(? Get(O, "length")).
            var len = o.length >>> 0;

            // 3. If IsCallable(predicate) is false, throw a TypeError
            // exception.
            if (typeof predicate !== 'function') {
                throw new TypeError('predicate must be a function');
            }

            // 4. If thisArg was supplied, let T be thisArg; else let T be
            // undefined.
            var thisArg = arguments[1];

            // 5. Let k be 0.
            var k = 0;

            // 6. Repeat, while k < len
            while (k < len) {
                // a. Let Pk be ! ToString(k).
                // b. Let kValue be ? Get(O, Pk).
                // c. Let testResult be ToBoolean(? Call(predicate, T, « kValue,
                // k, O »)).
                // d. If testResult is true, return kValue.
                var kValue = o[k];
                if (predicate.call(thisArg, kValue, k, o)) {
                    return kValue;
                }
                // e. Increase k by 1.
                k++;
            }

            // 7. Return undefined.
            return undefined;
        }
    });
}

function getAparatmentIdList(aggregationUnit, summaryCd) {
    if (Array.isArray(GKA0110_GLOBAL_VAR.dataAggregateResultVo)) {
        const dataAgree = GKA0110_GLOBAL_VAR.dataAggregateResultVo.find(function(dataAgree) {
            return dataAgree.aggregateCredit === aggregationUnit;
        });
        if (dataAgree && Array.isArray(dataAgree.lstSummaryItem)) {
            const dataSummary = dataAgree.lstSummaryItem.find(function(dataSummary) {
                return dataSummary.summaryCd === summaryCd;
            });
            if (dataSummary && Array.isArray(dataSummary.lstIdMansion)) {
                return dataSummary.lstIdMansion;
            }
        }
    }
    return [];
}
