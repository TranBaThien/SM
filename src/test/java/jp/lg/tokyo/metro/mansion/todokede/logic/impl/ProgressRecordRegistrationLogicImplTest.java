/*
 * @(#)FileName.java 2019/MM/dd
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author pvthinh
 * Create Date: 2019/12/10
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import ch.qos.logback.classic.Logger;
import jp.lg.tokyo.metro.mansion.todokede.LogAppender;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SequenceUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL105DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL300DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL310DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL105Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL300Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL310Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.ProgressRecordRegistrationForm;
import jp.lg.tokyo.metro.mansion.todokede.vo.FileManagerInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ProgressRecordRegistrationVo;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProgressRecordRegistrationLogicImplTest {
    
	@Mock
	private SequenceUtil sequenceUtil;

	@Mock
	private TBL100DAO tbl100DAO;

	@Mock
	private TBL105DAO tbl105DAO;

	@Mock
	private TBL300DAO tbl300DAO;

	@Mock
	private TBL310DAO tbl310DAO;
	
	@Mock
	MultipartFile multipartFile1;
	
	@Mock
    MultipartFile multipartFile2;
	
	@Mock
    MultipartFile multipartFile3;
	
	@InjectMocks
	private ProgressRecordRegistrationLogicImpl progressRecordRegistrationLogic;

	private Timestamp currentDate = new Timestamp(System.currentTimeMillis());

	/* Create TBL100Entity */
	private final String APARTMENT_ID = "11000000001";
	private final String ADDRESS = "tokyo";
	private final String APARTMENT_LOST_FLAG = "1";
	private final String APARTMENT_NAME = "abc";
	private final String APARTMENT_NAME_PHONETIC = "abc phonetic";
	private final String BUILD_DAY = "10";
	private final String BUILD_MONTH = "02";
	private final String BUILD_YEAR = "2020";
	private final double BUILDING_AREA = 00000000001;
	private final String BUILDING_STRUCTURE = "0101010101";
	private final String CITY_CODE = "202020";
	private final String CITY_NAME = "kyoto";
	private final String DELETE_FLAG = "0";
	private final int FLOOR_NUMBER = 111;
	private final int HOUSE_NUMBER = 222;
	private final String MAIL_ADDRESS = "abc@gmail.com";
	private final String NEWEST_NOTIFICATION_ADDRESS = "tokyo";
	private final String NEWEST_NOTIFICATION_NAME = "osaka";
	private final String NEWEST_NOTIFICATION_NO = "newest kyoto";
	private final LocalDate NEXT_NOTIFICATION_DATE = LocalDate.now();
	private final String NOTIFICATION_KBN = "1";
	private final String PRELIMINARY1 = "0000001";
	private final String PRELIMINARY2 = "0000002";
	private final String PRELIMINARY3 = "0000003";
	private final String PRELIMINARY4 = "0000004";
	private final String PRELIMINARY5 = "0000005";
	private final String PROPERTY_NUMBER = "00000008";
	private final String REAL_ESTATE_NO = "1111111111113";
	private final String REGISTRATION_ADDRESS = "kyoto";
	private final String REGISTRATION_HOUSE_NO = "osaka";
	private final String REGISTRATION_NO = "00000008";
	private final String REPASSWORD_MAIL_ADDRESS = "abcd@gmail.com";
	private final double SITE_AREA = 10101010;
	private final String SUPPORT = "1";
	private final double TOTAL_FLOOR_AREA = 11111;
	private final Timestamp UPDATE_DATETIME = new Timestamp(new Date().getTime());
	private final String UPDATE_USER_ID = "00001";
	private final String ZIP_CODE = "12345678";

	private TBL100Entity newTBL100Entity(String apartmentId) {
		TBL100Entity entity = new TBL100Entity();

		entity.setApartmentId(apartmentId);
		entity.setAddress(ADDRESS);
		entity.setApartmentLostFlag(APARTMENT_LOST_FLAG);
		entity.setApartmentName(APARTMENT_NAME);
		entity.setApartmentNamePhonetic(APARTMENT_NAME_PHONETIC);
		entity.setBuildDay(BUILD_DAY);
		entity.setBuildMonth(BUILD_MONTH);
		entity.setBuildYear(BUILD_YEAR);
		entity.setBuildingArea(BUILDING_AREA);
		entity.setBuildingStructure(BUILDING_STRUCTURE);
		entity.setCityCode(CITY_CODE);
		entity.setCityName(CITY_NAME);
		entity.setDeleteFlag(DELETE_FLAG);
		entity.setFloorNumber(FLOOR_NUMBER);
		entity.setHouseNumber(HOUSE_NUMBER);
		entity.setMailAddress(MAIL_ADDRESS);
		entity.setNewestNotificationAddress(NEWEST_NOTIFICATION_ADDRESS);
		entity.setNewestNotificationName(NEWEST_NOTIFICATION_NAME);
		entity.setNewestNotificationNo(NEWEST_NOTIFICATION_NO);
		entity.setNextNotificationDate(NEXT_NOTIFICATION_DATE);
		entity.setNotificationKbn(NOTIFICATION_KBN);
		entity.setPreliminary1(PRELIMINARY1);
		entity.setPreliminary2(PRELIMINARY2);
		entity.setPreliminary3(PRELIMINARY3);
		entity.setPreliminary4(PRELIMINARY4);
		entity.setPreliminary5(PRELIMINARY5);
		entity.setPropertyNumber(PROPERTY_NUMBER);
		entity.setRealEstateNo(REAL_ESTATE_NO);
		entity.setRegistrationAddress(REGISTRATION_ADDRESS);
		entity.setRegistrationHouseNo(REGISTRATION_HOUSE_NO);
		entity.setRegistrationNo(REGISTRATION_NO);
		entity.setRepasswordMailAddress(REPASSWORD_MAIL_ADDRESS);
		entity.setSiteArea(SITE_AREA);
		entity.setSupport(SUPPORT);
		entity.setTotalFloorArea(TOTAL_FLOOR_AREA);
		entity.setUpdateDatetime(UPDATE_DATETIME);
		entity.setUpdateUserId(UPDATE_USER_ID);
		entity.setZipCode(ZIP_CODE);

		return entity;

	}
	/* Create TBL105Entity */
	private final String HISTORY_NUMBER_5 = "22";
	private final String APARTMENT_ID_5 = "22";
	private final String ADDRESS_5 = "11";
	private final String APARTMENT_LOST_FLAG_5 = "1";
	private final String APARTMENT_NAME_5 = "22";
	private final String APARTMENT_NAME_PHONETIC_5 = "1111";
	private final String BUILD_DAY_5 = "22";
	private final String BUILD_MONTH_5 = "11";
	private final String BUILD_YEAR_5 = "33";
	private final double BUILDING_AREA_5 = 1.2;
	private final String BUILDING_STRUCTURE_5 = "11";
	private final String CITY_CODE_5 = "333";
	private final String CITY_NAME_5 = "33";
	private final String DELETE_FLAG_5 = "11";
	private final int FLOOR_NUMBER_5 = 1;
	private final int HOUSE_NUMBER_5 = 1;
	private final String MAIL_ADDRESS_5 = "55";
	private final String NEWEST_NOTIFICATION_ADDRESS_5 = "11";
	private final String NEWEST_NOTIFICATION_NAME_5 = "22";
	private final String NEWEST_NOTIFICATION_NO_5 = "44";
	private final LocalDate NEXT_NOTIFICATION_DATE_5 = LocalDate.now();
	private final String NOTIFICATION_KBN_5 = "22";
	private final String PRELIMINARY1_5 = "33";
	private final String PRELIMINARY2_5 = "33";
	private final String PRELIMINARY3_5 = "33";
	private final String PRELIMINARY4_5 = "33";
	private final String PRELIMINARY5_5 = "33";
	private final String PROPERTY_NUMBER_5 = "33";
	private final String REAL_ESTATE_NO_5 = "33";
	private final String REGISTRATION_ADDRESS_5 = "33";
	private final String REGISTRATION_HOUSE_NO_5 = "333";
	private final String REGISTRATION_NO_5 = "22";
	private final String REPASSWORD_MAIL_ADDRESS_5 = "111";
	private final String SITE_AREA_5 = "22";
	private final String SUPPORT_5 = "1";
	private final double TOTAL_FLOOR_AREA_5 = 2.2;
	private final Timestamp UPDATE_DATETIME_5 = new Timestamp(new Date().getTime());
	private final String UPDATE_USER_ID_5 = "333";
	private final String ZIP_CODE_5 = "111";



	/* Create TBL300Entity */
	private final String PROGRESS_RECORD_NO_1 = "000001";
	private final String PROGRESS_RECORD_NO_3 = "000000000000001";
	private final String APARTMENT_ID_1 = "11111";
	private final String CORRESPOND_DATE_1 = "111111";
	private final String CORRESPOND_TYPE_CODE_1 = "0";
	private final String DELETE_FLAG_1 = "1";
	private final String NOTICE_TYPE_CODE_1 = "1";
	private final String NOTIFICATION_METHOD_CODE_1 = "0";
	private final String PROGRESS_RECORD_DETAIL_1 = "abcdef";
	private final String PROGRESS_RECORD_OVERVIEW_1 = "0000004";
	private final String RELATED_NUMBER_1 = "11111";
	private final String SUPPORT_CODE_1 = "0";
	private final String TYPE_CODE_1 = "22";
	private final Timestamp UPDATE_DATETIME_1 = new Timestamp(new Date().getTime());
	private final String UPDATE_USER_ID_1 = "1111";

	private TBL300Entity newTBL300Entity(String progressRecordNo) {
		TBL300Entity entity = new TBL300Entity();

		entity.setProgressRecordNo(progressRecordNo);
		entity.setApartmentId(APARTMENT_ID_1);
		entity.setCorrespondTypeCode(CORRESPOND_TYPE_CODE_1);
		entity.setDeleteFlag(DELETE_FLAG_1);
		entity.setNoticeTypeCode(NOTICE_TYPE_CODE_1);
		entity.setNotificationMethodCode(NOTIFICATION_METHOD_CODE_1);
		entity.setProgressRecordDetail(PROGRESS_RECORD_DETAIL_1);
		entity.setRelatedNumber(RELATED_NUMBER_1);
		entity.setSupportCode(SUPPORT_CODE_1);
		entity.setTypeCode(TYPE_CODE_1);
		entity.setUpdateDatetime(UPDATE_DATETIME_1);
		entity.setUpdateUserId(UPDATE_USER_ID_1);
		entity.setCorrespondDate(CORRESPOND_DATE_1);


		return entity;
	}

	private TBL300Entity newTBL300Entity1(String progressRecordNo1) {
		TBL300Entity entity = new TBL300Entity();

		entity.setProgressRecordNo(progressRecordNo1);
		entity.setApartmentId(APARTMENT_ID_1);
		entity.setCorrespondTypeCode(CORRESPOND_TYPE_CODE_1);
		entity.setDeleteFlag(DELETE_FLAG_1);
		entity.setNoticeTypeCode(NOTICE_TYPE_CODE_1);
		entity.setNotificationMethodCode(NOTIFICATION_METHOD_CODE_1);
		entity.setProgressRecordDetail(PROGRESS_RECORD_DETAIL_1);
		entity.setRelatedNumber(RELATED_NUMBER_1);
		entity.setSupportCode(SUPPORT_CODE_1);
		entity.setTypeCode(TYPE_CODE_1);
		entity.setUpdateDatetime(UPDATE_DATETIME_1);
		entity.setUpdateUserId(UPDATE_USER_ID_1);
		entity.setCorrespondDate(CORRESPOND_DATE_1);

		return entity;
	}

	private List<TBL300Entity> listTBL300Entity(String progressRecordNo1) {
		List<TBL300Entity> listEntity = new ArrayList<TBL300Entity>();
		TBL300Entity entity = new TBL300Entity();

		entity.setProgressRecordNo(progressRecordNo1);
		entity.setApartmentId(APARTMENT_ID_1);
		entity.setCorrespondTypeCode(CORRESPOND_TYPE_CODE_1);
		entity.setDeleteFlag(DELETE_FLAG_1);
		entity.setNoticeTypeCode(NOTICE_TYPE_CODE_1);
		entity.setNotificationMethodCode(NOTIFICATION_METHOD_CODE_1);
		entity.setProgressRecordDetail(PROGRESS_RECORD_DETAIL_1);
		entity.setRelatedNumber(RELATED_NUMBER_1);
		entity.setSupportCode(SUPPORT_CODE_1);
		entity.setTypeCode(TYPE_CODE_1);
		entity.setUpdateDatetime(UPDATE_DATETIME_1);
		entity.setUpdateUserId(UPDATE_USER_ID_1);
		entity.setCorrespondDate(CORRESPOND_DATE_1);

		listEntity.add(entity);
		return listEntity;
	}

	/* Create TBL310Entity */
	private final String PROGRESS_RECORD_ATTACH_NO = "22222";
	private final String DELETE_FLAG_2 = "1";
	private final byte[] FILE = { 1, 2, 3 };
	private final String FILE_NAME = "11111";
	private final String PROGRESS_RECORD_NO_2 = "4444";
	private final Timestamp UPDATE_DATETIME_2 = new Timestamp(new Date().getTime());
	private final String UPDATE_USER_ID_2 = "44444";

	private List<TBL310Entity> listTBL310Entity(String progressRecordAttachNo) {
		List<TBL310Entity> listEntity = new ArrayList<TBL310Entity>();
		TBL310Entity entity = new TBL310Entity();

		entity.setProgressRecordAttachNo(progressRecordAttachNo);
		entity.setDeleteFlag(DELETE_FLAG_2);
		entity.setFile(FILE);
		entity.setFileName(FILE_NAME);
		entity.setProgressRecordNo(PROGRESS_RECORD_NO_2);
		entity.setUpdateDatetime(UPDATE_DATETIME_2);
		entity.setUpdateUserId(UPDATE_USER_ID_2);

		listEntity.add(entity);

		return listEntity;
	}

	private TBL310Entity newTBL310Entity(String progressRecordAttachNo) {
		TBL310Entity entity = new TBL310Entity();

		entity.setProgressRecordAttachNo(progressRecordAttachNo);
		entity.setDeleteFlag(DELETE_FLAG_2);
		entity.setFile(FILE);
		entity.setFileName(FILE_NAME);
		entity.setProgressRecordNo(PROGRESS_RECORD_NO_2);
		entity.setUpdateDatetime(UPDATE_DATETIME_2);
		entity.setUpdateUserId(UPDATE_USER_ID_2);

		return entity;
	}

	/* Create form */
	private ProgressRecordRegistrationForm progressRecordRegistrationForm(String PROGRESS_RECORD_NO_1) {
		ProgressRecordRegistrationForm form = new ProgressRecordRegistrationForm();
		form.setProgressRecordNo(PROGRESS_RECORD_NO_1);
        form.setApartmentId(APARTMENT_ID);
        form.setCalCorrespondDate(CORRESPOND_DATE_1);
        form.setTxtProgressRecordOverview(PROGRESS_RECORD_OVERVIEW_1);
		return form;
	}
	/* End Create form */

	/* Start test method getProgressRecordTbl300 */
	/**
	 * 案件ID:GEB0110/チェックリストID:UT- GEB0110-001/区分:I/チェック内容:Test Get Progress Record Tbl300 When Entity Is Not Null
	 */
	@Test
	public void testGetProgressRecordTbl300WhenEntityNotNull() throws BusinessException {
		TBL300Entity entity = newTBL300Entity(PROGRESS_RECORD_NO_1);
		Mockito.when(tbl300DAO.getProgressRecord(Mockito.anyString())).thenReturn(entity);

		ProgressRecordRegistrationVo vo = progressRecordRegistrationLogic.getProgressRecordTbl300(PROGRESS_RECORD_NO_1);
		assertResult(vo, entity);

	}
	
    @Before
    public void init() {
        LogAppender.pauseTillLogbackReady();
    }
	/**
	 * 案件ID:GEB0110/チェックリストID:UT- GEB0110-002/区分:E/チェック内容:Test Get Progress Record Tbl300 When Entity Is Null
	 */
	@Test
	public void testGetProgressRecordTbl300WhenEntityIsNull() throws BusinessException {
		Mockito.when(tbl300DAO.getProgressRecord(Mockito.anyString())).thenReturn(null);

		ProgressRecordRegistrationVo vo = progressRecordRegistrationLogic.getProgressRecordTbl300(PROGRESS_RECORD_NO_1);
		assertThat(StringUtils.isEmpty(vo.getProgressRecordNo())).isTrue();
	}

	/**
	 * 案件ID:GEB0110/チェックリストID:UT- GEB0110-003/区分:E/チェック内容:Test Get Progress Record Tbl300 When Exception
	 */
	@Test
	public void testGetProgressRecordTbl300WhenException() throws Exception {
		TBL300Entity entity1 = newTBL300Entity1(PROGRESS_RECORD_NO_1);
		Mockito.when(tbl300DAO.getProgressRecord(Mockito.anyString())).thenReturn(entity1);

		progressRecordRegistrationLogic.getProgressRecordTbl300(PROGRESS_RECORD_NO_2);
	}
	/* End test method getProgressRecordTbl300 */

	/* Start test method getProgressRecordFileInfos */
	/**
	 * 案件ID:GEB0110/チェックリストID:UT- GEB0110-004/区分:N/チェック内容:Test Get Progress Record File Infos When List Entity Is Not Null
	 * @throws Exception
	 */
	@Test
	public void testgetProgressRecordFileInfosWhenListEntityIsNotNull() throws Exception {
		List<TBL310Entity> entity = listTBL310Entity(PROGRESS_RECORD_ATTACH_NO);
		Mockito.when(tbl310DAO.getProgressRecord(Mockito.anyString())).thenReturn(entity);

		List<FileManagerInfoVo> listFile = progressRecordRegistrationLogic.getProgressRecordFileInfos(PROGRESS_RECORD_ATTACH_NO);
		progressRecordRegistrationLogic.getProgressRecordFileInfos(PROGRESS_RECORD_ATTACH_NO);
	}

	/**
	 * 案件ID:GEB0110/チェックリストID:UT- GEB0110-005/区分:E/チェック内容:Test Get Progress Record File Infos When List Entity Is Null
	 * @throws Exception
	 */
	@Test
	public void testgetProgressRecordFileInfosWhenListEntityIsNull() throws Exception  {
		Mockito.when(tbl310DAO.getProgressRecord(Mockito.anyString())).thenReturn(null);

		List<FileManagerInfoVo> listFile = progressRecordRegistrationLogic.getProgressRecordFileInfos(PROGRESS_RECORD_ATTACH_NO);
		Assert.assertEquals(listFile, new ArrayList<T>());
	}
	/* End test method getProgressRecordFileInfos */


	/* Start test method findProgressRecordFileById */
	/**
	 * 案件ID:GEB0110/チェックリストID:UT- GEB0110-006/区分:N/チェック内容:Test Find Progress Record File By Id Is Exactly
	 */
	@Test
	public void testFindProgressRecordFileByIdExactly()  {
		TBL310Entity ett = newTBL310Entity(PROGRESS_RECORD_NO_2);
		Mockito.when(tbl310DAO.findProgressRecordFileById(Mockito.anyString())).thenReturn(ett);

		FileManagerInfoVo vo = progressRecordRegistrationLogic.findProgressRecordFileById(PROGRESS_RECORD_ATTACH_NO);
		assertResult1(vo, ett);
	}
	/* End test method findProgressRecordFileById */


	/* Start test method isUpdateLatestTbl300 */
	/**
	 * 案件ID:GEB0110/チェックリストID:UT- GEB0110-007/区分:N/チェック内容:Test Update Latest Tbl300 When Entity Not Null And Compare Is Same
	 */
	@Test
	public void testUpdateLatestTbl300WhenEntityNotNullAndCompareIsSame()  {
		String dateTime = "2019-10-10 12:12:12";
		Timestamp UPDATE_DATETIME_1 = DateTimeUtil.convertStringToTimestamp(dateTime);
		TBL300Entity entity = newTBL300Entity(PROGRESS_RECORD_NO_1);
		Mockito.when(tbl300DAO.getProgressRecord(Mockito.anyString())).thenReturn(entity);
		entity.setUpdateDatetime(UPDATE_DATETIME_1);

		assertTrue(progressRecordRegistrationLogic.isUpdateLatestTbl300(PROGRESS_RECORD_NO_1, dateTime));
	}

	/**
	 * 案件ID:GEB0110/チェックリストID:UT- GEB0110-008/区分:E/チェック内容:Test Update Latest Tbl300 When Entity Is Null And Compare Is Same
	 */
	@Test
	public void testUpdateLatestTbl300WhenEntityIsNullAndCompareIsSame()  {
		String dateTime = "2019-10-10 12:12:12";
		Timestamp UPDATE_DATETIME_1 = DateTimeUtil.convertStringToTimestamp(dateTime);
		TBL300Entity entity = newTBL300Entity(PROGRESS_RECORD_NO_1);
		Mockito.when(tbl300DAO.getProgressRecord(Mockito.anyString())).thenReturn(null);
		entity.setUpdateDatetime(UPDATE_DATETIME_1);

		assertFalse(progressRecordRegistrationLogic.isUpdateLatestTbl300(PROGRESS_RECORD_NO_1, dateTime));
	}

	/**
	 * 案件ID:GEB0110/チェックリストID:UT- GEB0110-009/区分:E/チェック内容:Test Update Latest Tbl300 When Entity Is Not Null And Compare Is Not Same
	 */
	@Test
	public void testUpdateLatestTbl300WhenEntityIsNotNullAndCompareIsNotSame() {
		String dateTime = "2019-10-10 12:12:12";
		String dateTime2 = "2019-10-11 12:12:12";
		TBL300Entity entity = newTBL300Entity(PROGRESS_RECORD_NO_1);
		Timestamp UPDATE_DATETIME_1 = DateTimeUtil.convertStringToTimestamp(dateTime2);
		entity.setUpdateDatetime(UPDATE_DATETIME_1);
		Mockito.when(tbl300DAO.getProgressRecord(Mockito.anyString())).thenReturn(entity);

		assertFalse(progressRecordRegistrationLogic.isUpdateLatestTbl300(PROGRESS_RECORD_NO_1, dateTime));
	}

	/**
	 * 案件ID:GEB0110/チェックリストID:UT- GEB0110-010/区分:E/チェック内容:Test Update Latest Tbl300 When Exception
	 */
	@Test
	public void testUpdateLatestTbl300WhenException() {
		String dateTime = "2019";
		TBL300Entity entity = newTBL300Entity(PROGRESS_RECORD_NO_1);
		Timestamp UPDATE_DATETIME_1 = DateTimeUtil.convertStringToTimestamp(dateTime);
		entity.setUpdateDatetime(UPDATE_DATETIME_1);
		Mockito.when(tbl300DAO.getProgressRecord(Mockito.anyString())).thenReturn(entity);

		assertFalse(progressRecordRegistrationLogic.isUpdateLatestTbl300(PROGRESS_RECORD_NO_1, dateTime));
	}
	/* End test method isUpdateLatestTbl300 */

	/* Start test method delete */
	/**
	 * 案件ID:GEB0110/チェックリストID:UT- GEB0110-011/区分:N/チェック内容:Test Delete When Success
	 */
	@Test
	public void testDeleteWhenSuccess() {
		ProgressRecordRegistrationForm form = progressRecordRegistrationForm(PROGRESS_RECORD_NO_1);

		assertFalse(progressRecordRegistrationLogic.delete(form));
	}
	/* End test method delete */

	/* Start test method isValidMaxCorrespondDate */
	/**
	 * 案件ID:GEB0110/チェックリストID:UT- GEB0110-012/区分:N/チェック内容:Test Delete When Success
	 */
	@Test
	public void testisValidMaxCorrespondDateIsSuccess()  {
		String correspondDate = "";
		List<TBL300Entity> list300Entity = listTBL300Entity(PROGRESS_RECORD_NO_1);
		Mockito.when(tbl300DAO.getProgressRecordFromDate(APARTMENT_ID, correspondDate)).thenReturn(list300Entity);

		progressRecordRegistrationLogic.isValidMaxCorrespondDate(APARTMENT_ID, correspondDate);
	}
	/* End test method isValidMaxCorrespondDate */

	/* Start test method save */
	/**
	 * 案件ID:GEB0110/チェックリストID:UT- GEB0110-013/区分:N/チェック内容:Test Save When Update Success
	 * @throws BusinessException
	 */
	@Test
	public void testSaveWhenUpdateSuccess() throws BusinessException {
		ProgressRecordRegistrationForm form = progressRecordRegistrationForm(PROGRESS_RECORD_NO_1);
		form.setProgressRecordNo(PROGRESS_RECORD_NO_1);
		TBL300Entity entity = newTBL300Entity1(PROGRESS_RECORD_NO_1);
		Mockito.when(tbl300DAO.getProgressRecord(Mockito.anyString())).thenReturn(entity);

		form.setCalCorrespondDate("2019/12/11 12:12");
		form.setTxtProgressRecordOverview("123");
		form.setLstNoticeTypeCode("0");

		Mockito.when(tbl300DAO.save(Mockito.any())).thenReturn(new TBL300Entity());
		Mockito.when(sequenceUtil.generateKey(Mockito.anyString(), Mockito.anyString())).thenReturn("123");
		Mockito.when(tbl300DAO.getProgressRecord(PROGRESS_RECORD_NO_1)).thenReturn(entity);

		String[] selectedFiles = { "Apple", "Apricot", "Banana" };
		form.setSelectedFiles(selectedFiles);

		MockMultipartFile filFileFrom1 = new MockMultipartFile("file", "test.jpg",
				"image/jpeg", "test image content".getBytes());

		MockMultipartFile filFileFrom2 = new MockMultipartFile("file", "test.jpg",
				"image/jpeg", "test image content".getBytes());

		MockMultipartFile filFileFrom3 = new MockMultipartFile("file", "test.jpg",
				"image/jpeg", "test image content".getBytes());

		form.setFilFileFrom1(filFileFrom1);
		form.setFilFileFrom2(filFileFrom2);
		form.setFilFileFrom3(filFileFrom3);
		form.setFilFileFromName1("1");
		form.setFilFileFromName2("1");
		form.setFilFileFromName3("1");

		assertTrue(progressRecordRegistrationLogic.save(form));
	}

	/**
	 * 案件ID:GEB0110/チェックリストID:UT- GEB0110-014/区分:N/チェック内容:Test Save When Add Success
	 * @throws BusinessException
	 */
	@Test
	public void testSaveWhenAddSuccess() throws BusinessException  {

		ProgressRecordRegistrationForm form = progressRecordRegistrationForm(PROGRESS_RECORD_NO_1);
		form.setProgressRecordNo("");
		form.setApartmentId(APARTMENT_ID);
		form.setCalCorrespondDate("2019/12/11 12:12");
		form.setTxtProgressRecordOverview("123");

		Mockito.when(sequenceUtil.generateKey(Mockito.anyString(), Mockito.anyString())).thenReturn("123");
		Mockito.when(tbl300DAO.save(Mockito.any())).thenReturn(new TBL300Entity());

		form.setLstCorrespondTypeCode("06");
		TBL100Entity entity100 = newTBL100Entity(APARTMENT_ID);
		Mockito.when(tbl100DAO.getMansionInformationById(APARTMENT_ID)).thenReturn(entity100);

		form.setRdoSupportCode(SUPPORT);
		form.setUpdateUserId(UPDATE_USER_ID);

		Mockito.when(tbl100DAO.save(entity100)).thenReturn(entity100);
		TBL105Entity entt105 = new TBL105Entity();
		Mockito.when(tbl105DAO.save(Mockito.any())).thenReturn(entt105);

		Mockito.when(sequenceUtil.generateKey(Mockito.anyString(), Mockito.anyString())).thenReturn("123");
		String[] selectedFiles = { "Apple", "Apricot", "Banana" };
		form.setSelectedFiles(selectedFiles);

		MockMultipartFile filFileFrom1 = new MockMultipartFile("file", "test.jpg",
				"image/jpeg", "test image content".getBytes());

		MockMultipartFile filFileFrom2 = new MockMultipartFile("file", "test.jpg",
				"image/jpeg", "test image content".getBytes());

		MockMultipartFile filFileFrom3 = new MockMultipartFile("file", "test.jpg",
				"image/jpeg", "test image content".getBytes());

		form.setFilFileFrom1(filFileFrom1);
		form.setFilFileFrom2(filFileFrom2);
		form.setFilFileFrom3(filFileFrom3);
		form.setFilFileFromName1("1");
		form.setFilFileFromName2("1");
		form.setFilFileFromName3("1");

		assertTrue(progressRecordRegistrationLogic.save(form));

	}

	/**
	 * 案件ID:GEB0110/チェックリストID:UT- GEB0110-015/区分:E/チェック内容:Test Save Update When Entity Is Not Null And Not Save TBL300
	 * @throws BusinessException
	 */
	@Test(expected = BusinessException.class)
	public void testSaveUpdateWhenEntityIsNotNullAndNotSaveTBL300() throws BusinessException {
		ProgressRecordRegistrationForm form = progressRecordRegistrationForm(PROGRESS_RECORD_NO_1);
		form.setProgressRecordNo(PROGRESS_RECORD_NO_1);
		TBL300Entity entity = newTBL300Entity1(PROGRESS_RECORD_NO_1);
		Mockito.when(tbl300DAO.getProgressRecord(Mockito.anyString())).thenReturn(entity);
		progressRecordRegistrationLogic.save(form);
	}

	/**
	 * 案件ID:GEB0110/チェックリストID:UT- GEB0110-016/区分:E/チェック内容:Test Save Update When Entity Is Null And Not Save TBL310
	 * @throws BusinessException
	 */
	@Test(expected = NullPointerException.class)
	public void testSaveUpdateWhenEntityIsNullAndNotSaveTBL310() throws BusinessException {
	    ProgressRecordRegistrationForm form = progressRecordRegistrationForm(PROGRESS_RECORD_NO_1);
        form.setProgressRecordNo(PROGRESS_RECORD_NO_1);
        TBL300Entity entity = newTBL300Entity1(PROGRESS_RECORD_NO_1);
        Mockito.when(tbl300DAO.getProgressRecord(Mockito.anyString())).thenReturn(null);

		String[] selectedFiles = { "", ""};
		form.setSelectedFiles(selectedFiles);
		form.setFilFileFromName1("1");
		form.setFilFileFrom1(multipartFile1);
		form.setFilFileFrom2(multipartFile2);
		form.setFilFileFrom3(multipartFile3);

		progressRecordRegistrationLogic.save(form);
	}

	/**
	 * 案件ID:GEB0110/チェックリストID:UT- GEB0110-017/区分:E/チェック内容Test Save Add When Not Save TBL300
	 * @throws BusinessException
	 */
	@Test(expected = BusinessException.class)
	public void testSaveAddWhenNotSaveTBL300() throws BusinessException  {

		ProgressRecordRegistrationForm form = progressRecordRegistrationForm(PROGRESS_RECORD_NO_1);
		form.setProgressRecordNo("");
		form.setApartmentId(APARTMENT_ID);

		Mockito.when(sequenceUtil.generateKey(Mockito.anyString(), Mockito.anyString())).thenReturn("123");
		Mockito.when(tbl300DAO.save(Mockito.any())).thenReturn(null);

		progressRecordRegistrationLogic.save(form);

	}

	/**
	 * 案件ID:GEB0110/チェックリストID:UT- GEB0110-018/区分:E/チェック内容Test Save Add When Save TBL300 And GetLstCorrespondTypeCode Is TOKYO_SUPPORT_CODE And Not Save TBL100
	 * @throws BusinessException
	 */
	@Test(expected = NullPointerException.class)
	public void testSaveAddWhenSaveTBL300AndGetLstCorrespondTypeCodeIsTOKYO_SUPPORT_CODEAndNotSaveTBL100() throws BusinessException  {

		ProgressRecordRegistrationForm form = progressRecordRegistrationForm(PROGRESS_RECORD_NO_1);
		form.setProgressRecordNo("");
		form.setApartmentId(APARTMENT_ID);
		form.setCalCorrespondDate("2019/12/11 12:12");
		form.setTxtProgressRecordOverview("123");

		Mockito.when(sequenceUtil.generateKey(Mockito.anyString(), Mockito.anyString())).thenReturn("123");
		Mockito.when(tbl300DAO.save(Mockito.any())).thenReturn(new TBL300Entity());

		form.setLstCorrespondTypeCode("06");

		progressRecordRegistrationLogic.save(form);

	}

	/**
	 * 案件ID:GEB0110/チェックリストID:UT- GEB0110-019/区分:E/チェック内容Test Save Add When Save TBL300 And GetLstCorrespondTypeCode Is TOKYO_SUPPORT_CODE And Save TBL100
	 * @throws BusinessException
	 */
	@Test(expected = BusinessException.class)
	public void testSaveAddWhenSaveTBL300AndGetLstCorrespondTypeCodeIsTOKYO_SUPPORT_CODEAndSaveTBL100() throws BusinessException  {

		ProgressRecordRegistrationForm form = progressRecordRegistrationForm(PROGRESS_RECORD_NO_1);
		form.setProgressRecordNo("");
		form.setApartmentId(APARTMENT_ID);
		form.setCalCorrespondDate("2019/12/11 12:12");
		form.setTxtProgressRecordOverview("123");

		Mockito.when(sequenceUtil.generateKey(Mockito.anyString(), Mockito.anyString())).thenReturn("123");
		Mockito.when(tbl300DAO.save(Mockito.any())).thenReturn(new TBL300Entity());

		form.setLstCorrespondTypeCode("06");

		TBL100Entity entity100 = newTBL100Entity(APARTMENT_ID);
		Mockito.when(tbl100DAO.getMansionInformationById(APARTMENT_ID)).thenReturn(entity100);

		form.setRdoSupportCode(SUPPORT);
		form.setUpdateUserId(UPDATE_USER_ID);

		Mockito.when(tbl100DAO.save(entity100)).thenReturn(entity100);

		progressRecordRegistrationLogic.save(form);
	}

	/**
	 * 案件ID:GEB0110/チェックリストID:UT- GEB0110-020/区分:E/チェック内容Test Save Add When Save TBL300 And GetLstCorrespondTypeCode Is Not TOKYO_SUPPORT_CODE And Save TBL100 And Not Save TBL310
	 * @throws BusinessException
	 */
	@Test(expected = NullPointerException.class)
	public void testSaveAddWhenSaveTBL300AndGetLstCorrespondTypeCodeIsNotTOKYO_SUPPORT_CODEAndSaveTBL100AndNotSaveTBL310() throws BusinessException  {

		ProgressRecordRegistrationForm form = progressRecordRegistrationForm(PROGRESS_RECORD_NO_1);
		form.setProgressRecordNo("");
		form.setApartmentId(APARTMENT_ID);
		form.setCalCorrespondDate("2019/12/11 12:12");
		form.setTxtProgressRecordOverview("123");

		Mockito.when(sequenceUtil.generateKey(Mockito.anyString(), Mockito.anyString())).thenReturn("123");
		Mockito.when(tbl300DAO.save(Mockito.any())).thenReturn(new TBL300Entity());

		form.setLstCorrespondTypeCode("05");

		TBL100Entity entity100 = newTBL100Entity(APARTMENT_ID);
		Mockito.when(tbl100DAO.getMansionInformationById(APARTMENT_ID)).thenReturn(entity100);

		form.setRdoSupportCode(SUPPORT);
		form.setUpdateUserId(UPDATE_USER_ID);

		Mockito.when(tbl100DAO.save(entity100)).thenReturn(entity100);

		String[] selectedFiles = { "Apple", "Apricot", "Banana" };
		form.setSelectedFiles(selectedFiles);
		form.setFilFileFromName1("1");
		form.setFilFileFromName2("1");
		form.setFilFileFromName3("1");

		progressRecordRegistrationLogic.save(form);

	}

	/**
	 * 案件ID:GEB0110/チェックリストID:UT- GEB0110-021/区分:N/チェック内容Test Save TBL100 When Entity Is Null
	 * @throws BusinessException
	 */
	@Test(expected = BusinessException.class)
	public void testSaveTBL100WhenEntityIsNull() throws BusinessException  {

		ProgressRecordRegistrationForm form = progressRecordRegistrationForm(PROGRESS_RECORD_NO_1);
		form.setProgressRecordNo("");
		form.setApartmentId(APARTMENT_ID);
		form.setCalCorrespondDate("2019/12/11 12:12");
		form.setTxtProgressRecordOverview("123");
		Mockito.when(sequenceUtil.generateKey(Mockito.anyString(), Mockito.anyString())).thenReturn("123");
		Mockito.when(tbl300DAO.save(Mockito.any())).thenReturn(new TBL300Entity());
		form.setLstCorrespondTypeCode("06");
		TBL100Entity entity100 = newTBL100Entity(APARTMENT_ID);
		Mockito.when(tbl100DAO.getMansionInformationById(APARTMENT_ID)).thenReturn(entity100);
		form.setRdoSupportCode(SUPPORT);
		form.setUpdateUserId(UPDATE_USER_ID);
		Mockito.when(tbl100DAO.save(entity100)).thenReturn(null);

		assertFalse(progressRecordRegistrationLogic.save(form));
	}

	/**
	 * 案件ID:GEB0110/チェックリストID:UT- GEB0110-022/区分:N/チェック内容Test Save TBL300 When Entity Is Null
	 * @throws BusinessException
	 */
	@Test(expected = BusinessException.class)
	public void testSaveTBL300WhenEntityIsNull() throws BusinessException  {

		ProgressRecordRegistrationForm form = progressRecordRegistrationForm(PROGRESS_RECORD_NO_1);
		form.setProgressRecordNo("");
		form.setApartmentId(APARTMENT_ID);
		form.setCalCorrespondDate("2019/12/11 12:12");
		form.setTxtProgressRecordOverview("123");

		Mockito.when(sequenceUtil.generateKey(Mockito.anyString(), Mockito.anyString())).thenReturn("123");
		Mockito.when(tbl300DAO.save(Mockito.any())).thenReturn(null);

		assertFalse(progressRecordRegistrationLogic.save(form));
	}


	/**
	 * 案件ID:GEB0110/チェックリストID:UT- GEB0110-023/区分:N/チェック内容Test Save TBL310 When FileName Is Empty
	 * @throws BusinessException
	 */
	@Test
	public void testSaveTBL310WhenFileNameIsEmpty() throws BusinessException {
		ProgressRecordRegistrationForm form = progressRecordRegistrationForm(PROGRESS_RECORD_NO_1);
		form.setProgressRecordNo(PROGRESS_RECORD_NO_1);
		TBL300Entity entity = newTBL300Entity1(PROGRESS_RECORD_NO_1);
		
		Mockito.when(tbl300DAO.getProgressRecord(Mockito.anyString())).thenReturn(entity);

		form.setCalCorrespondDate("2019/12/11 12:12");
		form.setTxtProgressRecordOverview("123");
		form.setLstNoticeTypeCode("0");

		Mockito.when(tbl300DAO.save(Mockito.any())).thenReturn(new TBL300Entity());
		Mockito.when(sequenceUtil.generateKey(Mockito.anyString(), Mockito.anyString())).thenReturn("123");
		Mockito.when(tbl300DAO.getProgressRecord(PROGRESS_RECORD_NO_1)).thenReturn(entity);
		String[] selectedFiles = null;
		form.setSelectedFiles(selectedFiles);

		form.setFilFileFromName1("");
		form.setFilFileFromName2("");
		form.setFilFileFromName3("");
		assertTrue(progressRecordRegistrationLogic.save(form));
	}
	/* End test method save */
	
	   /**
     * 案件ID:GEB0110/チェックリストID:UT- GEB0110-024/区分:E/チェック内容:Test Save Update When Form is null
     * @throws BusinessException
     */
	@Test(expected = BusinessException.class)
    public void testSaveUpdateWhenFormIsNull() throws BusinessException {
        //prepare data
        ProgressRecordRegistrationForm form = null;
        progressRecordRegistrationLogic.save(form);
        
    }


	private void assertResult(ProgressRecordRegistrationVo vo, TBL300Entity entity) {
		assertThat(vo.getCalCorrespondDate()).isEqualTo(DateTimeUtil.convertReFormatDate(entity.getCorrespondDate()));
		assertThat(vo.getLstCorrespondTypeCode()).isEqualTo(entity.getCorrespondTypeCode());
		assertThat(vo.getLstNoticeTypeCode()).isEqualTo(entity.getNoticeTypeCode());
		assertThat(vo.getTxaProgressRecordDetail()).isEqualTo(entity.getProgressRecordDetail());
		assertThat(vo.getRdoSupportCode()).isEqualTo(entity.getSupportCode());
		assertThat(vo.getUpdateDatetime()).isEqualTo(entity.getUpdateDatetime().toString());

	}

	private void assertResult1(FileManagerInfoVo vo, TBL310Entity ett) {
		assertThat(vo.getFile()).isEqualTo(ett.getFile());
		assertThat(vo.getFileName()).isEqualTo(ett.getFileName());
	}
}