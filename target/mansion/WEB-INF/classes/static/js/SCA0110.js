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
/* Error code for Mail address/アドレスAddress/アドレス */
const MAIL = 'E0009';
/* Error code for minimum/最小*/
const MINIMUM = 'E0013';
/* Error code for maximum/最大*/
const MAXIMUM = 'E0012';
/* Error code for range for minimum/最小 and maximum/最大 */
const RANGED = 'E0014';

$(document).ready(function() {

	const SCA0110_MAX_LENGTH_LOGIN_ID = '8';
    const SCA0110_MAX_LENGTH_MAIL_ADDRESS = '120';

    // Set prevous url
    $(".reissue-password input[name='previousUrl'").val(sessionStorage.getItem('previousScreen'));

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
        var previousScreen = sessionStorage.getItem('previousScreen');
        window.location.href = '/' + previousScreen;
    });

    // Jquery validation engine
    $(".reissue-password").validationEngine({
        promptPosition : "bottomLeft",
        showArrowOnRadioAndCheckbox : true,
        focusFirstField : true,
        scroll : false,
        'custom_error_messages': {
            "#txtLoginId": {
                'required': {
                    'message': getMessage(REQUIRED, 'ログインID')
                },
                'custom[halfwidthAlphanumeric]' : {
                    'message': getMessage(HALFWIDTH_ALPHANUMERIC, ['ログインID'])
                },
                'maxSize' : {
                    'message': getMessage(MAXIMUM, ['ログインID', SCA0110_MAX_LENGTH_LOGIN_ID])
                }
            },
            "#txtMail": {
                'required': {
                    'message': getMessage(REQUIRED, 'メールアドレス')
                },
                'custom[email]' : {
                    'message': getMessage(MAIL, ['メールアドレス'])
                },
            }
        },
        onValidationComplete : function(form, status) {
            if (status == true) {
                // 確認メッセージ（C0003）を表示する。 - show message confirm before submit
                $("#dialog-confirm")
                .html(getMessage('C0003', 'パスワードを再発行'))
                .dialog({
                    modal: true,
                    title: "確認ダイアログ",
                    width: 305,
                    buttons: {
                        "OK": function() {
                            $(form).get(0).submit();
                        },
                        "キャンセル": function() {
                            $(this).dialog("close"); 
                        }
                    }
                });
            }
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
                    "regex":  /^[0-9\ ]+$/,
                    "alertText": getMessage('E0003', ['*']),                    
                },
                /* Check "Half-width/半角Alphanumeric/英数字"  */
                "halfwidthAlphanumeric": {
                     "regex":  /^[ｦ-ﾟ ､0-9a-zA-Z@.]*$/,
                     "alertText": getMessage('E0005', ['*']),
                 },
                 /* Check Full-width characters/全角文字 */
                 "fullwidthCharacters": {
                     "regex":  /^[ｦ-ﾟ ､a-zA-Z@.]*$/,
                     "alertText": getMessage('E0006', ['*']),
                 },
                 /* Check Full-width/全角    Katakana/カタカナ */
                 "kana": {
                     "regex": /^[０-９－ａ-ｚＡ-Ｚぁ-んァ-ー一-龠　]+$/,
                     "alertText": getMessage('E0007', ['*']),
                 },
                 "email": {
                     "regex": /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i,
                     "alertText": getMessage('E0009', ['*']),
                 },
                 "maxSize": {
                     "regex": "none",
                     "alertText": "* ",
                     "alertText2": getMessage('E0012', ['*', '*'])
                 },
            };
        }
    };
    $.validationEngineLanguage.newLang();
})(jQuery);