package com.felicanetworks.mfm.main.policy.service;

import android.text.TextUtils;
import com.felicanetworks.mfm.main.policy.service.CardIdPolicy;
import com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class IdPicker {

    public static abstract class ServiceIdSelector<T> {
        public abstract String sid(T item);
    }

    public static <T extends ServiceIdPolicy.ServiceIdHolder> T pickupWithSid(List<T> list, String sid) {
        if (list != null && sid != null) {
            for (T t : list) {
                if (TextUtils.equals(t.sid(), sid)) {
                    return t;
                }
            }
        }
        return null;
    }

    public static <T extends ServiceIdPolicy.ServiceIdHolder> T pickupWithSid(List<T> list, T t) {
        if (list == null || t == null) {
            return null;
        }
        return (T) pickupWithSid(list, t.sid());
    }

    public static <T> T pickupWithSid(List<T> list, String sid, ServiceIdSelector<T> selector) {
        if (list != null && selector != null) {
            for (T t : list) {
                if (TextUtils.equals(selector.sid(t), sid)) {
                    return t;
                }
            }
        }
        return null;
    }

    public static <T extends CardIdPolicy.CardIdHolder> T pickupWithCid(List<T> list, String cid) {
        if (list != null && cid != null) {
            for (T t : list) {
                if (TextUtils.equals(t.cid(), cid)) {
                    return t;
                }
            }
        }
        return null;
    }

    public static <T extends CardIdPolicy.CardIdHolder> T pickupWithCid(List<T> list, T t) {
        if (list == null || t == null) {
            return null;
        }
        return (T) pickupWithCid(list, t.cid());
    }
}
