/*
 * @(#) TBL300BDAO.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/12/16
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.vo.DataAggregateResultTmpCntVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.DataAggregateResultTmpVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.DataAggregateVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ProgressRecordAcceptUserVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ProgressRecordDetailsVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ProgressRecordOtherAcceptVo;

@Repository
public class TBL300BDAO extends JdbcDaoSupport {

    public static final String CORRESPOND_DATE = "correspondDate";
    public static final String TYPE_CODE = "typeCode";
    public static final String NOTIFICATION_METHOD_CODE = "notificationMethodCode";
    public static final String RELATED_NUMBER = "relatedNumber";
    public static final String AUTHORITY_MODIFY_FLAG = "authorityModifyFlag";
    public static final String MODIFY_DETAILS = "modifyDetails";
    public static final String USER_TYPE = "userType";
    public static final String SPACE = " ";
    public static final String COMMA = ",";
    public static final String OPEN_PARENTHESIS = "(";
    public static final String CLOSE_PARENTHESIS = ")";
    public static final String TBL300_ENTITY = "tbl300Entity";
    public static final String CORRESPOND_TYPE_CODE =  "correspondTypeCode";
    public static final String SUPPORT_CODE =  "supportCode";
    public static final String PROGRESS_RECORD_OVERVIEW =  "progressRecordOverview";
    public static final String PROGRESS_RECORD_NO =  "progressRecordNo";
    public static final String NOTICE_TYPE_CODE =  "noticeTypeCode";
    public static final String YEAR_NM = "??????";
    public static final String YEAR_MONTH_NM = "??????";
    public static final String DATE_NM = "?????????";
    public static final String CITY_NM = "????????????";

    private boolean isExistTbl200;

    private boolean isExistTbl200Summary;

    private MapSqlParameterSource mapSqlParameterSource;

    public TBL300BDAO() {
    }

    @Autowired
    public TBL300BDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Get progress record accept user
     * @screen MEA0110
     * @param apartmentId String
     * @param typeCodeList {@link List} of {@link String}
     * @return {@link List} of {@link ProgressRecordAcceptUserVo}
     */
    @SuppressWarnings("unchecked")
    public List<ProgressRecordAcceptUserVo> getProgressRecordAcceptUser(String apartmentId, List<String> typeCodeList) {

        StringBuilder queryString = new StringBuilder(CommonConstants.BLANK);

        queryString.append("SELECT ");
        queryString.append("    t1.CORRESPOND_DATE          as").append(SPACE).append(CORRESPOND_DATE).append(COMMA);
        queryString.append("    t1.TYPE_CODE                as").append(SPACE).append(TYPE_CODE).append(COMMA);
        queryString.append("    t1.NOTIFICATION_METHOD_CODE as").append(SPACE).append(NOTIFICATION_METHOD_CODE).append(COMMA);
        queryString.append("    t1.RELATED_NUMBER           as").append(SPACE).append(RELATED_NUMBER).append(COMMA);
        queryString.append("    t2.AUTHORITY_MODIFY_FLAG    as").append(SPACE).append(AUTHORITY_MODIFY_FLAG).append(COMMA);
        queryString.append("    t2.MODIFY_DETAILS           as").append(SPACE).append(MODIFY_DETAILS).append(COMMA);
        queryString.append("    t3.USER_TYPE                as").append(SPACE).append(USER_TYPE);
        queryString.append(" FROM");
        queryString.append("    tbl300 t1"); // ????????????????????????TBL300???
        queryString.append("    LEFT JOIN tbl210 t2 ON (t1.RELATED_NUMBER = t2.ACCEPT_NO AND t2.DELETE_FLAG = '0')"); // ????????????TBL210???
        queryString.append("    LEFT JOIN tbl120 t3 ON (t2.ACCEPT_USER_ID = t3.USER_ID AND t3.DELETE_FLAG = '0')"); // ????????????????????????????????????????????????TBL120???
        queryString.append(" WHERE");
        queryString.append("    t1.APARTMENT_ID = :apartmentId"); // ????????????????????????????????????????????????ID????????????????????????TBL300?????????????????????ID
        queryString.append("    AND t1.TYPE_CODE IN (:typeCodeList)"); // ????????????????????????TBL300?????????????????????=????????????????????????????????????????????????????????????CD033???????????????
        queryString.append("    AND t1.DELETE_FLAG = '0'"); // ????????????????????????TBL300?????????????????????????????????
        queryString.append(" ORDER BY t1.CORRESPOND_DATE DESC"); // ?????????????????????TBL300???????????????????????????

        Map<String, Object> mapParam = new HashedMap();
        mapParam.put("apartmentId", apartmentId);
        mapParam.put("typeCodeList", typeCodeList);

        return namedParameterJdbcTemplate.query(queryString.toString(), new MapSqlParameterSource(mapParam), new ProgressRecordAcceptUserMapper());
    }

    /**
     * Get Progress record detail by apartment id
     * @screen GEA0110
     * @param apartmentId String
     * @return {@link List} of {@link ProgressRecordDetailsVo}
     */
    @SuppressWarnings("unchecked")
    public List<ProgressRecordDetailsVo> getListProgressRecordDetailByApartmentId(String apartmentId) {

        StringBuilder queryString = new StringBuilder(CommonConstants.BLANK);

        queryString.append("SELECT ");
        queryString.append("    t1.CORRESPOND_DATE          as").append(SPACE).append(CORRESPOND_DATE).append(COMMA);
        queryString.append("    t1.TYPE_CODE                as").append(SPACE).append(TYPE_CODE).append(COMMA);
        queryString.append("    t1.CORRESPOND_TYPE_CODE     as").append(SPACE).append(CORRESPOND_TYPE_CODE).append(COMMA);
        queryString.append("    t1.SUPPORT_CODE             as").append(SPACE).append(SUPPORT_CODE).append(COMMA);
        queryString.append("    t1.PROGRESS_RECORD_OVERVIEW as").append(SPACE).append(PROGRESS_RECORD_OVERVIEW).append(COMMA);
        queryString.append("    t1.RELATED_NUMBER           as").append(SPACE).append(RELATED_NUMBER).append(COMMA);
        queryString.append("    t1.NOTICE_TYPE_CODE         as").append(SPACE).append(NOTICE_TYPE_CODE).append(COMMA);
        queryString.append("    t1.PROGRESS_RECORD_NO       as").append(SPACE).append(PROGRESS_RECORD_NO).append(COMMA);
        queryString.append("    t2.AUTHORITY_MODIFY_FLAG    as").append(SPACE).append(AUTHORITY_MODIFY_FLAG).append(COMMA);
        queryString.append("    t3.USER_TYPE                as").append(SPACE).append(USER_TYPE);
        queryString.append(" FROM");
        queryString.append("    tbl300 t1"); // ????????????????????????TBL300???
        queryString.append("    LEFT JOIN tbl210 t2 ON (t1.RELATED_NUMBER = t2.ACCEPT_NO AND t2.DELETE_FLAG = '0')"); // ????????????TBL210???
        queryString.append("    LEFT JOIN tbl120 t3 ON (t2.ACCEPT_USER_ID = t3.USER_ID AND t3.DELETE_FLAG = '0')"); // ????????????????????????????????????????????????TBL120???
        queryString.append(" WHERE");
        queryString.append("    t1.APARTMENT_ID = :apartmentId"); // ????????????????????????????????????????????????ID????????????????????????TBL300?????????????????????ID
        queryString.append("    AND t1.DELETE_FLAG = '0'"); // ????????????????????????TBL300?????????????????????????????????
        queryString.append(" ORDER BY t1.CORRESPOND_DATE DESC"); // ?????????????????????TBL300???????????????????????????

        Map<String, Object> mapParam = new HashedMap();
        mapParam.put("apartmentId", apartmentId);

        return namedParameterJdbcTemplate.query(queryString.toString(), new MapSqlParameterSource(mapParam), new ProgressRecordDetailMapper());
    }

    class ProgressRecordAcceptUserMapper implements RowMapper<ProgressRecordAcceptUserVo> {

        @Override
        public ProgressRecordAcceptUserVo mapRow(ResultSet rs, int rowNum) throws SQLException {

            ProgressRecordAcceptUserVo vo = new ProgressRecordAcceptUserVo();
            vo.setCorrespondDate(rs.getString(CORRESPOND_DATE));
            vo.setTypeCode(rs.getString(TYPE_CODE));
            vo.setNotificationMethodCode(rs.getString(NOTIFICATION_METHOD_CODE));
            vo.setRelatedNumber(rs.getString(RELATED_NUMBER));
            vo.setAuthorityModifyFlag(rs.getString(AUTHORITY_MODIFY_FLAG));
            vo.setModifyDetails(rs.getString(MODIFY_DETAILS));
            vo.setUserType(rs.getString(USER_TYPE));

            return vo;
        }
    }

    class ProgressRecordOtherAcceptMapper implements RowMapper<ProgressRecordOtherAcceptVo> {

        @Override
        public ProgressRecordOtherAcceptVo mapRow(ResultSet rs, int rowNum) throws SQLException {

            ProgressRecordOtherAcceptVo vo = new ProgressRecordOtherAcceptVo();
            vo.setCorrespondDate(rs.getString(CORRESPOND_DATE));
            vo.setTypeCode(rs.getString(TYPE_CODE));
            vo.setNotificationMethodCode(rs.getString(NOTIFICATION_METHOD_CODE));
            vo.setRelatedNumber(rs.getString(RELATED_NUMBER));

            return vo;
        }
    }

    class ProgressRecordDetailMapper implements RowMapper<ProgressRecordDetailsVo> {

        @Override
        public ProgressRecordDetailsVo mapRow(ResultSet rs, int rowNum) throws SQLException {

            ProgressRecordDetailsVo vo = new ProgressRecordDetailsVo();
            vo.setCorrespondDate(rs.getString(CORRESPOND_DATE));
            vo.setProgressRecordNo(rs.getString(PROGRESS_RECORD_NO));
            vo.setTypeCode(rs.getString(TYPE_CODE));
            vo.setCorrespondTypeCode(rs.getString(CORRESPOND_TYPE_CODE));
            vo.setSupportCode(rs.getString(SUPPORT_CODE));
            vo.setProgressRecordOverview(rs.getString(PROGRESS_RECORD_OVERVIEW));
            vo.setRelatedNumber(rs.getString(RELATED_NUMBER));
            vo.setNoticeTypeCode(rs.getString(NOTICE_TYPE_CODE));
            vo.setAuthorityModifyFlag(rs.getString(AUTHORITY_MODIFY_FLAG));
            vo.setUserType(rs.getString(USER_TYPE));

            return vo;
        }
    }

    private Map<String, String> getMapCountSql(DataAggregateVo vo) {
        Map<String, String> mapCountSql = new HashMap<String, String>();
        StringBuilder selectStr = new StringBuilder();
        StringBuilder fromStr = new StringBuilder();
        StringBuilder conditionStrDef = new StringBuilder();
        StringBuilder groupByStr = getGroupByStr();;
        StringBuilder conditionStrSummary;
        StringBuilder sql;
        String aggregateCredit = vo.getRdoAggregateCredit();

        int numSummaryItem;
        for (String summaryItem : vo.getChkSummaryItem()) {
            numSummaryItem = Integer.parseInt(summaryItem);
            sql = new StringBuilder();

            selectStr = getSqlSelectCntStr(aggregateCredit, numSummaryItem);
            fromStr = getSqlFromStr(numSummaryItem);
            conditionStrSummary = getConditionSummaryStr(numSummaryItem);
            conditionStrDef = getConditionDefStr(vo, numSummaryItem, true);

            // SQL
            sql.append(selectStr).append(fromStr);
            if (isExistTbl200() || isExistTbl200Summary()) {
                sql.append(
                        " LEFT JOIN tbl200 T2 ON ( T1.NEWEST_NOTIFICATION_NO = T2.NOTIFICATION_NO AND T2.DELETE_FLAG = '0' ) ");
            }
            sql.append(conditionStrDef).append(conditionStrSummary).append(groupByStr);

            mapCountSql.put(summaryItem, sql.toString());
        }

        return mapCountSql;
    }

    private StringBuilder getGroupByStr() {
        StringBuilder groupByStr = new StringBuilder();

        groupByStr.append(" GROUP BY aggregateCredit ");

        return groupByStr;
    }

    private StringBuilder getSqlSelectCntStr(String aggregateCredit, int numSummaryItem) {
        StringBuilder sqlCnt = new StringBuilder(CommonConstants.BLANK);
        String yearCdVal = CodeUtil.getValue(CommonConstants.CD058, YEAR_NM);
        String yearMonthCdVal = CodeUtil.getValue(CommonConstants.CD058, YEAR_MONTH_NM);
        String dateCdVal = CodeUtil.getValue(CommonConstants.CD058, DATE_NM);
        String cityCdVal = CodeUtil.getValue(CommonConstants.CD058, CITY_NM);

        sqlCnt.append(" SELECT ").append(" COUNT(1) as cnt, ");
        switch (numSummaryItem) {
        case 1:
        case 2:
        case 3:
            if (aggregateCredit.equals(yearCdVal)) {
                sqlCnt.append(" CASE ")
                        .append(" WHEN SUBSTRING(DATE_FORMAT(T5.UPDATE_DATETIME ,'%Y%m%d'), 5, 2) <= 3 ")
                        .append(" THEN SUBSTRING(DATE_FORMAT(T5.UPDATE_DATETIME ,'%Y%m%d'), 1, 4) - 1 ")
                        .append(" ELSE SUBSTRING(DATE_FORMAT(T5.UPDATE_DATETIME ,'%Y%m%d'), 1, 4) ")
                        .append(" END ");
            } else if (aggregateCredit.equals(yearMonthCdVal)) {
                sqlCnt.append(" SUBSTRING(DATE_FORMAT(T5.UPDATE_DATETIME ,'%Y%m%d'), 1, 6) ");
            } else if (aggregateCredit.equals(dateCdVal)) {
                sqlCnt.append(" DATE_FORMAT(T5.UPDATE_DATETIME ,'%Y%m%d') ");
            } else if (aggregateCredit.equals(cityCdVal)) {
                sqlCnt.append(" T1.CITY_CODE ");
            }
            break;

        default:
            if (aggregateCredit.equals(yearCdVal)) {
                sqlCnt.append(" CASE ")
                        .append(" WHEN LPAD(SUBSTRING(T3.CORRESPOND_DATE, 5, 2), 2, 0) <= 3 ")
                        .append(" THEN LPAD(SUBSTRING(T3.CORRESPOND_DATE, 1, 4), 4, 0) - 1 ")
                        .append(" ELSE LPAD(SUBSTRING(T3.CORRESPOND_DATE, 1, 4), 4, 0) ")
                        .append(" END ");
            } else if (aggregateCredit.equals(yearMonthCdVal)) {
                sqlCnt.append(" LPAD(SUBSTRING(T3.CORRESPOND_DATE, 1, 6), 6, 0) ");
            } else if (aggregateCredit.equals(dateCdVal)) {
                sqlCnt.append(" LPAD(SUBSTRING(T3.CORRESPOND_DATE, 1, 8), 8, 0) ");
            } else if (aggregateCredit.equals(cityCdVal)) {
                sqlCnt.append(" T1.CITY_CODE ");
            }

            break;
        }
        sqlCnt.append(" as aggregateCredit ");

        return sqlCnt;
    }

    private Map<String, String> getMapSql(DataAggregateVo vo) {
        Map<String, String> mapSql = new HashMap<String, String>();
        StringBuilder selectStr = new StringBuilder();
        StringBuilder fromStr = new StringBuilder();
        StringBuilder conditionStrDef = new StringBuilder();
        StringBuilder conditionStrSummary = new StringBuilder();
        StringBuilder sql;
        String aggregateCredit = vo.getRdoAggregateCredit();

        int numSummaryItem;
        for (String summaryItem : vo.getChkSummaryItem()) {
            sql = new StringBuilder();
            numSummaryItem = Integer.parseInt(summaryItem);

            selectStr = getSqlSelectStr(aggregateCredit, numSummaryItem);
            fromStr = getSqlFromStr(numSummaryItem);
            conditionStrSummary = getConditionSummaryStr(numSummaryItem);
            conditionStrDef = getConditionDefStr(vo, numSummaryItem, false);

            // SQL
            sql.append(selectStr).append(fromStr);
            if (isExistTbl200() || isExistTbl200Summary()) {
                sql.append(
                        " LEFT JOIN tbl200 T2 ON ( T1.NEWEST_NOTIFICATION_NO = T2.NOTIFICATION_NO AND T2.DELETE_FLAG = '0' ) ");
            }
            sql.append(conditionStrDef).append(conditionStrSummary);

            mapSql.put(summaryItem, sql.toString());
        }

        return mapSql;
    }

    private StringBuilder getSqlSelectStr(String aggregateCredit, int numSummaryItem) {
        StringBuilder sql = new StringBuilder(CommonConstants.BLANK);
        String yearCdVal = CodeUtil.getValue(CommonConstants.CD058, YEAR_NM);
        String yearMonthCdVal = CodeUtil.getValue(CommonConstants.CD058, YEAR_MONTH_NM);
        String dateCdVal = CodeUtil.getValue(CommonConstants.CD058, DATE_NM);
        String cityCdVal = CodeUtil.getValue(CommonConstants.CD058, CITY_NM);

        sql.append(" SELECT ")
                .append(" T1.APARTMENT_ID as apartmentId, ");

        switch (numSummaryItem) {
        case 1:
        case 2:
        case 3:
            sql.append(" T1.NOTIFICATION_KBN as notificationKbn, ");
            if (aggregateCredit.equals(yearCdVal)) {
                sql.append(" CASE ")
                        .append(" WHEN SUBSTRING(DATE_FORMAT(T5.UPDATE_DATETIME ,'%Y%m%d'), 5, 2) <= 3 ")
                        .append(" THEN SUBSTRING(DATE_FORMAT(T5.UPDATE_DATETIME ,'%Y%m%d'), 1, 4) - 1 ")
                        .append(" ELSE SUBSTRING(DATE_FORMAT(T5.UPDATE_DATETIME ,'%Y%m%d'), 1, 4) ")
                        .append(" END ");
            } else if (aggregateCredit.equals(yearMonthCdVal)) {
                sql.append(" SUBSTRING(DATE_FORMAT(T5.UPDATE_DATETIME ,'%Y%m%d'), 1, 6) ");
            } else if (aggregateCredit.equals(dateCdVal)) {
                sql.append(" SUBSTRING(DATE_FORMAT(T5.UPDATE_DATETIME ,'%Y%m%d'), 1, 8) ");
            } else if (aggregateCredit.equals(cityCdVal)) {
                sql.append(" T1.CITY_CODE ");
            }

            break;
        default:
            sql.append(" COALESCE(T3.CORRESPOND_TYPE_CODE, T3.TYPE_CODE) as typeCode, ");
            if (aggregateCredit.equals(yearCdVal)) {
                sql.append(" CASE ")
                        .append(" WHEN LPAD(SUBSTRING(T3.CORRESPOND_DATE, 5, 2), 2, 0) <= 3 ")
                        .append(" THEN LPAD(SUBSTRING(T3.CORRESPOND_DATE, 1, 4), 4, 0) - 1 ")
                        .append(" ELSE LPAD(SUBSTRING(T3.CORRESPOND_DATE, 1, 4), 4, 0) ")
                        .append(" END ");
            } else if (aggregateCredit.equals(yearMonthCdVal)) {
                sql.append(" LPAD(SUBSTRING(T3.CORRESPOND_DATE, 1, 6), 6, 0) ");
            } else if (aggregateCredit.equals(dateCdVal)) {
                sql.append(" LPAD(SUBSTRING(T3.CORRESPOND_DATE, 1, 8), 8, 0) ");
            } else if (aggregateCredit.equals(cityCdVal)) {
                sql.append(" T1.CITY_CODE ");
            }

            break;
        }

        sql.append(" as aggregateCredit ");

        return sql;
    }

    private StringBuilder getSqlFromStr(int numSummaryItem) {
        StringBuilder sqlFromStr = new StringBuilder();

        sqlFromStr.append(" FROM ").append(" tbl100 T1 ");
        switch (numSummaryItem) {
        case 1:
        case 2:
        case 3:
            sqlFromStr.append(" LEFT JOIN tbl105 T5 ON ( T1.APARTMENT_ID = T5.APARTMENT_ID AND T5.DELETE_FLAG = '0' ) ");
            break;

        default:
            sqlFromStr.append(" LEFT JOIN tbl300 T3 ON ( T1.APARTMENT_ID = T3.APARTMENT_ID AND T3.DELETE_FLAG = '0' ) ");
            break;
        }

        return sqlFromStr;
    }

    /**
     * Get default condition
     * @param vo DataAggregateVo
     * @param numSummaryItem
     * @param isCount
     * @return StringBuilder
     */
    private StringBuilder getConditionDefStr(DataAggregateVo vo, int numSummaryItem, Boolean isCount) {
        StringBuilder sql = new StringBuilder(CommonConstants.BLANK);
        MapSqlParameterSource mapParam = new MapSqlParameterSource();;

        this.setExistTbl200(false);

        sql.append(" WHERE ").append(" T1.DELETE_FLAG = '0' ");

        // ??????????????????
        String cldPeriodFrom = vo.getCldPeriodFrom();
        // ??????????????????
        String cldPeriodTo = vo.getCldPeriodTo();

        switch (numSummaryItem) {
        case 1:
        case 2:
        case 3:
            if (isCount) {
                // ??????????????????
                if (!StringUtils.isBlank(cldPeriodFrom)) {
                    sql.append(" AND DATE_FORMAT(T5.UPDATE_DATETIME ,'%Y%m%d') ")
                        .append(" >= ")
                        .append(" DATE_FORMAT(:cldPeriodFrom ,'%Y%m%d') ");

                    mapParam.addValue("cldPeriodFrom", cldPeriodFrom);
                }
            }
            // ??????????????????
            if (!StringUtils.isBlank(cldPeriodTo)) {
                sql.append(" AND DATE_FORMAT(T5.UPDATE_DATETIME ,'%Y%m%d') ")
                    .append(" <= ")
                    .append(" DATE_FORMAT(:cldPeriodTo ,'%Y%m%d') ");

                mapParam.addValue("cldPeriodTo", cldPeriodTo);
            }
            break;

        default:
            // ??????????????????
            if (!StringUtils.isBlank(cldPeriodFrom)) {
                sql.append(" AND LPAD(SUBSTRING(T3.CORRESPOND_DATE, 1, 8), 8, 0) ")
                    .append(" >= ")
                    .append(" DATE_FORMAT(:cldPeriodFrom ,'%Y%m%d') ");

                mapParam.addValue("cldPeriodFrom", cldPeriodFrom);
            }
            // ??????????????????
            if (!StringUtils.isBlank(cldPeriodTo)) {
                sql.append(" AND LPAD(SUBSTRING(T3.CORRESPOND_DATE, 1, 8), 8, 0) ")
                    .append(" <= ")
                    .append(" DATE_FORMAT(:cldPeriodTo ,'%Y%m%d') ");

                mapParam.addValue("cldPeriodTo", cldPeriodTo);
            }
            break;
        }

        // ?????????????????????
        String cldNewBulidingFrom = vo.getCldNewBulidingFrom();
        if (!StringUtils.isBlank(cldNewBulidingFrom)) {
            sql.append(" AND DATE_FORMAT(CONCAT(T1.BUILD_YEAR ,'/', T1.BUILD_MONTH,'/', T1.BUILD_DAY),'%Y/%m/%d') ")
                .append(" >= ")
                .append(" DATE_FORMAT(:cldNewBulidingFrom ,'%Y/%m/%d') ");

            mapParam.addValue("cldNewBulidingFrom", cldNewBulidingFrom);
        }

        // ?????????????????????
        String cldNewBulidingTo = vo.getCldNewBulidingTo();
        if (!StringUtils.isBlank(cldNewBulidingTo)) {
            sql.append(" AND DATE_FORMAT(CONCAT(T1.BUILD_YEAR ,'/', T1.BUILD_MONTH,'/', T1.BUILD_DAY),'%Y/%m/%d') ")
                .append(" <= ")
                .append(" DATE_FORMAT(:cldNewBulidingTo ,'%Y/%m/%d') ");

            mapParam.addValue("cldNewBulidingTo", cldNewBulidingTo);
        }

        // ??????????????????
        String txtHouseNumberFrom = vo.getTxtHouseNumberFrom();
        if (!StringUtils.isBlank(txtHouseNumberFrom)) {
            sql.append(" AND T1.HOUSE_NUMBER >=  :txtHouseNumberFrom");
            txtHouseNumberFrom = txtHouseNumberFrom.replaceAll(",$", "");

            mapParam.addValue("txtHouseNumberFrom", txtHouseNumberFrom);
        }

        // ??????????????????
        String txtHouseNumberTo = vo.getTxtHouseNumberTo();
        if (!StringUtils.isBlank(txtHouseNumberTo)) {
            sql.append(" AND T1.HOUSE_NUMBER <= :txtHouseNumberTo");
            txtHouseNumberTo = txtHouseNumberTo.replaceAll(",$", "");

            mapParam.addValue("txtHouseNumberTo", txtHouseNumberTo);
        }

        // ????????????
        if (!isCount) {
            String cityCode = vo.getCityCode();
            if (!StringUtils.isBlank(cityCode)) {
                sql.append(" AND T1.CITY_CODE = :cityCode");

                mapParam.addValue("cityCode", cityCode);
            }
        }

        // ????????????
        String rdoNotificationStatus = vo.getRdoNotificationStatus();
        String valUnReportCD036 = CodeUtil.getValue("CD036", "??????");
        String valReportCD036 = CodeUtil.getValue("CD036", "?????????");
        if (valUnReportCD036.equals(rdoNotificationStatus)) {
            sql.append(" AND T1.NEXT_NOTIFICATION_DATE <= date_format(now(),'%Y%m%d') ");
        } else if (valReportCD036.equals(rdoNotificationStatus)) {
            sql.append(" AND T1.NEXT_NOTIFICATION_DATE > date_format(now(),'%Y%m%d') ");
        }

        // ????????????????????????
        String rdoAcceptanceStatusNew = vo.getRdoAcceptanceStatusNew();
        String valNotAcceptCD030 = CodeUtil.getValue("CD030", "?????????");
        String valAcceptCD030 = CodeUtil.getValue("CD030", "?????????");
        if (valNotAcceptCD030.equals(rdoAcceptanceStatusNew)) {
            sql.append(" AND ( ")
                .append(" T1.NEXT_NOTIFICATION_DATE <= date_format(now(),'%Y%m%d') ")
                .append(" OR ")
                .append(" (T1.NEWEST_NOTIFICATION_NO = T2.NOTIFICATION_NO AND T2.ACCEPT_STATUS = 1 ) ")
                .append(" ) ");

            this.setExistTbl200(true);
        } else if (valAcceptCD030.equals(rdoAcceptanceStatusNew)) {
            sql.append(" AND T1.NEXT_NOTIFICATION_DATE > date_format(now(),'%Y%m%d') ")
                .append(" AND T1.NEWEST_NOTIFICATION_NO = T2.NOTIFICATION_NO ")
                .append(" AND T2.ACCEPT_STATUS = 2 ")
                .append(" AND T2.NOTIFICATION_TYPE = 1 ");

            this.setExistTbl200(true);
        }

        // ????????????????????????
        String rdoAcceptanceStatusChange = vo.getRdoAcceptanceStatusChange();
        if (valNotAcceptCD030.equals(rdoAcceptanceStatusChange)) {
            sql.append(" AND ( ")
                .append(" T1.NEXT_NOTIFICATION_DATE <= date_format(now(),'%Y%m%d') ")
                .append(" OR ")
                .append(" (T1.NEWEST_NOTIFICATION_NO = T2.NOTIFICATION_NO AND T2.ACCEPT_STATUS = 1 ) ")
                .append(" ) ");

            this.setExistTbl200(true);
        } else if (valAcceptCD030.equals(rdoAcceptanceStatusChange)) {
            sql.append(" AND T1.NEXT_NOTIFICATION_DATE > date_format(now(),'%Y%m%d') ")
                .append(" AND T1.NEWEST_NOTIFICATION_NO = T2.NOTIFICATION_NO ")
                .append(" AND T2.ACCEPT_STATUS = 2 ")
                .append(" AND T2.CHANGE_COUNT > 0 ");

            this.setExistTbl200(true);
        }

        // ???????????????
        String rdoSupportTarget = vo.getRdoSupportTarget();
        String valTargetCD021 = CodeUtil.getValue("CD021", "??????");
        String valNotAppli = CodeUtil.getValue("CD021", "?????????");
        if (valTargetCD021.equals(rdoSupportTarget)) {
            sql.append(" AND T1.SUPPORT = 1 ");
        } else if (valNotAppli.equals(rdoSupportTarget)) {
            sql.append(" AND T1.SUPPORT = 2 ");
        }

        // ????????????
        String rdoGroup = vo.getRdoGroup();
        String valAruCd053 = CodeUtil.getValue("CD053", "??????");
        String valNaiCd053 = CodeUtil.getValue("CD053", "??????");
        if (valAruCd053.equals(rdoGroup)) {
            sql.append(" AND T2.GROUP_CODE = 1 ");

            this.setExistTbl200(true);
        } else if (valNaiCd053.equals(rdoGroup)) {
            sql.append(" AND T2.GROUP_CODE = 2 ");

            this.setExistTbl200(true);
        }

        // ????????????
        String rdoManager = vo.getRdoManager();
        String valIruCd009 = CodeUtil.getValue("CD009", "??????");
        String valINaiCd009 = CodeUtil.getValue("CD009", "?????????");
        if (valIruCd009.equals(rdoManager)) {
            sql.append(" AND T2.MANAGER_CODE = 1 ");

            this.setExistTbl200(true);
        } else if (valINaiCd009.equals(rdoManager)) {
            sql.append(" AND T2.MANAGER_CODE = 2 ");

            this.setExistTbl200(true);
        }

        // ????????????
        String rdoRule = vo.getRdoRule();
        if (valAruCd053.equals(rdoRule)) {
            sql.append(" AND T2.RULE_CODE = 1 ");

            this.setExistTbl200(true);
        } else if (valNaiCd053.equals(rdoRule)) {
            sql.append(" AND T2.RULE_CODE = 2 ");

            this.setExistTbl200(true);
        }

        // ????????????????????????
        String rdoOneyearOver = vo.getRdoOneyearOver();
        if (valAruCd053.equals(rdoOneyearOver)) {
            sql.append(" AND T2.OPEN_ONEYEAR_OVER = 1 ");

            this.setExistTbl200(true);
        } else if (valNaiCd053.equals(rdoOneyearOver)) {
            sql.append(" AND T2.OPEN_ONEYEAR_OVER = 2 ");

            this.setExistTbl200(true);
        }

        // ??????????????????
        String rdoMinutes = vo.getRdoMinutes();
        if (valAruCd053.equals(rdoMinutes)) {
            sql.append(" AND T2.MINUTES = 1 ");

            this.setExistTbl200(true);
        } else if (valNaiCd053.equals(rdoMinutes)) {
            sql.append(" AND T2.MINUTES = 2 ");

            this.setExistTbl200(true);
        }

        // ?????????
        String rdoManagementCost = vo.getRdoManagementCost();
        if (valAruCd053.equals(rdoManagementCost)) {
            sql.append(" AND T2.MANAGEMENT_COST_CODE = 1 ");

            this.setExistTbl200(true);
        } else if (valNaiCd053.equals(rdoManagementCost)) {
            sql.append(" AND T2.MANAGEMENT_COST_CODE = 2 ");

            this.setExistTbl200(true);
        }

        // ???????????????
        String rdoRepairCost = vo.getRdoRepairCost();
        if (valAruCd053.equals(rdoRepairCost)) {
            sql.append(" AND T2.REPAIR_COST_CODE = 1 ");

            this.setExistTbl200(true);
        } else if (valNaiCd053.equals(rdoRepairCost)) {
            sql.append(" AND T2.REPAIR_COST_CODE = 2 ");

            this.setExistTbl200(true);
        }

        // ??????????????????????????? ??????????????????????????????
        String rdoRepairPlan = vo.getRdoRepairPlan();
        if (valAruCd053.equals(rdoRepairPlan)) {
            sql.append(" AND T2.REPAIR_PLAN_CODE = 1 ");

            this.setExistTbl200(true);
        } else if (valNaiCd053.equals(rdoRepairPlan)) {
            sql.append(" AND T2.REPAIR_PLAN_CODE = 2 ");

            this.setExistTbl200(true);
        }

        // ?????????????????????
        if ("on".equals(vo.getChkInadequateManagement())) {
            sql.append(" AND ( ")
                .append(" T2.GROUP_CODE = 2 ") // ?????????????????????
                .append(" OR T2.MANAGER_CODE = 2 ") // ?????????????????????
                .append(" OR T2.REPAIR_PLAN_CODE = 2 ") // ???????????????????????????
                .append(" OR T2.RULE_CODE = 2 ") // ?????????????????????
                .append(" OR T2.OPEN_ONEYEAR_OVER = 2 ") // ?????????1????????????????????????
                .append(" OR T2.MINUTES = 2 ") // ??????????????????
                .append(" OR T2.MANAGEMENT_COST_CODE = 2 ") // ??????????????????
                .append(" OR T2.REPAIR_COST_CODE = 2 ") // ????????????????????????
                .append(" OR T2.REPAIR_PLAN_CODE = 2 ") // ???????????????????????????
                .append(" ) ");

            this.setExistTbl200(true);
        }

        this.setMapSqlParameterSource(mapParam);

        return sql;
    }

    private StringBuilder getConditionSummaryStr(int numSummaryItem) {
        this.setExistTbl200Summary(false);
        StringBuilder sql = new StringBuilder();

        switch (numSummaryItem) {
        case 1:
            // ?????????????????????
            break;
        case 2:
            // ???????????????????????????
            sql.append(" AND T1.NOTIFICATION_KBN = 2 "); // AND ?????????????????????(TBL100).??????????????????= ?????????CD029???
            break;
        case 3:
            // ??????????????????????????????
            sql.append(" AND T1.NOTIFICATION_KBN = 1 "); // AND ?????????????????????(TBL100).??????????????????= ?????????CD029???
            break;
        case 4:
            // ????????????????????????
            sql.append(" AND T3.TYPE_CODE = 1 ") // AND ????????????(TBL300).??????????????? = ?????????CD033???
                    .append(" AND T3.RELATED_NUMBER = T2.NOTIFICATION_NO ") // AND ????????????(TBL300).???????????? = ????????????(TBL200).????????????
                    .append(" AND T2.NOTIFICATION_TYPE = 1 ") // AND ????????????(TBL200).???????????? = ?????????CD003???AND
                    .append(" AND T2.CHANGE_COUNT = 0 "); // AND ????????????(TBL200).???????????? = 0

            this.setExistTbl200Summary(true);
            break;
        case 5:
            // ??????????????????????????????
            sql.append(" AND T3.TYPE_CODE = 3 ") // AND ????????????(TBL300).??????????????? = ???????????????CD033???
                    .append(" AND T3.RELATED_NUMBER = T2.NOTIFICATION_NO ") // AND ????????????(TBL300).???????????? = ????????????(TBL200).????????????
                    .append(" AND T2.NOTIFICATION_TYPE = 1 ") // AND ????????????(TBL200).???????????? = ?????????CD003???
                    .append(" AND T2.CHANGE_COUNT = 0 "); // AND ????????????(TBL200).???????????? = 0

            this.setExistTbl200Summary(true);
            break;
        case 6:
            // ????????????
            sql.append(" AND T3.TYPE_CODE = 5 "); // AND ????????????(TBL300).??????????????? = ???????????????CD033???
            break;
        case 7:
            // ????????????????????????
            sql.append(" AND T3.TYPE_CODE = 2 "); // AND ????????????(TBL300).??????????????? = ???????????????CD033???
            break;
        case 8:
            // ??????????????????????????????
            sql.append(" AND T3.TYPE_CODE = 4 ");// AND ????????????(TBL300).??????????????? = ?????????????????????CD033???
            break;
        case 9:
            // ??????
            sql.append(" AND ( T3.TYPE_CODE = 7 ") // AND ???????????????(TBL300).??????????????? = ??????????????????????????????CD033???
                    .append(" OR T3.TYPE_CODE = 8 ) "); // OR ????????????(TBL300).??????????????? = ?????????????????????????????????CD033??????
            break;
        case 10:
            // ??????????????????????????????????????????????????????
            sql.append(" AND T3.CORRESPOND_TYPE_CODE = '07' "); // AND ????????????(TBL300).????????????????????? = ?????????????????????????????????????????????????????????CD019???
            break;
        case 11:
            // ?????????????????????(??????????????????)?????????
            sql.append(" AND T3.CORRESPOND_TYPE_CODE = '08' "); // AND ????????????(TBL300).????????????????????? = ?????????????????????(??????????????????)????????????CD019???
            break;
        case 12:
            // ?????????????????????(??????????????????)?????????
            sql.append(" AND T3.CORRESPOND_TYPE_CODE = '09' "); // AND ????????????(TBL300).????????????????????? = ????????????????????????(??????????????????)????????????CD019???
            break;
        case 13:
            // ?????????????????????????????????????????????????????????
            sql.append(" AND T3.CORRESPOND_TYPE_CODE = '10' "); // AND ????????????(TBL300).????????????????????? = ????????????????????????????????????????????????????????????CD019???
            break;
        case 14:
            // ?????????????????????(??????????????????)?????????
            sql.append(" AND T3.CORRESPOND_TYPE_CODE = '11' "); // AND ????????????(TBL300).????????????????????? = ?????????????????????(??????????????????)????????????CD019???
            break;
        case 15:
            // ?????????????????????(??????????????????)?????????
            sql.append(" AND T3.CORRESPOND_TYPE_CODE = '12' "); // AND ????????????(TBL300).????????????????????? = ?????????????????????(??????????????????)????????????CD019???
            break;
        case 16:
            // ?????????????????????????????????????????????????????????
            sql.append(" AND T3.CORRESPOND_TYPE_CODE = '13' "); // AND ????????????(TBL300).????????????????????? = ????????????????????????????????????????????????????????????CD019???
            break;
        case 17:
            // ?????????????????????(??????????????????)?????????
            sql.append(" AND T3.CORRESPOND_TYPE_CODE = '14' "); // AND ????????????(TBL300).????????????????????? = ?????????????????????(??????????????????)????????????CD019???
            break;
        case 18:
            // ?????????????????????(??????????????????)?????????
            sql.append(" AND T3.CORRESPOND_TYPE_CODE = '15' "); // AND ????????????(TBL300).????????????????????? = ?????????????????????(??????????????????)????????????CD019???
            break;
        case 19:
            // ???????????????
            sql.append(" AND T3.CORRESPOND_TYPE_CODE = '16' "); // AND ????????????(TBL300).????????????????????? = ??????????????????CD019???
            break;
        case 20:
            // ??????????????????????????????15???????????????
            sql.append(" AND T3.CORRESPOND_TYPE_CODE = '17' "); // AND ????????????(TBL300).????????????????????? = ??????????????????????????????15??????????????????CD019???
            break;
        case 21:
            // ??????????????????????????????18???????????????
            sql.append(" AND T3.CORRESPOND_TYPE_CODE = '18' "); // AND ????????????(TBL300).????????????????????? = ??????????????????????????????18??????????????????CD019???
            break;
        default:
            break;
        }

        return sql;
    }

    /**
     * getMapAggregateRecord
     * @param vo DataAggregateVo
     * @return Map
     */
    public Map<String, List<DataAggregateResultTmpVo>> getMapAggregateRecord(DataAggregateVo vo) {
        Map<String, List<DataAggregateResultTmpVo>> map = new HashMap<String, List<DataAggregateResultTmpVo>>();
        List<DataAggregateResultTmpVo> lstAggregateRecord;

        Map<String, String> mapSql = getMapSql(vo);
        for (String summaryItem : vo.getChkSummaryItem()) {
            if (mapSql.containsKey(summaryItem)) {

                lstAggregateRecord = namedParameterJdbcTemplate.query(mapSql.get(summaryItem), mapSqlParameterSource,
                        new RowMapper<DataAggregateResultTmpVo>() {

                            @Override
                            public DataAggregateResultTmpVo mapRow(ResultSet rs, int rowNum) throws SQLException {
                                DataAggregateResultTmpVo vo = new DataAggregateResultTmpVo();

                                vo.setApartmentId(rs.getString("apartmentId"));
                                vo.setAggregateCredit(rs.getString("aggregateCredit"));
                                switch (Integer.parseInt(summaryItem)) {
                                case 1:
                                case 2:
                                case 3:
                                    vo.setNotificationKbn(rs.getString("notificationKbn"));
                                    break;
                                default:
                                    vo.setTypeCode(rs.getString("typeCode"));
                                    break;
                                }

                                return vo;
                            }
                        });

                map.put(summaryItem, lstAggregateRecord);
            }
        }

        return map;
    }

    /**
     * getMapCntAggregateRecord
     * @param vo DataAggregateVo
     * @return Map
     */
    public Map<String, List<DataAggregateResultTmpCntVo>> getMapCntAggregateRecord(DataAggregateVo vo) {
        Map<String, List<DataAggregateResultTmpCntVo>> map = new HashMap<String, List<DataAggregateResultTmpCntVo>>();
        List<DataAggregateResultTmpCntVo> lstAggregateRecord;

        Map<String, String> mapSql = getMapCountSql(vo);
        for (String summaryItem : vo.getChkSummaryItem()) {
            if (mapSql.containsKey(summaryItem)) {

                lstAggregateRecord = namedParameterJdbcTemplate.query(mapSql.get(summaryItem), mapSqlParameterSource,
                        new RowMapper<DataAggregateResultTmpCntVo>() {

                            @Override
                            public DataAggregateResultTmpCntVo mapRow(ResultSet rs, int rowNum) throws SQLException {
                                DataAggregateResultTmpCntVo vo = new DataAggregateResultTmpCntVo();

                                vo.setCnt(rs.getString("cnt"));
                                vo.setAggregateCredit(rs.getString("aggregateCredit"));

                                return vo;
                            }
                        });

                map.put(summaryItem, lstAggregateRecord);
            }
        }

        return map;
    }

    public boolean isExistTbl200() {
        return isExistTbl200;
    }

    public void setExistTbl200(boolean isExistTbl200) {
        this.isExistTbl200 = isExistTbl200;
    }

    public boolean isExistTbl200Summary() {
        return isExistTbl200Summary;
    }

    public void setExistTbl200Summary(boolean isExistTbl200Summary) {
        this.isExistTbl200Summary = isExistTbl200Summary;
    }

    public MapSqlParameterSource getMapSqlParameterSource() {
        return mapSqlParameterSource;
    }

    public void setMapSqlParameterSource(MapSqlParameterSource mapSqlParameterSource) {
        this.mapSqlParameterSource = mapSqlParameterSource;
    }
}
