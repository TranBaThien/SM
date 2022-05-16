/**
 * ショートカットキー以外の禁止対応
 */


/**
 * 実行中のブラウザ名を取得する。
 * @returns {string}
 */
function getBrowserName() {
	var userAgent = window.navigator.userAgent.toLowerCase(),
		appVersion = window.navigator.appVersion.toLowerCase();
	if (userAgent.indexOf('msie') != -1) {
		if (appVersion.indexOf('msie 9.') != -1) {
			return 'ie9';
		} else if (appVersion.indexOf('msie 10.') != -1) {
			return 'ie10';
		}
	} else if ((userAgent.indexOf('trident') != -1) && (userAgent.indexOf('11') != -1)) {
		return 'ie11';
	} else if (userAgent.indexOf('edge') != -1) {
		return 'edge';
	} else if (userAgent.indexOf('chrome') != -1) {
		return 'chrome';
	} else if (userAgent.indexOf('firefox') != -1) {
		return 'firefox';
	} else if (userAgent.indexOf('safari') != -1) {
		return 'safari';
	} else if (userAgent.indexOf('opera') != -1) {
		return 'opera';
	}
}

function unbindConfirmMessageEvent() {
	$(window).unbind('.confirmMessage');
}

// M1720S00193_20180807_START
$(window).bind('contextmenu', function() {
	window.is_confirm = true;
})
// M1720S00193_20180807_END

$(window).bind('beforeunload.confirmMessage', function() {
// Case blank.html, message will not display (add src='blank.html' to fix https issue)
	// M1720CL0109_20180822_START
	// if(window.is_confirm !== false)
if(window.is_confirm != null && window.is_confirm !== false)
	//仕1800081_20180918_START
	//return "\n【重要！】\nブラウザの×ボタンを押下しないでください。\n終了する場合は、ログアウトボタンを押下してください。\n\n\n※正しく終了しない場合、一定時間ログインできません。入力したデータが失われますのでご注意願います。";
	return "\n【重要！】\nブラウザの×ボタンが押下されました。\n以下の「→　ページに留まる(S)」を必ず選択してください。\n\n\n※システムを終了する場合は、ログアウトボタンを押下してください。\n※ログアウトボタンを押下して終了しなかった場合、一定時間ログインできません。また、入力したデータは失われますのでご注意ください。";
	//仕1800081_20180918_END
})
.bind('mouseover.confirmMessage mouseleave.confirmMessage keydown.confirmMessage', function(event) {
	if (event.type == 'keydown') {
		if (event.keyCode == 13 ) //Enterキーでボタンなど起動する場合の処理
			//A001-007_M1720S00028_20180718_START
			//is_confirm = false;
			window.is_confirm = false;
			//A001-007_M1720S00028_20180718_END
	} else {
		//A001-007_M1720S00028_20180718_START
		//is_confirm = event.type == 'mouseleave';
		if (window.stop_window_bind_event !== true) {
			window.is_confirm = event.type == 'mouseleave';
		}
		//A001-007_M1720S00028_20180718_END
	}
});

//リンク・ボタン上での右クリックを禁止にする
$(function() {
	$("a").on("contextmenu", function() {
		window.is_confirm = false;
		return false;
	});
	$("input:button").on("contextmenu", function() {
		window.is_confirm = false;
		return false;
	});
	$("input:submit").on("contextmenu", function() {
		window.is_confirm = false;
		return false;
	});
	$(document).ajaxComplete(function() {
		$("a").on("contextmenu", function() {
			window.is_confirm = false;
			return false;
		});
		$("input:button").on("contextmenu", function() {
			window.is_confirm = false;
			return false;
		});
		$("input:submit").on("contextmenu", function() {
			window.is_confirm = false;
			return false;
		});
	});
});

$(function() {
	$(document).on('dragenter drop dragover', function(event) {
		event.stopPropagation();
		event.preventDefault();
	});
});

// SK1-217_20180910_START
// Enter抑止
$(function() {
	$(document).on("keypress", "input[type='text']:not(.allow_submit)", function(event) {
		return event.which !== 13;
	});
});
// SK1-217_20180910_END

//A001-007_M1720S00028_20180718_START
// override window.alert() function
(function() {

	var proxied = window.alert;
	window.alert = function() {

		var returnValue;

		window.stop_window_bind_event = true;
		returnValue = proxied.apply(this, arguments);
		window.stop_window_bind_event = false;

		return returnValue;
	};
})();

