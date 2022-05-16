// move from MAA0110 to MCA0110 when user click on register button
function RegisterAccount() {
  window.location.href = "MCA0110.html";
}

// move from MCA0110 to MAA0110 when register successfully
function SubmitAccount() {
  window.location.href = "MAA0110.html";
}

// move from MAA0110 to SBA0110 when login the first time
function Login() {
  window.location.href = "SBA0110.html";
}

// move from SBA0110 to MBA0110 after change password
function Changepass() {
  window.location.href = "MBA0110.html";
}

// move from to MBA0110 to SBA0110 to change password
function ChangePassAtMenuPage() {
  window.location.href = "SBA0110.html";
}

// move from MBA0110 to MEA0110 to show the report list
function ShowReportList() {
  window.location.href = "MEA0110.html";
}

// move from to MBA0110 to SBA0110 to change password
function ChangePassAtMenuPage() {
  window.location.href = "SBA0110.html";
}
// move from MBA0110 to SBA0110 to change password
function ChangePassAtMenuPage() {
  window.location.href = "SBA0110.html";
}

// move from MBA0110 to MDA0110
function NotificationRegistration() {
  window.location.href = "MDA0110.html";
}
// move from MDA0110 to MBA0110
function Register() {
  window.location.href = "MBA0110.html";
}

//move back to GJA0110
function RegisterForCity() {
  var domain = window.location.href;
  var res = domain.split("/");
  var url = res[res.length - 1];
  switch (url) {
    case 'GFA0110.html':
      window.open("RP030.html");
      break;
    case 'GGA0110.html':
      window.open("RP040.html");
      break;
    case 'GIA0110.html':
      window.open("RP060.html");
      break;
  }
  window.location.href = "GJA0110.html";
}

function search() {
  window.location.href = "GJA0120.html";
}

function progressRecord() {
  window.location.href = "GEA0110.html";
}

function notificationAcceptance() {
  window.location.href = "GDA0110.html";
}

function progressRecordRegister() {
  window.location.href = "GEB0110.html";
}

function registration() {
  window.location.href = "GEA0110.html";
}

function backtoMenu() {
  window.location.href = "GEA0110.html";
}

function move(){
  window.location.href = "MDA0110.html";

}

function goBack() {
  window.history.back();
}
$("table").on("click", ".btn-show-detail", function(event){
  var buttonName = event.target.textContent; 
  if('詳細表示' === buttonName){
    window.location.href = "GEB0110.html";
  }else if('帳票表示' === buttonName){
    var $row = $(this).closest("tr");    // Find the row
    var $text = $row.find(".detail").text(); // Find the text
    switch($text){
      case '届出受理通知をメールで通知しました。':
        window.open('RP020.html');
        break;
      case '変更届出を登録しました。':
          window.open('RP010.html');
          break;
      case '督促通知を登録しました。（2回目以降）':
          window.open('RP060.html');
          break;
      case '督促通知を登録しました。（1回目）':
          window.open('RP050.html');
          break;
      case '現地調査通知を登録しました。（通知方法：郵送）':
          window.open('RP040.html');
          break;
      case '助言通知を登録しました。（通知方法：郵送）':
          window.open('RP030.html');
          break;
      case '届出を登録しました。':
          window.open('RP010.html');
          break;
    }
  }
})