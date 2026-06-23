package com.felicanetworks.mfc.felica.access_control;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import com.felicanetworks.mfc.util.LogMgr;
import com.felicanetworks.mfw.i.fbl.Property;
import com.felicanetworks.mfw.i.util.CommonConfig;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

/* JADX INFO: loaded from: classes.dex */
public final class AccessConfig {
    private static final int ANDROID_OS_VERSION_LENGTH = 30;
    private static final String BROWSER_PKG_KEY = "00000003";
    private static final String CHIP_FUNCTION_IDENTIFICATION_INFO_KEY = "00000016";
    private static final String CHIP_TYPE_GP = "1";
    private static final String CHIP_TYPE_KEY = "00000013";
    private static final String FELICA_READER_WRITER_SUPPORT_KEY = "00000010";
    private static final String GET_INSTANCE_STATUS_SUPPORTED = "0001";
    private static final String GP_ESE_READER_NAME_KEY = "00000011";
    private static final int MODEL_NAME_LENGTH = 30;
    private static final String RW_UNSUPPORTED = "1";
    private static final String SYSTEM_FILE_CAREER_IDENTIFY_CODE_KEY = "00000002";
    private static final String SYSTEM_FILE_CHIP_ISSUER_ID_KEY = "00000001";
    private static final int SYSTEM_FILE_CHIP_ISSUER_ID_LENGTH = 6;

    private AccessConfig() {
    }

    public static String getUserAgent(Context context) {
        return createUserAgent(context, getChipIssuerId());
    }

    public static boolean isValidContainerIssueInfo(byte[] bArr) {
        String careerIdentifyCode = getCareerIdentifyCode();
        if (careerIdentifyCode != null && careerIdentifyCode.length() >= 6) {
            byte[] bArr2 = {(byte) Integer.parseInt(careerIdentifyCode.substring(0, 2), 16), (byte) Integer.parseInt(careerIdentifyCode.substring(2, 4), 16), (byte) Integer.parseInt(careerIdentifyCode.substring(4, 6), 16)};
            if (bArr.length == 16 && bArr2[0] == bArr[2] && bArr2[1] == bArr[3] && bArr2[2] == bArr[4]) {
                return true;
            }
        }
        return false;
    }

    public static String getDefaultBrowserPackageName() {
        try {
            return CommonConfig.getInstance().getValue(BROWSER_PKG_KEY);
        } catch (FileNotFoundException | IOException | ParseException unused) {
            return null;
        }
    }

    public static String getFelicaReaderWriterSupport() {
        try {
            return CommonConfig.getInstance().getValue(FELICA_READER_WRITER_SUPPORT_KEY);
        } catch (FileNotFoundException | IOException | ParseException unused) {
            return null;
        }
    }

    public static boolean isFelicaReaderWriterSupported() {
        if (getFelicaReaderWriterSupport() != null) {
            return !getFelicaReaderWriterSupport().equals("1");
        }
        return true;
    }

    public static String getEseReaderName() {
        try {
            return CommonConfig.getInstance().getValue(GP_ESE_READER_NAME_KEY);
        } catch (FileNotFoundException | IOException | ParseException unused) {
            return null;
        }
    }

    public static boolean isGpDevice() {
        if (getChipType() != null) {
            return getChipType().equals("1");
        }
        return false;
    }

    private static String getChipType() {
        try {
            return CommonConfig.getInstance().getValue(CHIP_TYPE_KEY);
        } catch (FileNotFoundException | IOException | ParseException unused) {
            return null;
        }
    }

    private static String getChipFunctionIdentificationInfo() {
        try {
            return CommonConfig.getInstance().getValue(CHIP_FUNCTION_IDENTIFICATION_INFO_KEY);
        } catch (FileNotFoundException | IOException | ParseException unused) {
            return null;
        }
    }

    public static boolean isGetInstanceStatusCommandSupported() {
        if (getChipFunctionIdentificationInfo() != null) {
            return "0001".equals(getChipFunctionIdentificationInfo());
        }
        return false;
    }

    static String getCareerIdentifyCode() {
        try {
            return CommonConfig.getInstance().getValue(SYSTEM_FILE_CAREER_IDENTIFY_CODE_KEY);
        } catch (FileNotFoundException | IOException | ParseException unused) {
            return null;
        }
    }

    static String getChipIssuerId() {
        try {
            return CommonConfig.getInstance().getValue(SYSTEM_FILE_CHIP_ISSUER_ID_KEY);
        } catch (FileNotFoundException | IOException | ParseException unused) {
            return null;
        }
    }

    static String createUserAgent(Context context, String str) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String strSubstring = Build.VERSION.RELEASE;
            if (strSubstring.length() > 30) {
                strSubstring = strSubstring.substring(0, 30);
            }
            if (str == null) {
                str = Property.URL_VERUP_SITE;
            } else if (str.length() > 6) {
                str = str.substring(0, 6);
            }
            String strSubstring2 = Build.MODEL;
            if (strSubstring2.length() > 30) {
                strSubstring2 = strSubstring2.substring(0, 30);
            }
            return String.format("MobileFeliCaClient/%s (Android %s; %s; %s)", packageInfo.versionName, strSubstring, str, strSubstring2);
        } catch (PackageManager.NameNotFoundException unused) {
            LogMgr.log(1, "%s  ERROR:There was an unnecessary token.", "801");
            return null;
        }
    }
}
