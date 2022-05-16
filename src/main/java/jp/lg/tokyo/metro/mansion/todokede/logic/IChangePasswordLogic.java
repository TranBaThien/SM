/*
 * @(#) IChangePasswordLogic.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nhvu
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL105Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.ChangePasswordForm;

public interface IChangePasswordLogic extends IBaseLogic<Object> {

    public TBL110Entity changePasswordTBL110(ChangePasswordForm form) throws BusinessException;
    
    public void changerPasswordTBL120(ChangePasswordForm form) throws BusinessException;
    
    public TBL110Entity findByLoginToTBL110(String loginID) throws BusinessException;
    
    public TBL120Entity findByLoginToTBL120(String loginID) throws BusinessException;
    
    public TBL100Entity updateTBL100(ChangePasswordForm changePasswordForm) throws BusinessException;

    public TBL105Entity updateTBL105(ChangePasswordForm changePasswordForm) throws BusinessException;
    
    public void updateApartmentInfor(ChangePasswordForm changePasswordForm)throws BusinessException;
    
}
