package a.a.a.a.c;

import java.util.Locale;

/* JADX INFO: compiled from: RequestParamCrtr.java */
/* JADX INFO: loaded from: classes.dex */
public class k1 {
    public static String a(String str, a aVar, String str2) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ai=");
        stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b(com.felicanetworks.mfw.a.cmn.p0.a().c("application.id")));
        stringBuffer.append("&av=");
        stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b(com.felicanetworks.mfw.a.cmn.p0.a().c("application.version")));
        if (str != null) {
            stringBuffer.append("&idm=");
            stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b(str));
        }
        stringBuffer.append("&ic=");
        stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b(com.felicanetworks.mfw.a.cmn.k.e().c()));
        String language = Locale.getDefault().getLanguage();
        if (language != null && !"".equals(language)) {
            stringBuffer.append("&lc=");
            stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b(language));
        }
        String country = Locale.getDefault().getCountry();
        if (country != null && !"".equals(country)) {
            stringBuffer.append("&cc=");
            stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b(country));
        }
        if (aVar.e() == 1 && aVar.d() != null) {
            stringBuffer.append("&cmd_url=");
            stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b(aVar.d()));
        } else if (aVar.e() == 2 && aVar.f() != null) {
            stringBuffer.append("&scm_url=");
            stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b(aVar.f()));
        }
        stringBuffer.append("&wt=");
        stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b(str2));
        return stringBuffer.toString();
    }
}
