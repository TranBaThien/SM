package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.AccountLockFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.LoginStatusFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserAvailabilityFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SequenceUtil;
import jp.lg.tokyo.metro.mansion.todokede.config.CodeUtilConfig;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100BDAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM001DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM001Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.SearchInformationMansionForm;
import jp.lg.tokyo.metro.mansion.todokede.security.UserPrincipal;
import jp.lg.tokyo.metro.mansion.todokede.vo.OutputCsvInformationSearchVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.OutputCsvSuperivseVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.PagingVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ResultSearchVo;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@Import(value = CodeUtilConfig.class)
public class SearchInformationMansionLogicImplTest {

    /* Create TBL120Entity */
    private static final String USER_ID_TBL120 = "10000001";
    private static final String LOGIN_ID_TBL120 = "G0000001";
    private static final LocalDateTime ACCOUNT_LOCK_TIME_TBL120 = LocalDateTime.now();
    private static final String BIGINING_PASSWORD_CHANGE_FLAG_TBL120 = "0";
    private static final String CITY_CODE_TBL120 = "111111";
    private static final String DELETE_FLAG_TBL120 = "0";
    private static final LocalDateTime LAST_TIME_LOGIN_TIME_TBL120 = LocalDateTime.now();
    private static final int LOGIN_ERROR_COUNT_TBL120 = 0;
    private static final String MAIL_ADDRESS_TBL120 = "abc@gmail.com";
    private static final String PASSWORD_TBL120 = "password_hash";
    private static final LocalDateTime PASSWORD_PERIOD_TBL120 = LocalDateTime.now().plus(365, ChronoUnit.DAYS);
    private static final String STOP_REASON_TBL120 = "stop reason";
    private static final LocalDateTime STOP_TIME_TBL120 = LocalDateTime.now();
    private static final String TEL_NO_TBL120 = "09999999";
    private static final LocalDateTime UPDATE_DATETIME_TBL120 = LocalDateTime.now();
    private static final String UPDATE_USER_ID_TBL120 = "G0000001";
    private static final String USER_NAME_TBL120 = "username";
    private static final String USER_NAME_PHONETIC_TBL120 = "username phonetic";
    private static final String USER_TYPE_TBL120 = "2";
    private final String APARTMENT_ID = "1000000001";
    private final String APARTMENT_NAME = "Da Nang";

    private final String CITY_CODE = "130001";
    private final String ADDRESS = "HCM";
    private final String NEWEST_NOTIFICATION_NO = "2000000000";
    private final String CITY_NAME = null;
    private final String ACCEPT_STATUS = "1";
    private final String ACCEPT_STATUS_VO = "未受理";
    private final String NOTIFICATION_DATE = "20191102";

    //FORM
    private final String TXT_APARTMENT_ID = "1000000001";

    private final String RDO_NOTIFICATION_STATUS = "1";
    private final String NEXT_NOTIFICATION_DATE = "20191230";

    //    //OutputCSVSuperviseVO
    private final String OUTPUT_APARTMENT_ID = "1000000001";
    private final String OUTPUTAPARTMENTNAME = "マンション１";
    private final String SUPERVISENOTICETYPE = "2";
    private final String APPENDIXNO = "第1";
    private final String DOCUMENTNO = "2019文京区";
    private final String NOTICEDATE = "20200102";
    private final String RECIPIENTNAMEAPARTMENT = "TOKYO";
    private final String RECIPIENTNAMEUSER = "されます。";
    private final String SENDER_SUPERVISE = "文京区長　成澤　廣修";
    private final String EVIDENCEBAR = "1";
    private final String EVIDENCENO = "5";
    private final String PERIODEVIDENCE = "5";
    private final String LASTNOTICEDATE = "20191230";
    private final String LASTDOCUMENTNO = "2019住住マ　第　号号";
    private final String TEXTMAILING2 = "されます。";
    private final String ADDRESS_SUPERVISE = "";
    private final String APARTMENTNAME = "マンション１";
    private final String NOTIFICATIONFORMATNAME = "1";
    private final String NOTIFICATIONTIMELIMIT = "20200930";
    private final String CONTACT_SUPERVISE = "されます。";
    //OutputCSVInformation
    private final String CSVINFO_APARTMENTID = "1000000001";
    private final String CSVINFO_APARTMENTNAME = "マンション１";
    private final String CSVINFO_APARTMENTNAMEPHONETIC = "ヤッタ";
    private final String CSVINFO_ZIPCODE = "141-0022";
    private final String CSVINFO_CITY = "品川区";
    private final String CSVINFO_ADDRESS = "東五反田";
    private final String CSVINFO_NOTIFICATIONDATE = "20200202";
    private final String CSVINFO_NOTIFICATIONSTATUS = "20201111";
    private final String CSVINFO_ACCEPTSTATUS = "1";
    private final String CSVINFO_BUILDDAY = null;
    private final String CSVINFO_BUILDYEAR = null;
    private final String CSVINFO_BUILDMONTH = null;
    private final String CSVINFO_GROUPFORM = "1";
    private final String CSVINFO_GROUPFORMELSE = null;
    private final String CSVINFO_LANDRIGHTS = "8";
    private final String CSVINFO_LANDRIGHTELSE = "土地の権利_その他1001";
    private final String CSVINFO_USEFOR = "8";
    private final String CSVINFO_USEFORELSE = "併設用途_その他1001";
    @Mock
    HttpServletResponse response;
    @Mock
    TBL100BDAO tbl100BDAO;

