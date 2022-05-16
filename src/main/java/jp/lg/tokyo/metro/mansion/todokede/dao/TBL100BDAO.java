/*
 * @(#)TBL100BDAO.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 *
 * Create Date: 2019/10/12
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.dao;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.PageUtil;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.form.SearchInformationMansionForm;
import jp.lg.tokyo.metro.mansion.todokede.form.SummaryDataPagingForm;
import jp.lg.tokyo.metro.mansion.todokede.vo.OutputCsvInformationSearchVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.OutputCsvSummaryDataDetailsVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.OutputCsvSuperivseVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ResultSearchVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.SummaryDataDetailsVo;


/**
 * The Class TBL100BDAO.
 *
 * @author nmtan
 */
@Repository
public class TBL100BDAO extends JdbcDaoSupport implements RowMapper<List<ResultSearchVo>> {

    private static final Logger logger = LogManager.getLogger(TBL100BDAO.class);
    /**
     * The Constant SPACE.
     */
    private static final String SPACE = " ";

    private static final String CD059 = "CD059";

    private static final String CD059_NOT_SPECIFIED = "指定しない";
    private static final String CD059_NOT_YET_NOTIFICATION = "未通知";
    private static final String CD059_NOTIFICATION = "通知済";

    /**
     * The jdbc template.
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    /**
     * Instantiates a new tbl100bdao.
     *
     * @throws InvocationTargetException the invocation target exception
     * @throws IllegalAccessException    the illegal access exception
     */
    TBL100BDAO() throws InvocationTargetException, IllegalAccessException {

    }

    /**
     * Instantiates a new tbl100bdao.
     *
     * @param dataSource the data source
     * @throws InvocationTargetException the invocation target exception
     * @throws IllegalAccessException    the illegal access exception
     */
    @Autowired
    public TBL100BDAO(DataSource dataSource) throws InvocationTargetException, IllegalAccessException {
        this.setDataSource(dataSource);
    }

    /**
     * Gets the mansion information by id.
     *
     * @param apartmentId the apartment id
     * @return the mansion information by id
     */
    public TBL100Entity getMansionInformationById(String apartmentId) {

        String sql = "SELECT tb FROM TBL100Entity tb WHERE tb.apartmentId = ? and tb.deleteFlag='0'";
        return jdbcTemplate.queryForObject(sql, new Object[]{apartmentId}, BeanPropertyRowMapper.newInstance(TBL100Entity.class));
    }

    /**
     * Gets the mansion and write csv.
     *
     * @param apartmentId the apartment id
     * @return the mansion and write csv
     * @author nmtan
     * @Screen GJA0110
     */
    @SuppressWarnings("unchecked")
    public List<OutputCsvSuperivseVo> getMansionAndWriteCsv(String apartmentId) {
        StringBuilder queryString = new StringBuilder(CommonConstants.BLANK);
        queryString.append(" SELECT DISTINCT TBL100.APARTMENT_ID, "
                + "TBL100.APARTMENT_NAME, "
                + "TBL240.SUPERVISED_NOTICE_TYPE_CODE, "
                + "TBL240.APPENDIX_NO, TBL240.DOCUMENT_NO, "
                + "TBL240.NOTICE_DATE, TBL240.RECIPIENT_NAME_APARTMENT, TBL240.RECIPIENT_NAME_USER, "
                + "TBL240.SENDER, TBL240.TEXTADDRESS1,TBL240.EVIDENCE_BAR, TBL240.EVIDENCE_NO, "
                + "TBL240.PERIOD_EVIDENCE, TBL240.LAST_NOTICE_DATE,  TBL240.LAST_DOCUMENT_NO, TBL240.TEXTADDRESS2, TBL240.ADDRESS, "
                + "TBL240.APARTMENT_NAME, TBL240.NOTIFICATION_FORMAT_NAME,  TBL240.NOTIFICATION_TIMELIMIT, TBL240.CONTACT, TBL240.SUPERVISED_NOTICE_COUNT "
                + "FROM tbl100 TBL100 INNER JOIN tbl240 TBL240 ON (TBL100.APARTMENT_ID = TBL240.APARTMENT_ID) "
                + " JOIN tbl300 TBL300 ON TBL300.RELATED_NUMBER = TBL240.SUPERVISED_NOTICE_NO "
                + "WHERE TBL240.DELETE_FLAG = '0'");

        if (!isBlank(apartmentId)) {
            queryString.append(SPACE);
            queryString.append("  AND TBL240.APARTMENT_ID = :apartmentId");
            queryString.append("   ORDER BY TBL240.SUPERVISED_NOTICE_COUNT DESC LIMIT 1");
        } else {
            queryString.append(" AND 2 = 1");
        }
        Map<String, Object> mapParam = new HashedMap();
        mapParam.put("apartmentId", apartmentId);
        List<OutputCsvSuperivseVo> outputCsvSuperivseVos =
                namedParameterJdbcTemplate.query(queryString.toString(), new MapSqlParameterSource(mapParam), new RowMapper<OutputCsvSuperivseVo>() {
                    @Override
                    public OutputCsvSuperivseVo mapRow(ResultSet rs, int rowNum) throws SQLException {
                        OutputCsvSuperivseVo vo = new OutputCsvSuperivseVo();
                        vo.setOutputApartmentID(rs.getString("APARTMENT_ID"));
                        vo.setOutputApartmentName(rs.getString("APARTMENT_NAME"));
                        vo.setSuperviseNoticeType(rs.getString("SUPERVISED_NOTICE_TYPE_CODE"));
                        vo.setAppendixNo(rs.getString("APPENDIX_NO"));
                        vo.setDocumentNo(rs.getString("DOCUMENT_NO"));
                        vo.setNoticeDate(rs.getString("NOTICE_DATE"));
                        vo.setRecipientNameApartment(rs.getString("RECIPIENT_NAME_APARTMENT"));
                        vo.setRecipientNameUser(rs.getString("RECIPIENT_NAME_USER"));
                        vo.setSender(rs.getString("SENDER"));
                        vo.setTextMailing1(rs.getString("TEXTADDRESS1"));
                        vo.setEvidenceBar(rs.getString("EVIDENCE_BAR"));
                        vo.setEvidenceNo(rs.getString("EVIDENCE_NO"));
                        vo.setPeriodEvidence(rs.getString("PERIOD_EVIDENCE"));
                        vo.setLastNoticeDate(rs.getString("LAST_NOTICE_DATE"));
                        vo.setLastDocumentNo(rs.getString("LAST_DOCUMENT_NO"));
                        vo.setTextMailing2(rs.getString("TEXTADDRESS2"));
                        vo.setApartmentName(rs.getString("APARTMENT_NAME"));
                        vo.setNotificationFormatName(rs.getString("NOTIFICATION_FORMAT_NAME"));
                        vo.setNotificationTimeLimit(rs.getString("NOTIFICATION_TIMELIMIT"));
                        vo.setContact(rs.getString("CONTACT"));
                        return vo;
                    }
                });
        return outputCsvSuperivseVos;
    }

