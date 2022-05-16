/*
 * @(#) IManagementAssociationLogic.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tbthien
 * Create Date: 2019/11/26
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic;

import java.util.List;

import org.springframework.data.domain.Page;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL400Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.ApproveNewUserRegistrationForm;
import jp.lg.tokyo.metro.mansion.todokede.form.SearchForm;
import jp.lg.tokyo.metro.mansion.todokede.vo.ApproveNewUserRegistrationVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.GCA0110Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ML010Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ML020Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ManagementAssociationCustomVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.UserRegistrationVo;

/**
 * @author tbthien
 *
 */
public interface IManagementAssociationLogic {
    
    /**
     * Get management association information and city name from TBL400 and TBM001 to display on the screen
     * @param page int
     * @param size int
     * @return Page
     * 
     */
    Page<GCA0110Vo> getListManagementAssociation(int page, int size);
     
    /**
     * 
     * @param page int
     * @param size int
     * @param form SearchForm
     * @return Page
     */
    Page<GCA0110Vo> searchManagementAssociation(int page, int size, SearchForm form);
     

    /**
     * 
     * @param page int
     * @param size int
     * @return
     */
    int totalResultWhenDisplay(int page, int size);

    /**
     * Get total search results number when search from table TBL400
     * @param form SearchForm
     * @return int
     * 
     */
    int TotalSearchResults(SearchForm form);
     
    Page<GCA0110Vo> getListManagementAssociationAgain(int page, int size, SearchForm form, List<String> listapplyno);

    ApproveNewUserRegistrationVo getRegistrationApartmentInformation(ManagementAssociationCustomVo customVo)
            throws BusinessException;

    ApproveNewUserRegistrationVo getRegistrationApartmentInformation(ManagementAssociationCustomVo customVo,
            TBL100Entity entity);

    ML010Vo getML010Template(ApproveNewUserRegistrationVo approveNewUserRegistrationVo) throws BusinessException;

    void sendML010(ML010Vo ml010Vo) throws BusinessException;

    ML020Vo getML020Template(ApproveNewUserRegistrationVo approveNewUserRegistrationVo) throws BusinessException;

    void sendML020(ML020Vo ml020Vo) throws BusinessException;

    ApproveNewUserRegistrationVo saveToRegisterAparment(ApproveNewUserRegistrationForm form, UserRegistrationVo userVo)
            throws BusinessException;

    ManagementAssociationCustomVo getNewRegistrationInformation(String applicationNo);

    TBL100Entity getMansionInformationById(String apartmentId);

    List<TBL400Entity> findAll();

}
