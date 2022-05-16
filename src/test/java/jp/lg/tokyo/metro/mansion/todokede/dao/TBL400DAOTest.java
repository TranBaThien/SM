/*
 * @(#) TBL400DAOTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/11/28
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.dao;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonProperties;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SequenceUtil;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL400Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.vo.ManagementAssociationCustomVo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL400;
import static org.assertj.core.api.Assertions.assertThat;

public class TBL400DAOTest extends AbstractDaoTest { 
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private TBL400DAO tbl400DAO;

    @Autowired
    private TBM001DAO tbm001DAO;

    @Autowired
    private SequenceUtil sequenceUtil;

	@Autowired
	@Before
	public void setUp() throws Exception {
		
	}

    private final static String APARTMENT_ZIPCODE_1 = "111";
    private final static String APARTMENT_ZIPCODE_2 = "1111";
    private final static String APARTMENT_ADDRESS_1 = "123";
    private final static String CONTACT_ZIPCODE_1 = "123";
    private final static String CONTACT_ZIPCODE_2 = "1234";
    private final static String CONTACT_TELNO_1 = "123";
    private final static String CONTACT_TELNO_2 = "111";
    private final static String CONTACT_TELNO_3 = "222";
    private final static String TEMP_PASSWORD = "123456";
    private final static String APARTMENT_NAME = "マンション名";
    private final static String APARTMENT_NAME_PHONETIC = "レオパレース";
    private final static String APARTMENT_ADDRESS_2 = "東京都八王子市石川町１一１一１";
    private final static String RDO_CONTACT_PROPERTY = "1";
    private final static String CONTACT_PROPERTY_ELSE = "";
    private final static String CONTACT_ADDRESS = "東京都八王子市石川町２４４７一１";
    private final static String CONTACT_NAME = "沖縄高菜";
    private final static String CONTACT_NAME_PHONETIC = "タキャナー・オーキナーワ";
    private final static String CONTACT_MAIL = "test@gmail.com";

    /**
     * Generate TBL400 Entity
     * 
     * @return TBL400Entity
     */

    private TBL400Entity generateTBL400Entity() {
        TBL400Entity entity = new TBL400Entity();

        String keyNo = sequenceUtil.generateKey(CommonProperties.getProperty(TBL400),
                CommonConstants.COL_APPLICATION_NO);
        String zipCode = APARTMENT_ZIPCODE_1 + "-" + APARTMENT_ZIPCODE_2;
        String cityCode = tbm001DAO.getCityCodeByCityName(APARTMENT_ADDRESS_1);
        String contactZipCode = CONTACT_ZIPCODE_1 + "-" + CONTACT_ZIPCODE_2;
        String contactTelNo = CONTACT_TELNO_1 + "-" + CONTACT_TELNO_2 + "-" + CONTACT_TELNO_3;
        String pwd = encoder.encode(TEMP_PASSWORD);
        LocalDateTime applicationTime = LocalDateTime.now();
        String judgeResult = CodeUtil.getValue(CommonConstants.CD016, CommonConstants.CD016_UNREVIEWED);
        String deleteFlag = CodeUtil.getValue(CommonConstants.CD001, CommonConstants.CD001_UNDELETEFLAG);
        Timestamp updateDateTime = DateTimeUtil.getCurrentSystemDateTime();

        entity.setApplicationNo(keyNo);
        entity.setApartmentName(APARTMENT_NAME);
        entity.setApartmentNamePhonetic(APARTMENT_NAME_PHONETIC);
        entity.setZipCode(zipCode);
        entity.setCityCode(cityCode);
        entity.setAddress(APARTMENT_ADDRESS_2);
        entity.setContactPropertyCode(RDO_CONTACT_PROPERTY);
        entity.setContactPropertyElse(CONTACT_PROPERTY_ELSE);
        entity.setContactZipCode(contactZipCode);
        entity.setContactAddress(CONTACT_ADDRESS);
        entity.setContactTelNo(contactTelNo);
        entity.setContactName(CONTACT_NAME);
        entity.setContactNamePhonetic(CONTACT_NAME_PHONETIC);
        entity.setContactMailAddress(CONTACT_MAIL);
        entity.setPassword(pwd);
        entity.setApplicationTime(applicationTime);
        entity.setJudgeResult(judgeResult);
        entity.setDeleteFlag(deleteFlag);
        entity.setUpdateDatetime(updateDateTime);
        return entity;
    }
    
    private TBL400Entity generateTBL400Entity2() {
        TBL400Entity entity = new TBL400Entity();
        entity.setApplicationNo("1000000013");
        entity.setApartmentName(APARTMENT_NAME);
        entity.setApartmentNamePhonetic(APARTMENT_NAME_PHONETIC);
        entity.setZipCode("141-0022");
        entity.setCityCode("131091");
        entity.setAddress("東京都八王子市石川町");
        entity.setContactPropertyCode("1");
        entity.setContactPropertyElse("連絡先属性その他");
        entity.setContactZipCode("192-0032");
        entity.setContactAddress("東京都八王子市石川町");
        entity.setContactTelNo("123-1111-1111");
        entity.setContactName("フリガナ");
        entity.setContactNamePhonetic("タキャナー・オーキナーワ");
        entity.setContactMailAddress("takana@gmail.com");
        entity.setPassword("$2a$10$uWhBHLWlifFYQwg.tNbV/uQF3Orphd.Qb3eADa./CCIJkS.oUDDdS");
        entity.setApplicationTime(LocalDateTime.now());
        entity.setJudgeResult("1");
        entity.setDeleteFlag("0");
        entity.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
        return entity;
    }

    /**
     * 案件ID：MCA0110／チェックリストID：UT- MCA0110-001／区分: I／チェック内容：Test save data to tbl400
     * when success
     * 
     * @throws BusinessException
     */
    @Test
    public void testSaveTBL400Success() throws BusinessException {
        // Prepare data
        TBL400Entity expected = generateTBL400Entity();

        // Execute
        TBL400Entity result = tbl400DAO.save(expected);
        
        assertThat(result).isEqualToComparingFieldByField(expected);
    }

    /**
     * 案件ID：MCA0110／チェックリストID：UT- MCA0110-002／区分：E／チェック内容：Test save data to tbl400
     * when fail and throw exception
     * 
     * @throws BusinessException
     */
    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void testSaveTBL400WhenFailThrowException() throws BusinessException {
        // Execute
        tbl400DAO.save(null);
    }
    
    
    /**
     * 案件ID:GCA0110/チェックリストID:UT- GCA0110-001/区分:N/チェック内容: test get List Management Association
     * 
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GCA0110-001.sql")
    public void TestgetListManagementAssociation() {
        
        Pageable pageable = PageRequest.of(0, 50);  
        
        LocalDateTime APPLICATIONTIME = LocalDateTime.of(2019, 12, 04, 10, 10, 10);
        
        ManagementAssociationCustomVo mavo = new ManagementAssociationCustomVo();
        
        mavo.setApplicationNo("1");
        mavo.setApplicationTime(APPLICATIONTIME);
        mavo.setApartmentName("アパート 1");
        mavo.setAddress("12 NA");
        mavo.setCityCode("131059");
        mavo.setCityName("文京区");
        mavo.setJudgeResult("1"); 
        

        Page<ManagementAssociationCustomVo> pageModel = tbl400DAO.getListManagementAssociation(pageable);

        for(ManagementAssociationCustomVo vo : pageModel.getContent()) {
            assertThat(vo.getApplicationNo()).isEqualTo(mavo.getApplicationNo());
            assertThat(vo.getApplicationTime()).isEqualTo(mavo.getApplicationTime());
            assertThat(vo.getApartmentName()).isEqualTo(mavo.getApartmentName());
            assertThat(vo.getAddress()).isEqualTo(mavo.getAddress());
            assertThat(vo.getCityCode()).isEqualTo(mavo.getCityCode());
            assertThat(vo.getCityName()).isEqualTo(mavo.getCityName());
            assertThat(vo.getJudgeResult()).isEqualTo(mavo.getJudgeResult());
        }
        
    }
    
    
    /**
     * 案件ID:GCA0110/チェックリストID:UT- GCA0110-002/区分:N/チェック内容: test count Management Association Number
     * 
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GCA0110-001.sql")
    public void TestcountManagementAssociationNumber() {
        
        int result = tbl400DAO.countManagementAssociationNumber();
        
        assertThat(result).isEqualTo(1);
        
    }
    
    
    /**
     * 案件ID:GCA0110/チェックリストID:UT- GCA0110-003/区分:I/チェック内容: test get List Management Association Again
     * 
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GCA0110-002.sql")
    public void TestgetListManagementAssociationAgain() {
        
        List<ManagementAssociationCustomVo> listtest = new ArrayList<ManagementAssociationCustomVo>();
        
        LocalDateTime APPLICATIONTIME = LocalDateTime.of(2019, 12, 04, 10, 10, 10);
        
        ManagementAssociationCustomVo mavo = new ManagementAssociationCustomVo();
        
        mavo.setApplicationNo("1");
        mavo.setApplicationTime(APPLICATIONTIME);
        mavo.setApartmentName("アパート 1");
        mavo.setAddress("12 NA");
        mavo.setCityCode("131059");
        mavo.setCityName("文京区");
        mavo.setJudgeResult("1"); 
        
        List<String> listapplyno = new ArrayList<String>(); 
        
        listapplyno.add("1");
        
        List<ManagementAssociationCustomVo> list = tbl400DAO.getListManagementAssociationAgain(listapplyno);
        
        for(ManagementAssociationCustomVo vo : list) {
            assertThat(vo.getApplicationNo()).isEqualTo(mavo.getApplicationNo());
            assertThat(vo.getApplicationTime()).isEqualTo(mavo.getApplicationTime());
            assertThat(vo.getApartmentName()).isEqualTo(mavo.getApartmentName());
            assertThat(vo.getAddress()).isEqualTo(mavo.getAddress());
            assertThat(vo.getCityCode()).isEqualTo(mavo.getCityCode());
            assertThat(vo.getCityName()).isEqualTo(mavo.getCityName());
            assertThat(vo.getJudgeResult()).isEqualTo(mavo.getJudgeResult());
        }
        
    }
    
    /**
     * 案件ID:GCA0120/チェックリストID:UT- GCA0120-001/区分:N/チェック内容: test get List Management
     * Association
     * 
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GCA0120-001.sql")
   public void testGetNewRegistrationInformation() {
       
        ManagementAssociationCustomVo vo = new ManagementAssociationCustomVo("12", "東京都八王子市石川町", "131091", "1",
                "", APARTMENT_NAME_PHONETIC, "141-0022", "マンション名17", "1", "連絡先属性その他", "192-0032", "東京都八王子市石川町",
                "123-1111-1111", "フリガナ", "タキャナー・オーキナーワ", "takana@gmail.com", "", "", "", new Date(), "", "");
        
        ManagementAssociationCustomVo actual = tbl400DAO.getNewRegistrationInformation("12");

        assertThat(actual.getApplicationNo()).isEqualTo(vo.getApplicationNo());
        assertThat(actual.getApartmentName()).isEqualTo(vo.getApartmentName());
        assertThat(actual.getZipCode()).isEqualTo(vo.getZipCode());
        assertThat(actual.getCityCode()).isEqualTo(vo.getCityCode());
        assertThat(actual.getAddress()).isEqualTo(vo.getAddress());
        assertThat(actual.getContactPropertyCode()).isEqualTo(vo.getContactPropertyCode());
        assertThat(actual.getContactPropertyElse()).isEqualTo(vo.getContactPropertyElse());
       
    }

}
