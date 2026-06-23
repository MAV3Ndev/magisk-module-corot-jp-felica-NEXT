package a.a.a.a.c;

import java.util.ArrayList;

/* JADX INFO: compiled from: CmdRslt.java */
/* JADX INFO: loaded from: classes.dex */
public class h0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private String f30a;
    private String b;
    private ArrayList c = new ArrayList();

    public void a(com.felicanetworks.mfw.a.cmn.i0 i0Var) {
        this.c.add(i0Var);
    }

    public String b() {
        return this.f30a;
    }

    public String c() {
        return this.b;
    }

    public ArrayList d() {
        return this.c;
    }

    public void e(String str) {
        this.f30a = str;
    }

    public void f(String str) {
        this.b = str;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("CmdRslt cmdName = " + this.f30a);
        stringBuffer.append(", resCode = " + this.b);
        for (int i = 0; i < this.c.size(); i++) {
            stringBuffer.append(", respItemList[" + i + "] = " + this.c.get(i));
        }
        return stringBuffer.toString();
    }
}
