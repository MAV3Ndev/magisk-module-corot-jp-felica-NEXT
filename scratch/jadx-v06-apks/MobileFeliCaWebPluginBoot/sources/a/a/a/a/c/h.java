package a.a.a.a.c;

/* JADX INFO: compiled from: ChipIssuerInfo.java */
/* JADX INFO: loaded from: classes.dex */
public class h implements o0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private String f29a;

    @Override // a.a.a.a.c.o0
    public String a() {
        return "0001";
    }

    public String b() {
        return this.f29a;
    }

    public void c(String str) {
        this.f29a = str;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ChipIssuerInfo chipIssuerCode = " + this.f29a);
        return stringBuffer.toString();
    }
}
