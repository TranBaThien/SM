/*
 * @(#)TBL100DAO.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 *
 * Create Date: 2019/12/25
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;

/**
 * @author PVHung3
 *
 */
@Repository
public interface TBL100DAO extends JpaRepository<TBL100Entity, String> {

    /**
     * method get Mansion Information
     * 
     * @Screen: GEA0110, SBA0110, GFA0110, GEB0110, GDA0110, MDA0110, MBA0110, GIA0110
     * @param apartmentId String
     * @return TBL100Entity
     */
    @Query("SELECT tb FROM TBL100Entity tb WHERE tb.apartmentId = :apartmentId AND tb.deleteFlag='0'")
    public TBL100Entity getMansionInformationById(@Param("apartmentId") String apartmentId);

    /**
     * method get Latest Progress Information Date
     * 
     * @Screen: MBA0110
     * @param apartmentId String
     * @return TBL100Entity
     */
    @Query("SELECT T3.correspondDate FROM TBL100Entity T1 INNER JOIN TBL300Entity T3 ON T1.newestNotificationNo = T3.relatedNumber WHERE " 
        + "T1.apartmentId = :apartmentId AND T1.deleteFlag = '0' AND T3.deleteFlag = '0'") 
    public List<String> getLatestProgressInformationDate(@Param("apartmentId") String apartmentId);

    /**
    * get Apartment Information
    *
    * @author nhvu
    * @Screen GCA0120
    * @param apartmentName String
    * @param zipCode String
    * @param cityCode String
    * @return
    */
    @Query(value = "SELECT * FROM tbl100 tbl100"
            + " JOIN tbm001 tbm001 ON tbl100.CITY_CODE = tbm001.CITY_CODE"
            + " JOIN tbl110 tbl110 ON tbl100.APARTMENT_ID = tbl110.APARTMENT_ID" 
            + " WHERE tbl100.APARTMENT_NAME COLLATE utf8_unicode_ci LIKE %:apartmentName%" 
            + " AND tbl100.ZIP_CODE = :zipCode"
            + " AND tbl100.CITY_CODE = :cityCode" 
            + " AND tbl100.DELETE_FLAG = '0'"
            + " AND tbm001.DELETE_FLAG = '0'" 
            + " AND tbl110.VALIDITY_FLAG = '2'" 
            + " AND tbl110.DELETE_FLAG = '0'"
            + " ORDER BY tbl100.ADDRESS, tbl100.APARTMENT_NAME", nativeQuery = true)
    List<TBL100Entity> getRegistrationApartmentInformation(@Param("apartmentName") String apartmentName, @Param("zipCode") String zipCode,
            @Param("cityCode") String cityCode);
	
	/**
	 * get COUNT(1) from DB - for check exist
	 * 
	 * @author nbvhoang
	 * @Screen AAA0110
	 * @param apartmentName
	 * @param zipCode
	 * @param buildYear
	 * @return
	 */
	@Query(value = "SELECT COUNT(1) FROM tbl100 T1 WHERE T1.APARTMENT_NAME COLLATE utf8_unicode_ci = :apartmentName AND T1.ZIP_CODE = :zipCode AND T1.BUILD_YEAR = :buildYear AND T1.DELETE_FLAG = '0'", nativeQuery = true)
	public int getCount(@Param("apartmentName") String apartmentName, @Param("zipCode") String zipCode, @Param("buildYear") String buildYear);
	
	/**
	 * get list apartmentId for check exist
	 * 
	 * @author nbvhoang
	 * @Screen GEC0110
	 * @param apartmentId
	 * @return
	 */
	@Query("SELECT T1.apartmentId FROM TBL100Entity T1 WHERE T1.deleteFlag = '0'")
	public List<String> getListApartmentId();
	
	/**
	 * get cityCode by apartmentId
	 * 
	 * @author nbvhoang
	 * @Screen GEC0110
	 * @param apartmentId
	 * @return
	 */
	@Query("SELECT T1.cityCode FROM TBL100Entity T1 WHERE T1.apartmentId = :apartmentId AND T1.deleteFlag = '0'")
	public String getCityCodeByApartmentId(@Param("apartmentId") String apartmentId);
	
	/**
	 * Check appartment with support code is 01 then error
	 * @Screen: GEB0110
	 * @param apartmentId
	 * @param supportCd
	 * @return
	 */
	@Query("SELECT tb FROM TBL100Entity tb WHERE tb.apartmentId = :apartmentId AND tb.support = :supportCd AND tb.deleteFlag='0'")
	public TBL100Entity getMansiondByIdAndSupportCd(@Param("apartmentId") String apartmentId, @Param("supportCd") String supportCd);


	/**
	 * Check appartment with support code is 01 then error
	 * @Screen: GJA0110
	 * @param apartmentId
	 * @return
	 */
	@Query("SELECT tb.newestNotificationNo FROM TBL100Entity tb WHERE tb.apartmentId = :apartmentId AND tb.deleteFlag='0'")
	public String getNewestNotificationById (@Param("apartmentId") String apartmentId);



}