    /**
     * Gets the information search write CSV.
     *
     * @param apartmentId the apartment id
     * @return the information search write CSV
     * @author nmtan
     * @screen GJA0110
     */
    public List<OutputCsvInformationSearchVo> getInformationSearchWriteCSV(String apartmentId) {
        StringBuilder queryString = new StringBuilder(CommonConstants.BLANK);
        queryString.append("SELECT DISTINCT TBL100.APARTMENT_ID , TBL100.APARTMENT_NAME, TBL100.APARTMENT_NAME_PHONETIC, TBL100.ZIP_CODE, "
                + "TBL100.CITY_NAME,TBL100.ADDRESS, TBL200.NOTIFICATION_DATE, TBL100.NEXT_NOTIFICATION_DATE, TBL200.ACCEPT_STATUS, "
                + "TBL100.BUILD_YEAR, TBL100.BUILD_MONTH, TBL100.BUILD_DAY,TBL100.HOUSE_NUMBER, TBL100.FLOOR_NUMBER,"
                + "TBL100.NEXT_NOTIFICATION_DATE, TBL200.GROUP_FORM, TBL200.GROUP_FORM_ELSE, "
                + "TBL200.LAND_RIGHTS_CODE, TBL200.LAND_RIGHTS_ELSE, TBL200.USEFOR_CODE, TBL200.USEFOR_ELSE "
                + "FROM tbl100 TBL100 JOIN tbl200 TBL200 ON TBL100.APARTMENT_ID = TBL200.APARTMENT_ID "
                + "WHERE TBL100.DELETE_FLAG = '0'");


        if (!isBlank(apartmentId)) {
            queryString.append(SPACE);
            queryString.append(" AND TBL200.APARTMENT_ID = :apartmentId");
        } else {
            queryString.append(SPACE);
            queryString.append(" AND 2 = 1");
        }
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("apartmentId", apartmentId);

        List<OutputCsvInformationSearchVo> outputCsvInformationSearchVos =
                namedParameterJdbcTemplate.query(queryString.toString(), params, new RowMapper<OutputCsvInformationSearchVo>() {
                    @Override
                    public OutputCsvInformationSearchVo mapRow(ResultSet rs, int rowNum) throws SQLException {
                        OutputCsvInformationSearchVo vo = new OutputCsvInformationSearchVo();
                        vo.setApartmentId(rs.getString("APARTMENT_ID"));
                        vo.setApartmentName(rs.getString("APARTMENT_NAME"));
                        vo.setApartmentNamePhonetic(rs.getString("APARTMENT_NAME_PHONETIC"));
                        vo.setZipCode(rs.getString("ZIP_CODE"));
                        vo.setCity(rs.getString("CITY_NAME"));
                        vo.setAddress(rs.getString("ADDRESS"));
                        vo.setNotificationDate(rs.getString("NOTIFICATION_DATE"));
                        vo.setNotificationStatus(rs.getString("NEXT_NOTIFICATION_DATE"));
                        vo.setAcceptStatus(rs.getString("ACCEPT_STATUS"));
                        vo.setBuildDay(rs.getString("BUILD_DAY"));
                        vo.setBuildMonth(rs.getString("BUILD_MONTH"));
                        vo.setBuildYear(rs.getString("BUILD_YEAR"));
                        vo.setGroupForm(rs.getString("GROUP_FORM"));
                        vo.setGroupFormElse(rs.getString("GROUP_FORM_ELSE"));
                        vo.setLandRights(rs.getString("LAND_RIGHTS_CODE"));
                        vo.setLandRightElse(rs.getString("LAND_RIGHTS_ELSE"));
                        vo.setUsefor(rs.getString("USEFOR_CODE"));
                        vo.setUseForElse(rs.getString("USEFOR_ELSE"));

                        return vo;
                    }
                });
        return outputCsvInformationSearchVos;

    }

    /**
     * Map row.
     *
     * @param rs     the rs
     * @param rowNum the row num
     * @return the list
     * @throws SQLException the SQL exception
     */
    @Override
    public List<ResultSearchVo> mapRow(ResultSet rs, int rowNum) throws SQLException {
        List<ResultSearchVo> list = new ArrayList<>();
        while (rs.next()) {
            ResultSearchVo vo = new ResultSearchVo();
            vo.setLblApartmentId(rs.getString("APARTMENT_ID"));
            vo.setLnkApartmentName(rs.getString("APARTMENT_NAME"));
            vo.setLblAddress(rs.getString("ADDRESS"));
            vo.setLblNotificationDate(rs.getString("NOTIFICATION_DATE"));
            vo.setLblNotificationStatus(rs.getString("NEXT_NOTIFICATION_DATE"));
            vo.setLblAcceptStatus(rs.getString("ACCEPT_STATUS"));
            list.add(vo);
        }

        return list;
    }


