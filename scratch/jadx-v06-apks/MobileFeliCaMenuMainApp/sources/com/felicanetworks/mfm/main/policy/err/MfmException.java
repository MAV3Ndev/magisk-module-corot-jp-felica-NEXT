package com.felicanetworks.mfm.main.policy.err;

import com.felicanetworks.mfm.main.policy.fix.ClassMap;
import com.felicanetworks.mfm.main.policy.fix.Information;
import java.util.Arrays;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
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

    public MfmException(Class cls, int i, Exception exc, int i2, String str) {
        super(str, exc);
        if (exc != null) {
            this.firstCaughtException = exc;
        } else {
            this.firstCaughtException = this;
        }
        this.occurCls = cls;
        this.index = i;
        this.extra = i2;
        this.errCode = String.format(Locale.US, "%04d", Integer.valueOf(Information.versionCode())) + "-" + resolveClassCode(this.occurCls) + "-" + String.format(Locale.US, "%03X", Integer.valueOf(this.index)) + "-" + resolveClassCode(this.firstCaughtException.getClass()) + "-" + String.format(Locale.US, "%04X", Integer.valueOf(this.extra));
        this.stacktraces = this.firstCaughtException.getStackTrace();
        if (exc instanceof MfmException) {
            this.essence = (MfmException) exc;
        } else {
            this.essence = this;
        }
    }

    public MfmException(Class cls, int i, Exception exc, int i2) {
        this(cls, i, exc, i2, null);
    }

    public MfmException(Class cls, int i, Exception exc, String str) {
        this(cls, i, exc, 0, str);
    }

    public MfmException(Class cls, int i, Exception exc) {
        this(cls, i, exc, 0, null);
    }

    public MfmException(Class cls, int i, String str) {
        this(cls, i, null, 0, str);
    }

    public MfmException(Class cls, int i) {
        this(cls, i, null, 0, null);
    }

    public String getErrorCode() {
        return this.essence.errCode;
    }

    private String resolveClassCode(Class cls) {
        Integer num = ClassMap.getInstance().get((Class<?>) cls);
        Locale locale = Locale.US;
        Object[] objArr = new Object[1];
        objArr[0] = Integer.valueOf(num != null ? num.intValue() : 0);
        return String.format(locale, "%03X", objArr);
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
