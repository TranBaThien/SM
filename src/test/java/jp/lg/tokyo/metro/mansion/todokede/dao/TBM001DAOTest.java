/*
 * @(#) TBM001DAOTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nvlong1
 * Create Date: Dec 18, 2019
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBM001Entity;
import jp.lg.tokyo.metro.mansion.todokede.vo.CityVo;

/**
 * @author nvlong1
 *
 */
public class TBM001DAOTest extends AbstractDaoTest {

    @Autowired
    private TBM001DAO tbm001DAO;

    private final String CITY_CODE = "130001";
    private final String CITY_NAME = "東京都";
    private final String USER_AUTHORITY = "1";
    private final String DOCUMENT_NO = "住住マ";
    private final String CHIEF_NAME = "小池　百合子";
    private final String CITY_HEAD_WORD = "tkt";
    private final String DELETE_FLAG = "0";
    private final String UPDATE_USER_ID = "";
    private final LocalDateTime UPDATE_DATETIME = LocalDateTime.parse("2019-12-12 15:02:51", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    /**
     * 案件ID:GFA0110/チェックリストID:UT- GFA0110-006-01/区")/区分:I/チェック内容:Test Get Municipal Master Information Success
     */
    @Test
    public void getMunicipalMasterInfo_WhenExist_ShouldBeExist() {
        // Execute
        TBM001Entity actual = tbm001DAO.getMunicipalMasterInfo(CITY_CODE);

        // Check result
        assertEntity(actual);
    }

    /**
     * 案件ID:GFA0110/チェックリストID:UT- GFA0110-006-02/区")/区分:E/チェック内容:Test Get Municipal Master Information Return Null When Not Exist
     */
    @Test
    public void getMunicipalMasterInfo_WhenNotExist_ShouldBeNull() {
        // Execute
        TBM001Entity actual = tbm001DAO.getMunicipalMasterInfo(null);

        // Check result
        assertThat(actual).isEqualTo(null);
    }

    private void assertEntity(TBM001Entity entity) {
        assertThat(entity.getCityCode()).isEqualTo(CITY_CODE);
        assertThat(entity.getCityName()).isEqualTo(CITY_NAME);
        assertThat(entity.getUserAuthority()).isEqualTo(USER_AUTHORITY);
        assertThat(entity.getDocumentNo()).isEqualTo(DOCUMENT_NO);
        assertThat(entity.getChiefName()).isEqualTo(CHIEF_NAME);
        assertThat(entity.getCityHeadWord()).isEqualTo(CITY_HEAD_WORD);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME);
    }
    
    private TBM001Entity generateTBM001Entity() {
        TBM001Entity entity = new TBM001Entity();
        entity.setCityCode(CITY_CODE);
        entity.setCityName(CITY_NAME);
        entity.setUserAuthority(USER_AUTHORITY);
        entity.setDocumentNo(DOCUMENT_NO);
        entity.setChiefName(CHIEF_NAME);
        entity.setCityHeadWord(CITY_HEAD_WORD);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setUpdateDatetime(UPDATE_DATETIME);

        return entity;
    }
    
