const _FORMAT_DATETIME = 'E0008';
const _LABEL_CAL_NOTICE_DATE = "通知年月日";
const _LABEL_CAL_BUILD_DATE = "新築年月日";
const _LABEL_CAL_NOTIFICATION_DATE = "届出年月日";

const _rdoLostElseReasonCode = $("input[name='infoAreaCommon.rdoLostElseReasonCode']");
const _rdoChangeReason = $("input[name='infoAreaCommon.rdoChangeReason']");
const _rdoGroupYesno = $("input[name='infoAreaCommon.rdoGroupYesno']");
const _rdoGroupForm = $("input[name='infoAreaCommon.rdoGroupForm']");
const _rdoLandRights = $("input[name='infoAreaCommon.rdoLandRights']");
const _rdoUseFor = $("input[name='infoAreaCommon.rdoUsefor']");
const _rdoManagementForm = $("input[name='infoAreaCommon.rdoManagementForm']");
const _rdoRule = $("input[name='infoAreaCommon.rdoRule']");
const _rdoOneYearOver = $("input[name='infoAreaCommon.rdoOneyearOver']");
const _rdoRepairCost = $("input[name='infoAreaCommon.rdoRepairCost']");
const _rdoRepairPlan = $("input[name='infoAreaCommon.rdoRepairPlan']");
const _rdoLongRepairPlan = $("input[name='infoAreaCommon.rdoLongRepairPlan']");
const _rdoSeismicDiagnosis = $("input[name='infoAreaCommon.rdoSeismicDiagnosis']");
const _rdoContactProperty = $("input[name='infoAreaCommon.rdoContactProperty']");
const _calNoticeDate = $("input[name='calNoticeDate']");
const _calBuiltDate = $("input[name='infoAreaCommon.calBuiltDate']");
const _calNotificationDate = $("input[name='infoAreaCommon.calNotificationDate']");
const _inActiveItem = $("input[name='infoAreaCommon.inActiveItem']");

const _JP_NAME_CALNOTICE_DATE = "通知年月日";
const _JP_NAME_CALBUILD_DATE = "新築年月日";
const _JP_NAME_CALNOTIFICATION_DATE = "届出年月日";

/* Radio */
var radio1 = [0];
var radio2 = [0];
var radio3 = [0];
var radio4 = [0];
var radio5 = [0];
var radio6 = [0];
var radio7 = [0];
var radio8 = [0];

const RDO_GROUP = "管理組合";
const RDO_MANAGER = "管理者等";
const RDO_RULER = "管理規約";
const RDO_ONEYEAROVER = "年1回以上の開催";
const RDO_MANAGERMENTCOST = "管理費";
const RDO_REPAITCOST = "修繕積立金";
const RDO_REPAITPALN = "修繕の計画的な実施（大規模な修繕工事）";
const RDO_CONTACTPROPERTY = "連絡先属性";

function showHideLostBuildingBlock() {
    const reason = $("input[name='infoAreaCommon.rdoChangeReason']:checked").val();
    const lostElseReasonBlock = $("#lostElseReasonBlock");
    const rdoLostElseReasonCode = $("input[name='infoAreaCommon.rdoLostElseReasonCode']");
    if (reason === "1") {
        rdoLostElseReasonCode.parent().addClass("disable-item");
        rdoLostElseReasonCode.prop("checked", false);
    } else {
        rdoLostElseReasonCode.parent().removeClass("disable-item");
    }
    showHideLostBuildingReasonBlock();
}

function showHideLostBuildingReasonBlock() {
    const reason = $("input[name='infoAreaCommon.rdoLostElseReasonCode']:checked").val();
    const txtLostElseReasonElse = $("input[name='infoAreaCommon.txtLostElseReasonElse']");
    if (reason === "9") {
        txtLostElseReasonElse.removeClass("disable-item");
    } else {
        txtLostElseReasonElse.addClass("disable-item");
        txtLostElseReasonElse.val("");
    }
}

function showHideManagementFormBlock() {
    const managementFormCode = $("input[name='infoAreaCommon.rdoGroupYesno']:checked").val();
    const txtApartmentNumber = $("input[name='infoAreaCommon.txtApartmentNumber']");
    if (managementFormCode === "1") {
        txtApartmentNumber.removeClass("disable-item");
        _rdoGroupForm.parent().removeClass("disable-item");
    } else {
        $("input[name='infoAreaCommon.rdoGroupForm'][value='9']").prop('checked', true);
        _rdoGroupForm.parent().addClass("disable-item");

        txtApartmentNumber.addClass("disable-item");
        txtApartmentNumber.val("");
    }
    showHideManagementFormReasonBlock();
}