    @Mock
    TBM001DAO tbm001DAO;
    @Mock
    TBL100DAO tbl100DAO;

    SimpleDateFormat myformatTime = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    @Mock
    HttpServletResponse httpServletResponse;
    @InjectMocks
    private SearchInformationMansionLogicImpl searchInformationMansionLogicImpl;
    @Mock
    private HttpSession session;
    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private SequenceUtil sequenceUtil;

    private TBL120Entity generateEntityTbl120(String accountLockFlag, String accountAvailableFlag,
                                              String loginStatusFlag) {
        TBL120Entity entity = new TBL120Entity();
        entity.setUserId(USER_ID_TBL120);
        entity.setLoginId(LOGIN_ID_TBL120);
        entity.setAccountLockFlag(accountLockFlag);
        entity.setLoginStatusFlag(loginStatusFlag);
        entity.setAvailability(accountAvailableFlag);
        entity.setUserType(String.valueOf(UserTypeCode.CITY.getCode()));
        entity.setPassword(PASSWORD_TBL120);
        entity.setUserType(USER_TYPE_TBL120);
        entity.setUserName(USER_NAME_TBL120);
        entity.setUserNamePhonetic(USER_NAME_PHONETIC_TBL120);
        entity.setAccountLockTime(ACCOUNT_LOCK_TIME_TBL120);
        entity.setLoginErrorCount(LOGIN_ERROR_COUNT_TBL120);
        entity.setCityCode(CITY_CODE_TBL120);
        entity.setDeleteFlag(DELETE_FLAG_TBL120);
        entity.setPasswordPeriod(PASSWORD_PERIOD_TBL120);
        entity.setMailAddress(MAIL_ADDRESS_TBL120);
        entity.setTelNo(TEL_NO_TBL120);
        entity.setBiginingPasswordChangeFlag(BIGINING_PASSWORD_CHANGE_FLAG_TBL120);
        entity.setStopReason(STOP_REASON_TBL120);
        entity.setStopTime(STOP_TIME_TBL120);
        entity.setLastTimeLoginTime(LAST_TIME_LOGIN_TIME_TBL120);
        entity.setUpdateUserId(UPDATE_USER_ID_TBL120);
        entity.setUpdateDatetime(UPDATE_DATETIME_TBL120);
        return entity;
    }


    private List<ResultSearchVo> generateSearchVoList() {

        int i;

        List<ResultSearchVo> list = new ArrayList<ResultSearchVo>();

        for (i = 0; i < 50; i++) {
            ResultSearchVo vo = new ResultSearchVo();

            vo.setLblApartmentId(APARTMENT_ID);
            vo.setLnkApartmentName(APARTMENT_NAME);
            vo.setLblNotificationStatus(NEXT_NOTIFICATION_DATE);
            vo.setLblNotificationDate(NOTIFICATION_DATE);
            vo.setLblAddress(ADDRESS);
            vo.setLblAcceptStatus(ACCEPT_STATUS);
            vo.setNewestNotificationNo(NEWEST_NOTIFICATION_NO);
            vo.setCityCode(CITY_CODE);
            vo.setCityName(CITY_NAME);
            vo.setUserLogin(String.valueOf(UserTypeCode.CITY.getCode()));

            list.add(vo);
        }

        return list;

    }

