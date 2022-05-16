var message = {
		//Check required selected
		E0001: '{0} は必須項目です。選択してください。',
		//Check required input
		E0002: '{0} は必須項目です。入力してください。',
		//Input character 1 byte
		E0003: '{0} は半角数字で入力してください。',
		E0004: '{0} は半角数字（小数点有り）で入力してください。',
		E0005: '{0} は半角英数字で入力してください。',
		E0006: '{0} は全角文字で入力してください。',
		E0007: '{0} は全角カタカナで入力してください。',
		//Check format date
		E0008: '{0} は日付形式で入力してください。',
		E0009: '{0} はメールアドレスの形式で入力してください。',
		E0010: '{0} はURLの形式で入力してください。',
		E0011: '{0} は電話番号の形式で入力してください。',
		E0012: '{0} は{1} 文字以下で入力してください。',
		E0013: '{0} は{1} 文字以上入力してください。',
		E0014: '{0} は{1} から {2} 文字の間で入力してください。',
		//Input not allow
		E0015: '{0} の入力値に使用禁止文字が含まれています。',
		E0100: '{0} に誤りがあります。入力内容をご確認ください。',
		E0101: '入力されたログインIDは現在使用することができません。しばらく経ってから、再度ログインしてください。',
		E0102: '{0} を入力してください。',
		E0103: '{0} は半角アルファベット、半角数字、半角記号から2種類以上の組み合わせで入力してください。',
		E0104: '{0} は、 {1} で入力してください。',
		E0105: '{0} と {1} の入力内容が一致していません。',
		E0106: '既に退会している可能性があります。',
		E0107: '{0} に誤りがある可能性があります。',
		E0108: '添付資料の {0} に指定したファイルは、登録済みのファイルのファイル名と重複しています。',
		E0109: '添付資料に指定したファイルは {0}です。この形式のファイルはアップロードできません。',
		E0110: '添付資料の {0} に指定したファイルは、ファイルのアップロードサイズの上限（｛1｝）を超えています。',
		E0111: '添付資料の {0} に指定したファイルに問題があるため、アップロードできません。',
		E0112: '届出情報に変更があります、再度確認してください。',
		E0113: '他のユーザにより対象ユーザの状態が変更されました。再度確認してください。',
		E0114: '対象ユーザがログイン中のため、 {0} できません。',
		E0115: '添付資料の {0} に指定したファイルは、ファイル最大行数（｛1｝行）を超えたため、処理をキャンセルしました。',
		E0116: '{0} は {1} 回以下で入力してください。',
		E0117: '経過記録アップロード対象のマンションと異なるため、アップロードを失敗しました。',
		E0118: '対象マンションは届出済のため、督促通知登録できません。再度確認してください。',
		E0119: '他のユーザにより督促通知登録されました。再度確認してください。',
		E0120: 'アップロードファイルの上限数は｛0｝ファイルまでです。再度確認してください。',
		E0121: '既に登録済みのマンションのマンション名｛0｝と重複しています。再度確認してください。',
		E0122: '他のユーザによって経過記録情報が更新されました。再度経過記録情報を確認してください。',
		E0123: '{0} が存在しません。',
		E0124: '都支援対象を変更されないため、再度確認してください。',
		E0125: '{0} に誤りがある可能性があります。',
		E0126: 'ログインするユーザが使用されています。\n ブラウザの「×」ボタンをクリックした可能性があります。\n ブラウザの「×」ボタンをクリックした場合、{0}分間ログインできません。',
		E0127: '検索結果が{0}件を超えました。検索条件を変更して、再度検索を行ってください。',
		E0128: '{0}は既に登録されています。入力内容を確認してください。',
		E0129: '{0} に {1} より大きい値が入力されています。入力内容を確認してください。',
		E0130: '指定されたファイルのサイズが0バイトです。インポートできません。',
		E0131: '指定されたファイルの形式に誤りがあります。インポートできません。',
		E0132: '{0} には {1} を入力してください。',
		E0133: '指定されたファイルのサイズが｛0｝バイトを超えています。インポートできません。',
		E0134: '指定されたファイルに問題があります。インポートできません。',
		E0135: '指定されたファイルの形式に誤りがあります。インポートできません。',
		E0136: '指定されたファイルの形式に誤りがあります。インポートできません。',
		E0137: '指定されたファイルが存在しません。再度確認してください。',
		E0138: '{0} の場合、{1} を選択してください。',
		E0139: 'パズル認証が通過しません、再度確認してください。',
		E0140: '｛0｝の間に全角スペースを入力してください。',
};

/**
 * Get message from json object
 * @param key
 * @param argument
 * @returns
 */
function getMessage(key, argument) {
	return message[key].format(argument);
}

/**
 * Format string argument
 */
String.prototype.format = function() {
  var a = this;
  for (var k in arguments) {
    a = a.replace("{" + k + "}", arguments[k]);
  }
  return a;
}