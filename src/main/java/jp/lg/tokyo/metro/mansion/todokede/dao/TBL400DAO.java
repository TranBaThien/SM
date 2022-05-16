/*
 * @(#) TBL400DAO.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tbthien
 * Create Date: 2019/11/26
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL400Entity;
import jp.lg.tokyo.metro.mansion.todokede.vo.ManagementAssociationCustomVo;

/**
 * @author tbthien
 *
 */
@Repository
public interface TBL400DAO extends PagingAndSortingRepository<TBL400Entity, String> {
       
    /**
     * method getListManagementAssociation
     * 
     * @for: GCA0110
     * @author tbthien
     * @param 
     * @param 
     * @return List<ManagementAssociationCustomVo>
     */
      @Query("SELECT new jp.lg.tokyo.metro.mansion.todokede.vo.ManagementAssociationCustomVo(" +
             "tb1.applicationNo, " +
             "tb1.applicationTime, " +
             "tb1.apartmentName, " +
             "tb1.address, " +
             "tb1.cityCode, " +
             "tb2.cityName, " +
             "tb1.judgeResult) " +
             "FROM TBL400Entity tb1, TBM001Entity tb2 " +
             "WHERE tb1.cityCode = tb2.cityCode AND tb1.judgeResult = '1' AND tb1.deleteFlag = '0' " +
             "ORDER BY tb1.applicationTime DESC")    
      Page<ManagementAssociationCustomVo> getListManagementAssociation(Pageable pageable); 

    /**
       * method countManagementAssociationNumber
       * 
       * @for: GCA0110
       * @author tbthien
       * @param 
       * @param 
       * @return 
       */
      @Query("select count(tb1) from TBL400Entity tb1, TBM001Entity tb2 " +
             "where tb1.cityCode = tb2.cityCode AND tb1.judgeResult = '1' AND tb1.deleteFlag = '0'")
      int countManagementAssociationNumber();
      
      
    /**
    * method getListManagementAssociationAgain
    * 
    * @for: GCA0110
    * @author tbthien
    * @param listapplyno List
    * @return  List
    */
    @Query("SELECT new jp.lg.tokyo.metro.mansion.todokede.vo.ManagementAssociationCustomVo(" +
    "tb1.applicationNo, " +
    "tb1.applicationTime, " +
    "tb1.apartmentName, " +
    "tb1.address, " +
    "tb1.cityCode, " +
    "tb2.cityName, " +
    "tb1.judgeResult) " +
    "FROM TBL400Entity tb1, TBM001Entity tb2 " +
    "WHERE tb1.cityCode = tb2.cityCode AND tb1.deleteFlag = '0' AND tb1.applicationNo IN (:listapplyno)" +
    "ORDER BY tb1.applicationTime DESC")
    List<ManagementAssociationCustomVo> getListManagementAssociationAgain(@Param("listapplyno") List<String> listapplyno); 

    /**
     * method get application information
     * 
     * @Screen: GCA0120
     * @param applicationNo String
     * @return
     */
    @Query("SELECT new jp.lg.tokyo.metro.mansion.todokede.vo.ManagementAssociationCustomVo("
            + " tbl400.applicationNo,"
            + " tbl400.address,"
            + " tbl400.cityCode,"
            + " tbl400.judgeResult,"
            + " tbl400.judgeRemarks,"
            + " tbl400.apartmentNamePhonetic,"
            + " tbl400.zipCode,"
            + " tbl400.apartmentName,"
            + " tbl400.contactPropertyCode,"
            + " tbl400.contactPropertyElse,"
            + " tbl400.contactZipCode,"
            + " tbl400.contactAddress,"
            + " tbl400.contactTelNo,"
            + " tbl400.contactName,"
            + " tbl400.contactNamePhonetic,"
            + " tbl400.contactMailAddress,"
            + " tbl400.resultApartmentName,"
            + " tbl400.resultZipCode,"
            + " tbl400.resultAddress,"
            + " tbl400.updateDatetime,"
            + " tbl400.apartmentId,"
            + " tbm001.cityName)"
            + " FROM TBL400Entity tbl400"
            + " JOIN TBM001Entity tbm001 ON tbl400.cityCode = tbm001.cityCode"
            + " WHERE tbl400.applicationNo = :applicationNo"
            + " AND tbl400.deleteFlag ='0'"
            + " AND tbm001.deleteFlag ='0'")
    ManagementAssociationCustomVo getNewRegistrationInformation(@Param("applicationNo") String applicationNo);

    /**
    * method find By ApplicationNo
    * 
    * @Screen: GCA0120
    * @param applicationNo String
    * @return
    */
    @Query("SELECT u FROM TBL400Entity u WHERE applicationNo=:applicationNo and u.deleteFlag = '0'")
    public TBL400Entity findByApplicationNo(@Param("applicationNo") String applicationNo);

}
