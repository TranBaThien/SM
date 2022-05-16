/*
 * @(#) JDayOfWeek.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/12/11
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.common.enumerated;

import java.time.DayOfWeek;
import java.util.Arrays;

public enum JDayOfWeek {
    MONDAY("月曜"), TUESDAY("火曜日"), WEDNESDAY("水曜日"), THURSDAY("木曜日"), FRIDAY("金曜日"), SATURDAY("土曜日"), SUNDAY("日曜日");

    private String jDayOfWeek;
    JDayOfWeek(String jDayOfWeek) {
        this.jDayOfWeek = jDayOfWeek;
    }

    public String getJDayOfWeek() {
        return jDayOfWeek;
    }

    public static JDayOfWeek parseFrom(DayOfWeek dayOfWeek) {
        return Arrays.stream(JDayOfWeek.values())
                .filter(jDayOfWeek -> jDayOfWeek.name().equals(dayOfWeek.name()))
                .findFirst()
                .orElse(null);
    }
}
