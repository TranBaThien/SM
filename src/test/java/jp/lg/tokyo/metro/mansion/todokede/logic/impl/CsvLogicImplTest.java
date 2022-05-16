/*
 * @(#) CsvLogicImplTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author pdquang
 * Create Date: 2019-12-26
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.anything;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.SystemException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import jp.lg.tokyo.metro.mansion.todokede.config.CodeUtilConfig;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL200DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL240DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL300DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM001DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL300Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.logic.IMansionInfoLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.IMunicipalMasterInfoLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.IProgressRecordLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.MansionInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.MunicipalMasterInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ProgressRecordDetailsVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.StatusInformationVo;

/**
 * @author pdquang
 *
 */
@RunWith(SpringRunner.class)
@Import(value = {CodeUtilConfig.class})
public class CsvLogicImplTest {

    @Mock
    private TBL300DAO tbl300DAO;
    
    @Mock
    private TBL100DAO tbl100DAO;
    
    @Mock
    private TBL200DAO tbl200DAO;
    
    @Mock
    private TBL240DAO tbl240DAO;

    @Mock
    private TBM001DAO tbm001DAO;
    
    @Mock
    private IProgressRecordLogic progressRecordLogic;

    @Mock
    private IMansionInfoLogic mansionInfoLogic;

    @Mock
    IMunicipalMasterInfoLogic municipalMasterInfoLogic;

    @InjectMocks
    private CsvLogicImpl csvlogicImpl;
    
    private final String[] LIST_PROGRESS_RECORD_NO = {"1000000001", "1000000002"};
    private final String APARTMENT_ID = "1000000001";
    private final String CITY_CODE = "130001";
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
    private final static String PROGRESS_RECORD_DETAIL = "test for progress record detail";
    private final static String PROGRESS_RECORD_DETAIL_2 = "test record 2 for progress record detail";
    
    @Test(expected = BusinessException.class)
    public void testExportCsvProgressRecord_WhenListTbl300IsNull() throws BusinessException, IOException, SystemException {        
        MansionInfoVo mansionInfoVo = null;
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        when(mockResponse.getOutputStream()).thenReturn(mockOutput);
        
        List<TBL300Entity> listTbl300 = getListProgressRecordByProgressRecordNos();
        Mockito.when(tbl300DAO.getListProgressRecordByProgressRecordNos(Mockito.anyList())).thenReturn(null);
        Mockito.when(mansionInfoLogic.getMansionInformationById(Mockito.anyString())).thenReturn(null);       
        csvlogicImpl.exportCsvProgressRecord(Arrays.asList(LIST_PROGRESS_RECORD_NO), mockOutput);
    }
    
    @Test(expected = BusinessException.class)
    public void testExportCsvProgressRecord_WhenMansionInfoVoIsNull() throws BusinessException, IOException, SystemException {        
        MansionInfoVo mansionInfoVo = null;
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        when(mockResponse.getOutputStream()).thenReturn(mockOutput);
        
        List<TBL300Entity> listTbl300 = getListProgressRecordByProgressRecordNos();
        Mockito.when(tbl300DAO.getListProgressRecordByProgressRecordNos(Arrays.asList(LIST_PROGRESS_RECORD_NO))).thenReturn(listTbl300);
        Mockito.when(mansionInfoLogic.getMansionInformationById(Mockito.anyString())).thenReturn(null);       
        csvlogicImpl.exportCsvProgressRecord(Arrays.asList(LIST_PROGRESS_RECORD_NO), mockOutput);
    }
    @Test(expected = BusinessException.class)
    public void testExportCsvProgressRecord_WhenStatusInfoVoIsNull() throws BusinessException, IOException, SystemException {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        when(mockResponse.getOutputStream()).thenReturn(mockOutput);
        
        List<TBL300Entity> listTbl300 = getListProgressRecordByProgressRecordNos();
        StatusInformationVo statusInfoVo = null;
        MansionInfoVo mansionInfoVo = new MansionInfoVo();
        Mockito.when(tbl300DAO.getListProgressRecordByProgressRecordNos(Arrays.asList(LIST_PROGRESS_RECORD_NO))).thenReturn(listTbl300);
        Mockito.when(progressRecordLogic.getStatusInformation(APARTMENT_ID)).thenReturn(statusInfoVo);
        Mockito.when(mansionInfoLogic.getMansionInformationById(APARTMENT_ID)).thenReturn(mansionInfoVo);
        csvlogicImpl.exportCsvProgressRecord(Arrays.asList(LIST_PROGRESS_RECORD_NO), mockOutput);
    }
    
