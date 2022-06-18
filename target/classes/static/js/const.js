var message ={
        I0001: '{0}を{1}しました。',
        I0002: '{0}が存在しません。',
        C0001: '入力内容は反映されません。<br />よろしいでしょうか。',
        C0002: '入力内容は上書きされます。<br />よろしいでしょうか。',
        C0003: '入力した内容で{0}します。<br />よろしいでしょうか。',
        C0004: '{0}を{1}。<br />よろしいでしょうか。',
        C0005: '{0}します。<br />よろしいでしょうか。',
        //Check required selected
        E0001: '{0}は必須項目です。選択してください。',
        //Check required input
        E0002: '{0}は必須項目です。入力してください。',
        //Input character 1 byte
        E0003: '{0}は半角数字で入力してください。',
        E0004: '{0}は半角数字（小数点有り）で入力してください。',
        E0005: '{0}は半角英数字で入力してください。',
        E0006: '{0}は全角文字で入力してください。',
        E0007: '{0}は全角カタカナで入力してください。',
        //Check format date
        E0008: '{0}は日付（時間）形式で入力してください。',
        E0009: '{0}はメールアドレスの形式で入力してください。',
        E0010: '{0}はURLの形式で入力してください。',
        E0011: '{0}は電話番号の形式で入力してください。',
        E0012: '{0}は{1}文字以下で入力してください。',
        E0013: '{0}は{1}文字以上で入力してください。',
        E0014: '{0}は{1}から{2}文字の間で入力してください。',
        //Input not allow
        E0015: '{0}の入力値に以下の使用禁止文字が含まれています。<br />"　\'　\\　%　&　<　>',
        E0016: '小数点以下は{0}桁で入力してください。',
        E0100: '{0}に誤りがあります。入力内容をご確認ください。',
        E0101: '入力されたログインIDは現在使用することができません。しばらく経ってから、再度ログインしてください。',
        E0102: '{0}を入力してください。',
        E0103: '{0}は半角アルファベット、半角数字、半角記号から2種類以上の組み合わせで入力してください。',
        E0104: '{0}は、{1}で入力してください。',
        E0105: '{0}と{1}の入力内容が一致していません。',
        E0106: '他のユーザにより助言通知が登録されたため、助言通知登録できません。再度確認してください。',
        E0107: '{0}に誤りがある可能性があります。',
        E0108: 'アップロードファイルに指定した{0}は、指定済の他のファイル名と重複しているか、添付ファイルのファイル名と重複しています。',
        E0109: '添付資料に指定したファイルは{0}です。この形式のファイルはアップロードできません。',
        E0110: '添付資料の{0}に指定したファイルは、ファイルのアップロードサイズの上限（{1}）を超えています。',
        E0111: '添付資料の{0}に指定したファイルに問題があるため、アップロードできません。',
        E0112: '届出情報に変更があるため、登録できません。再度確認してください。',
        E0113: '他のユーザにより対象ユーザの状態が変更されました。再度確認してください。',
        E0114: '対象ユーザがログイン中のため、{0}できません。',
        E0115: '添付資料の{0}に指定したファイルは、ファイル最大行数（{1}行）を超えたため、処理をキャンセルしました。',
        E0116: '{0}は{1}回以下で入力してください。',
        E0117: '登録対象のマンションが存在しない、または他の区市町村のマンションのため、登録できません。',
        E0118: '対象マンションは届出済のため、督促通知登録できません。再度確認してください。',
        E0119: '他のユーザにより督促通知登録されました。再度確認してください。',
        E0120: 'アップロードファイルの上限数は{0}ファイルまでです。再度確認してください。',
        E0121: '既に登録済みのマンションのマンション名{0}と重複しています。再度確認してください。',
        E0122: '他のユーザによって経過記録情報が更新されました。再度経過記録情報を確認してください。',
        E0123: '{0}が存在しません。',
        E0124: '都支援対象が変更されていません。入力内容を確認してください。',
        E0125: '{0}に誤りがある可能性があります。',
        E0126: 'ログインするユーザが使用されています。<br />ブラウザの「×」ボタンをクリックした可能性があります。<br />ブラウザの「×」ボタンをクリックした場合、{0}分間ログインできません。',
        E0127: '検索結果が{0}件を超えました。検索条件を変更して、再度検索を行ってください。',
        E0128: '{0}は既に登録されています。入力内容を確認してください。',
        E0129: '{0}に{1}より大きい値が入力されています。入力内容を確認してください。',
        E0130: '指定されたファイルのサイズが0バイトです。インポートできません。',
        E0131: '指定されたファイルの形式に誤りがあります。インポートできません。',
        E0132: '{0}には{1}を入力してください。',
        E0133: '指定されたファイルのサイズが{0}バイトを超えています。インポートできません。',
        E0134: '指定されたファイルに問題があります。インポートできません。',
        E0135: '指定されたファイルの形式に誤りがあります。インポートできません。',
        E0136: '指定されたファイルの形式に誤りがあります。インポートできません。',
        E0137: '指定されたファイルが存在しません。再度確認してください。',
        E0138: '{0}の場合、{1}を選択してください。',
        E0139: 'パズル認証が通過しません、再度確認してください。',
        E0140: '{0}と{1}の間に全角スペースを入力してください。',
        E0141: '助言通知を行いますので、現在届出ができません。助言通知後に再度登録してください。',
        E0142: '{0}と{1}の間に少なくとも一方に記載してください。',
        E0143: '{0}は一括登録対象外のため、インポートできません。',
        E0144: '{0}は、{1}で選択してください。',
        E0145: '検索条件に該当する{0}はありません。',
        E0146: '{0}と{1}が同じです。入力内容を確認してください。',
        E0147: 'アップロードファイルは、ファイル最大行数（{0}行）を超えたため、処理をキャンセルしました。',
        E0148: '{0}に{1}より前の日付入力できません、再度確認してください。',

};