function showHideManagementFormReasonBlock() {
    const code = $("input[name='infoAreaCommon.rdoGroupForm']:checked").val();
    const txtElement = $("input[name='infoAreaCommon.txtGroupFormElse']");
    if (code === "8") {
        txtElement.removeClass("disable-item");
    } else {
        txtElement.addClass("disable-item");
        txtElement.val("");
    }
}

function showHideLandRightsTxt() {
    const code = $("input[name='infoAreaCommon.rdoLandRights']:checked").val();
    const element = $("input[name='infoAreaCommon.txtLandRightsElse']");
    if (code === "8") {
        element.removeClass("disable-item");
    } else {
        element.addClass("disable-item");
        element.val("");
    }
}

function showHideAttachedUse() {
    const code = $("input[name='infoAreaCommon.rdoUsefor']:checked").val();
    const element = $("input[name='infoAreaCommon.txtUseforElse']");
    if (code === "8") {
        element.removeClass("disable-item");
    } else {
        element.addClass("disable-item");
        element.val("");
    }
}

/*
When the management form (rdoManagementForm) is other than this, this item is inactive and blank is set.
This item is active when the management form (rdoManagementForm) is other
管理形態（rdoManagementForm）がその他以外の場合、本項目は非活性し、空白を設定する
管理形態（rdoManagementForm）がその他の場合、本項目は活性
 */
function showHideManagementWay() {
    const code = $("input[name='infoAreaCommon.rdoManagementForm']:checked").val();
    const element = $("input[name='infoAreaCommon.txtManagementFormElse']");
    if (code === "8") {
        element.removeClass("disable-item");
    } else {
        element.addClass("disable-item");
        element.val("");
    }
    showHideCompanyManagement(code);
}

/*
If the management form (rdoManagementForm) is not outsourced / outsourced, this item is inactive and blank
This item is active when the management form (rdoManagementForm) is outsourced / partially outsourced
管理形態（rdoManagementForm）が全部委託/一部委託以外の場合、本項目は非活性し、空白を設定する
管理形態（rdoManagementForm）が全部委託/一部委託の場合、本項目は活性
 */
function showHideCompanyManagement(code) {
    const txtManagerName = $("input[name='infoAreaCommon.txtManagerName']");
    const txtManagerNamePhonetic = $("input[name='infoAreaCommon.txtManagerNamePhonetic']");
    const txtManagerZipCode1 = $("input[name='infoAreaCommon.txtManagerZipCode1']");
    const txtManagerZipCode2 = $("input[name='infoAreaCommon.txtManagerZipCode2']");
    const txtManagerAddress = $("input[name='infoAreaCommon.txtManagerAddress']");
    const txtManagerTelno1 = $("input[name='infoAreaCommon.txtManagerTelno1']");
    const txtManagerTelno2 = $("input[name='infoAreaCommon.txtManagerTelno2']");
    const txtManagerTelno3 = $("input[name='infoAreaCommon.txtManagerTelno3']");
    const validManagerZipCode = $("#validManagerZipCode");

    if (code === "1" || code === "2") {
        txtManagerName.removeClass("disable-item");
        txtManagerNamePhonetic.removeClass("disable-item");
        txtManagerZipCode1.removeClass("disable-item");
        txtManagerZipCode2.removeClass("disable-item");
        txtManagerAddress.removeClass("disable-item");
        txtManagerTelno1.removeClass("disable-item");
        txtManagerTelno2.removeClass("disable-item");
        txtManagerTelno3.removeClass("disable-item");
        validManagerZipCode.removeClass("disable-item");
    } else {
        txtManagerName.addClass("disable-item");
        txtManagerNamePhonetic.addClass("disable-item");
        txtManagerZipCode1.addClass("disable-item");
        txtManagerZipCode2.addClass("disable-item");
        txtManagerAddress.addClass("disable-item");
        txtManagerTelno1.addClass("disable-item");
        txtManagerTelno2.addClass("disable-item");
        txtManagerTelno3.addClass("disable-item");

        txtManagerName.val("");
        txtManagerNamePhonetic.val("");
        txtManagerZipCode1.val("");
        txtManagerZipCode2.val("");
        txtManagerAddress.val("");
        txtManagerTelno1.val("");
        txtManagerTelno2.val("");
        txtManagerTelno3.val("");

        validManagerZipCode.addClass("disable-item");
    }
}

/*
If there is a management rule (rdoRule), activate this item
If there is no management rule (rdoRule), this item is inactive and set blank
管理規約（rdoRule）があるの場合、本項目活性
管理規約（rdoRule）がなしの場合、本項目は非活性し、空白を設定する
 */
function showHideManagementAgreement() {
    const code = $("input[name='infoAreaCommon.rdoRule']:checked").val();
    const element = $("input[name='infoAreaCommon.txtRuleChangeYear']");
    if (code === "1") {
        element.removeClass("disable-item");
    } else {
        element.addClass("disable-item");
        element.val("");
    }
}

