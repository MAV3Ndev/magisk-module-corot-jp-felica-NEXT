package a.a.a.a.c;

import java.io.UnsupportedEncodingException;

/* JADX INFO: compiled from: ErrMsgOptr.java */
/* JADX INFO: loaded from: classes.dex */
public class m0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private com.felicanetworks.mfw.a.cmn.h0 f40a;

    public m0(com.felicanetworks.mfw.a.cmn.h0 h0Var) {
        this.f40a = h0Var;
    }

    private String b(String str, int i, String str2) throws UnsupportedEncodingException {
        byte[] bytes = str.getBytes(str2);
        if (bytes.length <= i) {
            return str;
        }
        byte[] bArr = new byte[i];
        System.arraycopy(bytes, 0, bArr, 0, i);
        return new String(bArr);
    }

    private String d(String str, String str2, String str3) {
        if (str3 == null) {
            str3 = "";
        }
        return str.replace(str2, str3);
    }

    private String e(int i) {
        try {
            return this.f40a.g().q(com.felicanetworks.mfw.a.boot.p.class.getField("e" + i).getInt(null));
        } catch (IllegalAccessException e) {
            throw new com.felicanetworks.mfw.a.cmn.c1(m0.class, "serchMessage", e);
        }
    }

    public String a(int i, String str) {
        try {
            return b(d(e(i), "%s", str), 1024, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new com.felicanetworks.mfw.a.cmn.c1(m0.class, "createMessage", e);
        } catch (NoSuchFieldException e2) {
            throw new com.felicanetworks.mfw.a.cmn.c1(m0.class, "createMessage", e2);
        }
    }

    public String c(int i) {
        return a(i, null);
    }
}
