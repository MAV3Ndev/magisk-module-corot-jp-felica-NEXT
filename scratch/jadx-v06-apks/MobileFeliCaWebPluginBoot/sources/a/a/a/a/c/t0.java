package a.a.a.a.c;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: OfflineAccessTarget.java */
/* JADX INFO: loaded from: classes.dex */
public class t0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private String f54a;
    private List b = new ArrayList();

    public void a(r0 r0Var) {
        this.b.add(r0Var);
    }

    public r0 b(int i) {
        return (r0) this.b.get(i);
    }

    public String c() {
        return this.f54a;
    }

    public int d() {
        return this.b.size();
    }

    public void e(String str) {
        this.f54a = str;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("OfflineAccessTarget systemCode = " + this.f54a);
        for (int i = 0; i < this.b.size(); i++) {
            stringBuffer.append("nodeCodeRangeArray[" + i + "] = " + this.b.get(i));
        }
        return stringBuffer.toString();
    }
}