    private List<String> newListCityName(){
        
        List<String> listCityName = new ArrayList<String>();
        listCityName.add("小笠原村");
        listCityName.add("東京都");
        listCityName.add("千代田区");
        listCityName.add("中央区");
        listCityName.add("港区");
        listCityName.add("新宿区");
        listCityName.add("文京区");
        listCityName.add("台東区");
        listCityName.add("墨田区");
        listCityName.add("江東区");
        listCityName.add("品川区");
        listCityName.add("目黒区");
        listCityName.add("大田区");
        listCityName.add("世田谷区");
        listCityName.add("渋谷区");
        listCityName.add("中野区");
        listCityName.add("杉並区");
        listCityName.add("豊島区");
        listCityName.add("北区");
        listCityName.add("荒川区");
        listCityName.add("板橋区");
        listCityName.add("練馬区");
        listCityName.add("足立区");
        listCityName.add("葛飾区");
        listCityName.add("江戸川区");
        listCityName.add("八王子市");
        listCityName.add("立川市");
        listCityName.add("武蔵野市");
        listCityName.add("三鷹市");
        listCityName.add("青梅市");
        listCityName.add("府中市");
        listCityName.add("昭島市");
        listCityName.add("調布市");
        listCityName.add("町田市");
        listCityName.add("小金井市");
        listCityName.add("小平市");
        listCityName.add("日野市");
        listCityName.add("東村山市");
        listCityName.add("国分寺市");
        listCityName.add("国立市");
        listCityName.add("福生市");
        listCityName.add("狛江市");
        listCityName.add("東大和市");
        listCityName.add("清瀬市");
        listCityName.add("東久留米市");
        listCityName.add("武蔵村山市");
        listCityName.add("多摩市");
        listCityName.add("稲城市");
        listCityName.add("羽村市");
        listCityName.add("あきる野市");
        listCityName.add("西東京市");
        listCityName.add("瑞穂町");
        listCityName.add("日の出町");
        listCityName.add("檜原村");
        listCityName.add("奥多摩町");
        listCityName.add("大島町");
        listCityName.add("利島村");
        listCityName.add("新島村");
        listCityName.add("神津島村");
        listCityName.add("三宅村");
        listCityName.add("御蔵島村");
        listCityName.add("八丈町");
        listCityName.add("青ヶ島村");
        listCityName.add("小笠原村");
        listCityName.add("TEST");
        
       return listCityName;
    }
    
    
    /**
     * 案件ID:AAA0110/チェックリストID:UT- UT- AAA0110-003	/区")/区分:N/チェック内容:test get cityCode by cityName when exist
     */
    @Test
    public void getCityCodeByCityName_WhenExist() {
        // Execute
        String actual = tbm001DAO.getCityCodeByCityName(CITY_NAME);

        // Check result
        assertThat(actual).isEqualTo(CITY_CODE);
    }
    
    /**
     * 案件ID:AAA0110/チェックリストID:UT- UT- AAA0110-004	/区")/区分:N/チェック内容:test get cityCode by cityName when exist
     */
    @Test
    public void getCityCodeByCityName_WhenNotExist() {
    	// Execute
        String actual = tbm001DAO.getCityCodeByCityName("abc");

        // Check result
        assertThat(actual).isEqualTo(null);
    }
    
    /**
     * 案件ID:AAA0110/チェックリストID:UT- UT- AAA0110-005	/区")/区分:N/チェック内容:test get cityHeadWord by cityName when exist
     */
    @Test
    public void getCityHeadWordByCityName_WhenExist() {
    	// Execute
        String actual = tbm001DAO.getCityHeadWordByCityCode(CITY_CODE);

        // Check result
        assertThat(actual).isEqualTo(CITY_HEAD_WORD);
    }
        
    /**
     * 案件ID:AAA0110/チェックリストID:UT- UT- AAA0110-007	/区")/区分:N/チェック内容:test get count city name when exist
     */
    @Test
    public void getCountCityName_WhenExist() {
    	// Execute
        List<String> actual = tbm001DAO.getListCityName();

        // Check result
        assertThat(actual).isEqualTo(newListCityName());
    }
    
    
    /**
     * 案件ID:AAA0110/チェックリストID:UT- UT- AAA0110-006   /区")/区分:N/チェック内容:test get cityHeadWord by cityName when not exist
     */
    @Test
    public void testGetCityHeadWordByCityCode_WhenExist() {
        String expected = "tkt";
        // Execute
        String actual = tbm001DAO.getCityHeadWordByCityCode(CITY_CODE);

        // Check result
        assertThat(actual).isEqualTo(expected);
    }
    
    /**
     * 案件ID:GCA0110/チェックリストID:UT- GCA0110-001/区分:N/チェック内容: test get List City Success
     * 
     */
    @Test
    public void getListCity_WhenSuccess() { 
        
        List<TBM001Entity> list = tbm001DAO.getListCity(); 
        
        CityVo cityvo = new CityVo();
        
        cityvo.setCityCode("111111");
        cityvo.setCityName("小笠原村");

        assertThat(list.get(0).getCityCode()).isEqualTo(cityvo.getCityCode());
        assertThat(list.get(0).getCityName()).isEqualTo(cityvo.getCityName());

    } 
}
