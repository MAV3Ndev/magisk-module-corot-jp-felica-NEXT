package a.a.a.a.c;

/* JADX INFO: compiled from: ResDataFormatVer.java */
/* JADX INFO: loaded from: classes.dex */
public class m1 extends l1 {
    private byte b;

    public int d() {
        return this.b;
    }

    public void e(byte b) {
        this.b = b;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ResDataFormatVer formatVersion = " + ((int) this.b));
        return stringBuffer.toString();
    }
}
