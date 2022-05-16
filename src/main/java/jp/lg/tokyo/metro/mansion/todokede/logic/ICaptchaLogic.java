/*
 * @(#) ICaptchaLogic.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/11/28
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic;

public interface ICaptchaLogic {

    void save(String sessionId, String text);

    boolean match(String sessionId, String text);

}
