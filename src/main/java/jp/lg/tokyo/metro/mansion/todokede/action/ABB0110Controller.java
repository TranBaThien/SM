/*
 * @(#)ABB0110Controller.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author ptluan
 * Create Date: 2019/12/17
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.action;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.BLANK;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD026;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD026_LOGGING_IN;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD027;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.LOG_LG1010_I;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.LOG_LG1020_I;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.MS_E0011;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.MS_E0105;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.MS_E0113;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.MS_E0114;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.MS_E0128;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.MS_E0138;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.MS_E0140;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.MS_I0001;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.SPACE_FULL_SIZE;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.AllowMaintenance;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.UserCityForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IUserCityManagementLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.UserCityVo;

@AllowMaintenance
@Controller
public class ABB0110Controller  extends BaseMansionController {
    private static final String ABB0110 = "ABB0110";
    private static final String USERNAME_ONE = "姓";
    private static final String USERNAME_TWO = "名";
    private static final String USERNAMEPHONETIC_ONE = "姓フリガナ";
    private static final String USERNAMEPHONETIC_TWO = "名フリガナ";
    private static final String PASSWORD = "パスワード";
    private static final String PASSWORDCONFIRM = "パスワード（確認）";
    private static final String MAIL = "メールアドレス";
    private static final String MAILCONFIRM = "メールアドレス（確認）";
    private static final String EDIT = "変更";
    private static final String CITY_CODEONE = "ユーザ種別が区市町村";
    private static final String CITY_CODETWO = "区市町村";
    private static final String ID_LOGIN = "入力したログインID";
    private static final int MAXPHONE = 13;
    private static final String PHONE_ONE = "電話番号";
    private static final String MESSAGE_PARAM_ONE = "ユーザ情報";
    private static final String MESSAGE_PARAM_TWO = "登録";
    private static final String TRUE = "true";
    private static final String MESSAGE_ERROR = "errorMessage";
    private static final String MESSAGE_SUCCESS = "messageSuccess";
    private static final String RIDIRECT_TO_ABA0110 = "redirectToABA";
    private static final String USER_CITY_VO = "UserCityVo";
    private static final String SHOW_PATH = "/ABB0110/show";
    private static final String EDIT_PATH = "/ABB0110/edit";
    private static final String SAVE_PATH = "/ABB0110/save";
    private static final String MESSAGE_USERID_IS_NULL = "UserId is null";
    private static final String MESSAGE_USERCITYFORM_IS_NULL = "UserCityForm is null";
    private static final String MESSAGE_GOVERNMENTINFO_IS_NULL = "Government info is null";

    private static final Logger logger = LogManager.getLogger(ABB0110Controller.class);

    @Autowired
    IUserCityManagementLogic userCityManagementLogic;

    /**
     * Call Show Add user info
     * @param model Model
     * @return view
     * @throws BusinessException when has error when show
     */
    @PostMapping(value = {SHOW_PATH})
    public String show(Model model) throws BusinessException {
        logger.info(MessageUtil.getMessage(LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        try {
            UserCityVo vo = userCityManagementLogic.getDataShow();
            vo.setRdoUserTypeList(copyListCodeInfos(CodeUtil.getList(CD027)));
            model.addAttribute(USER_CITY_VO, vo);
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(ex.getMessage());
        }
        logger.info(MessageUtil.getMessage(LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return ABB0110;
    }

    /**
     * Call Show Edit user info
     * @param userId String
     * @param model Model
     * @return view
     * @throws BusinessException when has error when edit
     */
    @PostMapping(value = {EDIT_PATH})
    public String edit(String userId, Model model) throws BusinessException {
        logger.info(MessageUtil.getMessage(LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        if (userId == null) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, MESSAGE_USERID_IS_NULL));
            throw new BusinessException(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, MESSAGE_USERID_IS_NULL));
        }
        try {
            UserCityVo vo = userCityManagementLogic.getUserInfor(userId);
            vo.setRdoUserTypeList(copyListCodeInfos(CodeUtil.getList(CD027)));
            model.addAttribute(USER_CITY_VO, vo);
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(ex.getMessage());
        }
        logger.info(MessageUtil.getMessage(LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return ABB0110;
    }

    /**
     * 2．ボタン、リンク等押下時のイベント処理
     * @param form UserCityForm
     * @param result BindingResult
     * @param request WebRequest
     * @param errors Errors
     * @return ModelAndView
     * @throws BusinessException when has error when save
     */
    @PostMapping(SAVE_PATH)
    public ModelAndView save(@ModelAttribute(USER_CITY_VO) @Valid UserCityForm form, BindingResult result,
            WebRequest request, Errors errors) throws BusinessException {
        logger.info(MessageUtil.getMessage(LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        if (form == null) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, MESSAGE_USERCITYFORM_IS_NULL));
            throw new BusinessException(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, MESSAGE_USERCITYFORM_IS_NULL));
        }
        ModelAndView md = new ModelAndView(ABB0110, USER_CITY_VO, form);
        List<String> errorsMesage = new ArrayList<String>();
        /* NO2 入力チェック（単項目） */
        if (result.hasErrors()) {
            CommonUtils.getErrorsFromBindingResult(result, errorsMesage);
        }
        /* Is add User*/
        if (form.getUserId() == null || (form.getUserId() != null && form.getUserId().isBlank())) {
            /* NO3 チェック（相関） */
            if (errorsMesage.isEmpty()) {
                checkCorrelation(form, errorsMesage, null);
            }
        } else {
            /* get GovernmentInfo */
            TBL120Entity entity = userCityManagementLogic.getGovernmentInfo(form.getUserId());
            if (null == entity) {
                logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, MESSAGE_GOVERNMENTINFO_IS_NULL));
                throw new BusinessException(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, MESSAGE_GOVERNMENTINFO_IS_NULL));
            }
            /* NO3 チェック（相関） */
            if (errorsMesage.isEmpty()) {
                checkCorrelation(form, errorsMesage, entity);
            }
            /*NO5 排他チェック */
            if (errorsMesage.isEmpty() && form.getUserId() != null && !form.getUserId().isBlank()) {
                LocalDateTime time = entity.getUpdateDatetime();
                if (time != null && !time.toString().equals(form.getUpdateTime())) {
                    md.addObject(MESSAGE_SUCCESS, MessageUtil.getMessage(MS_E0113));
                    md.addObject(RIDIRECT_TO_ABA0110, TRUE);
                    return md;
                }
            }
        }
        if (errorsMesage.isEmpty()) {
            try {
                /* NO6 ユーザ情報登録*/
                /* NO7 ユーザ情報更新*/
                userCityManagementLogic.saveUserInfor(form);
                logger.info(MessageUtil.getMessage(LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
                md.addObject(MESSAGE_SUCCESS, MessageFormat.format(MessageUtil.getMessage(MS_I0001), MESSAGE_PARAM_ONE, MESSAGE_PARAM_TWO));
                md.addObject(RIDIRECT_TO_ABA0110, TRUE);
            } catch (BusinessException ex) {
                logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
                throw new BusinessException(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            }
        }
        if (!errorsMesage.isEmpty()) {
            md.addObject(MESSAGE_ERROR, errorsMesage);
        }
        logger.info(MessageUtil.getMessage(LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return md;
    }

    /**
     * 入力チェック（相関）
     *
     * @param form UserCityForm
     * @param errorsMesage {@link List} of {@link String}
     * @param entity TBL120Entity
     * @return
     */
    private void checkCorrelation(UserCityForm form, List<String> errorsMesage, TBL120Entity entity) {
        /* 区市町村 */
        if ((UserTypeCode.CITY.getCode() + BLANK).equals(form.getRdoUserType()) && BLANK.equals(form.getLstCity())) {
            errorsMesage.add(MessageFormat.format(MessageUtil.getMessage(MS_E0138),  CITY_CODEONE, CITY_CODETWO));
        }

        /* 氏名 */
        if (!form.getTxtUserName().trim().contains(SPACE_FULL_SIZE)) {
            errorsMesage.add(MessageFormat.format(MessageUtil.getMessage(MS_E0140), USERNAME_ONE, USERNAME_TWO));
        }

        /* 氏名フリガナ */
        if (!form.getTxtUserNamePhonetic().trim().contains(SPACE_FULL_SIZE)) {
            errorsMesage.add(MessageFormat.format(MessageUtil.getMessage(MS_E0140), USERNAMEPHONETIC_ONE, USERNAMEPHONETIC_TWO));
        }

        /* メールアドレス（確認） */
        if (!form.getTxtMail().trim().equals(form.getTxtMailConfirm().trim())) {
            errorsMesage.add(MessageFormat.format(MessageUtil.getMessage(MS_E0105),  MAIL, MAILCONFIRM));
        }

        /* 電話番号 */
        if ((form.getTxtPhonenumber1().length() + form.getTxtPhonenumber2().length() + form.getTxtPhonenumber3().length() + 2) > MAXPHONE) {
            errorsMesage.add(MessageFormat.format(MessageUtil.getMessage(MS_E0011),  PHONE_ONE));
        }

        /* ログインID */
        if (userCityManagementLogic.checkExistLoginId(form.getTxtLoginid()) && form.getUserId().isBlank()) {
            errorsMesage.add(MessageFormat.format(MessageUtil.getMessage(MS_E0128),  ID_LOGIN));
        }

        /* パスワード（確認） */
        if (!form.getPwdPassword().equals(form.getPwdPasswordConfirm())) {
            errorsMesage.add(MessageFormat.format(MessageUtil.getMessage(MS_E0105),  PASSWORD, PASSWORDCONFIRM));
        }

        String codeCd026 = CodeUtil.getValue(CD026, CD026_LOGGING_IN);
        if (entity != null && codeCd026.equals(entity.getLoginStatusFlag())) {
            errorsMesage.add(MessageFormat.format(MessageUtil.getMessage(MS_E0114),  EDIT));
        }
    }
}
