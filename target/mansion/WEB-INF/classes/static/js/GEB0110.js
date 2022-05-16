(function($){
    $.fn.validationEngineLanguage = function(){
    };
    $.validationEngineLanguage = {
        newLang: function(){
            $.validationEngineLanguage.allRules = {
                "required": { 
                    "regex": "none",
//                    "alertText": getMessage('E0002', '対応日時')
                    "alertText": getMessage('E0002', '対応日時'),
                    "alertTextCheckboxMultiple": "* 選択してください",
                    "alertTextCheckboxe": "* チェックボックスをチェックしてください"
                },
                "prohibitionCharacter": {
                    //"regex": /^[０-９－ａ-ｚＡ-Ｚぁ-んァ-ー一-龠　]+$/,
                     "regex":  /["'&<>,\\%]+$/,
                     "alertText": getMessage('E0015', '経過記録概要')
                 },
                "datetime": {
                	"regex": /[0-9]{4}\/(0[1-9]|1[0-2])\/(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]/,
                	"alertText": getMessage('E0008', '対応日時')
                },
                "maxSize": {
                    "regex": "none",
                    "alertText": "* ",
                    "alertText2": "文字以下にしてください"
                },
            };
            
        }
    };
    $.validationEngineLanguage.newLang();
})(jQuery);


/**
 * Upload file method
 * @param idInput
 * @param idFile
 * @returns
 */
function uploadFileImg(idInput, idFile) {
    idInput1 = "#" + idInput;
    idFile1 = "#" + idFile;
    $(idFile1).click();
}
 
/**
 * Event change file selected
 * @param idFile
 * @returns
 */
function changeFile(idFile) {
	 idFile1 = "#" + idFile;
	 if($(idFile1).val()) {
    	let textUpload = $(idFile1).val().replace(/^.*[\\\/]/, '');
        $(idInput1).val(textUpload);
    }
}
