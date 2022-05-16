/*
 * @(#) ManagementAssociationLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tbthien
 * Create Date: 2020/01/06
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.dao;

import org.springframework.beans.factory.annotation.Autowired;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonProperties;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SequenceUtil;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL400Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.SearchForm;
import jp.lg.tokyo.metro.mansion.todokede.vo.GCA0110Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ManagementAssociationCustomVo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL400;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author tbthien
 *
 */
public class TBL400BDAOTest extends AbstractDaoTest { 

	@Autowired
	private TBL400BDAO tbl400bdao;
	
	/**
     * 案件ID:GCA0110/チェックリストID:UT- GCA0110-001/区分:N/チェック内容: test search Managemen tAssociation success
     * 
     */
	
	@Test
	@Sql(value = "classpath:/sql/UT-GCA0110-001.sql")
    public void TestsearchManagementAssociation() {
    	
		int page = 0;
		int size = 50;
		
		SearchForm form = new SearchForm();
		
		form.setCityCode("131059");
		form.setApartmentName("アパート 1");
		form.setStartTimeApply("20191203000000");
		form.setEndTimeApply("20191205240000");
		form.setUnexamined("on");
		form.setApproval("off");
		form.setReject("off");
		
		GCA0110Vo gvo = new GCA0110Vo();
		
		gvo.setApplicationNo("1");
		gvo.setApplicationTime("20191204101010");
		gvo.setApartmentName("アパート 1");
		gvo.setAddress("12 NA");
		gvo.setCityCode("131059");
		gvo.setCityName("文京区");
		gvo.setJudgeResult("1");
		
		List<GCA0110Vo> list = tbl400bdao.searchManagementAssociation(form, page, size);
		
		for(GCA0110Vo vo : list) {
			assertThat(vo.getApplicationNo()).isEqualTo(gvo.getApplicationNo());
			assertThat(vo.getApplicationTime()).isEqualTo(gvo.getApplicationTime());
			assertThat(vo.getApartmentName()).isEqualTo(gvo.getApartmentName());
			assertThat(vo.getAddress()).isEqualTo(gvo.getAddress());
			assertThat(vo.getCityCode()).isEqualTo(gvo.getCityCode());
			assertThat(vo.getCityName()).isEqualTo(gvo.getCityName());
			assertThat(vo.getJudgeResult()).isEqualTo(gvo.getJudgeResult());
		}
		
    }			
	
	/**
     * 案件ID:GCA0110/チェックリストID:UT- GCA0110-002/区分:I/チェック内容: Test Get Total Search Results Success
     * 
     */
	@Test
	@Sql(value = "classpath:/sql/UT-GCA0110-001.sql")
	public void TestTotalSearchResults() {
		
		SearchForm form = new SearchForm();
		
		form.setCityCode("131059");
		form.setApartmentName("アパート 1");
		form.setStartTimeApply("20191203000000");
		form.setEndTimeApply("20191205240000");
		form.setUnexamined("on");
		form.setApproval("off");
		form.setReject("off");
		
		int result = tbl400bdao.TotalSearchResults(form);
		
		assertThat(result).isEqualTo(1); 
		
	}
}
