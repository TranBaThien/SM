/*
 * @(#) SBA0110Controller.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nhvu
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.action;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL120;

import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.PasswordChangeGuide;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SecurityUtil;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.ChangePasswordForm;
import jp.lg.tokyo.metro.mansion.todokede.form.ChangePasswordNotContaintMailForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IChangePasswordLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.ChangePasswordVo;

@Controller
public class SBA0110Controller extends BaseMansionController {

    @Autowired
    IChangePasswordLogic changePasswordLogic;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private static final Logger logger = LogManager.getLogger(SBA0110Controller.class);
    private static final String SBA0110 = "SBA0110";
    private static final String MBA0110 = "MBA0110";
    private static final String GBA0110 = "GBA0110";
    private static final String ROLE_MANSION = "ROLE_MANSION";
    private static final String ROLE_CITY = "ROLE_CITY";
    private static final String ROLE_TOKYO = "ROLE_TOKYO";
    private static final String PASSWORD_CHANGE_GUIDE = "lblPasswordChangeGuide";
    private static final String PWD_PASSWORD_CONFIRM_ERROR = "新しいパスワード（確認）";
    private static final String PWD_PASSWORD_NOW_ERROR = "現在のパスワード";
    private static final String PWD_PASSWORD_ERROR = "新しいパスワード";
    private static final String MAIL_ERROR = "メールアドレス";
    private static final String MAIL_CONFIRM_ERROR = "メールアドレス（確認）";
    private static final String INITIAL_LOGIN_OR_ISSUANCE = "初期パスワードを変更してください。";
    private static final String EXPIRED = "パスワード有効期限が切れています。パスワードを変更してください。";

    /**
     * 
     * @param session HttpSession
     * @param model Model
     * @param request HttpServletRequest
     * @param principal Principal
     * @return String
     * @throws businessException BusinessException
     */
    @RequestMapping(value = "/SBA0110", method = {RequestMethod.POST, RequestMethod.GET})
    public String show(HttpSession session, Model model, HttpServletRequest request, Principal principal) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        show(model, principal, request);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return SBA0110;
    }

    /**
     * 
     * @param changePasswordForm ChangePasswordNotContaintMailForm
     * @param result BindingResult
     * @param principal Principal
     * @param model Model
     * @param request HttpServletRequest
     * @return String
     * @throws BusinessException BusinessException
     */
    @PostMapping("/SBA0110/save")
    public String changePassword(@ModelAttribute("changePassword") @Valid ChangePasswordForm changePasswordForm,
            BindingResult result, Principal principal, Model model, HttpServletRequest request)
            throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        
        return saveChangePassword(changePasswordForm, result, principal, model, request);
    }

    /**
     * Change password not contain email 
     * @param form
     * @param result
     * @param principal
     * @param model
     * @param request
     * @return
     * @throws BusinessException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @PostMapping("/SBA0110/save2")
    public String changePasswordHasNotEmail(@ModelAttribute("changePassword") @Valid ChangePasswordNotContaintMailForm form,
            BindingResult result, Principal principal, Model model, HttpServletRequest request)
            throws BusinessException, IllegalAccessException, InvocationTargetException {
        
        ChangePasswordForm changePasswordForm = new ChangePasswordForm();
        
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, SBA0110));
        
        BeanUtils.copyProperties(changePasswordForm, form);
       
        return saveChangePassword(changePasswordForm, result, principal, model, request);
    }
    
    /**
     * Method save change password data
     * @param changePasswordForm
     * @param result
     * @param principal
     * @param model
     * @param request
     * @return
     * @throws BusinessException
     */
    private String saveChangePassword(ChangePasswordForm changePasswordForm, BindingResult result, Principal principal,
            Model model, HttpServletRequest request) throws BusinessException {
        List<String> errorsMesage = new ArrayList<>();
        // チェック（単項目）
        if (result.hasErrors()) {
            CommonUtils.getErrorsFromBindingResult(result, errorsMesage);
        }
        // 入力チェック（相関） Check correlation
        if (errorsMesage.isEmpty()) {
            isValidateCorrelation(changePasswordForm, errorsMesage, request, principal);
        }
        if (!errorsMesage.isEmpty()) {
            model.addAttribute("errorsMesage", errorsMesage);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return SBA0110;
        }
        if (SecurityUtil.getUserLoggedInInfo().getUserTypeCode() != UserTypeCode.MANSION
                && SecurityUtil.getUserLoggedInInfo().getUserTypeCode() != UserTypeCode.NONE) {
            // ユーザログイン情報更新 TBL120
            updateUserlogin(changePasswordForm);
            model.addAttribute("success", GBA0110);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return SBA0110;
        } else {
            // ユーザログイン情報更新 TBL110
            updateUserlogin(changePasswordForm);
            model.addAttribute("success", MBA0110);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return SBA0110;
        }
    }
    
    /**
     * ユーザログイン情報更新
     * 
     * @param form ChangePasswordNotContaintMailForm
     * @throws BusinessException BusinessException
     */
    private void updateUserlogin(ChangePasswordForm form)
            throws BusinessException {
        if (SecurityUtil.getUserLoggedInInfo().getUserTypeCode() != UserTypeCode.MANSION
                && SecurityUtil.getUserLoggedInInfo().getUserTypeCode() != UserTypeCode.NONE) {
            String loginID = SecurityUtil.getUserLoggedInInfo().getLoginId();
            TBL120Entity entity = changePasswordLogic.findByLoginToTBL120(loginID);
            form.setBiginingPasswordChangeFlag(entity.getBiginingPasswordChangeFlag());
            form.setLoginId(loginID);
            form.setUpdateUserId(SecurityUtil.getUserLoggedInInfo().getUserId());
            changePasswordLogic.changerPasswordTBL120(form);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1060_I, TBL120));
        } else {
            String loginID = SecurityUtil.getUserLoggedInInfo().getLoginId();
            TBL110Entity entity = changePasswordLogic.findByLoginToTBL110(loginID);
            form.setLoginId(loginID);
            form.setUpdateUserId(SecurityUtil.getUserLoggedInInfo().getUserId());
            form.setBiginingPasswordChangeFlag(entity.getBiginingPasswordChangeFlag());
            changePasswordLogic.updateApartmentInfor(form);
        }

    }

    /**
     * チェック（相関）
     * 
     * @param form ChangePasswordNotContaintMailForm
     * @param request HttpServletRequest
     * @return boolean
     * @throws BusinessException BusinessException
     */
    private boolean isValidateCorrelation(ChangePasswordForm form, List<String> errorsMesage,
            HttpServletRequest request, Principal principal) throws BusinessException {
        if (!isValidatepwdPasswordNowCorrelation(form, request, errorsMesage, principal)) {
            return false;
        } else if (!isValidatepwdPasswordConfirmCorrelation(form, errorsMesage)) {
            return false;
        } else if (form.getTxtMail() != null && !isValidatetxtMailConfirmCorrelation(form, errorsMesage)) {
            return false;
        } else if (!isPasswordCorrelation(form, errorsMesage)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 初期表示時
     * 
     * @param model     Model
     * @param principal Principal
     * @param request   HttpServletRequest
     * @throws BusinessException BusinessException
     */
    private void show(Model model, Principal principal, HttpServletRequest request) throws BusinessException {
        HttpSession session = request.getSession();
        PasswordChangeGuide lpasswordChangeGuide = (PasswordChangeGuide) session.getAttribute("PASSWORD_CHANGE_GUIDE");
        if (lpasswordChangeGuide == null) {
            throw new BusinessException();
        }
        String initialLoginOrIssuance = INITIAL_LOGIN_OR_ISSUANCE;
        String expired = EXPIRED;
        int code = lpasswordChangeGuide.getCode();
        ChangePasswordVo vo = new ChangePasswordVo();
        String cd051 = "";
        
        vo.setUserTypeCode(SecurityUtil.getUserLoggedInInfo().getUserTypeCode().getCode());
        
        if (SecurityUtil.getUserLoggedInInfo().getUserTypeCode() != UserTypeCode.MANSION
                && SecurityUtil.getUserLoggedInInfo().getUserTypeCode() != UserTypeCode.NONE) {
            TBL120Entity entity = changePasswordLogic.findByLoginToTBL120(principal.getName());
            //Get login the first flag
            vo.setBiginingPasswordChangeFlag(entity.getBiginingPasswordChangeFlag());
            if (CommonConstants.NUM_1 == code) {
                cd051 = initialLoginOrIssuance;
                model.addAttribute(PASSWORD_CHANGE_GUIDE, cd051);
            } else if (CommonConstants.NUM_2 == code) {
                cd051 = expired;
                model.addAttribute(PASSWORD_CHANGE_GUIDE, cd051);
            } else {
                vo.setLnkReturn("lnkReturn");
            }
        } else {
            TBL110Entity entity = changePasswordLogic.findByLoginToTBL110(principal.getName());
            vo.setApartmentId(entity.getApartmentId());
            //Get login the first flag
            vo.setBiginingPasswordChangeFlag(entity.getBiginingPasswordChangeFlag());
            if (CommonConstants.NUM_1 == code) {
                cd051 = initialLoginOrIssuance;
                model.addAttribute(PASSWORD_CHANGE_GUIDE, cd051);
            } else if (CommonConstants.NUM_2 == code) {
                cd051 = expired;
                vo.setTxtMail(entity.getTbl100().getMailAddress());
                vo.setTxtMailConfirm(entity.getTbl100().getMailAddress());
                model.addAttribute(PASSWORD_CHANGE_GUIDE, cd051);
            } else {
                vo.setTxtMail(entity.getTbl100().getMailAddress());
                vo.setTxtMailConfirm(entity.getTbl100().getMailAddress());
            }
        }
        model.addAttribute("changePassword", vo);
    }
    
    /**
     * チェック（相関） メールアドレス（確認） txtMailConfirm
     * 
     * @param form ChangePasswordNotContaintMailForm
     * @param errorsMessage List
     * @return boolean
     */
    private boolean isPasswordCorrelation(ChangePasswordForm form, List<String> errorsMessage) {
        if (form.getPwdPassword().equals(form.getPwdPasswordNow())) {
            errorsMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0146, PWD_PASSWORD_NOW_ERROR, PWD_PASSWORD_ERROR));
            return false;
        } else {
            return true;
        }
    }

    /**
     * チェック（相関） メールアドレス（確認） txtMailConfirm
     * 
     * @param form ChangePasswordNotContaintMailForm
     * @param errorsMessage List
     * @return boolean
     */
    private boolean isValidatetxtMailConfirmCorrelation(ChangePasswordForm form, List<String> errorsMessage) {
        if (!form.getTxtMail().equals(form.getTxtMailConfirm())) {
            errorsMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0105, MAIL_ERROR, MAIL_CONFIRM_ERROR));
            return false;
        } else {
            return true;
        }
    }

    /**
     * チェック（相関） 新しいパスワード（確認） Validate pwdPasswordConfirm
     * 
     * @param form ChangePasswordNotContaintMailForm
     * @param errorsMessage List
     * @return boolean
     */
    private boolean isValidatepwdPasswordConfirmCorrelation(ChangePasswordForm form, List<String> errorsMessage) {
        if (!form.getPwdPassword().equals(form.getPwdPasswordConfirm())) {
            errorsMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0105, PWD_PASSWORD_ERROR, PWD_PASSWORD_CONFIRM_ERROR));
            return false;
        } else {
            return true;
        }

    }

    /**
     * チェック（相関） 現在のパスワード Validate pwdPasswordNow
     * 
     * @param form ChangePasswordNotContaintMailForm
     * @param request HttpServletRequest
     * @param errorsMessage List
     * @param principal Principal
     * @return boolean
     * @throws BusinessException BusinessException
     */
    private boolean isValidatepwdPasswordNowCorrelation(ChangePasswordForm form,
        HttpServletRequest request, List<String> errorsMessage, Principal principal) throws BusinessException {
        if (request.isUserInRole(ROLE_TOKYO) || request.isUserInRole(ROLE_CITY)) {
            TBL120Entity tbl120Entity = changePasswordLogic.findByLoginToTBL120(principal.getName());
            if (!encoder.matches(form.getPwdPasswordNow(), tbl120Entity.getPassword())) {
                errorsMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0100, PWD_PASSWORD_NOW_ERROR));
                return false;
            } else {
                return true;
            }
        } else if (request.isUserInRole(ROLE_MANSION)) {
            TBL110Entity tbl110Entity = changePasswordLogic.findByLoginToTBL110(principal.getName());
            if (!encoder.matches(form.getPwdPasswordNow(), tbl110Entity.getPassword())) {
                errorsMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0100, PWD_PASSWORD_NOW_ERROR));
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

}
