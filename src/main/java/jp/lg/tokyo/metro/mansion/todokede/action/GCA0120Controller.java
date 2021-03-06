/*
 * @(#) GCA0120Controller.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nhvu
 * Create Date: 2019/12/19
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.AllowMaintenance;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SecurityUtil;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL400Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.ApproveNewUserRegistrationForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IManagementAssociationLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.ApproveNewUserRegistrationVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ML010Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ML020Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ManagementAssociationCustomVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.UserRegistrationVo;

@AllowMaintenance
@Controller
public class GCA0120Controller extends BaseMansionController {

    @Autowired
    private IManagementAssociationLogic associationLogic;

    private static final Logger logger = LogManager.getLogger(GCA0120Controller.class);
    private static final String GCA0120 = "GCA0120";
    private static final String MANSION_INFO = "MansionInfo";
    private static final String RADIO_APARTMENTSELECT_ERROR = "??????";
    private static final String MANAGEMENT_APPLICATION_INFOR = "????????????????????????";
    private static final String MANSION_ID = "???????????????ID";

    /**
     * @param model         Model
     * @param request       HttpServletRequest
     * @param apartmentId   String
     * @param applicationNo String
     * @return String
     * @throws BusinessException BusinessException
     */
    @PostMapping("/GCA0120/show")
    public String show(Model model, HttpServletRequest request, String apartmentId, String applicationNo)
            throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        boolean display = true;
        ApproveNewUserRegistrationVo registrationVo;
        // ????????????????????????
        ManagementAssociationCustomVo customVo = associationLogic.getNewRegistrationInformation(applicationNo);

        // ?????????????????????????????????????????????
        registrationVo = associationLogic.getRegistrationApartmentInformation(customVo);
        // ?????????????????????????????????????????????ID???
        getSessionMansion(customVo, request, registrationVo);

        // ?????????????????????????????????????????????
        if (apartmentId != null && !("".equals(apartmentId))) {
            @SuppressWarnings("unchecked")
            List<UserRegistrationVo> listRegistrationVo = (List<UserRegistrationVo>) request.getSession()
                    .getAttribute(MANSION_INFO);
            TBL100Entity entity = associationLogic.getMansionInformationById(apartmentId);
            if (isApartmentIDNotExist(entity, listRegistrationVo)) {
                registrationVo = associationLogic.getRegistrationApartmentInformation(customVo, entity);
            }
        }
        if (CollectionUtils.isEmpty(registrationVo.getListUserRegistrationVo())) {
            display = false;
        }
        registrationVo.setCheckError(false);
        registrationVo.setRdoInspectionResult(customVo.getJudgeResult());
        registrationVo.setDisplay(display);
        registrationVo.setApartmentId(customVo.getApartmentId());
        model.addAttribute("approveregistration", registrationVo);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return GCA0120;
    }

    /**
     * 
     * @param form    ApproveNewUserRegistrationForm
     * @param result  BindingResult
     * @param session HttpSession
     * @param model   Model
     * @param request HttpServletRequest
     * @return String
     * @throws BusinessException BusinessException
     */
    @PostMapping("/GCA0120/save")
    public String save(@ModelAttribute("approveregistration") @Valid ApproveNewUserRegistrationForm form,
                       BindingResult result,
                       HttpSession session,
                       Model model,
                       HttpServletRequest request) throws BusinessException {
        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, currentMethodName));
        boolean checkError = false;
        List<String> errorsMesage = new ArrayList<>();
        if (result.hasErrors()) {
            CommonUtils.getErrorsFromBindingResult(result, errorsMesage);
        }
        // ????????????????????????
        if (errorsMesage.isEmpty()) {
            isValidateCorrelation(form, errorsMesage);
        }

        if (!errorsMesage.isEmpty()) {
            model.addAttribute("errorsMesage", errorsMesage);
            checkError = true;
            form.setCheckError(checkError);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return GCA0120;
        }

        // ????????????????????????????????????ID???????????????????????????????????????????????????TBL400?????????????????????
        ManagementAssociationCustomVo customVo = associationLogic
                .getNewRegistrationInformation(form.getApplicationNo());
        // ????????????????????????????????????
        if (!checkExclusiveApplication(customVo, form, errorsMesage)) {
            model.addAttribute("errorsMesage", errorsMesage);
            checkError = true;
            form.setCheckError(checkError);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return GCA0120;
        }
        String selectApartmentNameStr = form.getRdoApartmentSelect();
        UserRegistrationVo userVo;
        if (CommonConstants.OFF.equals(selectApartmentNameStr)
                && CommonConstants.OFF.equals(form.getRdoApartmentSelect())) {
            userVo = new UserRegistrationVo();
        } else {
            Integer selectApartmentName = !selectApartmentNameStr.isEmpty()
                    ? Integer.parseInt(intSelectApartmentName(selectApartmentNameStr))
                    : CommonConstants.NUM_0;
            int selectListApartment = selectApartmentName - 1;
            userVo = form.getListUserRegistrationVo().get(selectListApartment);
        }
        
        // ????????????????????????????????????ID???
        if (!checkExclusiveMansionId(userVo, errorsMesage)) {
            model.addAttribute("errorsMesage", errorsMesage);
            checkError = true;
            form.setCheckError(checkError);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return GCA0120;
        }
        
        // ??????????????????????????????
        String rdoInspectionResult = form.getRdoInspectionResult();
        String userID = SecurityUtil.getUserLoggedInInfo().getUserId();
        form.setUpdateUserId(userID);
        ApproveNewUserRegistrationVo registrationVo = associationLogic.saveToRegisterAparment(form, userVo);
        if (CommonConstants.STR_2.equals(rdoInspectionResult)) {
            // ????????????????????? Approve/??????
            ML010Vo ml010Vo = associationLogic.getML010Template(registrationVo);
            associationLogic.sendML010(ml010Vo);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1110_I, "ML010",
                    registrationVo.getContactMailAddress()));
        } else {
            // ????????????????????? Reject/??????
            ML020Vo ml020Vo = associationLogic.getML020Template(registrationVo);
            associationLogic.sendML020(ml020Vo);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1110_I, "ML020",
                    registrationVo.getContactMailAddress()));
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
        return "redirect:/GCA0110/return";
    }

    /**
     * ????????????????????????????????????
     * 
     * @param customVo ManagementAssociationCustomVo
     * @param form     ApproveNewUserRegistrationForm
     * @return boolean
     */
    private boolean checkExclusiveApplication(ManagementAssociationCustomVo customVo,
                                              @Valid ApproveNewUserRegistrationForm form,
                                              List<String> errorsMesage) {
        String updateDateTimeToTable = customVo.getUpdateDatetime().toString();
        String updateDateTimeToGJA0120 = form.getUpdateDatetime();
        if (updateDateTimeToTable.isEmpty()) {
            return true;
        }
        if (!updateDateTimeToGJA0120.equals(updateDateTimeToTable)) {
            errorsMesage.add(MessageUtil.getMessage(CommonConstants.MS_E0128, MANAGEMENT_APPLICATION_INFOR));
            return false;
        }
        return true;
    }

    /**
     * ????????????????????????????????????ID???
     * 
     * @param customVo     ManagementAssociationCustomVo
     * @param form         ApproveNewUserRegistrationForm
     * @param errorsMesage List
     * @return boolean
     */
    private boolean checkExclusiveMansionId(UserRegistrationVo userVo, List<String> errorsMesage) {
        if (null == userVo || null == userVo.getApartmentId()) {
            return true;
        }
        String mansionIdToGJA0120 = userVo.getApartmentId();
        for (TBL400Entity entity : associationLogic.findAll()) {
            if (mansionIdToGJA0120.equals(entity.getApartmentId())) {
                errorsMesage.add(MessageUtil.getMessage(CommonConstants.MS_E0128, MANSION_ID));
                return false;
            }
        }
        return true;
    }

    /**
     * ????????????????????????
     *
     * @param form         ApproveNewUserRegistrationForm
     * @param errorsMesage List
     */
    private boolean isValidateCorrelation(@Valid ApproveNewUserRegistrationForm form, List<String> errorsMesage) {
        String rdoApartmentSelect = form.getRdoApartmentSelect();
        String rdoInspectionResult = form.getRdoInspectionResult();
        if (CommonConstants.STR_2.equals(rdoInspectionResult)
                && (CommonConstants.OFF.equals(rdoApartmentSelect) || CommonConstants.ON.equals(rdoApartmentSelect))) {
            errorsMesage.add(MessageUtil.getMessage(CommonConstants.MS_E0001, RADIO_APARTMENTSELECT_ERROR));
            return false;
        }
        return true;
    }

    /**
     * ?????????????????????????????????????????????ID???
     * 
     * @param customVo       ManagementAssociationCustomVo
     * @param request        HttpServletRequest
     * @param registrationVo ApproveNewUserRegistrationVo
     */
    private void getSessionMansion(ManagementAssociationCustomVo customVo, HttpServletRequest request,
            ApproveNewUserRegistrationVo registrationVo) {
        String judgeResult = CodeUtil.getValue(CommonConstants.CD016, CommonConstants.CD016_UNREVIEWED);
        if (judgeResult.equals(customVo.getJudgeResult())) {
            HttpSession session = request.getSession();
            session.setAttribute(MANSION_INFO, registrationVo.getListUserRegistrationVo());
        }
    }

    /**
     * @param tBL100Entity       entity
     * @param listRegistrationVo List
     * @return
     */
    private boolean isApartmentIDNotExist(TBL100Entity entity, List<UserRegistrationVo> listRegistrationVo) {
        if (CommonConstants.NUM_0 == listRegistrationVo.size()) {
            return true;
        }
        Iterator<UserRegistrationVo> interator = listRegistrationVo.iterator();
        while (interator.hasNext()) {
            UserRegistrationVo userRegistrationVo = interator.next();
            if (entity.getApartmentId().equals(userRegistrationVo.getApartmentId())) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param rdoApartmentSelect String
     * @return String
     */
    private String intSelectApartmentName(String rdoApartmentSelect) {
        char selectFirstChar = rdoApartmentSelect.charAt(0);
        return String.valueOf(selectFirstChar);
    }

}
