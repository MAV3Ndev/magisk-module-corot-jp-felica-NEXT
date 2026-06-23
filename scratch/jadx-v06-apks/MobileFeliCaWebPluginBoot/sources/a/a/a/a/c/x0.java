package a.a.a.a.c;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: Permit.java */
/* JADX INFO: loaded from: classes.dex */
public class x0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private String f61a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private byte[] j;
    private List k = new ArrayList();

    public void a(o0 o0Var) {
        this.k.add(o0Var);
    }

    public int b() {
        return this.k.size();
    }

    public o0 c(int i) {
        return (o0) this.k.get(i);
    }

    public o0 d(String str) {
        for (int i = 0; i < this.k.size(); i++) {
            o0 o0Var = (o0) this.k.get(i);
            if (o0Var.a().equals(str)) {
                return o0Var;
            }
        }
        return null;
    }

    public String e() {
        return this.g;
    }

    public String f() {
        return this.f;
    }

    public String g() {
        return this.b;
    }

    public String h() {
        return this.i;
    }

    public String i() {
        return this.d;
    }

    public String j() {
        return this.h;
    }

    public void k(String str) {
        this.e = str;
    }

    public void l(String str) {
        this.g = str;
    }

    public void m(String str) {
        this.f = str;
    }

    public void n(String str) {
        this.c = str;
    }

    public void o(String str) {
        this.b = str;
    }

    public void p(String str) {
        this.i = str;
    }

    public void q(String str) {
        this.d = str;
    }

    public void r(String str) {
        this.h = str;
    }

    public void s(byte[] bArr) {
        this.j = bArr;
    }

    public void t(String str) {
        this.f61a = str;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Permit version = " + this.f61a);
        stringBuffer.append(", permitType = " + this.b);
        stringBuffer.append(", permitIssuerId = " + this.c);
        stringBuffer.append(", serialNum = " + this.d);
        stringBuffer.append(", keyId = " + this.e);
        stringBuffer.append(", notBefore = " + this.f);
        stringBuffer.append(", notAfter = " + this.g);
        stringBuffer.append(", serviceId = " + this.h);
        stringBuffer.append(", rvctionDistributionPoint = " + this.i);
        stringBuffer.append(", signature = [" + com.felicanetworks.mfw.a.cmn.b1.K(this.j) + "]");
        for (int i = 0; i < this.k.size(); i++) {
            stringBuffer.append(", extentionList[" + i + "] = {" + this.k.get(i) + "}");
        }
        return stringBuffer.toString();
    }
}