var patternRegex ={
        isNotSpecialCharacter: /["'&<>\\%]/,
        //yyyy/MM/dd HH:mm
        isDateTimeFormat: /[0-9]{4}\/(0[1-9]|1[0-2])\/(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]/,
        halfwidthNumbers: /^[0-9\ ]+$/,
        halfwidthAlphanumeric: /^[0-9a-zA-Z@.+-\/: ]*$/,
        // fullwidthCharacters: /^[ｦ-ﾟ ､a-zA-Z@.]*$/,
        fullwidthCharacters: /^[^ -~｡-ﾟ]*$/,
        // kana: /^[ァ-・ヽヾ゛゜ー]*$/,
        kana: /^[ァ-・ヽヾ゛゜ー　]*$/,
        email: /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i,
        isDateFormat: /^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])$/,
        date: /^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])$/,
        dateTime: /^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01]) ((0?[0-9])|(1[0-9])|(2[0-3])):([0-5][0-9])$/,
        phone: /^([\+][0-9]{1,3}([ \.\-])?)?([\(][0-9]{1,6}[\)])?([0-9 \.\-]{1,32})(([A-Za-z \:]{1,11})?[0-9]{1,4}?)$/
};

/**
 * Message
 */
var dialog = {
    title : 'Message'
}

/**
 * URL jpostal
 */
var jpostal_url = {
		//UAT URL
		//http: 'http://'+ window.location.hostname + '/doc/json/',
		//https: 'https://'+ window.location.hostname +'/doc/json/'
		//PROD URL
		//http: 'http://mansion-todokede.metro.tokyo.lg.jp/mng_apart/doc/json/',
		//https: 'https://mansion-todokede.metro.tokyo.lg.jp/mng_apart/doc/json/'
		//CT URL
		http: 'http://192.168.66.70:8080/mng_apart/doc/json/',
		https: 'https://192.168.66.70:8080/mng_apart/doc/json/'
}

