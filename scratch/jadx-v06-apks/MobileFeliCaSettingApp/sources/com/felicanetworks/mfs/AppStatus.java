package com.felicanetworks.mfs;

/* JADX INFO: loaded from: classes.dex */
public class AppStatus {
    public int felicaStatus;
    public boolean isNeedcheckbox;
    public State felicaInitState = State.READY;
    public int chk = -1;

    public enum State {
        READY,
        CHECK_NEEDINIT,
        CHECK_DONEINIT,
        CHECK_FAILED,
        INIT_SUCCESS,
        INIT_CANCEL,
        INIT_FAILED
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AppStatus[");
        sb.append(this.isNeedcheckbox ? "Needchkbx" : "");
        sb.append(',');
        sb.append(this.felicaStatus);
        sb.append(',');
        sb.append(this.felicaInitState);
        sb.append(',');
        sb.append(this.chk);
        sb.append("]");
        return sb.toString();
    }
}
