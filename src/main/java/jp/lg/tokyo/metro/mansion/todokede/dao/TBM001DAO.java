/*
 * @(#) TBM001DAO.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author dlly
 * Create Date: 2019/11/28
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBM001Entity;

/**
 * @author DLLy
 *
 */
@Repository
public interface TBM001DAO extends JpaRepository<TBM001Entity, String> {

    /**
     * Screen GGA0110, GJA0120, GFA0110, GEA0110, GDA0110, MDA0110
     * Get city code by city name.
     * @param cityCode
     * @return String
     * @author tqvu1
     */
    @Query("SELECT tb FROM TBM001Entity tb WHERE tb.cityCode = :cityCode AND tb.deleteFlag = '0'")
    TBM001Entity getMunicipalMasterInfo(@Param("cityCode") String cityCode);

    /**
     * Get city code by city name.
     * @Sceen AAA0110
     * @param cityName
     * @return String
     * @author tqvu1
     */
    @Query("SELECT tbm001.cityCode FROM TBM001Entity tbm001 WHERE tbm001.cityName = :cityName AND tbm001.deleteFlag = '0'")
    String getCityCodeByCityName(@Param("cityName") String cityName);
    
    
    /**
     * Get city head word by city code
     * @Screen AAA0110
     * @param cityCode
     * @return
     * @author nbvhoang
     */
    @Query("SELECT tbm001.cityHeadWord FROM TBM001Entity tbm001 WHERE tbm001.cityCode = :cityCode AND tbm001.deleteFlag = '0'")
    String getCityHeadWordByCityCode(@Param("cityCode") String cityCode);

    /**
     * Screen ABB0110
     * Get list city name by deleteflag
     * @return List<String>
     * @author ptluan
     */
    @Query("SELECT tb FROM TBM001Entity tb WHERE tb.deleteFlag = '0'")
    List<TBM001Entity> getMunicipalMasterNotDelete();
    
    
    /**
	 * method getListCity
	 * 
	 * @Screen GCA0110, MCA0110
	 * @author tbthien
	 * @return List<TBM001Entity>
	 */
    @Query("SELECT tb FROM TBM001Entity tb WHERE tb.deleteFlag = '0' AND tb.cityName NOT IN ('東京都')")
    List<TBM001Entity> getListCity();
    
	/**
	 * get list city name from tbm001 - for check exist
	 * 
	 * @author nbvhoang
	 * @Screen AAA0110
	 * @param cityName
	 * @return
	 */
	@Query("SELECT T1.cityName FROM TBM001Entity T1 WHERE T1.deleteFlag='0'")
	public List<String> getListCityName();


	/**
	 * Get city head word by city code
	 * @Screen GJA0110
	 * @param cityCode
	 * @return
	 * @author nmtan
	 */
	@Query("SELECT tbm001.cityName FROM TBM001Entity tbm001 WHERE tbm001.cityCode = :cityCode AND tbm001.deleteFlag = '0'")
	String getCityNameByCityCode(@Param("cityCode") String cityCode);



}
