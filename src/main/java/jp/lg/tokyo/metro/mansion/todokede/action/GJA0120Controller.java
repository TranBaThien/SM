/*
 * @(#) GJA0120Controller.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.action;

import static java.util.Objects.isNull;
import static jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil.getMessage;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpSession;
import javax.transaction.SystemException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.AllowCity;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.RedirectForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IApartmentInformationDetailsLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.ApartmentInformationDetailsVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ApartmentUserInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.MansionInfoVo;

@AllowCity
@Controller
public class GJA0120Controller extends BaseMansionController {
    private static final Logger logger = LogManager.getLogger(GJA0120Controller.class);

    private static final String GJA0120 = "GJA0120";
    private static final String MANSION_INFO = "mansionInfo";
    private static final String MANSION_STATUS_INFO = "mansionStatusInfo";
    private static final String NOTIFICATION_REGISTRATION = "notificationRegistration";
    private static final String CORRECT_DETAILS = "correctionDetails";
    private static final String USER_INFO = "userInfo";
    private static final String BTN_STATUS_VO = "btnStatusVo";
    private static final String MANSION_INFO_UPDATE = "MANSIONINFOUPDATE";
    private static final String USER_INFO_UPDATE = "USERINFOUPDATE";

    @Autowired
    IApartmentInformationDetailsLogic isApartmentInformationDetailsLogic;

    @PostMapping(value = {"/GJA0120/show"})
    public String show(Model model, RedirectForm redirectForm, HttpSession session)
            throws InvocationTargetException, IllegalAccessException, BusinessException, SystemException {
        logger.info(getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return loadPage(model, session, redirectForm.getApartmentId(), redirectForm.getNotificationNo(), CommonConstants.BLANK);
    }

    private String loadPage(Model model, HttpSession session, String apartmentId, String notificationNo, String message)
            throws SystemException, BusinessException, IllegalAccessException, InvocationTargetException {
        //マンション基本情報エリア/ Get mansion info area 
        MansionInfoVo mansionInfoVo = getMansionInfo(apartmentId);
        if (isNull(mansionInfoVo)) {
            mansionInfoVo = new MansionInfoVo();
        }
        model.addAttribute(MANSION_INFO, mansionInfoVo);

        ApartmentInformationDetailsVo vo = isApartmentInformationDetailsLogic.getMansionInformationGJA0120(mansionInfoVo);

        model.addAttribute(MANSION_STATUS_INFO, vo.getApartmentStatusInfoVo());
        model.addAttribute(NOTIFICATION_REGISTRATION, vo.getNotificationRegistrationVo());
        model.addAttribute(CORRECT_DETAILS, vo.getCorrectionDetails());
        model.addAttribute(USER_INFO, vo.getApartmentUserInfoVo());
        model.addAttribute(BTN_STATUS_VO, vo.getApartmentBtnStatusVo());

        //Set Session
        String mansionUpdateTime = isApartmentInformationDetailsLogic.getMansionInfoUpdateTime(apartmentId);
        session.setAttribute(MANSION_INFO_UPDATE, mansionUpdateTime);
        if (!ObjectUtils.isEmpty(vo.getApartmentUserInfoVo())) {
            session.setAttribute(USER_INFO_UPDATE, vo.getApartmentUserInfoVo().getUpdateDatetime());
        }
        //Set message error
        if (!message.isBlank()) {
            model.addAttribute("errorMessage", MessageUtil.getMessage(message));
        }
        logger.info(getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return GJA0120;
    }

    /**
     * 
     * @param model Model
     * @param redirectForm RedirectForm
     * @param session HttpSession
     * @return
     */
    @PostMapping(value = {"/MDA0110/noti-regis"})
    public String registerNotification(Model model, RedirectForm redirectForm, HttpSession session) throws BusinessException {
        logger.info(getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        try {
            if (!checkExclusiveMansionInfo(session, redirectForm.getApartmentId())) {
                return loadPage(model, session, redirectForm.getApartmentId(), redirectForm.getNotificationNo(), CommonConstants.MS_E0113);
            }
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(ex.getMessage());
        }
        logger.info(getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
//        model.addAttribute(REDIRECT_INFO, redirectForm);
        return "forward:/MDA0110/show";
    }

    /**
     * 
     * @param model Model
     * @param redirectForm RedirectForm
     * @param session HttpSession
     * @return String
     */
    @PostMapping(value = {"/MDA0110/change-noti"})
    public String changeNotification(Model model, RedirectForm redirectForm, HttpSession session) throws BusinessException {
        logger.info(getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        try {
            if (!checkExclusiveMansionInfo(session, redirectForm.getApartmentId())) {
                return loadPage(model, session, redirectForm.getApartmentId(), redirectForm.getNotificationNo(), CommonConstants.MS_E0113);
            }
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(ex.getMessage());
        }
        logger.info(getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return "forward:/MDA0110/show";
    }

    /**
     * 
     * @param model Model
     * @param redirectForm RedirectForm
     * @param session HttpSession
     * @return String
     * @throws BusinessException 
     */
    @PostMapping(value = {"/GFA0110/advice-noti-regis"})
    public String adviceNotificationRegistration(Model model, RedirectForm redirectForm, HttpSession session) throws BusinessException {
        logger.info(getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        try {
            if (!checkExclusiveMansionInfo(session, redirectForm.getApartmentId())) {
                return loadPage(model, session, redirectForm.getApartmentId(), redirectForm.getNotificationNo(), CommonConstants.MS_E0113);
            }
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(ex.getMessage());
        }
        logger.info(getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return "forward:/GFA0110/show";
    }

    /**
     * 
     * @param model Model
     * @param redirectForm RedirectForm
     * @param session HttpSession
     * @return String
     */
    @PostMapping(value = {"/GIA0110/supervised-noti"})
    public String supervisedNoticeRegistration(Model model, RedirectForm redirectForm, HttpSession session) {
        logger.info(getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        try {
            if (!checkExclusiveMansionInfo(session, redirectForm.getApartmentId())) {
                return loadPage(model, session, redirectForm.getApartmentId(), redirectForm.getNotificationNo(), CommonConstants.MS_E0113);
            }
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
        }
        logger.info(getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return "forward:/GIA0110/show";
    }

    /**
     * 
     * @param model Model
     * @param redirectForm RedirectForm
     * @param session HttpSession
     * @return String
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException IllegalAccessException
     * @throws BusinessException BusinessException
     * @throws SystemException SystemException
     */
    @PostMapping(value = {"/GJA0120/active-idlogin"})
    public String enableLoginID(Model model, RedirectForm redirectForm, HttpSession session)
            throws InvocationTargetException, IllegalAccessException, BusinessException, SystemException {
        logger.info(getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        try {
            if (!checkExclusiveUserInfo(session, redirectForm.getApartmentId())) {
                return loadPage(model, session, redirectForm.getApartmentId(), redirectForm.getNotificationNo(), CommonConstants.MS_E0113);
            }
            isApartmentInformationDetailsLogic.enableLoginID(redirectForm.getApartmentId());
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(ex.getMessage());
        }
        return loadPage(model, session, redirectForm.getApartmentId(), redirectForm.getNotificationNo(), CommonConstants.BLANK);
    }

    /**
     * 
     * @param model Model
     * @param redirectForm RedirectForm
     * @param session HttpSession
     * @return String
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException IllegalAccessException
     * @throws BusinessException BusinessException
     * @throws SystemException SystemException
     */
    @PostMapping(value = {"/GJA0120/resuming-use"})
    public String resumingUse(Model model, RedirectForm redirectForm, HttpSession session) 
            throws InvocationTargetException, IllegalAccessException, BusinessException, SystemException {
        logger.info(getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        try {
            if (!checkExclusiveUserInfo(session, redirectForm.getApartmentId())) {
                return loadPage(model, session, redirectForm.getApartmentId(), redirectForm.getNotificationNo(), CommonConstants.MS_E0113);
            }
            isApartmentInformationDetailsLogic.resumingUse(redirectForm.getApartmentId());
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(ex.getMessage());
        }
        return loadPage(model, session, redirectForm.getApartmentId(), redirectForm.getNotificationNo(), CommonConstants.BLANK);
    }

    /**
     * 
     * @param model Model
     * @param redirectForm RedirectForm
     * @param session HttpSession
     * @return String
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException IllegalAccessException
     * @throws BusinessException BusinessException
     * @throws SystemException SystemException
     */
    @PostMapping(value = {"/GJA0120/use-stop"})
    public String stopUse(Model model, RedirectForm redirectForm, HttpSession session)
            throws InvocationTargetException, IllegalAccessException, BusinessException, SystemException {
        logger.info(getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        try {
            if (!checkExclusiveUserInfo(session, redirectForm.getApartmentId())) {
                return loadPage(model, session, redirectForm.getApartmentId(), redirectForm.getNotificationNo(), CommonConstants.MS_E0113);
            }
            isApartmentInformationDetailsLogic.stopUse(redirectForm.getApartmentId());
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(ex.getMessage());
        }
        return loadPage(model, session, redirectForm.getApartmentId(), redirectForm.getNotificationNo(), CommonConstants.BLANK);
    }

    private boolean checkExclusiveMansionInfo(HttpSession session, String apartmentId) {
        //Mansion update time from session
        String sessionMansionUpdateTime = (String) session.getAttribute(MANSION_INFO_UPDATE);

        //Mansion update time from DB
        String mansionUpdateTime = isApartmentInformationDetailsLogic.getMansionInfoUpdateTime(apartmentId);
        return sessionMansionUpdateTime.equals(mansionUpdateTime);
    }

    private boolean checkExclusiveUserInfo(HttpSession session, String apartmentId) throws BusinessException {
        //User update time from session
        String sessionUserUpdateTime = (String) session.getAttribute(USER_INFO_UPDATE);
        //User update time from DB
        String userUpdateTime = CommonConstants.BLANK;
        try {
            ApartmentUserInfoVo userInfoVo = isApartmentInformationDetailsLogic.getUserInformation(apartmentId);
            if (userInfoVo != null) {
                userUpdateTime = userInfoVo.getUpdateDatetime();
            }
        } catch (Exception ex) {
            throw new BusinessException(ex.getMessage());
        }
        return sessionUserUpdateTime.equals(userUpdateTime);
    }
}
