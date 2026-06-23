package com.felicanetworks.mfm.main.presenter.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.tis.TapInteractionConst;
import com.google.android.gms.fido.fido2.api.common.DevicePublicKeyStringDef;
import java.io.IOException;

/* JADX INFO: loaded from: classes3.dex */
public class MfiTapPreference {
    private static final String PREF_NAME = "mfi_ti_setting";
    private static final String STOP_CHIP_ACCESS_FLAG = "ti_stop_chip_access";
    private static final boolean STOP_CHIP_ACCESS_FLAG_DEFAULT = false;
    private static final String TAP_INTERACTION_FLAG1 = "ti_notification_display";
    private static final String TAP_INTERACTION_FLAG2 = "ti_notification_display_mode";
    private static final boolean TAP_INTERACTION_FLAG_DEFAULT = true;
    private static final MfiTapPreference _instance = new MfiTapPreference();

    public enum Flag2Value {
        TI_NOTIFICATION_DISPLAY_MODE_CARD("card"),
        TI_NOTIFICATION_DISPLAY_MODE_NOTICE(TapInteractionConst.NOTIFICATION_DISPLAY_MODE_DEFAULT),
        TI_NOTIFICATION_DISPLAY_MODE_NONE(DevicePublicKeyStringDef.NONE);

        private final String text;

        Flag2Value(final String text) {
            this.text = text;
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
        if (Settings.DeviceType.FAVER == Settings.getDeviceType()) {
            return sharedPreferences.getBoolean("ti_notification_display", false);
        }
        return sharedPreferences.getBoolean("ti_notification_display", true);
    }

    public Flag2Value loadTapInteractionFlag2(Context context) {
        String string = getSharedPreferences(context).getString("ti_notification_display_mode", null);
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

    public boolean loadStopChipAccessFlag(Context context) {
        return getSharedPreferences(context).getBoolean("ti_stop_chip_access", false);
    }

    public void saveTapInteractionFlag1(Context context, boolean flg) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putBoolean("ti_notification_display", flg);
        check(editorEdit.commit());
    }

    public void saveTapInteractionFlag2(Context context, Flag2Value value) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putString("ti_notification_display_mode", value.getString());
        check(editorEdit.commit());
    }

    public void saveStopChipAccessFlag(Context context, boolean flg) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putBoolean("ti_stop_chip_access", flg);
        check(editorEdit.commit());
    }

    public void removeTapInteractionFlag2(Context context) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.remove("ti_notification_display_mode");
        check(editorEdit.commit());
    }

    private SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("mfi_ti_setting", 0);
    }

    private void check(boolean ret) {
        if (ret) {
            return;
        }
        LogUtil.warning(new IOException("commit failed."));
    }
}
