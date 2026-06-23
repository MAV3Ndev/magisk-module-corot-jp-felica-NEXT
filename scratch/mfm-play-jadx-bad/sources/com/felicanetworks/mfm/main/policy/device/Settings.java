package com.felicanetworks.mfm.main.policy.device;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.util.Objects;

/* JADX INFO: loaded from: classes3.dex */
public class Settings {
    private static String icCode;
    private static String idm;

    public static void initIdm(String value) {
        idm = value;
    }

    public static String idm() {
        return idm;
    }

    public static void initIcCode(String value) {
        icCode = value;
    }

    public static String icCode() {
        return icCode;
    }

    public enum DeviceType {
        FAVER(null) { // from class: com.felicanetworks.mfm.main.policy.device.Settings.DeviceType.1
            @Override // java.lang.Enum
            public String toString() {
                return "Faver";
            }
        },
        GP(1) { // from class: com.felicanetworks.mfm.main.policy.device.Settings.DeviceType.2
            @Override // java.lang.Enum
            public String toString() {
                return "GP";
            }
        },
        PIXEL(2) { // from class: com.felicanetworks.mfm.main.policy.device.Settings.DeviceType.3
            @Override // java.lang.Enum
            public String toString() {
                return "Pixel";
            }
        };

        private final Integer value;

        DeviceType(Integer value) {
            this.value = value;
        }

        static DeviceType resolve(Integer value) {
            for (DeviceType deviceType : values()) {
                if (Objects.equals(deviceType.value, value)) {
                    return deviceType;
                }
            }
            return null;
        }
    }

    public enum FelicaChipVersion {
        FAVER_3_0(null) { // from class: com.felicanetworks.mfm.main.policy.device.Settings.FelicaChipVersion.1
            @Override // java.lang.Enum
            public String toString() {
                return "Faver2/3";
            }
        },
        FAVER_GP_4_0(0) { // from class: com.felicanetworks.mfm.main.policy.device.Settings.FelicaChipVersion.2
            @Override // java.lang.Enum
            public String toString() {
                return "FaverGP(4.0)";
            }
        },
        FAVER_GP_4_1(1) { // from class: com.felicanetworks.mfm.main.policy.device.Settings.FelicaChipVersion.3
            @Override // java.lang.Enum
            public String toString() {
                return "FaverGP(4.1)";
            }
        };

        private final Integer value;

        FelicaChipVersion(Integer value) {
            this.value = value;
        }

        static FelicaChipVersion resolve(Integer value) {
            for (FelicaChipVersion felicaChipVersion : values()) {
                if (Objects.equals(felicaChipVersion.value, value)) {
                    return felicaChipVersion;
                }
            }
            return null;
        }
    }

    public static DeviceType getDeviceType() {
        return DeviceType.resolve((Integer) Sg.getValue(Sg.Key.SETTING_DEVICE_TYPE));
    }

    public static FelicaChipVersion getFelicaChipVersion() {
        int iOrdinal;
        FelicaChipVersion felicaChipVersionResolve = FelicaChipVersion.resolve((Integer) Sg.getValue(Sg.Key.SETTING_FELICA_VERSION));
        DeviceType deviceType = getDeviceType();
        if (felicaChipVersionResolve == null || deviceType == null) {
            return null;
        }
        int iOrdinal2 = felicaChipVersionResolve.ordinal();
        if (iOrdinal2 == 0) {
            int iOrdinal3 = deviceType.ordinal();
            if (iOrdinal3 == 0) {
                return FelicaChipVersion.FAVER_3_0;
            }
            if (iOrdinal3 == 2) {
                return FelicaChipVersion.FAVER_GP_4_0;
            }
        } else if (iOrdinal2 == 1) {
            int iOrdinal4 = deviceType.ordinal();
            if (iOrdinal4 == 1 || iOrdinal4 == 2) {
                return FelicaChipVersion.FAVER_GP_4_0;
            }
        } else if (iOrdinal2 == 2 && ((iOrdinal = deviceType.ordinal()) == 1 || iOrdinal == 2)) {
            return FelicaChipVersion.FAVER_GP_4_1;
        }
        return null;
    }

