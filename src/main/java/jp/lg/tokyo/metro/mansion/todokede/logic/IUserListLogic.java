/*
 * @(#)IUserListLogic.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author ptluan
 * Create Date: 2019/11/30
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic;

import java.util.List;

import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.vo.UserInforVo;

public interface IUserListLogic {

    List<UserInforVo> getListUserInfo() throws BusinessException;


}
