/*
 * @(#) ManagementAssociationLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tbthien
 * Create Date: 2019/11/26
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MailUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL110DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL400BDAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL400DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM001DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL400Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM001Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.ApproveNewUserRegistrationForm;
import jp.lg.tokyo.metro.mansion.todokede.form.SearchForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IEMailLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.IManagementAssociationLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.ApproveNewUserRegistrationVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.GCA0110Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ML010Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ML020Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ManagementAssociationCustomVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.UserRegistrationVo;


/**
 * @author tbthien
 *
 */
@Service
public class ManagementAssociationLogicImpl implements IManagementAssociationLogic { 
    
    private static final Logger logger = LogManager.getLogger(ManagementAssociationLogicImpl.class);
 
    @Autowired
    private TBL400DAO tbl400dao;
    
    @Autowired
    private TBL400BDAO tbl400bdao;
    
    @Autowired
    private TBL100DAO tbl100dao;
    
    @Autowired
    private TBL110DAO tbl110dao;
    
    @Autowired
    private TBM001DAO tbm110dao;
    
    @Autowired
    private IEMailLogic mailLogic;
    
    private static final String CANDIDATES_FOR_REGISTRATION = "登録対象マンション候補";
    
    /**
     * Get management association information and city name from TBL400 and TBM001 to display on the screen
     * @param page int
     * @param size int
     * @return Page
     */ 
    @Override 
    public Page<GCA0110Vo> getListManagementAssociation(int page, int size) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        List<GCA0110Vo> listvo = new ArrayList<GCA0110Vo>(); 

        Page<ManagementAssociationCustomVo> pageModel = null;

