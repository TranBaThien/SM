/*
 * @(#) UserRegistrationLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.GenericValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonProperties;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SequenceUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL400DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM001DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL400Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM001Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.ManagementAssociationApplicationForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IUserRegistrationLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.CityVo;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL400;

/**
 * @author lthai
 *
 */
@Service
public class UserRegistrationLogicImpl implements IUserRegistrationLogic {
    private static final Logger logger = LogManager.getLogger(UserRegistrationLogicImpl.class);
    private static final DateTimeFormatter FORMAT_YYYYMMDDHHMMSS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String SEPARATOR = "-";
    
    @Autowired
    private BCryptPasswordEncoder encoder;
    
    @Autowired
    private TBL400DAO tbl400DAO;
    
    @Autowired
    private TBM001DAO tbm001DAO;
    
    @Autowired
    private SequenceUtil sequenceUtil;

    /**
     * Save management association application information - 管理組合申込情報登録
     * 
     * @param form ManagementAssociationApplicationForm
     * @throws BusinessException BusinessException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void saveManagementAssociationApplicationInformation(ManagementAssociationApplicationForm form) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        LocalDateTime localDateTime = LocalDateTime.now();
        TBL400Entity entity = new TBL400Entity();
        /* APPLICATION_NO - 「別紙_ID定義書（申込番号）」参照 */
        String keyNo = sequenceUtil.generateKey(CommonProperties.getProperty(TBL400),
                CommonConstants.COL_APPLICATION_NO);
        /* ZIP_CODE - 郵便番号1と”-”（半角ハイフン）と郵便番号2を連結して設定する */
        String zipCode = formatZipCode(form.getTxtApartmentZipCode1(), form.getTxtApartmentZipCode2());
        /* CITY_CODE - 選択された区市町村コードを設定する */
        String cityCode = tbm001DAO.getCityCodeByCityName(form.getLstApartmentAddress1());
        /* CONTACT_ZIP_CODE - 郵便番号1と”-”（半角ハイフン）と郵便番号2を連結して設定する */
        String contactZipCode = formatZipCode(form.getTxtContactZipCode1(), form.getTxtContactZipCode2());
        /* TEL_NO - 電話番号1と”-”（半角ハイフン）と電話番号2と”-”（半角ハイフン）と電話番号3を連結して設定する */
        String contactTelNo = formatTelNo(form.getTxtContactTelno1(), form.getTxtContactTelno2(),
                form.getTxtContactTelno3());
        /* PASSWORD - パスワードをハッシュ化して設定する */
        String pwd = encoder.encode(form.getTxtTempPassword());
        /* APPLICATION_TIME - システム日時を設定するYYYYMMDDHH24MISS */
        LocalDateTime applicationTime = localDateTime;
        /* JUDGE_RESULT - 「未審査」の値を設定する（「コード定義書（CD016）」参照） */
        String judgeResult = CodeUtil.getValue(CommonConstants.CD016, CommonConstants.CD016_UNREVIEWED);
        /* DELETE_FLAG - 「未削除」の値を設定する（「コード定義書（CD001）」参照） */
        String deleteFlag = CodeUtil.getValue(CommonConstants.CD001, CommonConstants.CD001_UNDELETEFLAG);
        /* UPDATE_DATETIME - システム日時を設定する */
        Timestamp updateDateTime = Timestamp.valueOf(localDateTime.format(FORMAT_YYYYMMDDHHMMSS));

