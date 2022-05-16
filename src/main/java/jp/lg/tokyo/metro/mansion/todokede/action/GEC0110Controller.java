/*
 * @(#) GEC0110Controller.java
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
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.AllowCity;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CheckUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.FileUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL120DAO;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.exception.UserNotFoundException;
import jp.lg.tokyo.metro.mansion.todokede.form.ProgressRecordForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IProgressRecordBatchRegistrationLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.ZAA0150ErrorVo;

/**
 * @author nbvhoang
 *
 */

@AllowCity
@Controller
public class GEC0110Controller {

    private static final Logger logger = LogManager.getLogger(GEC0110Controller.class);

    private static final String GEC0110 = "GEC0110";
    private static final String EMPTY_STRING = "";
    private static final String PROGRESS_RECORD = "経過記録";
    private static final String UPLOAD = "一括登録";
    private static final String FILE_UPLOAD = "アップロードファイル";
    private static final String MESSAGE_MODEL_NAME = "message";
    private static final String MESSAGE_SUCCESS_MODEL_NAME = "messageSuccess";
    private static final String FILE_MAX_SIZE_MODEL_NAME = "fileMaxSize";
    private static final String ERROR_LIST_1_MODEL_NAME = "errorList1";

    @Autowired
    IProgressRecordBatchRegistrationLogic progressRecordLogic;

    @Autowired
    TBL120DAO tbl120DAO;

    /**
     * display GEC0110
     *
     * @return screenId
     */
    @PostMapping("/GEC0110")
    public String show(Model model) {
        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, currentMethodName));
        String fileMaxSize = SessionUtil.getSystemSettings().get(CommonConstants.GEC0110_FILE_SIZE_MAX);
        model.addAttribute(FILE_MAX_SIZE_MODEL_NAME, fileMaxSize);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
        return GEC0110;
    }

    /**
     * upload Progress
     *
     * @param file MultipartFile
     * @param model Model
     * @param session HttpSession
     * @return String
     * @throws ex BusinessException
     * @throws SystemException when has error when upload
     */
    @PostMapping("/GEC0110/uploadProgress")
    public String uploadProgress(@RequestParam("file") MultipartFile file, Model model, HttpSession session) throws BusinessException {
        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, currentMethodName));
        
        // get max size file upload of GEC0110
        String strMaxSize = SessionUtil.getSystemSettings().get(CommonConstants.GEC0110_FILE_SIZE_MAX);
        
        // チェック（単項目）（サーバ側）
        if (StringUtils.isEmpty(file.getOriginalFilename())) {
            model.addAttribute(FILE_MAX_SIZE_MODEL_NAME, strMaxSize);
            String message = MessageUtil.getMessage(CommonConstants.MS_E0001, FILE_UPLOAD);
            model.addAttribute(MESSAGE_MODEL_NAME, message);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return GEC0110;
        }
        
        // get file name
        Path fileName = Paths.get(file.getOriginalFilename()).getFileName();

        // check file upload
        // ファイルの形式が以下に該当しない場合、エラー。
        if (!CommonUtils.checkExtension(file.getOriginalFilename(), CommonConstants.FORMAT_FILE_IMPORT_CSV)) {
            model.addAttribute(FILE_MAX_SIZE_MODEL_NAME, strMaxSize);
            String message = MessageUtil.getMessage(CommonConstants.MS_E0109, fileName.toString());
            model.addAttribute(MESSAGE_MODEL_NAME, message);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return GEC0110;

        // ファイル存在チェック
        } else if (EMPTY_STRING.equals(file.getOriginalFilename())) {
            model.addAttribute(FILE_MAX_SIZE_MODEL_NAME, strMaxSize);
            String message = MessageUtil.getMessage(CommonConstants.MS_E0137);
            model.addAttribute(MESSAGE_MODEL_NAME, message);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return GEC0110;

        // ファイルにウィルスが含まれているか否かをチェックする。
        } else if (!FileUtil.isOkCheckVirus(file.getOriginalFilename(), file)) {
            model.addAttribute(FILE_MAX_SIZE_MODEL_NAME, strMaxSize);
            String message = MessageUtil.getMessage(CommonConstants.MS_E0111, fileName.toString());
            model.addAttribute(MESSAGE_MODEL_NAME, message);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return GEC0110;

        // 空ファイルチェック
        } else if (file.isEmpty()) {
            model.addAttribute(FILE_MAX_SIZE_MODEL_NAME, strMaxSize);
            String message = MessageUtil.getMessage(CommonConstants.MS_E0130, fileName.toString());
            model.addAttribute(MESSAGE_MODEL_NAME, message);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return GEC0110;

        // ファイルのアップロードサイズの上限チェック
        } else if (!CheckUtil.isNotMaxSizeFileUpload(file, strMaxSize)) {
            model.addAttribute(FILE_MAX_SIZE_MODEL_NAME, strMaxSize);
            String message = MessageUtil.getMessage(CommonConstants.MS_E0110, fileName.toString(), strMaxSize);
            model.addAttribute(MESSAGE_MODEL_NAME, message);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return GEC0110;
        }

        try {
            List<String> lengthOfRowList = new ArrayList<String>();
            // get Data from CSV
            List<ProgressRecordForm> lstProgress = progressRecordLogic.getDataFormCsv(file.getBytes(), lengthOfRowList);

            //get max row of GEC0110 csv
            String strFileCountMax = SessionUtil.getSystemSettings().get(CommonConstants.GEC0110_FILE_COUNT_MAX);

            // Check content CSV
            List<ZAA0150ErrorVo> errorList = progressRecordLogic.uploadCSV(lstProgress, strFileCountMax, lengthOfRowList);

            //if Success
            if (errorList.isEmpty()) {
                model.addAttribute(FILE_MAX_SIZE_MODEL_NAME, strMaxSize);
                String messageSuccess = MessageUtil.getMessage(CommonConstants.MS_I0001, PROGRESS_RECORD, UPLOAD);
                model.addAttribute(MESSAGE_SUCCESS_MODEL_NAME, messageSuccess);
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0020_I, GEC0110));
                return GEC0110;
            } else {
                session.setAttribute(ERROR_LIST_1_MODEL_NAME, errorList);
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
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
    @PostMapping("/GEC0110/checkFile")
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
}