        if (page > -1 && size > 0) {

            Pageable pageable = PageRequest.of(page, size);

            pageModel = tbl400dao.getListManagementAssociation(pageable);  

            for (ManagementAssociationCustomVo ma : pageModel.getContent()) {
                GCA0110Vo vo = new GCA0110Vo();
                
                /*
                 * convert 1 to 未審査
                 * same for other cases
                 */
                String judgeresult = CodeUtil.getLabel(CommonConstants.CD016, ma.getJudgeResult());
                
                vo.setApplicationNo(ma.getApplicationNo());
                // format ApplicationTime
                vo.setApplicationTime(DateTimeUtil.formatDateTime(ma.getApplicationTime(), CommonConstants.DATE_TIME_FORMATTER));
                vo.setApartmentName(ma.getApartmentName());
                vo.setAddress(ma.getAddress());
                vo.setCityCode(ma.getCityCode()); 
                vo.setCityName(ma.getCityName());
                vo.setJudgeResult(judgeresult);
                     
                listvo.add(vo);  
            }
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return new PageImpl<GCA0110Vo>(listvo, pageable, tbl400dao.countManagementAssociationNumber()); 
        } else {
            page = 0; 
            size = 50;
            Pageable pageable = PageRequest.of(page, size);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return new PageImpl<GCA0110Vo>(listvo, pageable, 0);
        }
    }
    
    /**
      * Search for management association information from table TBL400 
      * that corresponds to the search condition in SearchForm
      * @param page int
      * @param size int
      * @param form int
      * @return Page
      * 
      */
    @Override
    public Page<GCA0110Vo> searchManagementAssociation(int page, int size, SearchForm form) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        int length;
        StringBuffer start = new StringBuffer(); 
        StringBuffer end = new StringBuffer(); 
        
        // convert yyyy/mm/dd format to yyyymmdd format
        if (!CommonConstants.BLANK.equals(form.getStartTimeApply())) {
            
            String[] starttimeapply = form.getStartTimeApply().split(CommonConstants.SLASH);
            length = starttimeapply.length;
            for (int i = 0; i < length; i++) {
                start.append(starttimeapply[i]);
            }
            
        }
        
        // convert yyyy/mm/dd format to yyyymmdd format
        if (!CommonConstants.BLANK.equals(form.getEndTimeApply())) {
            
            String[] endtimeapply = form.getEndTimeApply().split(CommonConstants.SLASH);
            length = endtimeapply.length;
            for (int i = 0; i < length; i++) {
                end.append(endtimeapply[i]);
            }
            
        }
        
        SearchForm form2 = new SearchForm();
        
        /* Create a new form with two fields StartTimeApply and EndTimeApply
         * that have yyyymmddhhmmss format
         */ 
        form2.setCityCode(form.getCityCode());
        form2.setApartmentName(form.getApartmentName());
        form2.setStartTimeApply(start.toString() + "000000");
        form2.setEndTimeApply(end.toString() + "240000");
        form2.setUnexamined(form.getUnexamined());
        form2.setApproval(form.getApproval());
        form2.setReject(form.getReject());
               
        int currentPage;
        currentPage = page * size; 
        
        List<GCA0110Vo> listapply = tbl400bdao.searchManagementAssociation(form2, currentPage, size);
        
        for (GCA0110Vo vo : listapply) {
            
            /*
             * convert 1 to 未審査
             * same for other cases
             */
            String judgeresult = CodeUtil.getLabel(CommonConstants.CD016, vo.getJudgeResult()); 
            vo.setJudgeResult(judgeresult);
            
            // format ApplicationTime
            LocalDateTime applytime = DateTimeUtil.getLocalDateTimeFromString(vo.getApplicationTime());
            vo.setApplicationTime(DateTimeUtil.formatDateTime(applytime, CommonConstants.DATE_TIME_FORMATTER));
            
        }
        
        Pageable pageable = PageRequest.of(page, size);  
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return new PageImpl<GCA0110Vo>(listapply, pageable, tbl400bdao.TotalSearchResults(form2));
        
    }
    
    /**
      * get total search results number when search from table TBL400
      * @param page int
      * @param size int
      * @return List
      * 
      */
    @Override   
    public int totalResultWhenDisplay(int page, int size) {
        if (page > -1 && size > 0) {
            return tbl400dao.countManagementAssociationNumber();
        } else {
            return 0;
        }
    }

    /**
      * Get total search results number when search from table TBL400
      * @param form SearchForm
      * @return List
      * 
      */
    @Override
    public int TotalSearchResults(SearchForm form) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        int length = 0; 
        StringBuffer start = new StringBuffer();
        StringBuffer end = new StringBuffer();
        int i;
        
        if (!CommonConstants.BLANK.equals(form.getStartTimeApply())) {
            
            String[] starttimeapply = form.getStartTimeApply().split(CommonConstants.SLASH);
            length = starttimeapply.length;
            for (i = 0; i < length; i++) {
                start.append(starttimeapply[i]);
            }
            
        }
        
        if (!CommonConstants.BLANK.equals(form.getEndTimeApply())) {
            
            String[] endtimeapply = form.getEndTimeApply().split(CommonConstants.SLASH);
            length = endtimeapply.length;
            for (i = 0; i < length; i++) {
                end.append(endtimeapply[i]);
            }
        }

        SearchForm form2 = new SearchForm();
        
        form2.setCityCode(form.getCityCode());
        form2.setApartmentName(form.getApartmentName());
        form2.setStartTimeApply(start.toString() + "000000");
        form2.setEndTimeApply(end.toString() + "240000");
        form2.setUnexamined(form.getUnexamined());
        form2.setApproval(form.getApproval());
        form2.setReject(form.getReject());
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return tbl400bdao.TotalSearchResults(form2);
        
    }
    
    /**
     * @param listapplyno List
     * @param form SearchForm
     * @param size int
     * @param page int
     * @return Page
     * 
     */
    public Page<GCA0110Vo> getListManagementAssociationAgain(int page, int size, SearchForm form, List<String> listapplyno) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        List<GCA0110Vo> listvo = new ArrayList<GCA0110Vo>();
        
        List<ManagementAssociationCustomVo> list = new ArrayList<ManagementAssociationCustomVo>();
                      
        list = tbl400dao.getListManagementAssociationAgain(listapplyno);
        
        Pageable pageable = PageRequest.of(page, size); 
        
        for (ManagementAssociationCustomVo ma : list) {
            GCA0110Vo vo = new GCA0110Vo();
                  
            /*
             * convert 1 to 未審査
             * same for other cases
             */
            String judgeresult = CodeUtil.getLabel(CommonConstants.CD016, ma.getJudgeResult());
                 
            vo.setApplicationNo(ma.getApplicationNo());
            vo.setApplicationTime(DateTimeUtil.formatDateTime(ma.getApplicationTime(), CommonConstants.DATE_TIME_FORMATTER));
            vo.setApartmentName(ma.getApartmentName());
            vo.setAddress(ma.getAddress());
            vo.setCityCode(ma.getCityCode());
            vo.setCityName(ma.getCityName());
            vo.setJudgeResult(judgeresult);
                 
            listvo.add(vo);
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return new PageImpl<GCA0110Vo>(listvo, pageable, TotalSearchResults(form));        
    }
    

    @Override
    public ApproveNewUserRegistrationVo getRegistrationApartmentInformation(ManagementAssociationCustomVo customVo) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        if (customVo == null) {
            throw new BusinessException();
        }
        String judgeResult = CodeUtil.getValue(CommonConstants.CD016, CommonConstants.CD016_UNREVIEWED);
        if (judgeResult.equals(customVo.getJudgeResult())) {
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            // 未審査 Unexamined
            return unreviewedNewUserRegistrationVo(customVo);
        } else {
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            // 承認・却下 / Approval/rejection
            return approveNewUserRegistrationVo(customVo);
        }
    }
    

    @Override
    public ApproveNewUserRegistrationVo getRegistrationApartmentInformation(ManagementAssociationCustomVo customVo,
            TBL100Entity entity) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        UserRegistrationVo userVo = new UserRegistrationVo();
        userVo.setLblApartmentName2(entity.getApartmentName());
        userVo.setLblApartmentZipCode2(entity.getZipCode());
        StringBuilder address2 = new StringBuilder();
        address2.append(entity.getZipCode().concat(CommonConstants.SPACE_FULL_SIZE));
        TBM001Entity entity1 = tbm110dao.getMunicipalMasterInfo(entity.getCityCode());
        address2.append(entity1.getCityName());
        address2.append(entity.getAddress());
        userVo.setLblApartmentAddress2(address2.toString());
        userVo.setApartmentId(entity.getApartmentId());
        userVo.setCityCode(entity.getCityCode());
        userVo.setAddress(entity.getAddress());
        ApproveNewUserRegistrationVo vo = unreviewedNewUserRegistrationVo(customVo);
        int lblRowNumber = vo.getListUserRegistrationVo().size();
        lblRowNumber += 1;
        userVo.setLblRowNumber(java.lang.String.valueOf(lblRowNumber));
        vo.getListUserRegistrationVo().add(userVo);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return vo;
    }
    
    @Override
    public ManagementAssociationCustomVo getNewRegistrationInformation(String applicationNo) {
        return tbl400dao.getNewRegistrationInformation(applicationNo);
    }

    /**
     * 承認・却下 / Approval/rejection
     * 
     * @param applicationNo ManagementAssociationCustomVo
     * @return
     */
    private ApproveNewUserRegistrationVo approveNewUserRegistrationVo(ManagementAssociationCustomVo customVo) {
        ApproveNewUserRegistrationVo vo = new ApproveNewUserRegistrationVo();
        vo.setUpdateDatetime(customVo.getUpdateDatetime().toString());
        vo.setJudgeResult(customVo.getJudgeResult());
        vo.setApplicationNo(customVo.getApplicationNo());
        vo.setLblapartmentname1(customVo.getApartmentName());
        vo.setLblapartmentnamephonetic(customVo.getApartmentNamePhonetic());
        StringBuilder address = new StringBuilder();
        address.append(customVo.getZipCode().concat(CommonConstants.SPACE_FULL_SIZE));
        address.append(customVo.getCityName());
        address.append(customVo.getAddress());
        vo.setLblapartmentaddress1(address.toString());
        String contactPropertyCode = CodeUtil.getValue(CommonConstants.CD013, CommonConstants.CD013_OTHER);
        StringBuilder contactProperty = new StringBuilder();
        if (contactPropertyCode.equals(customVo.getContactPropertyCode())) {
            String contactPropertyCodeName = CodeUtil.getLabel(CommonConstants.CD013, customVo.getContactPropertyCode());
            contactProperty.append(contactPropertyCodeName);
            contactProperty.append("（");
            contactProperty.append(customVo.getContactPropertyElse());
            contactProperty.append("）");
        } else {
            String contactPropertyCodeName = CodeUtil.getLabel(CommonConstants.CD013, customVo.getContactPropertyCode());
            contactProperty.append(contactPropertyCodeName);
        }
        vo.setLblContactProperty(contactProperty.toString());
        vo.setLblContactZipCode(customVo.getContactZipCode());
        vo.setLblContactAddress(customVo.getContactAddress());
        vo.setLblContractTel(customVo.getContactTelNo());
        vo.setLblContractName(customVo.getContactName());
        vo.setLblContractNamePhonetic(customVo.getContactNamePhonetic());
        vo.setLblContractMail(customVo.getContactMailAddress());
        String lblNoInfoMessage = MessageUtil.getMessage(CommonConstants.MS_I0002, CANDIDATES_FOR_REGISTRATION);
        vo.setLblNoInfoMessage(lblNoInfoMessage);
        vo.setRdoApartmentSelect(CommonConstants.ON);
        List<UserRegistrationVo> resutList = apartmentInformationWithApprovalOrRejection(customVo);
        vo.setListUserRegistrationVo(resutList);
        vo.setRdoInspectionResult(customVo.getJudgeResult());
        vo.setTxaNote(customVo.getJudgeRemarks());
        return vo;
    }
    
    /**
     * 未審査 Unexamined
     * 
     * @param applicationNo ManagementAssociationCustomVo
     * @return
     */
    private ApproveNewUserRegistrationVo unreviewedNewUserRegistrationVo(ManagementAssociationCustomVo customVo) {
        ApproveNewUserRegistrationVo vo = new ApproveNewUserRegistrationVo();
        vo.setUpdateDatetime(customVo.getUpdateDatetime().toString());
        vo.setJudgeResult(customVo.getJudgeResult());
        vo.setApplicationNo(customVo.getApplicationNo());
        vo.setLblapartmentname1(customVo.getApartmentName());
        vo.setLblapartmentnamephonetic(customVo.getApartmentNamePhonetic());
        StringBuilder address = new StringBuilder();
        address.append(customVo.getZipCode().concat(CommonConstants.SPACE_FULL_SIZE));
        address.append(customVo.getCityName());
        address.append(customVo.getAddress());
        vo.setLblapartmentaddress1(address.toString());
        String contactPropertyCode = CodeUtil.getValue(CommonConstants.CD013, CommonConstants.CD013_OTHER);
        StringBuilder contactProperty = new StringBuilder();
        if (contactPropertyCode.equals(customVo.getContactPropertyCode())) {
            String contactPropertyCodeName = CodeUtil.getLabel(CommonConstants.CD013, customVo.getContactPropertyCode());
            contactProperty.append(contactPropertyCodeName);
            contactProperty.append("（");
            contactProperty.append(customVo.getContactPropertyElse());
            contactProperty.append("）");
        } else {
            String contactPropertyCodeName = CodeUtil.getLabel(CommonConstants.CD013, customVo.getContactPropertyCode());
            contactProperty.append(contactPropertyCodeName);
        }
        vo.setLblContactProperty(contactProperty.toString());
        vo.setLblContactZipCode(customVo.getContactZipCode());
        vo.setLblContactAddress(customVo.getContactAddress());
        vo.setLblContractTel(customVo.getContactTelNo());
        vo.setLblContractName(customVo.getContactName());
        vo.setLblContractNamePhonetic(customVo.getContactNamePhonetic());
        vo.setLblContractMail(customVo.getContactMailAddress());
        String lblNoInfoMessage = MessageUtil.getMessage(CommonConstants.MS_I0002, CANDIDATES_FOR_REGISTRATION);
        vo.setLblNoInfoMessage(lblNoInfoMessage);
        vo.setRdoApartmentSelect(CommonConstants.OFF);
        String apartmentName = customVo.getApartmentName();
        String zipCode = customVo.getZipCode();
        String cityCode = customVo.getCityCode();
        List<UserRegistrationVo> resutList = apartmentInformationWithUnreviewed(apartmentName, zipCode, cityCode);
        vo.setListUserRegistrationVo(resutList);
        vo.setTxaNote(CommonConstants.SPACE_HALF_SIZE);
        return vo;
    }

    /**
     * 登録対象マンション候補情報取得
     * @param vo ManagementAssociationCustomVo
     * @return List
     */
    private List<UserRegistrationVo> apartmentInformationWithApprovalOrRejection(ManagementAssociationCustomVo vo) {
        int lblRowNumber = CommonConstants.NUM_1;
        UserRegistrationVo userVo = new UserRegistrationVo();
        userVo.setLblRowNumber(java.lang.String.valueOf(lblRowNumber));
        userVo.setLblApartmentName2(vo.getResultApartmentName());
        userVo.setLblApartmentZipCode2(vo.getResultZipCode());
        userVo.setLblApartmentAddress2(vo.getResultAddress());
        List<UserRegistrationVo> getList = new ArrayList<>();
        getList.add(userVo);
        return getList;
    }

    /**
     * 
     * @param apartmentName String
     * @param zipCode String
     * @param cityCode String
     * @return List
     */
    private List<UserRegistrationVo> apartmentInformationWithUnreviewed(String apartmentName, String zipCode, String cityCode) {
        List<UserRegistrationVo> getList = new ArrayList<>();
        List<TBL100Entity> listTBL100Entity = tbl100dao.getRegistrationApartmentInformation(apartmentName, zipCode, cityCode);
        int lblRowNumber = CommonConstants.NUM_1;
        for (TBL100Entity entity : listTBL100Entity) {
            UserRegistrationVo userVo = new UserRegistrationVo();
            userVo.setLblRowNumber(java.lang.String.valueOf(lblRowNumber));
            userVo.setLblApartmentName2(entity.getApartmentName());
            userVo.setLblApartmentZipCode2(entity.getZipCode());
            StringBuilder address2 = new StringBuilder();
            address2.append(entity.getZipCode().concat(CommonConstants.SPACE_FULL_SIZE));
            TBM001Entity entity1 = tbm110dao.getMunicipalMasterInfo(entity.getCityCode());
            address2.append(entity1.getCityName());
            address2.append(entity.getAddress());
            userVo.setLblApartmentAddress2(address2.toString());
            userVo.setApartmentId(entity.getApartmentId());
            userVo.setCityCode(entity.getCityCode());
            userVo.setAddress(entity.getAddress());
            getList.add(userVo);
            lblRowNumber++;
        }
        return getList;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ApproveNewUserRegistrationVo saveToRegisterAparment(ApproveNewUserRegistrationForm form,
            UserRegistrationVo userVo) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        if (form == null) {
            throw new BusinessException();
        }
        String applicationNo = form.getApplicationNo();
        TBL400Entity entity = tbl400dao.findByApplicationNo(applicationNo);
        String rdoInspectionResult = form.getRdoInspectionResult();
        entity.setJudgeResult(rdoInspectionResult);
        String judgeResultCode = CodeUtil.getValue(CommonConstants.CD016, CommonConstants.CD016_APPROVAL);
        if (judgeResultCode.equals(rdoInspectionResult)) {
            entity.setResultApartmentName(userVo.getLblApartmentName2());
            entity.setResultZipCode(userVo.getLblApartmentZipCode2());
            entity.setResultCityCode(userVo.getCityCode());
            entity.setResultAddress(userVo.getAddress());
            entity.setJudgeTime(LocalDateTime.now());
            entity.setApartmentId(userVo.getApartmentId());
        }
        entity.setJudgeRemarks(form.getTxaNote());
        entity.setDeleteFlag(CodeUtil.getValue(CommonConstants.CD001, CommonConstants.CD001_UNDELETEFLAG));
        entity.setUpdateUserId(form.getUpdateUserId());
        entity.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
        tbl400dao.save(entity);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1060_I, new String[] { CommonConstants.TBL400, entity.getApplicationNo() }));
        if (judgeResultCode.equals(rdoInspectionResult)) {
            TBL110Entity entity2 = tbl110dao.getUserByApartmentId(entity.getApartmentId());
            String validityFlag = CodeUtil.getValue(CommonConstants.CD023, CommonConstants.CD023_VALID);
            entity2.setPassword(entity.getPassword());
            entity2.setValidityFlag(validityFlag);
            entity2.setLoginValidityTime(LocalDateTime.now());
            String deleteFlag = CodeUtil.getValue(CommonConstants.CD001, CommonConstants.CD001_UNDELETEFLAG);
            entity2.setDeleteFlag(deleteFlag);
            entity2.setUpdateUserId(form.getUpdateUserId());
            entity2.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
            tbl110dao.save(entity2);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1060_I, new String[] { CommonConstants.TBL110, entity2.getApartmentId() }));
        }
        ApproveNewUserRegistrationVo vo = new ApproveNewUserRegistrationVo();
        if (entity.getApartmentId() != null && !(CommonConstants.BLANK.equals(entity.getApartmentId()))) {
            TBL110Entity entity2 = tbl110dao.getUserByApartmentId(entity.getApartmentId());
            vo.setLoginId(entity2.getLoginId());
        }
        String loginUrl = SessionUtil.getSystemSettingByKey(CommonConstants.CITY_LOGIN_URL);
        vo.setContactMailAddress(entity.getContactMailAddress());
        vo.setApartmentName(entity.getApartmentName());
        vo.setLoginUrl(loginUrl);
        vo.setContactName(entity.getContactName());
        vo.setJudgeRemarks(entity.getJudgeRemarks());
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return vo;
    }
    
    @Override
    public ML010Vo getML010Template(ApproveNewUserRegistrationVo approveNewUserRegistrationVo) throws BusinessException {
        ML010Vo ml010Vo = mailLogic.getCommonTemplateMail(ML010Vo.class);
        ml010Vo.setContactMailAddress(approveNewUserRegistrationVo.getContactMailAddress());
        ml010Vo.setApartmentName(approveNewUserRegistrationVo.getApartmentName());
        ml010Vo.setContactName(approveNewUserRegistrationVo.getContactName());
        ml010Vo.setLoginUrl(approveNewUserRegistrationVo.getLoginUrl());
        ml010Vo.setLoginId(approveNewUserRegistrationVo.getLoginId());
        return ml010Vo;
    }

    @Override
    public void sendML010(ML010Vo ml010Vo) throws BusinessException {
        mailLogic.sendMailWithContent(ml010Vo.getMailTemplate(), MailUtil.convertToEmailInfo(ml010Vo), settingPropertyForML010(ml010Vo), null);
        
    }

    @Override
    public ML020Vo getML020Template(ApproveNewUserRegistrationVo approveNewUserRegistrationVo) throws BusinessException {
        ML020Vo ml020Vo = mailLogic.getCommonTemplateMail(ML020Vo.class);
        ml020Vo.setContactMailAddress(approveNewUserRegistrationVo.getContactMailAddress());
        ml020Vo.setApartmentName(approveNewUserRegistrationVo.getApartmentName());
        ml020Vo.setContactName(approveNewUserRegistrationVo.getContactName());
        ml020Vo.setJudgeRemarks(approveNewUserRegistrationVo.getJudgeRemarks());
        return ml020Vo;
    }

    @Override
    public void sendML020(ML020Vo ml020Vo) throws BusinessException {
        mailLogic.sendMailWithContent(ml020Vo.getMailTemplate(), MailUtil.convertToEmailInfo(ml020Vo), settingPropertyForML020(ml020Vo), null);
        
    }

    /**
     * Setting property for prepare send ML010 Mail.
     * 
     * @param ml010Vo ML010Vo
     * @return Map
     */
    private Map<String, Object> settingPropertyForML010(ML010Vo ml010Vo) {
        Map<String, Object> model = new HashMap<>();
        model.put("apartmentName", ml010Vo.getApartmentName());
        model.put("contactName", ml010Vo.getContactName());
        model.put("loginUrl", ml010Vo.getLoginUrl());
        model.put("loginId", ml010Vo.getLoginId());
        model.put("cityName", ml010Vo.getCityName());
        model.put("windowBelong", ml010Vo.getWindowBelong());
        model.put("windowTelNo", ml010Vo.getWindowTelNo());
        model.put("windowFaxNo", ml010Vo.getWindowFaxNo());
        model.put("windowMailAddress", ml010Vo.getWindowMailAddress());
        return model;
    }

    /**
     * Setting property for prepare send ML020 Mail.
     * 
     * @param ml020Vo ML020Vo
     * @return Map
     */
    private Map<String, Object> settingPropertyForML020(ML020Vo ml020Vo) {
        Map<String, Object> model = new HashMap<>();
        model.put("apartmentName", ml020Vo.getApartmentName());
        model.put("contactName", ml020Vo.getContactName());
        model.put("judgeRemarks", ml020Vo.getJudgeRemarks());
        model.put("cityName", ml020Vo.getCityName());
        model.put("windowBelong", ml020Vo.getWindowBelong());
        model.put("windowTelNo", ml020Vo.getWindowTelNo());
        model.put("windowFaxNo", ml020Vo.getWindowFaxNo());
        model.put("windowMailAddress", ml020Vo.getWindowMailAddress());
        return model;
    }

    @Override
    public TBL100Entity getMansionInformationById(String apartmentId) {
        return tbl100dao.getMansionInformationById(apartmentId);
    }
    
    @Override
    public List<TBL400Entity> findAll() {
        return (List<TBL400Entity>) tbl400dao.findAll();
    }

}