    /**
     * Search mansion information with paging
     *
     * @param pageable Pageable
     * @param form     SearchInformationMansionForm
     * @return {@link Page} of {@link ResultSearchVo}
     * @throws SQLException when has error
     */
    public Page<ResultSearchVo> searchInformationMansionPaging(Pageable pageable, SearchInformationMansionForm form) {

        StringBuilder queryString = new StringBuilder(CommonConstants.BLANK);
        StringBuilder countQuery = new StringBuilder(CommonConstants.BLANK);
        StringBuilder sqlQueryString = new StringBuilder(CommonConstants.BLANK);

        String sortParameter = form.getSortParameter();
        String txtApartmentId = form.getTxtApartmentId();
        String txtApartmentName = form.getTxtApartmentName();
        String txtApartmentNamePhonetic = form.getTxtApartmentNamePhonetic();
        String address2 = form.getTxtApartmentAddress2();
        String calNotificationDateFrom = form.getCalNotificationDateFrom();
        String calNotificationDateTo = form.getCalNotificationDateTo();
        String calBuiltDateFrom = form.getCalBuiltDateFrom();
        String calBuiltDateTo = form.getCalBuiltDateTo();
        String txtHouseNumberFrom = form.getTxtHouseNumberFrom();
        String txtHouseNumberTo = form.getTxtHouseNumberTo();
        String txtFloorNumberFrom = form.getTxtFloorNumberFrom();
        String txtFloorNumberTo = form.getTxtFloorNumberTo();
        String cityCode = form.getTxtCityCode();
        String rdoNotificationStatus = form.getRdoNotificationStatus();
        String rdoAcceptStatus = form.getRdoAcceptStatus();
        String rdoSuperviseStatus = form.getRdoSuperviseStatus();
        String rdoAdviceStatus = form.getRdoAdviceStatus();
        String receptNum = form.getTxtReceptNum();

        sqlQueryString.append(""
                + "SELECT DISTINCT "
                + "    TBL100.APARTMENT_ID,"
                + "    TBL100.APARTMENT_NAME, "
                + "    TBL100.CITY_CODE,TBL100.ADDRESS,"
                + "    TBL100.CITY_NAME,"
                + "    TBL100.NEXT_NOTIFICATION_DATE,"
                + "    TBL100.NEWEST_NOTIFICATION_NO, ");
        if (!isBlank(receptNum)
                || !isBlank(calNotificationDateFrom)
                || !isBlank(calNotificationDateTo)
                || (!isBlank(rdoAcceptStatus) && !CodeUtil.getValue(CommonConstants.CD030, CommonConstants.CD030_NOT_SPECIFIED).equals(rdoAcceptStatus))
                || (!isBlank(rdoAdviceStatus) && !CodeUtil.getValue(CD059, CD059_NOT_SPECIFIED).equals(rdoAdviceStatus))) {
            sqlQueryString.append("    TBL200.NOTIFICATION_DATE,");
            sqlQueryString.append("    TBL200.ACCEPT_STATUS ");
            sqlQueryString.append(" FROM tbl100 TBL100 ");
            sqlQueryString.append("     INNER JOIN tbl200 TBL200 ON (TBL100.APARTMENT_ID = TBL200.APARTMENT_ID AND TBL100.NEWEST_NOTIFICATION_NO = TBL200.NOTIFICATION_NO AND TBL200.DELETE_FLAG = '0')");
        } else {
            sqlQueryString.append("    TBL200.NOTIFICATION_DATE,");
            sqlQueryString.append("    TBL200.ACCEPT_STATUS ");
            sqlQueryString.append(" FROM tbl100 TBL100 ");
            sqlQueryString.append("     LEFT JOIN tbl200 TBL200 ON (TBL100.APARTMENT_ID = TBL200.APARTMENT_ID AND TBL100.NEWEST_NOTIFICATION_NO = TBL200.NOTIFICATION_NO AND TBL200.DELETE_FLAG = '0')");
        }
        if ((!isBlank(rdoSuperviseStatus) && !CodeUtil.getValue(CommonConstants.CD047, "指定しない").equals(rdoSuperviseStatus))) {
            sqlQueryString.append("     INNER JOIN tbl240 TBL240 ON (TBL100.APARTMENT_ID = TBL240.APARTMENT_ID AND TBL240.DELETE_FLAG = '0')");
        }
        if (CommonConstants.SCREEN_ID_GCA0120.equals(form.getPreviousScreen())) {
            sqlQueryString.append("     INNER JOIN tbl110 TBL110 ON (TBL100.APARTMENT_ID = TBL110.APARTMENT_ID AND TBL110.DELETE_FLAG = '0')");

        }
        sqlQueryString.append("WHERE TBL100.DELETE_FLAG = '0' ");

        countQuery.append(" SELECT COUNT(1) FROM(");
        // マンションID
        if (!isBlank(txtApartmentId)) {
            queryString.append(" AND TBL100.APARTMENT_ID =:txtApartmentId ");
        }
        // マンション名（部分一致）
        if (!isBlank(txtApartmentName)) {
            queryString.append(" AND TBL100.APARTMENT_NAME COLLATE utf8_unicode_ci like :txtApartmentName");
        }
        // 所在地
        // 住所
        if (!isBlank(address2)) {
            queryString.append(" AND TBL100.ADDRESS like :address2");
        }
        // 区市町村
        if (!isBlank(cityCode)) {
            queryString.append(" AND TBL100.CITY_CODE = :cityCode");
        }
        // マンション名フリガナ(部分一致)
        if (!isBlank(txtApartmentNamePhonetic)) {
            queryString.append(" AND TBL100.APARTMENT_NAME_PHONETIC COLLATE utf8_unicode_ci like :txtApartmentNamePhonetic");
        }
        // 受付番号(完全一致)
        if (!isBlank(receptNum)) {
            queryString.append(" AND TBL100.APARTMENT_ID = TBL200.APARTMENT_ID");
            queryString.append(" AND TBL200.RECEPTION_NO = :txtReceptNum ");
            queryString.append(" AND TBL200.ACCEPT_STATUS = 1 ");

        }
        // 届出日 FROM
        if (!isBlank(calNotificationDateFrom)) {
            queryString.append(" AND date_format((TBL200.NOTIFICATION_DATE) ,'%Y/%m/%d') >= DATE_FORMAT((  ");
            queryString.append(":calNotificationDateFrom");
            queryString.append("),'%Y/%m/%d')");
        }
        // 届出日 TO
        if (!isBlank(calNotificationDateTo)) {
            queryString.append(" AND date_format((TBL200.NOTIFICATION_DATE) ,'%Y/%m/%d') <=  DATE_FORMAT((");
            queryString.append(":calNotificationDateTo");
            queryString.append("),'%Y/%m/%d')");
        }
        // 届出状況
        if (!isBlank(rdoNotificationStatus)) {
            if (rdoNotificationStatus.equals(CodeUtil.getValue(CommonConstants.CD036, "指定しない"))) {
                // 指定しない
                queryString.append(SPACE);
            } else if (rdoNotificationStatus.equals(CodeUtil.getValue(CommonConstants.CD036, "未届"))) {
                // 未届
                queryString.append(" AND TBL100.NEXT_NOTIFICATION_DATE <= date_format(now(),'%Y%m%d')");
            } else if (rdoNotificationStatus.equals(CodeUtil.getValue(CommonConstants.CD036, "届出済"))) {
                // 届出済
                queryString.append(" AND TBL100.NEXT_NOTIFICATION_DATE > date_format(now(),'%Y%m%d')");
            }
        }
        // 受理状況
        if (!isBlank(rdoAcceptStatus)) {
            if ((CodeUtil.getValue(CommonConstants.CD030, CommonConstants.CD030_NOT_SPECIFIED).equals(rdoAcceptStatus))) {
                // 指定しない
                queryString.append(SPACE);
            } else if ((CodeUtil.getValue(CommonConstants.CD030, CommonConstants.CD030_UNACCEPTED).equals(rdoAcceptStatus))) {
                // 未受理
                queryString.append(" AND (TBL100.NEXT_NOTIFICATION_DATE <= date_format(now(),'%Y%m%d') OR (TBL100.NEWEST_NOTIFICATION_NO = TBL200.NOTIFICATION_NO AND TBL200.ACCEPT_STATUS=1))");
            } else if ((CodeUtil.getValue(CommonConstants.CD030, CommonConstants.CD030_ACCEPTED).equals(rdoAcceptStatus))) {
                // 受理済
                queryString.append(" AND (TBL100.NEXT_NOTIFICATION_DATE > date_format(now(),'%Y%m%d') AND  TBL100.NEWEST_NOTIFICATION_NO = TBL200.NOTIFICATION_NO AND TBL200.ACCEPT_STATUS=2)");
            }
        }
        // 助言状況
        if (!isBlank(rdoAdviceStatus)) {
            if ((CodeUtil.getValue(CD059, CD059_NOT_SPECIFIED).equals(rdoAdviceStatus))) {
                // 指定しない
                queryString.append(SPACE);
            }
            else if ((CodeUtil.getValue(CD059, CD059_NOT_YET_NOTIFICATION).equals(rdoAdviceStatus))) {
                // 通知済
                // Note: 届出情報(TBL200).助言実施フラグ=未実施 (TBL200.ADVICE_DONE_FLAG get from CD011)
                // CD011:
                // <property value="2" label="未実施" />
                queryString.append(" AND (TBL100.NEXT_NOTIFICATION_DATE <= date_format(now(),'%Y%m%d') OR (TBL100.NEWEST_NOTIFICATION_NO = TBL200.NOTIFICATION_NO AND TBL200.ADVICE_DONE_FLAG=2)) ");
            } else if ((CodeUtil.getValue(CD059, CD059_NOTIFICATION).equals(rdoAdviceStatus))) {
                // 通知済
                // Note: 届出情報(TBL200).助言実施フラグ=実施済 (TBL200.ADVICE_DONE_FLAG get from CD011)
                // CD011:
                // <property value="1" label="実施済" />
                queryString.append(" AND (TBL100.NEXT_NOTIFICATION_DATE > date_format(now(),'%Y%m%d') " +
                        " AND TBL100.NEWEST_NOTIFICATION_NO = TBL200.NOTIFICATION_NO AND TBL200.ADVICE_DONE_FLAG=1)");
            }
        }
        // 督促通知状況
        if (!isBlank(rdoSuperviseStatus)) {
            if ((CodeUtil.getValue(CommonConstants.CD047, "1回目用出力済").equals(rdoSuperviseStatus))) {
                // 1回目用出力のみ
                queryString.append(" AND EXISTS (SELECT(1) WHERE TBL100.APARTMENT_ID=TBL240.APARTMENT_ID "
                        + "AND "
                        + "TBL240.NOTIFICATION_NO IS NULL AND TBL240.SUPERVISED_NOTICE_COUNT = 1)"
                        + "AND NOT EXISTS (SELECT(1) WHERE TBL100.APARTMENT_ID=TBL240.APARTMENT_ID "
                        + "AND TBL240.NOTIFICATION_NO IS NULL AND TBL240.SUPERVISED_NOTICE_COUNT = 2)");
            } else if ((CodeUtil.getValue(CommonConstants.CD047, "2回目以降用出力済").equals(rdoSuperviseStatus))) {
                // 2回目以降用出力済
                queryString.append(" AND  EXISTS (SELECT(1) WHERE TBL100.APARTMENT_ID=TBL240.APARTMENT_ID "
                        + "AND TBL240.NOTIFICATION_NO IS NULL AND TBL240.SUPERVISED_NOTICE_COUNT = 2)");
            } else if ((CodeUtil.getValue(CommonConstants.CD047, "指定しない").equals(rdoSuperviseStatus))) {
                // 指定しない
                queryString.append(SPACE);
            }
        }

        // 新築年月日 FROM
        if (!isBlank(calBuiltDateFrom)) {
            queryString.append(" AND CONCAT(LPAD(TBL100.BUILD_YEAR, 4, 0), '/', LPAD(TBL100.BUILD_MONTH, 2, 0), '/', LPAD(TBL100.BUILD_DAY, 2, 0)) >= (");
            queryString.append(":calBuiltDateFrom");
            queryString.append(")");
        }
        // 新築年月日 TO
        if (!isBlank(calBuiltDateTo)) {
            queryString.append(" AND CONCAT(LPAD(TBL100.BUILD_YEAR, 4, 0), '/', LPAD(TBL100.BUILD_MONTH, 2, 0), '/', LPAD(TBL100.BUILD_DAY, 2, 0)) <= (");
            queryString.append(":calBuiltDateTo");
            queryString.append(")");
        }
        // 戸数 FROM
        if (!isBlank(txtHouseNumberFrom)) {
            queryString.append(" AND TBL100.HOUSE_NUMBER >=  :txtHouseNumberFrom");
            txtHouseNumberFrom = txtHouseNumberFrom.replaceAll(",$", "");
        }
        // 戸数 TO
        if (!isBlank(txtHouseNumberTo)) {
            queryString.append(" AND TBL100.HOUSE_NUMBER <= :txtHouseNumberTo");
            txtHouseNumberTo = txtHouseNumberTo.replaceAll(",$", "");
        }
        // 階数 FROM
        if (!isBlank(txtFloorNumberFrom)) {
            txtFloorNumberFrom = txtFloorNumberFrom.replaceAll(",$", "");
            queryString.append(" AND TBL100.FLOOR_NUMBER >= :txtFloorNumberFrom");
        }
        // 階数 TO
        if (!isBlank(txtFloorNumberTo)) {
            txtFloorNumberFrom = txtFloorNumberFrom.replaceAll(",$", "");
            queryString.append(" AND TBL100.FLOOR_NUMBER <= :txtFloorNumberTo");
        }
        // 建物の滅失
        if (form.isChkApartmentLost()) {
            queryString.append(" AND TBL100.APARTMENT_LOST_FLAG <> '1' ");
        }
        // 新規ユーザ登録審査（GCA0120）からマンション情報検索画面に遷移された場合、下記固定検索条件追加：
        if (CommonConstants.SCREEN_ID_GCA0120.equals(form.getPreviousScreen())) {
            queryString.append(" AND TBL100.APARTMENT_ID = TBL110.APARTMENT_ID");
            queryString.append(" AND TBL110.VALIDITY_FLAG = 2");
            queryString.append(" AND TBL110.DELETE_FLAG = 0");
        }

        if (!isBlank(sortParameter)) {
            if (CommonConstants.SORT_NOTIFICATION_DATE_ASC.equalsIgnoreCase(sortParameter) && StringUtils.contains(sqlQueryString.toString(), "TBL200")) {
                // ①届出日（昇順）を選択した場合
                // 届出情報（TBL200）.届出年月日（昇順）
                queryString.append(" ORDER BY TBL200.NOTIFICATION_DATE ASC");
            } else if (CommonConstants.SORT_NOTIFICATION_DATE_DESC.equalsIgnoreCase(sortParameter) && StringUtils.contains(sqlQueryString.toString(), "TBL200")) {
                // ②届出日（降順）を選択した場合
                // 届出情報（TBL200）.届出年月日（降順）
                queryString.append(" ORDER BY TBL200.NOTIFICATION_DATE DESC");
            } else if (CommonConstants.SORT_APARTMENT_NAME_ASC.equalsIgnoreCase(sortParameter)) {
                // ③マンション名（昇順）を選択した場合
                // マンション情報（TBL100）.マンション名（昇順）
                queryString.append(" ORDER BY TBL100.APARTMENT_NAME ASC");
            } else if (CommonConstants.SORT_APARTMENT_NAME_DESC.equalsIgnoreCase(sortParameter)) {
                // ④マンション名（降順）を選択した場合
                // マンション情報（TBL100）.マンション名（降順）
                queryString.append(" ORDER BY TBL100.APARTMENT_NAME DESC");
            } else if (CommonConstants.SORT_ADDRESS_ASC.equals(sortParameter)) {
                // ⑤住所（昇順）を選択した場合
                // 第1ソートキー：区市町村コード（昇順）
                // 第2ソートキー：住所（昇順）
                queryString.append(" ORDER BY TBL100.CITY_CODE ASC , TBL100.ADDRESS ASC");
            } else if (CommonConstants.SORT_ADDRESS_DESC.equals(sortParameter)) {
                // ⑥住所（降順）
                // 第1ソートキー：区市町村コード（降順）
                // 第2ソートキー：住所（降順）
                queryString.append(" ORDER BY TBL100.CITY_CODE DESC,TBL100.ADDRESS DESC ");
            }
        }


        queryString.insert(0, sqlQueryString);
        countQuery.append(queryString + ") table2");
        queryString.append(" LIMIT ");
        queryString.append(" :pageSize");
        queryString.append(" OFFSET ");
        queryString.append(" :offset");
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("txtApartmentId", txtApartmentId);
        mapSqlParameterSource.addValue("txtApartmentName", "%" + txtApartmentName + "%");
        mapSqlParameterSource.addValue("address2", address2 + "%");
        mapSqlParameterSource.addValue("cityCode", cityCode);
        mapSqlParameterSource.addValue("txtReceptNum", receptNum);
        mapSqlParameterSource.addValue("txtApartmentNamePhonetic", "%" + txtApartmentNamePhonetic + "%");
        mapSqlParameterSource.addValue("calNotificationDateFrom", calNotificationDateFrom);
        mapSqlParameterSource.addValue("calNotificationDateTo", calNotificationDateTo);
        mapSqlParameterSource.addValue("calBuiltDateFrom", calBuiltDateFrom);
        mapSqlParameterSource.addValue("calBuiltDateTo", calBuiltDateTo);
        mapSqlParameterSource.addValue("txtHouseNumberFrom", txtHouseNumberFrom);
        mapSqlParameterSource.addValue("txtHouseNumberTo", txtHouseNumberTo);
        mapSqlParameterSource.addValue("txtFloorNumberFrom", txtFloorNumberFrom);
        mapSqlParameterSource.addValue("txtFloorNumberTo", txtFloorNumberTo);
        mapSqlParameterSource.addValue("pageSize", PageUtil.getLimit(pageable));
        mapSqlParameterSource.addValue("offset", PageUtil.getOffset(pageable));

        try {
            Long totalRow = namedParameterJdbcTemplate.queryForObject(countQuery.toString(), mapSqlParameterSource, Long.class);
            List<ResultSearchVo> resultSearchVoList = namedParameterJdbcTemplate.query(queryString.toString(),
                    mapSqlParameterSource, new RowMapper<ResultSearchVo>() {
                        @Override
                        public ResultSearchVo mapRow(ResultSet rs, int rowNum) throws SQLException {
                            ResultSearchVo vo = new ResultSearchVo();
                            vo.setLblApartmentId(rs.getString("APARTMENT_ID"));
                            vo.setLnkApartmentName(rs.getString("APARTMENT_NAME"));
                            vo.setLblAddress(rs.getString("ADDRESS"));
                            vo.setCityCode(rs.getString("CITY_CODE"));
                            vo.setCityName(rs.getString("CITY_NAME"));
                            vo.setNewestNotificationNo(rs.getString("NEWEST_NOTIFICATION_NO"));
                            vo.setLblNotificationDate(rs.getString("NOTIFICATION_DATE"));
                            vo.setLblNotificationStatus(rs.getString("NEXT_NOTIFICATION_DATE"));
                            vo.setLblAcceptStatus(rs.getString("ACCEPT_STATUS"));
                            return vo;
                        }
                    });
            return new PageImpl<>(resultSearchVoList, pageable, totalRow == null ? 0 : totalRow);
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            return null;
        }

    }

