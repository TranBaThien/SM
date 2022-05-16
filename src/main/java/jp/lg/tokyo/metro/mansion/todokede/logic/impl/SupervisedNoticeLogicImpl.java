/*
 * @(#) SupervisedNoticeLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/11/28
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonProperties;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.*;
import jp.lg.tokyo.metro.mansion.todokede.dao.*;
import jp.lg.tokyo.metro.mansion.todokede.entity.*;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.SupervisedNoticeLogicForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.ISupervisedNoticeLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.SupervisedNoticeLogicVo;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL240;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL300;

@Service
public class SupervisedNoticeLogicImpl implements ISupervisedNoticeLogic {

	private static final Logger logger = LogManager.getLogger(SupervisedNoticeLogicImpl.class);

	@Autowired
	private TBL100DAO tbl100DAO;

	@Autowired
	private TBL200DAO tbl200DAO;

	@Autowired
	private TBL240DAO tbl240DAO;

	@Autowired
	private TBL300DAO tbl300DAO;

	@Autowired
	private TBM001DAO tbm001DAO;

	@Autowired
	private TBM002DAO tbm002DAO;

	@Autowired
	private SequenceUtil sequenceUtil;

	private static final String TBL200_INDIVIDUAL_OWNER = "区分所有者";
	private static final String TBM002_FIRST_TIME = "督促通知書(1回目用)";
	private static final String TBM002_SECOND_TIME = "督促通知書(2回目以降用)";
	private static final String FIRST_TIME_CODE = "1";
	private static final String SECOND_TIME_CODE = "2";
	private static final String FIRST_TIME_BAR = "15";
	private static final String SECOND_TIME_BAR = "16";
	private static final String TBL300_CD033_1 = "督促通知（一回目）";
	private static final String TBL300_CD033_2 = "督促通知（二回目以降）";
	private static final String NULL = null;
	private static final String DEFAULT_NOTIFICATION_TIME_LIMIT = "2020/09/30";
	private static final String SEPARATOR = "\n";
	private static final String SLASH_STRING = "/";

	@Override
	public TBL100Entity getApartmentInformation(String apartmentId) throws BusinessException {
		return tbl100DAO.getMansionInformationById(apartmentId);
	}

	@Override
	public TBL200Entity getNotificationInformation(String notificationNo) throws BusinessException {
		return tbl200DAO.getNotificationInfo(notificationNo);
	}

	// set value for Supervised Notice view object when Notice can not be obtained
	private SupervisedNoticeLogicVo setDataInCaseUnableToGetDunningNoticeData(TBL100Entity tbl100Entity) {
		SupervisedNoticeLogicVo supervisedNoticeLogicVo = new SupervisedNoticeLogicVo();
		
		TBM002Entity tbm002Entity = tbm002DAO.getWindowInformation(
				SecurityUtil.getUserLoggedInInfo().getCityCode(), CodeUtil.getValue(CommonConstants.CD046, TBM002_FIRST_TIME));
		
		supervisedNoticeLogicVo.setTxtAppendixNo(String.format("第%s%s号様式(第%s%s関係）",
				CommonConstants.SPACE_FULL_SIZE, CommonConstants.SPACE_FULL_SIZE, CommonConstants.SPACE_FULL_SIZE, CommonConstants.SPACE_FULL_SIZE ));

		supervisedNoticeLogicVo.setLblSupervisedNoticeTypeCode(CodeUtil.getLabel(CommonConstants.CD022, FIRST_TIME_CODE));
		
		supervisedNoticeLogicVo.setRdoMailingCode(CommonConstants.BLANK);
		
		supervisedNoticeLogicVo.setTxtRecipientNameApartment(tbl100Entity.getApartmentName());
		
		supervisedNoticeLogicVo.setTxtRecipientNameUser(CommonConstants.BLANK);
		
		supervisedNoticeLogicVo.setTxtTextAdress1(CommonConstants.BLANK);
		
		supervisedNoticeLogicVo.setLblEvidenceBar(StringUtils.isBlank(tbl100Entity.getNewestNotificationNo()) ? 
				CodeUtil.getLabel(CommonConstants.CD041, FIRST_TIME_CODE) : CodeUtil.getLabel(CommonConstants.CD041,SECOND_TIME_CODE));
		
		supervisedNoticeLogicVo.setLstEvidenceNo(CommonConstants.BLANK);
		
		supervisedNoticeLogicVo.setLblPeriodEvidence(CommonConstants.BLANK);
				
		supervisedNoticeLogicVo.setTxtTextAdress2(CommonConstants.BLANK);
		
		supervisedNoticeLogicVo.setLblNotificationFormatName(CommonConstants.BLANK);
		
		if(tbm002Entity == null)
			supervisedNoticeLogicVo.setTxaContact(CommonConstants.BLANK);
		else {
			StringBuffer contactString = new StringBuffer();
			contactString.append(tbm002Entity.getWindowBelong());
			contactString.append(SEPARATOR);
			contactString.append(tbm002Entity.getWindowMailAddress());
			contactString.append(SEPARATOR);
			contactString.append(tbm002Entity.getWindowTelNo());
			contactString.append(SEPARATOR);
			contactString.append(tbm002Entity.getWindowFaxNo());
			supervisedNoticeLogicVo.setTxaContact(contactString.toString());
		}
		
		supervisedNoticeLogicVo.setSupervisedNoticeCount(0);
		

		return supervisedNoticeLogicVo;
	}

	// set value for Supervised Notice View object when Notice can be obtained
	private SupervisedNoticeLogicVo setDataInCaseAbleToGetDunningNoticeData(TBL240Entity tbl240Entity) {
		SupervisedNoticeLogicVo supervisedNoticeLogicVo = new SupervisedNoticeLogicVo();
		
		supervisedNoticeLogicVo.setTxtAppendixNo(tbl240Entity.getAppendixNo());
		
		supervisedNoticeLogicVo.setLblSupervisedNoticeTypeCode(CodeUtil.getLabel(CommonConstants.CD022, SECOND_TIME_CODE));
		
		supervisedNoticeLogicVo.setRdoMailingCode(CodeUtil.getLabel(CommonConstants.CD018, tbl240Entity.getMailingCode()));
		
		supervisedNoticeLogicVo.setTxtRecipientNameApartment(tbl240Entity.getRecipientNameApartment());
		
		supervisedNoticeLogicVo.setTxtRecipientNameUser(tbl240Entity.getRecipientNameUser());
		
		supervisedNoticeLogicVo.setTxtTextAdress1(tbl240Entity.getTextaddress1());
		
		supervisedNoticeLogicVo.setLblEvidenceBar(CodeUtil.getLabel(CommonConstants.CD041, tbl240Entity.getEvidenceBar()));
		
		if(FIRST_TIME_BAR.equals(supervisedNoticeLogicVo.getLblEvidenceBar()))
			supervisedNoticeLogicVo.setLstEvidenceNo(CodeUtil.getLabel(CommonConstants.CD044 ,tbl240Entity.getEvidenceNo()));
		else
			supervisedNoticeLogicVo.setLstEvidenceNo(CodeUtil.getLabel(CommonConstants.CD043 ,tbl240Entity.getEvidenceNo()));
		
		supervisedNoticeLogicVo.setLblPeriodEvidence(CodeUtil.getLabel(CommonConstants.CD045, tbl240Entity.getPeriodEvidence()));
		
		supervisedNoticeLogicVo.setLblLastNoticeDate(DateTimeUtil.convertReFormatDateOnly(tbl240Entity.getNoticeDate()));
		
		supervisedNoticeLogicVo.setTxtTextAdress2(tbl240Entity.getTextaddress2());
		
		supervisedNoticeLogicVo.setLblNotificationFormatName(CodeUtil.getLabel(CommonConstants.CD032,tbl240Entity.getNotificationFormatName()));
		
		supervisedNoticeLogicVo.setTxaContact(tbl240Entity.getContact());
		
		supervisedNoticeLogicVo.setSupervisedNoticeCount(tbl240Entity.getSupervisedNoticeCount());
		
		
		return supervisedNoticeLogicVo;
	}

	@Override
	public SupervisedNoticeLogicVo getSupervisedNoticeData(String apartmentId) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
		SupervisedNoticeLogicVo supervisedNoticeLogicVo = new SupervisedNoticeLogicVo();
		
		TBL100Entity tbl100Entity = getApartmentInformation(apartmentId);
		
		TBM001Entity tbm001Entity = tbm001DAO.getMunicipalMasterInfo(SecurityUtil.getUserLoggedInInfo().getCityCode());
		
		if (tbm001Entity == null || tbl100Entity == null) {
			// Return error screen
			throw new BusinessException();
		}

		TBL240Entity entity = tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNoIsNotNull(apartmentId);

		if (entity == null)
			supervisedNoticeLogicVo = setDataInCaseUnableToGetDunningNoticeData(tbl100Entity);
		else
			supervisedNoticeLogicVo = setDataInCaseAbleToGetDunningNoticeData(entity);
		
		supervisedNoticeLogicVo.setCalNoticeDate(DateTimeFormatter.ofPattern(CommonConstants.SLASH_YYYYMMDD).format(LocalDate.now()));
		
		if((UserTypeCode.CITY).equals(SecurityUtil.getUserLoggedInInfo().getUserTypeCode()))
			supervisedNoticeLogicVo.setTxtSender(String.format("%s長%s%s",tbm001Entity.getCityName(),CommonConstants.SPACE_FULL_SIZE,tbm001Entity.getChiefName()));
		else
			supervisedNoticeLogicVo.setTxtSender(String.format("東京都知事%s%s",CommonConstants.SPACE_FULL_SIZE,tbm001Entity.getChiefName()));
		
		TBM001Entity tbm001EntityForTxtSender = tbm001DAO.getMunicipalMasterInfo(tbl100Entity.getCityCode());
		
		supervisedNoticeLogicVo.setLblAddress(String.format("東京都%s%s",tbm001EntityForTxtSender.getCityName(),tbl100Entity.getAddress()));
		
		supervisedNoticeLogicVo.setTxtDocumentNo(String.format("%s%s%s第%s号",
		        DateUtil.getOnlyFiscalJapaneseYearAsStringForSetting(),tbm001EntityForTxtSender.getDocumentNo(), CommonConstants.SPACE_FULL_SIZE, CommonConstants.SPACE_FULL_SIZE));

        supervisedNoticeLogicVo.setLblLastDocumentNo(supervisedNoticeLogicVo.getTxtDocumentNo());

		supervisedNoticeLogicVo.setLblApartmentName(tbl100Entity.getApartmentName());
		
		supervisedNoticeLogicVo.setCalNotificationTimelimit(DEFAULT_NOTIFICATION_TIME_LIMIT);
		
		TBL200Entity tbl200Entity = getNotificationInformation(tbl100Entity.getNewestNotificationNo());
		
		if(tbl200Entity == null)
			supervisedNoticeLogicVo.setRecipientNameInCaseAddressIsIndividualOwner(TBL200_INDIVIDUAL_OWNER);
		else supervisedNoticeLogicVo.setRecipientNameInCaseAddressIsIndividualOwner(String.format("%s%s%s",TBL200_INDIVIDUAL_OWNER,CommonConstants.SPACE_FULL_SIZE,tbl200Entity.getNotificationPersonName()));
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return supervisedNoticeLogicVo;
	}

	// Exclusive check (report status)
	// 排他チェック（届出状況）
	@Override
	public boolean exclusiveCheckReportStatus(SupervisedNoticeLogicForm form) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
		TBL100Entity tbl100Entity = tbl100DAO.getMansionInformationById(form.getApartmentId());

		if (StringUtils.isBlank(tbl100Entity.getNewestNotificationNo()) 
                && StringUtils.isBlank(form.getNewestNotificationNo())) {
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return true;
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return (form.getNewestNotificationNo().equals(tbl100Entity.getNewestNotificationNo()));
	}

	//Exclusive check (dunning notification count)
	// 排他チェック（督促通知回数）
	@Override
	public boolean exclusiveCheckDunningNoticeCount(SupervisedNoticeLogicForm form) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
		TBL240Entity tbl240Entity = tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNoIsNotNull(form.getApartmentId());
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
		if(tbl240Entity == null) return true;
		
		return tbl240Entity.getSupervisedNoticeCount() == form.getSupervisedNoticeCount();
	}

	
	/**
	 * Save
	 * 
	 * @param form
	 * @return
	 * @throws BusinessException
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public String superviseNoticeRegister(SupervisedNoticeLogicForm form) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
		TBL240Entity entity240 = new TBL240Entity();

		entity240 = tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNoIsNotNull(form.getApartmentId());

		entity240 = saveDataToTbl240(form, new TBL240Entity(), entity240);
		
		saveDataToTbl300(form, new TBL300Entity(), entity240);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return entity240.getSupervisedNoticeNo();
	}

	// save data to table tbl300
	private void saveDataToTbl300(SupervisedNoticeLogicForm form, TBL300Entity entity300, TBL240Entity entity240) {
		Timestamp updateDateTime = DateTimeUtil.getCurrentSystemDateTime();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		String keyNo = sequenceUtil.generateKey(CommonProperties.getProperty(TBL300),
				CommonConstants.COL_PROGRESS_RECORD_NO);

		entity300.setApartmentId(form.getApartmentId());
		entity300.setCorrespondDate(simpleDateFormat.format(updateDateTime));
		
		entity300.setTypeCode(
				entity240.getSupervisedNoticeCount() == CommonConstants.NUM_1 ? CodeUtil.getValue(CommonConstants.CD033, TBL300_CD033_1)
						: CodeUtil.getValue(CommonConstants.CD033, TBL300_CD033_2));
		
		entity300.setCorrespondTypeCode(NULL);
		entity300.setNoticeTypeCode(NULL);
		entity300.setProgressRecordOverview(NULL);
		entity300.setProgressRecordDetail(NULL);
		entity300.setNotificationMethodCode(NULL);
		entity300.setSupportCode(NULL);
		entity300.setRelatedNumber(entity240.getSupervisedNoticeNo());
		entity300.setDeleteFlag(CodeUtil.getValue(CommonConstants.CD001, CommonConstants.CD001_UNDELETEFLAG));
		entity300.setUpdateUserId(SecurityUtil.getUserLoggedInInfo().getUserId());
		entity300.setUpdateDatetime(updateDateTime);
		entity300.setProgressRecordNo(keyNo);

		logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1050_I,
				new String[] { TBL300, String.valueOf(entity300.getProgressRecordNo()) }));
		
		tbl300DAO.save(entity300);
	}

	// save data to table tbl240
	private TBL240Entity saveDataToTbl240(SupervisedNoticeLogicForm form, TBL240Entity entity240, TBL240Entity tbl240Entity) {
		String keyNo = sequenceUtil.generateKey(CommonProperties.getProperty(TBL240),
				CommonConstants.COL_SUPERVISED_NOTICE_NO);
		Timestamp updateDateTime = DateTimeUtil.getCurrentSystemDateTime();

		entity240.setSupervisedNoticeNo(keyNo);

		entity240.setApartmentId(form.getApartmentId());
		
		entity240.setAppendixNo(form.getTxtAppendixNo());
		entity240.setDocumentNo(form.getTxtDocumentNo());
		entity240.setNoticeDate(form.getCalNoticeDate().replace(SLASH_STRING, CommonConstants.BLANK));
		entity240.setSupervisedNoticeTypeCode(
				CodeUtil.getValue(CommonConstants.CD022, form.getLblSupervisedNoticeTypeCode()));
		
		entity240.setSender(form.getTxtSender());
		entity240.setMailingCode(CodeUtil.getValue(CommonConstants.CD018, form.getRdoMailingCode()));
		entity240.setRecipientNameApartment(form.getTxtRecipientNameApartment());
		entity240.setRecipientNameUser(form.getTxtRecipientNameUser());
		entity240.setTextaddress1(form.getTxtTextAdress1());
		entity240.setEvidenceBar(CodeUtil.getValue(CommonConstants.CD041, form.getLblEvidenceBar()));

		if (form.getLblEvidenceBar().equals(FIRST_TIME_BAR))
			entity240.setEvidenceNo(CodeUtil.getValue(CommonConstants.CD044, form.getLstEvidenceNo()));
		if (form.getLblEvidenceBar().equals(SECOND_TIME_BAR))
			entity240.setEvidenceNo(CodeUtil.getValue(CommonConstants.CD043, form.getLstEvidenceNo()));

		entity240.setPeriodEvidence(CodeUtil.getValue(CommonConstants.CD045, form.getLblPeriodEvidence()));

		entity240.setTextaddress2(form.getTxtTextAdress2());
		entity240.setAddress(form.getLblAddress());
		entity240.setApartmentName(form.getLblApartmentName());
		entity240.setNotificationFormatName(
				CodeUtil.getValue(CommonConstants.CD032, form.getLblNotificationFormatName()));
		entity240.setNotificationTimelimit(form.getCalNotificationTimelimit().replace(SLASH_STRING, CommonConstants.BLANK));
		entity240.setContact(form.getTxaContact());

		entity240.setNotificationNo(NULL);

		entity240.setDeleteFlag(CodeUtil.getValue(CommonConstants.CD001, "未削除"));

		entity240.setUpdateUserId(SecurityUtil.getUserLoggedInInfo().getUserId());

		entity240.setUpdateDatetime(updateDateTime);

		if (tbl240Entity != null) {
			entity240.setSupervisedNoticeCount(tbl240Entity.getSupervisedNoticeCount() + 1);
			entity240.setLastDocumentNo(tbl240Entity.getDocumentNo());

			if (tbl240Entity.getNoticeDate() != null)
				entity240.setLastNoticeDate(tbl240Entity.getNoticeDate().replace(SLASH_STRING, CommonConstants.BLANK));
		} else {

			entity240.setSupervisedNoticeCount(1);
			entity240.setLastDocumentNo(CommonConstants.BLANK);
			entity240.setLastNoticeDate(CommonConstants.BLANK);
		}

		logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1050_I,
				new String[] { TBL240, String.valueOf(entity240.getSupervisedNoticeCount()) }));

		return tbl240DAO.save(entity240);
	}

	@Override
	public TBL240Entity getSupervisedNoticeBySupervisedNoticeNo(String supervisedNoticeNo) throws BusinessException {
		return tbl240DAO.getSupervisedNoticeBySupervisedNoticeNo(supervisedNoticeNo);
	}

}
