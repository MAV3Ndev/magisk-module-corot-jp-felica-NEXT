package a.a.a.a.c;

import java.util.ArrayList;

/* JADX INFO: compiled from: FeliCaAccessData.java */
/* JADX INFO: loaded from: classes.dex */
public class p0 implements o0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private byte[] f45a;
    private byte[] b;
    private ArrayList c = new ArrayList();

    @Override // a.a.a.a.c.o0
    public String a() {
        return "0002";
    }

    public void b(t0 t0Var) {
        this.c.add(t0Var);
    }

    public t0 c(int i) {
        return (t0) this.c.get(i);
    }

    public byte[] d() {
        return this.b;
    }

    public byte[] e() {
        return this.f45a;
    }

    public int f() {
        return this.c.size();
    }

    public void g(byte[] bArr) {
        this.b = bArr;
    }

    public void h(byte[] bArr) {
        this.f45a = bArr;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("FeliCaAccessData privilegedCommandCategory = [" + com.felicanetworks.mfw.a.cmn.b1.K(this.f45a) + "]");
        stringBuffer.append(", ordinaryCommandCategory = [" + com.felicanetworks.mfw.a.cmn.b1.K(this.b) + "]");
        for (int i = 0; i < this.c.size(); i++) {
            stringBuffer.append(", offlineAccessTargetArray[" + i + "] = " + this.c.get(i));
        }
        return stringBuffer.toString();
    }
}
