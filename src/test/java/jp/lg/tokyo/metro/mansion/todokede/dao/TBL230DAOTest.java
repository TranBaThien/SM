/*
 * @(#) TBL230DAOTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/12/25
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.jdbc.Sql;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL230Entity;

/**
 * @author tqvu1
 *
 */
public class TBL230DAOTest extends AbstractDaoTest {

    @Autowired
    private TBL230DAO tbl230DAO;

    private final String INVESTIGATION_NO = "1000000001";
    private final String APARTMENT_ID = "1000000001";
    private final String APPENDIX_NO = "第　　号様式（第　　条関係）";
    private final String DOCUMENT_NO = "2019中央区文書番号初期値第　号";
    private final String NOTICE_DATE = "20191225";
    private final String NOTICE_DESTINATION_CODE = "1";
    private final String RECIPIENT_NAME_APARTMENT = "Tokyo";
    private final String RECIPIENT_NAME_USER = "管理組合理事長";
    private final String SENDER = "東京都知事山本　泰人";
    private final String ADDRESS = "0001　130001Da Nang";
    private final String APARTMENT_NAME = "Tokyo";
    private final String INVEST_IMPL_PLAN_TIME = "201912251413";
    private final int INVEST_IMPL_NUMBER_PEOPLE = 6;
    private final String NECESSARY_DOCUMENT = "fghfg";
    
