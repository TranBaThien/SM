/*
 * @(#) SubmitFormFlag.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2020/01/14
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.common.enumerated;

/**
 * @author tqvu1
 *
 */

public enum SubmitFormFlag {

    IS_SEARCHING("1"), IS_PAGING("2"), IS_SORTING("3");

    private String flag;

    SubmitFormFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

}
