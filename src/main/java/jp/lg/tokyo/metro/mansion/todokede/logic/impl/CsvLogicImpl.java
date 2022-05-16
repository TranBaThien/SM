/*
 * @(#) CsvLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author pdquang
 * Create Date: Dec 12, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import com.opencsv.CSVWriter;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL300DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL300Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.logic.ICsvLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.IMansionInfoLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.IMunicipalMasterInfoLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.IProgressRecordLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.MansionInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.MunicipalMasterInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.StatusInformationVo;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * @author pdquang
 *
 */
@Service
public class CsvLogicImpl implements ICsvLogic {
    
    // consts for export csv GEA0110
    private static final String APARTMENT_NAME = "マンション名";
    private static final String APARTMENT_NAME_PHONETIC = "マンション名フリガナ";
    private static final String APARTMENT_ADDRESS = "住所";
    private static final String NOTIFICATION_STATUS = "届出状況";
    private static final String ACCEPT_STATUS = "受理状況";
    private static final String SUPERVISE_STATUS = "督促通知状況";
    private static final String SUPPORT_STATUS = "都支援対象";
    private static final String CORRESPOND_DATE = "対応日時";
    private static final String TYPE = "種別";
    private static final String PROGRESS_RECORD_OVERVIEW = "経過記録概要";
    private static final String PROGRESS_RECORD_DETAIL = "経過記録詳細";
    private static final String LIST_PROGRESS_RECORD_DETAIL_NULL = "ListProgressRecordByProgressRecordNos null";
    private static final String MASION_INFORMATION_BY_ID_NULL = "MansionInformationById null";
    private static final String STATUS_INFO_NULL = "statusInfoVo or municipalMasterInfoVo null";

    @Autowired
    private IProgressRecordLogic progressRecordLogic;

    @Autowired
    private IMansionInfoLogic mansionInfoLogic;

    @Autowired
    IMunicipalMasterInfoLogic municipalMasterInfoLogic;

    @Autowired
    private TBL300DAO tbl300DAO;

    private static final Logger logger = LogManager.getLogger(CsvLogicImpl.class);
    
    @Override
    public void exportCsvProgressRecord(List<String> listProgressRecordNo, OutputStream outputStream) throws BusinessException {
        try {
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            // get data for export csv
            List<TBL300Entity> listTbl300 = tbl300DAO.getListProgressRecordByProgressRecordNos(listProgressRecordNo);
            if (listTbl300 == null) {
                throw new BusinessException(LIST_PROGRESS_RECORD_DETAIL_NULL);
            }
            String apartmentId = listTbl300.get(CommonConstants.NUM_0).getApartmentId();
            StatusInformationVo statusInfoVo = progressRecordLogic.getStatusInformation(apartmentId);
            MansionInfoVo mansionInfoVo = mansionInfoLogic.getMansionInformationById(apartmentId);
            if (mansionInfoVo == null) {
                throw new BusinessException(MASION_INFORMATION_BY_ID_NULL);
            }
            MunicipalMasterInfoVo municipalMasterInfoVo = municipalMasterInfoLogic.getMunicipalMasterInfo(mansionInfoVo.getCityCode());
            if (statusInfoVo == null || municipalMasterInfoVo == null) {
                throw new BusinessException(STATUS_INFO_NULL);
            }

            // create a csv writer
            CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(outputStream, "UTF8"), CSVWriter.DEFAULT_SEPARATOR,
                                                CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

            // wirte header line 1
            String[] headerRecord = { APARTMENT_NAME, APARTMENT_NAME_PHONETIC, APARTMENT_ADDRESS,
                                      NOTIFICATION_STATUS, ACCEPT_STATUS, SUPERVISE_STATUS, SUPPORT_STATUS };
            csvWriter.writeNext(headerRecord);

            // wirte data line 2
            String apartmentAddress = mansionInfoVo.getZipCode() + CommonConstants.SPACE_FULL_SIZE 
                                      + municipalMasterInfoVo.getCityName() + mansionInfoVo.getAddress();
            String[] lineMansionInfo = { mansionInfoVo.getApartmentName(), mansionInfoVo.getApartmentNamePhonetic(),
                                         apartmentAddress, statusInfoVo.getReportStatus(), statusInfoVo.getAcceptedStatus(),
                                         statusInfoVo.getUrgeStatus(), statusInfoVo.getSupportCode() };

            csvWriter.writeNext(lineMansionInfo);

            // write header line 3
            String[] headerProgressRecord = { CORRESPOND_DATE, TYPE, PROGRESS_RECORD_OVERVIEW, PROGRESS_RECORD_DETAIL };
            csvWriter.writeNext(headerProgressRecord);

            // write line 4 of data progress record details
            String typeCode;
            for (TBL300Entity entityTbl300 : listTbl300) {
                if (entityTbl300.getTypeCode() != null) {
                    typeCode = CodeUtil.getLabel(CommonConstants.CD033, entityTbl300.getTypeCode());
                } else {
                    typeCode = CodeUtil.getLabel(CommonConstants.CD019, entityTbl300.getCorrespondTypeCode());
                }

                String[] lineProgressRecord = { entityTbl300.getCorrespondDate(), typeCode, entityTbl300.getProgressRecordOverview(),
                        cleanCsvString(entityTbl300.getProgressRecordDetail()) };
                csvWriter.writeNext(lineProgressRecord);
            }

            // close CSVwriter
            csvWriter.close();
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        } catch (IOException | SystemException e) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, e.getMessage()));
            throw new BusinessException("Export File CSV Fail!");
        }    
    }

    /**
     * cleanCsvString
     * @param progressRecorDaital String
     * @return output String
     */
    private String cleanCsvString(String progressRecorDaital) {
        String output = CommonConstants.BLANK;
        if (StringUtils.isNotEmpty(progressRecorDaital)) {
            output = progressRecorDaital.replace("\"", "\"\"").replace("\r\n", " ").replace("\r", " ").replace("\n", "");
        }
        return output; 
    }
}
