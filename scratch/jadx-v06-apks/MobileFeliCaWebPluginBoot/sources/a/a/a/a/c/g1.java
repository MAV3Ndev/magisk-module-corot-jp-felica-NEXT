package a.a.a.a.c;

/* JADX INFO: compiled from: PermitOptr.java */
/* JADX INFO: loaded from: classes.dex */
class g1 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private byte[] f28a;
    private int b;
    private t1 c;

    private g1(h1 h1Var) {
        this.c = new f1(this);
    }

    public byte[] c(String str, String str2) {
        com.felicanetworks.mfw.a.cmn.d1.d(str, str2, this.c);
        return this.f28a;
    }

    public boolean d(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        com.felicanetworks.mfw.a.cmn.d1.e(bArr, bArr.length, bArr2, bArr2.length, bArr3, bArr3.length, this.c);
        return this.b == 0;
    }
}