    public static boolean getTIIncompatibleFlag() {
        Integer num = (Integer) Sg.getValue(Sg.Key.SETTING_TAP_INTERACTION_INCOMPATIBLE_FLG);
        return num != null && 1 == num.intValue();
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    public static boolean isScreenLock(Context context) {
        boolean zIsSecureNfcEnabled = false;
        Cursor cursorQuery = null;
        try {
            try {
                if (Build.VERSION.SDK_INT >= 29) {
                    zIsSecureNfcEnabled = NfcAdapter.getDefaultAdapter(context).isSecureNfcEnabled();
                } else {
                    cursorQuery = context.getContentResolver().query(Uri.parse("content://com.felicanetworks.lockapp.provider/lockstatus"), null, null, null, null);
                    if (cursorQuery != null) {
                        cursorQuery.moveToNext();
                        if (Integer.parseInt(cursorQuery.getString(cursorQuery.getColumnIndex("type"))) == 0 && 1 == Integer.parseInt(cursorQuery.getString(cursorQuery.getColumnIndex(NotificationCompat.CATEGORY_STATUS)))) {
                            zIsSecureNfcEnabled = true;
                        }
                    }
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return zIsSecureNfcEnabled;
            } catch (Exception e) {
                LogUtil.warning(e);
                if (0 != 0) {
                    cursorQuery.close();
                }
                return false;
            }
        } finally {
        }
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    public boolean isCheckInbound(Context context) {
        int i;
        Integer num = (Integer) Sg.getValue(Sg.Key.SETTING_SKU_VALUE);
        if (num != null) {
            try {
                return 1 != num.intValue();
            } catch (NumberFormatException unused) {
            }
        }
        String str = (String) Sg.getValue(Sg.Key.SETTING_SKU_URL_VALUE);
        String str2 = (String) Sg.getValue(Sg.Key.SETTING_SKU_KEY_VALUE);
        if (str == null && str2 == null) {
            return false;
        }
        try {
            try {
                Cursor cursorQuery = context.getContentResolver().query(Uri.parse(str), null, null, null, null);
                cursorQuery.moveToFirst();
                int columnIndex = cursorQuery.getColumnIndex(str2);
                int type = cursorQuery.getType(columnIndex);
                if (1 == type) {
                    i = cursorQuery.getInt(columnIndex);
                } else {
                    i = 3 == type ? Integer.parseInt(cursorQuery.getString(columnIndex)) : 0;
                }
                if (i == 1) {
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return false;
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return true;
            } catch (Exception unused2) {
                throw new IllegalStateException();
            }
        } finally {
        }
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0071 A[PHI: r3
  0x0071: PHI (r3v1 android.database.Cursor) = (r3v0 android.database.Cursor), (r3v2 android.database.Cursor) binds: [B:30:0x006f, B:23:0x0064] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String getInbound(Context context) {
        int columnIndex;
        int type;
        Integer num = (Integer) Sg.getValue(Sg.Key.SETTING_SKU_VALUE);
        if (num != null) {
            try {
                return Integer.toString(num.intValue());
            } catch (NumberFormatException unused) {
            }
        }
        String str = (String) Sg.getValue(Sg.Key.SETTING_SKU_URL_VALUE);
        String str2 = (String) Sg.getValue(Sg.Key.SETTING_SKU_KEY_VALUE);
        if (str == null && str2 == null) {
            return "";
        }
        Cursor cursorQuery = null;
        try {
            cursorQuery = context.getContentResolver().query(Uri.parse(str), null, null, null, null);
            cursorQuery.moveToFirst();
            columnIndex = cursorQuery.getColumnIndex(str2);
            type = cursorQuery.getType(columnIndex);
        } catch (Exception unused2) {
            if (0 != 0) {
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursorQuery.close();
                throw th;
            }
            throw th;
        }
        if (1 == type) {
            String string = Integer.toString(cursorQuery.getInt(columnIndex));
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return string;
        }
        if (3 != type) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return "";
        }
        String string2 = cursorQuery.getString(columnIndex);
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return string2;
    }
}
