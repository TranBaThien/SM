/*
 * @(#) TBM002DAOTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nvlong1
 * Create Date: 07/01/2020
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBM002Entity;

/**
 * @author nvlong1
 *
 */
public class TBM002DAOTest extends AbstractDaoTest {

    private final String CITY_CODE = "111111";
    private final String USED_CODE = "07";
    private final String WINDOW_FAX_NO = "FAX_987654320";
    private final String WINDOW_TEL_NO = "TEL_123456780";
    private final String WINDOW_BELONG = "問合せ窓口_部署（区市町村向け）";
    private final String WINDOW_CITY_NAME = "BBBBB";
    private final String WINDOW_MAIL_ADDRESS = "window2@gmail.com";
    private final String DELETE_FLAG = "0";

    @Autowired
    private TBM002DAO tbm002DAO;

    /**
     * 案件ID:GGA0110/チェックリストID:UT- UT-GGA0110-035/区分:N/チェック内容:Get Window Info Mansions When Exist Should Be Exist
     */
    @Test
    public void getWindowInformation_WhenExist_ShouldBeExist() {
        // Prepare data
        assertEntity(tbm002DAO.getWindowInformation(CITY_CODE, USED_CODE));
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-036/区分:E/チェック内容:Get Window Info Mansions When Exist Should Be Null
     */
    @Test
    public void getWindowInformation_WhenNotExist_ShouldBeNull() {
        // Execute
        TBM002Entity  actual = tbm002DAO.getWindowInformation(CITY_CODE, "00");
        assertThat(actual).isEqualTo(null);
    }
    
    /**
     * 案件ID:GGA0110/チェックリストID:UT- UT-GGA0110-037/区分:I/チェック内容:Test Input Get Window Information
     */
    @Test
    public void testInputGetWindowInformation() {
        // execute 
        TBM002Entity result = tbm002DAO.getWindowInformation(CITY_CODE, USED_CODE);
        
        //assert Result
        assertThat(result.getId().getCityCode()).isEqualTo(CITY_CODE);
        assertThat(result.getId().getUsedCode()).isEqualTo(USED_CODE);
    }

    private void assertEntity(TBM002Entity entity) {
        assertThat(entity.getId().getCityCode()).isEqualTo(CITY_CODE);
        assertThat(entity.getId().getUsedCode()).isEqualTo(USED_CODE);
        assertThat(entity.getWindowFaxNo()).isEqualTo(WINDOW_FAX_NO);
        assertThat(entity.getWindowTelNo()).isEqualTo(WINDOW_TEL_NO);
        assertThat(entity.getWindowBelong()).isEqualTo(WINDOW_BELONG);
        assertThat(entity.getWindowCityName()).isEqualTo(WINDOW_CITY_NAME);
        assertThat(entity.getWindowMailAddress()).isEqualTo(WINDOW_MAIL_ADDRESS);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG);
        assertThat(entity.getUpdateUserId()).isEqualTo(null);
        assertThat(entity.getUpdateDatetime()).isEqualTo(null);
    }
}
