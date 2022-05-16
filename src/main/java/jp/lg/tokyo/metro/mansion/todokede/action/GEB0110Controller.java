/*
 * @(#)GEB0110Controller.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 *
 * Create Date: 2019/11/20
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.action;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.AllowCity;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CheckUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CheckVirus;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CustomDateUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SecurityUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.exception.UserNotFoundException;
import jp.lg.tokyo.metro.mansion.todokede.form.ProgressRecordRegistrationForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IProgressRecordRegistrationLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.CodeVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.FileManagerInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.MansionInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ProgressRecordRegistrationVo;

@AllowCity
@Controller
public class GEB0110Controller extends BaseMansionController {
    private static final Logger logger = LogManager.getLogger(GEB0110Controller.class);
    
    private static final String GEB0110 = "GEB0110";
    private static final String PROGRESS_RECORD = "経過記録";
    private static final String REGISTRATION = "登録";
    private static final String CORRESPOND_DATE = "対応日時";
    private static final String MESSAGE_FORM_NULL = "MansionInfoVo null";
    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String ATTRIBUTE_SUCCESS_MESSAGE = "messageSuccess";
    private static final String MESSAGE_FILE_NOT_FOUND = "File not Exist!";
    private static final String ATTRIBUTE_NOT_MAP_UPDATE_DATETIME = "errorNotMapUpdateDatetime";
    
    @Autowired
    private IProgressRecordRegistrationLogic progressRecordRegistrationLogic;
    
    /**
     * 
     * @param model Model
     * @param form ProgressRecordRegistrationForm
     * @param request HttpServletRequest
     * @return
     * @throws BusinessException 
     */
    @PostMapping(value = "/GEB0110/show")
    public String show(Model model,ProgressRecordRegistrationForm form, HttpServletRequest request) throws BusinessException {
        try {
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            //Get mansion info
            MansionInfoVo mansionVo =  getMansionInfo(form.getApartmentId());
            if (mansionVo == null || StringUtils.isEmpty(mansionVo.getApartmentId())) {
                //Return error screen
                throw new BusinessException(MESSAGE_FORM_NULL);
            }
            ProgressRecordRegistrationVo vo = new ProgressRecordRegistrationVo();
             
            BeanUtils.copyProperties(vo, mansionVo);
            //Prepare data for display
            prepare(vo);
            //Get parameter
            String progressRecordNo = form.getProgressRecordNo();
            //Check if progressRecordNo is not blank or Empty
            if (StringUtils.isNotEmpty(progressRecordNo)) {
                //Get Progress Record
                ProgressRecordRegistrationVo votbl300 = progressRecordRegistrationLogic.getProgressRecordTbl300(progressRecordNo);
                if (votbl300 != null) {
                    vo.setProgressRecordNo(progressRecordNo);
                    vo.setCalCorrespondDate(votbl300.getCalCorrespondDate());
                    vo.setLstCorrespondTypeCode(votbl300.getLstCorrespondTypeCode());
                    vo.setLstNoticeTypeCode(votbl300.getLstNoticeTypeCode());
                    vo.setTxaProgressRecordDetail(votbl300.getTxaProgressRecordDetail());
                    vo.setTxtProgressRecordOverview(votbl300.getTxtProgressRecordOverview());
                    //Get list file by record no
                    vo.setFileInfos(progressRecordRegistrationLogic.getProgressRecordFileInfos(progressRecordNo));
                    vo.setRdoSupportCode(votbl300.getRdoSupportCode());
                    vo.setUpdateDatetime(votbl300.getUpdateDatetime());
                    vo.setUpdateUserId(SecurityUtil.getUserLoggedInInfo().getUserId());
                }
            }
            
            //City_code =1017
            model.addAttribute("mansionInfo", vo);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(ex.getMessage());
        }

        return GEB0110;
    }
    
    /**
     * 
     * @param form ProgressRecordRegistrationForm
     * @param result BindingResult
     * @param request WebRequest
     * @param errors Errors
     * @return 
     * @throws BusinessException 
     */
    @PostMapping("/GEB0110/save")
    public ModelAndView save(@ModelAttribute("mansionInfo") @Valid ProgressRecordRegistrationForm form, 
            BindingResult result, WebRequest request, Errors errors) throws BusinessException {
        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, currentMethodName));
        ModelAndView mdv = new ModelAndView(GEB0110, "mansionInfo", form);
        List<String> errorMessage = new ArrayList<String>();
        
        try {
            if (StringUtils.isEmpty(form.getChkConfirm())) {
            	//Reset file Input
            	resetInputFile(form);
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
                return mdv;
            }
            // Check case if update info
            else if (StringUtils.isNoneEmpty(form.getApartmentId())
                    && StringUtils.isNoneEmpty(form.getProgressRecordNo())
                    // Check record update is latest TBL300
                    && !progressRecordRegistrationLogic.isUpdateLatestTbl300(form.getProgressRecordNo(),
                            form.getUpdateDatetime())) {
                // Reject message not map update datetime
            	mdv.addObject(ATTRIBUTE_NOT_MAP_UPDATE_DATETIME, MessageUtil.getMessage(CommonConstants.MS_E0122));
            	return mdv;
            }
            // Check format file upload
            else if (!isCorrectFormatFile(form, errorMessage)) {
                mdv.addObject(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
                //Reset file Input
            	resetInputFile(form);
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
                return mdv;
            }
            // Check correspondDate in MAX in TBL300 when add new and tokyo support
            else if (StringUtils.isEmpty(form.getProgressRecordNo())
                    && CommonConstants.TOKYO_SUPPORT_CODE.equals(form.getLstCorrespondTypeCode())
                    && !progressRecordRegistrationLogic.isValidMaxCorrespondDate(form.getApartmentId(),
                            DateTimeUtil.convertFormatDate(form.getCalCorrespondDate()))) {
                errorMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0100, CORRESPOND_DATE));
                mdv.addObject(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
                return mdv;
            } else if (StringUtils.isEmpty(form.getProgressRecordNo())
                    && CommonConstants.TOKYO_SUPPORT_CODE.equals(form.getLstCorrespondTypeCode())
                    && mansionInfoLogic.isExistMansionWithSupportCd(form.getApartmentId(), form.getRdoSupportCode())) {
                errorMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0124));
            }
            if (result.hasErrors() || CollectionUtils.isNotEmpty(errorMessage)) {
                CommonUtils.getErrorsFromBindingResult(result, errorMessage);
                mdv.addObject(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
                //Reset file Input
            	resetInputFile(form);
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
                return mdv;
            }
            form.setUpdateUserId(SecurityUtil.getUserLoggedInInfo().getUserId());
            // Add or Update data
            if (progressRecordRegistrationLogic.save(form)) {
                ModelAndView md = new ModelAndView(GEB0110);
                String messageSuccess = MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_I0001),
                        PROGRESS_RECORD, REGISTRATION);
                md.addObject(ATTRIBUTE_SUCCESS_MESSAGE, messageSuccess);
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
                return md;
            }
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            //Reset file Input
        	resetInputFile(form);
            throw new BusinessException(ex.getMessage());
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
        return mdv;
    }
    
    /**
     * 
     * @param progressRecordAttachNo String
     * @return
     */
    @GetMapping("/GEB0110/downloadFile/{progressRecordAttachNo}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String progressRecordAttachNo) {
        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, currentMethodName));
        // Load file from database
        FileManagerInfoVo vo = progressRecordRegistrationLogic.findProgressRecordFileById(progressRecordAttachNo);
        //Check can't get file
        if (vo == null || vo.getFile() == null) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, MESSAGE_FILE_NOT_FOUND));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + vo.getFileName() + "\"")
                .body(new ByteArrayResource(vo.getFile()));
    }
    
    /**
     * Check file not found
     * @param file
     * @return
     */
    @PostMapping("/GEB0110/checkFile")
    public ResponseEntity<?> checkFile(@RequestParam("file") MultipartFile file) {
        try {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);

        } catch (UserNotFoundException ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Prepare data for display
     * @param vo
     * @throws BusinessException 
     */
    private void prepare(ProgressRecordRegistrationVo vo) throws BusinessException {
        try {
            List<CodeVo> lstSupportCd =  copyListCodeInfos(CodeUtil.getList(CommonConstants.CD021));
            List<CodeVo> lstResponseTypeCode =  copyListCodeInfos(CodeUtil.getList(CommonConstants.CD019));
            List<CodeVo> listNoticeTypeCodes =  copyListCodeInfos(CodeUtil.getList(CommonConstants.CD020));
            //Get Current Date when init screen
            vo.setCalCorrespondDate(CustomDateUtil.getCurrentDateTime(CommonConstants.FORMAT_DATETIME_YYYYMMDDHHMM));
            //Display for radio radiobox support code
            vo.setLstSupportCode(lstSupportCd);
            //Display for radio radiobox response type code
            vo.setLstResponseTypeCode(lstResponseTypeCode);
            //Display for radio dropdowlist NoticeType code
            vo.setListNoticeTypeCodes(listNoticeTypeCodes);
            
            vo.setFileMaxSize(SessionUtil.getSystemSettings().get(CommonConstants.GEB0110_FILE_SIZE_MAX));
            
            vo.setFileMaxCount(SessionUtil.getSystemSettings().get(CommonConstants.GEB0110_FILE_COUNT_MAX));
            
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(ex.getMessage());
        }
    }
    
    /**
     * New List when remove file deleted
     * @param lstSelectFile selectedFiles
     * @param lstFileInDb lstFileInDb
     * @return
     */
    private List<FileManagerInfoVo> lstAfterRemoveSelectedFile(String[] selectedFiles, List<FileManagerInfoVo> lstFileInDb) {
        List<FileManagerInfoVo> lstFileRemove = new ArrayList<FileManagerInfoVo>();
        if (selectedFiles != null && lstFileInDb != null) {
            List<String> lstSelectFile = Arrays.asList(selectedFiles);
            for (FileManagerInfoVo obj : lstFileInDb) {
                if (!lstSelectFile.contains(obj.getProgressRecordAttachNo())) {
                    lstFileRemove.add(obj);
                }
            }
        }
        return CollectionUtils.isEmpty(lstFileRemove) ? lstFileInDb : lstFileRemove;
    }
    
    /**
     * Check validation file input
     * @param form ProgressRecordRegistrationForm
     * @param errors errorMessage
     * @return boolean
     * @throws Exception  Exception
     */
    private boolean isCorrectFormatFile(ProgressRecordRegistrationForm form, List<String> errorMessage) throws Exception {
        //List file type allow insert
        MultipartFile file1 = form.getFilFileFrom1();
        MultipartFile file2 = form.getFilFileFrom2();
        MultipartFile file3 = form.getFilFileFrom3();
        //Count file selected on screen
        int countFileSelected = 0;
        List<FileManagerInfoVo> lstFileInDb = lstAfterRemoveSelectedFile(form.getSelectedFiles(), form.getFileInfos());
        
        //List file name for check duplicate file when upload
        List<String> lstString = new ArrayList<>();
        if (file1 != null) {
            String name = form.getFilFileFromName1();
            //Check file1 is Input and incorrect type
            if (StringUtils.isNotEmpty(name) && !CommonUtils.checkExtension(name, CommonConstants.FORMAT_FILE_UPLOAD)) {
                errorMessage.add(MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0109), name));
                return false;
            }
            //Check file1 not exist 
            else if (StringUtils.isNotEmpty(form.getFilFileFromName1()) && file1.isEmpty()) {
                errorMessage.add(MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0137), form.getFilFileFromName1()));
                return false;
            }
            else if (!checkDuplicateFile(name, errorMessage, lstString, lstFileInDb)) {
                return false;
            }
            else if (!CheckUtil.isNotMaxSizeFileUpload(file1, form.getFileMaxSize())) {
                errorMessage.add(MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0110), name, form.getFileMaxSize()));
                return false;
            }
            //Check virus file
            else if (!isOkCheckVirus(name, file1, errorMessage)) {
                return false;
            }
            else if (StringUtils.isNotEmpty(name)) {
                countFileSelected++;
            }
        }
        if (file2 != null) {
            String name = form.getFilFileFromName2();
            //Check file2 is Input and incorrect type
            if (StringUtils.isNotEmpty(name) && !CommonUtils.checkExtension(name, CommonConstants.FORMAT_FILE_UPLOAD)) {
                errorMessage.add(MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0109), name));
                return false;
            }
            //Check file2 not exist 
            else if (StringUtils.isNotEmpty(form.getFilFileFromName2()) && file2.isEmpty()) {
                errorMessage.add(MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0137), form.getFilFileFromName2()));
                return false;
            }
            else if (!checkDuplicateFile(name, errorMessage, lstString, lstFileInDb)) {
                return false;
            }
            else if (!CheckUtil.isNotMaxSizeFileUpload(file2, form.getFileMaxSize())) {
                errorMessage.add(MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0110), name, form.getFileMaxSize()));
                return false;
            }
            //Check virus file
            else if (!isOkCheckVirus(name, file2, errorMessage)) {
                return false;
            }
            else if (StringUtils.isNotEmpty(name)) {
                countFileSelected++;
            }
        }
        if (file3 != null) {
            String name = form.getFilFileFromName3();
            //Check file3 is Input and incorrect type
            if (StringUtils.isNotEmpty(name) && !CommonUtils.checkExtension(name, CommonConstants.FORMAT_FILE_UPLOAD)) {
                errorMessage.add(MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0109), name));
                return false;
            }
            //Check file3 not exist 
            else if (StringUtils.isNotEmpty(form.getFilFileFromName3()) && file3.isEmpty()) {
                errorMessage.add(MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0137), form.getFilFileFromName3()));
                return false;
            }
            //Check duplicate file
            if (!checkDuplicateFile(name, errorMessage, lstString, lstFileInDb)) {
                return false;
            }
            else if (!CheckUtil.isNotMaxSizeFileUpload(file3, form.getFileMaxSize())) {
                errorMessage.add(MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0110), name, form.getFileMaxSize()));
                return false;
            }
            //Check virus file
            if (!isOkCheckVirus(name, file3, errorMessage)) {
                return false;
            }
            else if (StringUtils.isNotEmpty(name)) {
                countFileSelected++;
            }
        }
        
        //Count max file upload
        if (!isNotMaxFileUpload(form, errorMessage, countFileSelected)) {
            return false;
        }
        //If contain error
        if (CollectionUtils.isNotEmpty(errorMessage)) {
            return false;
        }
        return true;
    }
    
    /**
     * Count max file upload
     * @param form ProgressRecordRegistrationForm
     * @param errorMessage errorMessage
     * @param countFileSelected countFileSelected
     * @return
     */
    private boolean isNotMaxFileUpload(ProgressRecordRegistrationForm form, List<String> errorMessage,
            int countFileSelected) {
        if (form.getFileInfos() != null) {
            int countRemainFile = form.getFileInfos().size()
                    - (form.getSelectedFiles() != null ? form.getSelectedFiles().length : 0) + countFileSelected;
            //Check file upload remain with file max upload setting
            if (countRemainFile > Integer.parseInt(form.getFileMaxCount())) {
                errorMessage.add(MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0120), form.getFileMaxCount()));
                return false;
            }
        }
        return true;
    }
    
    /**
     * Check virus attack file
     * @param name String
     * @param file MultipartFile
     * @param errors errorMessage
     * @return
     */
    private boolean isOkCheckVirus(String name, MultipartFile file, List<String> errorMessage) {
        try {
            if (!CheckVirus.validateNoVirus(file.getInputStream())) {
                errorMessage.add(MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0111), name));

                return false;
            }
            return true;
        } catch (IOException e) {
            return false;
        } 
    }
    
    /**
     * Check duplicate upload file
     * @param name String
     * @param errors errorMessage
     * @param lstFileName lstFileInDb
     */
    private boolean checkDuplicateFile(String name,  List<String> errorMessage, List<String> lstFileName, List<FileManagerInfoVo> lstFileInDb) {
        if (StringUtils.isNotEmpty(name)) {
            if (lstFileName.contains(name)) {
                String message = MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0108), name);
                
                errorMessage.add(message);
                return false;
            }
            lstFileName.add(name);
        }
        //Check duplicate file upload in DB
        if (lstFileInDb != null) {
            for (FileManagerInfoVo item : lstFileInDb) {
                if (lstFileName.contains(item.getFileName())) {
                    String message = MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0108), item.getFileName());
                    errorMessage.add(message);
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Reset input file when has error
     * @param form
     */
    private void resetInputFile (ProgressRecordRegistrationForm form) {
    	form.setFilFileFromName1(StringUtils.EMPTY);
    	form.setFilFileFromName2(StringUtils.EMPTY);
    	form.setFilFileFromName3(StringUtils.EMPTY);
    }
}
