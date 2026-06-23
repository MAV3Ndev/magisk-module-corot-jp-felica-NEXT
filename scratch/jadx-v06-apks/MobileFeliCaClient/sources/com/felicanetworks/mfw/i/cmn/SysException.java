package com.felicanetworks.mfw.i.cmn;

import com.felicanetworks.mfw.i.fbl.Property;

/* JADX INFO: loaded from: classes.dex */
public class SysException extends RuntimeException {
    private static final long serialVersionUID = 2663030884552211470L;
    private Class<?> mCauseClass;
    private String mCauseMethod;
    private Throwable mCaused;
    private String mMessage;

    public SysException(Class<?> cls, String str) {
        this(cls, str, Property.URL_VERUP_SITE, null);
    }

    public SysException(Class<?> cls, String str, String str2) {
        this(cls, str, str2, null);
    }

    public SysException(Class<?> cls, String str, Throwable th) {
        this(cls, str, Property.URL_VERUP_SITE, th);
    }

    public SysException(Class<?> cls, String str, String str2, Throwable th) {
        str = str == null ? Property.URL_VERUP_SITE : str;
        str2 = str2 == null ? Property.URL_VERUP_SITE : str2;
        this.mCauseClass = cls;
        this.mCauseMethod = str;
        this.mMessage = str2;
        this.mCaused = th;
    }

    public Class<?> getCauseClass() {
        return this.mCauseClass;
    }

    public String getCauseMethod() {
        return this.mCauseMethod;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.mMessage;
    }

    public Throwable getCaused() {
        return this.mCaused;
    }

    @Override // java.lang.Throwable
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getClass().getName());
        if (!this.mMessage.equals(Property.URL_VERUP_SITE)) {
            stringBuffer.append(" : " + this.mMessage);
        }
        stringBuffer.append("\r\n");
        if (this.mCauseClass != null) {
            stringBuffer.append("\tat " + this.mCauseClass.getName());
            if (!this.mCauseMethod.equals(Property.URL_VERUP_SITE)) {
                stringBuffer.append("." + this.mCauseMethod + "()");
            }
            stringBuffer.append("\r\n");
        }
        if (this.mCaused != null) {
            stringBuffer.append("\tCaused by " + this.mCaused + "\r\n");
        }
        return stringBuffer.toString();
    }
}
