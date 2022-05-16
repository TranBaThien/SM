/*
 * @(#) MCA0110Controller.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.action;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.NUM_2;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CheckUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.ManagementAssociationApplicationForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IUserRegistrationLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.CityVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.CodeVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ManagementAssociationApplicationVo;

/**
 * @author lthai
 *
 */
@Controller
public class MCA0110Controller extends BaseMansionController {
    private static final Logger logger = LogManager.getLogger(MCA0110Controller.class);

    private static final String MCA0110 = "MCA0110";
    private static final String MCA0110_SHOW = "/MCA0110/show";
    private static final String MCA0110_SUBMIT = "/MCA0110/save";

    private static final String MCA0110_LBL_CONTACT_TELNO = "連絡先の電話番号";
    private static final String MCA0110_LBL_CONTACT_PROPERTY_ELSE = "連絡先属性のその他";
    private static final String MCA0110_LBL_CONTACT_MAIL = "メールアドレス";
    private static final String MCA0110_LBL_CONTACT_MAIL_RE = "確認用のメールアドレス";
    private static final String MCA0110_LBL_TEMP_PASWD = "仮パスワード";
    private static final String MCA0110_LBL_TEMP_PASWD_RE = "確認用の仮パスワード";
    private static final String MCA0110_ATTRIBUTE_OTHER = "9";

    private static final String MCA0110_USER_REGISTRATION = "userRegistration";
    private static final String MCA0110_CHECK_ON_CONFIRM = "checkOnConfirm";

    @Autowired
    private IUserRegistrationLogic userRegistrationLogic;

    /**
     * 
     * @param model Model
     * @param managementAssociationApplicationVo ManagementAssociationApplicationVo
     * @return String
     */
    @PostMapping(value = {MCA0110_SHOW})
    public String show(Model model, ManagementAssociationApplicationVo managementAssociationApplicationVo) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        ManagementAssociationApplicationVo vo = new ManagementAssociationApplicationVo();
        try {
            // Prepare data for display
            prepare(vo);
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(ex.getMessage());
        }
        model.addAttribute(MCA0110_USER_REGISTRATION, vo);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return MCA0110;
    }

    /**
     * 
     * @param form ManagementAssociationApplicationForm
     * @param result BindingResult
     * @return ModelAndView
     */
    @PostMapping(MCA0110_SUBMIT)
    public ModelAndView submit(
            @ModelAttribute(MCA0110_USER_REGISTRATION) @Valid ManagementAssociationApplicationForm form,
            BindingResult result) throws BusinessException {

        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        ModelAndView md = new ModelAndView();
        List<String> errorsMessage = new ArrayList<>();
        if (result.hasErrors()) {
            CommonUtils.getErrorsFromBindingResult(result, errorsMessage);
        }
        /* チェック（相関） */
        if (errorsMessage.isEmpty()) {
            checkCorrelation(form, errorsMessage);
        }
        /* get error message form BindingResult */
        if (errorsMessage.isEmpty()) {
            userRegistrationLogic.saveManagementAssociationApplicationInformation(form);
            md = new ModelAndView("redirect:/" + CommonConstants.SCREEN_ID_MAA0110);
        }
        if (!errorsMessage.isEmpty()) {
            md = new ModelAndView(MCA0110, MCA0110_USER_REGISTRATION, form);
            md.addObject("errorMessage", errorsMessage);
            if (!StringUtils.isEmpty(form.getChkTermsConditions())) {
                md.addObject(MCA0110_CHECK_ON_CONFIRM, "true");
            }
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return md;
    }

    /**
     * Prepare data for display
     * 
     * @param vo ManagementAssociationApplicationVo
     */
    private void prepare(ManagementAssociationApplicationVo vo) throws BusinessException {
        try {
            List<CodeVo> lstContactProperty = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD013));
            List<CityVo> lstCityCode = userRegistrationLogic.getMunicipalMasterInformation();

            // Display for radio radio box support code
            vo.setLstContactProperty(lstContactProperty);
            vo.setLstCityCodeApartmentAddress1(lstCityCode);

        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Check correlation
     * 
     * @param form ManagementAssociationApplicationForm
     * @param errors ListErrorsMessage
     */
    private void checkCorrelation(ManagementAssociationApplicationForm form, List<String> errorsMessage) {
        final String rdoContactProperty = form.getRdoContactProperty();
        final String txtContactPropertyElse = form.getTxtContactPropertyElse();
        final String txtContactMail = form.getTxtContactMail();
        final String txtContactMailRe = form.getTxtContactMailRe();
        final String txtTempPassword = form.getTxtTempPassword();
        final String txtTempPasswordRe = form.getTxtTempPasswordRe();
        final String txtContactTelno1 = form.getTxtContactTelno1();
        final String txtContactTelno2 = form.getTxtContactTelno2();
        final String txtContactTelno3 = form.getTxtContactTelno3();

        // 連絡先属性をその他選択する場合、必須チェック
        if (CheckUtil.isBlank(txtContactPropertyElse) && rdoContactProperty.equals(MCA0110_ATTRIBUTE_OTHER)) {
            errorsMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0002, MCA0110_LBL_CONTACT_PROPERTY_ELSE));
        }
        // 左記項目をハイフンで連結した桁数＞１３桁の場合、エラー
        if (!StringUtils.isEmpty(txtContactTelno1) && (txtContactTelno1.length() + txtContactTelno2.length() + txtContactTelno3.length() + NUM_2) > 13) {
            errorsMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0011, MCA0110_LBL_CONTACT_TELNO));
        }
        // 同値チェック メールアドレス（txtContactMail）と一致しない場合、エラー
        if (!txtContactMail.equals(txtContactMailRe)) {
            errorsMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0105, MCA0110_LBL_CONTACT_MAIL,
                    MCA0110_LBL_CONTACT_MAIL_RE));
        }
        // 同値チェック 仮パスワード（txtTempPassword）と一致しない場合、エラー
        if (!StringUtils.isEmpty(txtTempPassword) && !txtTempPassword.equals(txtTempPasswordRe)) {
            errorsMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0105, MCA0110_LBL_TEMP_PASWD,
                    MCA0110_LBL_TEMP_PASWD_RE));
        }
    }
}
