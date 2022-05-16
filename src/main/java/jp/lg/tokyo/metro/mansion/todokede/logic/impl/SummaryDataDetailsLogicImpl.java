/*
 * @(#)
 * SummaryDataDetailsLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.opencsv.CSVWriter;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.PageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100BDAO;
import jp.lg.tokyo.metro.mansion.todokede.form.SummaryDataPagingForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.ISummaryDataDetailsLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.OutputCsvSummaryDataDetailsVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.PagingVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.SummaryDataDetailsVo;

@Service
public class SummaryDataDetailsLogicImpl implements ISummaryDataDetailsLogic {

    @Autowired
    TBL100BDAO tbl100BDAO;

    private static final Logger logger = LogManager.getLogger(SummaryDataDetailsLogicImpl.class);
    private static final String APARTMENT_ID = "マンションID";
    private static final String APARTMENT_NAME = "マンション名";
    private static final String APARTMENT_NAME_PHONETIC = "マンション名フリガナ";
    private static final String ZIP_CODE = "郵便番号";
    private static final String CITY_CODE = "区市町村名";
    private static final String ADDRESS = "住所";
    private static final String ACCEPT_ＳTATUS = "受理状況";
    private static final String NOTIFICATION_TYPE = "届出種別";
    private static final String NOTIFICATION_DATE = "届出年月日";
    private static final String NOTIFICATION_GROUP_NAME = "届出者（管理組合名）";
    private static final String NOTIFICATION_PERSON_NAME = "届出者（届出者氏名）";
    private static final String CHANGE_COUNT = "変更回数";
    private static final String CHANGE_REASON = "変更理由";
    private static final String LOST_ELSE_REASON = "建物の滅失その他の事由";
    private static final String LOST_ELSE_REASON_ELSE = "建物の滅失その他の事由その他";
    private static final String GROUP_YESNO = "団地管理組合是否";
    private static final String APARTMENT_NUMBER = "棟数";
    private static final String GROUP_FORM = "管理組合の管理形態";
    private static final String GROUP_FORM_ELSE = "管理組合の管理形態その他";
    private static final String HOUSE_NUMBER = "戸数";
    private static final String FLOOR_NUMBER = "階数";
    private static final String BUILT_DATE = "新築年月日";
    private static final String LAND_RIGHTS = "土地権利";
    private static final String LAND_RIGHTS_ELSE = "土地権利その他";
    private static final String USE_FOR = "併設用途";
    private static final String USE_FOR_ELSE = "併設用途その他";
    private static final String MANAGEMENT_FORM = "管理形態";
    private static final String MANAGEMENT_FORM_ELSE = "管理形態その他";
    private static final String MANAGER_NAME = "管理業者名";
    private static final String MANAGER_NAME_PHONETIC = "管理業者名フリガナ";
    private static final String MANAGER_ZIP_CODE = "管理業者郵便番号";
    private static final String MANAGER_ADDRESS = "管理業者住所";
    private static final String MANAGER_TEL_NO = "管理業者電話番号";
    private static final String GROUP = "管理組合";
    private static final String MANAGER = "管理者等";
    private static final String RULE = "管理規約";
    private static final String RULE_CHANGE_YEAR = "管理規約最終改正年";
    private static final String OPEN_ONE_YEAR_OVER = "総会年1回以上開催";
    private static final String MINUTES = "議事録";
    private static final String MANAGEMENT_COST = "管理費";
    private static final String REPAIR_COST = "修繕積立金";
    private static final String REPAIR_MONTHLY_COST = "修繕積立金平米当たり月額";
    private static final String REPAIR_PLAN = "修繕計画実施";
    private static final String REPAIR_NEAREST_YEAR = "修繕計画直近実施年";
    private static final String LONG_REPAIR_PLAN = "長期修繕計画";
    private static final String LONG_REPAIR_PLAN_YEAR = "長期修繕計画最新作成年";
    private static final String LONG_REPAIR_PLAN_PERIOD = "長期修繕計画期間";
    private static final String LONG_REPAIR_PLAN_YEAR_FROM = "長期修繕計画年度１";
    private static final String LONG_REPAIR_PLAN_YEAR_TO = "長期修繕計画年度２";
    private static final String ARREARAGE_RULE = "滞納対応ルール";
    private static final String SEGMENT = "区分所有者等名簿等";
    private static final String EMPTY_PERCENT = "空き住戸割合";
    private static final String EMPTY_NUMBER = "空き住戸戸数";
    private static final String RENTAL_PERCENT = "賃貸化住戸割合";
    private static final String RENTAL_NUMBER = "賃貸化住戸戸数";
    private static final String SEISMIC_DIAGNOSIS = "耐震診断";
    private static final String EARTHQUAKE_RESISTANCE = "耐震性";
    private static final String SEISMIC_RETROFIT = "耐震改修";
    private static final String DESIGN_DOCUMENT = "設計図書";
    private static final String REPAIR_HISTORY = "修繕履歴";
    private static final String VOLUNTARY_ORGANIZATION = "自主防災組織";
    private static final String DISASTER_PREVENTION_MANUAL = "防災マニュアル";
    private static final String DISASTER_PREVENTION_STOCKPILE = "防災用品備蓄";
    private static final String NEED_SUPPORT_LIST = "避難行動要支援者名簿";
    private static final String DISASTER_PREVENTION_REGULAR = "防災訓練定期実施";
    private static final String SLOPE = "エントランスバリアフリー化";
    private static final String RAILING = "共用廊下等手すり設置";
    private static final String ELEVATOR = "エレベーター設置";
    private static final String LED = "共用部分LED化";
    private static final String HEATSHIELDING = "開口部遮熱性能向上";
    private static final String EQUIPMENT_CHARGE = "電気自動車等用充電設備設置等";
    private static final String COMMUNITY = "地域コミュニティ形成等取組";
    private static final String CONTACT_PROPERTY = "連絡先属性";
    private static final String CONTACT_PROPERTY_ELSE = "連絡先属性その他";
    private static final String CONTACT_ZIP = "連絡先郵便番号";
    private static final String CONTACT_ADDRESS = "連絡先住所";
    private static final String CONTACT_TEL_NO = "連絡先電話番号";
    private static final String CONTACT_NAME = "連絡先氏名";
    private static final String CONTACT_NAME_PHONETIC = "連絡先氏名フリガナ";
    private static final String CONTACT_MAIL_ADDRESS = "連絡先メールアドレス";
    private static final String OPTIONAL = "自由記述欄";

    /**
     * Get Summary Data Details prepare show
     * @param form SummaryDataPagingForm 
     */
    public PagingVo<SummaryDataDetailsVo> getSummaryDataDetails(SummaryDataPagingForm form) {

        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I,
                Thread.currentThread().getStackTrace()[1].getMethodName()));
        int displayMaxRecordPerPage = Integer
                .parseInt(SessionUtil.getSystemSettingByKey(CommonConstants.GKA0120_LIST_DISPLAY_MAX));
        Pageable pageable = PageUtil.getPageable(form.getPage(), displayMaxRecordPerPage);

        Page<SummaryDataDetailsVo> pages = tbl100BDAO.getSummaryDataDetails(pageable, form);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I,
                Thread.currentThread().getStackTrace()[1].getMethodName()));

        return new PagingVo<>(pages, PageUtil.getStartIndex(pages), PageUtil.getLastIndex(pages),
                PageUtil.getTotalPage(pages));
    }

    /**
     * Get Data For Export CSV
     * @param apartmentIds String
     * @param outputStream OutputStream
     * @throws IOException throws exception when close WriteCsv fail
     */
    public void outputCsv(String[] apartmentIds, OutputStream outputStream) throws IOException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I,
                Thread.currentThread().getStackTrace()[1].getMethodName()));
        CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8),
                CSVWriter.DEFAULT_SEPARATOR, CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
        String[] headerRecord = { APARTMENT_ID, APARTMENT_NAME, APARTMENT_NAME_PHONETIC, ZIP_CODE, CITY_CODE, ADDRESS,
            ACCEPT_ＳTATUS, NOTIFICATION_TYPE, NOTIFICATION_DATE, NOTIFICATION_GROUP_NAME, NOTIFICATION_PERSON_NAME,
            CHANGE_COUNT, CHANGE_REASON, LOST_ELSE_REASON, LOST_ELSE_REASON_ELSE, GROUP_YESNO, APARTMENT_NUMBER,
            GROUP_FORM, GROUP_FORM_ELSE, HOUSE_NUMBER, FLOOR_NUMBER, BUILT_DATE, LAND_RIGHTS, LAND_RIGHTS_ELSE,
            USE_FOR, USE_FOR_ELSE, MANAGEMENT_FORM, MANAGEMENT_FORM_ELSE, MANAGER_NAME, MANAGER_NAME_PHONETIC,
            MANAGER_ZIP_CODE, MANAGER_ADDRESS, MANAGER_TEL_NO, GROUP, MANAGER, RULE, RULE_CHANGE_YEAR,
            OPEN_ONE_YEAR_OVER, MINUTES, MANAGEMENT_COST, REPAIR_COST, REPAIR_MONTHLY_COST, REPAIR_PLAN,
            REPAIR_NEAREST_YEAR, LONG_REPAIR_PLAN, LONG_REPAIR_PLAN_YEAR, LONG_REPAIR_PLAN_PERIOD,
            LONG_REPAIR_PLAN_YEAR_FROM, LONG_REPAIR_PLAN_YEAR_TO, ARREARAGE_RULE, SEGMENT, EMPTY_PERCENT,
            EMPTY_NUMBER, RENTAL_PERCENT, RENTAL_NUMBER, SEISMIC_DIAGNOSIS, EARTHQUAKE_RESISTANCE, SEISMIC_RETROFIT,
            DESIGN_DOCUMENT, REPAIR_HISTORY, VOLUNTARY_ORGANIZATION, DISASTER_PREVENTION_MANUAL,
            DISASTER_PREVENTION_STOCKPILE, NEED_SUPPORT_LIST, DISASTER_PREVENTION_REGULAR, SLOPE, RAILING, ELEVATOR,
            LED, HEATSHIELDING, EQUIPMENT_CHARGE, COMMUNITY, CONTACT_PROPERTY, CONTACT_PROPERTY_ELSE, CONTACT_ZIP,
            CONTACT_ADDRESS, CONTACT_TEL_NO, CONTACT_NAME, CONTACT_NAME_PHONETIC, CONTACT_MAIL_ADDRESS, OPTIONAL };
        csvWriter.writeNext(headerRecord, true);

        List<OutputCsvSummaryDataDetailsVo> voList = tbl100BDAO.getSummaryDataDetailsWriteCSV(apartmentIds);
        if (!CollectionUtils.isEmpty(voList)) {
            for (int j = 0; j < voList.size(); j++) {
                OutputCsvSummaryDataDetailsVo vo = voList.get(j);

                String[] superviseSummaryDataInfo = { vo.getApartmentId(), vo.getApartmentName(),
                        vo.getApartmentNamePhonetic(), vo.getZipCode(), vo.getCityCode(), vo.getAddress(),
                        vo.getAcceptＳtatus(), vo.getNotificationType(), vo.getNotificationDate(),
                        vo.getNotificationGroupName(), vo.getNotificationPersonName(), vo.getChangeCount(),
                        vo.getChangeReason(), vo.getLostElseReason(), vo.getLostElseReasonElse(), vo.getGroupYesno(),
                        vo.getApartmentNumber(), vo.getGroupForm(), vo.getGroupFormElse(), vo.getHouseNumber(),
                        vo.getFloorNumber(), vo.getBuiltDate(), vo.getLandRights(), vo.getLandRightsElse(),
                        vo.getUsefor(), vo.getUseforElse(), vo.getManagementForm(), vo.getManagementFormElse(),
                        vo.getManagerName(), vo.getManagerNamePhonetic(), vo.getManagerZipCode(),
                        vo.getManagerAddress(), vo.getManagerTelNo(), vo.getGroup(), vo.getManager(), vo.getRule(),
                        vo.getRuleChangeYear(), vo.getOpenOneyearOver(), vo.getMinutes(), vo.getManagementCost(),
                        vo.getRepairCost(), vo.getRepairMonthlyCost(), vo.getRepairPlan(), vo.getRepairNearestYear(),
                        vo.getLongRepairPlan(), vo.getLongRepairPlanYear(), vo.getLongRepairPlanPeriod(),
                        vo.getLongRepairPlanYearFrom(), vo.getLongRepairPlanYearTo(), vo.getArrearageRule(),
                        vo.getSegment(), vo.getEmptyPercent(), vo.getEmptyNumber(), vo.getRentalPercent(),
                        vo.getRentalNumber(), vo.getSeismicDiagnosis(), vo.getEarthquakeResistance(),
                        vo.getSeismicRetrofit(), vo.getDesignDocument(), vo.getRepairHistory(),
                        vo.getVoluntaryOrganization(), vo.getDisasterPreventionManual(),
                        vo.getDisasterPreventionStockpile(), vo.getNeedSupportList(), vo.getDisasterPreventionRegular(),
                        vo.getSlope(), vo.getRailing(), vo.getElevator(), vo.getLed(), vo.getHeatShielding(),
                        vo.getEquipmentCharge(), vo.getCommunity(), vo.getContactProperty(),
                        vo.getContactPropertyElse(), vo.getContactZip(), vo.getContactAddress(), vo.getContactTelNo(),
                        vo.getContactName(), vo.getContactNamePhonetic(), vo.getContactMailAddress(),
                        vo.getOptional() };
                csvWriter.writeNext(superviseSummaryDataInfo, false);
            }
        }
        csvWriter.close();
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I,
                Thread.currentThread().getStackTrace()[1].getMethodName()));
    }
}
