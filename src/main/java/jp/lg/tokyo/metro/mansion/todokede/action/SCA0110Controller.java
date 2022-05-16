/*
 * @(#) SCA0110Controller.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/11/27
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.action;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CheckUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.ReissuePasswordForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.ICaptchaLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.IPasswordManagementLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.GovernmentStaffInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ML030Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ML035Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ManagementAssociationApplicationVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ReissuePasswordAfterSaveVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ReissuePasswordVo;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil.getMessage;

@Controller
@RequestMapping("/SCA0110")
public class SCA0110Controller extends BaseMansionController {

    private static final Logger logger = LogManager.getLogger(SCA0110Controller.class);
    private static final String SCA0110 = "SCA0110";
    private static final String SCA0110_CAPTCHA = "/captcha.jpg";
    private static final String SCA0110_SUBMIT = "/save";
    private static final String PASSWORD_FIELD_NAME = "パスワード";
    private static final String INFORMATION_ENTERED = "入力した情報";
    private static final String REISSUE = "再発行";
    private static final String REISSUE_PASSWORD_MODEL_NAME = "reissuePassword";
    private static final String ERRORS_MESSAGE_MODEL_NAME = "errorMessageList";
    private static final String SUCCESS_MESSAGE_MODEL_NAME = "messageSuccess";
    private static final String MSG_UPDATE_USER_INFOR_FAILED = "Failed to update user information";
    private static final String MSG_GET_PREVIOUS_SCREEN_ID_FAILED = "Could not find previous screen id";
    private static final String MSG_PREVIOUS_SCREEN_ID_INVALID = "Previous screen must be MAA0110 or GAA0110";
    private static final String ML035 = "ML035";
    private static final String ML030 = "ML030";

    private static final String TXT_LOGIN_ID_OR_TXT_MAIL_ADDRESS = "ログインIDまたはメールアドレス";
    public static final int MAX_LENGTH_TXT_LOGIN_ID = 8;
    public static final int MAX_LENGTH_TXT_MAIL = 120;

    @Autowired
    private DefaultKaptcha producer;

    @Autowired
    private ICaptchaLogic captchaLogic;

    @Autowired
    private IPasswordManagementLogic passwordManagementLogic;

    /**
     * @param model Model
     * @param httpServletRequest HttpServletRequest
     * @return
     * @throws BusinessException 
     */
    @PostMapping
    public String show(Model model, HttpServletRequest httpServletRequest) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        try {
            ReissuePasswordVo reissuePasswordVo = new ReissuePasswordVo();
            String previousScreenId = SessionUtil.getPreviousScreenId();
            if (CommonConstants.SCREEN_ID_MAA0110.equals(previousScreenId)) {
                // セッション情報の画面IDはMAA0110（管理組合）の場合は
                // セッション情報のAPARTMENT_ONETIME_PASSWORD_PERIODを設定する
                reissuePasswordVo.setLblTime(SessionUtil.getSystemSettingByKey(CommonConstants.APARTMENT_ONETIME_PASSWORD_PERIOD)); 
            } else {
                // セッション情報の画面IDはGAA0110（区市町村）の場合は
                // セッション情報のCITY_ONETIME_PASSWORD_PERIODを設定する
                reissuePasswordVo.setLblTime(SessionUtil.getSystemSettingByKey(CommonConstants.CITY_ONETIME_PASSWORD_PERIOD));
            }
            model.addAttribute(REISSUE_PASSWORD_MODEL_NAME, reissuePasswordVo);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new BusinessException(ex.getMessage());
        }
        logger.info(getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return SCA0110;
    }

    /**
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param sessionId String
     * @throws IOException when has error when captcha
     */
    @GetMapping(SCA0110_CAPTCHA)
    public void captcha(HttpServletRequest request, HttpServletResponse response, String sessionId) throws IOException {
        logger.info(getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        String text = producer.createText();

        if (StringUtils.isBlank(sessionId)) {
            sessionId = request.getRequestedSessionId();
        }

        captchaLogic.save(sessionId, text);

        response.setContentType("image/jpeg");
        response.addHeader("sessionId", sessionId);
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        ServletOutputStream out = response.getOutputStream();
        BufferedImage image = producer.createImage(text);
        ImageIO.write(image, "jpg", out);
        logger.info(getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
    }

    /**
     * @param form ReissuePasswordForm
     * @param result BindingResult
     * @param errors Errors
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model Model
     * @return String
     * @throws BusinessException when has error when update
     */
    @PostMapping(SCA0110_SUBMIT)
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String save(@ModelAttribute(REISSUE_PASSWORD_MODEL_NAME) @Valid ReissuePasswordForm form,
            BindingResult result, Errors errors, HttpServletRequest request, HttpServletResponse response, Model model) throws BusinessException {
        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info(getMessage(CommonConstants.LOG_LG1010_I, currentMethodName));
        try {

            List<String> errorsMesage = new ArrayList<>();

            // 1. チェック（単項目）
            if (result.hasErrors()) {
                CommonUtils.getErrorsFromBindingResult(result, errorsMesage);
                model.addAttribute(ERRORS_MESSAGE_MODEL_NAME, errorsMesage);
                logger.info(getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
                return SCA0110;
            }

            // 3. ユーザ情報取得
            // 4. チェック（相関）
            if (!checkCorrelation(form, errorsMesage, request)) {
                model.addAttribute(ERRORS_MESSAGE_MODEL_NAME, errorsMesage);
                logger.info(getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
                return SCA0110;
            }

            final String previousScreen = form.getPreviousUrl();
            final String loginId = form.getTxtLoginId();

            // 5. DB更新
            ReissuePasswordAfterSaveVo afterSaveVo = null;
            // 前画面ID=管理組合ログイン（MAA0110）の場合は
            if (previousScreen.contains(CommonConstants.SCREEN_ID_GAA0110)) {
                afterSaveVo = passwordManagementLogic.saveDataToTbl120(loginId);
            } else { // 前画面ID=都区市町村ログイン（GAA0110）の場合は
                afterSaveVo = passwordManagementLogic.saveDataToTbl110(loginId);
            }
            if (afterSaveVo == null) {
                throw new BusinessException(MSG_UPDATE_USER_INFOR_FAILED);
            }

            // 5. メール送信
            if (previousScreen.contains(CommonConstants.SCREEN_ID_GAA0110)) {
                ML035Vo ml035Vo = passwordManagementLogic.getML035Template(afterSaveVo);
                passwordManagementLogic.sendML035(ml035Vo);
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1110_I, ML035, afterSaveVo.getContactMailAddress()));
            } else {
                ML030Vo ml030Vo = passwordManagementLogic.getML030Template(afterSaveVo);
                passwordManagementLogic.sendML030(ml030Vo);
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1110_I, ML030, afterSaveVo.getContactMailAddress()));
            }

            // 6. 完了メッセージ
            // 7. 画面遷移
            // 完了メッセージ（I0001）を表示する。
            model.addAttribute(SUCCESS_MESSAGE_MODEL_NAME, MessageUtil.getMessage(CommonConstants.MS_I0001, PASSWORD_FIELD_NAME, REISSUE));
            logger.info(getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));

            return SCA0110;
        } catch (Exception e) {

            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, e.getMessage()));
            throw new BusinessException(e, e.getMessage());
        }
    }

    /**
     * Check correlation SCA0110.
     * @param form ReissuePasswordForm
     * @param errorsMessage {@link List} of {@link String}
     * @param request HttpServletRequest
     * @throws BusinessException when has exception when check correlation
     * @author tqvu1
     */
    private boolean checkCorrelation(ReissuePasswordForm form, List<String> errorsMessage, HttpServletRequest request) throws BusinessException {

        final String catpchaText = form.getCatpchaText();
        final String loginId = form.getTxtLoginId();
        final String mailAddress = form.getTxtMail();

        // 【ログインID桁数チェック】：
        // ログインIDの桁数<>8の場合、エラー
        if (StringUtils.trim(loginId).length() != MAX_LENGTH_TXT_LOGIN_ID) {
            errorsMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0107, TXT_LOGIN_ID_OR_TXT_MAIL_ADDRESS));
            return false;
        }

        // 【ログインID文字属性チェック】：
        // ログインIDが半角英数字ではない場合、エラー
        if (!CommonUtils.isAlphaNumeric(loginId)) {
            errorsMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0107, TXT_LOGIN_ID_OR_TXT_MAIL_ADDRESS));
            return false;
        }

        // 【メールアドレス桁数チェック】：
        // メールアドレスの桁数>120の場合、エラー
        if (StringUtils.trim(mailAddress).length() > MAX_LENGTH_TXT_MAIL) {
            errorsMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0107, TXT_LOGIN_ID_OR_TXT_MAIL_ADDRESS));
            return false;
        }

        // 【ログインID文字属性チェック】：
        // ログインIDが半角英数字ではない場合、エラー
        if (!CommonUtils.isAlphaNumericForMail(mailAddress)) {
            errorsMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0107, TXT_LOGIN_ID_OR_TXT_MAIL_ADDRESS));
            return false;
        }

        // 【メールアドレス形式チェック】：
        // メールアドレスの形式ではない場合は、エラー
        if (!CheckUtil.isMailAddress(mailAddress)) {
            errorsMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0107, TXT_LOGIN_ID_OR_TXT_MAIL_ADDRESS));
            return false;
        }

        // パズル認証が通過しない場合は、エラー
        if (!captchaLogic.match(request.getRequestedSessionId(), catpchaText)) {
            errorsMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0139));
            return false;
        } else {

            final String previousScreen = form.getPreviousUrl();
            GovernmentStaffInfoVo gsInfor = null;
            ManagementAssociationApplicationVo maInfor = null;

            String availability = CodeUtil.getValue(CommonConstants.CD024, CommonConstants.CD024_POSSIBLE);
            String validityFlag = CodeUtil.getValue(CommonConstants.CD023, CommonConstants.CD023_VALID);

            if (CheckUtil.isBlank(previousScreen)) {
                throw new BusinessException(MSG_GET_PREVIOUS_SCREEN_ID_FAILED);
            }

            // 3. ユーザ情報取得
            // セッション情報の画面IDはGAA0110（区市町村）の場合
            if (previousScreen.contains(CommonConstants.SCREEN_ID_GAA0110)) {
                gsInfor = passwordManagementLogic.getGovernmentStaffUserLoginInfo(loginId, mailAddress, availability);
                // ユーザ情報取得できないの場合は、エラー
                if (gsInfor == null) {
                    errorsMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0100, INFORMATION_ENTERED));
                    return false;
                } else if (CodeUtil.getValue(CommonConstants.CD026, CommonConstants.CD026_LOGGING_IN).equals(gsInfor.getLoginStatusFlag())) {
                    // ユーザ情報取得したの場合は、ログイン中フラグはログイン中（CD026）の場合は、エラー
                    errorsMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0114, REISSUE));
                    return false;
                }
            } else if (previousScreen.contains(CommonConstants.SCREEN_ID_MAA0110)) {
                // セッション情報の画面IDはMAA0110（管理組合）の場合
                maInfor = passwordManagementLogic.getManagementAssociationInfo(loginId, mailAddress, availability, validityFlag);
                // ユーザ情報取得できないの場合は、エラー
                if (maInfor == null) {
                    errorsMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0100, INFORMATION_ENTERED));
                    return false;
                } else if (CodeUtil.getValue(CommonConstants.CD026, CommonConstants.CD026_LOGGING_IN).equals(maInfor.getLoginStatusFlag())) {
                    // ユーザ情報取得したの場合は、ログイン中フラグはログイン中（CD026）の場合は、エラー
                    errorsMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0114, REISSUE));
                    return false;
                }
            } else {
                throw new BusinessException(MSG_PREVIOUS_SCREEN_ID_INVALID);
            }
        }
        return true;
    }

}
