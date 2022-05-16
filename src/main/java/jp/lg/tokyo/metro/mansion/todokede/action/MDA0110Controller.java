/*
 * @(#) MDA0110Controller
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author DLLy
 * Create Date: 2019/11/27
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.action;

import static java.text.MessageFormat.format;
import static java.util.Objects.isNull;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD002;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD002_NO_ANSWER;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD004;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD005;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD005_NO_ANSWER;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD006;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD006_NO_ANSWER;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD007;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD007_NO_ANSWER;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD008;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD008_NO_ANSWER;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD010;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD010_NO_ANSWER;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD011;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD011_NO_ANSWER;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD012;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD012_NO_ANSWER;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD013;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD014;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD014_NO_ANSWER;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD030_UNACCEPTED;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD031;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD031_TEMPORARY_TYPE_CITY;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD031_TEMPORARY_TYPE_MAINTENANCE_CONTRACTOR;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD031_TEMPORARY_TYPE_MANSION;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD031_TEMPORARY_TYPE_SYS_ADMIN;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD031_TEMPORARY_TYPE_TOKYO_STAFF;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD040;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD052;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD052_NO_ANSWER;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD053;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.LOG_LG1090_I;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.LOG_LG9900_E;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.MS_E0141;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.NEXT_NOTIFICATION_DATE_MAX_KEY;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.NOTIFICATION_RENEWAL_PERIOD_KEY;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.NUM_100;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.NUM_120;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.NUM_2;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.NUM_20;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.NUM_3;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.NUM_30;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.NUM_300;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.NUM_34;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.NUM_4;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.NUM_5;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.NUM_50;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.NUM_6;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.NUM_7;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.NUM_8;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.SCREEN_ID_MDA0110;
import static jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil.getMessage;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.SystemException;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CheckUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CheckVirus;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.Code;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SecurityUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.converter.NotificationConverter;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.MansionInfoForm;
import jp.lg.tokyo.metro.mansion.todokede.form.NotificationExportForm;
import jp.lg.tokyo.metro.mansion.todokede.form.NotificationRegistrationForm;
import jp.lg.tokyo.metro.mansion.todokede.form.NotificationTemporaryForm;
import jp.lg.tokyo.metro.mansion.todokede.form.ReportForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IAdviceNotificationLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.IReportLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.ITemporaryNotificationInfoLogic;
import jp.lg.tokyo.metro.mansion.todokede.security.CurrentUserInformation;
import jp.lg.tokyo.metro.mansion.todokede.vo.MansionInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.NotificationInfoImportCsvVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.NotificationInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.NotificationRegistrationVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP010Vo;

@Controller
public class MDA0110Controller extends BaseMansionController {
    private static final Logger logger = LogManager.getLogger(MDA0110Controller.class);

    private static final String MDA0110_PATH = "/MDA0110";
    private static final String SHOW_SCREEN = "/show";
    private static final String NOTIFICATION_NO = "届出事項";
    private static final String REGISTRATION = "登録";
    private static final String MESSAGE_RESTORE_0 = "届出事項";
    private static final String MESSAGE_RESTORE_1 = "一時保存";
    private static final String NOTIFICATION_REGISTRATION = "届出事項登録_";
    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessages";
    private static final String ATTRIBUTE_ERROR_MESSAGE_EXCLUDE = "errorMessageExclude";
    private static final String APARTMENT_ID_IS_REQUIRED = "ApartmentId is required";
    private static final int COLUMN_NUM_CSV = 83;
    private static final int LINE_TWO_CSV = 2;
    private static final String MESSAGE_TEXT = "message";

    @Autowired
    private ITemporaryNotificationInfoLogic temporaryNotificationInfoLogic;
    @Autowired
    private IAdviceNotificationLogic adviceNotificationLogic;
    @Autowired
    private IReportLogic reportLogic;

    //MDA0110/1000000001
    @PostMapping(MDA0110_PATH + SHOW_SCREEN)
    public String show(Model model, String apartmentId, String activeBtn) throws BusinessException {
        try {
            if (isNotBlank(apartmentId)) {
                logger.info(getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
                CurrentUserInformation user = SecurityUtil.getUserLoggedInInfo();

                //マンション基本情報エリア/ Get mansion info area
                MansionInfoVo mansionInfoVo = getMansionInfo(apartmentId);
                if (isNull(mansionInfoVo)) {
                    mansionInfoVo = new MansionInfoVo();
                }
                model.addAttribute("mansionInfo", mansionInfoVo);

                //届出基本情報エリア/ Get notification area
                NotificationInfoVo notificationInfoVo = new NotificationInfoVo();
                boolean isCreated = isBlank(mansionInfoVo.getNewestNotificationNo());
                if (!isCreated) {
                    notificationInfoVo = getNotificationInfo(mansionInfoVo.getNewestNotificationNo());
                }

                //一時保存データ取得 / Get temporarily saved data from TBL205
                NotificationInfoVo temporaryData = initTemporaryData(apartmentId, Objects.requireNonNull(user).getUserTypeCode(), isCreated);

                //届出情報取得
                initNotificationInfo(mansionInfoVo, notificationInfoVo, mansionInfoVo.getCityName(), SCREEN_ID_MDA0110, activeBtn);
                NotificationRegistrationVo ntRegistrationVo = NotificationConverter.toNotificationRegistrationVo(mansionInfoVo, notificationInfoVo, SCREEN_ID_MDA0110, false);
                ntRegistrationVo.setFileMaxSize(Objects.requireNonNull(SessionUtil.getSystemSettings()).get(CommonConstants.MDA0110_FILE_MAX_SIZE));
                //Set login id for check event screen
                ntRegistrationVo.setLoginScreenId(SessionUtil.getScreenId());
                //Check notification is created/updated
                boolean isUpdateNotification = checkIsUpdateNotification(mansionInfoVo.getNewestNotificationNo(), mansionInfoVo.getNextNotificationDate());
                ntRegistrationVo.setUpdateNotification(isUpdateNotification);
                ntRegistrationVo.setCanRestore(Objects.nonNull(temporaryData));
                ntRegistrationVo.setScreenGJA0120(false);

                model.addAttribute("notificationRegistration", ntRegistrationVo);
                model.addAttribute("roleMansion", user.getUserTypeCode().getCode());

                logger.info(getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            } else {
                logger.error(APARTMENT_ID_IS_REQUIRED);
                throw new BusinessException(APARTMENT_ID_IS_REQUIRED);
            }
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(ex.getMessage());
        }

        return SCREEN_ID_MDA0110;
    }

    @PostMapping("/MDA0110/save")
    public ModelAndView saveNotificationRegistration(
            @ModelAttribute("notificationRegistration") @Valid NotificationRegistrationForm form, BindingResult result)
            throws BusinessException, SystemException {
        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info(getMessage(CommonConstants.LOG_LG1010_I, currentMethodName));
        ModelAndView md = new ModelAndView(SCREEN_ID_MDA0110, "notificationRegistration", form);

        CurrentUserInformation user = SecurityUtil.getUserLoggedInInfo();
        String nextNotificationDateMax = SessionUtil.getSystemSettingByKey(NEXT_NOTIFICATION_DATE_MAX_KEY);
        String notificationRenewalPeriod = SessionUtil.getSystemSettingByKey(NOTIFICATION_RENEWAL_PERIOD_KEY);
        md.addObject("roleMansion", user.getUserTypeCode().getCode());

        List<String> errorMessages = new ArrayList<>();

        //入力チェック
        //Check single item
        if (result.hasErrors()) {
            CommonUtils.getErrorsFromBindingResult(result, errorMessages);
        }

        //Check correlation
        if (errorMessages.isEmpty()) {
            checkCorrelation(form, errorMessages, SCREEN_ID_MDA0110, user.getUserTypeCode());
        }

        MansionInfoForm mansionInfoForm = new MansionInfoForm();
        CommonUtils.copyProperties(mansionInfoForm, form);

        //助言一時保存チェック / check advice temporary storage
        String newestNotificationNo = mansionInfoForm.getNewestNotificationNo();
        if (isNotBlank(newestNotificationNo) && adviceNotificationLogic.getTemporaryAdviceNotification(newestNotificationNo)) {
            errorMessages.add(format(getMessage(MS_E0141, "")));
        }

        md.addObject("mansionInfo", mansionInfoForm);

        //排他チェック / Check Exclusive
        if (checkExclusive(mansionInfoForm, errorMessages)) {
            md.addObject(ATTRIBUTE_ERROR_MESSAGE_EXCLUDE, errorMessages.get(0));
            logger.info(getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return md;
        }

        if (result.hasErrors() || CollectionUtils.isNotEmpty(errorMessages)) {
            md.addObject(ATTRIBUTE_ERROR_MESSAGE, errorMessages);
            logger.info(getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return md;
        }

        //convert form to vo before saving
        NotificationInfoVo vo = NotificationConverter.toNotificationInfoVo(form);

        //一時保存データ削除 / Delete temporarily saved data
        //登録/更新 / Register/Update data
        notificationInfoLogic.deleteTemporaryAndSaveNotification(vo, SCREEN_ID_MDA0110, user, nextNotificationDateMax, notificationRenewalPeriod);
        form.setNotificationNo(vo.getNotificationNo());
        form.setNextNotificationDate(vo.getNextNotificationDate());

        String messageSuccess = format(getMessage(CommonConstants.MS_I0001), NOTIFICATION_NO, REGISTRATION);
        md.addObject("messageSuccess", messageSuccess);

        //export RP010 in another window
        md.addObject("notificationNoRP010", form.getNotificationNo());

        logger.info(getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
        return md;
    }

    @PostMapping(MDA0110_PATH + "/temporary")
    public ModelAndView saveTemporary(@ModelAttribute("notificationRegistration") @Valid NotificationTemporaryForm form, BindingResult result) throws SystemException, InvocationTargetException, IllegalAccessException {
        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info(getMessage(CommonConstants.LOG_LG1010_I, currentMethodName));
        ModelAndView md = new ModelAndView(SCREEN_ID_MDA0110, "notificationRegistration", form);
        CurrentUserInformation user = SecurityUtil.getUserLoggedInInfo();

        md.addObject("roleMansion", user.getUserTypeCode().getCode());

        List<String> errorMessages = new ArrayList<>();

        //入力チェック（単項目） / Check single item
        if (result.hasErrors()) {
            CommonUtils.getErrorsFromBindingResult(result, errorMessages);
        }

        MansionInfoForm mansionInfoForm = new MansionInfoForm();
        CommonUtils.copyProperties(mansionInfoForm, form);

        md.addObject("mansionInfo", mansionInfoForm);

        //排他チェック / Check Exclusive
        if (checkExclusive(mansionInfoForm, errorMessages)) {
            md.addObject(ATTRIBUTE_ERROR_MESSAGE_EXCLUDE, errorMessages.get(0));
            logger.info(getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return md;
        }

        //Check has error and return
        if (result.hasErrors() || CollectionUtils.isNotEmpty(errorMessages)) {
            md.addObject(ATTRIBUTE_ERROR_MESSAGE, errorMessages);
            logger.info(getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return md;
        }

        String tempKbn = classifyTemporaryStorage(user.getUserTypeCode());
        NotificationInfoVo vo = NotificationConverter.toNotificationInfoVo(form);

        //一時保存データ削除 / Delete temporarily saved data
        //一時保存データ登録 / Register temporarily saved data
        temporaryNotificationInfoLogic.deleteAndSaveTemporaryData(vo, user, tempKbn, SCREEN_ID_MDA0110);
        String messageSuccess = format(getMessage(CommonConstants.MS_I0001), MESSAGE_RESTORE_0, MESSAGE_RESTORE_1);
        md.addObject("messageSuccess", messageSuccess);
        md.addObject("temporarySave", "true");
        form.setCanRestore(true);

        logger.info(getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
        return md;
    }

    @PostMapping(MDA0110_PATH + "/restore")
    public ResponseEntity<?> restoreTemporary(MansionInfoForm mansionInfoForm) throws SystemException {
        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info(getMessage(CommonConstants.LOG_LG1010_I, currentMethodName));
        CurrentUserInformation user = SecurityUtil.getUserLoggedInInfo();
        Map<String, Object> response = new HashMap<>();

        String apartmentId = mansionInfoForm.getApartmentId();
        //require apartmentId
        if (isBlank(apartmentId)) {
            logger.info(getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //マンション基本情報エリア/ Get mansion info area
        MansionInfoVo mansionInfoVo = getMansionInfo(apartmentId);
        if (isNull(mansionInfoVo)) {
            mansionInfoVo = new MansionInfoVo();
        }
        response.put("mansionInfo", mansionInfoVo);

        // 一時保存データ取得 / Get temporarily saved data from TBL205
        NotificationInfoVo temporaryData = initTemporaryData(apartmentId, user.getUserTypeCode(), isBlank(mansionInfoVo.getNewestNotificationNo()));

        if (isNull(temporaryData)) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add(MessageUtil.getMessage(CommonConstants.MS_E0123, "一時保存データ"));
            response.put(ATTRIBUTE_ERROR_MESSAGE, errorMessages);
            logger.info(getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        } else {
            initNotificationInfo(mansionInfoVo, temporaryData, temporaryData.getCityName(), SCREEN_ID_MDA0110, null);
            NotificationRegistrationVo registrationVo = NotificationConverter.toNotificationRegistrationVo(mansionInfoVo, temporaryData, SCREEN_ID_MDA0110, true);
            response.put("registrationVo", registrationVo);
            logger.info(getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/MDA0110/importCsv")
    public ResponseEntity<?> importCsv(@RequestParam("fileCsv") MultipartFile fileCsv) {
        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info(getMessage(CommonConstants.LOG_LG1010_I, currentMethodName));
        //Check list csv
        //file csv not found
        try {
            Map<String, String> message = new HashedMap<String, String>();

            if (!isCorrectFormatFile(fileCsv, message)) {
                logger.info(getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
                return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
            }
           
            //Get object csv file
            List<NotificationInfoImportCsvVo> lstRegistrationVo = notificationInfoLogic.getDataFormCsv(fileCsv.getBytes());
            logger.info(MessageUtil.getMessage(LOG_LG1090_I, fileCsv.getOriginalFilename()));
            //If file csv > 2 record then show message error
            if (lstRegistrationVo != null && lstRegistrationVo.size() != 2) {
                message.put(MESSAGE_TEXT, MessageUtil.getMessage(CommonConstants.MS_E0131));
                logger.info(getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
                return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
            } else if (lstRegistrationVo != null && lstRegistrationVo.size() == 2) {
                initImportDataBeforeDisplay(lstRegistrationVo.get(1));
            }

            logger.info(getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return new ResponseEntity<>(lstRegistrationVo != null ? lstRegistrationVo.get(1) : new NotificationInfoImportCsvVo(), HttpStatus.OK);
        } catch (Exception ex) {
            logger.error(getMessage(LOG_LG9900_E, ex.getMessage()));
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping(MDA0110_PATH + "/exportCsv")
    public ResponseEntity<?> exportCsv(@Valid NotificationExportForm form, BindingResult result, HttpServletResponse response) throws IOException {
        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, currentMethodName));
        Map<String, Object> rsData = new HashedMap<String, Object>();
        List<String> errorMessages = new ArrayList<>();
        //画面内容チェック
        //Check single item
        //Check correlation
        if (result.hasErrors()) {
            CommonUtils.getErrorsFromBindingResult(result, errorMessages);
            if (CollectionUtils.isNotEmpty(errorMessages)) {
            	rsData.put(ATTRIBUTE_ERROR_MESSAGE, errorMessages);
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
                return new ResponseEntity<>(rsData, HttpStatus.NOT_ACCEPTABLE);
            }
        }
        
        // set header file csv
        response.setHeader("Content-Type", "text/csv");
        form.getInfoAreaCommon().setCalNotificationDate(DateTimeUtil.convertFormatDateOnly(form.getInfoAreaCommon().getCalNotificationDate()));
        form.getInfoAreaCommon().setCalBuiltDate(DateTimeUtil.convertFormatDateOnly(form.getInfoAreaCommon().getCalBuiltDate()));

        notificationInfoLogic.exportCsv(form, response.getOutputStream());

        String fileName = NOTIFICATION_REGISTRATION.concat(DateTimeUtil.getCurrentDateForCsv()) + ".csv";

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + fileName + "\"");

        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1100_I, fileName));
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @param reportform ReportForm
     * @param model String
     * @return String
     * @throws BusinessException
     */
    @PostMapping(MDA0110_PATH + "/report")
    public String getReport(ReportForm reportform, Model model) throws BusinessException {
        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, currentMethodName));
        String reportName = reportform.getReportName();
        String relatedNumber = reportform.getRelatedNumber();

        // get user id form session
        String loginUserId = SecurityUtil.getUserLoggedInInfo().getUserId();

        // report with report name
        switch (reportName) {
            case CommonConstants.RP010_CREATE:
                RP010Vo rp010VoCreate = reportLogic.getReportRP010(relatedNumber);
                model.addAttribute("rp010Vo", rp010VoCreate);
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1120_I, loginUserId, CommonConstants.RP010, relatedNumber));
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
                return CommonConstants.RP010_CREATE;
            case CommonConstants.RP010_UPDATE:
                RP010Vo rp010VoUpdate = reportLogic.getReportRP010(relatedNumber);
                model.addAttribute("rp010Vo", rp010VoUpdate);
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1120_I, loginUserId, CommonConstants.RP010, relatedNumber));
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
                return CommonConstants.RP010_UPDATE;
            default:
                break;
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I));

        return SCREEN_ID_MDA0110;
    }

    /**
     * Check if import
     *
     * @param fileCsv
     * @param message
     * @return
     * @throws IOException
     * @throws FileNotFoundException
     */
    private boolean isCorrectFormatFile(MultipartFile fileCsv, Map<String, String> message) throws FileNotFoundException, IOException {
        if (fileCsv != null) {
            String name = fileCsv.getOriginalFilename();
            String fileMaxSize = SessionUtil.getSystemSettings().get(CommonConstants.MDA0110_FILE_MAX_SIZE);
            //Check file1 is Input and incorrect type
            if (StringUtils.isNotEmpty(name) && !CommonUtils.checkExtension(name, CommonConstants.FORMAT_FILE_IMPORT)) {
                message.put(MESSAGE_TEXT, MessageUtil.getMessage(CommonConstants.MS_E0135));
                return false;
            } else if (!CheckVirus.validateNoVirus(fileCsv.getInputStream())) {
                message.put(MESSAGE_TEXT, MessageUtil.getMessage(CommonConstants.MS_E0134));
                return false;
            } else if (StringUtils.isNotEmpty(name) && fileCsv.getSize() == 0) {
                message.put(MESSAGE_TEXT, MessageUtil.getMessage(CommonConstants.MS_E0130));
                return false;
            } else if (!CheckUtil.isNotMaxSizeFileUpload(fileCsv, fileMaxSize)) {
                message.put(MESSAGE_TEXT, MessageUtil.getMessage(CommonConstants.MS_E0133, new String[]{fileMaxSize}));
                return false;
            } else if (!isMappingColumnFile(fileCsv.getBytes())) {
            	message.put(MESSAGE_TEXT, MessageUtil.getMessage(CommonConstants.MS_E0136));
            	return false;
            }
        }
        return true;
    }
    
    /**
     * Check mapping column file
     * @param fileBytes
     * @return
     */
    private boolean isMappingColumnFile(byte[] fileBytes) {
    	try {
    		 InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(fileBytes), CommonConstants.ENCODE_UTF8);
             BufferedReader reader = new BufferedReader(isr);
             String line;
             int countLine = 1;
             while ((line = reader.readLine()) != null) {
            	 if (countLine == LINE_TWO_CSV) {
            		 //Count comma in string ','
            		 int countComma = StringUtils.countMatches(line, CommonConstants.COMMA);
            		 if (countComma != COLUMN_NUM_CSV) {
            			 return false;
            		 }
            	 }
             	
                // process the line
             	countLine++;
             }
             return true;
    	}
    	catch (Exception ex) {
    		return false;
    	}
    }

    /**
     * @param apartmentId, userType, notificationNo
     * @param isCreated
     * @return
     */
    private NotificationInfoVo initTemporaryData(String apartmentId, UserTypeCode userType, boolean isCreated) throws SystemException {
        String status = CodeUtil.getValue(CommonConstants.CD030, CD030_UNACCEPTED);
        String tempKbn = classifyTemporaryStorage(userType);

        return temporaryNotificationInfoLogic.getTemporaryData(apartmentId, status, tempKbn, isCreated);
    }

    /**
     * @param userType
     * @return
     */
    private String classifyTemporaryStorage(UserTypeCode userType) {
        String tempKbn;
        switch (userType) {
            case CITY:
                tempKbn = CodeUtil.getValue(CD031, CD031_TEMPORARY_TYPE_CITY);
                break;
            case TOKYO_STAFF:
                tempKbn = CodeUtil.getValue(CD031, CD031_TEMPORARY_TYPE_TOKYO_STAFF);
                break;
            case SYSTEM_ADMIN:
                tempKbn = CodeUtil.getValue(CD031, CD031_TEMPORARY_TYPE_SYS_ADMIN);
                break;
            case MAINTENANCER:
                tempKbn = CodeUtil.getValue(CD031, CD031_TEMPORARY_TYPE_MAINTENANCE_CONTRACTOR);
                break;
            default:
                tempKbn = CodeUtil.getValue(CD031, CD031_TEMPORARY_TYPE_MANSION);
        }
        return tempKbn;
    }

    private void initImportDataBeforeDisplay(NotificationInfoImportCsvVo vo) {
        if (Objects.nonNull(vo)) {
            if (isNotBlank(vo.getNotificationDate())) {
                if (vo.getNotificationDate().length() > NUM_8) {
                    vo.setNotificationDate(null);
                } else if (vo.getNotificationDate().length() == NUM_8) {
                    vo.setNotificationDate(DateTimeUtil.convertReFormatDateOnly(vo.getNotificationDate()));
                }
            }

            if (isNotBlank(vo.getNotificationGroupName()) && vo.getNotificationGroupName().length() > NUM_50) {
                vo.setNotificationGroupName(null);
            }

            if (isNotBlank(vo.getNotificationPersonName()) && vo.getNotificationPersonName().length() > NUM_20) {
                vo.setNotificationPersonName(null);
            }

            List<String> lstCodeCD004 = CodeUtil.getList(CD004).stream().map(Code::getValue).collect(Collectors.toList());
            if (isNotBlank(vo.getChangeReason()) && !lstCodeCD004.contains(vo.getChangeReason())) {
                vo.setChangeReason(null);
            }

            List<String> lstCodeCD040 = CodeUtil.getList(CD040).stream().map(Code::getValue).collect(Collectors.toList());
            if (isNotBlank(vo.getLostElseReasonCode()) && !lstCodeCD040.contains(vo.getLostElseReasonCode())) {
                vo.setLostElseReasonCode(null);
            }

            if (isNotBlank(vo.getLostElseReasonElse()) && vo.getLostElseReasonElse().length() > NUM_30) {
                vo.setLostElseReasonElse(null);
            }

            if (isNotBlank(vo.getApartmentName()) && vo.getApartmentName().length() > NUM_50) {
                vo.setApartmentName(null);
            }

            if (isNotBlank(vo.getApartmentNamePhonetic()) && vo.getApartmentNamePhonetic().length() > NUM_100) {
                vo.setApartmentNamePhonetic(null);
            }

            if (isNotBlank(vo.getApartmentZipCode1()) && vo.getApartmentZipCode1().length() > NUM_3) {
                vo.setApartmentZipCode1(null);
            }

            if (isNotBlank(vo.getApartmentZipCode2()) && vo.getApartmentZipCode2().length() > NUM_4) {
                vo.setApartmentZipCode2(null);
            }

            if (isNotBlank(vo.getApartmentAddress2()) && vo.getApartmentAddress2().length() > NUM_100) {
                vo.setApartmentAddress2(null);
            }

            List<String> lstCodeCD014 = CodeUtil.getList(CD014).stream().map(Code::getValue).collect(Collectors.toList());
            if (isNotBlank(vo.getGroupYesno()) && !lstCodeCD014.contains(vo.getGroupYesno())) {
                vo.setGroupYesno(CodeUtil.getValue(CD014, CD014_NO_ANSWER));
            }

            if (isNotBlank(vo.getApartmentNumber()) && vo.getApartmentNumber().length() > NUM_3) {
                vo.setApartmentNumber(null);
            }

            List<String> lstCodeCD005 = CodeUtil.getList(CD005).stream().map(Code::getValue).collect(Collectors.toList());
            if (isNotBlank(vo.getGroupForm()) && !lstCodeCD005.contains(vo.getGroupForm())) {
                vo.setGroupForm(CodeUtil.getValue(CD005, CD005_NO_ANSWER));
            }

            if (isNotBlank(vo.getGroupFormElse()) && vo.getGroupFormElse().length() > NUM_30) {
                vo.setGroupFormElse(null);
            }

            if (isNotBlank(vo.getHouseNumber()) && vo.getHouseNumber().length() > NUM_6) {
                vo.setHouseNumber(null);
            }

            if (isNotBlank(vo.getFloorNumber()) && vo.getFloorNumber().length() > NUM_2) {
                vo.setFloorNumber(null);
            }

            if (isNotBlank(vo.getBuiltDate())) {
                if (vo.getBuiltDate().length() > NUM_8) {
                    vo.setBuiltDate(null);
                } else if (vo.getBuiltDate().length() == NUM_8) {
                    vo.setBuiltDate(DateTimeUtil.convertReFormatDateOnly(vo.getBuiltDate()));
                }
            }

            List<String> lstCodeCD006 = CodeUtil.getList(CD006).stream().map(Code::getValue).collect(Collectors.toList());
            if (isNotBlank(vo.getLandRights()) && !lstCodeCD006.contains(vo.getLandRights())) {
                vo.setLandRights(CodeUtil.getValue(CD006, CD006_NO_ANSWER));
            }

            if (isNotBlank(vo.getLandRightsElse()) && vo.getLandRightsElse().length() > NUM_30) {
                vo.setLandRightsElse(null);
            }

            List<String> lstCodeCD007 = CodeUtil.getList(CD007).stream().map(Code::getValue).collect(Collectors.toList());
            if (isNotBlank(vo.getUserfor()) && !lstCodeCD007.contains(vo.getUserfor())) {
                vo.setUserfor(CodeUtil.getValue(CD007, CD007_NO_ANSWER));
            }

            if (isNotBlank(vo.getUserforElse()) && vo.getUserforElse().length() > NUM_30) {
                vo.setUserforElse(null);
            }

            List<String> lstCodeCD008 = CodeUtil.getList(CD008).stream().map(Code::getValue).collect(Collectors.toList());
            if (isNotBlank(vo.getManagementForm()) && !lstCodeCD008.contains(vo.getManagementForm())) {
                vo.setManagementForm(CodeUtil.getValue(CD008, CD008_NO_ANSWER));
            }

            if (isNotBlank(vo.getManagementFormElse()) && vo.getManagementFormElse().length() > NUM_30) {
                vo.setManagementFormElse(null);
            }

            if (isNotBlank(vo.getManagerName()) && vo.getManagerName().length() > NUM_34) {
                vo.setManagerName(null);
            }

            if (isNotBlank(vo.getManagerNamePhonetic()) && vo.getManagerNamePhonetic().length() > NUM_34) {
                vo.setManagerNamePhonetic(null);
            }

            if (isNotBlank(vo.getManagerZipCode1()) && vo.getManagerZipCode1().length() > NUM_3) {
                vo.setManagerZipCode1(null);
            }

            if (isNotBlank(vo.getManagerZipCode2()) && vo.getManagerZipCode2().length() > NUM_4) {
                vo.setManagerZipCode2(null);
            }

            if (isNotBlank(vo.getManagerAddress()) && vo.getManagerAddress().length() > NUM_100) {
                vo.setManagerAddress(null);
            }

            if (isNotBlank(vo.getManagerTelno1()) && vo.getManagerTelno1().length() > NUM_5) {
                vo.setManagerTelno1(null);
            }

            if (isNotBlank(vo.getManagerTelno2()) && vo.getManagerTelno2().length() > NUM_4) {
                vo.setManagerTelno2(null);
            }

            if (isNotBlank(vo.getManagerTelno3()) && vo.getManagerTelno3().length() > NUM_4) {
                vo.setManagerTelno3(null);
            }

            List<String> lstCodeCD053 = CodeUtil.getList(CD053).stream().map(Code::getValue).collect(Collectors.toList());
            if (isNotBlank(vo.getGroup()) && !lstCodeCD053.contains(vo.getGroup())) {
                vo.setGroup(null);
            }

            if (isNotBlank(vo.getManager()) && !lstCodeCD053.contains(vo.getManager())) {
                vo.setManager(null);
            }

            if (isNotBlank(vo.getRule()) && !lstCodeCD053.contains(vo.getRule())) {
                vo.setRule(null);
            }

            if (isNotBlank(vo.getRuleChangeYear()) && vo.getRuleChangeYear().length() > NUM_4) {
                vo.setRuleChangeYear(null);
            }

            if (isNotBlank(vo.getOneyearOver()) && !lstCodeCD053.contains(vo.getOneyearOver())) {
                vo.setOneyearOver(null);
            }

            if (isNotBlank(vo.getMinutes()) && !lstCodeCD053.contains(vo.getMinutes())) {
                vo.setMinutes(null);
            }

            if (isNotBlank(vo.getManagementCost()) && !lstCodeCD053.contains(vo.getManagementCost())) {
                vo.setManagementCost(null);
            }

            if (isNotBlank(vo.getRepairCost()) && !lstCodeCD053.contains(vo.getRepairCost())) {
                vo.setRepairCost(null);
            }

            if (isNotBlank(vo.getRepairMonthlycost()) && vo.getRepairMonthlycost().length() > NUM_7) {
                vo.setRepairMonthlycost(null);
            }

            if (isNotBlank(vo.getRepairPlan()) && !lstCodeCD053.contains(vo.getRepairPlan())) {
                vo.setRepairPlan(null);
            }

            if (isNotBlank(vo.getRepairNearestYear()) && vo.getRepairNearestYear().length() > NUM_4) {
                vo.setRepairNearestYear(null);
            }

            List<String> lstCodeCD002 = CodeUtil.getList(CD002).stream().map(Code::getValue).collect(Collectors.toList());
            if (isNotBlank(vo.getLongRepairPlan()) && !lstCodeCD002.contains(vo.getLongRepairPlan())) {
                vo.setLongRepairPlan(null);
            }

            if (isNotBlank(vo.getLongRepairPlanYear()) && vo.getLongRepairPlanYear().length() > NUM_4) {
                vo.setLongRepairPlanYear(null);
            }

            if (isNotBlank(vo.getLongRepairPlanPeriod()) && vo.getLongRepairPlanPeriod().length() > NUM_2) {
                vo.setLongRepairPlanPeriod(null);
            }

            if (isNotBlank(vo.getLongRepairPlanYearFrom()) && vo.getLongRepairPlanYearFrom().length() > NUM_4) {
                vo.setLongRepairPlanYearFrom(null);
            }

            if (isNotBlank(vo.getLongRepairPlanYearTo()) && vo.getLongRepairPlanYearTo().length() > NUM_4) {
                vo.setLongRepairPlanYearTo(null);
            }

            if (isNotBlank(vo.getArrearageRule()) && !lstCodeCD002.contains(vo.getArrearageRule())) {
                vo.setArrearageRule(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isNotBlank(vo.getSegment()) && !lstCodeCD002.contains(vo.getSegment())) {
                vo.setSegment(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            List<String> lstCodeCD010 = CodeUtil.getList(CD010).stream().map(Code::getValue).collect(Collectors.toList());
            if (isNotBlank(vo.getEmptyPercent()) && !lstCodeCD010.contains(vo.getEmptyPercent())) {
                vo.setEmptyPercent(CodeUtil.getValue(CD010, CD010_NO_ANSWER));
            }

            if (isNotBlank(vo.getEmptyNumber()) && vo.getEmptyNumber().length() > NUM_4) {
                vo.setEmptyNumber(null);
            }

            List<String> lstCodeCD052 = CodeUtil.getList(CD052).stream().map(Code::getValue).collect(Collectors.toList());
            if (isNotBlank(vo.getRentalPercent()) && !lstCodeCD052.contains(vo.getRentalPercent())) {
                vo.setRentalPercent(CodeUtil.getValue(CD052, CD052_NO_ANSWER));
            }

            if (isNotBlank(vo.getRentalNumber()) && vo.getRentalNumber().length() > NUM_4) {
                vo.setRentalNumber(null);
            }

            List<String> lstCodeCD011 = CodeUtil.getList(CD011).stream().map(Code::getValue).collect(Collectors.toList());
            if (isNotBlank(vo.getSeismicDiagnosis()) && !lstCodeCD011.contains(vo.getSeismicDiagnosis())) {
                vo.setSeismicDiagnosis(CodeUtil.getValue(CD011, CD011_NO_ANSWER));
            }

            List<String> lstCodeCD012 = CodeUtil.getList(CD012).stream().map(Code::getValue).collect(Collectors.toList());
            if (isNotBlank(vo.getEarthquakeResistance()) && !lstCodeCD012.contains(vo.getEarthquakeResistance())) {
                vo.setEarthquakeResistance(CodeUtil.getValue(CD012, CD012_NO_ANSWER));
            }

            if (isNotBlank(vo.getSeismicRetrofit()) && !lstCodeCD011.contains(vo.getSeismicRetrofit())) {
                vo.setSeismicRetrofit(CodeUtil.getValue(CD011, CD011_NO_ANSWER));
            }

            if (isNotBlank(vo.getDesignDocument()) && !lstCodeCD002.contains(vo.getDesignDocument())) {
                vo.setDesignDocument(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isNotBlank(vo.getRepairHistory()) && !lstCodeCD002.contains(vo.getRepairHistory())) {
                vo.setRepairHistory(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isNotBlank(vo.getVoluntaryOrganization()) && !lstCodeCD002.contains(vo.getVoluntaryOrganization())) {
                vo.setVoluntaryOrganization(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isNotBlank(vo.getDisasterPreventionManual()) && !lstCodeCD002.contains(vo.getDisasterPreventionManual())) {
                vo.setDisasterPreventionManual(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isNotBlank(vo.getDisasterPreventionStockpile()) && !lstCodeCD002.contains(vo.getDisasterPreventionStockpile())) {
                vo.setDisasterPreventionStockpile(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isNotBlank(vo.getNeedSupportList()) && !lstCodeCD002.contains(vo.getNeedSupportList())) {
                vo.setNeedSupportList(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isNotBlank(vo.getDisasterPreventionRegular()) && !lstCodeCD002.contains(vo.getDisasterPreventionRegular())) {
                vo.setDisasterPreventionRegular(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isNotBlank(vo.getSlope()) && !lstCodeCD002.contains(vo.getSlope())) {
                vo.setSlope(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isNotBlank(vo.getRailing()) && !lstCodeCD002.contains(vo.getRailing())) {
                vo.setRailing(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isNotBlank(vo.getElevator()) && !lstCodeCD002.contains(vo.getElevator())) {
                vo.setElevator(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isNotBlank(vo.getLed()) && !lstCodeCD002.contains(vo.getLed())) {
                vo.setLed(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isNotBlank(vo.getHeatShielding()) && !lstCodeCD002.contains(vo.getHeatShielding())) {
                vo.setHeatShielding(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isNotBlank(vo.getEquipmentCharge()) && !lstCodeCD002.contains(vo.getEquipmentCharge())) {
                vo.setEquipmentCharge(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isNotBlank(vo.getCommunity()) && !lstCodeCD002.contains(vo.getCommunity())) {
                vo.setCommunity(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            List<String> lstCodeCD013 = CodeUtil.getList(CD013).stream().map(Code::getValue).collect(Collectors.toList());
            if (isNotBlank(vo.getContactProperty()) && !lstCodeCD013.contains(vo.getContactProperty())) {
                vo.setContactProperty(null);
            }

            if (isNotBlank(vo.getContactPropertyElse()) && vo.getContactPropertyElse().length() > NUM_30) {
                vo.setContactPropertyElse(null);
            }

            if (isNotBlank(vo.getContactZipCode1()) && vo.getContactZipCode1().length() > NUM_3) {
                vo.setContactZipCode1(null);
            }

            if (isNotBlank(vo.getContactZipCode2()) && vo.getContactZipCode2().length() > NUM_4) {
                vo.setContactZipCode2(null);
            }

            if (isNotBlank(vo.getContactAddress()) && vo.getContactAddress().length() > NUM_100) {
                vo.setContactAddress(null);
            }

            if (isNotBlank(vo.getContactTelno1()) && vo.getContactTelno1().length() > NUM_5) {
                vo.setContactTelno1(null);
            }

            if (isNotBlank(vo.getContactTelno2()) && vo.getContactTelno2().length() > NUM_4) {
                vo.setContactTelno2(null);
            }

            if (isNotBlank(vo.getContactTelno3()) && vo.getContactTelno3().length() > NUM_4) {
                vo.setContactTelno3(null);
            }

            if (isNotBlank(vo.getContactName()) && vo.getContactName().length() > NUM_34) {
                vo.setContactName(null);
            }

            if (isNotBlank(vo.getContactNamePhonetic()) && vo.getContactNamePhonetic().length() > NUM_34) {
                vo.setContactNamePhonetic(null);
            }

            if (isNotBlank(vo.getContactMail()) && vo.getContactMail().length() > NUM_120) {
                vo.setContactMail(null);
            }

            if (isNotBlank(vo.getOptional()) && vo.getOptional().length() > NUM_300) {
                vo.setOptional(null);
            }
        }
    }
}