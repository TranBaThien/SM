/*
 * @(#) ProgressRecordRegistrationLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author PVHung3
 * Create Date: 2019/12/09
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonProperties;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SequenceUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL105DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL300DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL310DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL105Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL300Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL310Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.ProgressRecordRegistrationForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IProgressRecordRegistrationLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.FileManagerInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ProgressRecordRegistrationVo;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.*;

/**
 * @author PVHung3
 *
 */
@Service
public class ProgressRecordRegistrationLogicImpl implements IProgressRecordRegistrationLogic {
	
	private static final Logger logger = LogManager.getLogger(ProgressRecordRegistrationLogicImpl.class);
	
	private static final String MESSAGE_FORM_NULL = "ProgressRecordRegistrationForm null";

	@Autowired
    private SequenceUtil sequenceUtil;

    @Autowired
    private TBL100DAO tbl100DAO;

    @Autowired
    private TBL105DAO tbl105DAO;

    @Autowired
    private TBL300DAO tbl300DAO;

    @Autowired
    private TBL310DAO tbl310DAO;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
	public boolean save(Object obj) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
		// Cast form
		ProgressRecordRegistrationForm form = mansionForm(obj);

		if (form == null) {
			throw new BusinessException(MESSAGE_FORM_NULL);
		}

		// Check in case update
		if (form != null && StringUtils.isNotEmpty(form.getProgressRecordNo())) {
			// Check record exist
			TBL300Entity entity300 = tbl300DAO.getProgressRecord(form.getProgressRecordNo());
			// Check has data and save successfully
			if (entity300 != null && !saveDataToTbl300(form, entity300, false)) {
				throw new BusinessException(String.format(CommonConstants.MSG_UPDATE_FAILED, TBL300));
			} else if (!saveDataToTbl310(form)) {
				throw new BusinessException(String.format(CommonConstants.MSG_UPDATE_FAILED, TBL310));
			}
		}
		// Check in case add new
		else if (StringUtils.isEmpty(form.getProgressRecordNo()) && StringUtils.isNotEmpty(form.getApartmentId())) {

			if (!saveDataToTbl300(form, new TBL300Entity(), true)) {
				throw new BusinessException(String.format(CommonConstants.MSG_ADD_FAILED, TBL300));
			}
			// Check if in case add new and CorrespondTypeCode = 6 (都支援対)
			// Check for save data to TBL100
			else if (CommonConstants.TOKYO_SUPPORT_CODE.equals(form.getLstCorrespondTypeCode())
					&& !saveDataToTbl100(form)) {
				throw new BusinessException(String.format(CommonConstants.MSG_UPDATE_FAILED, TBL100));
			}
			// Check if save data to Table TBL310
			else if (!saveDataToTbl310(form)) {
				throw new BusinessException(String.format(CommonConstants.MSG_UPDATE_FAILED, TBL310));
			}
		}
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return true;
	}

    @Override
    public boolean delete(Object obj) {
        return false;
    }

    /**
     * Save data to table TBL100 in case add new and Tokyo support code
     *
     * @param form
     * @return
     */
    private boolean saveDataToTbl100(ProgressRecordRegistrationForm form) {
        try {
            TBL100Entity entity100 = tbl100DAO.getMansionInformationById(form.getApartmentId());
            entity100.setSupport(form.getRdoSupportCode());
            entity100.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
            entity100.setUpdateUserId(form.getUpdateUserId());
            //Save data to table TBL100
            if (tbl100DAO.save(entity100) != null) {
            	logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1060_I, new String[] {TBL100, form.getApartmentId()}));
                return saveDataToTbl105(entity100);
            }
            return false;
        } catch (Exception ex) {
        	logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            return false;
        }
    }

    /**
     * Save data to TBL105
     *
     * @param entity100
     * @return
     */
    private boolean saveDataToTbl105(TBL100Entity entity100) {
        TBL105Entity entt105 = new TBL105Entity();
        try {
            BeanUtils.copyProperties(entt105, entity100);
            String keyNo = sequenceUtil.generateKey(CommonProperties.getProperty(TBL105), CommonConstants.COL_HISTORY_NUMBER);
            entt105.setHistoryNumber(keyNo);
            entt105.setApartmentId(entity100.getApartmentId());
            //Save data backup
            if (tbl105DAO.save(entt105) != null) {
            	logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1050_I, new String[] {TBL105, keyNo}));
            	return true;
            }
            return false;
            
        } catch (Exception ex) {
        	logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            return false;
        }
    }

    /**
     * Save data to table TBL300
     *
     * @param form
     * @param entity
     * @param isAddProgressRecord check add new or update
     * @return True || False
     */
    private boolean saveDataToTbl300(ProgressRecordRegistrationForm form, TBL300Entity entity, boolean isAddProgressRecord) {
        try {
            if (isAddProgressRecord) {
                String keyNo = sequenceUtil.generateKey(CommonProperties.getProperty(TBL300), CommonConstants.COL_PROGRESS_RECORD_NO);
                //Set again ProgressRecordNo to form to add data to table TBL310
                form.setProgressRecordNo(keyNo);
                entity.setProgressRecordNo(keyNo);
                entity.setApartmentId(form.getApartmentId());
                entity.setDeleteFlag(CommonConstants.ZERO);
            }
            entity.setSupportCode(form.getRdoSupportCode());
            //Convert string to Local Date Time
            entity.setCorrespondDate(DateTimeUtil.convertFormatDate(form.getCalCorrespondDate()));
            entity.setCorrespondTypeCode(form.getLstCorrespondTypeCode());
            entity.setNoticeTypeCode(StringUtils.isNotEmpty(form.getLstNoticeTypeCode()) ? form.getLstNoticeTypeCode(): CommonConstants.ZERO);
            entity.setProgressRecordOverview(form.getTxtProgressRecordOverview().trim());
            entity.setProgressRecordDetail(form.getTxaProgressRecordDetail());
            entity.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
            entity.setUpdateUserId(form.getUpdateUserId());

			if (tbl300DAO.save(entity) != null) {
				logger.info(MessageUtil.getMessage( isAddProgressRecord ? CommonConstants.LOG_LG1050_I : CommonConstants.LOG_LG1060_I,
                        TBL300, entity.getProgressRecordNo()));
				return true;
			}
            return false;
        } catch (Exception ex) {
        	logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            return false;
        }
    }

    /**
     * Save data to table TBL310
     *
     * @param form
     * @return True || False
     */
    private boolean saveDataToTbl310(ProgressRecordRegistrationForm form) {
        try {
            String keyNo = StringUtils.EMPTY;
            String fileName1 = form.getFilFileFromName1();
            String fileName2 = form.getFilFileFromName2();
            String fileName3 = form.getFilFileFromName3();
            TBL310Entity ettFile1 = new TBL310Entity();
            TBL310Entity ettFile2 = new TBL310Entity();
            TBL310Entity ettFile3 = new TBL310Entity();
            List<TBL310Entity> fileManagerAdd = new ArrayList<TBL310Entity>();
            // Check case delete file
            if (ArrayUtils.isNotEmpty(form.getSelectedFiles())) {
                for (String progressRecordAttachNo : form.getSelectedFiles()) {
                    //Delete file selected physical
                    tbl310DAO.deleteById(progressRecordAttachNo);
                    logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1070_I, TBL310, progressRecordAttachNo));
                }
            }

            //Choose file no 1
            if (!StringUtils.isEmpty(fileName1)) {

                keyNo = sequenceUtil.generateKey(CommonProperties.getProperty(TBL310), CommonConstants.COL_PROGRESS_RECORD_ATTACH_NO);
                ettFile1.setProgressRecordAttachNo(keyNo);
                ettFile1.setProgressRecordNo(form.getProgressRecordNo());
                ettFile1.setFile(form.getFilFileFrom1().getBytes());
                ettFile1.setFileName(form.getFilFileFromName1());
                ettFile1.setDeleteFlag(CommonConstants.ZERO);
                ettFile1.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
                ettFile1.setUpdateUserId(form.getUpdateUserId());
                fileManagerAdd.add(ettFile1);
            }
            //Choose file no 2
            if (!StringUtils.isEmpty(fileName2)) {

                keyNo = sequenceUtil.generateKey(CommonProperties.getProperty(TBL310), CommonConstants.COL_PROGRESS_RECORD_ATTACH_NO);
                ettFile2.setProgressRecordAttachNo(keyNo);
                ettFile2.setProgressRecordNo(form.getProgressRecordNo());
                ettFile2.setFile(form.getFilFileFrom2().getBytes());
                ettFile2.setFileName(form.getFilFileFromName2());
                ettFile2.setDeleteFlag(CommonConstants.ZERO);
                ettFile2.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
                ettFile2.setUpdateUserId(form.getUpdateUserId());
                fileManagerAdd.add(ettFile2);
            }
            //Choose file no 3
            if (!StringUtils.isEmpty(fileName3)) {

                keyNo = sequenceUtil.generateKey(CommonProperties.getProperty(TBL310), CommonConstants.COL_PROGRESS_RECORD_ATTACH_NO);
                ettFile3.setProgressRecordAttachNo(keyNo);
                ettFile3.setProgressRecordNo(form.getProgressRecordNo());
                ettFile3.setFile(form.getFilFileFrom3().getBytes());
                ettFile3.setFileName(form.getFilFileFromName3());
                ettFile3.setDeleteFlag(CommonConstants.ZERO);
                ettFile3.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
                ettFile3.setUpdateUserId(form.getUpdateUserId());
                fileManagerAdd.add(ettFile3);
            }
            //Check if
            if (CollectionUtils.isNotEmpty(fileManagerAdd) && tbl310DAO.saveAll(fileManagerAdd) != null) {
            	logger.info(MessageUtil.getMessage( CommonConstants.LOG_LG1050_I, TBL310, keyNo));
                return true;
            }

            return true;
        } catch (Exception ex) {
        	logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            return false;
        }
    }

    /**
     * Cast object to form
     *
     * @param obj
     * @return ProgressRecordRegistrationForm
     */
    private ProgressRecordRegistrationForm mansionForm(Object obj) {
        return (obj instanceof ProgressRecordRegistrationForm) ? (ProgressRecordRegistrationForm) obj : null;
    }

    @Override
    public ProgressRecordRegistrationVo getProgressRecordTbl300(String progressRecordNo) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        ProgressRecordRegistrationVo vo = new ProgressRecordRegistrationVo();
        try {
            TBL300Entity entity = tbl300DAO.getProgressRecord(progressRecordNo);
            if (entity != null) {
                vo.setCalCorrespondDate(DateTimeUtil.convertReFormatDate(entity.getCorrespondDate()));
                vo.setLstCorrespondTypeCode(entity.getCorrespondTypeCode());
                vo.setLstNoticeTypeCode(entity.getNoticeTypeCode());
                vo.setTxaProgressRecordDetail(entity.getProgressRecordDetail());
                vo.setTxtProgressRecordOverview(entity.getProgressRecordOverview());
                vo.setRdoSupportCode(entity.getSupportCode());
                vo.setUpdateDatetime(entity.getUpdateDatetime().toString());
            }
        } catch (Exception ex) {
        	logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(ex.getMessage());
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return vo;
    }

    @Override
    public List<FileManagerInfoVo> getProgressRecordFileInfos(String progressRecordNo) throws Exception {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        List<TBL310Entity> ett = tbl310DAO.getProgressRecord(progressRecordNo);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return copyListFileInfos(ett);
    }

    @Override
    public FileManagerInfoVo findProgressRecordFileById(String progressRecordAttachNo) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        TBL310Entity ett = tbl310DAO.findProgressRecordFileById(progressRecordAttachNo);
        FileManagerInfoVo vo = new FileManagerInfoVo();

        vo.setFile(ett.getFile());
        vo.setFileName(ett.getFileName());
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return vo;
    }

    @SuppressWarnings("unchecked")
    private <T, K> List<T> copyListFileInfos(List<K> lstSource) throws Exception {
        if (!CollectionUtils.isEmpty(lstSource)) {
            List<T> lstDest = new ArrayList<T>();
            int size = lstSource.size();
            for (int i = 0; i < size; i++) {
                T vo = (T) new FileManagerInfoVo();
                BeanUtils.copyProperties(vo, lstSource.get(i));

                lstDest.add(vo);
            }
            return lstDest;
        }
        return new ArrayList<T>();
    }


    @Override
    public boolean isUpdateLatestTbl300(String progressRecordNo, String dateTime) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        try {
            TBL300Entity entt = tbl300DAO.getProgressRecord(progressRecordNo);
            Timestamp timeStmp = DateTimeUtil.convertStringToTimestamp(dateTime);
            if (entt != null && timeStmp.compareTo(entt.getUpdateDatetime()) == 0) {
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
                return true;
            }
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return false;
        } catch (Exception ex) {
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return false;
        }

    }

	@Override
	public boolean isValidMaxCorrespondDate(String appartmentId, String corrsponseDate) {
		//Format date is always correct
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
		List<TBL300Entity> progressRecords = tbl300DAO.getProgressRecordMaxDateTime(appartmentId, corrsponseDate);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
		//If exist progressRecords is null true
		return CollectionUtils.isEmpty(progressRecords);
	}
}
