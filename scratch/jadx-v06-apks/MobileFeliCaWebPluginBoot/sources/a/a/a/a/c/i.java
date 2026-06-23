package a.a.a.a.c;

import java.util.ArrayList;

/* JADX INFO: compiled from: CmdData.java */
/* JADX INFO: loaded from: classes.dex */
public class i {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private String f32a;
    private String b;
    private String c;
    private ArrayList d = new ArrayList();

    public void a(q0 q0Var) {
        this.d.add(q0Var);
    }

    public ArrayList b() {
        return this.d;
    }

    public String c() {
        return this.b;
    }

    public String d() {
        return this.c;
    }

    public void e(String str) {
        this.b = str;
    }

    public void f(String str) {
        this.f32a = str;
    }

    public void g(String str) {
        this.c = str;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("CmdData permit = " + this.f32a);
        stringBuffer.append(", mode = " + this.b);
        stringBuffer.append(", url = " + this.c);
        for (int i = 0; i < this.d.size(); i++) {
            stringBuffer.append(", cmdLineDataList[" + i + "] = " + this.d.get(i));
        }
        return stringBuffer.toString();
    }
}