    private final String CONTACT = "問合せ窓口_部署（区市町村向け） window2@gmail.com TEL_123456780 FAX_987654320";
    private final String NOTIFICATION_METHOD_CODE = "2";
    private final String DELETE_FLAG = "0";
    private final String UPDATE_USER_ID = "1000000030";
    private final Timestamp UPDATE_DATETIME = Timestamp.valueOf(LocalDateTime.parse("2019-12-25 14:13:06", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    private final String TEXTADDRESS = "貴管理組合が管理する";

    /**
     * 案件ID:GGA0110/チェックリストID:UT- GGA0110-001-01/区分:N/チェック内容:Test save success
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GGA0110-001-01.sql")
    public void save_WhenSuccess_ShouldBeCreated() {
        // Prepare data
        TBL230Entity expected = generateTBL230Entity();

        // Execute
        TBL230Entity actual = tbl230DAO.save(expected);

        // Check result
        assertEntity(actual);
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT- GGA0110-001-02/区分:N/チェック内容:test save throw exception
     */
    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void save_WhenSuccess_ShouldBeThrowException() {
        // Execute
        tbl230DAO.save(null);
    }

    /**
     * 案件ID:GEA0110/チェックリストID:UT- GEA0110-036/区分:E/チェック内容:Test Get Accept By AcceptNo When Not Exist Should Be Null
     */
    @Test
    public void testGetAcceptByAcceptNo_WhenNotExist_ShouldBeNull() {
     // Execute
        TBL230Entity actual = tbl230DAO.getSurveyById(null);
        // Check result
        assertThat(actual).isNull();
    }

    /**
     * 案件ID:GEA0110/チェックリストID:UT- GEA0110-037/区分:I/チェック内容:Test Get Accept By AcceptNo When Exist Should Be Exist
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GGA0110-001-01.sql")
    public void testGetAcceptByAcceptNo_WhenExist_ShouldBeExist() {
        // Execute
        TBL230Entity actual = tbl230DAO.getSurveyById(INVESTIGATION_NO);
        // Check result
        assertThat(actual).isNotNull();
        assertEntity(actual);
    }
    
    /**
     * 案件ID:GJA0120/チェックリストID:UT- GJA0120-007/区分:E/チェック内容:Test Get Surveys By ApartmentId When Not Exist Should Be Null
     */
    @Test
    public void testGetSurveysByApartmentId_WhenNotExist_ShouldBeNull() {
    	// Execute
    	List<TBL230Entity> actual = tbl230DAO.getSurveysByApartmentId(null);
    	// Check result
    	assertThat(actual).isEqualTo(new ArrayList<TBL230Entity>());
    }
    
    /**
     * 案件ID:GJA0120/チェックリストID:UT- GJA0120-008/区分:N/チェック内容:Test Get Surveys By ApartmentId When Exist Should Be Exist
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GGA0110-001-01.sql")
    public void testGetSurveysByApartmentId_WhenExist_ShouldBeExist() {
    	List<TBL230Entity> expect = new ArrayList<TBL230Entity>();
    	expect.add(generateTBL230Entity());
    	// Execute
    	List<TBL230Entity> actual = tbl230DAO.getSurveysByApartmentId(APARTMENT_ID);
    	// Check result

    	assertThat(actual.size()).isEqualTo(expect.size());
        assertTbl230Entity(actual.get(0));
    }
    
    private void assertTbl230Entity(TBL230Entity tbl300Entity) {
        
    }

    private void assertEntity(TBL230Entity entity) {
        assertThat(entity.getInvestigationNo()).isEqualTo(INVESTIGATION_NO);
        assertThat(entity.getApartmentId()).isEqualTo(APARTMENT_ID);
        assertThat(entity.getAppendixNo()).isEqualTo(APPENDIX_NO);
        assertThat(entity.getDocumentNo()).isEqualTo(DOCUMENT_NO);
        assertThat(entity.getNoticeDate()).isEqualTo(NOTICE_DATE);
        assertThat(entity.getNoticeDestinationCode()).isEqualTo(NOTICE_DESTINATION_CODE);
        assertThat(entity.getRecipientNameApartment()).isEqualTo(RECIPIENT_NAME_APARTMENT);
        assertThat(entity.getRecipientNameUser()).isEqualTo(RECIPIENT_NAME_USER);
        assertThat(entity.getSender()).isEqualTo(SENDER);
        assertThat(entity.getAddress()).isEqualTo(ADDRESS);
        assertThat(entity.getApartmentName()).isEqualTo(APARTMENT_NAME);
        assertThat(entity.getInvestImplPlanTime()).isEqualTo(INVEST_IMPL_PLAN_TIME);
        assertThat(entity.getInvestImplNumberPeople()).isEqualTo(INVEST_IMPL_NUMBER_PEOPLE);
        assertThat(entity.getNecessaryDocument()).isEqualTo(NECESSARY_DOCUMENT);
        assertThat(entity.getContact()).isEqualTo(CONTACT);
        assertThat(entity.getNotificationMethodCode()).isEqualTo(NOTIFICATION_METHOD_CODE);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME);
        assertThat(entity.getTextaddress()).isEqualTo(TEXTADDRESS);

    }

    private TBL230Entity generateTBL230Entity() {
        TBL230Entity entity = new TBL230Entity();
        entity.setInvestigationNo(INVESTIGATION_NO);
        entity.setApartmentId(APARTMENT_ID);
        entity.setAppendixNo(APPENDIX_NO);
        entity.setDocumentNo(DOCUMENT_NO);
        entity.setNoticeDate(NOTICE_DATE);
        entity.setNoticeDestinationCode(NOTICE_DESTINATION_CODE);
        entity.setRecipientNameApartment(RECIPIENT_NAME_APARTMENT);
        entity.setRecipientNameUser(RECIPIENT_NAME_USER);
        entity.setSender(SENDER);
        entity.setAddress(ADDRESS);
        entity.setApartmentName(APARTMENT_NAME);
        entity.setInvestImplPlanTime(INVEST_IMPL_PLAN_TIME);
        entity.setInvestImplNumberPeople(INVEST_IMPL_NUMBER_PEOPLE);
        entity.setNecessaryDocument(NECESSARY_DOCUMENT);
        entity.setContact(CONTACT);
        entity.setNotificationMethodCode(NOTIFICATION_METHOD_CODE);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setUpdateDatetime(UPDATE_DATETIME);
        entity.setTextaddress(TEXTADDRESS);
        return entity;
    }

}
