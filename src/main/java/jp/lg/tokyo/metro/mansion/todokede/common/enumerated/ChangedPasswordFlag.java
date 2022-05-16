package jp.lg.tokyo.metro.mansion.todokede.common.enumerated;

public enum ChangedPasswordFlag {
    CHANGED("1"), UNCHANGED("0");
    private String flag;
    ChangedPasswordFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }
}