    /**
     * 
     * @param pageable
     * @param apartmentId
     * @return
     */
    public Page<SummaryDataDetailsVo> getSummaryDataDetails(Pageable pageable, SummaryDataPagingForm form) {
        StringBuilder queryString = new StringBuilder(CommonConstants.BLANK);
        StringBuilder countQuery = new StringBuilder(CommonConstants.BLANK);
        queryString.append(""
                + "SELECT "
                + "    TBL100.APARTMENT_NAME, "
                + "    TBL100.APARTMENT_ID, "
                + "    TBL100.ZIP_CODE,"
                + "    TBM001.CITY_NAME,"
                + "    TBL100.ADDRESS "
                + "FROM tbl100 TBL100 "
                + "    LEFT JOIN tbl200 TBL200 ON (TBL200.NOTIFICATION_NO = TBL100.NEWEST_NOTIFICATION_NO AND TBL200.DELETE_FLAG = '0')"
                + "    LEFT JOIN tbm001 TBM001 ON (TBL100.CITY_CODE = TBM001.CITY_CODE AND TBM001.DELETE_FLAG = '0') "
                + "WHERE TBL100.APARTMENT_ID in (:apartmentId) "
                + "AND TBL100.DELETE_FLAG = '0' "
                + "ORDER BY ADDRESS ");

        countQuery.append(" SELECT COUNT(1) FROM (");
        countQuery.append(queryString + ") table2");

        queryString.append(" LIMIT ");
        queryString.append(" :pageSize");
        queryString.append(" OFFSET ");
        queryString.append(" :offset");

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("apartmentId", Arrays.asList(form.getApartmentIds()));
        mapSqlParameterSource.addValue("pageSize", PageUtil.getLimit(pageable));
        mapSqlParameterSource.addValue("offset", PageUtil.getOffset(pageable));

        try {
            Long totalRow = namedParameterJdbcTemplate.queryForObject(countQuery.toString(), mapSqlParameterSource, Long.class);
            List<SummaryDataDetailsVo> resultSearchVoList = namedParameterJdbcTemplate.query(queryString.toString(),
                    mapSqlParameterSource, new RowMapper<SummaryDataDetailsVo>() {
                        @Override
                        public SummaryDataDetailsVo mapRow(ResultSet rs, int rowNum) throws SQLException {
                            SummaryDataDetailsVo vo = new SummaryDataDetailsVo();
                            vo.setLblApartmentName(rs.getString("APARTMENT_NAME"));
                            vo.setApartmentId(rs.getString("APARTMENT_ID"));
                            vo.setZipCode(rs.getString("ZIP_CODE"));
                            vo.setCityName(rs.getString("CITY_NAME"));
                            vo.setAddress(rs.getString("ADDRESS"));
                            return vo;
                        }
                    });
            return new PageImpl<>(resultSearchVoList, pageable, totalRow == null ? 0 : totalRow);
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            return null;
        }
    }

