/*
 * @(#) GJA0110Controller.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.action;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.SystemException;
import javax.validation.Valid;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.AllowCity;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.SubmitFormFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.PageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SecurityUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.SearchInformationMansionForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.ICityLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.ISearchInformationMansionLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.CodeVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.PagingVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ResultSearchVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.SearchInformationMansionVo;

@Controller
@AllowCity
public class GJA0110Controller extends BaseMansionController {

    private static final String CD059 = "CD059";

    private static final String CD059_NO_SPECIFIC = "指定しない";
    private static final String STATUS_NOTIFIED = "届出済";
    //GJA0110 - Label Field
    private static final String LBL_CAL_NOTIFICATION_DATE_FROM = "届出日－範囲開始";
    private static final String LBL_CAL_NOTIFICATION_DATE_TO = "届出日－範囲終了";
    private static final String LBL_CAL_BUILD_DATE_FROM = "新築年月日－範囲開始";
    private static final String LBL_CAL_BUILD_DATE_TO = "新築年月日－範囲終了";
    private static final String LBL_TXT_HOUSE_NUMBER_FROM = "戸数－範囲開始";
    private static final String LBL_TXT_HOUSE_NUMBER_TO = "戸数－範囲終了";
    private static final String LBL_TXT_FLOOR_NUMBER_FROM = "階数－範囲開始";
    private static final String LBL_TXT_FLOOR_NUMBER_TO = "階数－範囲終了";

    //GJA0110 - Check Max Length
    private static final String GJA0110_SHOW = "/GJA0110/show";
    private static final String GJA0110_SEARCH = "/GJA0110/search";
    private static final String GJA0110_CSV_SUPERVISE = "/GJA0110/csvSuperVise/";
    private static final String GJA0110_CSV_INFORMATION = "/GJA0110/csvInformation/";
    private static final String GJA0110_RETURN = "/GJA0110/return";
    private static final String GJA0110_CHECK_NEWEST_NOTIFICATION = "/GJA0110/checkNewestNotification";
    private static final String GJA0110_RESET_SEARCH_CONDITION = "/GJA0110/resetSearchCondition";
    private static final Logger logger = LogManager.getLogger(GJA0110Controller.class);
    private static final String GJA0110 = "GJA0110";
    private static final String SEARCH_MANSION_MODEL_NAME = "searchInformationMansion";
    private static final String GC0120_SCREEN_ID = "GC0120";
    private static final String GBA0110_SCREEN_ID = "GBA0110";

    private static final String SEARCHNOTIFICATION = "マンション情報";

    private static final String BUTTON_ID = "buttonId";
    private static final String CITY_LIST = "listCity";
    private static final String APPLICATION_NO = "applicationNo";
    private static final String MESSAGE_ERROR = "messageError";
    private static final String PAGING_MODEL_NAME = "pagingVo";
    private static final String PAGE = "page";
    private static final String LIST_APARTMENT_ID = "listApartmentId";
    private static final String LIST_NEWEST_NOTIFICATION = "listNewestNotification";
    private static final String BUTTON_ACCEPT_NOTIFI = "acceptNotifi";
    private static final String BUTTON_ADVICE_NOTIFI = "adviceNotifi";
    private static final String BUTTON_FIELD_NOTIFI = "fieldSurvey";
    private static final String BUTTON_SEARCH_BTN = "searchBtn";
    private static final String PREVIOUS_SCREEN_GJA0110 = "previousScreenGJA0110";
    private static final String CHECKED = "checked";
    private static final String UNCHECKED = "unchecked";

    @Autowired
    protected ISearchInformationMansionLogic searchInformationMansionLogic;

    @Autowired
    private ICityLogic citylogic;

    /**
     * Show GJA0110 screen
     *
     * @param model         Model
     * @param buttonId      String
     * @param applicationNo String
     * @param session       HttpSession
     * @throws BusinessException when has error
     * @return
     */
    @PostMapping(GJA0110_SHOW)
    public String show(Model model,
                       @RequestParam(name = BUTTON_ID, required = false, defaultValue = BUTTON_ID) String buttonId,
                       @RequestParam(name = APPLICATION_NO, required = false, defaultValue = APPLICATION_NO) String applicationNo,
                       HttpSession session, HttpServletRequest request) throws BusinessException {
        try {
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));

            // Setting application no and button id from previous screen to session
            session.setAttribute(APPLICATION_NO, applicationNo);
            session.setAttribute(BUTTON_ID, buttonId);

            // Prepare show data, ex: list radio button, search condition, ...
            SearchInformationMansionVo vo = new SearchInformationMansionVo();
            prepare(vo, buttonId);

            // Copy properties vo to form
            SearchInformationMansionForm form = new SearchInformationMansionForm();
            form.setUserLogin(String.valueOf(SecurityUtil.getUserLoggedInInfo().getUserTypeCode().getCode()));

            CommonUtils.copyProperties(form, vo);

            // Execute search mansion
            searchMansion(form, null, request, model);

            model.addAttribute(APPLICATION_NO, applicationNo);
            model.addAttribute(SEARCH_MANSION_MODEL_NAME, vo);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(ex.getMessage());
        }
        return GJA0110;
    }

    /**
     * output dunning information - 督促通知情報CSVファイル出力
     *
     * @param apartmentIds String[]
     * @param request      HttpServletRequest
     * @param response     HttpServletResponse
     * @throws BusinessException when has error
     */
    @GetMapping(GJA0110_CSV_SUPERVISE)
    public void outputCsvSuperVise(@RequestParam(name = "apartmentIds") String[] apartmentIds,
                                   HttpServletRequest request, HttpServletResponse response)
            throws BusinessException {
        try {
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            response.setHeader("Content-Type", "application/octet-stream; charset=UTF-8");
            searchInformationMansionLogic.outputCsv(apartmentIds, response.getOutputStream());
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        } catch (IOException e) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, e.getMessage()));
            throw new BusinessException(e, e.getMessage());
        }
    }

    /**
     * output mansion information - マンション情報CSVファイル出力
     *
     * @param apartmentIds String[]
     * @param request      HttpServletRequest
     * @param response     HttpServletResponse
     * @throws IOException               when has error
     * @throws BusinessException         when has error
     * @throws InvocationTargetException when has error
     * @throws IllegalAccessException    when has error
     */
    @GetMapping(GJA0110_CSV_INFORMATION)
    public void outputCSVInformation(@RequestParam(name = "apartmentIds") String[] apartmentIds,
                                     HttpServletRequest request, HttpServletResponse response)
            throws BusinessException {
        try {
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            response.setHeader("Content-Type", "application/octet-stream; charset=UTF-8");
            searchInformationMansionLogic.outputCsvInformationMansion(apartmentIds, response.getOutputStream());
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        } catch (IOException e) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, e.getMessage()));
            throw new BusinessException(e, e.getMessage());
        }
    }

    /**
     * When press button search or paging
     *
     * @param form    SearchInformationMansionForm
     * @param result  BindingResult
     * @param request HttpServletRequest
     * @param model   Model
     * @return String
     * @throws SystemException   when has error
     * @throws BusinessException when has error
     * @throws ParseException    when has error
     * @throws SQLException      when has error
     */
    @PostMapping(GJA0110_SEARCH)
    public String searchMansion(@ModelAttribute(SEARCH_MANSION_MODEL_NAME) @Valid SearchInformationMansionForm form,
                                BindingResult result, HttpServletRequest request, Model model)
            throws SystemException, BusinessException, ParseException, SQLException {
        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, currentMethodName));

        HttpSession session = request.getSession();
        String applicationNo = (String) session.getAttribute(APPLICATION_NO);
        model.addAttribute(APPLICATION_NO, applicationNo);

        // set default paging and sorting
        // if page is null, set page is 1
        // if sort is null, set sort is 届出日（昇順）
        setDefaultPagingAndSorting(form);

        // format data form
        formatDataForm(session, form);

        // set search condition from session and return is paging
        setSearchConditionFromSession(session, form);

        List<String> errorMessage = new ArrayList<>();

        // Check single item and check correlation when is searching
        if (SubmitFormFlag.IS_SEARCHING.getFlag().equals(form.getAction()) && result != null) {
            // No1. チェック（単項目）
            if (result.hasErrors()) {
                CommonUtils.getErrorsFromBindingResult(result, errorMessage);
                model.addAttribute(MESSAGE_ERROR, errorMessage);
                model.addAttribute(CITY_LIST, citylogic.getListCity());
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
                return GJA0110;
            }
            // No2. チェック（相関）
            if (!checkCorrelation(form, errorMessage)) {
                model.addAttribute(MESSAGE_ERROR, errorMessage);
                model.addAttribute(CITY_LIST, citylogic.getListCity());
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
                return GJA0110;
            }
        }

        int searchMax = Integer.parseInt(SessionUtil.getSystemSettingByKey(CommonConstants.GJA0110_SEARCH_MAX));
        // No3. 検索処理
        PagingVo<ResultSearchVo> pageSearchMansion = searchInformationMansionLogic.getListInformationMansion(form);

        // なお、検索結果がセッション情報.SEARCH_MAXを超える場合、エラーメッセージ（ID：E0127、{ 0 }：セッション情報.SEARCH_MAX）をエラーメッセージエリアに表示する。
        if (pageSearchMansion.getPages().getTotalElements() > searchMax) {
            pageSearchMansion.setPages(
                    new PageImpl<ResultSearchVo>(
                            new ArrayList<ResultSearchVo>(),
                            pageSearchMansion.getPages().getPageable(),
                            pageSearchMansion.getPages().getTotalElements()));
            errorMessage.add(MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0127), searchMax));
            model.addAttribute(MESSAGE_ERROR, errorMessage);
            model.addAttribute(CITY_LIST, citylogic.getListCity());
            model.addAttribute(PAGING_MODEL_NAME, pageSearchMansion);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return GJA0110;
        }

        // 検索結果が0件の場合、一覧は表示せずにエラーメッセージ（ID：E0145、{ 0 }：マンション情報）をエラーメッセージエリアに表示する。
        if (pageSearchMansion.getPages().isEmpty()) {
            errorMessage.add(MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0145), SEARCHNOTIFICATION));
            model.addAttribute(MESSAGE_ERROR, errorMessage);
            model.addAttribute(CITY_LIST, citylogic.getListCity());
            model.addAttribute(PAGING_MODEL_NAME, pageSearchMansion);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return GJA0110;
        }

        List<String> listApartmentId = new ArrayList<>();
        List<String> listNewestNotification = new ArrayList<>();
        MultiValuedMap<String, String> mapNewestNotificationNo = new HashSetValuedHashMap<>();

        for (ResultSearchVo vo : pageSearchMansion.getPages().getContent()) {
            listApartmentId.add(vo.getLblApartmentId());
            listNewestNotification.add(vo.getNewestNotificationNo());
        }

        for (int i = 0; i < listApartmentId.size(); i++) {
            mapNewestNotificationNo.put(listApartmentId.get(i), listNewestNotification.get(i));
        }

        // No4. セッション情報保存
        // save information to session
        saveToSession(session, form, listApartmentId, mapNewestNotificationNo);

        model.addAttribute(CITY_LIST, citylogic.getListCity());
        model.addAttribute(PAGING_MODEL_NAME, pageSearchMansion);
        model.addAttribute(SEARCH_MANSION_MODEL_NAME, form);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
        return GJA0110;

    }

    /**
     * GJA0110 return event
     *
     * @param model   Model
     * @param request HttpServletRequest
     * @return String
     * @throws BusinessException when has error
     * @throws ParseException    when has error
     * @throws SQLException      when has error
     */
    @PostMapping(GJA0110_RETURN)
    public String returnFromOtherScreens(Model model, HttpServletRequest request) throws
            BusinessException, ParseException, SQLException {
        HttpSession session = request.getSession();
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        SearchInformationMansionForm form = (SearchInformationMansionForm) session.getAttribute(SEARCH_MANSION_MODEL_NAME);
        form.setPreviousScreen((String) session.getAttribute(PREVIOUS_SCREEN_GJA0110));
        MultiValuedMap<String, String> listNewestNotification = (MultiValuedMap<String, String>) session.getAttribute(LIST_NEWEST_NOTIFICATION);
        PagingVo<ResultSearchVo> pageSearchMansion = searchInformationMansionLogic.getListInformationMansion(form);
        model.addAttribute(SEARCH_MANSION_MODEL_NAME, form);
        model.addAttribute(CITY_LIST, citylogic.getListCity());
        model.addAttribute(PAGING_MODEL_NAME, pageSearchMansion);
        model.addAttribute(LIST_NEWEST_NOTIFICATION, listNewestNotification);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return GJA0110;
    }

    /**
     * Checks if is validation param.
     *
     * @param form the form
     * @return true, if is validation param
     * @throws SystemException the system exception
     */
    private boolean checkCorrelation(SearchInformationMansionForm form, List<String> errorMessage) {
        if (!isBlank(form.getCalNotificationDateFrom()) && !isBlank(form.getCalNotificationDateTo())
                && DateTimeUtil.getLocalDateFromString2(form.getCalNotificationDateFrom())
                .isAfter(DateTimeUtil.getLocalDateFromString2(form.getCalNotificationDateTo()))) {
            // 届出日－範囲開始と届出日－範囲終了が入力されている場合、かつ
            // 届出日－範囲開始＞届出日－範囲終了の場合、エラー
            errorMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0129,
                    LBL_CAL_NOTIFICATION_DATE_FROM, LBL_CAL_NOTIFICATION_DATE_TO));
            return false;
        }
        if (!isBlank(form.getCalBuiltDateFrom()) && !isBlank(form.getCalBuiltDateTo())
                && DateTimeUtil.getLocalDateFromString2(form.getCalBuiltDateFrom())
                .isAfter(DateTimeUtil.getLocalDateFromString2(form.getCalBuiltDateTo()))) {
            // 新築年月日－範囲開始と新築年月日－範囲終了が入力されている場合、かつ
            // 新築年月日－範囲開始＞新築年月日－範囲終了の場合、エラー
            errorMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0129,
                    LBL_CAL_BUILD_DATE_FROM, LBL_CAL_BUILD_DATE_TO));
            return false;
        }
        if (!isBlank(form.getTxtHouseNumberFrom()) && !isBlank(form.getTxtHouseNumberTo())
                && Integer.parseInt(form.getTxtHouseNumberFrom()) > Integer.parseInt(form.getTxtHouseNumberTo())) {
            // 戸数－範囲開始と戸数－範囲終了が入力されている場合、かつ
            // 戸数－範囲開始＞戸数－範囲終了の場合、エラー
            errorMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0129,
                    LBL_TXT_HOUSE_NUMBER_FROM, LBL_TXT_HOUSE_NUMBER_TO));
            return false;
        }
        if (!isBlank(form.getTxtFloorNumberFrom()) && !isBlank(form.getTxtFloorNumberTo())
                && Integer.parseInt(form.getTxtFloorNumberFrom()) > Integer.parseInt(form.getTxtFloorNumberTo())) {
            // 階数－範囲開始と階数－範囲終了が入力されている場合、かつ
            // 階数－範囲開始＞階数－範囲終了の場合、エラー
            errorMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0129,
                    LBL_TXT_FLOOR_NUMBER_FROM, LBL_TXT_FLOOR_NUMBER_TO));
            return false;
        }
        return true;
    }

    /**
     * API request to check is newest notification
     *
     * @param request     HttpServletRequest
     * @param apartmentId String
     * @return String
     * @throws BusinessException
     */
    @PostMapping(GJA0110_CHECK_NEWEST_NOTIFICATION)
    @ResponseBody
    public String isValid(HttpServletRequest request, String apartmentId) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        boolean checkNewest = false;
        HttpSession session = request.getSession();
        MultiValuedMap<String, String> mapNewestNotification = (MultiValuedMap<String, String>) session.getAttribute(LIST_NEWEST_NOTIFICATION);

        checkNewest = searchInformationMansionLogic.checkNewestNotification(apartmentId, mapNewestNotification);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        if (checkNewest) {
            return CHECKED;
        } else {
            return UNCHECKED;
        }
    }

    /**
     * Rest controller reset search condition
     * @param request HttpServletRequest
     * @return ResponseEntity
     * @throws BusinessException when has error
     */
    @PostMapping(GJA0110_RESET_SEARCH_CONDITION)
    @ResponseBody
    public ResponseEntity<?> resetSearchCondition(HttpServletRequest request) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        Map<String, Object> response = new HashMap<>();
        HttpSession session = request.getSession(true);
        Object objButtonId = session.getAttribute(BUTTON_ID);

        if (objButtonId instanceof String) {
            String buttonId = (String) objButtonId;
            SearchInformationMansionVo searchMansionVo = new SearchInformationMansionVo();
            prepare(searchMansionVo, buttonId);
            response.put(SEARCH_MANSION_MODEL_NAME, searchMansionVo);
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Prepare data for display
     *
     * @param vo       SearchInformationMansionVo
     * @param buttonId String
     * @throws BusinessException
     */
    private void prepare(SearchInformationMansionVo vo, String buttonId) throws BusinessException {
        try {
            List<CodeVo> acceptStatusList = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD030));
            List<CodeVo> adviceList = copyListCodeInfos(CodeUtil.getList(CD059));
            List<CodeVo> superviseList = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD047));
            List<CodeVo> notificationList = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD036));
            List<String> sortParameters =
                    Arrays.asList(
                            CommonConstants.SORT_NOTIFICATION_DATE_ASC,
                            CommonConstants.SORT_NOTIFICATION_DATE_DESC,
                            CommonConstants.SORT_APARTMENT_NAME_ASC,
                            CommonConstants.SORT_APARTMENT_NAME_DESC,
                            CommonConstants.SORT_ADDRESS_ASC,
                            CommonConstants.SORT_ADDRESS_DESC);

            vo.setChkApartmentLost(true);
            vo.setUserLogin(String.valueOf(SecurityUtil.getUserLoggedInInfo().getUserTypeCode().getCode()));

            String citycode = SecurityUtil.getUserLoggedInInfo().getCityCode();
            if (String.valueOf(UserTypeCode.CITY.getCode()).equals(vo.getUserLogin())) {
                vo.setTxtCityCode(citycode);
            }

            //Display for radio radiobox support code
            vo.setLstAcceptStatus(acceptStatusList);
            vo.setLstSortParameter(sortParameters);
            vo.setLstAdviceStatus(adviceList);
            vo.setLstSuperViseStatus(superviseList);
            vo.setLstNotificationStatus(notificationList);
            // Q&A 178。
            if (CommonConstants.SCREEN_ID_GCA0120.equals(SessionUtil.getPreviousScreenId())) {
                // ①遷移元画面が新規ユーザ登録審査(GCA0120)画面の場合
                // 新規ユーザ登録審査(GCA0120)に遷移する。
                vo.setPreviousScreen(CommonConstants.SCREEN_ID_GCA0120);
            } else {
                // ②上記以外の場合
                // メニュー（区市町村/都職員用）（GBA0110）に遷移する。
                vo.setPreviousScreen(CommonConstants.SCREEN_ID_GBA0110);
            }
            // Input before Search
            vo.setUserLogin(String.valueOf(SecurityUtil.getUserLoggedInInfo().getUserTypeCode().getCode()));
            if (String.valueOf(UserTypeCode.CITY.getCode()).equals(vo.getUserLogin())) {
                if (GBA0110_SCREEN_ID.equals(vo.getPreviousScreen())) {
                    vo.setRdoAdviceStatus(CodeUtil.getValue(CD059, CD059_NO_SPECIFIC));
                    vo.setRdoSuperviseStatus(CodeUtil.getValue(CommonConstants.CD047, CD059_NO_SPECIFIC));
                    switch (buttonId) {
                    case BUTTON_ACCEPT_NOTIFI:
                        vo.setRdoNotificationStatus(CodeUtil.getValue(CommonConstants.CD036, STATUS_NOTIFIED));
                        vo.setRdoAcceptStatus(CodeUtil.getValue(CommonConstants.CD030, CommonConstants.CD030_UNACCEPTED));

                        break;
                    case BUTTON_ADVICE_NOTIFI:
                        vo.setRdoNotificationStatus(CodeUtil.getValue(CommonConstants.CD036, STATUS_NOTIFIED));
                        vo.setRdoAcceptStatus(CodeUtil.getValue(CommonConstants.CD030, CommonConstants.CD030_ACCEPTED));
                        break;
                    default:
                        vo.setRdoNotificationStatus(CodeUtil.getValue(CommonConstants.CD036, CD059_NO_SPECIFIC));
                        vo.setRdoAcceptStatus(CodeUtil.getValue(CommonConstants.CD030, CommonConstants.CD030_NOT_SPECIFIED));
                        break;
                    }
                }
            } else if (String.valueOf(UserTypeCode.TOKYO_STAFF.getCode()).equals(vo.getUserLogin())) {
                if (GBA0110_SCREEN_ID.equals(vo.getPreviousScreen())) {
                    vo.setRdoAdviceStatus(CodeUtil.getValue(CD059, CD059_NO_SPECIFIC));
                    vo.setRdoSuperviseStatus(CodeUtil.getValue(CommonConstants.CD047, CD059_NO_SPECIFIC));
                    switch (buttonId) {
                    case BUTTON_ACCEPT_NOTIFI:
                        vo.setRdoNotificationStatus(CodeUtil.getValue(CommonConstants.CD036, STATUS_NOTIFIED));
                        vo.setRdoAcceptStatus(CodeUtil.getValue(CommonConstants.CD030, CommonConstants.CD030_UNACCEPTED));

                        break;
                    case BUTTON_ADVICE_NOTIFI:
                        vo.setRdoNotificationStatus(CodeUtil.getValue(CommonConstants.CD036, STATUS_NOTIFIED));
                        vo.setRdoAcceptStatus(CodeUtil.getValue(CommonConstants.CD030, CommonConstants.CD030_ACCEPTED));
                        break;
                    default:
                        vo.setRdoNotificationStatus(CodeUtil.getValue(CommonConstants.CD036, CD059_NO_SPECIFIC));
                        vo.setRdoAcceptStatus(CodeUtil.getValue(CommonConstants.CD030, CommonConstants.CD030_NOT_SPECIFIED));
                        break;
                    }
                }
            } else if (String.valueOf(UserTypeCode.SYSTEM_ADMIN.getCode()).equals(vo.getUserLogin())
                    || String.valueOf(UserTypeCode.MAINTENANCER.getCode()).equals(vo.getUserLogin())) {
                if (GBA0110_SCREEN_ID.equals(vo.getPreviousScreen())) {
                    vo.setRdoAdviceStatus(CodeUtil.getValue(CD059, CD059_NO_SPECIFIC));
                    vo.setRdoSuperviseStatus(CodeUtil.getValue(CommonConstants.CD047, CD059_NO_SPECIFIC));
                    switch (buttonId) {
                    case BUTTON_ACCEPT_NOTIFI:
                        vo.setRdoNotificationStatus(CodeUtil.getValue(CommonConstants.CD036, STATUS_NOTIFIED));
                        vo.setRdoAcceptStatus(CodeUtil.getValue(CommonConstants.CD030, CommonConstants.CD030_UNACCEPTED));
                        break;
                    case BUTTON_ADVICE_NOTIFI:
                        vo.setRdoNotificationStatus(CodeUtil.getValue(CommonConstants.CD036, STATUS_NOTIFIED));
                        vo.setRdoAcceptStatus(CodeUtil.getValue(CommonConstants.CD030, CommonConstants.CD030_ACCEPTED));
                        break;
                    default:
                        vo.setRdoNotificationStatus(CodeUtil.getValue(CommonConstants.CD036, CD059_NO_SPECIFIC));
                        vo.setRdoAcceptStatus(CodeUtil.getValue(CommonConstants.CD030, CommonConstants.CD030_NOT_SPECIFIED));
                        break;
                    }

                } else if (GC0120_SCREEN_ID.equals(vo.getPreviousScreen())) {
                    vo.setRdoNotificationStatus(CodeUtil.getValue(CommonConstants.CD036, CD059_NO_SPECIFIC));
                    vo.setRdoAcceptStatus(CodeUtil.getValue(CommonConstants.CD030, CommonConstants.CD030_NOT_SPECIFIED));
                    vo.setRdoAdviceStatus(CodeUtil.getValue(CD059, CD059_NO_SPECIFIC));
                    vo.setRdoSuperviseStatus(CodeUtil.getValue(CommonConstants.CD047, CD059_NO_SPECIFIC));
                }
            }

        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Save information into session
     *
     * @param session                HttpSession
     * @param form                   SearchInformationMansionForm
     * @param listApartmentId        {@link List} of {@link String}
     * @param listNewestNotification {@link Map} of {@link String}
     */
    private void saveToSession(
            HttpSession session,
            SearchInformationMansionForm form,
            List<String> listApartmentId,
            MultiValuedMap<String, String> listNewestNotification) {
        // ① 画面で入力した検索条件
        session.setAttribute(SEARCH_MANSION_MODEL_NAME, form);
        // ②表示するページ番号
        session.setAttribute(PAGE, form.getPage());
        // ③No.３検索処理で取得したマンション情報のマンションID
        session.setAttribute(LIST_APARTMENT_ID, listApartmentId);
        // ④No.３検索処理で取得したマンション情報の最新届出番号
        session.setAttribute(LIST_NEWEST_NOTIFICATION, listNewestNotification);
    }

    /**
     * Set default page is 1, and sort is 届出日（昇順）
     * @param form SearchInformationMansionForm
     */
    private void setDefaultPagingAndSorting(SearchInformationMansionForm form) {
        if (form.getPage() == null) {
            form.setPage(PageUtil.FIRST_PAGE);
        }
        if (StringUtils.isBlank(form.getSortParameter())) {
            form.setSortParameter(CommonConstants.SORT_NOTIFICATION_DATE_ASC);
        }
    }

    /**
     * Get and set search condition from session when searching, paging, sorting
     * @param session HttpSession
     * @param searchForm SearchInformationMansionForm
     * @throws SystemException when has error when copy properties
     */
    private void setSearchConditionFromSession(HttpSession session, SearchInformationMansionForm searchForm) throws
            SystemException {
        String action = searchForm.getAction();
        if (SubmitFormFlag.IS_SEARCHING.getFlag().equals(action)) {
            saveToSession(session, searchForm, new ArrayList<String>(), new HashSetValuedHashMap<>());
        } else {
            Integer keepPage = searchForm.getPage();
            String keepSorting = searchForm.getSortParameter();

            Object objSession = session.getAttribute(SEARCH_MANSION_MODEL_NAME);
            if (objSession instanceof SearchInformationMansionForm) {
                CommonUtils.copyProperties(searchForm, objSession);
                // don't copy below properties
                searchForm.setPage(keepPage);
                searchForm.setSortParameter(keepSorting);
            }
        }
    }

    /**
     * Format data form.
     * @param session HttpSession
     * @param form SearchInformationMansionForm
     */
    private void formatDataForm(HttpSession session, SearchInformationMansionForm form) {
        form.setUserLogin(String.valueOf(SecurityUtil.getUserLoggedInInfo().getUserTypeCode().getCode()));
        form.setPreviousScreen(SessionUtil.getPreviousScreenId());

        if (session.getAttribute(PREVIOUS_SCREEN_GJA0110) == null) {
            session.setAttribute(PREVIOUS_SCREEN_GJA0110, form.getPreviousScreen());
        } else {
            form.setPreviousScreen((String) session.getAttribute(PREVIOUS_SCREEN_GJA0110));
        }

        String txtHouseNumberFrom = form.getTxtHouseNumberFrom();
        String txtHouseNumberTo = form.getTxtHouseNumberTo();
        if (StringUtils.isNotBlank(txtHouseNumberFrom)) {
            form.setTxtHouseNumberFrom(txtHouseNumberFrom.replaceAll(",$", ""));
        }
        if (StringUtils.isNotBlank(txtHouseNumberTo)) {
            form.setTxtHouseNumberTo(txtHouseNumberTo.replaceAll(",$", ""));
        }
    }

}
