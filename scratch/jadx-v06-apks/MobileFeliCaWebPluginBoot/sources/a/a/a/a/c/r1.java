package a.a.a.a.c;

import java.util.ArrayList;
import java.util.Locale;

/* JADX INFO: compiled from: RespCrtr.java */
/* JADX INFO: loaded from: classes.dex */
public class r1 {
    public static String a(ArrayList arrayList) {
        String str = "pftype=" + com.felicanetworks.mfw.a.cmn.k0.b(com.felicanetworks.mfw.a.cmn.p0.a().c("platform.type")) + "&issuer=" + com.felicanetworks.mfw.a.cmn.k0.b(com.felicanetworks.mfw.a.cmn.k.e().c()) + "&appver=" + com.felicanetworks.mfw.a.cmn.k0.b(com.felicanetworks.mfw.a.cmn.p0.a().c("application.version")) + "&langcode=" + com.felicanetworks.mfw.a.cmn.k0.b(Locale.getDefault().getLanguage()) + "&councode=" + com.felicanetworks.mfw.a.cmn.k0.b(Locale.getDefault().getCountry());
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        if (arrayList == null) {
            return stringBuffer.toString();
        }
        for (int i = 0; i < arrayList.size(); i++) {
            com.felicanetworks.mfw.a.cmn.i0 i0Var = (com.felicanetworks.mfw.a.cmn.i0) arrayList.get(i);
            stringBuffer.append("&");
            stringBuffer.append(i0Var.a());
            stringBuffer.append("=");
            stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b(i0Var.b()));
        }
        return stringBuffer.toString();
    }
}
