/*
 * @(#) IPasswordManagementLogic.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/11/27
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic;

import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.vo.GovernmentStaffInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ML030Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ML035Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ManagementAssociationApplicationVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ReissuePasswordAfterSaveVo;

public interface IPasswordManagementLogic {

    /**
     * Get government staff user login information.
     * @param loginId String
     * @param mailAddress String
     * @param availability String
     * @return GovernmentStaffInfoVo
     * @throws BusinessException when has error
     * @author tqvu1
     */
    public GovernmentStaffInfoVo getGovernmentStaffUserLoginInfo(String loginId, String mailAddress, String availability) throws BusinessException;

    /**
     * Get apartment manager user login information.
     * @param loginId String
     * @param mailAddress String
     * @param availability String
     * @param validityFlag String
     * @return ManagementAssociationApplicationVo
     * @throws BusinessException when has error
     * @author tqvu1
     */
    public ManagementAssociationApplicationVo getManagementAssociationInfo(
            String loginId, String mailAddress, String availability, String validityFlag) throws BusinessException;

    /**
     * Update data to table TBL120.
     * @param loginId String
     * @return ReissuePasswordAfterSaveVo
     * @throws BusinessException when has error
     * @author tqvu1
     */
    public ReissuePasswordAfterSaveVo saveDataToTbl120(String loginId) throws BusinessException;

    /**
     * Update data to table TBL110.
     * @param loginId String
     * @return ReissuePasswordAfterSaveVo
     * @throws BusinessException when has error
     * @author tqvu1
     */
    public ReissuePasswordAfterSaveVo saveDataToTbl110(String loginId) throws BusinessException;

    /**
     * Get ML030 Template.
     * @param reissuePasswordAfterSaveVo ReissuePasswordAfterSaveVo
     * @return ML030Vo
     * @throws BusinessException when has error
     * @author tqvu1
     */
    public ML030Vo getML030Template(ReissuePasswordAfterSaveVo reissuePasswordAfterSaveVo) throws BusinessException;

    /**
     * Get ML030 Template.
     * @param reissuePasswordAfterSaveVo ReissuePasswordAfterSaveVo
     * @return ML035Vo
     * @throws BusinessException when has error
     * @author tqvu1
     */
    public ML035Vo getML035Template(ReissuePasswordAfterSaveVo reissuePasswordAfterSaveVo) throws BusinessException;

    /**
     * Send ML030 using IMailLogic.
     * @param ml030Vo ML030Vo
     * @throws BusinessException when has error
     * @author tqvu1
     */
    public void sendML030(ML030Vo ml030Vo) throws BusinessException;

    /**
     * Send ML035 using IMailLogic.
     * @param ml035Vo ML035Vo
     * @throws BusinessException when has error
     * @author tqvu1
     */
    public void sendML035(ML035Vo ml035Vo) throws BusinessException;

}
