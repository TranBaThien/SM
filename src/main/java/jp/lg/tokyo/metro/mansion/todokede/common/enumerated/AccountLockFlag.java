package jp.lg.tokyo.metro.mansion.todokede.common.enumerated;

public enum AccountLockFlag {
    LOCK("1"), UNLOCK("0");

    private String flag;
    AccountLockFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }
}