/*
If there is more than once a year (rdoOneyearOver), this item activity
If there is no more than once a year (rdoOneyearOver), this item is inactive and unselected
年1回以上の開催（rdoOneyearOver）があるの場合、本項目活性
年1回以上の開催（rdoOneyearOver）がなしの場合、本項目は非活性し、未選択とする
 */
function showHideMeetingOnceAYear() {
    const code = $("input[name='infoAreaCommon.rdoOneyearOver']:checked").val();
    const element = $("input[name='infoAreaCommon.rdoMinutes']");
    if (code === "1") {
        element.parent().removeClass("disable-item");
    } else {
        element.parent().addClass("disable-item");
        element.prop("checked", false);
    }
}

//修繕積立金
function showHideRepairReserveFund() {
    const code = $("input[name='infoAreaCommon.rdoRepairCost']:checked").val();
    const element = $("input[name='infoAreaCommon.txtRepairMonthlycost']");
    if (code === "1") {
        element.removeClass("disable-item");
    } else {
        element.addClass("disable-item");
        element.val("");
    }
}

//修繕の計画的な実施（大規模な修繕工事）
function showHideSystematicImplementationRepairs() {
    const code = $("input[name='infoAreaCommon.rdoRepairPlan']:checked").val();
    const element = $("input[name='infoAreaCommon.txtRepairNearestYear']");
    if (code === "1") {
        element.removeClass("disable-item");
    } else {
        element.addClass("disable-item");
        element.val("");
    }
}

//長期修繕計画
function showHideRepairPlan() {
    const code = $("input[name='infoAreaCommon.rdoLongRepairPlan']:checked").val();
    const txtLongRepairPlanYear = $("input[name='infoAreaCommon.txtLongRepairPlanYear']");
    const txtLongRepairPlanPeriod = $("input[name='infoAreaCommon.txtLongRepairPlanPeriod']");
    const txtLongRepairPlanYearFrom = $("input[name='infoAreaCommon.txtLongRepairPlanYearFrom']");
    const txtLongRepairPlanYearTo = $("input[name='infoAreaCommon.txtLongRepairPlanYearTo']");

    if (code === "1") {
        txtLongRepairPlanYear.removeClass("disable-item");
        txtLongRepairPlanPeriod.removeClass("disable-item");
        txtLongRepairPlanYearFrom.removeClass("disable-item");
        txtLongRepairPlanYearTo.removeClass("disable-item");
    } else {
        txtLongRepairPlanYear.addClass("disable-item");
        txtLongRepairPlanPeriod.addClass("disable-item");
        txtLongRepairPlanYearFrom.addClass("disable-item");
        txtLongRepairPlanYearTo.addClass("disable-item");

        txtLongRepairPlanYear.val("");
        txtLongRepairPlanPeriod.val("");
        txtLongRepairPlanYearFrom.val("");
        txtLongRepairPlanYearTo.val("");
    }
}

//耐震性
function showHideEarthquakeResistance() {
    const code = $("input[name='infoAreaCommon.rdoSeismicDiagnosis']:checked").val();
    const element = $("input[name='infoAreaCommon.rdoEarthquakeResistance']");

    if (code === "1") {
        element.parent().removeClass("disable-item");
    } else {
        element.parent().addClass("disable-item");
        $("input[name='infoAreaCommon.rdoEarthquakeResistance'][value='9']").prop("checked", true);
    }
}

//連絡窓口:属性 - 連絡先属性その他
function showHideContactAttribute() {
    const code = $("input[name='infoAreaCommon.rdoContactProperty']:checked").val();
    const element = $("input[name='infoAreaCommon.txtContactPropertyElse']");
    if (code === "9") {
        element.removeClass("disable-item");
    } else {
        element.addClass("disable-item");
        element.val("");
    }
}

function showHideCommonArea() {
    if (_inActiveItem.val() !== "true") {
        showHideLostBuildingBlock();
        showHideLostBuildingReasonBlock();
        showHideManagementFormBlock();
        showHideManagementFormReasonBlock();
        showHideLandRightsTxt();
        showHideAttachedUse();
        showHideManagementWay();
        showHideManagementAgreement();
        showHideMeetingOnceAYear();
        showHideRepairReserveFund();
        showHideSystematicImplementationRepairs();
        showHideRepairPlan();
        showHideEarthquakeResistance();
        showHideContactAttribute();
    }
}

/**
 * Append tap index
 */