    /**
     * Gets the summary data details write CSV.
     *
     * @param apartmentId the apartment id
     * @return the summary data details write CSV
     * @author NXViet
     * @screen GKA0120
     */
    public List<OutputCsvSummaryDataDetailsVo> getSummaryDataDetailsWriteCSV(String[] apartmentId) {
        StringBuilder queryString = new StringBuilder(CommonConstants.BLANK);
        queryString.append(" SELECT "
                + "    TBL100.APARTMENT_ID, "
                + "    TBL100.NEWEST_NOTIFICATION_NO, "
                + "    CASE "
                + "        WHEN TBL100.NEWEST_NOTIFICATION_NO IS NULL THEN TBL100.APARTMENT_NAME "
                + "        ELSE TBL200.APARTMENT_NAME "
                + "    END AS APARTMENT_NAME, "
                + "    CASE "
                + "        WHEN TBL100.NEWEST_NOTIFICATION_NO IS NULL THEN TBL100.APARTMENT_NAME_PHONETIC "
                + "        ELSE TBL200.APARTMENT_NAME_PHONETIC "
                + "    END AS APARTMENT_NAME_PHONETIC, "
                + "    CASE "
                + "        WHEN TBL100.NEWEST_NOTIFICATION_NO IS NULL THEN TBL100.ZIP_CODE "
                + "        ELSE TBL200.ZIP_CODE "
                + "    END AS ZIP_CODE, "
                + "    TBM001.CITY_NAME, "
                + "    CASE "
                + "        WHEN TBL100.NEWEST_NOTIFICATION_NO IS NULL THEN TBL100.ADDRESS "
                + "        ELSE TBL200.ADDRESS "
                + "    END AS ADDRESS, "
                + "    TBL200.ACCEPT_STATUS, "
                + "    TBL200.NOTIFICATION_TYPE, "
                + "    TBL200.NOTIFICATION_DATE, "
                + "    TBL200.NOTIFICATION_GROUP_NAME, "
                + "    TBL200.NOTIFICATION_PERSON_NAME, "
                + "    TBL200.CHANGE_COUNT, "
                + "    TBL200.CHANGE_REASON_CODE, "
                + "    TBL200.LOST_ELSE_REASON_CODE, "
                + "    TBL200.LOST_ELSE_REASON_ELSE, "
                + "    TBL200.GROUP_YESNO_CODE, "
                + "    TBL200.APARTMENT_NUMBER, "
                + "    TBL200.GROUP_FORM, "
                + "    TBL200.GROUP_FORM_ELSE, "
                + "    TBL200.HOUSE_NUMBER, "
                + "    TBL200.FLOOR_NUMBER, "
                + "    CASE "
                + "        WHEN "
                + "            TBL100.NEWEST_NOTIFICATION_NO IS NULL "
                + "        THEN "
                + "            CONCAT(TBL100.BUILD_YEAR, "
                + "                    '年', "
                + "                    TBL100.BUILD_MONTH, "
                + "                    '月', "
                + "                    TBL100.BUILD_DAY, "
                + "                    '日') "
                + "        ELSE TBL200.BUILT_DATE "
                + "    END AS BUILT_DATE, "
                + "    TBL200.LAND_RIGHTS_CODE, "
                + "    TBL200.LAND_RIGHTS_ELSE, "
                + "    TBL200.USEFOR_CODE, "
                + "    TBL200.USEFOR_ELSE, "
                + "    TBL200.MANAGEMENT_FORM_CODE, "
                + "    TBL200.MANAGEMENT_FORM_ELSE, "
                + "    TBL200.MANAGER_NAME, "
                + "    TBL200.MANAGER_NAME_PHONETIC, "
                + "    TBL200.MANAGER_ZIP_CODE, "
                + "    TBL200.MANAGER_ADDRESS, "
                + "    TBL200.MANAGER_TEL_NO, "
                + "    TBL200.GROUP_CODE, "
                + "    TBL200.MANAGER_CODE, "
                + "    TBL200.RULE_CODE, "
                + "    TBL200.RULE_CHANGE_YEAR, "
                + "    TBL200.OPEN_ONEYEAR_OVER, "
                + "    TBL200.MINUTES, "
                + "    TBL200.MANAGEMENT_COST_CODE, "
                + "    TBL200.REPAIR_COST_CODE, "
                + "    TBL200.REPAIR_MONTHLY_COST, "
                + "    TBL200.REPAIR_PLAN_CODE, "
                + "    TBL200.REPAIR_NEAREST_YEAR, "
                + "    TBL200.LONG_REPAIR_PLAN_CODE, "
                + "    TBL200.LONG_REPAIR_PLAN_YEAR, "
                + "    TBL200.LONG_REPAIR_PLAN_PERIOD, "
                + "    TBL200.LONG_REPAIR_PLAN_YEAR_FROM, "
                + "    TBL200.LONG_REPAIR_PLAN_YEAR_TO, "
                + "    TBL200.ARREARAGE_RULE_CODE, "
                + "    TBL200.SEGMENT_CODE, "
                + "    TBL200.EMPTY_PERCENT_CODE, "
                + "    TBL200.EMPTY_NUMBER, "
                + "    TBL200.RENTAL_PERCENT_CODE, "
                + "    TBL200.RENTAL_NUMBER, "
                + "    TBL200.SEISMIC_DIAGNOSIS_CODE, "
                + "    TBL200.EARTHQUAKE_RESISTANCE_CODE, "
                + "    TBL200.SEISMIC_RETROFIT_CODE, "
                + "    TBL200.DESIGN_DOCUMENT_CODE, "
                + "    TBL200.REPAIR_HISTORY_CODE, "
                + "    TBL200.VOLUNTARY_ORGANIZATION_CODE, "
                + "    TBL200.DISASTER_PREVENTION_MANUAL_CODE, "
                + "    TBL200.DISASTER_PREVENTION_STOCKPILE_CODE, "
                + "    TBL200.NEED_SUPPORT_LIST_CODE, "
                + "    TBL200.DISASTER_PREVENTION_REGULAR_CODE, "
                + "    TBL200.SLOPE_CODE, "
                + "    TBL200.RAILING_CODE, "
                + "    TBL200.ELEVATOR_CODE, "
                + "    TBL200.LED_CODE, "
                + "    TBL200.HEAT_SHIELDING_CODE, "
                + "    TBL200.EQUIPMENT_CHARGE_CODE, "
                + "    TBL200.COMMUNITY_CODE, "
                + "    TBL200.CONTACT_PROPERTY_CODE, "
                + "    TBL200.CONTACT_PROPERTY_ELSE, "
                + "    TBL200.CONTACT_ZIP_CODE, "
                + "    TBL200.CONTACT_ADDRESS, "
                + "    TBL200.CONTACT_TEL_NO, "
                + "    TBL200.CONTACT_NAME, "
                + "    TBL200.CONTACT_NAME_PHONETIC, "
                + "    TBL200.CONTACT_MAIL_ADDRESS, "
                + "    TBL200.OPTIONAL "
                + " FROM "
                + "    tbl100 TBL100 "
                + "        LEFT JOIN "
                + "    tbl200 TBL200 ON (TBL100.NEWEST_NOTIFICATION_NO = TBL200.NOTIFICATION_NO "
                + "        AND TBL200.DELETE_FLAG = '0') "
                + "        LEFT JOIN "
                + "    tbm001 TBM001 ON (TBL100.CITY_CODE = TBM001.CITY_CODE "
                + "        AND TBM001.DELETE_FLAG = '0') "
                + " WHERE "
                + "    TBL100.APARTMENT_ID in (:apartmentId) "
                + "AND TBL100.DELETE_FLAG = '0' ");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("apartmentId", Arrays.asList(apartmentId));

        List<OutputCsvSummaryDataDetailsVo> outputCsvSummaryDataDetailsVos =
                namedParameterJdbcTemplate.query(queryString.toString(), params, new RowMapper<OutputCsvSummaryDataDetailsVo>() {
                    @Override
                    public OutputCsvSummaryDataDetailsVo mapRow(ResultSet rs, int rowNum) throws SQLException {
                        OutputCsvSummaryDataDetailsVo vo = new OutputCsvSummaryDataDetailsVo();
                        vo.setApartmentId(rs.getString("APARTMENT_ID"));
                        vo.setApartmentName(rs.getString("APARTMENT_NAME"));
                        vo.setNewestNotificationNo(rs.getString("NEWEST_NOTIFICATION_NO"));
                        vo.setApartmentNamePhonetic(rs.getString("APARTMENT_NAME_PHONETIC"));
                        vo.setZipCode(rs.getString("ZIP_CODE"));
                        vo.setCityCode(rs.getString("CITY_NAME"));
                        vo.setAddress(rs.getString("ADDRESS"));
                        vo.setAcceptＳtatus(CodeUtil.getLabel(CommonConstants.CD030, rs.getString("ACCEPT_STATUS")));
                        vo.setNotificationType(CodeUtil.getLabel(CommonConstants.CD003, rs.getString("NOTIFICATION_TYPE")));
                        String notificationDate = rs.getString("NOTIFICATION_DATE");
                        if (!StringUtil.isBlank(notificationDate)) {
                            vo.setNotificationDate(DateUtil.getFormatJapaneseDateYYYYMMDD(notificationDate));
                        }
                        vo.setNotificationGroupName(rs.getString("NOTIFICATION_GROUP_NAME"));
                        vo.setNotificationPersonName(rs.getString("NOTIFICATION_PERSON_NAME"));
                        vo.setChangeCount(rs.getString("CHANGE_COUNT"));
                        vo.setChangeReason(CodeUtil.getLabel(CommonConstants.CD004, rs.getString("CHANGE_REASON_CODE")));
                        vo.setLostElseReason(CodeUtil.getLabel(CommonConstants.CD040, rs.getString("LOST_ELSE_REASON_CODE")));
                        vo.setLostElseReasonElse(rs.getString("LOST_ELSE_REASON_ELSE"));
                        vo.setGroupYesno(CodeUtil.getLabel(CommonConstants.CD014, rs.getString("GROUP_YESNO_CODE")));
                        vo.setApartmentNumber(rs.getString("APARTMENT_NUMBER"));
                        vo.setGroupForm(CodeUtil.getLabel(CommonConstants.CD005, rs.getString("GROUP_FORM")));
                        vo.setGroupFormElse(rs.getString("GROUP_FORM_ELSE"));
                        vo.setHouseNumber(rs.getString("HOUSE_NUMBER"));
                        vo.setFloorNumber(rs.getString("FLOOR_NUMBER"));
                        if (!StringUtils.isBlank(rs.getString("NEWEST_NOTIFICATION_NO")) && !StringUtils.isBlank(rs.getString("BUILT_DATE"))) {
                            vo.setBuiltDate(DateUtil.getFormatJapaneseDateYYYYMMDD(rs.getString("BUILT_DATE")));
                        } else {
                            vo.setBuiltDate(rs.getString("BUILT_DATE"));
                        }
                        vo.setLandRights(CodeUtil.getLabel(CommonConstants.CD006, rs.getString("LAND_RIGHTS_CODE")));
                        vo.setLandRightsElse(rs.getString("LAND_RIGHTS_ELSE"));
                        vo.setUsefor(CodeUtil.getLabel(CommonConstants.CD007, rs.getString("USEFOR_CODE")));
                        vo.setUseforElse(rs.getString("USEFOR_ELSE"));
                        vo.setManagementForm(CodeUtil.getLabel(CommonConstants.CD008, rs.getString("MANAGEMENT_FORM_CODE")));
                        vo.setManagementFormElse(rs.getString("MANAGEMENT_FORM_ELSE"));
                        vo.setManagerName(rs.getString("MANAGER_NAME"));
                        vo.setManagerNamePhonetic(rs.getString("MANAGER_NAME_PHONETIC"));
                        vo.setManagerZipCode(rs.getString("MANAGER_ZIP_CODE"));
                        vo.setManagerAddress(rs.getString("MANAGER_ADDRESS"));
                        vo.setManagerTelNo(rs.getString("MANAGER_TEL_NO"));
                        vo.setGroup(CodeUtil.getLabel(CommonConstants.CD053, rs.getString("GROUP_CODE")));
                        vo.setManager(CodeUtil.getLabel(CommonConstants.CD009, rs.getString("MANAGER_CODE")));
                        vo.setRule(CodeUtil.getLabel(CommonConstants.CD053, rs.getString("RULE_CODE")));
                        vo.setRuleChangeYear(rs.getString("RULE_CHANGE_YEAR"));
                        vo.setOpenOneyearOver(CodeUtil.getLabel(CommonConstants.CD053, rs.getString("OPEN_ONEYEAR_OVER")));
                        vo.setMinutes(CodeUtil.getLabel(CommonConstants.CD053, rs.getString("MINUTES")));
                        vo.setManagementCost(CodeUtil.getLabel(CommonConstants.CD053, rs.getString("MANAGEMENT_COST_CODE")));
                        vo.setRepairCost(CodeUtil.getLabel(CommonConstants.CD053, rs.getString("REPAIR_COST_CODE")));
                        vo.setRepairMonthlyCost(rs.getString("REPAIR_MONTHLY_COST"));
                        vo.setRepairPlan(CodeUtil.getLabel(CommonConstants.CD053, rs.getString("REPAIR_PLAN_CODE")));
                        vo.setRepairNearestYear(rs.getString("REPAIR_NEAREST_YEAR"));
                        vo.setLongRepairPlan(CodeUtil.getLabel(CommonConstants.CD002, rs.getString("LONG_REPAIR_PLAN_CODE")));
                        vo.setLongRepairPlanYear(rs.getString("LONG_REPAIR_PLAN_YEAR"));
                        vo.setLongRepairPlanPeriod(rs.getString("LONG_REPAIR_PLAN_PERIOD"));
                        vo.setLongRepairPlanYearFrom(rs.getString("LONG_REPAIR_PLAN_YEAR_FROM"));
                        vo.setLongRepairPlanYearTo(rs.getString("LONG_REPAIR_PLAN_YEAR_TO"));
                        vo.setArrearageRule(CodeUtil.getLabel(CommonConstants.CD002, rs.getString("ARREARAGE_RULE_CODE")));
                        vo.setSegment(CodeUtil.getLabel(CommonConstants.CD002, rs.getString("SEGMENT_CODE")));
                        vo.setEmptyPercent(CodeUtil.getLabel(CommonConstants.CD010, rs.getString("EMPTY_PERCENT_CODE")));
                        vo.setEmptyNumber(rs.getString("EMPTY_NUMBER"));
                        vo.setRentalPercent(CodeUtil.getLabel(CommonConstants.CD052, rs.getString("RENTAL_PERCENT_CODE")));
                        vo.setRentalNumber(rs.getString("RENTAL_NUMBER"));
                        vo.setSeismicDiagnosis(CodeUtil.getLabel(CommonConstants.CD011, rs.getString("SEISMIC_DIAGNOSIS_CODE")));
                        vo.setEarthquakeResistance(CodeUtil.getLabel(CommonConstants.CD012, rs.getString("EARTHQUAKE_RESISTANCE_CODE")));
                        vo.setSeismicRetrofit(CodeUtil.getLabel(CommonConstants.CD011, rs.getString("SEISMIC_RETROFIT_CODE")));
                        vo.setDesignDocument(CodeUtil.getLabel(CommonConstants.CD002, rs.getString("DESIGN_DOCUMENT_CODE")));
                        vo.setRepairHistory(CodeUtil.getLabel(CommonConstants.CD002, rs.getString("REPAIR_HISTORY_CODE")));
                        vo.setVoluntaryOrganization(CodeUtil.getLabel(CommonConstants.CD002, rs.getString("VOLUNTARY_ORGANIZATION_CODE")));
                        vo.setDisasterPreventionManual(CodeUtil.getLabel(CommonConstants.CD002, rs.getString("DISASTER_PREVENTION_MANUAL_CODE")));
                        vo.setDisasterPreventionStockpile(CodeUtil.getLabel(CommonConstants.CD002, rs.getString("DISASTER_PREVENTION_STOCKPILE_CODE")));
                        vo.setNeedSupportList(CodeUtil.getLabel(CommonConstants.CD002, rs.getString("NEED_SUPPORT_LIST_CODE")));
                        vo.setDisasterPreventionRegular(CodeUtil.getLabel(CommonConstants.CD002, rs.getString("DISASTER_PREVENTION_REGULAR_CODE")));
                        vo.setSlope(CodeUtil.getLabel(CommonConstants.CD002, rs.getString("SLOPE_CODE")));
                        vo.setRailing(CodeUtil.getLabel(CommonConstants.CD002, rs.getString("RAILING_CODE")));
                        vo.setElevator(CodeUtil.getLabel(CommonConstants.CD002, rs.getString("ELEVATOR_CODE")));
                        vo.setLed(CodeUtil.getLabel(CommonConstants.CD002, rs.getString("LED_CODE")));
                        vo.setHeatShielding(CodeUtil.getLabel(CommonConstants.CD002, rs.getString("HEAT_SHIELDING_CODE")));
                        vo.setEquipmentCharge(CodeUtil.getLabel(CommonConstants.CD002, rs.getString("EQUIPMENT_CHARGE_CODE")));
                        vo.setCommunity(CodeUtil.getLabel(CommonConstants.CD002, rs.getString("COMMUNITY_CODE")));
                        vo.setContactProperty(CodeUtil.getLabel(CommonConstants.CD013, rs.getString("CONTACT_PROPERTY_CODE")));
                        vo.setContactPropertyElse(rs.getString("CONTACT_PROPERTY_ELSE"));
                        vo.setContactZip(rs.getString("CONTACT_ZIP_CODE"));
                        vo.setContactAddress(rs.getString("CONTACT_ADDRESS"));
                        vo.setContactTelNo(rs.getString("CONTACT_TEL_NO"));
                        vo.setContactName(rs.getString("CONTACT_NAME"));
                        vo.setContactNamePhonetic(rs.getString("CONTACT_NAME_PHONETIC"));
                        vo.setContactMailAddress(rs.getString("CONTACT_MAIL_ADDRESS"));
                        vo.setOptional(rs.getString("OPTIONAL"));
                        
                        return vo;
                    }
                });
        return outputCsvSummaryDataDetailsVos;

    }
}

