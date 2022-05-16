
const CONFIRM_MESSAGE = 'C0003';

const ACTIVE_LOGIN = 'ユーザを有効化';

const RESUME_ACTIVE = 'ユーザを利用再開';

const SUSPENSION_ACTIVE = 'ユーザを利用停止';

function getMessage(key, argument) {
    var str = message[key];
    if (Array.isArray(argument)) {
        for (var k in argument) {
            str = str.replace("{" + k + "}", argument[k]);
        }
        return str;
    } else {
        return str.replace("{0}", argument);
    }
}

function previousScreen() {
    $('#submitRedirect').attr('action', baseUrl + '/GJA0110/return')
    $('#submitRedirect').submit();
}

$(function () {
    $("#b0").click(function (event) {
        scrollingTable("#a0", event);
    });
    $("#b1").click(function (event) {
        scrollingTable("#a1", event);
    });
    $("#b11").click(function (event) {
        scrollingTable("#a11", event);
    });
    $("#b12").click(function (event) {
        scrollingTable("#a12", event);
    });
});

function submitNotificationRegistration(btnNotificationActive) {
    $('#submitRedirect').attr('action',  baseUrl + '/MDA0110/noti-regis');
    var activeBtn = btnNotificationActive ? '1' : '0';
    $('input[name=activeBtn]').val(activeBtn);
    $('#submitRedirect').submit();
}

function submitChangeNotification(btnNotificationChangeActive) {
    $('#submitRedirect').attr('action', baseUrl +  '/MDA0110/change-noti');
    var activeBtn = btnNotificationChangeActive ? '2' : '0';
    $('input[name=activeBtn]').val(activeBtn);
    $('#submitRedirect').submit();
}

function submitAdviceNotificationRegistration() {
    $('#submitRedirect').attr('action', baseUrl +  '/GFA0110/advice-noti-regis')
    $('#submitRedirect').submit();
}

function submitSupervisedNoticeRegistration() {
    $('#submitRedirect').attr('action',  baseUrl + '/GIA0110/supervised-noti')
    $('#submitRedirect').submit();
}

function submitFormGGA0110() {
    $('#submitRedirect').attr('action',  baseUrl + '/GGA0110')
    $('#submitRedirect').submit();
}

function submitFormGLA0110() {
    $('#submitRedirect').attr('action',  baseUrl + '/GLA0110/show')
    $('#submitRedirect').submit();
}

function submitEnableLoginID(event) {
    $("#div3")
        .html(getMessage(CONFIRM_MESSAGE, ACTIVE_LOGIN))
        .dialog({
            modal: true, // モーダル表示
            title: dialog.title,// タイトル
            buttons: { // ボタン
                "OK": function () {
                    $(this).dialog("close");
                    $('#submitRedirect').attr('action',  baseUrl + '/GJA0120/active-idlogin')
                    $('#submitRedirect').submit();
                },
                "キャンセル": function () {
                    $(this).dialog("close");
                    event.preventdefault();
                }
            },
        });

}

function submitResumingUse(event) {
    $("#div3")
        .html(getMessage(CONFIRM_MESSAGE, RESUME_ACTIVE))
        .dialog({
            modal: true, // モーダル表示
            title: dialog.title,// タイトル
            buttons: { // ボタン
                "OK": function () {
                    $(this).dialog("close");
                    $('#submitRedirect').attr('action', baseUrl +  '/GJA0120/resuming-use')
                    $('#submitRedirect').submit();
                },
                "キャンセル": function () {
                    $(this).dialog("close");
                    event.preventdefault();
                }
            },
        });
}

function submitUseStop(event) {
    $("#div3")
        .html(getMessage(CONFIRM_MESSAGE, SUSPENSION_ACTIVE))
        .dialog({
            modal: true, // モーダル表示
            title: dialog.title,// タイトル
            buttons: { // ボタン
                "OK": function () {
                    $(this).dialog("close");
                    $('#submitRedirect').attr('action', baseUrl +  '/GJA0120/use-stop')
                    $('#submitRedirect').submit();
                },
                "キャンセル": function () {
                    $(this).dialog("close");
                    event.preventdefault();
                }
            },
        });
}