function appendTapIndex(){
    /* 届出基本情報 */
    $("#btn-table-2").attr("tabindex", function(index, attr) { return index + 100;});
    $("input[name='basicReportInfo.txtApartmentName']").attr("tabindex", function(index, attr) { return index + 101;});
    $("input[name='basicReportInfo.txtApartmentNamePhonetic']").attr("tabindex", function(index, attr) { return index + 102;});
    $("#txtApartmentZipCode1").attr("tabindex", "103");
    $("#txtApartmentZipCode2").attr("tabindex", "104");
    $("#validApartmentZipCode").attr("tabindex", "105");
    $("#lblApartmentAddress1").attr("tabindex", "106");
    $("#txtApartmentAddress2").attr("tabindex", "107");
    
    /*届出情報*/
    $("#btn-table-3").attr("tabindex", "200");
    $("#datepicker-1").attr("tabindex", "201");
    $("input[name='infoAreaCommon.txtNotificationGroupName']").attr("tabindex", function(index, attr) { return index + 202;});
    $("input[name='infoAreaCommon.txtNotificationPersonName']").attr("tabindex", function(index, attr) { return index + 203;});
    $("#radio-1").parent().attr("tabindex", "204");
    $("#radio-2").parent().attr("tabindex", "205");
    $("#radio-3").parent().attr("tabindex", "206");
    $("#radio-4").parent().attr("tabindex", "207");
    $("#r-2").parent().attr("tabindex", "208");
    $("#r-3").parent().attr("tabindex", "209");
    $("#r-4").parent().attr("tabindex", "210");
    $("input[name='infoAreaCommon.txtLostElseReasonElse']").attr("tabindex", function(index, attr) { return index + 211;});
    
    /* マンションの概要 */
    $("#btn-table-4").attr("tabindex", "301");
    $("#r-5").parent().attr("tabindex", "302");
    $("#r-6").parent().attr("tabindex", "303");
    $("#r-7").parent().attr("tabindex", "304");
    $("input[name = 'infoAreaCommon.txtApartmentNumber']").attr("tabindex", "305");
    $("#r-8").parent().attr("tabindex", "306");
    $("#r-9").parent().attr("tabindex", "307");
    $("#r-10").parent().attr("tabindex", "309");
    $("#r-11").parent().attr("tabindex", "308");
    $("input[name='infoAreaCommon.txtGroupFormElse']").attr("tabindex", function(index, attr) { return index + 310;});
    $("input[name='infoAreaCommon.txtHouseNumber']").attr("tabindex", function(index, attr) { return index + 311;});
    $("input[name='infoAreaCommon.txtFloorNumber']").attr("tabindex", function(index, attr) { return index + 312;});
    $("input[name='infoAreaCommon.calBuiltDate']").attr("tabindex", function(index, attr) { return index + 313;});
    $("#r-12").parent().attr("tabindex", "314");
    $("#r-13").parent().attr("tabindex", "315");
    $("#r-14").parent().attr("tabindex", "316");
    $("#r-16").parent().attr("tabindex", "317");
    $("#r-15").parent().attr("tabindex", "318");
    $("input[name='infoAreaCommon.txtLandRightsElse']").attr("tabindex", function(index, attr) { return index + 319;});
    $("#r-17").parent().attr("tabindex", "320");
    $("#r-18").parent().attr("tabindex", "321");
    $("#r-19").parent().attr("tabindex", "322");
    $("#r-20").parent().attr("tabindex", "324");
    $("#r-21").parent().attr("tabindex", "323");
    $("input[name='infoAreaCommon.txtUseforElse']").attr("tabindex", function(index, attr) { return index + 325;});
    $("#r-22").parent().attr("tabindex", "326");
    $("#r-23").parent().attr("tabindex", "327");
    $("#r-24").parent().attr("tabindex", "328");
    $("#r-25").parent().attr("tabindex", "330");
    $("#r-26").parent().attr("tabindex", "329");
    $("input[name='infoAreaCommon.txtManagementFormElse']").attr("tabindex", function(index, attr) { return index + 331;});
    $("input[name='infoAreaCommon.txtManagerName']").attr("tabindex", function(index, attr) { return index + 332;});
    $("input[name='infoAreaCommon.txtManagerNamePhonetic']").attr("tabindex", function(index, attr) { return index + 333;});
    $("input[name='infoAreaCommon.txtManagerZipCode1']").attr("tabindex", function(index, attr) { return index + 334;});
    $("input[name='infoAreaCommon.txtManagerZipCode2']").attr("tabindex", function(index, attr) { return index + 335;});
    $("#validManagerZipCode").attr("tabindex", "336");
    $("#lblManagerAddress1").attr("tabindex", "337");
    $("input[name='infoAreaCommon.txtManagerTelno1']").attr("tabindex", function(index, attr) { return index + 338;});
    $("input[name='infoAreaCommon.txtManagerTelno2']").attr("tabindex", function(index, attr) { return index + 339;});
    $("input[name='infoAreaCommon.txtManagerTelno3']").attr("tabindex", function(index, attr) { return index + 340;});
    
    /*管理不全を予防するための必須事項*/
    $("#btn-table-5").attr("tabindex", "441");
    $("#r-27").parent().attr("tabindex", "442");
    $("#r-28").parent().attr("tabindex", "443");
    $("#r-29").parent().attr("tabindex", "444");
    $("#r-30").parent().attr("tabindex", "445");
    $("#r-31").parent().attr("tabindex", "446");
    $("#r-32").parent().attr("tabindex", "447");
    $("input[name='infoAreaCommon.txtRuleChangeYear']").attr("tabindex", function(index, attr) { return index + 448;});
    $("#r-33").parent().attr("tabindex", "449");
    $("#r-34").parent().attr("tabindex", "450");
    $("#r-35").parent().attr("tabindex", "451");
    $("#r-36").parent().attr("tabindex", "452");
    $("#r-37").parent().attr("tabindex", "453");
    $("#r-38").parent().attr("tabindex", "454");
    $("#r-39").parent().attr("tabindex", "455");
    $("#r-40").parent().attr("tabindex", "456");
    $("input[name='infoAreaCommon.txtRepairMonthlycost']").attr("tabindex", function(index, attr) { return index + 457;});
    $("#r-41").parent().attr("tabindex", "458");
    $("#r-42").parent().attr("tabindex", "459");
    $("input[name='infoAreaCommon.txtRepairNearestYear']").attr("tabindex", function(index, attr) { return index + 460;});
    
    /*適正な維持管理に関する事項*/
    $("#btn-table-6").attr("tabindex", "500");
    $("#r-43").parent().attr("tabindex", "501");
    $("#r-44").parent().attr("tabindex", "502");
    $("#r-45").parent().attr("tabindex", "503");
    $("input[name='infoAreaCommon.txtLongRepairPlanYear']").attr("tabindex", function(index, attr) { return index + 504;});
    $("input[name='infoAreaCommon.txtLongRepairPlanPeriod']").attr("tabindex", function(index, attr) { return index + 505;});
    $("input[name='infoAreaCommon.txtLongRepairPlanYearFrom']").attr("tabindex", function(index, attr) { return index + 506;});
    $("input[name='infoAreaCommon.txtLongRepairPlanYearTo']").attr("tabindex", function(index, attr) { return index + 507;});
    $("#r-46").parent().attr("tabindex", "508");
    $("#r-47").parent().attr("tabindex", "509");
    $("#r-48").parent().attr("tabindex", "510");
    $("#r-49").parent().attr("tabindex", "511");
    $("#r-50").parent().attr("tabindex", "512");
    $("#r-51").parent().attr("tabindex", "513");
    $("#r-52").parent().attr("tabindex", "515");
    $("#r-53").parent().attr("tabindex", "516");
    $("#r-54").parent().attr("tabindex", "517");
    $("#r-55").parent().attr("tabindex", "518");
    $("#r-56").parent().attr("tabindex", "519");
    $("#r-57").parent().attr("tabindex", "520");
    $("#r-58").parent().attr("tabindex", "521");
    $("#r-59").parent().attr("tabindex", "514");
    $("input[name='infoAreaCommon.txtEmptyNumber']").attr("tabindex", function(index, attr) { return index + 522;});
    $("#r-130").parent().attr("tabindex", "524");
    $("#r-131").parent().attr("tabindex", "525");
    $("#r-132").parent().attr("tabindex", "526");
    $("#r-133").parent().attr("tabindex", "527");
    $("#r-134").parent().attr("tabindex", "528");
    $("#r-135").parent().attr("tabindex", "529");
    $("#r-136").parent().attr("tabindex", "523");
    $("input[name='infoAreaCommon.txtRentalNumber']").attr("tabindex", function(index, attr) { return index + 530;});
    $("#r-66").parent().attr("tabindex", "531");
    $("#r-67").parent().attr("tabindex", "532");
    $("#r-68").parent().attr("tabindex", "533");
    $("#r-69").parent().attr("tabindex", "534");
    $("#r-70").parent().attr("tabindex", "535");
    $("#r-71").parent().attr("tabindex", "537");
    $("#r-72").parent().attr("tabindex", "538");
    $("#r-73").parent().attr("tabindex", "539");
    $("#r-74").parent().attr("tabindex", "540");
    $("#r-75").parent().attr("tabindex", "541");
    $("#r-76").parent().attr("tabindex", "542");
    $("#r-77").parent().attr("tabindex", "543");
    $("#r-78").parent().attr("tabindex", "544");
    $("#r-79").parent().attr("tabindex", "545");
    $("#r-80").parent().attr("tabindex", "546");
    
    /*マンションの社会的機能の向上に資する取組に関する事*/
    $("#btn-table-7").attr("tabindex", "600");
    $("#r-81").parent().attr("tabindex", "601");
    $("#r-82").parent().attr("tabindex", "602");
    $("#r-83").parent().attr("tabindex", "603");
    $("#r-84").parent().attr("tabindex", "604");
    $("#r-85").parent().attr("tabindex", "605");
    $("#r-86").parent().attr("tabindex", "606");
    $("#r-87").parent().attr("tabindex", "607");
    $("#r-88").parent().attr("tabindex", "608");
    $("#r-89").parent().attr("tabindex", "609");
    $("#r-90").parent().attr("tabindex", "610");
    $("#r-91").parent().attr("tabindex", "611");
    $("#r-92").parent().attr("tabindex", "612");
    $("#r-93").parent().attr("tabindex", "613");
    $("#r-94").parent().attr("tabindex", "614");
    $("#r-95").parent().attr("tabindex", "615");
    $("#r-96").parent().attr("tabindex", "616");
    $("#r-97").parent().attr("tabindex", "617");
    $("#r-98").parent().attr("tabindex", "618");
    $("#r-99").parent().attr("tabindex", "619");
    $("#r-100").parent().attr("tabindex", "620");
    $("#r-101").parent().attr("tabindex", "621");
    $("#r-102").parent().attr("tabindex", "622");
    $("#r-103").parent().attr("tabindex", "623");
    $("#r-104").parent().attr("tabindex", "624");
    $("#r-105").parent().attr("tabindex", "625");
    $("#r-106").parent().attr("tabindex", "626");
    $("#r-107").parent().attr("tabindex", "627");
    $("#r-108").parent().attr("tabindex", "628");
    $("#r-109").parent().attr("tabindex", "629");
    $("#r-110").parent().attr("tabindex", "630");
    $("#r-111").parent().attr("tabindex", "631");
    $("#r-112").parent().attr("tabindex", "632");
    $("#r-113").parent().attr("tabindex", "633");
    $("#r-114").parent().attr("tabindex", "634");
    $("#r-115").parent().attr("tabindex", "635");
    $("#r-116").parent().attr("tabindex", "636");
    /*連絡先*/
    $("#btn-table-8").attr("tabindex", "700");
    $("#r-117").parent().attr("tabindex", "701");
    $("#r-118").parent().attr("tabindex", "702");
    $("#r-119").parent().attr("tabindex", "703");
    $("#r-120").parent().attr("tabindex", "704");
    $("input[name='infoAreaCommon.txtContactPropertyElse']").attr("tabindex", function(index, attr) { return index + 705;});
    $("input[name='infoAreaCommon.txtContactZipCode1']").attr("tabindex", function(index, attr) { return index + 706;});
    $("input[name='infoAreaCommon.txtContactZipCode2']").attr("tabindex", function(index, attr) { return index + 707;});
    $("#validContactZipCode").attr("tabindex", "708");
    $("input[name='infoAreaCommon.txtContactAddress']").attr("tabindex", function(index, attr) { return index + 709;});
    $("input[name='infoAreaCommon.txtContactTelno1']").attr("tabindex", function(index, attr) { return index + 710;});
    $("input[name='infoAreaCommon.txtContactTelno2']").attr("tabindex", function(index, attr) { return index + 711;});
    $("input[name='infoAreaCommon.txtContactTelno3']").attr("tabindex", function(index, attr) { return index + 712;});
    $("input[name='infoAreaCommon.txtContactName']").attr("tabindex", function(index, attr) { return index + 713;});
    $("input[name='infoAreaCommon.txtContactNamePhonetic']").attr("tabindex", function(index, attr) { return index + 714;});
    $("input[name='infoAreaCommon.txtContactMail']").attr("tabindex", function(index, attr) { return index + 715;});
    $("input[name='infoAreaCommon.txtContactMailConfirm']").attr("tabindex", function(index, attr) { return index + 716;});
    $("textarea[name='infoAreaCommon.txaOptional']").attr("tabindex", function(index, attr) { return index + 717;});
}