    private List<OutputCsvSuperivseVo> generateOutputSuperviseVo() {

        int i;

        List<OutputCsvSuperivseVo> list = new ArrayList<OutputCsvSuperivseVo>();

        for (i = 0; i < 4; i++) {
            OutputCsvSuperivseVo vo = new OutputCsvSuperivseVo();
            vo.setOutputApartmentID(OUTPUT_APARTMENT_ID);
            vo.setOutputApartmentName(OUTPUTAPARTMENTNAME);
            vo.setSuperviseNoticeType(SUPERVISENOTICETYPE);
            vo.setAppendixNo(APPENDIXNO);
            vo.setDocumentNo(DOCUMENTNO);
            vo.setNoticeDate(NOTICEDATE);
            vo.setRecipientNameApartment(RECIPIENTNAMEAPARTMENT);
            vo.setRecipientNameUser(RECIPIENTNAMEUSER);
            vo.setSender(SENDER_SUPERVISE);
            vo.setTextMailing1(ADDRESS_SUPERVISE);
            vo.setEvidenceBar(EVIDENCEBAR);
            vo.setEvidenceNo(EVIDENCENO);
            vo.setPeriodEvidence(PERIODEVIDENCE);
            vo.setLastNoticeDate(LASTNOTICEDATE);
            vo.setLastDocumentNo(LASTDOCUMENTNO);
            vo.setTextMailing2(TEXTMAILING2);
            vo.setApartmentName(APARTMENTNAME);
            vo.setNotificationFormatName(NOTIFICATIONFORMATNAME);
            vo.setNotificationTimeLimit(NOTIFICATIONTIMELIMIT);
            vo.setContact(CONTACT_SUPERVISE);

            list.add(vo);
        }

        return list;
    }

    private List<OutputCsvInformationSearchVo> generateCSVInformationVo() {

        int i;

        List<OutputCsvInformationSearchVo> list = new ArrayList<OutputCsvInformationSearchVo>();

        for (i = 0; i < 1; i++) {
            OutputCsvInformationSearchVo vo = new OutputCsvInformationSearchVo();
            vo.setApartmentId(CSVINFO_APARTMENTID);
            vo.setApartmentName(CSVINFO_APARTMENTNAME);
            vo.setApartmentNamePhonetic(CSVINFO_APARTMENTNAMEPHONETIC);
            vo.setZipCode(CSVINFO_ZIPCODE);
            vo.setCity(CSVINFO_CITY);
            vo.setAddress(CSVINFO_ADDRESS);
            vo.setNotificationDate(CSVINFO_NOTIFICATIONDATE);
            vo.setNotificationStatus(CSVINFO_NOTIFICATIONSTATUS);
            vo.setAcceptStatus(CSVINFO_ACCEPTSTATUS);
            vo.setBuildDay(CSVINFO_BUILDDAY);
            vo.setBuildMonth(CSVINFO_BUILDMONTH);
            vo.setBuildYear(CSVINFO_BUILDYEAR);
            vo.setGroupForm(CSVINFO_GROUPFORM);
            vo.setGroupFormElse(CSVINFO_GROUPFORMELSE);
            vo.setLandRights(CSVINFO_LANDRIGHTS);
            vo.setLandRightElse(CSVINFO_LANDRIGHTELSE);
            vo.setUsefor(CSVINFO_USEFOR);
            vo.setUseForElse(CSVINFO_USEFORELSE);

            list.add(vo);
        }

        return list;
    }


    private ResultSearchVo generateSearchVo() throws ParseException {
        ResultSearchVo vo = new ResultSearchVo();
        vo.setLblApartmentId(APARTMENT_ID);
        vo.setLnkApartmentName(APARTMENT_NAME);
        vo.setLblNotificationStatus(RDO_NOTIFICATION_STATUS);
        vo.setLblNotificationDate(myformatTime.format(yyyyMMdd.parse(NOTIFICATION_DATE)));
        vo.setLblAddress(ADDRESS);
        vo.setLblAcceptStatus(ACCEPT_STATUS_VO);
        vo.setNewestNotificationNo(NEWEST_NOTIFICATION_NO);
        vo.setCityCode(CITY_CODE);
        vo.setCityName(CITY_NAME);
        vo.setUserLogin(String.valueOf(UserTypeCode.CITY.getCode()));
        return vo;
    }