//override window.confirm() function
(function() {

	var proxied = window.confirm;
	window.confirm = function() {

		var returnValue;

		window.stop_window_bind_event = true;
		returnValue = proxied.apply(this, arguments);
		window.stop_window_bind_event = false;

		return returnValue;
	};
})();
// A001-007_M1720S00028_20180718_END

// M1720CL0005_20180816_START
function displayBanMessage() {
    //アラートは時間差で表示しないとショートカットキー操作の抑止に失敗する場合がある。Firefox全般、IEのAlt+Cなど。
    var browser = getBrowserName();
    if (browser.indexOf('ie') !== -1 || browser.indexOf('edge') !== -1) {
      alert('その操作は禁止されています');
    } else {
    	// M1720CL0005_20180809_START
      setTimeout(function() { alert('その操作は禁止されています')  }, 1);
    	// M1720CL0005_20180809_END
    }
}
// M1720CL0005_20180816_END


/**
 * ショートカットキー禁止対応
 */
!function() {
//$(window).bind('keydown', function() {
	var shortcutBlackList = {
		'Win': {
			'IE9': [
				'F11',
				'Alt+Home',
				'Alt+Right',
				'Alt+Left',
				'BackSpace',
				'Shift+F10',
				//'Alt+F4',
				'Ctrl+F4',
				'F5',
				'Ctrl+R',
				'Ctrl+F5',
				'Esc',
				'Ctrl+O',
				'Ctrl+L',
				'Ctrl+N',
				'Ctrl+W',
				'Ctrl+S',
				'Ctrl+E',
				'Ctrl+I',
				'Ctrl+H',
				'Ctrl+D',
				'Ctrl+B',
				'Ctrl+T',
				'Ctrl+Shift+P',
				'Ctrl+Shift+L',
				'Alt+Enter'
			],
			'IE10': [
				'F11',
				'Alt+Home',
				'Alt+Right',
				'Alt+Left',
				'BackSpace',
				'Shift+F10',
				//'Alt+F4',
				'Ctrl+F4',
				'F5',
				'Ctrl+R',
				'Ctrl+F5',
				'Esc',
				'Ctrl+O',
				'Ctrl+L',
				'Ctrl+N',
				'Ctrl+W',
				'Ctrl+S',
				'Ctrl+E',
				'Ctrl+I',
				'Ctrl+H',
				'Ctrl+D',
				'Ctrl+B',
				'Ctrl+T',
				'Ctrl+Shift+P',
				'Ctrl+Shift+L',
				'Alt+Enter'
			],
			'IE11': [
				'F11',
				'Alt+Home',
				'Alt+Right',
				'Alt+Left',
				'BackSpace',
				'Shift+F10',
				//'Alt+F4',
				'Ctrl+F4',
				'F5',
				'Ctrl+R',
				'Ctrl+F5',
				'Esc',
				'Ctrl+O',
				'Ctrl+L',
				'Ctrl+N',
				'Ctrl+W',
				'Ctrl+S',
				'Ctrl+E',
				'Ctrl+I',
				'Ctrl+H',
				'Ctrl+D',
				'Ctrl+B',
				'Ctrl+T',
				'Ctrl+Shift+P',
				'Ctrl+Shift+L',
				'Alt+Enter'
			],
			'Edge': [
				'F11',
				'Alt+Home',
				'Alt+Right',
				'Alt+Left',
				'BackSpace',
				'Shift+F10',
				//'Alt+F4',
				'Ctrl+F4',
				'F5',
				'Ctrl+R',
				'Ctrl+F5',
				'Esc',
				'Ctrl+O',
				'Ctrl+L',
				'Ctrl+N',
				'Ctrl+W',
				'Ctrl+S',
				'Ctrl+E',
				'Ctrl+I',
				'Ctrl+H',
				'Ctrl+D',
				'Ctrl+B',
				'Ctrl+T',
				'Ctrl+Shift+P',
				'Ctrl+Shift+L',
				'Alt+Enter'
			],
			'Chrome': [
				'F11',
				'Alt+Home',
				'Alt+Right',
				'Alt+Left',
				'BackSpace',
				'Shift+F10',
				//'Alt+F4',
				'Ctrl+F4',
				'F5',
				'Ctrl+R',
				'Ctrl+F5',
				'Esc',
				'Ctrl+O',
				'Ctrl+L',
				'Ctrl+N',
				'Ctrl+W',
				'Ctrl+S',
				'Ctrl+E',
				'Ctrl+I',
				'Ctrl+H',
				'Ctrl+D',
				'Ctrl+B',
				'Ctrl+T',
				'Ctrl+Shift+P',
				'Ctrl+Shift+L',
				'Alt+Enter'
			],
			'FireFox': [
				'F11',
				'Alt+Home',
				'Alt+Right',
				'Alt+Left',
				'BackSpace',
				'Shift+F10',
				//'Alt+F4',
				'Ctrl+F4',
				'F5',
				'Ctrl+R',
				'Ctrl+F5',
				'Esc',
				'Ctrl+O',
				'Ctrl+L',
				'Ctrl+N',
				'Ctrl+W',
				'Ctrl+S',
				'Ctrl+E',
				'Ctrl+I',
				'Ctrl+H',
				'Ctrl+D',
				'Ctrl+B',
				'Ctrl+T',
				'Ctrl+Shift+P',
				'Ctrl+Shift+L',
				'Alt+Enter'
			]
		},
		'Mac': {
			'Safari': [
				'Shift+Backspace',//macにはdeleteキーが二つある。BackspaceはEnterキーの上にあるDeleteキーを指す。
				'Command+]',
				'Backspace',
				'Command+[',
				'Command+R',
				'Command+Shift+H',
				'Command+D',
				'Command+Option+B',
				'Command+Option+L',
				'Command+S',
				'Command+Shift+S',
				'Command+N',
				'Command+E',
				'Command+T',
				'Command+Shift+W',
				'Command+W',
				'Esc',
				'Command+Option+U',
				'Command+I',
				'Command+Shift+I',
				'Command+Option+K',
				'Command+Option+S',
				'Shift+Command+D',
				'Command+Option+1',
				'Command+Shift+B',
				'Command+Option+F',
				'Command+L',
				'Command+Q',
				'Command+Option+W',
				'Command+Option+Shift+W'
			]
		}
	};

	disableBrowserShortcutKeys(shortcutBlackList, function() {
		// show message warning hot key
        displayBanMessage();
	});
}();

