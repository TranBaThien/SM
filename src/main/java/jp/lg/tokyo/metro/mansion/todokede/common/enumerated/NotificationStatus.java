/*
 * @(#) NotificationStatus.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author hntvy
 * Create Date: Dec 16, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.common.enumerated;

/**
 * @author hntvy
 *
 */
public enum NotificationStatus {
    UNREPORTED("未届"),
    REPORTED_UNDER_REVIEW("届出済（審査中）"),
    CHANGE_NOTIFICATION_COMPLETED_UNDER_REVIEW("変更届出済（審査中）"),
    ACCEPTED("受理済"),
    REPORTED_ACCEPTED("届出済（受理済）"),
    CHANGE_NOTIFICATION_ACCEPTED("変更届出済（受理済）");
    
    
    private String notificationStatus;

    /**
     * @return the notificationStatus
     */
    public String getNotificationStatus() {
        return notificationStatus;
    }

    /**
     * @param notificationStatus String
     */
    private NotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }
    

}
