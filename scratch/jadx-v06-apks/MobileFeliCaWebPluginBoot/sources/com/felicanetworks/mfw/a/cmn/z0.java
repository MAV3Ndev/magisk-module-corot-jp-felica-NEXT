package com.felicanetworks.mfw.a.cmn;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: SpecCPSigDefinition.java */
/* JADX INFO: loaded from: classes.dex */
public final class z0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static final String[] f185a = {"com.felicanetworks.mfs"};
    private static final String[][] b = {new String[]{"BE51DBF4FEC89BD32846457B13B7300876AF5594D2874DEE026904965AE4A6CB", ""}};
    private static final List c = new ArrayList(f185a.length);

    static {
        for (String str : f185a) {
            c.add(str);
        }
    }

    public static List a(String str) {
        if (str == null || !c.contains(str)) {
            return null;
        }
        String[] strArr = b[c.indexOf(str)];
        ArrayList arrayList = new ArrayList(strArr.length);
        for (String str2 : strArr) {
            arrayList.add(str2);
        }
        return arrayList;
    }
}
