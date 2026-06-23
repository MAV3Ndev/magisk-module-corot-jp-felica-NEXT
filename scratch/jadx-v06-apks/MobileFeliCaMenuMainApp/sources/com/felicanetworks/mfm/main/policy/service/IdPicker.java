package com.felicanetworks.mfm.main.policy.service;

import android.text.TextUtils;
import com.felicanetworks.mfm.main.policy.service.CardIdPolicy;
import com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class IdPicker {
    public static <T extends ServiceIdPolicy.ServiceIdHolder> T pickupWithSid(List<T> list, String str) {
        if (list != null && str != null) {
            for (T t : list) {
                if (TextUtils.equals(t.sid(), str)) {
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

    public static <T extends CardIdPolicy.CardIdHolder> T pickupWithCid(List<T> list, String str) {
        if (list != null && str != null) {
            for (T t : list) {
                if (TextUtils.equals(t.cid(), str)) {
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