    @Test
    public void testExportCsvProgressRecord_WhenStatusInfoVoIsNotNullAndMansionInfoVoIsNotNull() throws BusinessException, IOException, SystemException {
       // String OUTPUT_FILE = "C:\\Users\\pdquang\\Desktop\\test.txt";
        OutputStream outputStream = new OutputStream() {
            private StringBuilder string = new StringBuilder();
            @Override
            public void write(int b) throws IOException {
                this.string.append((char) b );
            }

        };
//        OutputStream outputStream  = new FileOutputStream(OUTPUT_FILE);
        
        List<TBL300Entity> listTbl300 = getListProgressRecordByProgressRecordNos();
        StatusInformationVo statusInfoVo = getStatusInfoVo();
        MansionInfoVo mansionInfoVo = getMansionInformationById();
        MunicipalMasterInfoVo municipalMasterInfoVo = new MunicipalMasterInfoVo();
        municipalMasterInfoVo.setCityName("東京都");

        Mockito.when(tbl300DAO.getListProgressRecordByProgressRecordNos(Arrays.asList(LIST_PROGRESS_RECORD_NO))).thenReturn(listTbl300);
        Mockito.when(progressRecordLogic.getStatusInformation(APARTMENT_ID)).thenReturn(statusInfoVo);
        Mockito.when(mansionInfoLogic.getMansionInformationById(APARTMENT_ID)).thenReturn(mansionInfoVo);
        Mockito.when(municipalMasterInfoLogic.getMunicipalMasterInfo(CITY_CODE)).thenReturn(municipalMasterInfoVo);
        csvlogicImpl.exportCsvProgressRecord(Arrays.asList(LIST_PROGRESS_RECORD_NO), outputStream);
        // assertThat(outputStream.toString()).isEqualTo(results());
        // byte[] type = outputStream.toByteArray();
    }
    
    private List<TBL300Entity> getListProgressRecordByProgressRecordNos() {

        List<TBL300Entity> listVo = new ArrayList<TBL300Entity>();
        TBL300Entity entityTbl300 = new TBL300Entity();
        entityTbl300.setApartmentId(APARTMENT_ID);
        entityTbl300.setCorrespondDate(CORRESPOND_DATE);
        entityTbl300.setTypeCode(TYPE_CODE);
        entityTbl300.setCorrespondTypeCode(CORRESPOND_TYPE_CODE);
        entityTbl300.setProgressRecordOverview(PROGRESS_RECORD_OVERVIEW);
        entityTbl300.setProgressRecordDetail(PROGRESS_RECORD_DETAIL);

        TBL300Entity entityTbl300A = new TBL300Entity();
        entityTbl300A.setApartmentId(APARTMENT_ID);
        entityTbl300A.setCorrespondDate(CORRESPOND_DATE_2);
        entityTbl300A.setTypeCode(TYPE_CODE_2);
        entityTbl300A.setCorrespondTypeCode(CORRESPOND_TYPE_CODE_2);
        entityTbl300A.setProgressRecordOverview(PROGRESS_RECORD_OVERVIEW_2);
        entityTbl300A.setProgressRecordDetail(PROGRESS_RECORD_DETAIL_2);

        listVo.add(entityTbl300);
        listVo.add(entityTbl300A);

        return listVo;
    }

    private StatusInformationVo getStatusInfoVo() {
        StatusInformationVo statusInfoVo = new StatusInformationVo();
        statusInfoVo.setAcceptedStatus("未受理");
        statusInfoVo.setUrgeStatus("未通知");
        statusInfoVo.setSupportCode("対象外");
        return statusInfoVo;
    }
    
    private MansionInfoVo getMansionInformationById() {
        MansionInfoVo mansionInformation = new MansionInfoVo();
        mansionInformation.setApartmentName("Araptment Name");
        mansionInformation.setApartmentNamePhonetic("Araptment Name Phonetic");
        mansionInformation.setZipCode("192-0032");
        mansionInformation.setCityCode(CITY_CODE);
        mansionInformation.setAddress("Tokyo");
        return mansionInformation;
    }
    
    private static String results() {
        StringBuilder result = new StringBuilder(); 
        result.append("マンション名,マンション名フリガナ,住所,届出状況,受理状況,督促通知状況,都支援対象\n");
        result.append("Araptment Name,Araptment Name Phonetic,192-0032　東京都Tokyo,未受理,未通知,対象外\n");
        result.append("対応日時,種別,経過記録概要,経過記録詳細\n");
        result.append("201912121010,届出,Abcde,test for progress record detail\n");
        result.append("201911111010,電話,ABCDE,test record 2 for progress record detail");
        return result.toString();
    } 
    
}