/**
 * Get message from json object
 * Ex: getMessage('E0138', ['abc', 'xyz']))
 * @param key
 * @param argument
 * @returns
 */
function getMessage(key, argument) {
    var str = message[key];
    if (Array.isArray(argument)) {
        for ( var k in argument) {
            str = str.replace("{" + k + "}", argument[k]);
        }
        return str;
    } else {
        return str.replace("{0}", argument);
    }
}

$.fn.showError = function(message) {
    const classError = $(this).attr('id');
    // check if error already exist, edit content error
    if ($(this).prev('.' + classError).length > 0) {
        $(this).prev('.' + classError).find('.formErrorContent').html(message);
        return;
    }
    // if error not exist, create new one
    // get position of error
    $(this).parent().css('position', 'relative');
    const top = $(this).outerHeight();
    const left =  $(this).position().left;
    
    var error = '<div class="formError '+classError+'" style="opacity: 0.87;position: absolute;top: '+top+'px;left: '+left+'px;right: initial;margin-top: 0px;">';
        error += '  <div class="formErrorArrow formErrorArrowBottom">';
        error += '    <div class="line1"></div>';
        error += '    <div class="line2"></div>';
        error += '    <div class="line3"></div>';
        error += '    <div class="line4"></div>';
        error += '    <div class="line5"></div>';
        error += '    <div class="line6"></div>';
        error += '    <div class="line7"></div>';
        error += '    <div class="line8"></div>';
        error += '    <div class="line9"></div>';
        error += '    <div class="line10"></div>';
        error += '  </div>';
        error += '  <div class="formErrorContent">'+message+'</div>';
        error += '</div>';

    $(error).insertBefore($(this));
}

$.fn.removeError = function() {
    const inputId = $(this).attr('id');
    $(this).prev('.' + inputId).remove();
}

function handleShowErrorDateTimePickerOnBlur(selector, param, dateFormat, isDate) {
    const DATE = 'E0008';
    const REQUIRED = 'E0002';
    const $this = selector;
    const val = $this.val();
    const halfSizeAlphaRegEx = new RegExp(patternRegex.halfwidthAlphanumeric);
    const currentDate = $.datepicker.formatDate(dateFormat, new Date());
    var dateTimeRegEx = null;
    if(isDate){
        dateTimeRegEx = new RegExp(patternRegex.isDateFormat);
    }else{
        dateTimeRegEx = new RegExp(patternRegex.dateTime);
    }

    //Check required date
    if (val == '' || val == undefined) {
        // remove custom error
            $this.removeError();
    } else if (!halfSizeAlphaRegEx.test(val)) {
        $this.val(currentDate);
        // custom show error
        $this.showError(getMessage(HALFWIDTH_ALPHANUMERIC, param));
    } else if (!dateTimeRegEx.test(val)) {
        if(val.length == 8){
            var year = val.substring(0,4);
            var month = val.substring(4,6);
            var date = val.substring(6,8);
            var fulldate = year + '/' + month + '/' + date;
            if(dateTimeRegEx.test(fulldate)){
                $this.val(fulldate);
                $this.removeError();
            } else {
                $this.val(currentDate);
                $this.showError(getMessage(DATE, param));
            }
        } else {
            $this.val(currentDate);
            // custom show error
            $this.showError(getMessage(DATE, param));
        }
    }
    //Check validate format date
    if (dateTimeRegEx.test(val) && halfSizeAlphaRegEx.test(val)) {
        // remove custom error
        $this.removeError();
    }
}

