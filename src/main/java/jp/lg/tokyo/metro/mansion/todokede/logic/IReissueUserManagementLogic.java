/*
 * @(#)IReissueManagementUserLogic.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author: hbthinh
 * Create Date: Dec 2, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic;

import jp.lg.tokyo.metro.mansion.todokede.vo.ReissueUserManagementVo;

/**
 * @author hbthinh
 *
 */
public interface IReissueUserManagementLogic {
    
    boolean updateUserLogin(String apartmentId, ReissueUserManagementVo reissueUserManagementVo);
    
    ReissueUserManagementVo getUserLoginInformation(String apartmentId);
    
    ReissueUserManagementVo getUserLoginInformationByLoginId(String loginId);
}
