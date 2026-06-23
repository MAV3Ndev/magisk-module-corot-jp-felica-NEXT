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

/* JADX INFO: loaded from: classes.dex */
public class Settings {
    private static String icCode;
    private static String idm;

    public static void initIdm(String str) {
        idm = str;
    }

    public static String idm() {
        return idm;
    }

    public static void initIcCode(String str) {
        icCode = str;
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

        /* synthetic */ DeviceType(Integer num, AnonymousClass1 anonymousClass1) {
            this(num);
        }

        DeviceType(Integer num) {
            this.value = num;
        }

        static DeviceType resolve(Integer num) {
            for (DeviceType deviceType : values()) {
                if (Objects.equals(deviceType.value, num)) {
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

        /* synthetic */ FelicaChipVersion(Integer num, AnonymousClass1 anonymousClass1) {
            this(num);
        }

        FelicaChipVersion(Integer num) {
            this.value = num;
        }

        static FelicaChipVersion resolve(Integer num) {
            for (FelicaChipVersion felicaChipVersion : values()) {
                if (Objects.equals(felicaChipVersion.value, num)) {
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
        int i;
        FelicaChipVersion felicaChipVersionResolve = FelicaChipVersion.resolve((Integer) Sg.getValue(Sg.Key.SETTING_FELICA_VERSION));
        DeviceType deviceType = getDeviceType();
        if (felicaChipVersionResolve == null || deviceType == null) {
            return null;
        }
        int i2 = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$policy$device$Settings$FelicaChipVersion[felicaChipVersionResolve.ordinal()];
        if (i2 == 1) {
            int i3 = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$policy$device$Settings$DeviceType[deviceType.ordinal()];
            if (i3 == 1) {
                return FelicaChipVersion.FAVER_3_0;
            }
            if (i3 == 3) {
                return FelicaChipVersion.FAVER_GP_4_0;
            }
        } else if (i2 == 2) {
            int i4 = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$policy$device$Settings$DeviceType[deviceType.ordinal()];
            if (i4 == 2 || i4 == 3) {
                return FelicaChipVersion.FAVER_GP_4_0;
            }
        } else if (i2 == 3 && ((i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$policy$device$Settings$DeviceType[deviceType.ordinal()]) == 2 || i == 3)) {
            return FelicaChipVersion.FAVER_GP_4_1;
        }
        return null;
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.policy.device.Settings$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$policy$device$Settings$DeviceType;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$policy$device$Settings$FelicaChipVersion;

        static {
            int[] iArr = new int[FelicaChipVersion.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$policy$device$Settings$FelicaChipVersion = iArr;
            try {
                iArr[FelicaChipVersion.FAVER_3_0.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$device$Settings$FelicaChipVersion[FelicaChipVersion.FAVER_GP_4_0.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$device$Settings$FelicaChipVersion[FelicaChipVersion.FAVER_GP_4_1.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[DeviceType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$policy$device$Settings$DeviceType = iArr2;
            try {
                iArr2[DeviceType.FAVER.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$device$Settings$DeviceType[DeviceType.GP.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$device$Settings$DeviceType[DeviceType.PIXEL.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public static boolean getTIIncompatibleFlag() {
        Integer num = (Integer) Sg.getValue(Sg.Key.SETTING_TAP_INTERACTION_INCOMPATIBLE_FLG);
        return num != null && 1 == num.intValue();
    }

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
            } catch (Exception e) {
                LogUtil.warning(e);
                if (0 != 0) {
                }
            }
            return zIsSecureNfcEnabled;
        } finally {
            if (0 != 0) {
                cursorQuery.close();
            }
        }
    }

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
        Cursor cursor = null;
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
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }
}
