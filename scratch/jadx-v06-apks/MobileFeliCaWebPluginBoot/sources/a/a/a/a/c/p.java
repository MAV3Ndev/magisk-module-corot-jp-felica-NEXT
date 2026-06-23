package a.a.a.a.c;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: CmdOptr.java */
/* JADX INFO: loaded from: classes.dex */
class p implements com.felicanetworks.mfw.a.cmn.g0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private ArrayList f44a;

    private p(f0 f0Var) {
    }

    @Override // com.felicanetworks.mfw.a.cmn.g0
    public void a(List list) {
        if (list == null) {
            this.f44a = null;
            return;
        }
        ArrayList arrayList = new ArrayList();
        this.f44a = arrayList;
        arrayList.addAll(list);
    }

    public ArrayList b() {
        return this.f44a;
    }
}
