/*
 * @(#) UploadMansionLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nbvhoang
 * Create Date: 2019/12/16
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonProperties;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.*;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL105DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL110DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM001DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL105Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.MansionInfoUploadForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IUploadMansionLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.ZAA0150ErrorVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.SystemException;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.*;

/**
 * @author nbvhoang
 *
 */
@Service
public class UploadMansionLogicImpl implements IUploadMansionLogic {

    private static final Logger logger = LogManager.getLogger(UploadMansionLogicImpl.class);

    @Autowired
    private SequenceUtil sequenceUtil;

    @Autowired
    private TBL100DAO tbl100DAO;

    @Autowired
    private TBL105DAO tbl105DAO;

    @Autowired
    private TBL110DAO tbl110DAO;

    @Autowired
    private TBM001DAO tbm001DAO;
    
    private static final String NOT_FOUND = "NOT FOUND";

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public List<ZAA0150ErrorVo> uploadCSV(List<MansionInfoUploadForm> lstMansion, String fileCountMax,
            List<String> lengthOfRowList) throws SystemException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        if (validMansionInfo(lstMansion, fileCountMax, lengthOfRowList).isEmpty()) {
            for (MansionInfoUploadForm mansionForm : lstMansion) {
                TBL100Entity tbl100Entity = saveDataToTbl100(mansionForm);
                saveDataToTbl105(tbl100Entity);
                saveDataToTbl110(tbl100Entity);
            }
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return new ArrayList<ZAA0150ErrorVo>();
        } else {
            List<ZAA0150ErrorVo> listErrorVo;
            listErrorVo = validMansionInfo(lstMansion, fileCountMax, lengthOfRowList);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return listErrorVo;
        }
    }

    @SuppressWarnings("resource")
    @Override
    public List<MansionInfoUploadForm> getDataFormCsv(byte[] file, List<String> lengthOfRowList) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        try {
            InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(file), CommonConstants.ENCODE_UTF8);
            BufferedReader reader = new BufferedReader(isr);

            ColumnPositionMappingStrategy<MansionInfoUploadForm> columnPosition = new ColumnPositionMappingStrategy<MansionInfoUploadForm>();
            columnPosition.setType(MansionInfoUploadForm.class);

            CsvToBean<MansionInfoUploadForm> csvToBean = new CsvToBeanBuilder<MansionInfoUploadForm>(reader)
                    .withType(MansionInfoUploadForm.class).withMappingStrategy(columnPosition)
                    .withIgnoreLeadingWhiteSpace(true).build();

            List<MansionInfoUploadForm> lstMansion = csvToBean.parse();

            CSVReader csvReader = new CSVReader(new InputStreamReader(new ByteArrayInputStream(file)));
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                int a = nextRecord.length;
                lengthOfRowList.add(String.valueOf(a));
            }
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return lstMansion;
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(ex.getMessage());
        }
    }

    /**
     * Save data to TBL100
     * 
     * @param form MansionInfoUploadForm
     * @return TBL100Entity
     */
    private TBL100Entity saveDataToTbl100(MansionInfoUploadForm form) {

        TBL100Entity tbl100Entity = new TBL100Entity();

        String cityCode = tbm001DAO.getCityCodeByCityName(form.getCityName());
        String lastCityCode = getLastCityCode(cityCode);
        String apartmentId = sequenceUtil.generateKey(CommonProperties.getProperty(TBL100), CommonConstants.COL_APARTMENT_ID, lastCityCode);

        tbl100Entity.setApartmentId(apartmentId);
        tbl100Entity.setPropertyNumber(form.getPropertyNumber());
        tbl100Entity.setApartmentName(form.getApartmentName());
        tbl100Entity.setApartmentNamePhonetic(form.getApartmentNamePhonetic());
        tbl100Entity.setZipCode(convertZipCode(form.getZipCode()));
        tbl100Entity.setCityCode(cityCode);
        tbl100Entity.setAddress(form.getAddress());
        tbl100Entity.setNotificationKbn(CodeUtil.getValue(CommonConstants.CD029, form.getNotificationKbn()));
        tbl100Entity.setSupport(CodeUtil.getValue(CommonConstants.CD021, form.getSupport()));
        tbl100Entity.setNextNotificationDate(LocalDate.of(2020, Month.APRIL, 01));
        tbl100Entity.setBuildYear(form.getBuildYear());
        tbl100Entity.setBuildMonth(form.getBuildMonth());
        tbl100Entity.setBuildDay(form.getBuildDay());
        tbl100Entity.setFloorNumber(stringToInt(form.getFloorNumber()));
        tbl100Entity.setHouseNumber(stringToInt(form.getHouseNumber()));
        tbl100Entity.setSiteArea(stringToDouble(form.getSiteArea()));
        tbl100Entity.setTotalFloorArea(stringToDouble(form.getTotalFloorArea()));
        tbl100Entity.setBuildingArea(stringToDouble(form.getBuildingArea()));
        tbl100Entity.setBuildingStructure(form.getBuildingStructure());
        tbl100Entity.setRegistrationNo(form.getRegistrationNo());
        tbl100Entity.setRegistrationAddress(form.getRegistrationAddress());
        tbl100Entity.setRegistrationHouseNo(form.getRegistrationHouseNo());
        tbl100Entity.setRealEstateNo(form.getRealEstateNo());
        tbl100Entity.setApartmentLostFlag(CommonConstants.ZERO);
        tbl100Entity.setPreliminary1(form.getPreliminary1());
        tbl100Entity.setPreliminary2(form.getPreliminary2());
        tbl100Entity.setPreliminary3(form.getPreliminary3());
        tbl100Entity.setPreliminary4(form.getPreliminary4());
        tbl100Entity.setPreliminary5(form.getPreliminary5());
        tbl100Entity.setDeleteFlag(CommonConstants.ZERO);
        tbl100Entity.setUpdateUserId(SecurityUtil.getUserLoggedInInfo().getUserId());
        tbl100Entity.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1050_I, TBL100, tbl100Entity.getApartmentId()));
        return tbl100DAO.save(tbl100Entity);
    }

    /**
     * Save data to TBL105
     * 
     * @param tbl100Entity TBL100Entity
     * @return TBL105Entity
     * @throws ex ySstemException
     */
    private void saveDataToTbl105(TBL100Entity tbl100Entity) throws SystemException {
        TBL105Entity tbl105Entity = new TBL105Entity();
        String historyNumber = sequenceUtil.generateKey(CommonProperties.getProperty(TBL105),
                CommonConstants.COL_HISTORY_NUMBER);

        tbl105Entity.setHistoryNumber(historyNumber);
        CommonUtils.copyProperties(tbl105Entity, tbl100Entity);
        tbl105Entity.setDeleteFlag(CommonConstants.ZERO);
        tbl105Entity.setUpdateUserId(SecurityUtil.getUserLoggedInInfo().getUserId());
        tbl105Entity.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1050_I, TBL105, tbl105Entity.getApartmentId()));
        tbl105DAO.save(tbl105Entity);
    }

    /**
     * Save data to TBL110
     * 
     * @param tbl100Entity TBL100Entity
     * @return void
     */
    private void saveDataToTbl110(TBL100Entity tbl100Entity) {

        TBL110Entity tbl110Entity = new TBL110Entity();

        String apartmentId = tbl100Entity.getApartmentId();
        String loginId = sequenceUtil.generateKey(CommonProperties.getProperty("TBL110"), CommonConstants.COL_LOGIN_ID,
                UserTypeCode.NONE);
        String cityHeadWord = tbm001DAO.getCityHeadWordByCityCode(tbl100Entity.getCityCode());
        String password = loginId.concat(cityHeadWord);
        String passwordHash = new BCryptPasswordEncoder().encode(password);
        String updateUserId = SecurityUtil.getUserLoggedInInfo().getUserId();
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());

        tbl110Entity.setApartmentId(apartmentId);
        tbl110Entity.setLoginId(loginId);
        tbl110Entity.setPassword(passwordHash);
        tbl110Entity.setLoginErrorCount(CommonConstants.NUM_0);
        tbl110Entity.setAccountLockFlag(CommonConstants.ZERO);
        tbl110Entity.setValidityFlag(CommonConstants.TWO);
        tbl110Entity.setAvailability(CommonConstants.ONE);
        tbl110Entity.setBiginingPasswordChangeFlag(CommonConstants.ZERO);
        tbl110Entity.setLoginStatusFlag(CommonConstants.ZERO);
        tbl110Entity.setDeleteFlag(CommonConstants.ZERO);
        tbl110Entity.setUpdateUserId(updateUserId);
        tbl110Entity.setUpdateDatetime(currentDate);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1050_I, TBL110, tbl110Entity.getApartmentId()));
        tbl110DAO.save(tbl110Entity);
    }

    // define error message

    // required error message
    private String requiredMessage(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0002, name);
    }

    // half size error message
    private String halfSizeMessage(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0003, name);
    }

    // half size and decimal error message
    private String halfSizeDecimal(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0004, name);
    }

    // half size alphanumeric error message
    private String halfSizeAlphaMessage(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0005, name);
    }

    // full size error message
    private String fullSizeMessage(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0006, name);
    }

    // katakana error message
    private String katakanaMessage(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0007, name);
    }

    // max error message
    private String maxMessage(String name, String max) {
        return MessageUtil.getMessage(CommonConstants.MS_E0012, name, max);
    }
    
    // min error message
    private String minMessage(String name, String min) {
        return MessageUtil.getMessage(CommonConstants.MS_E0013, name, min);
    }

    // special character error message
    private String specialChar(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0015, name);
    }

    // half size and decimal error message
    private String halfSizeDecimalLimitMsg(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0016, name);
    }
    
    // check file row message
    private String fileRow(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0147, name);
    }

    // check equals message
    private String equalsMessage(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0100, name);
    }

    // check equals message
    private String existMessage(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0121, name);
    }
    
    // check file row message
    private String formatMessage() {
        return MessageUtil.getMessage(CommonConstants.MS_E0136);
    }


    /**
     * @param List<MansionInfoUploadForm> lstMansion
     * @param String countMax
     * @param List<String> lengthOfRowList
     * @return
     */
    private List<ZAA0150ErrorVo> validMansionInfo(List<MansionInfoUploadForm> lstMansion, String fileCountMax, List<String> lengthOfRowList) {

        List<ZAA0150ErrorVo> listMansionError = new ArrayList<ZAA0150ErrorVo>();
        List<String> listCityName = tbm001DAO.getListCityName();
        int fileCountMaxInt = Integer.parseInt(fileCountMax);
        // ファイルチェック（単項目）/File check (single item)
        for (int i = 0; i < lstMansion.size(); i++) {
            MansionInfoUploadForm mansion = lstMansion.get(i);

            // propertyNumber check
            if (!isAlphaNumeric(mansion.getPropertyNumber())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_PROPERTY_NUMBER,
                                     CommonConstants.E0005, halfSizeAlphaMessage(CommonConstants.MI_PROPERTY_NUMBER)));
            } else if (lengthOf(mansion.getPropertyNumber()) > 7) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_PROPERTY_NUMBER,
                                     CommonConstants.E0012, maxMessage(CommonConstants.MI_PROPERTY_NUMBER, CommonConstants.STR_7)));
            }

            // apartmentName check
            if (StringUtils.isEmpty(mansion.getApartmentName())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_APARTMENT_NAME, CommonConstants.E0002,
                                     requiredMessage(CommonConstants.MI_APARTMENT_NAME)));
            } else if (lengthOf(mansion.getApartmentName()) > 50) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_APARTMENT_NAME, CommonConstants.E0012,
                                     maxMessage(CommonConstants.MI_APARTMENT_NAME, CommonConstants.STR_50)));
            } else if (!CommonUtils.isNotSpecialCharacter(mansion.getApartmentName())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_APARTMENT_NAME, CommonConstants.E0015,
                                     specialChar(CommonConstants.MI_APARTMENT_NAME)));
            }

            // apartmentNamePhonetic check
            if (StringUtils.isEmpty(mansion.getApartmentNamePhonetic())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_APARTMENT_NAME_PHONETIC,
                                     CommonConstants.E0002, requiredMessage(CommonConstants.MI_APARTMENT_NAME_PHONETIC)));
            } else if (!isKatakanaString(mansion.getApartmentNamePhonetic())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_APARTMENT_NAME_PHONETIC,
                                     CommonConstants.E0007, katakanaMessage(CommonConstants.MI_APARTMENT_NAME_PHONETIC)));
            } else if (lengthOf(mansion.getApartmentNamePhonetic()) > 100) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_APARTMENT_NAME_PHONETIC, 
                                     CommonConstants.E0012, maxMessage(CommonConstants.MI_APARTMENT_NAME_PHONETIC, CommonConstants.STR_100)));
            }

            // zipCode check
            if (StringUtils.isEmpty(mansion.getZipCode())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_ZIPCODE, CommonConstants.E0002,
                                     requiredMessage(CommonConstants.MI_ZIPCODE)));
            } else if (!isHalfSizeRatio(mansion.getZipCode())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_ZIPCODE, CommonConstants.E0003,
                                     halfSizeMessage(CommonConstants.MI_ZIPCODE)));
            } else if (lengthOf(mansion.getZipCode()) < 7) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_ZIPCODE, CommonConstants.E0013,
                                     minMessage(CommonConstants.MI_ZIPCODE, CommonConstants.STR_7)));
            } else if (lengthOf(mansion.getZipCode()) > 7) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_ZIPCODE, CommonConstants.E0012,
                                     maxMessage(CommonConstants.MI_ZIPCODE, CommonConstants.STR_7)));
            }

            // cityName check
            if (StringUtils.isEmpty(mansion.getCityName())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_CITY_NAME, CommonConstants.E0002,
                                     requiredMessage(CommonConstants.MI_CITY_NAME)));
            } else if (!isFullSizeString(mansion.getCityName())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_CITY_NAME, CommonConstants.E0006,
                                     fullSizeMessage(CommonConstants.MI_CITY_NAME)));
            } else if (lengthOf(mansion.getCityName()) > 5) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_CITY_NAME, CommonConstants.E0012,
                                     maxMessage(CommonConstants.MI_CITY_NAME, CommonConstants.STR_5)));
            }

            // address check
            if (StringUtils.isEmpty(mansion.getAddress())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_ADDRESS, CommonConstants.E0002,
                                     requiredMessage(CommonConstants.MI_ADDRESS)));
            } else if (!isFullSizeString(mansion.getAddress())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_ADDRESS, CommonConstants.E0006,
                                     fullSizeMessage(CommonConstants.MI_ADDRESS)));
            } else if (lengthOf(mansion.getAddress()) > 100) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_ADDRESS, CommonConstants.E0012,
                                     maxMessage(CommonConstants.MI_ADDRESS, CommonConstants.STR_100)));
            }

            // notificationKbn check
            if (StringUtils.isEmpty(mansion.getNotificationKbn())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_NOTIFICATION_KBN,
                                     CommonConstants.E0002, requiredMessage(CommonConstants.MI_NOTIFICATION_KBN)));
            } else if (!isFullSizeString(mansion.getNotificationKbn())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_NOTIFICATION_KBN,
                                     CommonConstants.E0006, fullSizeMessage(CommonConstants.MI_NOTIFICATION_KBN)));
            } else if (lengthOf(mansion.getNotificationKbn()) > 2) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_NOTIFICATION_KBN,
                                     CommonConstants.E0012, maxMessage(CommonConstants.MI_NOTIFICATION_KBN, CommonConstants.STR_2)));
            }

            // support check
            if (StringUtils.isEmpty(mansion.getSupport())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_SUPPORT, CommonConstants.E0002,
                                     requiredMessage(CommonConstants.MI_SUPPORT)));
            } else if (!isFullSizeString(mansion.getSupport())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_SUPPORT, CommonConstants.E0006,
                                     fullSizeMessage(CommonConstants.MI_SUPPORT)));
            } else if (lengthOf(mansion.getSupport()) > 5) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_SUPPORT, CommonConstants.E0012,
                                     maxMessage(CommonConstants.MI_SUPPORT, CommonConstants.STR_5)));
            }

            // buildYear check
            if (StringUtils.isEmpty(mansion.getBuildYear())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_BUILD_YEAR, CommonConstants.E0002,
                                     requiredMessage(CommonConstants.MI_BUILD_YEAR)));
            } else if (!isHalfSizeRatio(mansion.getBuildYear())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_BUILD_YEAR, CommonConstants.E0003,
                                     halfSizeMessage(CommonConstants.MI_BUILD_YEAR)));
            } else if (lengthOf(mansion.getBuildYear()) > 4) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_BUILD_YEAR, CommonConstants.E0012,
                                     maxMessage(CommonConstants.MI_BUILD_YEAR, CommonConstants.STR_4)));
            }

            // buildMonth check
            if (StringUtils.isEmpty(mansion.getBuildMonth())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_BUILD_MONTH, CommonConstants.E0002,
                                     requiredMessage(CommonConstants.MI_BUILD_MONTH)));
            } else if (!isHalfSizeRatio(mansion.getBuildMonth())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_BUILD_MONTH, CommonConstants.E0003,
                                     halfSizeMessage(CommonConstants.MI_BUILD_MONTH)));
            } else if (lengthOf(mansion.getBuildMonth()) > 2) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_BUILD_MONTH, CommonConstants.E0012,
                                     maxMessage(CommonConstants.MI_BUILD_MONTH, CommonConstants.STR_2)));
            }

            // buildDay check
            if (StringUtils.isEmpty(mansion.getBuildDay())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_BUILD_DAY, CommonConstants.E0002,
                                     requiredMessage(CommonConstants.MI_BUILD_DAY)));
            } else if (!isHalfSizeRatio(mansion.getBuildDay())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_BUILD_DAY, CommonConstants.E0003,
                                     halfSizeMessage(CommonConstants.MI_BUILD_DAY)));
            } else if (lengthOf(mansion.getBuildDay()) > 2) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_BUILD_DAY, CommonConstants.E0012,
                                     maxMessage(CommonConstants.MI_BUILD_DAY, CommonConstants.STR_2)));
            }

            // floorNumber check
            if (!isHalfSizeRatio(mansion.getFloorNumber())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_FLOOR_NUMBER, CommonConstants.E0003,
                                     halfSizeMessage(CommonConstants.MI_FLOOR_NUMBER)));
            } else if (lengthOf(mansion.getFloorNumber()) > 2) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_FLOOR_NUMBER, CommonConstants.E0012,
                                     maxMessage(CommonConstants.MI_FLOOR_NUMBER, CommonConstants.STR_2)));
            }

            // houseNumber check
            if (!isHalfSizeRatio(mansion.getHouseNumber())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_HOUSE_NUMBER, CommonConstants.E0003,
                                     halfSizeMessage(CommonConstants.MI_HOUSE_NUMBER)));
            } else if (lengthOf(mansion.getHouseNumber()) > 6) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_HOUSE_NUMBER, CommonConstants.E0012,
                                     maxMessage(CommonConstants.MI_HOUSE_NUMBER, CommonConstants.STR_6)));
            }

            // siteArea check
            if (!isDecimalPoint(mansion.getSiteArea())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_SITE_AREA, CommonConstants.E0004,
                                     halfSizeDecimal(CommonConstants.MI_SITE_AREA)));
            } else if (!isDecimalPointLimit(mansion.getSiteArea(), 2)) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_SITE_AREA, CommonConstants.E0016,
                                     halfSizeDecimalLimitMsg(CommonConstants.STR_2)));
                
            } else if (lengthOf(mansion.getSiteArea()) > 11) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_SITE_AREA, CommonConstants.E0012,
                                     maxMessage(CommonConstants.MI_SITE_AREA, CommonConstants.STR_11)));
            }

            // totalFloorArea check
            if (!isDecimalPoint(mansion.getTotalFloorArea())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_TOTAL_FLOOR_AREA,
                                     CommonConstants.E0004, halfSizeDecimal(CommonConstants.MI_TOTAL_FLOOR_AREA)));
            } else if (!isDecimalPointLimit(mansion.getTotalFloorArea(), 2)) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_TOTAL_FLOOR_AREA,
                                     CommonConstants.E0016, halfSizeDecimalLimitMsg(CommonConstants.STR_2)));
                
            } else if (lengthOf(mansion.getTotalFloorArea()) > 11) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_TOTAL_FLOOR_AREA, CommonConstants.E0012,
                                     maxMessage(CommonConstants.MI_TOTAL_FLOOR_AREA, CommonConstants.STR_11)));
            }

            // buildingArea check
            if (!isDecimalPoint(mansion.getBuildingArea())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_BUILDING_AREA, CommonConstants.E0004,
                                     halfSizeDecimal(CommonConstants.MI_BUILDING_AREA)));
            } else if (!isDecimalPointLimit(mansion.getBuildingArea(), 2)) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_BUILDING_AREA, CommonConstants.E0016,
                                     halfSizeDecimalLimitMsg(CommonConstants.STR_2)));
                
            } else if (lengthOf(mansion.getBuildingArea()) > 11) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_BUILDING_AREA, CommonConstants.E0012,
                                     maxMessage(CommonConstants.MI_BUILDING_AREA, CommonConstants.STR_11)));
            }
            
            // buildingStructure check
            if (lengthOf(mansion.getBuildingStructure()) > 30) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_BUILDING_STRUCTURE, 
                                     CommonConstants.E0012, maxMessage(CommonConstants.MI_BUILDING_STRUCTURE, CommonConstants.STR_30)));
            } else if (!CommonUtils.isNotSpecialCharacter(mansion.getBuildingStructure())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_BUILDING_STRUCTURE,
                                     CommonConstants.E0015, specialChar(CommonConstants.MI_BUILDING_STRUCTURE)));
            }

            // registrationNo check
            if (lengthOf(mansion.getRegistrationNo()) > 8) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_REGISTRATION_NO,
                                     CommonConstants.E0012, maxMessage(CommonConstants.MI_REGISTRATION_NO, CommonConstants.STR_8)));
            } else if (!CommonUtils.isNotSpecialCharacter(mansion.getRegistrationNo())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_REGISTRATION_NO,
                                     CommonConstants.E0015, specialChar(CommonConstants.MI_REGISTRATION_NO)));
            }

            // registrationAddress check
            if (!isFullSizeString(mansion.getRegistrationAddress())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_REGISTRATION_ADDRESS,
                                     CommonConstants.E0006, fullSizeMessage(CommonConstants.MI_REGISTRATION_ADDRESS)));
            } else if (lengthOf(mansion.getRegistrationAddress()) > 30) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_REGISTRATION_ADDRESS,
                                     CommonConstants.E0012, maxMessage(CommonConstants.MI_REGISTRATION_ADDRESS, CommonConstants.STR_30)));
            }

            // registrationHouseNo check
            if (!isFullSizeString(mansion.getRegistrationHouseNo())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_REGISTRATION_HOUSE_NO,
                                     CommonConstants.E0006, fullSizeMessage(CommonConstants.MI_REGISTRATION_HOUSE_NO)));
            } else if (lengthOf(mansion.getRegistrationHouseNo()) > 12) {
                listMansionError
                        .add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_REGISTRATION_HOUSE_NO, CommonConstants.E0012,
                                                maxMessage(CommonConstants.MI_REGISTRATION_HOUSE_NO, CommonConstants.STR_12)));
            }

            // realEstateNo check
            if (!isAlphaNumeric(mansion.getRealEstateNo())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_REAL_ESTATE_NO, CommonConstants.E0005,
                        halfSizeAlphaMessage(CommonConstants.MI_REAL_ESTATE_NO)));
            } else if (lengthOf(mansion.getRealEstateNo()) > 14) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_REAL_ESTATE_NO, CommonConstants.E0012,
                                     maxMessage(CommonConstants.MI_REAL_ESTATE_NO, CommonConstants.STR_14)));
            }

            // preliminary 1 check
            if (lengthOf(mansion.getPreliminary1()) > 30) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_PRELIMINARY_1, CommonConstants.E0012,
                                     maxMessage(CommonConstants.MI_PRELIMINARY_1, CommonConstants.STR_30)));
            } else if (!CommonUtils.isNotSpecialCharacter(mansion.getPreliminary1())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_PRELIMINARY_1, CommonConstants.E0015,
                                     specialChar(CommonConstants.MI_PRELIMINARY_1)));
            }

            // preliminary 2 check
            if (lengthOf(mansion.getPreliminary2()) > 30) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_PRELIMINARY_2, CommonConstants.E0012,
                                     maxMessage(CommonConstants.MI_PRELIMINARY_2, CommonConstants.STR_30)));
            } else if (!CommonUtils.isNotSpecialCharacter(mansion.getPreliminary2())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_PRELIMINARY_2, CommonConstants.E0015,
                                     specialChar(CommonConstants.MI_PRELIMINARY_2)));
            }

            // preliminary 3 check
            if (lengthOf(mansion.getPreliminary3()) > 30) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_PRELIMINARY_3, CommonConstants.E0012,
                                     maxMessage(CommonConstants.MI_PRELIMINARY_3, CommonConstants.STR_30)));
            } else if (!CommonUtils.isNotSpecialCharacter(mansion.getPreliminary3())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_PRELIMINARY_3, CommonConstants.E0015,
                                     specialChar(CommonConstants.MI_PRELIMINARY_3)));
            }

            // preliminary 4 check
            if (lengthOf(mansion.getPreliminary4()) > 30) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_PRELIMINARY_4, CommonConstants.E0012,
                                     maxMessage(CommonConstants.MI_PRELIMINARY_4, CommonConstants.STR_30)));
            } else if (!CommonUtils.isNotSpecialCharacter(mansion.getPreliminary4())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_PRELIMINARY_4, CommonConstants.E0015,
                                     specialChar(CommonConstants.MI_PRELIMINARY_4)));
            }

            // preliminary 5 check
            if (lengthOf(mansion.getPreliminary5()) > 30) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_PRELIMINARY_5, CommonConstants.E0012,
                                     maxMessage(CommonConstants.MI_PRELIMINARY_5, CommonConstants.STR_30)));
            } else if (!CommonUtils.isNotSpecialCharacter(mansion.getPreliminary5())) {
                listMansionError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.MI_PRELIMINARY_5, CommonConstants.E0015,
                                     specialChar(CommonConstants.MI_PRELIMINARY_5)));
            }
        }
        
        // ファイルチェック（相関）/File check (correlation)
        if (listMansionError.isEmpty()) {
            for (int j = 0; j < lstMansion.size(); j++) {
                MansionInfoUploadForm mansion = lstMansion.get(j);
                // 重複チェック
                if (tbl100DAO.getCount(lstMansion.get(j).getApartmentName(), convertZipCode(lstMansion.get(j).getZipCode()),
                        lstMansion.get(j).getBuildYear()) != 0) {
                    listMansionError.add(new ZAA0150ErrorVo(String.valueOf(j + 1), CommonConstants.MI_APARTMENT_NAME,
                            CommonConstants.E0121, existMessage(CommonConstants.MI_APARTMENT_NAME)));
                }

                // ファイルの区市町村名を条件に区市町村マスターを検索し、該当レコードなしの場合、エラー
                if (!listCityName.contains(mansion.getCityName())) {
                    listMansionError.add(new ZAA0150ErrorVo(String.valueOf(j + 1), CommonConstants.MI_CITY_NAME,
                            CommonConstants.E0100, equalsMessage(CommonConstants.MI_CITY_NAME)));
                }

                // 届出必須区分の値がCD029のデコード値以外の場合、エラー
                if (CodeUtil.getValue(CommonConstants.CD029, mansion.getNotificationKbn(), NOT_FOUND).equals(NOT_FOUND)) {
                    listMansionError.add(new ZAA0150ErrorVo(String.valueOf(j + 1), CommonConstants.MI_NOTIFICATION_KBN,
                            CommonConstants.E0100, equalsMessage(CommonConstants.MI_NOTIFICATION_KBN)));
                }

                // 都支援対象の値がCD021のデコード値以外の場合、エラー
                if (CodeUtil.getValue(CommonConstants.CD021, mansion.getSupport(), NOT_FOUND).equals(NOT_FOUND)) {
                    listMansionError.add(new ZAA0150ErrorVo(String.valueOf(j + 1), CommonConstants.MI_SUPPORT,
                            CommonConstants.E0100, equalsMessage(CommonConstants.MI_SUPPORT)));
                }

                // 項目個数チェック
                if (Integer.parseInt(lengthOfRowList.get(j)) != 26) {
                    listMansionError.add(new ZAA0150ErrorVo(String.valueOf(j + 1), CommonConstants.BLANK,
                            CommonConstants.E0136, formatMessage()));
                }
            }
            // 上限数のチェック
            if (lstMansion.size() > fileCountMaxInt) {
                listMansionError.add(new ZAA0150ErrorVo(CommonConstants.STR_1, CommonConstants.BLANK,
                        CommonConstants.E0147, fileRow(fileCountMax)));
            }
        }
        
        return listMansionError;
    }

    /**
     * Get Last 4 character of City Code
     * 
     * @param cityCode String
     * @return cityCode
     */
    private String getLastCityCode(String cityCode) {
        return GenericValidator.isBlankOrNull(cityCode) ? CommonConstants.BLANK : cityCode.substring(2);
    }

    /**
     * @param str String
     * @return int
     */
    private int stringToInt(String str) {
        return StringUtils.isNotEmpty(str) ? Integer.parseInt(str) : 0;
    }

    /**
     * @param str String
     * @return int
     */
    private double stringToDouble(String str) {
        return StringUtils.isNotEmpty(str) ? Double.parseDouble(str) : 0.00;
    }
    
    /**
     * get Length of String, if null -> length = 0
     * 
     * @param str String
     * @return
     */
    private int lengthOf(String str) {
        return (null == str) ? 0 : str.length();
    }

    /**
     * alpha numeric check
     * 
     * @param input String
     * @return
     */
    public boolean isAlphaNumeric(String input) {

        if (null == input) {
            return true;
        }

        return CheckUtil.isPatternMatch("/^[A-Za-z0-9+-.@/]*$/", input);
    }

    /**
     * katakana check
     * 
     * @param str String
     * @return
     */
    private boolean isKatakanaString(String str) {

        if (null == str) {
            return true;
        }

        String pattern = "/^[ァ-・ヽヾ゛゜ー　]*$/";

        // 全角カタカナチェック
        return CheckUtil.isPatternMatch(pattern, str);
    }

    /**
     * halfsize ratio check
     * 
     * @param ratio String
     * @return
     */
    private boolean isHalfSizeRatio(String ratio) {
        if (null == ratio) {
            return true;
        }
        return CheckUtil.isPatternMatch("/^[\\.0-9]*$/", ratio);
    }

    /**
     * fullsize string check
     * 
     * @param input String
     * @return
     */
    private boolean isFullSizeString(String input) {

        if (null == input) {
            return true;
        }
        return CheckUtil.isValidX0208(input);
    }
    
    /**
     * Returns true if the input is the decimal point
     * @param input String
     * @return
     */
    private static boolean isDecimalPoint(String input) {
        if (GenericValidator.isBlankOrNull(input)) {
            return true;
        }

        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * decimal point limit check
     * 
     * @param input String
     * @param val int
     * @return
     */
    private boolean isDecimalPointLimit(String input, int val) {
        if (GenericValidator.isBlankOrNull(input)) {
            return true;
        }

        if (CommonUtils.isDecimalPoint(input)) {
            String[] decimalSplits = input.split("\\.");
            return (decimalSplits.length == 2 && decimalSplits[1].length() == val);
        }
        return false;
    }
    
    /**
     * convert zipCode from csv for insert DB
     * ex: 1234567 -> 123-4567
     * 
     * @param zipCode String
     * @return
     */
    private String convertZipCode(String zipCode) {
        return (StringUtils.isEmpty(zipCode)) ? zipCode : zipCode.substring(0, 3) + CommonConstants.DASH + zipCode.substring(3);
    }
}
