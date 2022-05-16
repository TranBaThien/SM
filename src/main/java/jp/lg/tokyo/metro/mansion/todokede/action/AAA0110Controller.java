/*
 * @(#) AAA0110Controller.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nbvhoang
 * Create Date: 2019/12/16
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.action;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.SystemException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.AllowMaintenance;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CheckUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.FileUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.exception.UserNotFoundException;
import jp.lg.tokyo.metro.mansion.todokede.form.MansionInfoUploadForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IUploadMansionLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.ZAA0150ErrorVo;

@AllowMaintenance
@Controller
public class AAA0110Controller {
    
    private static final Logger logger = LogManager.getLogger(AAA0110Controller.class);
    
    private static final String AAA0110 = "AAA0110";
    private static final String FILE_MAX_SIZE_MODEL_NAME = "fileMaxSize";
    private static final String EMPTY_STRING = "";
    private static final String MESSAGE_MODEL_NAME = "message";
    private static final String MESSAGE_SUCCESS_MODEL_NAME = "messageSuccess";
    private static final String APARTMENT_INFOMATION = "マンション情報";
    private static final String UPLOAD = "アップロード";
    private static final String FILE_UPLOAD = "アップロードファイル";
    private static final String ERROR_LIST_1_MODEL_NAME = "errorList1";

    @Autowired
    private IUploadMansionLogic uploadMansionLogic;
    
    
    /**
     * display.
     * 
     * @param model Model
     * @return screen AAA0110
     */
    @PostMapping("/AAA0110")
    public String show(Model model) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        String fileMaxSize = SessionUtil.getSystemSettings().get(CommonConstants.AAA0110_FILE_SIZE_MAX);
        model.addAttribute(FILE_MAX_SIZE_MODEL_NAME, fileMaxSize);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return AAA0110;
       
    }

    /**
     * Upload Mansion.
     * 
     * @param file MultipartFile
     * @param model Model
     * @param session HttpSession
     * @return String
     * @throws BusinessException BusinessException
     * @throws SystemException when has error when upload
     */
    @PostMapping("/AAA0110/uploadMansion")
    public String uploadMansion(@RequestParam("file") MultipartFile file, Model model, HttpSession session) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        try {
            // get max size file upload of AAA0110
            String maxSize = SessionUtil.getSystemSettings().get(CommonConstants.AAA0110_FILE_SIZE_MAX);
            if (validateUploadFile(file, model, maxSize)) return AAA0110;

            List<String> lengthOfRowList = new ArrayList<>();
            //get Data from CSV
            List<MansionInfoUploadForm> lstMansion = uploadMansionLogic.getDataFormCsv(file.getBytes(), lengthOfRowList);
            
            //get max row of AAA0110 csv
            String strFileCountMax = SessionUtil.getSystemSettings().get(CommonConstants.AAA0110_FILE_COUNT_MAX);
            
            //Upload csv
            List<ZAA0150ErrorVo> errorList = uploadMansionLogic.uploadCSV(lstMansion, strFileCountMax, lengthOfRowList);
            
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            //if Success
            if (errorList.isEmpty()) {
                model.addAttribute(FILE_MAX_SIZE_MODEL_NAME, maxSize);
                String messageSuccess = MessageUtil.getMessage(CommonConstants.MS_I0001, APARTMENT_INFOMATION, UPLOAD);
                model.addAttribute(MESSAGE_SUCCESS_MODEL_NAME, messageSuccess);
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
                return AAA0110;
            } else {
                session.setAttribute(ERROR_LIST_1_MODEL_NAME, errorList);
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
                return "redirect:/ZAA0150";
            }

        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(ex.getMessage());
        }
    }

    /**
     * Check file not found
     * @param file MultipartFile
     * @return
     */
    @PostMapping("/AAA0110/checkFile")
    public ResponseEntity<?> checkFile(@RequestParam("file") MultipartFile file) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        try {
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return new ResponseEntity<>(HttpStatus.ACCEPTED);

        } catch (UserNotFoundException ex) {
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private boolean validateUploadFile(MultipartFile file, Model model, String maxSize) {
        // チェック（単項目）（サーバ側）
        if (StringUtils.isEmpty(file.getOriginalFilename())) {
            model.addAttribute(FILE_MAX_SIZE_MODEL_NAME, maxSize);
            String message = MessageUtil.getMessage(CommonConstants.MS_E0001, FILE_UPLOAD);
            model.addAttribute(MESSAGE_MODEL_NAME, message);
            return true;
        }

        // get file name for work on IE
        Path fileName = Paths.get(file.getOriginalFilename()).getFileName();

        // check file upload
        // ファイルの形式が以下に該当しない場合、エラー。
        if (!CommonUtils.checkExtension(file.getOriginalFilename(), CommonConstants.FORMAT_FILE_IMPORT_CSV)) {
            model.addAttribute(FILE_MAX_SIZE_MODEL_NAME, maxSize);
            String message = MessageUtil.getMessage(CommonConstants.MS_E0109, fileName.toString());
            model.addAttribute(MESSAGE_MODEL_NAME, message);
            return true;

            // ファイル存在チェック
        } else if (EMPTY_STRING.equals(file.getOriginalFilename())) {
            model.addAttribute(FILE_MAX_SIZE_MODEL_NAME, maxSize);
            String message = MessageUtil.getMessage(CommonConstants.MS_E0137);
            model.addAttribute(MESSAGE_MODEL_NAME, message);
            return true;

            // ファイルにウィルスが含まれているか否かをチェックする。
        } else if (!FileUtil.isOkCheckVirus(file.getOriginalFilename(), file)) {
            model.addAttribute(FILE_MAX_SIZE_MODEL_NAME, maxSize);
            String message = MessageUtil.getMessage(CommonConstants.MS_E0111, fileName.toString());
            model.addAttribute(MESSAGE_MODEL_NAME, message);
            return true;

            // 空ファイルチェック
        } else if (file.isEmpty()) {
            model.addAttribute(FILE_MAX_SIZE_MODEL_NAME, maxSize);
            String message = MessageUtil.getMessage(CommonConstants.MS_E0130, fileName.toString());
            model.addAttribute(MESSAGE_MODEL_NAME, message);
            return true;

            // ファイルのアップロードサイズの上限チェック
        } else if (!CheckUtil.isNotMaxSizeFileUpload(file, maxSize)) {
            model.addAttribute(FILE_MAX_SIZE_MODEL_NAME, maxSize);
            String message = MessageUtil.getMessage(CommonConstants.MS_E0110, fileName.toString(), maxSize);
            model.addAttribute(MESSAGE_MODEL_NAME, message);
            return true;
        }
        return false;
    }

}
