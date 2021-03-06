/*
 * @(#) ProgressRecordLogicImplTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author pdquang
 * Create Date: 2019/12/20
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.config.CodeUtilConfig;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL200DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL240DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL300BDAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL300DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL200Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL240Entity;
import jp.lg.tokyo.metro.mansion.todokede.vo.ProgressRecorInfoWrapperVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ProgressRecordAcceptUserVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ProgressRecordDetailsVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.StatusInformationVo;

@RunWith(SpringRunner.class)
@Import(value = {CodeUtilConfig.class})
public class ProgressRecordLogicImplTest {

    @Mock
    private TBL100DAO tbl100DAO;

    @Mock
    private TBL200DAO tbl200DAO;

    @Mock
    private TBL240DAO tbl240DAO;

    @Mock
    private TBL300DAO tbl300DAO;

    @Mock
    private TBL300BDAO tbl300BDAO;


    @InjectMocks
    private ProgressRecordLogicImpl progressRecordLogic;
    
    @InjectMocks
    private ProgressRecordLogicImpl progressRecordLogicImpl;

    private final static String APARTMENT_ID = "100000000001";
    private final static String NEWEST_NOTIFICATION_NO = "100000000001";
    private final static String NEXT_NOTIFICATION_DATE = "20241212";
    private final static String ACCEPT_STATUS  = "1";
    private final static String SUPPORT = "1";

    // Data tbl300 1
    private final static String PROGRESS_RECORD_NO = "00001";
    private final static String RELATED_NUMBER_1 = "00001";
    private final static String CORRESPOND_DATE = "201912121010";
    private final static String TYPE_CODE = "1";
    private final static String CORRESPOND_TYPE_CODE = null;
    private final static String NOTIFICATION_METHOD_CODE = "2";
    private final static String SUPPORT_CODE = "1";
    private final static String PROGRESS_RECORD_OVERVIEW = "Abcde";
    private final static String NOTICE_TYPE_CODE = "1";
    
    // data tbl300 2
    private final static String PROGRESS_RECORD_NO_2 = "00002";
    private final static String RELATED_NUMBER_2 = "00001";
    private final static String CORRESPOND_DATE_2 = "201911111010";
    private final static String TYPE_CODE_2 = null;
    private final static String CORRESPOND_TYPE_CODE_2 = "01";
    private final static String NOTIFICATION_METHOD_CODE_2 = "2";
    private final static String SUPPORT_CODE_2 = "2";
    private final static String PROGRESS_RECORD_OVERVIEW_2 = "ABCDE";
    private final static String NOTICE_TYPE_CODE_2 = "2";

    private final static String SUPERVISED_NOTICE_NO = "100000000001";
    
    // const
    private static final String DETAIL_VIEW = "0";
    private static final String REPORT_DISPLAY = "1";
    private static final String REGISTERED = "?????????????????????";
    private static final String EMAILED = "?????????????????????<br/>???";
    private static final String CHANGED = "?????????????????????<br/>???";
    private static final String SUPPORTED = "?????????????????????<br/>???";
    private static final String BRACKETS = "???";
    private static final String CORRECTED_BY = "??????????????????????????????";
    private static final String EMAILED_NOTIFICATION = "???????????????????????????????????????????????????";
    private static final String EMAILED_CHANGE_NOTIFICATION = "?????????????????????????????????????????????????????????";
    private static final String EMAILED_ADVICE_NOTIFICATION = "?????????????????????????????????????????????";
    private static final String EMAILED_SURVEY_NOTIFICATION = "???????????????????????????????????????????????????";

    private final String RELATED_NUMBER = "1000000197";
    private final String AUTHORITY_MODIFY_FLAG = "";
    private final String MODIFY_DETAILS = "";
    private final String USER_TYPE = "";
    
    
    private TBL100Entity getEntityTbl100ById() {
        TBL100Entity entityTbl100 = new TBL100Entity();
        entityTbl100.setNewestNotificationNo(NEWEST_NOTIFICATION_NO);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate nextNoDate = LocalDate.parse(NEXT_NOTIFICATION_DATE,formatter);
        entityTbl100.setNextNotificationDate(nextNoDate);
        entityTbl100.setSupport(SUPPORT_CODE);
        return entityTbl100;
    }
    
    private LocalDate convertStringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate nextNoDate = LocalDate.parse(date,formatter);
        return nextNoDate;
    }
    private TBL200Entity getEntityTbl200ByNewestNotificationNo() {
        TBL200Entity entityTbl200 = new TBL200Entity();
        entityTbl200.setAcceptStatus(ACCEPT_STATUS);
        return entityTbl200;
    }
    
    private List<TBL240Entity> getEntityTbl240ByByApartmentIdAndNotificationNoNotNull() {
        List<TBL240Entity> listEntityTbl240 = new ArrayList<TBL240Entity>();
        TBL240Entity entityTbl240 = new TBL240Entity();
        entityTbl240.setAppendixNo(SUPERVISED_NOTICE_NO);
        listEntityTbl240.add(entityTbl240);
        return listEntityTbl240;
    }
    
    private List<ProgressRecordDetailsVo> getListProgressRecordByIdAndSizeIs2() {
        List<ProgressRecordDetailsVo> listVo = new ArrayList<>();
        ProgressRecordDetailsVo vo = new ProgressRecordDetailsVo();
        ProgressRecordDetailsVo vo2 = new ProgressRecordDetailsVo();

        vo.setCorrespondDate(CORRESPOND_DATE);
        vo.setTypeCode(TYPE_CODE);
        vo.setCorrespondTypeCode(CORRESPOND_TYPE_CODE);
        vo.setSupportCode(SUPPORT_CODE);
        vo.setProgressRecordOverview(PROGRESS_RECORD_OVERVIEW);
        vo.setNoticeTypeCode(NOTICE_TYPE_CODE);
        vo.setProgressRecordNo(PROGRESS_RECORD_NO);
        vo.setRelatedNumber(RELATED_NUMBER_1);
        vo.setAuthorityModifyFlag("1");
        vo.setUserType("1");
        listVo.add(vo);

        vo2.setCorrespondDate(CORRESPOND_DATE_2);
        vo2.setTypeCode(TYPE_CODE_2);
        vo2.setCorrespondTypeCode(CORRESPOND_TYPE_CODE_2);
        vo2.setSupportCode(SUPPORT_CODE_2);
        vo2.setProgressRecordOverview(PROGRESS_RECORD_OVERVIEW_2);
        vo2.setNoticeTypeCode(NOTICE_TYPE_CODE_2);
        vo2.setProgressRecordNo(PROGRESS_RECORD_NO_2);
        vo2.setRelatedNumber(RELATED_NUMBER_2);
        vo2.setAuthorityModifyFlag("1");
        vo2.setUserType("1");
        listVo.add(vo2);

        return listVo;
    }

    private List<ProgressRecordDetailsVo> getListProgressRecordSizeIs1AndTypeCodeIsNotNull() {
        List<ProgressRecordDetailsVo> listVo = new ArrayList<>();
        ProgressRecordDetailsVo vo = new ProgressRecordDetailsVo();
        vo.setCorrespondDate(CORRESPOND_DATE);
        vo.setTypeCode(TYPE_CODE);
        vo.setCorrespondTypeCode(CORRESPOND_TYPE_CODE);
        vo.setSupportCode(SUPPORT_CODE);
        vo.setProgressRecordOverview(PROGRESS_RECORD_OVERVIEW);
        vo.setNoticeTypeCode(NOTICE_TYPE_CODE);
        vo.setProgressRecordNo(PROGRESS_RECORD_NO);
        vo.setRelatedNumber(RELATED_NUMBER_1);
        vo.setAuthorityModifyFlag("1");
        vo.setUserType("1");
        listVo.add(vo);
        listVo.add(vo);
        return listVo;
    }

    private List<ProgressRecordDetailsVo> getListProgressRecordSizeIs1AndTypeCodeIsNull() {
        List<ProgressRecordDetailsVo> listVo = new ArrayList<>();
        ProgressRecordDetailsVo vo2 = new ProgressRecordDetailsVo();
        vo2.setCorrespondDate(CORRESPOND_DATE_2);
        vo2.setTypeCode(TYPE_CODE_2);
        vo2.setCorrespondTypeCode(CORRESPOND_TYPE_CODE_2);
        vo2.setSupportCode(SUPPORT_CODE_2);
        vo2.setProgressRecordOverview(PROGRESS_RECORD_OVERVIEW_2);
        vo2.setNoticeTypeCode(NOTICE_TYPE_CODE_2);
        vo2.setProgressRecordNo(PROGRESS_RECORD_NO_2);
        vo2.setRelatedNumber(RELATED_NUMBER_2);
        vo2.setAuthorityModifyFlag("1");
        vo2.setUserType("1");
        listVo.add(vo2);
        return listVo;
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-001????????????N?????????ID???01????????????????????????Test Get Status Information When EntityTbl100 And EntityTbl200 Are Not Null
     */
    @Test
    public void testGetStatusInformation_WhenEntityTbl100AndEntityTbl200AreNotNull() {
        TBL100Entity entityTbl100 = getEntityTbl100ById();
        TBL200Entity entityTbl200 = getEntityTbl200ByNewestNotificationNo();
        List<TBL240Entity> listEntityTbl240 = getEntityTbl240ByByApartmentIdAndNotificationNoNotNull();

        Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entityTbl100);
        Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(entityTbl200);
        Mockito.when(tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNo(Mockito.anyString())).thenReturn(listEntityTbl240);

        StatusInformationVo statusInfo = progressRecordLogic.getStatusInformation(APARTMENT_ID);

        assertThat(statusInfo.getReportStatus()).isEqualTo("?????????");
        assertThat(statusInfo.getAcceptedStatus()).isEqualTo("?????????");
        assertThat(statusInfo.getUrgeStatus()).isEqualTo("?????????");

    }
    
    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-002????????????N?????????ID???02????????????????????????Test Get Status Information When EntityTbl100 And EntityTbl200 AreNot Null And Accepted status Is Unaccepted
     */
    @Test
    public void testGetStatusInformation_WhenEntityTbl100AndEntityTbl200AreNotNullAndAcceptedstatusIsUnaccepted() {
        TBL100Entity entityTbl100 = getEntityTbl100ById();
        TBL200Entity entityTbl200 = getEntityTbl200ByNewestNotificationNo();
        List<TBL240Entity> listEntityTbl240 = getEntityTbl240ByByApartmentIdAndNotificationNoNotNull();
        entityTbl200.setAcceptStatus("1");
        Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entityTbl100);
        Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(entityTbl200);
        Mockito.when(tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNo(Mockito.anyString())).thenReturn(listEntityTbl240);

        StatusInformationVo statusInfo = progressRecordLogic.getStatusInformation(APARTMENT_ID);

        assertThat(statusInfo.getReportStatus()).isEqualTo("?????????");
        assertThat(statusInfo.getAcceptedStatus()).isEqualTo("?????????");
        assertThat(statusInfo.getUrgeStatus()).isEqualTo("?????????");

    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-003????????????N?????????ID???03????????????????????????Test Get Status Information When Entity Tbl100 And EntityTbl200 AreNotNull And Acceptedstatus Is Accepted
     */
    @Test
    public void testGetStatusInformation_WhenEntityTbl100AndEntityTbl200AreNotNullAndAcceptedstatusIsAccepted_ThenReturnReportStatusIsReportedAndUrgeStatusIsNotified() {
        TBL100Entity entityTbl100 = getEntityTbl100ById();
        TBL200Entity entityTbl200 = getEntityTbl200ByNewestNotificationNo();
        List<TBL240Entity> listEntityTbl240 = getEntityTbl240ByByApartmentIdAndNotificationNoNotNull();
        entityTbl200.setAcceptStatus("2");
        Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entityTbl100);
        Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(entityTbl200);
        Mockito.when(tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNo(Mockito.anyString())).thenReturn(listEntityTbl240);

        StatusInformationVo statusInfo = progressRecordLogic.getStatusInformation(APARTMENT_ID);

        assertThat(statusInfo.getReportStatus()).isEqualTo("?????????");
        assertThat(statusInfo.getAcceptedStatus()).isEqualTo("?????????");
        assertThat(statusInfo.getUrgeStatus()).isEqualTo("?????????");

    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-004????????????N?????????ID???04????????????????????????Test Get Status Information When Next Notification Date is After SystemDate
     */
    @Test
    public void testGetStatusInformation_WhenNextNotificationDateisAfterSystemDate_ThenReturnReportStatusIsUnreported() {
        TBL100Entity entityTbl100 = getEntityTbl100ById();
        TBL200Entity entityTbl200 = getEntityTbl200ByNewestNotificationNo();
        List<TBL240Entity> listEntityTbl240 = getEntityTbl240ByByApartmentIdAndNotificationNoNotNull();
        entityTbl100.setNextNotificationDate(convertStringToLocalDate("20191212"));
 
        Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entityTbl100);
        Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(entityTbl200);
        Mockito.when(tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNo(Mockito.anyString())).thenReturn(listEntityTbl240);
        
        StatusInformationVo statusInfo = progressRecordLogic.getStatusInformation(APARTMENT_ID);
        
        assertThat(statusInfo.getReportStatus()).isEqualTo("??????");
        assertThat(statusInfo.getAcceptedStatus()).isEqualTo("?????????");
        assertThat(statusInfo.getUrgeStatus()).isEqualTo("?????????");
        assertThat(statusInfo.getSupportCode()).isEqualTo("??????");

    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-005????????????N?????????ID???05????????????????????????Test Get Status Information When Next Notification Date Equal SystemDate
     */
    @Test
    public void testGetStatusInformation_WhenNextNotificationDateEqualSystemDate_ThenReturnReportStatusIsUnreported() {
        TBL100Entity entityTbl100 = getEntityTbl100ById();
        TBL200Entity entityTbl200 = getEntityTbl200ByNewestNotificationNo();
        List<TBL240Entity> listEntityTbl240 = getEntityTbl240ByByApartmentIdAndNotificationNoNotNull();
        entityTbl100.setNextNotificationDate(LocalDate.now());
 
        Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entityTbl100);
        Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(entityTbl200);
        Mockito.when(tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNo(Mockito.anyString())).thenReturn(listEntityTbl240);
        
        StatusInformationVo statusInfo = progressRecordLogic.getStatusInformation(APARTMENT_ID);
        
        assertThat(statusInfo.getReportStatus()).isEqualTo("??????");
        assertThat(statusInfo.getAcceptedStatus()).isEqualTo("?????????");
        assertThat(statusInfo.getUrgeStatus()).isEqualTo("?????????");
        assertThat(statusInfo.getSupportCode()).isEqualTo("??????");
        
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-006????????????E?????????ID???06????????????????????????Test Get Status Information When Next Notification Date Is Null
     */
    @Test
    public void testGetStatusInformation_whenNextNotificationDateIsNull_ThenReturnReportStatusIsUnreported() {
        TBL100Entity entityTbl100 = getEntityTbl100ById();
        TBL200Entity entityTbl200 = getEntityTbl200ByNewestNotificationNo();
        List<TBL240Entity> listEntityTbl240 = getEntityTbl240ByByApartmentIdAndNotificationNoNotNull();
        entityTbl100.setNextNotificationDate(null);
 
        Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entityTbl100);
        Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(entityTbl200);
        Mockito.when(tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNo(Mockito.anyString())).thenReturn(listEntityTbl240);
        
        StatusInformationVo statusInfo = progressRecordLogic.getStatusInformation(APARTMENT_ID);
        
        assertThat(statusInfo.getReportStatus()).isEqualTo("??????");
        assertThat(statusInfo.getAcceptedStatus()).isEqualTo("?????????");
        assertThat(statusInfo.getUrgeStatus()).isEqualTo("?????????");
        assertThat(statusInfo.getSupportCode()).isEqualTo("??????");
        
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-007????????????E?????????ID???07????????????????????????Test Get Status Information When EntityTbl100 Is Null And Aparment Id Not Found Then StatusInformationVo Is Null
     */
    @Test
    public void testGetStatusInformation_whenEntityTbl100IsNull_ThenAparmentIdNotFoundAndStatusInformationVoIsNull() {
        TBL100Entity entityTbl100 = null;
        TBL200Entity entityTbl200 = getEntityTbl200ByNewestNotificationNo();
        List<TBL240Entity> listEntityTbl240 = getEntityTbl240ByByApartmentIdAndNotificationNoNotNull();
        Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entityTbl100);
        Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(entityTbl200);
        Mockito.when(tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNo(Mockito.anyString())).thenReturn(listEntityTbl240);
        StatusInformationVo statusInfo = progressRecordLogic.getStatusInformation(APARTMENT_ID);
        assertThat(statusInfo).isNull();
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-008????????????E?????????ID???08????????????????????????Test Get Status Information When Entity Tbl200 Is Null Newest Notification No Not Found Then StatusInformationVo Is Null
     */
    @Test
    public void testGetStatusInformation_WhenEntityTbl200IsNull_NewestNotificationNoNotFoundThenStatusInformationVoIsNull() {
        TBL100Entity entityTbl100 = getEntityTbl100ById();
        TBL200Entity entityTbl200 = null;
        List<TBL240Entity> listEntityTbl240 = getEntityTbl240ByByApartmentIdAndNotificationNoNotNull();
        Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entityTbl100);
        Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(entityTbl200);
        Mockito.when(tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNo(Mockito.anyString())).thenReturn(listEntityTbl240);
        StatusInformationVo statusInfo = progressRecordLogic.getStatusInformation(entityTbl100.getNewestNotificationNo());
        assertThat(statusInfo).isNull();
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-009????????????N?????????ID???09???????????????????????? Test Get Status Information When EntityTbl240 Is Null Then Return Urge Status Is Not Notified
     */
    @Test
    public void testGetStatusInformation_WhenEntityTbl240IsNull_ThenReturnUrgeStatusIsNotNotified() {
        TBL100Entity entityTbl100 = getEntityTbl100ById();
        TBL200Entity entityTbl200 = getEntityTbl200ByNewestNotificationNo();
        List<TBL240Entity> listEntityTbl240 = null;
 
        Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entityTbl100);
        Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(entityTbl200);
        Mockito.when(tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNo(Mockito.anyString())).thenReturn(listEntityTbl240);
        
        StatusInformationVo statusInfo = progressRecordLogic.getStatusInformation(APARTMENT_ID);
        
        assertThat(statusInfo.getReportStatus()).isEqualTo("?????????");
        assertThat(statusInfo.getAcceptedStatus()).isEqualTo("?????????");
        assertThat(statusInfo.getUrgeStatus()).isEqualTo("?????????");
        assertThat(statusInfo.getSupportCode()).isEqualTo("??????");
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-010????????????N?????????ID???10???????????????????????? Test Get Status Information When Entity Tbl240 Size Is Zero Then Return Urge Status Is NotNotified

     */
    @Test
    public void testGetStatusInformation_WhenEntityTbl240SizeIsZero_ThenReturnUrgeStatusIsNotNotified() {
        TBL100Entity entityTbl100 = getEntityTbl100ById();
        TBL200Entity entityTbl200 = getEntityTbl200ByNewestNotificationNo();
        List<TBL240Entity> listEntityTbl240 = new ArrayList<TBL240Entity>();
 
        Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entityTbl100);
        Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(entityTbl200);
        Mockito.when(tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNo(Mockito.anyString())).thenReturn(listEntityTbl240);
        
        StatusInformationVo statusInfo = progressRecordLogic.getStatusInformation(APARTMENT_ID);
        
        assertThat(statusInfo.getReportStatus()).isEqualTo("?????????");
        assertThat(statusInfo.getAcceptedStatus()).isEqualTo("?????????");
        assertThat(statusInfo.getUrgeStatus()).isEqualTo("?????????");
        assertThat(statusInfo.getSupportCode()).isEqualTo("??????");
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-011????????????N?????????ID???11???????????????????????? Test Get List Progress Record Detail When List Entity300 Is Null Then Return Aparment Id Is Not Found And ListProgressDetails Is Null
     */
    @Test
    public void testGetListProgressRecordDetail_WhenListEntity300IsNull_AparmentIdNotFoundThenThrowException() {
        Mockito.when(tbl300BDAO.getListProgressRecordDetailByApartmentId(Mockito.anyString())).thenReturn(null);
        List<ProgressRecordDetailsVo> listProgressDetails = progressRecordLogic.getListProgressRecordDetail(APARTMENT_ID);
        Assert.assertEquals(listProgressDetails.size(), 0);
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-012????????????N?????????ID???12???????????????????????? Test Get List Progress Record Detail When List Entity300 Size Is Zero Then Return Aparment Id Not Found And ListProgressDetails Is Null
     */
    @Test
    public void testGetListProgressRecordDetail_WhenListEntity300SizeIsZero_AparmentIdNotFoundThenListProgressDetailsIsNull() {
        List<ProgressRecordDetailsVo> listProgressDetailsByApartmentId = new ArrayList<ProgressRecordDetailsVo>();
        Mockito.when(tbl300BDAO.getListProgressRecordDetailByApartmentId(Mockito.anyString())).thenReturn(listProgressDetailsByApartmentId);
        List<ProgressRecordDetailsVo> listProgressDetails = progressRecordLogic.getListProgressRecordDetail(APARTMENT_ID);
        Assert.assertEquals(listProgressDetails.size(), 0);
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-013????????????N?????????ID???13????????????????????????test Get List Progress Record Detail When List Entuty 300 Is Not Empty
     */
    @Test
    public void testGetListProgressRecordDetail_WhenListEntuty300IsNotEmpty() {
        List<ProgressRecordDetailsVo> listVo = getListProgressRecordByIdAndSizeIs2();

        Mockito.when(tbl300BDAO.getListProgressRecordDetailByApartmentId(Mockito.anyString())).thenReturn(listVo);
        List<ProgressRecordDetailsVo> listProgressDetails = progressRecordLogic.getListProgressRecordDetail(APARTMENT_ID);
        
        assertThat(listProgressDetails.get(0).getCorrespondDate()).isEqualTo("2019/12/12 10:10");
        assertThat(listProgressDetails.get(0).getProgressRecordNo()).isEqualTo(PROGRESS_RECORD_NO);
        assertThat(listProgressDetails.get(0).getRelatedNumber()).isEqualTo(RELATED_NUMBER_1);
        assertThat(listProgressDetails.get(0).getTypeCode()).isEqualTo("??????");
        assertThat(listProgressDetails.get(0).getBtnDisplay()).isEqualTo(REPORT_DISPLAY);
        assertThat(listProgressDetails.get(0).getProgressRecordDetail()).isEqualTo("??????" + REGISTERED);
        assertThat(listProgressDetails.get(0).getReportName()).isEqualTo("RP010Create");

        assertThat(listProgressDetails.get(1).getCorrespondDate()).isEqualTo("2019/11/11 10:10");
        assertThat(listProgressDetails.get(1).getProgressRecordNo()).isEqualTo(PROGRESS_RECORD_NO_2);
        assertThat(listProgressDetails.get(1).getRelatedNumber()).isEqualTo(RELATED_NUMBER_2);
        assertThat(listProgressDetails.get(1).getTypeCode()).isEqualTo("??????");
        assertThat(listProgressDetails.get(1).getBtnDisplay()).isEqualTo(DETAIL_VIEW);
        assertThat(listProgressDetails.get(1).getReportName()).isEqualTo("");
        assertThat(listProgressDetails.get(1).getProgressRecordDetail()).isEqualTo("???????????????????????????<br/>???" + PROGRESS_RECORD_OVERVIEW_2 + BRACKETS);
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-014????????????N?????????ID???14???????????????????????? Test Get List Progress Record Detail When Correspond Date Is Blank 
     */
    @Test
    public void testGetListProgressRecordDetail_WhenCorrespondDateIsBlank() {
        List<ProgressRecordDetailsVo> listVo = getListProgressRecordByIdAndSizeIs2();
        listVo.get(0).setCorrespondDate("");
        Mockito.when(tbl300BDAO.getListProgressRecordDetailByApartmentId(Mockito.anyString())).thenReturn(listVo);
        List<ProgressRecordDetailsVo> listProgressDetails = progressRecordLogic.getListProgressRecordDetail(APARTMENT_ID);
        assertThat(listProgressDetails.get(0).getCorrespondDate()).isEqualTo(null);
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-015????????????N?????????ID???15????????????????????????Test Get List Progress Record Detail When Type Code Is Null
     */
    @Test
    public void testGetListProgressRecordDetail_WhenTypeCodeIsNull() {
        List<ProgressRecordDetailsVo> listVo = getListProgressRecordSizeIs1AndTypeCodeIsNull();
        Mockito.when(tbl300BDAO.getListProgressRecordDetailByApartmentId(Mockito.anyString())).thenReturn(listVo);
        List<ProgressRecordDetailsVo> listProgressDetails = progressRecordLogic.getListProgressRecordDetail(APARTMENT_ID);
        assertThat(listProgressDetails.get(0).getTypeCode()).isEqualTo("??????");
        assertThat(listProgressDetails.get(0).getBtnDisplay()).isEqualTo(DETAIL_VIEW);
        assertThat(listProgressDetails.get(0).getReportName()).isEqualTo("");
        assertThat(listProgressDetails.get(0).getProgressRecordDetail()).isEqualTo("???????????????????????????<br/>???ABCDE???");
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-016????????????N?????????ID???16???????????????????????? Test Get List Progress Record Detail When Type Code Is Change Notification Return Report Name Is RP010Update
     */
    @Test
    public void testGetListProgressRecordDetail_WhenTypeCodeIsChangeNotification_ReturnReportNameIsRP010Update() {
        List<ProgressRecordDetailsVo> listVo = getListProgressRecordSizeIs1AndTypeCodeIsNotNull();
        listVo.get(0).setTypeCode("2");
        Mockito.when(tbl300BDAO.getListProgressRecordDetailByApartmentId(Mockito.anyString())).thenReturn(listVo);
        List<ProgressRecordDetailsVo> listProgressDetails = progressRecordLogic.getListProgressRecordDetail(APARTMENT_ID);
        assertThat(listProgressDetails.get(0).getReportName()).isEqualTo("RP010Update");
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-017????????????N?????????ID???17???????????????????????? Test Get List Progress Record Detail When Type Code Is Advice Notice Return Report Name Is RP030
     */
    @Test
    public void getListProgressRecordDetail_WhenTypeCodeIsAdviceNotice_ReturnReportNameIsRP030() {
        List<ProgressRecordDetailsVo> listVo = getListProgressRecordSizeIs1AndTypeCodeIsNotNull();
        listVo.get(0).setTypeCode("5");
        Mockito.when(tbl300BDAO.getListProgressRecordDetailByApartmentId(Mockito.anyString())).thenReturn(listVo);
        List<ProgressRecordDetailsVo> listProgressDetails = progressRecordLogic.getListProgressRecordDetail(APARTMENT_ID);
        assertThat(listProgressDetails.get(0).getReportName()).isEqualTo("RP030");
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-018????????????N?????????ID???18???????????????????????? Test Get List Progress Record Detail When Type Code Is Survey Notice Return Report Name Is RP040
     */
    @Test
    public void getListProgressRecordDetail_WhenTypeCodeIsSurveyNotice_ReturnReportNameIsRP040() {
        List<ProgressRecordDetailsVo> listVo = getListProgressRecordSizeIs1AndTypeCodeIsNotNull();
        listVo.get(0).setTypeCode("6");
        Mockito.when(tbl300BDAO.getListProgressRecordDetailByApartmentId(Mockito.anyString())).thenReturn(listVo);
        List<ProgressRecordDetailsVo> listProgressDetails = progressRecordLogic.getListProgressRecordDetail(APARTMENT_ID);
        assertThat(listProgressDetails.get(0).getReportName()).isEqualTo("RP040");
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-019????????????N?????????ID???19???????????????????????? Test Get List Progress Record Detail When Type Code Is Dunning NoticeFirstTime ReturnReportNameIsRP050
     */
    @Test
    public void getListProgressRecordDetail_WhenTypeCodeIsDunningNoticeFirstTime_ReturnReportNameIsRP050() {
        List<ProgressRecordDetailsVo> listVo = getListProgressRecordSizeIs1AndTypeCodeIsNotNull();
        listVo.get(0).setTypeCode("7");
        Mockito.when(tbl300BDAO.getListProgressRecordDetailByApartmentId(Mockito.anyString())).thenReturn(listVo);
        List<ProgressRecordDetailsVo> listProgressDetails = progressRecordLogic.getListProgressRecordDetail(APARTMENT_ID);
        assertThat(listProgressDetails.get(0).getReportName()).isEqualTo("RP050");
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-020????????????N?????????ID???20???????????????????????? Test Get List Progress Record Detail When Type Code Is Dunning NoticeSecondTime ReturnReportNameIsRP060
     */
    @Test
    public void getListProgressRecordDetail_WhenTypeCodeIsDunningNoticeSecondTime_ReturnReportNameIsRP060() {
        List<ProgressRecordDetailsVo> listVo = getListProgressRecordSizeIs1AndTypeCodeIsNotNull();
        listVo.get(0).setTypeCode("8");
        Mockito.when(tbl300BDAO.getListProgressRecordDetailByApartmentId(Mockito.anyString())).thenReturn(listVo);
        List<ProgressRecordDetailsVo> listProgressDetails = progressRecordLogic.getListProgressRecordDetail(APARTMENT_ID);
        assertThat(listProgressDetails.get(0).getReportName()).isEqualTo("RP060");
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-021????????????N?????????ID???21???????????????????????? Test Get List Progress Record Detail When Type Code Is Emailed Notification
     */
    @Test
    public void getListProgressRecordDetail_WhenTypeCodeIsEmailedNotification() {
        List<ProgressRecordDetailsVo> listVo = getListProgressRecordSizeIs1AndTypeCodeIsNotNull();
        listVo.get(0).setTypeCode("9");
        Mockito.when(tbl300BDAO.getListProgressRecordDetailByApartmentId(Mockito.anyString())).thenReturn(listVo);
        List<ProgressRecordDetailsVo> listProgressDetails = progressRecordLogic.getListProgressRecordDetail(APARTMENT_ID);
        assertThat(listProgressDetails.get(0).getProgressRecordDetail()).isEqualTo("???????????????????????????????????????????????????");
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-022????????????N?????????ID???22???????????????????????? Test Get List Progress Record Detail When Type Code Is Emailed Change Notification
     */
    @Test
    public void getListProgressRecordDetail_WhenTypeCodeIsEmailedChangeNotification() {
        List<ProgressRecordDetailsVo> listVo = getListProgressRecordSizeIs1AndTypeCodeIsNotNull();
        listVo.get(0).setTypeCode("10");
        Mockito.when(tbl300BDAO.getListProgressRecordDetailByApartmentId(Mockito.anyString())).thenReturn(listVo);
        List<ProgressRecordDetailsVo> listProgressDetails = progressRecordLogic.getListProgressRecordDetail(APARTMENT_ID);
        assertThat(listProgressDetails.get(0).getProgressRecordDetail()).isEqualTo("?????????????????????????????????????????????????????????");
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-023????????????N?????????ID???23???????????????????????? Test Get List Progress Record Detail When Type Code Is Emailed Advice Notification
     */
    @Test
    public void getListProgressRecordDetail_WhenTypeCodeIsEmailedAdviceNotification() {
        List<ProgressRecordDetailsVo> listVo = getListProgressRecordSizeIs1AndTypeCodeIsNotNull();
        listVo.get(0).setTypeCode("11");
        Mockito.when(tbl300BDAO.getListProgressRecordDetailByApartmentId(Mockito.anyString())).thenReturn(listVo);
        List<ProgressRecordDetailsVo> listProgressDetails = progressRecordLogic.getListProgressRecordDetail(APARTMENT_ID);
        assertThat(listProgressDetails.get(0).getProgressRecordDetail()).isEqualTo("?????????????????????????????????????????????");
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-024????????????N?????????ID???24???????????????????????? Test Get List Progress Record Detail When Type Code Is Emailed Survey Notification
     */
    @Test
    public void getListProgressRecordDetail_WhenTypeCodeIsEmailedSurveyNotification() {
        List<ProgressRecordDetailsVo> listVo = getListProgressRecordSizeIs1AndTypeCodeIsNotNull();
        listVo.get(0).setTypeCode("12");
        Mockito.when(tbl300BDAO.getListProgressRecordDetailByApartmentId(Mockito.anyString())).thenReturn(listVo);
        List<ProgressRecordDetailsVo> listProgressDetails = progressRecordLogic.getListProgressRecordDetail(APARTMENT_ID);
        assertThat(listProgressDetails.get(0).getProgressRecordDetail()).isEqualTo("???????????????????????????????????????????????????");
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-025????????????N?????????ID???25???????????????????????? Test Get List Progress Record Detail When Type Code Is Notification AcceptanceAndAuthorityModify Flag Is Modify
     */
    @Test
    public void getListProgressRecordDetail_WhenTypeCodeIsNotificationAcceptanceAndAuthorityModifyFlagIsModify() {
        List<ProgressRecordDetailsVo> listVo = getListProgressRecordSizeIs1AndTypeCodeIsNotNull();
        listVo.get(0).setTypeCode("3");
        listVo.get(0).setUserType("1");
        Mockito.when(tbl300BDAO.getListProgressRecordDetailByApartmentId(Mockito.anyString())).thenReturn(listVo);
        List<ProgressRecordDetailsVo> listProgressDetails = progressRecordLogic.getListProgressRecordDetail(APARTMENT_ID);
        assertThat(listProgressDetails.get(0).getReportName()).isEqualTo("RP020");
        assertThat(listProgressDetails.get(0).getProgressRecordDetail()).isEqualTo("?????????????????????????????????<br/>??????????????????????????????????????????");
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-026????????????N?????????ID???26???????????????????????? Test Get List Progress Record Detail When Type Code Is Change Notification Acceptance And Authority Modify Flag Is Modify
     */
    @Test
    public void getListProgressRecordDetailWhenTypeCodeIsChangeNotificationAcceptanceAndAuthorityModifyFlagIsModify() {
        List<ProgressRecordDetailsVo> listVo = getListProgressRecordSizeIs1AndTypeCodeIsNotNull();
        listVo.get(0).setTypeCode("4");
        listVo.get(0).setUserType("1");
        Mockito.when(tbl300BDAO.getListProgressRecordDetailByApartmentId(Mockito.anyString())).thenReturn(listVo);
        List<ProgressRecordDetailsVo> listProgressDetails = progressRecordLogic.getListProgressRecordDetail(APARTMENT_ID);
        assertThat(listProgressDetails.get(0).getReportName()).isEqualTo("RP020");
        assertThat(listProgressDetails.get(0).getProgressRecordDetail()).isEqualTo("???????????????????????????????????????<br/>??????????????????????????????????????????");
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-027????????????N?????????ID???27???????????????????????? Test Get List Progress Record Detail When Type Code Is Change Notification Acceptance And Authority Modify Flag Is NotModify
     */
    @Test
    public void getListProgressRecordDetailWhenTypeCodeIsChangeNotificationAcceptanceAndAuthorityModifyFlagIsNotModify() {
        List<ProgressRecordDetailsVo> listVo = getListProgressRecordSizeIs1AndTypeCodeIsNotNull();
        listVo.get(0).setTypeCode("4");
        listVo.get(0).setAuthorityModifyFlag("2");
        Mockito.when(tbl300BDAO.getListProgressRecordDetailByApartmentId(Mockito.anyString())).thenReturn(listVo);
        List<ProgressRecordDetailsVo> listProgressDetails = progressRecordLogic.getListProgressRecordDetail(APARTMENT_ID);
        assertThat(listProgressDetails.get(0).getProgressRecordDetail()).isEqualTo("???????????????????????????????????????");
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-028????????????N?????????ID???28???????????????????????? Test Get List Progress Record Detail When Type Code Is Change Notification Acceptance And Authority Modify Flag Is Modify And User Type Is Maintenance Company
     */
    @Test
    public void getListProgressRecordDetailWhenTypeCodeIsChangeNotificationAcceptanceAndAuthorityModifyFlagIsModifyAndUserTypeIsMaintenanceCompany() {
        List<ProgressRecordDetailsVo> listVo = getListProgressRecordSizeIs1AndTypeCodeIsNotNull();
        listVo.get(0).setTypeCode("4");
        listVo.get(0).setUserType("4");
        Mockito.when(tbl300BDAO.getListProgressRecordDetailByApartmentId(Mockito.anyString())).thenReturn(listVo);
        List<ProgressRecordDetailsVo> listProgressDetails = progressRecordLogic.getListProgressRecordDetail(APARTMENT_ID);
        assertThat(listProgressDetails.get(0).getProgressRecordDetail()).isEqualTo("???????????????????????????????????????<br/>?????????????????????????????????????????????");
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-029????????????N?????????ID???29???????????????????????? Test Get List Progress Record Detail When Type Code Is Null And Correspond Type Code Is Notice Mail
     */
    @Test
    public void getListProgressRecordDetailWhenTypeCodeIsNullAndCorrespondTypeCodeIsNoticeMail() {
        List<ProgressRecordDetailsVo> listVo = getListProgressRecordSizeIs1AndTypeCodeIsNull();
        listVo.get(0).setCorrespondTypeCode("05");
        listVo.get(0).setNoticeTypeCode("1");
        Mockito.when(tbl300BDAO.getListProgressRecordDetailByApartmentId(Mockito.anyString())).thenReturn(listVo);
        List<ProgressRecordDetailsVo> listProgressDetails = progressRecordLogic.getListProgressRecordDetail(APARTMENT_ID);
        assertThat(listProgressDetails.get(0).getProgressRecordDetail()).isEqualTo("??????????????????????????????????????????<br/>???ABCDE???");
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-030????????????N?????????ID???30???????????????????????? Test  getListProgressRecordDetailWhenTypeCodeIsNullAndCorrespondTypeCodeIsChangeOfTokyoSuportTarget
     */
    @Test
    public void getListProgressRecordDetailWhenTypeCodeIsNullAndCorrespondTypeCodeIsChangeOfTokyoSuportTarget() {
        List<ProgressRecordDetailsVo> listVo = getListProgressRecordSizeIs1AndTypeCodeIsNull();
        listVo.get(0).setCorrespondTypeCode("06");
        listVo.get(0).setSupportCode("1");
        Mockito.when(tbl300BDAO.getListProgressRecordDetailByApartmentId(Mockito.anyString())).thenReturn(listVo);
        List<ProgressRecordDetailsVo> listProgressDetails = progressRecordLogic.getListProgressRecordDetail(APARTMENT_ID);
        assertThat(listProgressDetails.get(0).getProgressRecordDetail()).isEqualTo("???????????????????????????<br/>???ABCDE???");
    }

    /**
     * ??????ID???GEA0110????????????????????????ID???UT-GEA0110-031????????????N?????????ID???31????????????????????????  Test getListProgressRecordDetailWhenTypeCodeIsNullAndCorrespondTypeCodeIsRecognitionOfSectionalOwners
     */
    @Test
    public void getListProgressRecordDetailWhenTypeCodeIsNullAndCorrespondTypeCodeIsRecognitionOfSectionalOwners() {
        List<ProgressRecordDetailsVo> listVo = getListProgressRecordSizeIs1AndTypeCodeIsNull();
        listVo.get(0).setCorrespondTypeCode("18");
        Mockito.when(tbl300BDAO.getListProgressRecordDetailByApartmentId(Mockito.anyString())).thenReturn(listVo);
        List<ProgressRecordDetailsVo> listProgressDetails = progressRecordLogic.getListProgressRecordDetail(APARTMENT_ID);
        assertThat(listProgressDetails.get(0).getProgressRecordDetail()).isEqualTo("??????????????????????????????18????????????????????????????????????<br/>???ABCDE???");
    }

    /* Start test getProgressRecordInfoForMEA0110 */
    /**
     * ??????ID:MEA0110/?????????????????????ID:UT- MEA0110-001/??????:N/??????????????????:Test Get Progress Record Info For MEA0110 When Progress Record Accept User List Is Empty And Progress Record List Is Empty
     */
    @Test
    public void testGetProgressRecordInfoForMEA0110WhenProgressRecordAcceptUserListIsEmptyAndProgressRecordListIsEmpty() {
        List<ProgressRecordAcceptUserVo> progressRecordAcceptUserVo = new ArrayList<ProgressRecordAcceptUserVo>();
        Mockito.when(tbl300BDAO.getProgressRecordAcceptUser(Mockito.anyString(), Mockito.any())).thenReturn(progressRecordAcceptUserVo);
        
        ProgressRecorInfoWrapperVo progressRecordInfoForMEA0110 = progressRecordLogicImpl.getProgressRecordInfoForMEA0110(APARTMENT_ID);
        assertThat(progressRecordInfoForMEA0110.getLblCount()).isEqualTo(CommonConstants.BLANK);
        assertThat(progressRecordInfoForMEA0110.getLblNoInfoMessage()).isEqualTo("??????????????????????????????????????????");
        assertThat(progressRecordInfoForMEA0110.getProgressRecordList()).isEqualTo(new ArrayList<>());
    }
    
    /**
     * ??????ID:MEA0110/?????????????????????ID:UT- MEA0110-002/??????:N/??????????????????:Test Get Progress Record Info For MEA0110 When Progress Record Accept User List Is Not Empty And Authority Modify Flag Is Authority Correction And Type Code Is Notification Acceptance
     */
    @Test
    public void testGetProgressRecordInfoForMEA0110WhenProgressRecordAcceptUserListIsNotEmptyAndAuthorityModifyFlagIsAuthorityCorrectionAndTypeCodeIsNotificationAcceptance() {
        List<ProgressRecordAcceptUserVo> progressRecordAcceptUserVo = generateProgressRecordAcceptUserVo();
        
        if (!progressRecordAcceptUserVo.isEmpty()) {
            progressRecordAcceptUserVo.get(0).setAuthorityModifyFlag(CodeUtil.getValue(CommonConstants.CD039, CommonConstants.CD039_AUTHORITY_CORRECTION));
            progressRecordAcceptUserVo.get(0).setTypeCode(CodeUtil.getValue(CommonConstants.CD033, CommonConstants.CD033_NOTIFICATION_ACCEPTANCE));
        }
        Mockito.when(tbl300BDAO.getProgressRecordAcceptUser(Mockito.anyString(), Mockito.any())).thenReturn(progressRecordAcceptUserVo);
        
        ProgressRecorInfoWrapperVo progressRecordInfoForMEA0110 = progressRecordLogicImpl.getProgressRecordInfoForMEA0110(APARTMENT_ID);
        assertThat(progressRecordInfoForMEA0110.getLblCount()).isEqualTo(formatLblTotal(progressRecordAcceptUserVo.size()));
        assertThat(progressRecordInfoForMEA0110.getLblNoInfoMessage()).isEqualTo(null);
        assertThat(progressRecordInfoForMEA0110.getProgressRecordList()).isNotEmpty();
        for (int i = 0; i < progressRecordAcceptUserVo.size(); i++) {
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getLblCorrespondDate()).isEqualTo(DateTimeUtil.convertReFormatDate(progressRecordAcceptUserVo.get(i).getCorrespondDate()));
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getLblProgressRecordDetail()).isEqualTo("??????????????????????????????????????????<br/>?????????????????????????????????");
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getLblProgressRecordType()).isEqualTo("????????????");
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getRelatedNumber()).isEqualTo("1000000197");
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getReportName()).isEqualTo("RP020");
        }
    }
    
    /**
     * ??????ID:MEA0110/?????????????????????ID:UT- MEA0110-003/??????:N/??????????????????:Test Get Progress Record Info For MEA0110 When Progress Record Accept User List Is Not Empty And Authority Modify Flag Is Authority Correction And Type Code Is Change Notification Acceptance
     */
    @Test
    public void testGetProgressRecordInfoForMEA0110WhenProgressRecordAcceptUserListIsNotEmptyAndAuthorityModifyFlagIsAuthorityCorrectionAndTypeCodeIsChangeNotificationAcceptance() {
        List<ProgressRecordAcceptUserVo> progressRecordAcceptUserVo = generateProgressRecordAcceptUserVo();
        
        if (!progressRecordAcceptUserVo.isEmpty()) {
            progressRecordAcceptUserVo.get(0).setAuthorityModifyFlag(CodeUtil.getValue(CommonConstants.CD039, CommonConstants.CD039_AUTHORITY_CORRECTION));
            progressRecordAcceptUserVo.get(0).setTypeCode(CodeUtil.getValue(CommonConstants.CD033, CommonConstants.CD033_CHANGE_NOTIFICATION_ACCEPTANCE));
            progressRecordAcceptUserVo.get(0).setTypeCode(CodeUtil.getValue(CommonConstants.CD033, CommonConstants.CD033_CHANGE_NOTIFICATION_ACCEPTANCE));
        }
        Mockito.when(tbl300BDAO.getProgressRecordAcceptUser(Mockito.anyString(), Mockito.any())).thenReturn(progressRecordAcceptUserVo);
        
        ProgressRecorInfoWrapperVo progressRecordInfoForMEA0110 = progressRecordLogicImpl.getProgressRecordInfoForMEA0110(APARTMENT_ID);
        
        assertThat(progressRecordInfoForMEA0110.getLblCount()).isEqualTo(formatLblTotal(progressRecordAcceptUserVo.size()));
        assertThat(progressRecordInfoForMEA0110.getLblNoInfoMessage()).isEqualTo(null);
        assertThat(progressRecordInfoForMEA0110.getProgressRecordList()).isNotEmpty();
        for (int i = 0; i < progressRecordAcceptUserVo.size(); i++) {
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getLblCorrespondDate()).isEqualTo(DateTimeUtil.convertReFormatDate(progressRecordAcceptUserVo.get(i).getCorrespondDate()));
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getLblProgressRecordDetail()).isEqualTo("??????????????????????????????????????????<br/>?????????????????????????????????");
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getLblProgressRecordType()).isEqualTo("??????????????????");
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getRelatedNumber()).isEqualTo("1000000197");
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getReportName()).isEqualTo("RP020");
        }
    }

    /**
     * ??????ID:MEA0110/?????????????????????ID:UT- MEA0110-004/??????:N/??????????????????:Test Get Progress Record Info For MEA0110 When Progress Record Accept User List Is Not Empty And Type Code Is Noti Receipt Email Noti
     */
    @Test
    public void testGetProgressRecordInfoForMEA0110WhenProgressRecordAcceptUserListIsNotEmptyAndTypeCodeIsNotiReceiptEmailNoti() {
        List<ProgressRecordAcceptUserVo> progressRecordAcceptUserVo = generateProgressRecordAcceptUserVo();
        
        if (!progressRecordAcceptUserVo.isEmpty()) {
            progressRecordAcceptUserVo.get(0).setTypeCode(CodeUtil.getValue(CommonConstants.CD033, CommonConstants.CD033_NOTI_RECEIPT_EMAIL_NOTI));
        }
        Mockito.when(tbl300BDAO.getProgressRecordAcceptUser(Mockito.anyString(), Mockito.any())).thenReturn(progressRecordAcceptUserVo);
        
        ProgressRecorInfoWrapperVo progressRecordInfoForMEA0110 = progressRecordLogicImpl.getProgressRecordInfoForMEA0110(APARTMENT_ID);
        assertThat(progressRecordInfoForMEA0110.getLblCount()).isEqualTo(formatLblTotal(progressRecordAcceptUserVo.size()));
        assertThat(progressRecordInfoForMEA0110.getLblNoInfoMessage()).isEqualTo(null);
        assertThat(progressRecordInfoForMEA0110.getProgressRecordList()).isNotEmpty();
        for (int i = 0; i < progressRecordAcceptUserVo.size(); i++) {
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getLblCorrespondDate()).isEqualTo(DateTimeUtil.convertReFormatDate(progressRecordAcceptUserVo.get(i).getCorrespondDate()));
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getLblProgressRecordDetail()).isEqualTo("???????????????????????????????????????????????????");
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getLblProgressRecordType()).isEqualTo("???????????????????????????");
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getRelatedNumber()).isEqualTo("1000000197");
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getReportName()).isEqualTo("RP020");
        }
    }
    
    /**
     * ??????ID:MEA0110/?????????????????????ID:UT- MEA0110-005/??????:N/??????????????????:Test Get Progress Record Info For MEA0110 When Progress Record Accept User List Is Not Empty And Type Code Is Change Noti Email Noti
     */
    @Test
    public void testGetProgressRecordInfoForMEA0110WhenProgressRecordAcceptUserListIsNotEmptyAndTypeCodeIsChangeNotiEmailNoti() {
        List<ProgressRecordAcceptUserVo> progressRecordAcceptUserVo = generateProgressRecordAcceptUserVo();
        
        if (!progressRecordAcceptUserVo.isEmpty()) {
            progressRecordAcceptUserVo.get(0).setTypeCode(CodeUtil.getValue(CommonConstants.CD033, CommonConstants.CD033_CHANGE_NOTI_EMAIL_NOTI));
        }
        Mockito.when(tbl300BDAO.getProgressRecordAcceptUser(Mockito.anyString(), Mockito.any())).thenReturn(progressRecordAcceptUserVo);
        
        ProgressRecorInfoWrapperVo progressRecordInfoForMEA0110 = progressRecordLogicImpl.getProgressRecordInfoForMEA0110(APARTMENT_ID);
        assertThat(progressRecordInfoForMEA0110.getLblCount()).isEqualTo(formatLblTotal(progressRecordAcceptUserVo.size()));
        assertThat(progressRecordInfoForMEA0110.getLblNoInfoMessage()).isEqualTo(null);
        assertThat(progressRecordInfoForMEA0110.getProgressRecordList()).isNotEmpty();
        for (int i = 0; i < progressRecordAcceptUserVo.size(); i++) {
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getLblCorrespondDate()).isEqualTo(DateTimeUtil.convertReFormatDate(progressRecordAcceptUserVo.get(i).getCorrespondDate()));
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getLblProgressRecordDetail()).isEqualTo("?????????????????????????????????????????????????????????");
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getLblProgressRecordType()).isEqualTo("?????????????????????????????????");
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getRelatedNumber()).isEqualTo("1000000197");
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getReportName()).isEqualTo("RP020");
        }
    }
    
    /**
     * ??????ID:MEA0110/?????????????????????ID:UT- MEA0110-006/??????:N/??????????????????:Test Get Progress Record Info For MEA0110 When Progress Record Accept User List Is Not Empty And Type Code Is Advice Email Notification
     */
    @Test
    public void testGetProgressRecordInfoForMEA0110WhenProgressRecordAcceptUserListIsNotEmptyAndTypeCodeIsAdviceEmailNotification() {
        List<ProgressRecordAcceptUserVo> progressRecordAcceptUserVo = generateProgressRecordAcceptUserVo();
        
        if (!progressRecordAcceptUserVo.isEmpty()) {
            progressRecordAcceptUserVo.get(0).setTypeCode(CodeUtil.getValue(CommonConstants.CD033, CommonConstants.CD033_ADVICE_EMAIL_NOTIFICATION));
        }
        Mockito.when(tbl300BDAO.getProgressRecordAcceptUser(Mockito.anyString(), Mockito.any())).thenReturn(progressRecordAcceptUserVo);
        
        ProgressRecorInfoWrapperVo progressRecordInfoForMEA0110 = progressRecordLogicImpl.getProgressRecordInfoForMEA0110(APARTMENT_ID);
        assertThat(progressRecordInfoForMEA0110.getLblCount()).isEqualTo(formatLblTotal(progressRecordAcceptUserVo.size()));
        assertThat(progressRecordInfoForMEA0110.getLblNoInfoMessage()).isEqualTo(null);
        assertThat(progressRecordInfoForMEA0110.getProgressRecordList()).isNotEmpty();
        for (int i = 0; i < progressRecordAcceptUserVo.size(); i++) {
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getLblCorrespondDate()).isEqualTo(DateTimeUtil.convertReFormatDate(progressRecordAcceptUserVo.get(i).getCorrespondDate()));
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getLblProgressRecordDetail()).isEqualTo("?????????????????????????????????????????????");
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getLblProgressRecordType()).isEqualTo("?????????????????????");
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getRelatedNumber()).isEqualTo("1000000197");
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getReportName()).isEqualTo("RP030");
        }
    }
    
    /**
     * ??????ID:MEA0110/?????????????????????ID:UT- MEA0110-007/??????:N/??????????????????:Test Get Progress Record Info For MEA0110 When Progress Record Accept User List Is Not Empty And Type Code Is Fieldsurveymailnotification
     */
    @Test
    public void testGetProgressRecordInfoForMEA0110WhenProgressRecordAcceptUserListIsNotEmptyAndTypeCodeIsFieldsurveymailnotification() {
        List<ProgressRecordAcceptUserVo> progressRecordAcceptUserVo = generateProgressRecordAcceptUserVo();
        
        if (!progressRecordAcceptUserVo.isEmpty()) {
            progressRecordAcceptUserVo.get(0).setTypeCode(CodeUtil.getValue(CommonConstants.CD033, CommonConstants.CD033_FIELDSURVEYMAILNOTIFICATION));
        }
        Mockito.when(tbl300BDAO.getProgressRecordAcceptUser(Mockito.anyString(), Mockito.any())).thenReturn(progressRecordAcceptUserVo);
        
        ProgressRecorInfoWrapperVo progressRecordInfoForMEA0110 = progressRecordLogicImpl.getProgressRecordInfoForMEA0110(APARTMENT_ID);
        assertThat(progressRecordInfoForMEA0110.getLblCount()).isEqualTo(formatLblTotal(progressRecordAcceptUserVo.size()));
        assertThat(progressRecordInfoForMEA0110.getLblNoInfoMessage()).isEqualTo(null);
        assertThat(progressRecordInfoForMEA0110.getProgressRecordList()).isNotEmpty();
        for (int i = 0; i < progressRecordAcceptUserVo.size(); i++) {
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getLblCorrespondDate()).isEqualTo(DateTimeUtil.convertReFormatDate(progressRecordAcceptUserVo.get(i).getCorrespondDate()));
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getLblProgressRecordDetail()).isEqualTo("???????????????????????????????????????????????????");
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getLblProgressRecordType()).isEqualTo("???????????????????????????");
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getRelatedNumber()).isEqualTo("1000000197");
            assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getReportName()).isEqualTo("RP040");
        }
    }
    
    /**
     * ??????ID:MEA0110/?????????????????????ID:UT- MEA0110-008/??????:N/??????????????????:Test Get Progress Record Info For MEA0110 When Progress Record Accept User List Is Not Empty And Type Code Is Other Case
     */
    @Test
    public void testGetProgressRecordInfoForMEA0110WhenProgressRecordAcceptUserListIsNotEmptyAndTypeCodeIsOtherCase() {
        List<ProgressRecordAcceptUserVo> progressRecordAcceptUserVo = generateProgressRecordAcceptUserVo();
        
        if (!progressRecordAcceptUserVo.isEmpty()) {
            progressRecordAcceptUserVo.get(0).setTypeCode("8");
        }
        Mockito.when(tbl300BDAO.getProgressRecordAcceptUser(Mockito.anyString(), Mockito.any())).thenReturn(progressRecordAcceptUserVo);
        
      ProgressRecorInfoWrapperVo progressRecordInfoForMEA0110 = progressRecordLogicImpl.getProgressRecordInfoForMEA0110(APARTMENT_ID);
      assertThat(progressRecordInfoForMEA0110.getLblCount()).isEqualTo(formatLblTotal(progressRecordAcceptUserVo.size()));
      assertThat(progressRecordInfoForMEA0110.getLblNoInfoMessage()).isEqualTo(null);
      assertThat(progressRecordInfoForMEA0110.getProgressRecordList()).isNotEmpty();
      for (int i = 0; i < progressRecordAcceptUserVo.size(); i++) {
          assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getLblCorrespondDate()).isEqualTo(DateTimeUtil.convertReFormatDate(progressRecordAcceptUserVo.get(i).getCorrespondDate()));
          assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getLblProgressRecordDetail()).isEqualTo("??????????????????????????????????????????????????????");
          assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getLblProgressRecordType()).isEqualTo("?????????????????????????????????");
          assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getRelatedNumber()).isEqualTo("1000000197");
          assertThat(progressRecordInfoForMEA0110.getProgressRecordList().get(i).getReportName()).isEqualTo("RP060");
      }
    }
    /* End test getProgressRecordInfoForMEA0110 */
    
    private List<ProgressRecordAcceptUserVo> generateProgressRecordAcceptUserVo() {
        
        List<ProgressRecordAcceptUserVo> voes = new ArrayList<ProgressRecordAcceptUserVo>();
        
        ProgressRecordAcceptUserVo vo = new ProgressRecordAcceptUserVo();
        vo.setAuthorityModifyFlag(AUTHORITY_MODIFY_FLAG);
        vo.setCorrespondDate(CORRESPOND_DATE);
        vo.setModifyDetails(MODIFY_DETAILS);
        vo.setNotificationMethodCode(NOTIFICATION_METHOD_CODE);
        vo.setRelatedNumber(RELATED_NUMBER);
        vo.setTypeCode(TYPE_CODE);
        vo.setUserType(USER_TYPE);
        voes.add(vo);

        return voes;
    }
    
    private String formatLblTotal(Integer total) {
        StringBuilder sb = new StringBuilder(CommonConstants.BLANK);
        sb.append(CommonConstants.STR_TOTAL);
        sb.append(CommonConstants.SPACE_HALF_SIZE);
        sb.append(total);
        sb.append(CommonConstants.SPACE_HALF_SIZE);
        sb.append(CommonConstants.STR_CASE);
        return sb.toString();
    }
}
