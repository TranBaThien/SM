package jp.lg.tokyo.metro.mansion.todokede.common.enumerated;

public enum LoginStatusFlag {
    NOT_LOGGED_IN("0"), LOGGED_IN("1");

    private String flag;
    LoginStatusFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }
}
