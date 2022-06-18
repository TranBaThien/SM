const currentScreenId = "MAA0110";
const chkTermsConditionsKey = "MAA0110_chkTermsConditions";
const LOGIN_PERIOD_SCREEN_ID = "MAA0110_periodScreenId";
const screenId = "screenId";
const REQUIRED = 'E0002';
const MS_E0002 = "E0002";
const MS_E0107 = "E0107";

function registerAccount() {
    sessionStorage.setItem(LOGIN_PERIOD_SCREEN_ID, "MCA0110");
    $("#redirectMCAForm").submit();
}

function goForgotPasswordScreen() {
    sessionStorage.setItem(LOGIN_PERIOD_SCREEN_ID, "SCA0110");
    $('#redirectForm').attr('action', baseUrl + '/SCA0110');
    $('#redirectForm').submit();
}

function activeInactiveBtnLogin() {
    if($("#chkTermsConditions").is(':checked') && sessionStorage.getItem(chkTermsConditionsKey) === "ON") {
        $("button[name='btnLogin']").prop('disabled', false);
    } else {
        $("button[name='btnLogin']").prop('disabled', true);
    }
}

$(document).ready(function() {
    const periodScreenId = sessionStorage.getItem(LOGIN_PERIOD_SCREEN_ID);
    const isCheckedTermsConditions = sessionStorage.getItem(chkTermsConditionsKey);
    sessionStorage.clear();
    sessionStorage.setItem(screenId, currentScreenId);
    if (periodScreenId === 'MCA0110' || periodScreenId === 'SCA0110') {
        sessionStorage.setItem(chkTermsConditionsKey, isCheckedTermsConditions);
    }

    $("#chkTermsConditions").prop('checked', false);
    $("button[name='btnLogin']").prop('disabled', true);
    if (isError === "true") {
        $(".alert-error").attr("hidden", false);
        $("#chkTermsConditions").prop("checked", true);
        sessionStorage.setItem(chkTermsConditionsKey, "ON");
    }

    $("input[name='txtLoginId']").focus();

    $("#amLoginForm").validationEngine('attach', {
        promptPosition: "bottomLeft",
        showArrowOnRadioAndCheckbox: true,
        maxErrorsPerField: 1,
        showArrow: true,
        focusFirstField: true,
        scroll: false,
        'custom_error_messages': {
            '#txtLoginId': {
                'required': {
                    'message': getMessage(REQUIRED, "ログインID")
                }
            },
            '#pwdPassword': {
                'required': {
                    'message': getMessage(REQUIRED, "パスワード")
                }
            }
        }
    });

    $.get(baseUrl + "/MAA0110/notice", function(data, status){
        $("#contents_notice").html(data);
    });

    showHideEye();
    activeInactiveBtnLogin();
});

$("#chkTermsConditions").change(function() {
    activeInactiveBtnLogin();
});

$("#termsConditionsUrl").click(function () {
    sessionStorage.setItem(chkTermsConditionsKey, "ON");
    activeInactiveBtnLogin();
    window.open(baseUrl + "/SAA0110", "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=100,left=100,width=1000,height=700");
});

$("#lblChkTermsConditions").keypress(function (event) {
    if (event.which === 13 || event.which === 32)
    if($("#chkTermsConditions").is(':checked')) {
        $("#chkTermsConditions").prop('checked', false);
    } else {
        $("#chkTermsConditions").prop('checked', true);
    }
});

$("button[name='btnLogin']").click(function () {
    $(".alert-error").empty();
    if (!$("button[name='btnLogin']").disabled) {
        $("#amLoginForm").submit();
    }
});
