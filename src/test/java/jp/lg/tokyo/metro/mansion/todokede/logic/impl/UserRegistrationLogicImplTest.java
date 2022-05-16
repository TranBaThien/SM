/*
 * @(#) UserRegistrationLogicImplTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: Dec 12, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import ch.qos.logback.classic.Level;
import jp.lg.tokyo.metro.mansion.todokede.LogAppender;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonProperties;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SequenceUtil;
import jp.lg.tokyo.metro.mansion.todokede.config.CodeUtilConfig;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL400DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM001DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM001Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.ManagementAssociationApplicationForm;
import jp.lg.tokyo.metro.mansion.todokede.vo.CityVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.SystemException;
import java.util.ArrayList;
import java.util.List;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL400;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author lthai
 * @Screen MCA0110
 *
 */
@RunWith(SpringRunner.class)
@Import(CodeUtilConfig.class)
public class UserRegistrationLogicImplTest {

    @Mock
    private BCryptPasswordEncoder encoder;

    @Mock
    private TBL400DAO tbl400DAO;

    @Mock
    private TBM001DAO tbm001DAO;

    @InjectMocks
    private UserRegistrationLogicImpl userRegistrationLogic;

    @Mock
    private SequenceUtil sequenceUtil;

    private static final String FAILED_HANDLER_PATH = "jp.lg.tokyo.metro.mansion.todokede.logic.impl.UserRegistrationLogicImpl";
    private static final int FIRST_LOGGING = 0;

    private static final String APARTMENT_ZIPCODE_1 = "111";
    private static final String APARTMENT_ZIPCODE_2 = "1111";
    private static final String APARTMENT_ADDRESS_1 = "123";
    private static final String CONTACT_ZIPCODE_1 = "123";
    private static final String CONTACT_ZIPCODE_2 = "1234";
    private static final String CONTACT_TELNO_1 = "123";
    private static final String CONTACT_TELNO_2 = "111";
    private static final String CONTACT_TELNO_3 = "222";
    private static final String TEMP_PASSWORD = "123456";
    private static final String APARTMENT_NAME = "マンション名";
    private static final String APARTMENT_NAME_PHONETIC = "レオパレース";
    private static final String APARTMENT_ADDRESS_2 = "東京都八王子市石川町１一１一１";
    private static final String RDO_CONTACT_PROPERTY = "1";
    private static final String CONTACT_PROPERTY_ELSE = "";
    private static final String CONTACT_ADDRESS = "東京都八王子市石川町２４４７一１";
    private static final String CONTACT_NAME = "沖縄高菜";
    private static final String CONTACT_NAME_PHONETIC = "タキャナー・オーキナーワ";
    private static final String CONTACT_MAIL = "test@gmail.com";

    /**
     * Generate ManagementAssociationApplicationForm
     * 
     * @return
     */
    private ManagementAssociationApplicationForm generateManagementAssociationApplicationForm(String zipCode1, String zipCode2, String telNo1, String telNo2, String telNo3) {
        ManagementAssociationApplicationForm form = new ManagementAssociationApplicationForm();
        form.setTxtApartmentName(APARTMENT_NAME);
        form.setTxtApartmentNamePhonetic(APARTMENT_NAME_PHONETIC);
        form.setTxtApartmentZipCode1(zipCode1);
        form.setTxtApartmentZipCode2(zipCode2);
        form.setLstApartmentAddress1(APARTMENT_ADDRESS_1);
        form.setTxtApartmentName(APARTMENT_ADDRESS_2);
        form.setRdoContactProperty(RDO_CONTACT_PROPERTY);
        form.setRdoContactProperty(CONTACT_PROPERTY_ELSE);
        form.setTxtContactZipCode1(CONTACT_ZIPCODE_1);
        form.setTxtContactZipCode2(CONTACT_ZIPCODE_2);
        form.setTxtContactTelno1(telNo1);
        form.setTxtContactTelno2(telNo2);
        form.setTxtContactTelno3(telNo3);
        form.setTxtContactName(CONTACT_NAME);
        form.setTxtContactNamePhonetic(CONTACT_NAME_PHONETIC);
        form.setTxtContactAddress(CONTACT_ADDRESS);
        form.setTxtContactMail(CONTACT_MAIL);
        form.setTxtContactMailRe(CONTACT_MAIL);
        form.setTxtTempPassword(TEMP_PASSWORD);
        form.setTxtTempPassword(TEMP_PASSWORD);
        return form;
    }

    /**
     * Generate TBM001 Entity List
     * 
     * @return List<TBM001Entity>
     */
    private List<TBM001Entity> generateTBM001EntityList() {
        List<TBM001Entity> lst = new ArrayList<>();
        TBM001Entity entity = new TBM001Entity();
        entity.setCityCode("132055");
        entity.setCityName("青梅市");
        lst.add(entity);
        entity = new TBM001Entity();
        entity.setCityCode("134023");
        entity.setCityName("青ヶ島村");
        lst.add(entity);
        return lst;
    }

    /**
     * @param list, entity
     * @param entity
     */
    private void assertResult(List<CityVo> list, List<TBM001Entity> entity) {
        assertThat(list.getClass()).isEqualTo(entity.getClass());
    }

