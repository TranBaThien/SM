/*
 * @(#)GLA0110Controller.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author: hbthinh
 * Create Date: Dec 2, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.action;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.SystemException;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.AllowTokyo;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.AccountLockFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.ChangedPasswordFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.DeleteFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.LoginStatusFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SecurityUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.ReissueUserManagementForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IReissueUserManagementLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.MansionInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ReissueUserManagementVo;


/**
 * @author hbthinh
 *
 */
@Controller
@AllowTokyo
public class GLA0110Controller extends BaseMansionController {
    private static final Logger logger = LogManager.getLogger(GLA0110Controller.class);
    private static final String GLA0110 = "GLA0110";
    private static final String GLA0110_SHOW = "/GLA0110/show";
    private static final String GLA0110_SAVE = "/GLA0110/save";

    private static final String MANAGEMENT_UNION_USER_LOGIN_ID = "管理組合ユーザログインID再発行";
    private static final String REISSUE = "再発行";
    private static final String UPDATE_FAILED = "GLA0110の更新に失敗しました。";

    private static final String GLA0110_PWD_PASSWORD = "パスワード";
    private static final String GLA0110_PWD_PASSWORD_CONFIRM = "パスワード（確認）";
    private static final String MESSAGE_FORM_NULL = "MansionInfoVo or ReissueUserManagementVo is null";
    

    @Autowired
    public IReissueUserManagementLogic reissueUserManagementLogic;

    @Autowired
    public BCryptPasswordEncoder encoder;
    
    

