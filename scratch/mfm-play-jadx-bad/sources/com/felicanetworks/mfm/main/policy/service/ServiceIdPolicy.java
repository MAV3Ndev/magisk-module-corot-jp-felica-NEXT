package com.felicanetworks.mfm.main.policy.service;

import android.text.TextUtils;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class ServiceIdPolicy {
    private static final List<String> ALLOWED_SERVICE_ID_PREFIXES = Collections.singletonList((String) Sg.getValue(Sg.Key.SETTING_PREFIX_FELICA_SERVICE));

    public interface ServiceHolder {
        String getPriority();

        String getServiceId();
    }

    public interface ServiceIdHolder {
        String sid();
    }

    public static abstract class ServiceIdSelector<T> {
        public abstract String sid(T item);
    }

    public static <T extends ServiceIdHolder> boolean isAllowed(T obj) {
        if (obj == null) {
            return false;
        }
        return isAllowed(obj.sid());
    }

    public static boolean isAllowed(String sid) {
        if (TextUtils.isEmpty(sid)) {
            return false;
        }
        Iterator<String> it = ALLOWED_SERVICE_ID_PREFIXES.iterator();
        while (it.hasNext()) {
            if (sid.startsWith(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static <T extends ServiceIdHolder> List<T> filter(List<T> list) {
        if (list == null) {
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList();
        for (T t : list) {
            if (isAllowed(t)) {
                arrayList.add(t);
            }
        }
        return arrayList;
    }

    public static <T> List<T> filter(T[] array, ServiceIdSelector<T> selector) {
        if (array == null || selector == null) {
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList();
        for (T t : array) {
            if (isAllowed(selector.sid(t))) {
                arrayList.add(t);
            }
        }
        return arrayList;
    }

    public static <T extends ServiceIdHolder> boolean equals(T t1, T t2) {
        if (t1 == null || t2 == null) {
            return false;
        }
        return TextUtils.equals(t1.sid(), t2.sid());
    }

    public static <T extends ServiceIdHolder> boolean contains(List<T> ts, T t) {
        if (ts != null && t != null) {
            Iterator<T> it = ts.iterator();
            while (it.hasNext()) {
                if (equals(it.next(), t)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static <T extends ServiceHolder> boolean contains(List<T> ts, String t) {
        if (ts == null) {
            return false;
        }
        Iterator<T> it = ts.iterator();
        while (it.hasNext()) {
            if (TextUtils.equals(it.next().getServiceId(), t)) {
                return true;
            }
        }
        return false;
    }

    public static <T extends ServiceHolder> void sort(List<T> ts) {
        if (ts == null) {
            return;
        }
        Collections.sort(ts, new Comparator<T>() { // from class: com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy.1
            /* JADX DEBUG: Method merged with bridge method: compare(Ljava/lang/Object;Ljava/lang/Object;)I */
            /* JADX WARN: Incorrect types in method signature: (TT;TT;)I */
            @Override // java.util.Comparator
            public int compare(ServiceHolder o1, ServiceHolder o2) {
                try {
                    int iCompareTo = o1.getPriority().compareTo(o2.getPriority());
                    return iCompareTo == 0 ? o1.getServiceId().compareTo(o2.getServiceId()) : iCompareTo;
                } catch (Exception unused) {
                    return 0;
                }
            }
        });
    }
}