    @Before
    public void init() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(requestAttributes);
        Map<String, String> systemSettings = new HashMap<>();
        systemSettings.put("GJA0110_LIST_DISPLAY_MAX", String.valueOf(2000));
        Mockito.when(session.getAttribute(CommonConstants.SYSTEM_SETTING)).thenReturn(systemSettings);
        Mockito.when(session.getAttributeNames()).thenReturn(Collections.emptyEnumeration());
        Mockito.when(httpServletRequest.getSession()).thenReturn(session);
    }

    private UserDetails prepareSecurityContextHolder(TBL120Entity entity) {
        UserPrincipal userDetails = UserPrincipal.create(entity, true);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return userDetails;
    }

    private void setUpforUserAndDisplayMax() {
        TBL120Entity tbl120entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(tbl120entity);
        TBM001Entity tbm001Entity = new TBM001Entity();
        tbm001Entity.setDocumentNo("住住マ文書番号");
        tbm001Entity.setCityName("青梅市");
        tbm001Entity.setChiefName("132055");
        Mockito.when(tbm001DAO.getMunicipalMasterInfo(Mockito.any())).thenReturn(tbm001Entity);
    }

    @Test
    public void GetListMansionInformation_WhenAllConditionsAreMet_ThenReturnPageImpl() throws ParseException {
        setUpforUserAndDisplayMax();
        List<ResultSearchVo> list = generateSearchVoList();
        SearchInformationMansionForm form = new SearchInformationMansionForm();
        form.setTxtApartmentId(TXT_APARTMENT_ID);
        form.setChkApartmentLost(true);
        Pageable pageable = PageRequest.of(0, 50);
        ResultSearchVo vo = generateSearchVo();
        Page<ResultSearchVo> pageVo = new PageImpl<ResultSearchVo>(list, pageable, 50);
        Mockito.when(tbl100BDAO.searchInformationMansionPaging(Mockito.any(), Mockito.any())).thenReturn(pageVo);
        PagingVo<ResultSearchVo> page = searchInformationMansionLogicImpl.getListInformationMansion(form);
        for (ResultSearchVo svo : page.getPages().getContent()) {
            assertThat(svo.getLblApartmentId()).isEqualTo(vo.getLblApartmentId());
            assertThat(svo.getLnkApartmentName()).isEqualTo(vo.getLnkApartmentName());
            assertThat(svo.getCityCode()).isEqualTo(vo.getCityCode());
            assertThat(svo.getLblNotificationDate()).isEqualTo(vo.getLblNotificationDate());
            assertThat(svo.getLblAcceptStatus()).isEqualTo(vo.getLblAcceptStatus());
            assertThat(svo.getLblAddress()).isEqualTo(vo.getCityCode() + "-" + vo.getLblAddress() + "-" + vo.getCityName());
        }
    }


    //
    @Test
    public void GetListMansionInformation_WhenAllConditionsAreMet_FormNull() throws ParseException {
        setUpforUserAndDisplayMax();

        List<ResultSearchVo> list = generateSearchVoList();
        SearchInformationMansionForm form = new SearchInformationMansionForm();
        Pageable pageable = PageRequest.of(0, 50);
        ResultSearchVo vo = generateSearchVo();


        Page<ResultSearchVo> pageVo = new PageImpl<ResultSearchVo>(list, pageable, 50);
        Mockito.when(tbl100BDAO.searchInformationMansionPaging(Mockito.any(), Mockito.any())).thenReturn(pageVo);

        PagingVo<ResultSearchVo> actual = searchInformationMansionLogicImpl.getListInformationMansion(form);
        assertThat(actual.getTotalPage()).isEqualTo(1);
    }

    @Test
    public void GetListMansionInformation_WhenAllConditionsAreMet_ThenReturnListAparmentId() throws ParseException {
        setUpforUserAndDisplayMax();

        List<ResultSearchVo> list = generateSearchVoList();
        SearchInformationMansionForm form = new SearchInformationMansionForm();
        form.setTxtApartmentId(TXT_APARTMENT_ID);
        form.setChkApartmentLost(true);
        Pageable pageable = PageRequest.of(0, 50);
        Page<ResultSearchVo> pageVo = new PageImpl<ResultSearchVo>(list, pageable, 50);
        Mockito.when(tbl100BDAO.searchInformationMansionPaging(Mockito.any(), Mockito.any())).thenReturn(pageVo);

        PagingVo<ResultSearchVo> page = searchInformationMansionLogicImpl.getListInformationMansion(form);
        assertThat(page.getPages().getTotalElements()).isEqualTo(50);
    }
    @Test
    public void GetListMansionInformation_WhenAllConditionsAreMet_ReturnNotificationDateIsInValid() throws ParseException {
        setUpforUserAndDisplayMax();

        List<ResultSearchVo> list = generateSearchVoList();
        list.stream().forEach(elt -> elt.setLblNotificationDate("Invalid"));
        SearchInformationMansionForm form = new SearchInformationMansionForm();
        form.setTxtApartmentId(TXT_APARTMENT_ID);
        form.setChkApartmentLost(true);
        Pageable pageable = PageRequest.of(0, 50);
        Page<ResultSearchVo> pageVo = new PageImpl<ResultSearchVo>(list, pageable, 50);
        Mockito.when(tbl100BDAO.searchInformationMansionPaging(Mockito.any(), Mockito.any())).thenReturn(pageVo);

        PagingVo<ResultSearchVo> page = searchInformationMansionLogicImpl.getListInformationMansion(form);
        assertThat(page.getPages().getTotalElements()).isEqualTo(50);
    }

    @Test
    public void GetListMansionInformation_WhenAllConditions_AndHaveSortParameter() throws ParseException {
        setUpforUserAndDisplayMax();
        List<ResultSearchVo> list = generateSearchVoList();
        SearchInformationMansionForm form = new SearchInformationMansionForm();
        form.setTxtApartmentId(TXT_APARTMENT_ID);
        form.setChkApartmentLost(true);
        Pageable pageable = PageRequest.of(0, 50);
        Page<ResultSearchVo> pageVo = new PageImpl<ResultSearchVo>(list, pageable, 50);
        Mockito.when(tbl100BDAO.searchInformationMansionPaging(Mockito.any(), Mockito.any())).thenReturn(pageVo);

        ResultSearchVo vo = generateSearchVo();
        PagingVo<ResultSearchVo> page = searchInformationMansionLogicImpl.getListInformationMansion(form);
        assertThat(page.getPages().getTotalElements()).isEqualTo(50);
        for (ResultSearchVo svo : page.getPages().getContent()) {
            assertThat(svo.getLblApartmentId()).isEqualTo(vo.getLblApartmentId());
            assertThat(svo.getLnkApartmentName()).isEqualTo(vo.getLnkApartmentName());
            assertThat(svo.getCityCode()).isEqualTo(vo.getCityCode());
            assertThat(svo.getLblNotificationDate()).isEqualTo(vo.getLblNotificationDate());
            assertThat(svo.getLblAcceptStatus()).isEqualTo(vo.getLblAcceptStatus());
            assertThat(svo.getLblAddress()).isEqualTo(vo.getCityCode() + "-" + vo.getLblAddress() + "-" + vo.getCityName());
        }
    }

    //
    @Test
    public void GetNewestNotification_WhenAllConditions_AndReturnTrue() throws ParseException, BusinessException {
        setUpforUserAndDisplayMax();

        String newestNotification = NEWEST_NOTIFICATION_NO;
        String aparmentId = APARTMENT_ID;

        List<ResultSearchVo> list = generateSearchVoList();
        SearchInformationMansionForm form = new SearchInformationMansionForm();
        form.setTxtApartmentId(TXT_APARTMENT_ID);
        form.setChkApartmentLost(true);
        String sortParameter = "届出日（昇順）";

        List<String> apartmentIds = new ArrayList<>();
        List<String> newestNotifications = new ArrayList<>();
        MultiValuedMap<String, String> mapNewestNotificationNo = new HashSetValuedHashMap<>();


        Pageable pageable = PageRequest.of(0, 50);
        Page<ResultSearchVo> pageVo = new PageImpl<ResultSearchVo>(list, pageable, 50);
        Mockito.when(tbl100BDAO.searchInformationMansionPaging(Mockito.any(), Mockito.any())).thenReturn(pageVo);

        Mockito.when(tbl100DAO.getNewestNotificationById(Mockito.anyString())).thenReturn(newestNotification);
        PagingVo<ResultSearchVo> page = searchInformationMansionLogicImpl.getListInformationMansion(form);
        for (ResultSearchVo resultSearchVo : page.getPages().getContent()) {
            apartmentIds.add(resultSearchVo.getLblApartmentId());
            newestNotifications.add(resultSearchVo.getNewestNotificationNo());
        }
        for (int i = 0; i < apartmentIds.size(); i++) {
            mapNewestNotificationNo.put(apartmentIds.get(i), newestNotifications.get(i));
        }


        boolean checkNewestNotification = searchInformationMansionLogicImpl.checkNewestNotification(aparmentId, mapNewestNotificationNo);
        assertThat(checkNewestNotification).isTrue();
    }

    //
    @Test
    public void GetListMansionInformation_WhenAllConditions_AndLblAcceptStatus() throws ParseException {
        setUpforUserAndDisplayMax();


        List<ResultSearchVo> list = generateSearchVoList();
        list.stream().forEach(elt -> elt.setLblAcceptStatus(""));
        SearchInformationMansionForm form = new SearchInformationMansionForm();
        form.setTxtApartmentId(TXT_APARTMENT_ID);
        form.setChkApartmentLost(true);
        Pageable pageable = PageRequest.of(0, 50);
        Page<ResultSearchVo> pageVo = new PageImpl<ResultSearchVo>(list, pageable, 50);
        Mockito.when(tbl100BDAO.searchInformationMansionPaging(Mockito.any(), Mockito.any())).thenReturn(pageVo);

        ResultSearchVo vo = generateSearchVo();
        PagingVo<ResultSearchVo> page = searchInformationMansionLogicImpl.getListInformationMansion(form);
        assertThat(page.getPages().getTotalElements()).isEqualTo(50);
        for (ResultSearchVo svo : page.getPages().getContent()) {
            assertThat(svo.getLblAcceptStatus()).isEqualTo("-");
        }
    }

    @Test
    public void GetListMansionInformation_WhenAllConditions_AndReturn() throws ParseException {
        setUpforUserAndDisplayMax();
        List<ResultSearchVo> list = generateSearchVoList();
        list.stream().forEach(elt -> elt.setLblAcceptStatus(""));
        SearchInformationMansionForm form = new SearchInformationMansionForm();
        form.setTxtApartmentId(TXT_APARTMENT_ID);
        form.setChkApartmentLost(true);
        Pageable pageable = PageRequest.of(0, 50);
        Page<ResultSearchVo> pageVo = new PageImpl<ResultSearchVo>(list, pageable, 50);
        Mockito.when(tbl100BDAO.searchInformationMansionPaging(Mockito.any(), Mockito.any())).thenReturn(pageVo);

        ResultSearchVo vo = generateSearchVo();
        PagingVo<ResultSearchVo> page = searchInformationMansionLogicImpl.getListInformationMansion(form);
        assertThat(page.getPages().getTotalElements()).isEqualTo(50);
        for (ResultSearchVo svo : page.getPages().getContent()) {
            assertThat(svo.getLblAcceptStatus()).isEqualTo("-");
        }
    }


    @Test
    public void OutputCSVSupervise_WhenListApartmentNotNull() throws BusinessException, IOException {
        OutputStream outputStream = new OutputStream() {
            private StringBuilder string = new StringBuilder();

            @Override
            public void write(int b) throws IOException {
                this.string.append((char) b);
            }
        };
        List<OutputCsvSuperivseVo> outputCsvSuperivses = generateOutputSuperviseVo();

        Mockito.when(tbl100BDAO.getMansionAndWriteCsv(Mockito.any())).thenReturn(outputCsvSuperivses);
        String[] apartmentIds = {"1000000001", "1000000002", "1000000003", "1000000004"};
        searchInformationMansionLogicImpl.outputCsv(apartmentIds, outputStream);
    }

    @Test
    public void OutputCSVSupervise_WhenListApartmentNotNull_EvidenceBarIsTwo() throws BusinessException, IOException {
        OutputStream outputStream = new OutputStream() {
            private StringBuilder string = new StringBuilder();

            @Override
            public void write(int b) throws IOException {
                this.string.append((char) b);
            }
        };
        List<OutputCsvSuperivseVo> outputCsvSuperivses = generateOutputSuperviseVo();
        outputCsvSuperivses.stream().forEach(elt -> elt.setEvidenceBar("2"));
        Mockito.when(tbl100BDAO.getMansionAndWriteCsv(Mockito.any())).thenReturn(outputCsvSuperivses);
        String[] apartmentIds = {"1000000001", "1000000002", "1000000003", "1000000004"};
        searchInformationMansionLogicImpl.outputCsv(apartmentIds, outputStream);
    }

    @Test
    public void OutputCSVMansionInformation_WhenListApartmentNotNull() throws BusinessException, IOException {
        OutputStream outputStream = new OutputStream() {
            private StringBuilder string = new StringBuilder();

            @Override
            public void write(int b) {
                this.string.append((char) b);
            }
        };
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(mockOutput);
        List<OutputCsvInformationSearchVo> outputCsvInformationSearchVos = generateCSVInformationVo();
        Mockito.when(tbl100BDAO.getInformationSearchWriteCSV(Mockito.any())).thenReturn(outputCsvInformationSearchVos);
        String[] apartmentIds = {"1000000001"};
        searchInformationMansionLogicImpl.outputCsvInformationMansion(apartmentIds, outputStream);
    }

    @Test
    public void OutputCSVMansionInformation_WhenListApartmentNotNull_NotificationDateIsBefore() throws BusinessException, IOException {
        OutputStream outputStream = new OutputStream() {
            private StringBuilder string = new StringBuilder();

            @Override
            public void write(int b) {
                this.string.append((char) b);
            }
        };
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(mockOutput);
        List<OutputCsvInformationSearchVo> outputCsvInformationSearchVos = generateCSVInformationVo();
        outputCsvInformationSearchVos.stream().forEach(elt -> elt.setNotificationDate("20190202"));
        Mockito.when(tbl100BDAO.getInformationSearchWriteCSV(Mockito.any())).thenReturn(outputCsvInformationSearchVos);
        String[] apartmentIds = {"1000000001"};
        searchInformationMansionLogicImpl.outputCsvInformationMansion(apartmentIds, outputStream);
    }

    @Test
    public void OutputCSVMansionInformation_WhenListApartmentNotNull_NotificationDateIsNull() throws BusinessException, IOException {
        OutputStream outputStream = new OutputStream() {
            private StringBuilder string = new StringBuilder();

            @Override
            public void write(int b) {
                this.string.append((char) b);
            }
        };
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(mockOutput);
        List<OutputCsvInformationSearchVo> outputCsvInformationSearchVos = generateCSVInformationVo();
        outputCsvInformationSearchVos.stream().forEach(elt -> elt.setNotificationDate(null));
        Mockito.when(tbl100BDAO.getInformationSearchWriteCSV(Mockito.any())).thenReturn(outputCsvInformationSearchVos);
        String[] apartmentIds = {"1000000001", "1000000002", "1000000003", "1000000004"};
        searchInformationMansionLogicImpl.outputCsvInformationMansion(apartmentIds, outputStream);
    }

    @Test
    public void OutputCSVMansionInformation_WhenListApartmentNotNull_GroupFormIs() throws BusinessException, IOException {
        OutputStream outputStream = new OutputStream() {
            private StringBuilder string = new StringBuilder();

            @Override
            public void write(int b) {
                this.string.append((char) b);
            }
        };
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(mockOutput);
        List<OutputCsvInformationSearchVo> outputCsvInformationSearchVos = generateCSVInformationVo();
        outputCsvInformationSearchVos.stream().forEach(elt -> elt.setGroupForm("8"));
        Mockito.when(tbl100BDAO.getInformationSearchWriteCSV(Mockito.any())).thenReturn(outputCsvInformationSearchVos);
        String[] apartmentIds = {"1000000001"};
        searchInformationMansionLogicImpl.outputCsvInformationMansion(apartmentIds, outputStream);
    }

    @Test
    public void OutputCSVMansionInformation_WhenListApartmentNotNull_GroupFormIsTwo() throws BusinessException, IOException {
        OutputStream outputStream = new OutputStream() {
            private StringBuilder string = new StringBuilder();

            @Override
            public void write(int b) {
                this.string.append((char) b);
            }
        };
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(mockOutput);
        List<OutputCsvInformationSearchVo> outputCsvInformationSearchVos = generateCSVInformationVo();
        outputCsvInformationSearchVos.stream().forEach(elt -> elt.setGroupForm("2"));
        Mockito.when(tbl100BDAO.getInformationSearchWriteCSV(Mockito.any())).thenReturn(outputCsvInformationSearchVos);
        String[] apartmentIds = {"1000000001"};
        searchInformationMansionLogicImpl.outputCsvInformationMansion(apartmentIds, outputStream);
    }

    @Test
    public void OutputCSVMansionInformation_WhenListApartmentNotNull_LandRightsNotEigth() throws BusinessException, IOException {
        OutputStream outputStream = new OutputStream() {
            private StringBuilder string = new StringBuilder();

            @Override
            public void write(int b) {
                this.string.append((char) b);
            }
        };
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(mockOutput);
        List<OutputCsvInformationSearchVo> outputCsvInformationSearchVos = generateCSVInformationVo();
        outputCsvInformationSearchVos.stream().forEach(elt -> elt.setLandRights("1"));
        Mockito.when(tbl100BDAO.getInformationSearchWriteCSV(Mockito.any())).thenReturn(outputCsvInformationSearchVos);
        String[] apartmentIds = {"1000000001"};
        searchInformationMansionLogicImpl.outputCsvInformationMansion(apartmentIds, outputStream);
    }


    @Test
    public void OutputCSVMansionInformation_WhenListApartmentNotNull_UseForIsOne() throws BusinessException, IOException {
        OutputStream outputStream = new OutputStream() {
            private StringBuilder string = new StringBuilder();

            @Override
            public void write(int b) {
                this.string.append((char) b);
            }
        };
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(mockOutput);
        List<OutputCsvInformationSearchVo> outputCsvInformationSearchVos = generateCSVInformationVo();
        outputCsvInformationSearchVos.stream().forEach(elt -> elt.setUsefor("1"));
        Mockito.when(tbl100BDAO.getInformationSearchWriteCSV(Mockito.any())).thenReturn(outputCsvInformationSearchVos);
        String[] apartmentIds = {"1000000001"};
        searchInformationMansionLogicImpl.outputCsvInformationMansion(apartmentIds, outputStream);
    }

    @Test
    public void OutputCSVMansionInformation_WhenListApartmentNotNull_UseForIsTwo() throws BusinessException, IOException {
        OutputStream outputStream = new OutputStream() {
            private StringBuilder string = new StringBuilder();

            @Override
            public void write(int b) {
                this.string.append((char) b);
            }
        };
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(mockOutput);
        List<OutputCsvInformationSearchVo> outputCsvInformationSearchVos = generateCSVInformationVo();
        outputCsvInformationSearchVos.stream().forEach(elt -> elt.setUsefor("2"));
        Mockito.when(tbl100BDAO.getInformationSearchWriteCSV(Mockito.any())).thenReturn(outputCsvInformationSearchVos);
        String[] apartmentIds = {"1000000001"};
        searchInformationMansionLogicImpl.outputCsvInformationMansion(apartmentIds, outputStream);
    }

    @Test
    public void OutputCSVMansionInformation_WhenListApartmentNotNull_UseForIsThree() throws BusinessException, IOException {
        OutputStream outputStream = new OutputStream() {
            private StringBuilder string = new StringBuilder();

            @Override
            public void write(int b) {
                this.string.append((char) b);
            }
        };
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(mockOutput);
        List<OutputCsvInformationSearchVo> outputCsvInformationSearchVos = generateCSVInformationVo();
        outputCsvInformationSearchVos.stream().forEach(elt -> elt.setUsefor("3"));
        Mockito.when(tbl100BDAO.getInformationSearchWriteCSV(Mockito.any())).thenReturn(outputCsvInformationSearchVos);
        String[] apartmentIds = {"1000000001"};
        searchInformationMansionLogicImpl.outputCsvInformationMansion(apartmentIds, outputStream);
    }

}
