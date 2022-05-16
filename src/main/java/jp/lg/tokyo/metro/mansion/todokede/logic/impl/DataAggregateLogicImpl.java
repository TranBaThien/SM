/*
 * @(#) DataAggregateLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author LVTrinh1
 * Create Date: 2020/01/15
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonProperties;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL300BDAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM001DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM001Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.logic.IDataAggregateLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.CityVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.DataAggregateResultItemVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.DataAggregateResultTmpCntVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.DataAggregateResultTmpVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.DataAggregateResultVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.DataAggregateVo;

@Service
public class DataAggregateLogicImpl implements IDataAggregateLogic {

    private static final String SUMMARY_ITEM = "集計項目";
    private static final String SUMMARY_ITEM_ERR = "7件以下";
    private static final String PERIOD_FROM = "集計期間開始";
    private static final String PERIOD_TO = "集計期間終了";
    private static final String NEWBULIDING_FROM = "新築年月日開始";
    private static final String NEWBULIDING_TO = "新築年月日終了";
    private static final String HOUSENUMBER_FROM = "建物戸数開始";
    private static final String HOUSENUMBER_TO = "建物戸数終了";
    private static final String ZERO = "0";
    private static final String AGGREGATE_CREDIT_TITLE = "集計単位";
    public static final String YEAR_NM = "年度";
    public static final String YEAR_MONTH_NM = "年月";
    public static final String DATE_NM = "年月日";
    public static final String CITY_NM = "区市町村";
    public static final String YYYYMM = "yyyyMM";
    public static final String SLASH_YYYYMM = "yyyy/MM";
    public static final String DEF_STR_DATE_AGGREGATE = "DEF_STR_DATE_AGGREGATE";

    @Autowired
    TBL300BDAO tbl300BDAO;

    @Autowired
    private TBM001DAO tbm001DAO;

    @Override
    public boolean save(Object obj) throws Exception {
        return false;
    }

    @Override
    public boolean delete(Object obj) {
        return false;
    }

    @Override
    public boolean isValidCorrelation(DataAggregateVo vo, List<String> errorMessage) {
        boolean isErr = false;

        // 集計項目
        if (vo.getChkSummaryItem().length > 7) {
            errorMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0144, SUMMARY_ITEM, SUMMARY_ITEM_ERR));
            isErr = true;
        }

        // 集計期間開始
        String cldPeriodFrom = vo.getCldPeriodFrom();
        if (!StringUtils.isBlank(cldPeriodFrom)
                && DateTimeUtil.getLocalDateFromString2(cldPeriodFrom).isAfter(LocalDate.now())) {
            errorMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0100, PERIOD_FROM));
            isErr = true;
        }

        String defDateAggregate = CommonProperties.getProperty(DEF_STR_DATE_AGGREGATE);
        // 集計期間終了
        String cldPeriodTo = vo.getCldPeriodTo();
        if (!StringUtils.isBlank(cldPeriodTo) && DateTimeUtil.getLocalDateFromString2(cldPeriodTo)
                .isBefore(DateTimeUtil.getLocalDateFromString2(defDateAggregate))) {
            errorMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0100, PERIOD_TO));
            isErr = true;
        }

        // 集計期間開始 _ 集計期間終了
        if (!isBlank(cldPeriodFrom) && !isBlank(cldPeriodTo)) {
            if (DateTimeUtil.getLocalDateFromString2(cldPeriodFrom)
                    .isAfter(DateTimeUtil.getLocalDateFromString2(cldPeriodTo))) {
                errorMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0129, PERIOD_FROM, PERIOD_TO));
                isErr = true;
            }
        }

        // 新築年月日開始 _ 新築年月日終了
        String cldNewBulidingFrom = vo.getCldNewBulidingFrom();
        String cldNewBulidingTo = vo.getCldNewBulidingTo();
        if (!isBlank(cldNewBulidingFrom) && !isBlank(cldNewBulidingTo)) {
            if (DateTimeUtil.getLocalDateFromString2(cldNewBulidingFrom)
                    .isAfter(DateTimeUtil.getLocalDateFromString2(cldNewBulidingTo))) {
                errorMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0129, NEWBULIDING_FROM, NEWBULIDING_TO));
                isErr = true;
            }
        }

        // 建物戸数開始 _ 建物戸数終了
        String txtHouseNumberFrom = vo.getTxtHouseNumberFrom();
        txtHouseNumberFrom = txtHouseNumberFrom.replaceAll(",$", "");
        String txtHouseNumberTo = vo.getTxtHouseNumberTo();
        txtHouseNumberTo = txtHouseNumberTo.replaceAll(",$", "");
        if (!isBlank(txtHouseNumberFrom) && !isBlank(txtHouseNumberTo)) {
            if (Integer.parseInt(txtHouseNumberFrom) > Integer.parseInt(txtHouseNumberTo)) {
                errorMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0129, HOUSENUMBER_FROM, HOUSENUMBER_TO));
                isErr = true;
            }
        }

        return isErr;
    }

    @Override
    public List<DataAggregateResultVo> getAggregateRecord(DataAggregateVo vo) {
        List<DataAggregateResultVo> lsAggregateRecord = new ArrayList<DataAggregateResultVo>();
        DataAggregateResultVo aggregateResultVo;
        List<DataAggregateResultItemVo> lstSummaryItem;
        DataAggregateResultItemVo summaryItem;
        LocalDate lcDateNow = LocalDate.now();
        String yearCdVal = CodeUtil.getValue(CommonConstants.CD058, YEAR_NM);
        String yearMonthCdVal = CodeUtil.getValue(CommonConstants.CD058, YEAR_MONTH_NM);
        String dateCdVal = CodeUtil.getValue(CommonConstants.CD058, DATE_NM);
        String cityCdVal = CodeUtil.getValue(CommonConstants.CD058, CITY_NM);
        String rdoAggregateCredit = vo.getRdoAggregateCredit();

        // 集計期間開始が2020/04/01より過去の日付が入力され　または　未入力の場合、2020/04/01とする。集計期間終了が現在日より未来の日付が入力され　または　未入力の場合、現在日とする。
        String defDateAggregate = CommonProperties.getProperty(DEF_STR_DATE_AGGREGATE);
        // 集計期間開始
        String cldPeriodFrom = vo.getCldPeriodFrom();
        if (StringUtils.isBlank(cldPeriodFrom) || DateTimeUtil.getLocalDateFromString2(cldPeriodFrom)
                .isBefore(DateTimeUtil.getLocalDateFromString2(defDateAggregate))) {
            cldPeriodFrom = defDateAggregate;
        }
        // 集計期間終了
        String cldPeriodTo = vo.getCldPeriodTo();
        if (StringUtils.isBlank(cldPeriodTo) || DateTimeUtil.getLocalDateFromString2(cldPeriodTo).isAfter(lcDateNow)) {
            cldPeriodTo = DateTimeUtil.getLocalDateAsString2(lcDateNow);
        }

        // Edit date when 集計単位: 年度 and 集計単位:年月
        LocalDate lcdFrom = DateTimeUtil.getLocalDateFromString2(cldPeriodFrom);
        LocalDate lcdTo = DateTimeUtil.getLocalDateFromString2(cldPeriodTo);
        if (rdoAggregateCredit.equals(yearCdVal)) {
            if (lcdFrom.getMonthValue() < 4) { // ex: 2019/03/03 -> 2018/04/01
                lcdFrom = LocalDate.of(lcdFrom.getYear() - 1, 4, 1);
            } else { // ex: 2019/06/06 -> 2019/04/01
                lcdFrom = LocalDate.of(lcdFrom.getYear(), 4, 1);
            }

            if (lcdTo.getMonthValue() < 4) { // ex: 2019/02/02 -> 2019/03/31
                lcdTo = LocalDate.of(lcdTo.getYear(), 3, 31);
            } else { // ex: 2019/05/02 -> 2020/03/31
                lcdTo = LocalDate.of(lcdTo.getYear() + 1, 3, 31);
            }
        } else if (rdoAggregateCredit.equals(yearMonthCdVal)) {
            lcdFrom = lcdFrom.withDayOfMonth(1); // ex: 2019/01/11 -> 2019/01/01
            lcdTo = lcdTo.withDayOfMonth(lcdTo.lengthOfMonth()); // ex: 2019/01/11 -> 2019/01/31
        }

        cldPeriodFrom = DateTimeUtil.getLocalDateAsString2(lcdFrom);
        cldPeriodTo = DateTimeUtil.getLocalDateAsString2(lcdTo);
        vo.setCldPeriodFrom(cldPeriodFrom);
        vo.setCldPeriodTo(cldPeriodTo);

        // 集計対象表（SQL結果）生成
        Map<String, List<DataAggregateResultTmpVo>> lstTmpAll = tbl300BDAO.getMapAggregateRecord(vo);
        Map<String, List<DataAggregateResultTmpCntVo>> lstTmpCntAll = tbl300BDAO.getMapCntAggregateRecord(vo);

        // 集計キー（集計単位）リスト設定
        Map<String, String> mapAggregateCredit = new HashMap<String, String>();
        String[] lstChkSummaryItem = vo.getChkSummaryItem();

        // Init aggregate credit
        DateTimeFormatter dfKey;
        DateTimeFormatter dfVal;
        if (rdoAggregateCredit.equals(yearCdVal)) {
            int yearFrom = lcdFrom.getYear();
            int yearTo = lcdTo.getYear();

            if (lcdFrom.getMonthValue() < 4) {
                yearFrom--;
            }
            if (lcdTo.getMonthValue() < 4) {
                yearTo--;
            }

            while (yearFrom <= yearTo) {
                mapAggregateCredit.put(String.valueOf(yearFrom), String.valueOf(yearFrom) + YEAR_NM);
                yearFrom++;
            }
        } else if (rdoAggregateCredit.equals(yearMonthCdVal)) {
            dfKey = DateTimeFormatter.ofPattern(YYYYMM);
            dfVal = DateTimeFormatter.ofPattern(SLASH_YYYYMM);

            while (!lcdFrom.isAfter(lcdTo) || lcdFrom.getMonthValue() == lcdTo.getMonthValue()) {
                mapAggregateCredit.put(lcdFrom.format(dfKey), lcdFrom.format(dfVal));
                lcdFrom = lcdFrom.plusMonths(1);
            }
        } else if (rdoAggregateCredit.equals(dateCdVal)) {
            dfKey = DateTimeFormatter.ofPattern(CommonConstants.YYYYMMDD);
            dfVal = DateTimeFormatter.ofPattern(CommonConstants.SLASH_YYYYMMDD);

            while (!lcdFrom.isAfter(lcdTo)) {
                mapAggregateCredit.put(lcdFrom.format(dfKey), lcdFrom.format(dfVal));
                lcdFrom = lcdFrom.plusDays(1);
            }
        } else if (rdoAggregateCredit.equals(cityCdVal)) {
            List<CityVo> lstCity = vo.getLstCity();

            if (!StringUtils.isBlank(vo.getCityCode())) {
                String cityCode = vo.getCityCode();
                TBM001Entity entityTbm001 = tbm001DAO.getMunicipalMasterInfo(cityCode);
                mapAggregateCredit.put(cityCode, entityTbm001.getCityName());
            } else {
                for (CityVo city : lstCity) {
                    mapAggregateCredit.put(city.getCityCode(), city.getCityName());
                }
            }
        }

        // Sort map
        LinkedHashMap<String, String> aggregateCreditSortedMap = new LinkedHashMap<>();
        if (rdoAggregateCredit.equals(cityCdVal)) {
            // In descending order
            mapAggregateCredit.entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                    .forEachOrdered(x -> aggregateCreditSortedMap.put(x.getKey(), x.getValue()));
        } else {
            // In ascending order
            mapAggregateCredit.entrySet().stream().sorted(Map.Entry.comparingByKey())
                    .forEachOrdered(x -> aggregateCreditSortedMap.put(x.getKey(), x.getValue()));
        }

        // Set aggregate credit
        int size = 0;
        for (String credit : new ArrayList<String>(aggregateCreditSortedMap.keySet())) {
            // Max size: 100
            if (size == 100) {
                break;
            }
            size++;

            aggregateResultVo = new DataAggregateResultVo();
            aggregateResultVo.setAggregateCredit(credit);
            aggregateResultVo.setAggregateCreditDsp(aggregateCreditSortedMap.get(credit));
            lsAggregateRecord.add(aggregateResultVo);
        }

        // Set summary item
        boolean isZeroSummaryItemAll;
        List<String> lstIdMansion;
        DecimalFormat numFormat = new DecimalFormat("###,###");
        for (DataAggregateResultVo record : lsAggregateRecord) {
            lstSummaryItem = new ArrayList<DataAggregateResultItemVo>();

            for (String chkItem : lstChkSummaryItem) {
                summaryItem = new DataAggregateResultItemVo();

                summaryItem.setSummaryCd(chkItem);
                // Set value lblSummaryItemAll
                isZeroSummaryItemAll = true;
                for (DataAggregateResultTmpCntVo item : lstTmpCntAll.get(chkItem)) {
                    if (record.getAggregateCredit().equals(item.getAggregateCredit())) {
                        isZeroSummaryItemAll = false;
                        summaryItem.setLblSummaryItemAll(numFormat.format(Double.valueOf(item.getCnt())));
                        break;
                    }
                }

                if (isZeroSummaryItemAll) {
                    summaryItem.setLblSummaryItemAll(ZERO);
                }

                // Set list id mansion and LnkSummaryItem
                lstIdMansion = getLstIdMansion(lstTmpAll.get(chkItem), record.getAggregateCredit());
                summaryItem.setLstIdMansion(lstIdMansion);
                summaryItem.setLnkSummaryItem(numFormat.format(Double.valueOf(lstIdMansion.size())));

                lstSummaryItem.add(summaryItem);
            }

            record.setLstSummaryItem(lstSummaryItem);
        }

        return lsAggregateRecord;
    }

    private List<String> getLstIdMansion(List<DataAggregateResultTmpVo> list, String aggregateCredit) {
        List<String> lstIdMansion = new ArrayList<String>();

        for (DataAggregateResultTmpVo item : list) {
            if (item.getAggregateCredit().equals(aggregateCredit)) {
                lstIdMansion.add(item.getApartmentId());
            }
        }

        return lstIdMansion;
    }

    @Override
    public List<String> getLstLblSummaryItem(String[] chkSummaryItem) {
        List<String> lstLblSummaryItem = new ArrayList<String>();

        for (String val : chkSummaryItem) {
            lstLblSummaryItem.add(CodeUtil.getLabel(CommonConstants.CD056, val));
        }

        return lstLblSummaryItem;
    }

    public void outputCsv(List<DataAggregateResultVo> dataAggregationResultVo, List<String> lstLblSummaryItem, ServletOutputStream outputStream) throws BusinessException {
        try {
            CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8),
                    CSVWriter.DEFAULT_SEPARATOR, CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            int size = lstLblSummaryItem.size()*2 + 1;
            String[] headerRecord = new String[size];

            headerRecord[0] = AGGREGATE_CREDIT_TITLE;
            for (int i = 0; i <lstLblSummaryItem.size(); i++) {
                headerRecord[i*2 + 1] = lstLblSummaryItem.get(i);
                headerRecord[i*2 + 2] = lstLblSummaryItem.get(i) + "都全体";
            }

            csvWriter.writeNext(headerRecord, true);

            for (int i = 0; i < dataAggregationResultVo.size(); i++) {
                String[] csvOuput = new String[size];
                csvOuput[0] = dataAggregationResultVo.get(i).getAggregateCreditDsp();
                for( int y = 0; y < dataAggregationResultVo.get(i).getLstSummaryItem().size(); y++) {
                    csvOuput[y*2 + 1] = dataAggregationResultVo.get(i).getLstSummaryItem().get(y).getLnkSummaryItem();
                    csvOuput[y*2 + 2] = dataAggregationResultVo.get(i).getLstSummaryItem().get(y).getLblSummaryItemAll();
                }
                csvWriter.writeNext(csvOuput, false);
            }
            csvWriter.close();
        } catch (IOException e) {
            throw new BusinessException();
        }
    }
}
