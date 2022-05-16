/*
 * @(#) ApartmentNotificationForm.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/12/23
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.form;

import jp.lg.tokyo.metro.mansion.todokede.vo.*;

import java.io.*;

public class ApartmentNotificationForm extends BaseMansionInfoVo implements Serializable {
    private static final long serialVersionUID = 10L;
    private BasicReportInfoForm basicReportInfo;
    private NotificationInfoAreaCommonForm infoAreaCommon;
    private NotificationAcceptanceForm acceptanceInfo;

    public BasicReportInfoForm getBasicReportInfo() {
        return basicReportInfo;
    }

    public void setBasicReportInfo(BasicReportInfoForm basicReportInfo) {
        this.basicReportInfo = basicReportInfo;
    }

    public NotificationInfoAreaCommonForm getInfoAreaCommon() {
        return infoAreaCommon;
    }

    public void setInfoAreaCommon(NotificationInfoAreaCommonForm infoAreaCommon) {
        this.infoAreaCommon = infoAreaCommon;
    }

    public NotificationAcceptanceForm getAcceptanceInfo() {
        return acceptanceInfo;
    }

    public void setAcceptanceInfo(NotificationAcceptanceForm acceptanceInfo) {
        this.acceptanceInfo = acceptanceInfo;
    }
}
