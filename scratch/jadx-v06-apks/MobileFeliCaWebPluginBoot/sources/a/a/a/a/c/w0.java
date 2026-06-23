package a.a.a.a.c;

/* JADX INFO: compiled from: OpenPlatformApp.java */
/* JADX INFO: loaded from: classes.dex */
public class w0 implements o0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private byte[] f59a;

    @Override // a.a.a.a.c.o0
    public String a() {
        return "0004";
    }

    public byte[] b() {
        return this.f59a;
    }

    public void c(byte[] bArr) {
        this.f59a = bArr;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("applicationSignerCertificationHash = [" + com.felicanetworks.mfw.a.cmn.b1.K(this.f59a) + "]");
        return stringBuffer.toString();
    }
}
