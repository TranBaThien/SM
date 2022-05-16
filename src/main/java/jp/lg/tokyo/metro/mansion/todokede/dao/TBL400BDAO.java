/*
 * @(#) TBL400BDAO.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tbthien
 * Create Date: 2019/11/26
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.form.SearchForm;
import jp.lg.tokyo.metro.mansion.todokede.vo.GCA0110Vo;



/**
 * @author tbthien
 *
 */
@Repository
public class TBL400BDAO extends JdbcDaoSupport implements RowMapper<GCA0110Vo> {

    public TBL400BDAO() {
    }
    
    @Autowired
    public TBL400BDAO(DataSource dataSource) {
        this.setDataSource(dataSource); 
    } 
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    /**
     * method searchManagementAssociation
     * 
     * @for: GCA0110
     * @author tbthien
     * @param
     * @param
     * @return List<GCA0110Vo>
     */
    public List<GCA0110Vo> searchManagementAssociation(SearchForm form, int page, int size) { 
        
        StringBuilder queryString = new StringBuilder(CommonConstants.BLANK);
        Map<String, Object> mapParam = new HashedMap();
        
        queryString.append("SELECT ");
        queryString.append("tb1.APPLICATION_NO, ");
        queryString.append("tb1.APPLICATION_TIME, ");
        queryString.append("tb1.APARTMENT_NAME, ");
        queryString.append("tb1.ADDRESS, ");
        queryString.append("tb1.CITY_CODE, ");
        queryString.append("tb2.CITY_NAME, ");
        queryString.append("tb1.JUDGE_RESULT ");
        queryString.append("FROM tbl400 tb1, tbm001 tb2 ");
        queryString.append("WHERE tb1.DELETE_FLAG = '0' AND tb1.CITY_CODE = tb2.CITY_CODE "); 
        
        if (!form.getCityCode().equals("")) {
            queryString.append("AND tb1.CITY_CODE = :cityCode ");
            mapParam.put("cityCode", form.getCityCode());
        }
        
        if (!form.getApartmentName().equals("")) {
            queryString.append("AND tb1.APARTMENT_NAME COLLATE utf8_unicode_ci LIKE :apartmentName ");
            mapParam.put("apartmentName", "%"+form.getApartmentName()+"%");
        }
        
        if (!form.getStartTimeApply().equals("") && !form.getStartTimeApply().equals("000000")) { 
            queryString.append("AND tb1.APPLICATION_TIME >= :startTimeApply ");
            mapParam.put("startTimeApply", form.getStartTimeApply()); 
        }
        
        if (!form.getEndTimeApply().equals("") && !form.getEndTimeApply().equals("240000")) { 
            queryString.append("AND tb1.APPLICATION_TIME <= :endTimeApply ");
            mapParam.put("endTimeApply", form.getEndTimeApply());
        }
        
        queryString.append("AND tb1.JUDGE_RESULT in (");
        
        if (!form.getUnexamined().equals("off")) {
            form.setUnexamined("1");            
            queryString.append(":unexamined");
            mapParam.put("unexamined", form.getUnexamined());
            
            if (!form.getApproval().equals("off")) {    
                form.setApproval("2");
                queryString.append(",:approval");
                mapParam.put("approval", form.getApproval());
            }
             
            if (!form.getReject().equals("off")) {
                form.setReject("3");
                queryString.append(",:reject");
                mapParam.put("reject", form.getReject());
            }
        }
        
        if (!form.getApproval().equals("off") && form.getUnexamined().equals("off")) {
            form.setApproval("2");
            queryString.append(":approval");
            mapParam.put("approval", form.getApproval());
            
            if (!form.getReject().equals("off")) {
                form.setReject("3");
                queryString.append(",:reject");
                mapParam.put("reject", form.getReject());
            }
        }
        
        if (!form.getReject().equals("off") && form.getUnexamined().equals("off") && form.getApproval().equals("off")) {
            form.setReject("3");
            queryString.append(":reject");
            mapParam.put("reject", form.getReject());
        }
        
        queryString.append(") ORDER BY tb1.APPLICATION_TIME DESC ");
        queryString.append("LIMIT :size ");
        queryString.append("OFFSET :page ");
        mapParam.put("size", size);
        mapParam.put("page", page);
        
        return namedParameterJdbcTemplate.query(queryString.toString(), new MapSqlParameterSource(mapParam), new TBL400BDAO());
        
    }
    
    
    /**
     * method TotalSearchResults
     * 
     * @for: GCA0110
     * @author tbthien
     * @param 
     * @param 
     * @return 
     */
    public Integer TotalSearchResults(SearchForm form) { 
        
        StringBuilder queryString = new StringBuilder(CommonConstants.BLANK);
        Map<String, Object> mapParam = new HashedMap();
        
        queryString.append("SELECT COUNT(tb1.APPLICATION_NO) ");        
        queryString.append("FROM tbl400 tb1, tbm001 tb2 ");
        queryString.append("WHERE tb1.DELETE_FLAG = '0' AND tb1.CITY_CODE = tb2.CITY_CODE ");
        
        if (!form.getCityCode().equals("")) {
            queryString.append("AND tb1.CITY_CODE = :cityCode ");
            mapParam.put("cityCode", form.getCityCode());
        }
        
        if (!form.getApartmentName().equals("")) {
            queryString.append("AND tb1.APARTMENT_NAME COLLATE utf8_unicode_ci LIKE :apartmentName ");
            mapParam.put("apartmentName", "%"+form.getApartmentName()+"%");
        }
        
        if (!form.getStartTimeApply().equals("") && !form.getStartTimeApply().equals("000000")) { 
            queryString.append("AND tb1.APPLICATION_TIME >= :startTimeApply ");
            mapParam.put("startTimeApply", form.getStartTimeApply());
        }
        
        if (!form.getEndTimeApply().equals("") && !form.getEndTimeApply().equals("240000")) { 
            queryString.append("AND tb1.APPLICATION_TIME <= :endTimeApply ");
            mapParam.put("endTimeApply", form.getEndTimeApply());
        }
        
        queryString.append("AND tb1.JUDGE_RESULT in (");
        
        if (!form.getUnexamined().equals("off")) {
            form.setUnexamined("1");            
            queryString.append(":unexamined");
            mapParam.put("unexamined", form.getUnexamined());
            
            if (!form.getApproval().equals("off")) {    
                form.setApproval("2");
                queryString.append(",:approval");
                mapParam.put("approval", form.getApproval());
            }
             
            if (!form.getReject().equals("off")) {
                form.setReject("3");
                queryString.append(",:reject");
                mapParam.put("reject", form.getReject());
            }
        }
        
        if (!form.getApproval().equals("off") && form.getUnexamined().equals("off")) {
            form.setApproval("2");
            queryString.append(":approval");
            mapParam.put("approval", form.getApproval());
            
            if (!form.getReject().equals("off")) {
                form.setReject("3");
                queryString.append(",:reject");
                mapParam.put("reject", form.getReject());
            }
        }
        
        if (!form.getReject().equals("off") && form.getUnexamined().equals("off") && form.getApproval().equals("off")) {
            form.setReject("3");
            queryString.append(":reject");
            mapParam.put("reject", form.getReject());
        }
        
        queryString.append(") ORDER BY tb1.APPLICATION_TIME DESC ");
        
        return namedParameterJdbcTemplate.queryForObject(queryString.toString(), new MapSqlParameterSource(mapParam), Integer.class);
                
    }
    
    
    /**
     * method mapRow
     * 
     * @for: GCA0110
     * @author tbthien
     * @param 
     * @param 
     * @return GCA0110Vo
     */
    @Override
    public GCA0110Vo mapRow(ResultSet rs, int rowNum) throws SQLException {
                
        GCA0110Vo vo = new GCA0110Vo();
        vo.setApplicationNo(rs.getString("APPLICATION_NO"));
        vo.setApplicationTime(rs.getString("APPLICATION_TIME"));
        vo.setApartmentName(rs.getString("APARTMENT_NAME"));
        vo.setAddress(rs.getString("ADDRESS"));
        vo.setCityCode(rs.getString("CITY_CODE"));
        vo.setCityName(rs.getString("CITY_NAME"));
        vo.setJudgeResult(rs.getString("JUDGE_RESULT"));
                                        
        return vo;
        
    }

}