$(document).ready(function() {
	appendTapIndex();

	showHideCommonArea();

    _rdoLostElseReasonCode.change(function () {
        showHideLostBuildingReasonBlock();
    });

    _rdoChangeReason.change(function () {
        showHideLostBuildingBlock();
    });

    _rdoGroupYesno.change(function () {
        showHideManagementFormBlock();
    });

    _rdoGroupForm.change(function () {
        showHideManagementFormReasonBlock();
    });

    _rdoLandRights.change(function () {
        showHideLandRightsTxt();
    });

    _rdoUseFor.change(function () {
        showHideAttachedUse();
    });

    _rdoManagementForm.change(function () {
        showHideManagementWay();
    });

    _rdoRule.change(function () {
        showHideManagementAgreement();
    });

    _rdoOneYearOver.change(function () {
        showHideMeetingOnceAYear();
    });

    _rdoRepairCost.change(function () {
        showHideRepairReserveFund();
    });

    _rdoRepairPlan.change(function () {
        showHideSystematicImplementationRepairs();
    });

    _rdoLongRepairPlan.change(function () {
        showHideRepairPlan();
    });

    _rdoSeismicDiagnosis.change(function () {
        showHideEarthquakeResistance();
    });

    _rdoContactProperty.change(function () {
        showHideContactAttribute();
    });

    //format calNoticeDate
    _calNoticeDate.blur(function () {
        handleShowErrorDateTimePickerOnBlur($(this), _JP_NAME_CALNOTICE_DATE, "yy/MM/dd", true);
    });

    _calBuiltDate.blur(function () {
        handleShowErrorDateTimePickerOnBlur($(this), _JP_NAME_CALBUILD_DATE, "yy/MM/dd", true);
    });

    _calNotificationDate.blur(function () {
        handleShowErrorDateTimePickerOnBlur($(this), _JP_NAME_CALNOTIFICATION_DATE, "yy/MM/dd", true);
    });

    /* Set event tabIndex for radio */
    $("input[name='infoAreaCommon.rdoGroup']").addClass("check-radio1");
    $(".check-radio1").parent().on( "keypress keydown click focusout", function(e) {
        checkRadioByClassId(this, e, radio1, RDO_GROUP, false);
    });

    $("input[name='infoAreaCommon.rdoManager']").addClass("check-radio2");
    $(".check-radio2").parent().on( "keypress keydown click focusout", function(e) {
        checkRadioByClassId(this, e, radio2, RDO_MANAGER, false);
    });

    $("input[name='infoAreaCommon.rdoRule']").addClass("check-radio3");
    $(".check-radio3").parent().on( "keypress keydown click focusout", function(e) {
        checkRadioByClassId(this, e, radio3, RDO_RULER, false);
    });

    $("input[name='infoAreaCommon.rdoOneyearOver']").addClass("check-radio4");
    $(".check-radio4").parent().on( "keypress keydown click focusout", function(e) {
        checkRadioByClassId(this, e, radio4, RDO_ONEYEAROVER, false);
    });

    $("input[name='infoAreaCommon.rdoManagementCost']").addClass("check-radio5");
    $(".check-radio5").parent().on( "keypress keydown click focusout", function(e) {
        checkRadioByClassId(this, e, radio5, RDO_MANAGERMENTCOST, false);
    });

    $("input[name='infoAreaCommon.rdoRepairCost']").addClass("check-radio6");
    $(".check-radio6").parent().on( "keypress keydown click focusout", function(e) {
        checkRadioByClassId(this, e, radio6, RDO_REPAITCOST, false);
    });

    $("input[name='infoAreaCommon.rdoRepairPlan']").addClass("check-radio7");
    $(".check-radio7").parent().on( "keypress keydown click focusout", function(e) {
        checkRadioByClassId(this, e, radio7, RDO_REPAITPALN, false);
    });

    $("input[name='infoAreaCommon.rdoContactProperty']").addClass("check-radio8");
    $(".check-radio8").parent().on( "keypress keydown click focusout", function(e) {
        checkRadioByClassId(this, e, radio8, RDO_CONTACTPROPERTY, true);
        if($("input[name='infoAreaCommon.rdoContactProperty']:last").is(":checked")){
        	$("input[name='infoAreaCommon.txtContactPropertyElse']").removeClass('disable-item');
        } else {
        	$("input[name='infoAreaCommon.txtContactPropertyElse']").addClass('disable-item');
        }
    });

/* validate radio */
    $("input[name='infoAreaCommon.rdoNotificationType']").addClass("check-radio9");
    $(".check-radio9").parent().on( "keypress", function(e) {
        $(this).children().spacePressRadio(e);
    });

    $("input[name='infoAreaCommon.rdoGroupYesno']").addClass("check-radio10");
    $(".check-radio10").parent().on( "keypress", function(e) {
        $(this).children().spacePressRadio(e);
        if($("input[name='infoAreaCommon.rdoGroupYesno']:first").is(":checked")){
        	$("input[name='infoAreaCommon.rdoGroupForm']").parent().removeClass('disable-item');
        	$("input[name='infoAreaCommon.txtApartmentNumber']").removeClass('disable-item');
        } else {
        	$("input[name='infoAreaCommon.rdoGroupForm']").parent().addClass('disable-item');
        	$("input[name='infoAreaCommon.txtApartmentNumber']").addClass('disable-item');
        }
    });

    $("input[name='infoAreaCommon.rdoGroupForm']").addClass("check-radio11");
    $(".check-radio11").parent().on( "keypress", function(e) {
        $(this).children().spacePressRadio(e);
        if($("input[name='infoAreaCommon.rdoGroupForm']:last").is(":checked")){
        	$("input[name='infoAreaCommon.txtGroupFormElse']").removeClass('disable-item');
        } else {
        	$("input[name='infoAreaCommon.txtGroupFormElse']").addClass('disable-item');
        }
    });

    $("input[name='infoAreaCommon.rdoLandRights']").addClass("check-radio12");
    $(".check-radio12").parent().on( "keypress", function(e) {
        $(this).children().spacePressRadio(e);
        if($("input[name='infoAreaCommon.rdoLandRights']:last").is(":checked")){
        	$("input[name='infoAreaCommon.txtLandRightsElse']").removeClass('disable-item');
        } else {
        	$("input[name='infoAreaCommon.txtLandRightsElse']").addClass('disable-item');
        }
    });

    $("input[name='infoAreaCommon.rdoUsefor']").addClass("check-radio13");
    $(".check-radio13").parent().on( "keypress", function(e) {
        $(this).children().spacePressRadio(e);
        if($("input[name='infoAreaCommon.rdoUsefor']:last").is(":checked")){
        	$("input[name='infoAreaCommon.txtUseforElse']").removeClass('disable-item');
        } else {
        	$("input[name='infoAreaCommon.txtUseforElse']").addClass('disable-item');
        }
    });

    $("input[name='infoAreaCommon.rdoManagementForm']").addClass("check-radio14");
    $(".check-radio14").parent().on( "keypress", function(e) {
        $(this).children().spacePressRadio(e);
        if($("input[name='infoAreaCommon.rdoManagementForm']:last").is(":checked")){
        	$("input[name='infoAreaCommon.txtManagementFormElse']").removeClass('disable-item');
        } else {
        	$("input[name='infoAreaCommon.txtManagementFormElse']").addClass('disable-item');
        }
    });

    $("input[name='infoAreaCommon.rdoMinutes']").addClass("check-radio15");
    $("input[name='infoAreaCommon.rdoLongRepairPlan']").addClass("check-radio16");
    $("input[name='infoAreaCommon.rdoArrearageRule']").addClass("check-radio17");
    $("input[name='infoAreaCommon.rdoSegment']").addClass("check-radio18");
    $("input[name='infoAreaCommon.rdoEmptyPercent']").addClass("check-radio19");
    $("input[name='infoAreaCommon.rdoRentalPercent']").addClass("check-radio20");
    $("input[name='infoAreaCommon.rdoSeismicDiagnosis']").addClass("check-radio21");
    $("input[name='infoAreaCommon.rdoEarthquakeResistance']").addClass("check-radio22");
    $("input[name='infoAreaCommon.rdoSeismicRetrofit']").addClass("check-radio23");
    $("input[name='infoAreaCommon.rdoDesignDocument']").addClass("check-radio24");
    $("input[name='infoAreaCommon.rdoVoluntaryOrganization']").addClass("check-radio25");
    $("input[name='infoAreaCommon.rdoDisasterPreventionManual']").addClass("check-radio26");
    $("input[name='infoAreaCommon.rdoDisasterPreventionStockpile']").addClass("check-radio27");
    $("input[name='infoAreaCommon.rdoNeedSupportList']").addClass("check-radio28");
    $("input[name='infoAreaCommon.rdoDisasterPreventionRegular']").addClass("check-radio29");
    $("input[name='infoAreaCommon.rdoSlope']").addClass("check-radio30");
    $("input[name='infoAreaCommon.rdoRailing']").addClass("check-radio31");
    $("input[name='infoAreaCommon.rdoElevator']").addClass("check-radio32");
    $("input[name='infoAreaCommon.rdoLed']").addClass("check-radio33");
    $("input[name='infoAreaCommon.rdoHeatShielding']").addClass("check-radio34");
    $("input[name='infoAreaCommon.rdoEquipmentCharge']").addClass("check-radio35");
    $("input[name='infoAreaCommon.rdoCommunity']").addClass("check-radio36");
    $("input[name='infoAreaCommon.rdoRepairHistory']").addClass("check-radio37");
    $("input[name='infoAreaCommon.rdoChangeReason']").addClass("check-radio38");

    $(".check-radio15, .check-radio16, .check-radio17, .check-radio18, .check-radio19, .check-radio20, .check-radio21" +
    		",.check-radio22, .check-radio23, .check-radio24, .check-radio25, .check-radio26, .check-radio27, .check-radio28" +
    		",.check-radio29, .check-radio30, .check-radio31, .check-radio32, .check-radio33, .check-radio34, .check-radio35" +
    		", .check-radio36, .check-radio37, .check-radio38").parent().on( "keypress", function(e) {
        $(this).children().spacePressRadio(e);
    });

    $("input[name='infoAreaCommon.rdoLostElseReasonCode']").addClass("check-radio39");
    $(".check-radio39").parent().on( "keypress", function(e) {
        $(this).children().spacePressRadio(e);
        if($("input[name='infoAreaCommon.rdoLostElseReasonCode']:last").is(":checked")){
        	$("input[name='infoAreaCommon.txtLostElseReasonElse']").removeClass('disable-item');
        } else {
        	$("input[name='infoAreaCommon.txtLostElseReasonElse']").addClass('disable-item');
        }
    });

});