$.fn.showMessage = function(message){
    var selector = this.attr('id');
    if ($('#' + selector).parent().parent().find('.formError').length > 0) {
        return;
    }
    var position = $('#' + selector).position();
    var top = position.top + 20;
    var error = '<div class="formError '+ selector +'" style="opacity: 0.87 !important; position: absolute; top: '+ top +'px; left: '+ position.left +'px; right: initial; margin-top: 0px; display: block;">';
    error += ' <div class="formErrorArrow formErrorArrowBottom">';
    error += ' <div class="line1"></div>';
    error += ' <div class="line2"></div>';
    error += ' <div class="line3"></div>';
    error += ' <div class="line4"></div>';
    error += ' <div class="line5"></div>';
    error += ' <div class="line6"></div>';
    error += ' <div class="line7"></div>';
    error += ' <div class="line8"></div>';
    error += ' <div class="line9"></div>';
    error += ' <div class="line10"></div>';
    error += ' </div>';
    error += ' <div class="formErrorContent">'+message+'</div>';
    error += '</div>';
    $(error).insertBefore($('#' + selector));
}

function radioTab(selectorRadio, e, fieldJPName, isVertical){
    // 9 tab. 32 space
    /* Action tab */
    const selector = $(selectorRadio).children("input").attr('id');
    const selectorName = $(selectorRadio).children("input").attr('name');
    if (e.keyCode == 32){
        if(!($("#" + selector).parent().hasClass('disabledItem') || $("#" + selector).parent().hasClass('disabledRadioCustom')))
        $("#" + selector).not(':checked').prop("checked", true);
        e.preventDefault();
    }
    var lastSelector = $("input[name='"+ selectorName +"']:last").attr("id");
    var firstSelector = $("input[name='"+ selectorName +"']:first").attr("id");
    var showId;
    if(isVertical == true){
        showId = lastSelector;
    } else {
        showId = firstSelector;
    }

    var isChecked = $("input[name='"+ selectorName +"']").is(":checked");
    if(!isChecked && e.keyCode == 9 && selector == lastSelector) {
        $("#" + showId).showMessage(getMessage('E0001', [fieldJPName]));
    }
    if(isChecked){
        $("#" + showId).prev('.' + showId).remove();
    } else if (e.type == "focusout"){
        $("#" + showId).showMessage(getMessage('E0001', [fieldJPName]));
    }
}

function checkRadioByClassId(rootSelector, e, localVal, fieldJPName, isVertical){
    if(e.type == "keydown"){
        localVal[0] = 1;
        return
    }
    if(e.type == "keypress"){
        radioTab(rootSelector, e, fieldJPName, isVertical);
    }
    var selecter = $(rootSelector).children("input").attr('id');
    const selectorName = $(rootSelector).children("input").attr('name');
    var lastId = $("input[name='"+ selectorName +"']:last").attr("id");
    if(localVal[0] == 0 || selecter == lastId){
        radioTab(rootSelector, e, fieldJPName, isVertical);
    } else {
        localVal[0] = 0;
    }
}

$.fn.spacePress = function(e){
    var selector = this.attr('id');
    if (e.keyCode == 32){
        $("#" + selector).prop("checked", !$("#" + selector).prop("checked"));
    }
    e.preventDefault();
}

$.fn.spacePressRadio = function(e){
    var selector = this.attr('id');
    if (e.keyCode == 32 && !($("#" + selector).parent().hasClass('disabledItem') || $("#" + selector).parent().hasClass('disabledRadioCustom'))){
        $("#" + selector).not(':checked').prop("checked", true);
    }
    e.preventDefault();
}

function leftPadZero(number) {
    if (number && number.length < 2) {
        return number = '0' + number;
    }
    return number;
}

/**
 * get date current date for file csv name
 * @returns
 */
function currentDateForCsv() {
    var date = new Date();
    var	month = '' + (date.getMonth() + 1),
        day = '' + date.getDate(),
        year = date.getFullYear(),
        hours = '' + date.getHours(),
        minutes = '' + date.getMinutes(),
        seconds = '' + date.getSeconds();

    month = leftPadZero(month);
    day = leftPadZero(day);
    hours = leftPadZero(hours);
    minutes = leftPadZero(minutes);
    seconds = leftPadZero(seconds);

    var formatDate = year + month + day;
    var time = [hours, minutes, seconds].join('');
    return [formatDate, time].join('_');
}
