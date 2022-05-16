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
const SPECIAL_CHARACTERS = 'E0015';
/* Error code for Mail address/アドレスAddress/アドレス */
const MAIL = 'E0009';
/* Error code for minimum/最小*/
const MINIMUM = 'E0013';
/* Error code for maximum/最大*/
const MAXIMUM = 'E0012';
/* Error code for range for minimum/最小 and maximum/最大 */
const RANGED = 'E0014';

var isMaster = '';
var submitType = '';
var redirecToGJA0120;
var message;
var apartmentId;
var newestNotificationNo;
var mailAddress;
var notificationPersonName;
var inveseNo ;
var rdoNoticeDestination;
var callReport;

$(document).ready(function(){
    /* Set time format */
    $("#datepicker-gga1").datetimepicker({
        format: "Y/m/d",
        timepicker:false
    });
    $('#button-datepicker-gga1').on('click', function(){
        $('#datepicker-gga1').focus();
    });
    $("#datepicker-gga2").datetimepicker({
        dateFormat: "Y/m/d"
    });
    $('#button-datepicker-gga2').on('click', function(){
        $('#datepicker-gga2').focus();
    });

	/* check show message area */
    if(message == ''){
        $("#messageErrorDiv").hide();
    }

    /* enable or disable select mail */
    if(mailAddress == ''){
        document.getElementById("r-91").disabled = true;
    }

    /* redirect to GJA0120 */
    if(rdoNoticeDestination == 1){
        isMaster = 'true';
    }else if(rdoNoticeDestination == 2 || rdoNoticeDestination == 3){
        isMaster = 'false';
    }

    /* confirm check box */
    document.getElementById("registerSurvey").disabled = true;
    $('#confirm').click(function() {
        var checkBox = document.getElementById("confirm");
        if (checkBox.checked == true){
            document.getElementById("registerSurvey").disabled = false;
        } else {
            document.getElementById("registerSurvey").disabled = true;
        }
    });

	$('#registerSurvey').on('click', function(){
		submitType = 'registerSurvey';
	});
	$('#temporarySave').on('click', function(){
		submitType = 'temporarySave';
	});
    /* resote button click */
    $('#ReStore').click(function() {
		submitType = 'ReStore';
     });



   /* get data txtRecipientNameUser */
    $('input[name="rdoNoticeDestination"]').click(function() {
         var checkBoxgga81 = document.getElementById("gga81");
         var checkBoxgga82 = document.getElementById("gga82");
         var checkBoxgga83 = document.getElementById("gga83");
         var checkBoxgga84 = document.getElementById("gga84");
         if (checkBoxgga81.checked){
             $("#txtRecipientNameUser").val("管理組合理事長")
             $("#txtTextAdress").val("貴管理組合が管理する")
         }else if (checkBoxgga82.checked){
             if('' != mailAddress){
                 $("#txtRecipientNameUser").val("区分所有者" + "　" + notificationPersonName)
             }else{
                 $("#txtRecipientNameUser").val("区分所有者") 
             }
             $("#txtTextAdress").val("条例第15条第６項に基づく認定を受けた貴殿が区分所有権を有する")
         }else  if (checkBoxgga83.checked){
            $("#txtRecipientNameUser").val("区分所有者")
             $("#txtTextAdress").val("貴殿が区分所有権を有する")
         }else  if (checkBoxgga84.checked){
            $("#txtRecipientNameUser").val("")
             $("#txtTextAdress").val("")
         }
    });
    
    /* check 5 row of text area */
    var numberOfLines = 0;
    $('textarea[data-limit-rows=true]')
    .on('keypress', function (event) {
        var textarea = $(this);
        var text = textarea.val();
        var numberOfLines = (text.match(/\n/g) || []).length + 1;
        if(event.which === 13 && numberOfLines == 6) {
          return false;
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
                 /* specialCharacters */
                 "specialCharacters": {
                     "regex": "/^[0-9\ ]+$/",
                     "alertText": getMessage('E0015', ['*']),
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
