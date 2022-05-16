/* Error code for Required/必須 for select */
const REQUIRED_FOR_SELECT = 'E0001';
/* Error code for Required/必須 for input */
const REQUIRED_FOR_INPUT = 'E0002';
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
/* Message code for show dialog confirm*/
const CONFIRM = 'C0003';

const SCA0110_FIELD_NAME_LOGIN_ID = 'ログインID';
const SCA0110_FIELD_NAME_MAIL_ADDRESS = 'メールアドレス';
const SCA0110_DIALOG_PARAM_REISSUE_PASSWORD = 'パスワードを再発行'
const SCA0110_MAX_LENGTH_LOGIN_ID = '8';
const SCA0110_MAX_LENGTH_MAIL_ADDRESS = '120';

$(document).ready(function() {

    if (!SCA0110_MESSAGE_SUCCESS) {
        // initial focus
        $('[tabindex=1]').focus();
    }

    // Set previous url
    $(".reissue-password input[name='previousUrl'").val(sessionStorage.getItem('screenId'));

    // Event prevent on button reset captcha
    // If enter Enter => call click function and prevent submit form
    $('.reset-captcha').on('keypress', function (event) {
        if (event.which === 13) {
            $(this).click();
            return false;
        }
    });

    // Event click button reset captcha
    $('.reset-captcha').on('click', function() {
        var currentSrc = $('.image-captcha').attr('src');
        var indexEndSub = currentSrc.indexOf('?');
        if (indexEndSub !== -1) {
            currentSrc = currentSrc.substring(0, indexEndSub);
        }
        var newSrc = currentSrc + '?' + new Date().getTime();
        $('.image-captcha').attr('src', newSrc);
    });

    // Event click button back previous creen
    $('.back-previous-screen').on('click', function() {
        var previousScreen = sessionStorage.getItem('screenId');
        window.location.href = baseUrl + '/' + previousScreen;
    });

    // Event Biding message success from server
    if (SCA0110_MESSAGE_SUCCESS) {
        $("#dialog-success")
            .html(SCA0110_MESSAGE_SUCCESS)
            .dialog({
                modal:true,
                title: dialog.title,
                buttons: {
                    "OK": function() {
                        window.location.href = baseUrl + '/' + $(".reissue-password input[name='previousUrl'").val();
                    }
                }
        });
    }

    // Append max length attribute
    appendMaxLengthAttribute();

    // Apply validationEngine
    appendValidationEngineAttribute();

    $('.btn-submit').on('click', function() {
        $("#dialog-confirm")
        .html(getMessage(CONFIRM, SCA0110_DIALOG_PARAM_REISSUE_PASSWORD))
        .dialog({
            modal: true,
            title: dialog.title,
            width: 305,
            buttons: {
                "OK": function() {
                    $(this).dialog("close");
                    var result = $(".reissue-password").validationEngine('validate');
                    if (result) {
                        $("#form").submit();
                    }
                },
                "キャンセル": function() {
                    $(this).dialog("close"); 
                }
            }
        });
    });
    
    // Jquery validation engine
    $(".reissue-password").validationEngine({
        promptPosition : "bottomLeft",
        showArrowOnRadioAndCheckbox : true,
        focusFirstField : true,
        scroll : false,
        maxErrorsPerField: 1,
        'custom_error_messages': {
            "#txtLoginId": {
                'required': {
                    'message': getMessage(REQUIRED_FOR_INPUT, SCA0110_FIELD_NAME_LOGIN_ID)
                }
            },
            "#txtMail": {
                'required': {
                    'message': getMessage(REQUIRED_FOR_INPUT, SCA0110_FIELD_NAME_MAIL_ADDRESS)
                }
            }
        },
        onValidationComplete : function(form, status) {
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
                "halfwidthNumbers" : {
                    "regex":  patternRegex.halfwidthNumbers,
                    "alertText": getMessage(HALFWIDTH_NUMBERS, ['*']),                    
                },
                /* Check "Half-width/半角Alphanumeric/英数字"  */
                "halfwidthAlphanumeric": {
                     "regex":  patternRegex.halfwidthAlphanumeric,
                     "alertText": getMessage(HALFWIDTH_ALPHANUMERIC, ['*']),
                 },
                 /* Check Full-width characters/全角文字 */
                 "fullwidthCharacters": {
                     "regex":  patternRegex.fullwidthCharacters,
                     "alertText": getMessage(FULLWIDTH_CHARACTERS, ['*']),
                 },
                 /* Check Full-width/全角    Katakana/カタカナ */
                 "kana": {
                     "regex": patternRegex.kana,
                     "alertText": getMessage(FULLWIDTH_KATAKANA, ['*']),
                 },
                 "email": {
                     "regex": patternRegex.email,
                     "alertText": getMessage(MAIL, ['*']),
                 },
                 "maxSize": {
                     "regex": "none",
                     "alertText": "* ",
                     "alertText2": getMessage(MAXIMUM, ['*', '*'])
                 }
            };
        }
    };
    $.validationEngineLanguage.newLang();
})(jQuery);

function appendMaxLengthAttribute() {
    // Append max length attribute
    $('#txtLoginId').attr("maxlength", SCA0110_MAX_LENGTH_LOGIN_ID);
    $('#txtMail').attr("maxlength", SCA0110_MAX_LENGTH_MAIL_ADDRESS);
}

function appendValidationEngineAttribute() {
    // Append data-validation-engine
    $('#txtLoginId').attr("data-validation-engine", 'validate[required]');
    $('#txtMail').attr("data-validation-engine", 'validate[required]');
}