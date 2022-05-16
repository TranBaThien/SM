package jp.lg.tokyo.metro.mansion.todokede.common.enumerated;

public enum DeleteFlag {
    NOT_DELETE("0"), DELETED("1");

    private String flag;
    DeleteFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }
}