    /**
     * * 案件ID：MCA0110／チェックリストID：UT- MCA0110-001／区分：N／チェック内容：Test get municipal
     * master information when entity not null
     * 
     * @throws BusinessException
     * @throws SystemException
     */
    @Test
    public void testGetMunicipalMasterInformationWhenEntityNotNull() throws BusinessException, SystemException {
        List<TBM001Entity> listEntity = generateTBM001EntityList();
        Mockito.when(tbm001DAO.getListCity()).thenReturn(listEntity);
        List<CityVo> result = userRegistrationLogic.getMunicipalMasterInformation();
        assertResult(result, listEntity);
    }

    /**
     * *案件ID：MCA0110／チェックリストID：UT- MCA0110-002／区分：E／チェック内容：Test get municipal
     * master information when entity is empty
     * 
     * @throws BusinessException
     */
    @Test
    public void testGetMunicipalMasterInformationWhenEntityIsNull() throws BusinessException {
        Mockito.when(tbm001DAO.getListCity()).thenReturn(new ArrayList<TBM001Entity>());
        List<CityVo> result = userRegistrationLogic.getMunicipalMasterInformation();
        assertThat(result).isEqualTo(new ArrayList<CityVo>());
    }

    /**
     * 案件ID：MCA0110／チェックリストID：UT- MCA0110-003／区分：N／チェック内容：Test save management
     * association application information when form not null
     * 
     * @throws BusinessException
     */
    @Test
    public void testSaveManagementAssociationApplicationInformationWhenNotNull() throws BusinessException {
        LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
        Mockito.when(
                sequenceUtil.generateKey(CommonProperties.getProperty(TBL400), CommonConstants.COL_APPLICATION_NO))
                .thenReturn("10000004");
        Mockito.when(tbm001DAO.getCityCodeByCityName(Mockito.anyString())).thenReturn("132055");
        userRegistrationLogic
                .saveManagementAssociationApplicationInformation(generateManagementAssociationApplicationForm(CONTACT_ZIPCODE_1, CONTACT_ZIPCODE_2, CONTACT_TELNO_1, CONTACT_TELNO_2, CONTACT_TELNO_3));
        // Verified logger
        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getMessage())
                .isEqualTo("登録を実行します。テーブル：TBL400、キー：10000004");
    }
    
       /**
     * 案件ID：MCA0110／チェックリストID：UT- MCA0110-004／区分：N／チェック内容：Test save management
     * association application information when form not null
     * 
     * @throws BusinessException
     */
    @Test
    public void testSaveManagementAssociationApplicationInformationWhenZipCodeIsNull() throws BusinessException {
        LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
        Mockito.when(
                sequenceUtil.generateKey(CommonProperties.getProperty(TBL400), CommonConstants.COL_APPLICATION_NO))
                .thenReturn("10000004");
        Mockito.when(tbm001DAO.getCityCodeByCityName(Mockito.anyString())).thenReturn("132055");
        userRegistrationLogic
                .saveManagementAssociationApplicationInformation(generateManagementAssociationApplicationForm(CommonConstants.BLANK, CONTACT_ZIPCODE_2, CONTACT_TELNO_1, CONTACT_TELNO_2, CONTACT_TELNO_3));
        // Verified logger
        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getMessage())
                .isEqualTo("登録を実行します。テーブル：TBL400、キー：10000004");
    }
    
    
    /**
      * 案件ID：MCA0110／チェックリストID：UT- MCA0110-005／区分：N／チェック内容：Test save management
      * association application information when form not null
      * 
      * @throws BusinessException
      */
     @Test
     public void testSaveManagementAssociationApplicationInformationWhenTelNoIsNull() throws BusinessException {
         LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
         Mockito.when(
                 sequenceUtil.generateKey(CommonProperties.getProperty(TBL400), CommonConstants.COL_APPLICATION_NO))
                 .thenReturn("10000004");
     Mockito.when(tbm001DAO.getCityCodeByCityName(Mockito.anyString())).thenReturn("132055");
     userRegistrationLogic
             .saveManagementAssociationApplicationInformation(generateManagementAssociationApplicationForm(CONTACT_ZIPCODE_1, CONTACT_ZIPCODE_2, CommonConstants.BLANK, CONTACT_TELNO_2, CONTACT_TELNO_3));
     // Verified logger
     assertThat(logAppender.getEvents().get(FIRST_LOGGING).getLevel()).isEqualTo(Level.INFO);
     assertThat(logAppender.getEvents().get(FIRST_LOGGING).getMessage())
             .isEqualTo("登録を実行します。テーブル：TBL400、キー：10000004");
     }
    /**
     * 案件ID：MCA0110／チェックリストID：UT- MCA0110-006／区分：E／チェック内容：Test save management
     * association application information fail when form is null
     * 
     * @throws BusinessException
     */
    @Test(expected = NullPointerException.class)
    public void testSaveManagementAssociationApplicationInformationIsNull() throws BusinessException {
        userRegistrationLogic.saveManagementAssociationApplicationInformation(null);
    }
}
