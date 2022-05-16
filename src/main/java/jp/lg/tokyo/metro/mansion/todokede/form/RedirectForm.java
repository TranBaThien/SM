/*
 * @(#) RedirectForm.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/12/23
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.form;

import java.io.Serializable;

public class RedirectForm  implements Serializable {

    private static final long serialVersionUID = 1L;

    private String apartmentId;

    private String notificationNo;

    public RedirectForm() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getNotificationNo() {
        return notificationNo;
    }

    public void setNotificationNo(String notificationNo) {
        this.notificationNo = notificationNo;
    }
}