    /**
     * @param model Model
     * @param apartmentId String
     * @return
     * @ when execute System Exception
     * @throws SystemException
     * @ when execute BusinessException
     * @throws BusinessException Exception
     */    
    @PostMapping(value = GLA0110_SHOW)
    public String show(Model model, String apartmentId) throws BusinessException, SystemException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I,
                Thread.currentThread().getStackTrace()[1].getMethodName()));
        
        MansionInfoVo mansionInfoVo = getMansionInfo(apartmentId);
        ReissueUserManagementVo reissueUserManagementVo = reissueUserManagementLogic
                .getUserLoginInformation(apartmentId);
        if (mansionInfoVo == null || reissueUserManagementVo == null || apartmentId == null) {
            throw new BusinessException(MESSAGE_FORM_NULL);
        }
        model.addAttribute("mansionInfo", mansionInfoVo);
        model.addAttribute("reissueUserManagementInfo", reissueUserManagementVo);

        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I,
                Thread.currentThread().getStackTrace()[1].getMethodName()));
        return GLA0110;
    }

 

    /**
     * @param form ReissueUserManagementForm
     * @param errors Errors
     * @param model Model
     * @param result BindingResult
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return 
     * @ when execute System Exception
     * @throws SystemException
     * @ when execute BusinessException
     * @throws BusinessException Exception
     */
    // 再発行ボタンをクリックした場合
    @PostMapping(value = GLA0110_SAVE)
    public String save(@ModelAttribute("reissueUserManagementInfo") @Valid ReissueUserManagementForm form,
                       Model model,
                       BindingResult result) throws SystemException, BusinessException {
        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, currentMethodName));
        String apartmentId = form.getApartmentId();
        List<String> messageError = new ArrayList<String>();
        // ユーザ情報取得/Get user info from table TBL110
        ReissueUserManagementVo reissueUserManagementVo = reissueUserManagementLogic
                .getUserLoginInformationByLoginId(form.getLblLoginidNow());
        
        String turnBackGJA0120 = "false";
        // Check single item/入力チェック（単項目）
        if (result.hasErrors()) {
            CommonUtils.getErrorsFromBindingResult(result, messageError);
        }

        // Check correlation/入力チェック（相関）
        if (messageError.isEmpty()) {
            checkCorrelation(form, reissueUserManagementVo, messageError);
        }
        
        ReissueUserManagementVo vo = reissueUserManagementLogic.getUserLoginInformation(apartmentId);
        vo.setPwdPassword(form.getPwdPassword());
        vo.setPwdPasswordConfirm(form.getPwdPasswordConfirm());
        MansionInfoVo mansionInfoVo = getMansionInfo(apartmentId);
        // Check Exclusive/排他チェック
        
        if (!messageError.isEmpty()) {            
            model.addAttribute("turnBackGJA0120", turnBackGJA0120);
            model.addAttribute("mansionInfo", mansionInfoVo);
            model.addAttribute("reissueUserManagementInfo", vo);
            model.addAttribute("messageError", messageError);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return GLA0110;
        }
        
        if (!checkExclusive(form, reissueUserManagementVo)) {                
            turnBackGJA0120 = "true";            
            model.addAttribute("turnBackGJA0120", turnBackGJA0120);
            model.addAttribute("mansionInfo", mansionInfoVo);
            model.addAttribute("reissueUserManagementInfo", vo);
            model.addAttribute("messageError", null);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return GLA0110;
        }  

        
        // prepare data
        prepareData(reissueUserManagementVo, form);
        
        // update user info/ユーザ情報更新
        if (reissueUserManagementLogic.updateUserLogin(apartmentId, reissueUserManagementVo)) {            
            String success = String.format(MessageUtil.getMessage(CommonConstants.MS_I0001), MANAGEMENT_UNION_USER_LOGIN_ID, REISSUE);
            model.addAttribute("success", success);

            model.addAttribute("mansionInfo", mansionInfoVo);
            model.addAttribute("reissueUserManagementInfo", vo);

            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return GLA0110;

        } else {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, UPDATE_FAILED));
            throw new BusinessException(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, UPDATE_FAILED));
        }

    }


    /**
     * checkExclusive
     * 
     * @param form ReissueUserManagementForm
     * @param vo ReissueUserManagementVo
     * @param List<String> errorsMesage
     * @return 
     */
    private void checkCorrelation(ReissueUserManagementForm form, 
                                  ReissueUserManagementVo vo,
                                  List<String> errorsMesage) {
        final String pwdPassword = form.getPwdPassword();
        final String pwdPasswordConfirm = form.getPwdPasswordConfirm();

        // パスワード（pwdPassword）と一致しない場合、エラー
        if (!pwdPassword.equals(pwdPasswordConfirm)) {
            errorsMesage.add(MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0105),
                    GLA0110_PWD_PASSWORD, GLA0110_PWD_PASSWORD_CONFIRM));
        }

        // 【ログインID重複チェック】 ユーザログイン（管理組合）（ＴＢＬ110）に入力したログインIDと一致するレコードが存在する場合、エラー
        if (vo != null && LoginStatusFlag.LOGGED_IN.getFlag().equals(vo.getLoginStatusFlag())) {
            // 【ログイン中チェック】 ユーザログイン（管理組合）（ＴＢＬ110）.ログインフラグがログイン中（CD026）場合、エラー
            errorsMesage.add(MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0114), MANAGEMENT_UNION_USER_LOGIN_ID));
        }
    }


    /**
     * checkExclusive
     * 
     * @param form ReissueUserManagementForm
     * @param vo ReissueUserManagementVo
     * @return boolean
     */
    private boolean checkExclusive(ReissueUserManagementForm form, ReissueUserManagementVo vo) {
        // 初期表示と同じ条件で ユーザログイン（管理組合）テーブル（TBL110）から更新日時を取得する。更新日時が一致しない場合は、エラーメッセージ        
        if (!form.getUpdateDateTimeInitial().toString().equals(vo.getUpdateDateTimeInitial())) {
            return false;
        }
        return true;
    }

    /**
     * Set data to ReissueUserManagementVo before execute update
     * 
     * @param vo ReissueUserManagementVo
     * @param form ReissueUserManagementForm
     * @return vo ReissueUserManagementVo
     */
    private ReissueUserManagementVo prepareData(ReissueUserManagementVo vo, ReissueUserManagementForm form) {
        // Get format passwordPeriodNow
        Map<String, String> getSystemSettings = SessionUtil.getSystemSettings();
        LocalDateTime passwordPeriodNow = LocalDateTime.now();
        LocalDateTime passwordPeriod = passwordPeriodNow
                .plusDays(Integer.parseInt(getSystemSettings.get(CommonConstants.ST_G_PASSWORD_VALID_PERIOD)));

        if (vo != null) {
            /* Set data for ReissueUserManagementVo */
            // ハッシュ化して設定する
            vo.setPwdPassword(encoder.encode(form.getPwdPassword()));
            // システム日時+セッション情報.M_PASSWORD_VALID_PERIODを設定する
            vo.setPasswordPeriod(passwordPeriod);
            // "0"を設定する
            vo.setLoginErrorCount(0);
            // アンロック（CD038）を設定する
            vo.setAcountLockFlag(AccountLockFlag.UNLOCK.getFlag());
            // NULLを設定する
            vo.setAccountLockTime(null);
            // NULLを設定する
            vo.setLblLastTimeLoginTime(null);
            // （未変更）を設定する（「コード定義書（CD025）」参照）
            vo.setBindingPasswordChangeFlag(ChangedPasswordFlag.UNCHANGED.getFlag());
            // （未削除）を設定する（「コード定義書（CD001）」参照）
            vo.setDeleteFlag(DeleteFlag.NOT_DELETE.getFlag());
            // セッション情報のユーザIDをそのまま設定する
            vo.setUpdateUserId(SecurityUtil.getUserLoggedInInfo().getUserId());
            // システム日時を設定する
            vo.setUpdateDateTime(DateTimeUtil.getCurrentSystemDateTime());
        }
        return vo;
    }
}
