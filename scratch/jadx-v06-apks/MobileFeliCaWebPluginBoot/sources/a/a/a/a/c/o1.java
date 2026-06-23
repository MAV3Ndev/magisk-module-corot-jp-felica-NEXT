package a.a.a.a.c;

/* JADX INFO: compiled from: ResDataPrblmAnalyze.java */
/* JADX INFO: loaded from: classes.dex */
public class o1 extends l1 {
    private int b = -1;
    private String c = null;

    public String d() {
        return this.c;
    }

    public int e() {
        return this.b;
    }

    public void f(String str) {
        this.c = str;
    }

    public void g(int i) {
        this.b = i;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ResDataPrblmAnalyze mgmtFlag = " + this.b);
        stringBuffer.append(", addInfo = " + this.c);
        return stringBuffer.toString();
    }
}
