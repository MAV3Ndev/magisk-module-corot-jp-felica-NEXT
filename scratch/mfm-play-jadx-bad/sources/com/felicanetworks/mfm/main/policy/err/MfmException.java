package com.felicanetworks.mfm.main.policy.err;

import com.felicanetworks.mfm.main.policy.fix.ClassMap;
import com.felicanetworks.mfm.main.policy.fix.Information;
import java.util.Arrays;
import java.util.Locale;

/* JADX INFO: loaded from: classes3.dex */
public class MfmException extends Exception {
    private String errCode;
    private MfmException essence;
    private int extra;
    private Exception firstCaughtException;
    private int index;
    private Class occurCls;
    private StackTraceElement[] stacktraces;

    @Override // java.lang.Throwable
    public String toString() {
        return "MfmException{occurCls=" + this.occurCls + ", index=" + String.format(Locale.US, "0x%03X", Integer.valueOf(this.index)) + ", firstCaughtException=" + this.firstCaughtException.getClass().getSimpleName() + ", extra=" + String.format(Locale.US, "0x%04X", Integer.valueOf(this.extra)) + ", stacktraces=" + Arrays.toString(this.stacktraces) + ", errCode='" + this.errCode + "', essence=" + this.essence.getClass().getSimpleName() + "} " + super.toString();
    }

    public MfmException(Class cls, int i, Exception e, int ex, String msg) {
        super(msg, e);
        if (e != null) {
            this.firstCaughtException = e;
        } else {
            this.firstCaughtException = this;
        }
        this.occurCls = cls;
        this.index = i;
        this.extra = ex;
        this.errCode = String.format(Locale.US, "%04d", Integer.valueOf(Information.versionCode())) + "-" + resolveClassCode(this.occurCls) + "-" + String.format(Locale.US, "%03X", Integer.valueOf(this.index)) + "-" + resolveClassCode(this.firstCaughtException.getClass()) + "-" + String.format(Locale.US, "%04X", Integer.valueOf(this.extra));
        this.stacktraces = this.firstCaughtException.getStackTrace();
        if (e instanceof MfmException) {
            this.essence = (MfmException) e;
        } else {
            this.essence = this;
        }
    }

    public MfmException(Class cls, int i, Exception e, int ex) {
        this(cls, i, e, ex, null);
    }

    public MfmException(Class cls, int index, Exception e, String msg) {
        this(cls, index, e, 0, msg);
    }

    public MfmException(Class cls, int index, Exception e) {
        this(cls, index, e, 0, null);
    }

    public MfmException(Class cls, int index, String msg) {
        this(cls, index, null, 0, msg);
    }

    public MfmException(Class cls, int index) {
        this(cls, index, null, 0, null);
    }

    public String getErrorCode() {
        return this.essence.errCode;
    }

    private String resolveClassCode(Class cls) {
        Integer num = ClassMap.getInstance().get((Class<?>) cls);
        return String.format(Locale.US, "%03X", Integer.valueOf(num != null ? num.intValue() : 0));
    }

    public Exception getFirstCaughtException() {
        return this.firstCaughtException;
    }

    public int getIndex() {
        return this.index;
    }

    public int getExtra() {
        return this.extra;
    }
}
