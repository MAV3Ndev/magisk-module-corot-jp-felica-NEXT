package com.felicanetworks.mfm.main.policy.service;

import android.text.TextUtils;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ServiceIdPolicy {
    private static final List<String> ALLOWED_SERVICE_ID_PREFIXES = Collections.singletonList((String) Sg.getValue(Sg.Key.SETTING_PREFIX_FELICA_SERVICE));

    public interface ServiceIdHolder {
        String sid();
    }

    public static abstract class ServiceIdSelector<T> {
        public abstract String sid(T t);
    }

    public static <T extends ServiceIdHolder> boolean isAllowed(T t) {
        if (t == null) {
            return false;
        }
        return isAllowed(t.sid());
    }

    public static boolean isAllowed(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Iterator<String> it = ALLOWED_SERVICE_ID_PREFIXES.iterator();
        while (it.hasNext()) {
            if (str.startsWith(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static <T extends ServiceIdHolder> List<T> filter(List<T> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (T t : list) {
            if (isAllowed(t)) {
                arrayList.add(t);
            }
        }
        return arrayList;
    }

    public static <T> List<T> filter(T[] tArr, ServiceIdSelector<T> serviceIdSelector) {
        if (tArr == null || serviceIdSelector == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (T t : tArr) {
            if (isAllowed(serviceIdSelector.sid(t))) {
                arrayList.add(t);
            }
        }
        return arrayList;
    }

    public static <T extends ServiceIdHolder> boolean equals(T t, T t2) {
        if (t == null || t2 == null) {
            return false;
        }
        return TextUtils.equals(t.sid(), t2.sid());
    }

    public static <T extends ServiceIdHolder> boolean contains(List<T> list, T t) {
        if (list != null && t != null) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                if (equals(it.next(), t)) {
                    return true;
                }
            }
        }
        return false;
    }
}
