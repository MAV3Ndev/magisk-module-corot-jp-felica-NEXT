package com.google.android.gms.common.data;

import com.felicanetworks.mfm.mfcutil.mfc.mfi.MfiClient;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.4.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class FreezableUtils {
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static <T, E extends Freezable<T>> ArrayList<T> freeze(ArrayList<E> arrayList) {
        MfiClient.AnonymousClass10 anonymousClass10 = (ArrayList<T>) new ArrayList(arrayList.size());
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            anonymousClass10.add(arrayList.get(i).freeze());
        }
        return anonymousClass10;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static <T, E extends Freezable<T>> ArrayList<T> freezeIterable(Iterable<E> iterable) {
        MfiClient.AnonymousClass10 anonymousClass10 = (ArrayList<T>) new ArrayList();
        Iterator<E> it = iterable.iterator();
        while (it.hasNext()) {
            anonymousClass10.add(it.next().freeze());
        }
        return anonymousClass10;
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freeze(E[] eArr) {
        MfiClient.AnonymousClass10 anonymousClass10 = (ArrayList<T>) new ArrayList(eArr.length);
        for (E e : eArr) {
            anonymousClass10.add(e.freeze());
        }
        return anonymousClass10;
    }
}
