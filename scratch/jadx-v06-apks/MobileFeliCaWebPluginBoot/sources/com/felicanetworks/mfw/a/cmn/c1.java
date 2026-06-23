package com.felicanetworks.mfw.a.cmn;

/* JADX INFO: compiled from: SysException.java */
/* JADX INFO: loaded from: classes.dex */
public class c1 extends RuntimeException {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Class f147a;
    private String b;

    public c1(Class cls, String str) {
        this(cls, str, null, null);
    }

    @Override // java.lang.Throwable
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(c1.class.getName());
        if (getMessage() != null) {
            stringBuffer.append(" : " + getMessage());
        }
        stringBuffer.append("\r\n");
        if (this.f147a != null) {
            stringBuffer.append("\tat " + this.f147a.getName());
            if (this.b != null) {
                stringBuffer.append("." + this.b + "()");
            }
            stringBuffer.append("\r\n");
        }
        if (getCause() != null) {
            stringBuffer.append("\tCaused by " + getCause() + "\r\n");
        }
        return stringBuffer.toString();
    }

    public c1(Class cls, String str, String str2) {
        this(cls, str, str2, null);
    }

    public c1(Class cls, String str, Throwable th) {
        this(cls, str, null, th);
    }

    public c1(Class cls, String str, String str2, Throwable th) {
        super(str2, th);
        this.f147a = cls;
        this.b = str;
    }
}
