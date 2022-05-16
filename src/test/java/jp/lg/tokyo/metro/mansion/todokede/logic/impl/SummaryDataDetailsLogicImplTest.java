/*
 * @(#) SummaryDataDetailsLogicImplTest.java
 *
 * Copyright(C) 2020 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nvlong1
 * Create Date: Feb 3, 2020
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jp.lg.tokyo.metro.mansion.todokede.LogAppender;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.PageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SequenceUtil;
import jp.lg.tokyo.metro.mansion.todokede.config.CodeUtilConfig;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100BDAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM004Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.SummaryDataPagingForm;
import jp.lg.tokyo.metro.mansion.todokede.vo.OutputCsvSummaryDataDetailsVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.PagingVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.SummaryDataDetailsVo;

@RunWith(SpringRunner.class)
@Import(CodeUtilConfig.class)
public class SummaryDataDetailsLogicImplTest {

    @Mock
    private SequenceUtil sequenceUtil;

    @Mock
    private TBL100BDAO tbl100BDAO;

    @InjectMocks
    private SummaryDataDetailsLogicImpl summaryDataDetailsLogicImpl;

    protected MockHttpSession session;

    protected MockHttpServletRequest request;

    private final String GKA0120_LIST_DISPLAY_MAX = "50";
    private final String FAILED_HANDLER_PATH = "jp.lg.tokyo.metro.mansion.todokede.logic.impl.SummaryDataDetailsLogicImpl";
    
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        session = new MockHttpSession();
        session.setAttribute(CommonConstants.SYSTEM_SETTING, generateSettingMap());
        request = new MockHttpServletRequest();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    private Map<String, String> generateSettingMap() {
        return generateTBM004EntityList().stream().collect(Collectors.toMap(TBM004Entity::getSetTargetNameEng, TBM004Entity::getSetPoint));
    }

    private List<TBM004Entity> generateTBM004EntityList() {
        List<TBM004Entity> settingList = new ArrayList<TBM004Entity>();
        settingList.add(generateTBM004Entity("1", CommonConstants.GKA0120_LIST_DISPLAY_MAX, GKA0120_LIST_DISPLAY_MAX));
        return settingList;
    }

    private TBM004Entity generateTBM004Entity(String setNo, String setTargetNameEng, String setPoint) {
        TBM004Entity entity = new TBM004Entity();
        entity.setSetNo(setNo);
        entity.setSetTargetNameEng(setTargetNameEng);
        entity.setSetPoint(setPoint);
        return entity;
    }
    
    private String[] apartmentIds = new String[] { "1000000001", "1000000002", "1000000003" };
    private Integer page = null;

    private SummaryDataPagingForm getSummaryDataPagingForm() {
        SummaryDataPagingForm dataPagingForm = new SummaryDataPagingForm();
        dataPagingForm.setApartmentIds(apartmentIds);
        dataPagingForm.setPage(page);
        return dataPagingForm;
    }
    
    private List<SummaryDataDetailsVo> generateSummaryDataDetailsVoList() {
        int i;
        List<SummaryDataDetailsVo> list = new ArrayList<SummaryDataDetailsVo>();
        for (i = 0; i < 3; i++) {
            SummaryDataDetailsVo vo = new SummaryDataDetailsVo();
            vo.setApartmentId(apartmentIds[i]);
            vo.setAddress("１－１－１　１１ビル１－１－１　１１ビル");
            vo.setCityName("千代田区");
            vo.setLblApartmentName("千代田区マンション１千代田区マンション１千代田区マンション１千代田区マンション１千代田区マンション１");
            list.add(vo);
        }
        return list;
    }
    
    private List<OutputCsvSummaryDataDetailsVo> generateOutputCsvSummaryDataDetailsVo() {

        int i;

        List<OutputCsvSummaryDataDetailsVo> list = new ArrayList<OutputCsvSummaryDataDetailsVo>();

        for (i = 0; i < 3; i++) {
            OutputCsvSummaryDataDetailsVo vo = new OutputCsvSummaryDataDetailsVo();
            vo.setApartmentId(apartmentIds[i]);
            vo.setApartmentName("千代田区マンション２千代田区マンション２千代田区マンション２千代田区マンション２千代田区マンション２");
            vo.setNewestNotificationNo("1000000039");
            vo.setApartmentNamePhonetic("チヨダクマンションニイチヨダクマンションニイチヨダクマンションニイチヨダクマンションニイチヨダクマンションニイチヨダクマンションニイチヨダクマンションニイチヨダクマンションニイチヨダクマンションニイチ");
            vo.setZipCode("102-0073");
            vo.setCityCode("千代田区");
            vo.setAddress("１－１－２　１１ビル１－１－２　１１ビル１－１－２　１１ビル１－１－２　１１ビル１－１－２　１１ビル１－１－２　１１ビル１－１－２　１１ビル１－１－２　１１ビル１－１－２　１１ビル１－１－２　１１ビル");
            vo.setAcceptＳtatus("未受理");
            vo.setNotificationType("更新");
            vo.setNotificationDate("2020年03月03日");
            vo.setNotificationGroupName("管理組合名");
            vo.setNotificationPersonName("届出者氏名");
            vo.setChangeCount("1");
            vo.setChangeReason("変更");
            vo.setLostElseReason(null);
            vo.setLostElseReasonElse(null);
            vo.setGroupYesno("回答しない");
            vo.setApartmentNumber("0");
            vo.setGroupForm(CommonConstants.BLANK);
            vo.setGroupFormElse(null);
            vo.setHouseNumber("101");
            vo.setFloorNumber("10");
            vo.setBuiltDate("2000年03月11日");
            vo.setLandRights("回答しない");
            vo.setLandRightsElse(null);
            vo.setUsefor("回答しない");
            vo.setUseforElse(null);
            vo.setManagementForm("全部委託");
            vo.setManagementFormElse(null);
            vo.setManagerName("管理業者名");
            vo.setManagerNamePhonetic("フリガ");
            vo.setManagerZipCode(null);
            vo.setManagerAddress("東京都文京区白山");
            vo.setManagerTelNo("0957-433-321");
            vo.setGroup("ある");
            vo.setManager("いる");
            vo.setRule("ない");
            vo.setRuleChangeYear(null);
            vo.setOpenOneyearOver("ない");
            vo.setMinutes(null);
            vo.setManagementCost(null);
            vo.setRepairCost("ない");
            vo.setRepairMonthlyCost("0");
            vo.setRepairPlan("ない");
            vo.setRepairNearestYear(null);
            vo.setLongRepairPlan("ない");
            vo.setLongRepairPlanYear(null);
            vo.setLongRepairPlanPeriod("0");
            vo.setLongRepairPlanYearFrom(null);
            vo.setLongRepairPlanYearTo(null);
            vo.setArrearageRule("回答しない");
            vo.setSegment("回答しない");
            vo.setEmptyPercent("～10％");
            vo.setEmptyNumber("0");
            vo.setRentalPercent("～20％");
            vo.setRentalNumber("0");
            vo.setSeismicDiagnosis("回答しない");
            vo.setEarthquakeResistance("回答しない");
            vo.setSeismicRetrofit("回答しない");
            vo.setDesignDocument("回答しない");
            vo.setRepairHistory("回答しない");
            vo.setVoluntaryOrganization("回答しない");
            vo.setDisasterPreventionManual("回答しない");
            vo.setDisasterPreventionStockpile("回答しない");
            vo.setNeedSupportList("回答しない");
            vo.setDisasterPreventionRegular("回答しない");
            vo.setSlope("回答しない");
            vo.setRailing("回答しない");
            vo.setElevator("回答しない");
            vo.setLed("回答しない");
            vo.setHeatShielding("回答しない");
            vo.setEquipmentCharge("回答しない");
            vo.setCommunity("回答しない");
            vo.setContactProperty("マンション管理業者");
            vo.setContactPropertyElse(null);
            vo.setContactZip("112-0001");
            vo.setContactAddress("東京都文京区白山");
            vo.setContactTelNo("0511-234-232");
            vo.setContactName("氏名");
            vo.setContactNamePhonetic("フリガナ");
            vo.setContactMailAddress("long@test.com");
            vo.setOptional(CommonConstants.BLANK);
            list.add(vo);
        }
        return list;
    }
    
    /**
     * 案件ID:GKA0120/チェックリストID:UT- GKA0120-001/区分:I/チェック内容:Test Interface Get Summary Data Details
     */
    @Test
    public void testInterfaceGetSummaryDataDetails() {
        List<SummaryDataDetailsVo> list = generateSummaryDataDetailsVoList();
        Pageable pageable = PageRequest.of(0, 50);
        Page<SummaryDataDetailsVo> pageVo = new PageImpl<SummaryDataDetailsVo>(list, pageable, 3);
        Mockito.when(tbl100BDAO.getSummaryDataDetails(Mockito.any(), Mockito.any())).thenReturn(pageVo);
        
        PagingVo<SummaryDataDetailsVo> summaryDataDetails = summaryDataDetailsLogicImpl.getSummaryDataDetails(getSummaryDataPagingForm());
        
        for (int i = 0; i < apartmentIds.length; i++) {
            assertThat(summaryDataDetails.getPages().getContent().get(i).getApartmentId()).isEqualTo(getSummaryDataPagingForm().getApartmentIds()[i]);
        }
    }

    /**
     * 案件ID:GKA0120/チェックリストID:UT- GKA0120-002/区分:N/チェック内容:Test Get Summary Data Details Is Success
     */
    @Test
    public void testInterfaceGetSummaryDataDetailsIsSuccess() {
        LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
        List<SummaryDataDetailsVo> list = generateSummaryDataDetailsVoList();
        Pageable pageable = PageRequest.of(0, 50);
        Page<SummaryDataDetailsVo> pageVo = new PageImpl<SummaryDataDetailsVo>(list, pageable, 3);
        Mockito.when(tbl100BDAO.getSummaryDataDetails(Mockito.any(), Mockito.any())).thenReturn(pageVo);
        
        PagingVo<SummaryDataDetailsVo> pageSummaryDataDetails = new PagingVo<>(pageVo, PageUtil.getStartIndex(pageVo), PageUtil.getLastIndex(pageVo), PageUtil.getTotalPage(pageVo));
        
        PagingVo<SummaryDataDetailsVo> summaryDataDetails = summaryDataDetailsLogicImpl.getSummaryDataDetails(getSummaryDataPagingForm());
        assertThat(summaryDataDetails.getLast()).isEqualTo(pageSummaryDataDetails.getLast());
        assertThat(summaryDataDetails.getPages()).isEqualTo(pageSummaryDataDetails.getPages());
        assertThat(summaryDataDetails.getStart()).isEqualTo(pageSummaryDataDetails.getStart());
        assertThat(summaryDataDetails.getTotalPage()).isEqualTo(pageSummaryDataDetails.getTotalPage());
        assertThat(summaryDataDetails.getClass()).isEqualTo(pageSummaryDataDetails.getClass());
        assertThat(logAppender.getEvents().get(0).getMessage()).isEqualTo("プログラムを開始しました。モジュール名：getSummaryDataDetails");
        assertThat(logAppender.getEvents().get(1).getMessage()).isEqualTo("プログラムを終了しました。モジュール名：getSummaryDataDetails");
    }
    
    
    /**
     * 案件ID:GKA0120/チェックリストID:UT- GKA0120-003/区分:N/チェック内容:Test Output Csv Summary Data Details When List Apartment Not Null And Get Summary Data Details Write CSV Not Null
     * @throws BusinessException
     * @throws IOException
     */
    @Test
    public void testOutputCsvSummaryDataDetails_WhenListApartmentNotNullAndGetSummaryDataDetailsWriteCSVNotNull() throws BusinessException, IOException {
        LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
        OutputStream outputStream = new OutputStream() {
            private StringBuilder string = new StringBuilder();

            @Override
            public void write(int b) throws IOException {
                this.string.append((char) b);
            }
        };
        List<OutputCsvSummaryDataDetailsVo> csvSummaryDataDetailsVos = generateOutputCsvSummaryDataDetailsVo();
        Mockito.when(tbl100BDAO.getSummaryDataDetailsWriteCSV(Mockito.any())).thenReturn(csvSummaryDataDetailsVos);
        summaryDataDetailsLogicImpl.outputCsv(getSummaryDataPagingForm().getApartmentIds(), outputStream);
        assertThat(logAppender.getEvents().get(0).getMessage()).isEqualTo("プログラムを開始しました。モジュール名：outputCsv");
        assertThat(logAppender.getEvents().get(1).getMessage()).isEqualTo("プログラムを終了しました。モジュール名：outputCsv");
    }
    
    /**
     * 案件ID:GKA0120/チェックリストID:UT- GKA0120-004/区分:N/チェック内容:Test Output Csv Summary Data Details When List Apartment Not Null And Get Summary Data Details Write CSV Null
     * @throws BusinessException
     * @throws IOException
     */
    @Test
    public void testOutputCsvSummaryDataDetails_WhenListApartmentNotNullAndGetSummaryDataDetailsWriteCSVNull() throws BusinessException, IOException {
        LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
        OutputStream outputStream = new OutputStream() {
            private StringBuilder string = new StringBuilder();
            
            @Override
            public void write(int b) throws IOException {
                this.string.append((char) b);
            }
        };
        Mockito.when(tbl100BDAO.getSummaryDataDetailsWriteCSV(Mockito.any())).thenReturn(null);
        summaryDataDetailsLogicImpl.outputCsv(getSummaryDataPagingForm().getApartmentIds(), outputStream);
        assertThat(logAppender.getEvents().get(0).getMessage()).isEqualTo("プログラムを開始しました。モジュール名：outputCsv");
        assertThat(logAppender.getEvents().get(1).getMessage()).isEqualTo("プログラムを終了しました。モジュール名：outputCsv");
    }
}