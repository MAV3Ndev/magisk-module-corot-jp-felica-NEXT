package com.google.android.gms.common.data;

import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes2.dex */
public final class FreezableUtils {
    public static <T, E extends Freezable<T>> ArrayList<T> freeze(ArrayList<E> arrayList) {
        MfcExpert.AnonymousClass12 anonymousClass12 = (ArrayList<T>) new ArrayList(arrayList.size());
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            anonymousClass12.add(arrayList.get(i).freeze());
        }
        return anonymousClass12;
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freeze(E[] eArr) {
        MfcExpert.AnonymousClass12 anonymousClass12 = (ArrayList<T>) new ArrayList(eArr.length);
        for (E e : eArr) {
            anonymousClass12.add(e.freeze());
        }
        return anonymousClass12;
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freezeIterable(Iterable<E> iterable) {
        MfcExpert.AnonymousClass12 anonymousClass12 = (ArrayList<T>) new ArrayList();
        Iterator<E> it = iterable.iterator();
        while (it.hasNext()) {
            anonymousClass12.add(it.next().freeze());
        }
        return anonymousClass12;
    }
}
