package com.felicanetworks.mfw.a.boot;

import android.app.Activity;
import com.felicanetworks.mfw.a.cmn.p0;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: MultiBootManager.java */
/* JADX INFO: loaded from: classes.dex */
public class n {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static List f135a = new ArrayList();

    private n() {
    }

    public static synchronized void a(Activity activity) {
        f135a.add(activity);
    }

    public static boolean b(Activity activity) {
        p0 p0VarA = p0.a();
        String str = p0VarA.c("execute.package.name") + p0VarA.c("execute.class.name");
        for (Activity activity2 : f135a) {
            if (activity != activity2 && str.equals(activity2.getClass().getName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean c() {
        return f135a.size() > 1;
    }

    public static synchronized void d(Activity activity) {
        f135a.remove(activity);
    }
}