        /* APPLICATION_NO - 「別紙_ID定義書（申込番号）」参照 */
        entity.setApplicationNo(keyNo);
        /* APARTMENT_NAME */
        entity.setApartmentName(form.getTxtApartmentName());
        /* APARTMENT_NAME_PHONETIC */
        entity.setApartmentNamePhonetic(form.getTxtApartmentNamePhonetic());
        /* ZIP_CODE = ApartmentZipCode1 + '-' + ApartmentZipCode2 */
        entity.setZipCode(zipCode);
        /* CITY_CODE */
        entity.setCityCode(cityCode);
        /* ADDRESS */
        entity.setAddress(form.getTxtApartmentAddress2());
        /* PROPERTY_CODE */
        entity.setContactPropertyCode(form.getRdoContactProperty());
        /* PROPERTY_ELSE */
        entity.setContactPropertyElse(form.getTxtContactPropertyElse());
        /* ZIP_CODE = txtContactZipCode1 + txtContactZipCode2 */
        entity.setContactZipCode(contactZipCode);
        /* ADDRESS */
        entity.setContactAddress(form.getTxtContactAddress());
        /* TEL_NO = txtContactTelno1 + txtContactTelno2 + txtContactTelno3 */
        entity.setContactTelNo(contactTelNo);
        /* CONTACT_NAME */
        entity.setContactName(form.getTxtContactName());
        /* CONTACT_NAME_PHONETIC */
        entity.setContactNamePhonetic(form.getTxtContactNamePhonetic());
        /* CONTACT_MAIL_ADDRESS */
        entity.setContactMailAddress(form.getTxtContactMail());
        /* PASSWORD */
        entity.setPassword(pwd);
        /* APPLICATION_TIME - システム日時を設定するYYYYMMDDHH24MISS */
        entity.setApplicationTime(applicationTime);
        /* JUDGE_RESULT - 「未審査」の値を設定する（「コード定義書（CD016）」参照） */
        entity.setJudgeResult(judgeResult);
        /* DELETE_FLAG - 「未削除」の値を設定する（「コード定義書（CD001）」参照） */
        entity.setDeleteFlag(deleteFlag);
        /* UPDATE_DATETIME - システム日時を設定する */
        entity.setUpdateDatetime(updateDateTime);
        // Save data to table tbl400
        tbl400DAO.save(entity);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1050_I, TBL400, entity.getApplicationNo()));
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
    }

    /**
     * Format zipcode - 郵便番号1と”-”（半角ハイフン）と郵便番号2を連結して設定する
     * 
     * @param zipCode1 String
     * @param zipCode2 String
     * @return
     */
    private String formatZipCode(String zipCode1, String zipCode2) {
        if (GenericValidator.isBlankOrNull(zipCode1) || GenericValidator.isBlankOrNull(zipCode2)) {
            return CommonConstants.BLANK;
        }
        StringBuffer results = new StringBuffer();
        results.append(CommonUtils.trim(zipCode1));
        results.append(SEPARATOR);
        results.append(CommonUtils.trim(zipCode2));
        return results.toString();
    }

    /**
     * Format telephone number - 電話番号1と”-”（半角ハイフン）と電話番号2と”-”（半角ハイフン）と電話番号3を連結して設定する
     * 
     * @param telNo1 String
     * @param telNo2 String
     * @param telNo3 String
     * @return
     */
    private String formatTelNo(String telNo1, String telNo2, String telNo3) {
        if (GenericValidator.isBlankOrNull(telNo1) || GenericValidator.isBlankOrNull(telNo2)
                || GenericValidator.isBlankOrNull(telNo3)) {
            return CommonConstants.BLANK;
        }
        StringBuffer results = new StringBuffer();
        results.append(CommonUtils.trim(telNo1));
        results.append(SEPARATOR);
        results.append(CommonUtils.trim(telNo2));
        results.append(SEPARATOR);
        results.append(CommonUtils.trim(telNo3));
        return results.toString();
    }

    /**
     * Get List CityName and CityCode
     * 
     * @return getMunicipalMasterInformation List
     */
    @Override
    public List<CityVo> getMunicipalMasterInformation() {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        // Get list city from TBM001
        List<TBM001Entity> listcity = tbm001DAO.getListCity();

        List<CityVo> listcityvo = new ArrayList<>();

        // browse the city list
        for (TBM001Entity city : listcity) {
            CityVo cityvo = new CityVo();

            cityvo.setCityCode(city.getCityCode());
            cityvo.setCityName(city.getCityName());

            // add new cityVo element to list cityVo
            listcityvo.add(cityvo);
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return listcityvo;
    }
}
