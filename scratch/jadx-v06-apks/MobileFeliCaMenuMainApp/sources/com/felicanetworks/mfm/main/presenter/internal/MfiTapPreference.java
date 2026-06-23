package com.felicanetworks.mfm.main.presenter.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class MfiTapPreference {
    private static final String PREF_NAME = "mfi_ti_setting";
    private static final String TAP_INTERACTION_FLAG1 = "ti_notification_display";
    private static final String TAP_INTERACTION_FLAG2 = "ti_notification_display_mode";
    private static final boolean TAP_INTERACTION_FLAG_DEFAULT = false;
    private static final MfiTapPreference _instance = new MfiTapPreference();

    public enum Flag2Value {
        TI_NOTIFICATION_DISPLAY_MODE_CARD("card"),
        TI_NOTIFICATION_DISPLAY_MODE_NOTICE("notice"),
        TI_NOTIFICATION_DISPLAY_MODE_NONE("none");

        private final String text;

        Flag2Value(String str) {
            this.text = str;
        }

        public String getString() {
            return this.text;
        }
    }

    public static MfiTapPreference getInstance() {
        return _instance;
    }

    private MfiTapPreference() {
    }

    public boolean loadTapInteractionFlag1(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        if (Settings.DeviceType.GP == Settings.getDeviceType()) {
            return sharedPreferences.getBoolean(TAP_INTERACTION_FLAG1, true);
        }
        return sharedPreferences.getBoolean(TAP_INTERACTION_FLAG1, false);
    }

    public Flag2Value loadTapInteractionFlag2(Context context) {
        String string = getSharedPreferences(context).getString(TAP_INTERACTION_FLAG2, null);
        if (string == null) {
            return null;
        }
        for (Flag2Value flag2Value : Flag2Value.values()) {
            if (TextUtils.equals(flag2Value.getString(), string)) {
                return flag2Value;
            }
        }
        return null;
    }

    public void saveTapInteractionFlag1(Context context, boolean z) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putBoolean(TAP_INTERACTION_FLAG1, z);
        check(editorEdit.commit());
    }

    public void saveTapInteractionFlag2(Context context, Flag2Value flag2Value) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putString(TAP_INTERACTION_FLAG2, flag2Value.getString());
        check(editorEdit.commit());
    }

    public void removeTapInteractionFlag2(Context context) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.remove(TAP_INTERACTION_FLAG2);
        check(editorEdit.commit());
    }

    private SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, 0);
    }

    private void check(boolean z) {
        if (z) {
            return;
        }
        LogUtil.warning(new IOException("commit failed."));
    }
}
