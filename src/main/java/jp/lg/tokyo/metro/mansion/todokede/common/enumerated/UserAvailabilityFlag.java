package jp.lg.tokyo.metro.mansion.todokede.common.enumerated;

public enum UserAvailabilityFlag {
    POSSIBLE("1"), IMPOSSIBLE("2");

    private String flag;
    UserAvailabilityFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }
}