/**
 * ブラウザのショートカットキーの動作を抑制する。
 *
 * @param shortcutBlackList 止めたいショートカットキーのリスト ex... { 'Windows': {'IE9': ['Ctrl+C', 'Alt+Left'], 'Chrome':['F5', 'Ctrl+Pageup']}, 'Mac' : 'safari':{'Command+R'} }
 * @param callback 抑制対象に指定されたキーが入力された場合に実行されるコールバック関数。 この関数内でfalseを返せば、抑制を取りやめる（ブラウザの動作が実行される）。
 */
function disableBrowserShortcutKeys(shortcutBlackList, callback) {
	
	var MODIFIER_KEYS_CODE_MAP = {
		16: ['shift', '?'],
		17: ['ctrl', 'control', '?'],
		18: ['alt', 'option', '?'],
		91: ['meta', 'command', '?']
	};

	var NON_ALPHABETIC_KEYS_CODE_MAP = {
		186: ':',
		187: ';',
		188: ',',
		189: '-',
		190: '.',
		191: '/',
		192: '@',
		219: '[',
		220: '\\',
		221: ']',
		222: '^',
		226: '_',//For second backslash key. Japanese keyboards have two backslash key. So we need separate the symbol for them.
		27: ['esc', 'escape'],
		9: 'tab',
		32: 'space',
		13: ['enter', 'return'],
		8: ['backspace', 'bs'],
		145: ['scrolllock', 'scroll_lock', 'scroll'],
		20: ['capslock', 'caps_lock', 'caps'],
		144: ['numlock', 'num_lock', 'num'],
		19: ['pause', 'break'],
		45: 'insert',
		36: 'home',
		46: 'delete',
		35: 'end',
		33: ['pageup', 'page_up', 'pu'],
		34: ['pagedown', 'page_down', 'pd'],
		37: ['←', 'left'],
		38: ['↑', 'up'],
		39: ['→', 'right'],
		40: ['↓', 'down'],
		112: 'f1',
		113: 'f2',
		114: 'f3',
		115: 'f4',
		116: 'f5',
		117: 'f6',
		118: 'f7',
		119: 'f8',
		120: 'f9',
		121: 'f10',
		122: 'f11',
		123: 'f12'
	};

	var BACKSPACE_EXCEPTIONAL_INPUT_TYPE = [
		'text',
		'password',
		'date',
		'datetime',
		'datetime-local',
		'email',
		'month',
		'number',
		'range',
		'search',
		'tel',
		'time',
		'url',
		'week'
	];

	/**
	 * パース後の抑止対象リストを格納するオブジェクト
	 * @param nonParsedShortcuts
	 * @constructor
	 */
	function ShortcutBlackList(nonParsedShortcuts) {
		this.blackList = [];
		for (var key in nonParsedShortcuts) if (nonParsedShortcuts.hasOwnProperty(key)) {
			this.blackList.push(new KeyCombinationInfo(nonParsedShortcuts[key]));
		}
	}

	/**
	 * onkeydownのイベントオブジェクト一致するキー情報を返却する。
	 * 無ければnullを返却する。
	 * @param event
	 * @returns {*}
	 */
	ShortcutBlackList.prototype.findKeyInfoByEvent = function(event) {
		for (var i in this.blackList) if (this.blackList.hasOwnProperty(i)) {
			if (this.blackList[i].matchWithEvent(event)) return this.blackList[i];
		}
		return null;
	};

	/**
	 * パース後のキーの組み合わせ情報を格納するオブジェクト
	 * @param nonParsedShortcut
	 * @constructor
	 */
	function KeyCombinationInfo(nonParsedShortcut) {
		this.original = nonParsedShortcut;

		var keys = nonParsedShortcut.toLowerCase().split("+");
		this.keyName = keys[keys.length - 1]; // 末尾に指定されたキーをメインキーとする。
		this.shiftKey = containsShift(keys);
		this.ctrlKey = containsCtrl(keys);
		this.altKey = containsAlt(keys);
		this.metaKey = containsMeta(keys); //Mac's command key

		function containsShift(keys) {
			return haveCommonValue(keys, MODIFIER_KEYS_CODE_MAP[16]);
		}

		function containsCtrl(keys) {
			return haveCommonValue(keys, MODIFIER_KEYS_CODE_MAP[17]);
		}

		function containsAlt(keys) {
			return haveCommonValue(keys, MODIFIER_KEYS_CODE_MAP[18]);
		}

		function containsMeta(keys) {
			return haveCommonValue(keys, MODIFIER_KEYS_CODE_MAP[91]);
		}

		function haveCommonValue(array1, array2) {
			for (var key in array1) if (array1.hasOwnProperty(key)) {
				if (array2.indexOf(array1[key]) !== -1) return true;
			}
			return false;
		}
	}

	/**
	 * メインキーが指定されたキーコードと一致するかどうか判定する。
	 * @param keyCode
	 * @returns {boolean}
	 */
	KeyCombinationInfo.prototype.matchKeyByKeyCode = function(keyCode) {
		var keyName = convertKeyCodeToKeyName(keyCode);
		return keyName === this.keyName || (keyName instanceof Array && keyName.indexOf(this.keyName) !== -1)
	};

	/**
	 * イベントが一致するかどうかを判定する。
	 * @param event
	 * @returns {boolean}
	 */
	KeyCombinationInfo.prototype.matchWithEvent = function(event) {
		var keyCode = extractKeyCodeInEvent(event);
		return this.matchKeyByKeyCode(keyCode)
			&& event.ctrlKey === this.ctrlKey
			&& event.shiftKey === this.shiftKey
			&& event.altKey === this.altKey
			&& (event.metaKey === undefined || event.metaKey === this.metaKey)
	};

	//コールバック関数の引数にするための、指定されたままの(toLowerCaseされていない)状態のブラウザ名とOS名。
	var originalBrowserName;
	var originalOsName;

	/**
	 * メイン関数
	 * 指定されたショートカットキーの動作を抑制するイベントリスナーを、ウィンドウに追加する。
	 */
	!function() {
		var nonParsedBlackList = selectBlackListForThisEnvironment(shortcutBlackList);
		
		if (!nonParsedBlackList || nonParsedBlackList.length === 0) return;

		var parsedBlackList = new ShortcutBlackList(nonParsedBlackList);
		var eventListener = function(event) {
			var e = event || window.event;
			//例外的なパターンの場合、抑制しない。テキストエリア内でのBackspaceなど。
			if (isExceptionalPattern(e)) return;

			var keyInfo = parsedBlackList.findKeyInfoByEvent(e);
			if (keyInfo == null) return;

			if (callback) {
				var isCanceled = callback(originalBrowserName, keyInfo.original, e) === false;
				if (isCanceled) return;
			}
			if (e.preventDefault) e.preventDefault();
			if (e.stopPropagation) e.stopPropagation();
			e.returnValue = false;
			if (window.event) {
				window.event.keyCode = 37; //It's dark magic. In IE it required to disable Alt+C, Alt+F4 or any other key combinations.
				window.event.returnValue = false;
				if (window.event.preventDefault) window.event.preventDefault();
				if (window.event.stopPropagation) window.event.stopPropagation();
			}
			return false;
		};

		if (window.addEventListener) window.addEventListener('keydown', eventListener, false);
		else if (window.attachEvent) window.attachEvent('onkeydown', eventListener);
		else window['onkeydown'] = eventListener;

		//IEのF1対応 F1を押すと、onkeydownとは別にonhelpイベントが走る。
		if (getBrowserName().indexOf('ie') !== -1 && (nonParsedBlackList.indexOf('F1') !== -1 || nonParsedBlackList.indexOf('f1') !== -1)) {
			window.document.onhelp = function() {
				window.event.returnValue = false;
				return false;
			};
		}

	}();

	/**
	 * パース前のショートカットキーのリストから、
	 * 実行中の環境のリストを選び出す。
	 * @param shortcutsLists
	 * @returns {*}
	 */
	function selectBlackListForThisEnvironment(shortcutsLists) {
		var blackListForThisOs = selectBlackListForThisOs(shortcutsLists);
		var browser = getBrowserName();
		return selectBlackListForThisBrowser(blackListForThisOs, browser);
	}

	/**
	 * パース前のショートカットキー抑制対象のリストから、実行環境のOS用の情報を取り出す。
	 * @param shortcutsLists
	 * @returns {*}
	 */
	function selectBlackListForThisOs(shortcutsLists) {
		var osNamePatterns;
		if (isWindows()) osNamePatterns = ['win', 'windows'];
		else if (isMac()) osNamePatterns = ['mac', 'macintosh'];
		else return null;

		for (var key in shortcutsLists) if (shortcutsLists.hasOwnProperty(key)) {
			if (osNamePatterns.indexOf(key.toLowerCase()) !== -1) {
				originalOsName = key;
				return shortcutsLists[key];
			}
		}
	}

	/**
	 * パース前のショートカットキー抑制対象のリストから、実行環境のブラウザ用の情報を取り出す。
	 * @param shortcutsLists
	 * @param browser
	 * @returns {*}
	 */
	function selectBlackListForThisBrowser(shortcutsLists, browser) {
		for (var key in shortcutsLists) if (shortcutsLists.hasOwnProperty(key)) {
			if ((key + "").toLowerCase() === browser) {
				originalBrowserName = key;
				return shortcutsLists[key];
			}
		}
		return null;
	}

	/**
	 * 実行環境がWindowsかどうかを判定
	 * @returns {boolean}
	 */
	function isWindows() {
		return window.navigator.userAgent.indexOf('Win') !== -1;
	}

	/**
	 * 実行環境がMacかどうかを判定
	 * @returns {boolean}
	 */
	function isMac() {
		return window.navigator.userAgent.indexOf('Mac') != -1;
	}

	/**
	 * イベントが抑制対象とすべきでないパターンかどうかを判定する。
	 * 例：入力フォーム内でのBackspace
	 * @param event
	 * @returns {boolean}
	 */
	function isExceptionalPattern(event) {
		var keyCode = extractKeyCodeInEvent(event);
		var keyName = convertKeyCodeToKeyName(keyCode);
		var target = getEventTargetElement(event);
		if (keyName.indexOf('backspace') !== -1) {
			if (target.tagName === 'TEXTAREA' && target.readOnly == false) return true;
			//INPUTタグ内で入力された時は、テキスト入力可能なタグでのみBackSpaceを有効にする(buttonなどは抑止)。
			if (target.tagName === 'INPUT' && target.readOnly == false && BACKSPACE_EXCEPTIONAL_INPUT_TYPE.indexOf(target.type) !== -1) return true;
		} else if (keyName.indexOf('enter') !== -1) {
			if (target.tagName === 'TEXTAREA' && target.readOnly == false) return true;
		}
		return false;
	}

	/**
	 * イベントオブジェクトからキーコードを抽出する。
	 * @param event
	 * @returns {*}
	 */
	function extractKeyCodeInEvent(event) {
		if (event.keyCode) return event.keyCode;
		else if (event.which) return event.which;
	}

	/**
	 * イベントのターゲット要素を取得する。
	 * @param event
	 * @returns {string}
	 */
	function getEventTargetElement(event) {
		var element;
		if (event.target) element = event.target;
		else if (event.srcElement) element = event.srcElement;
		if (element.nodeType == 3) element = element.parentNode;
		return element;
	}

	/**
	 * キーコードを対応するキー名に変換する。
	 * @param keyCode
	 * @returns {string}
	 */
	function convertKeyCodeToKeyName(keyCode) {
		if (NON_ALPHABETIC_KEYS_CODE_MAP[keyCode]) return NON_ALPHABETIC_KEYS_CODE_MAP[keyCode];
		if (MODIFIER_KEYS_CODE_MAP[keyCode]) return MODIFIER_KEYS_CODE_MAP[keyCode];
		return String.fromCharCode(keyCode).toLowerCase();
	}
}
