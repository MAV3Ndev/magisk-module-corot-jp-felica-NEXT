package com.felicanetworks.mfc.mfi.felica.access_control;

import com.felicanetworks.mfc.mfi.mfw.i.util.CommonConfig;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

/* JADX INFO: loaded from: classes.dex */
public final class AccessConfig {
    private static final String BROWSER_PKG_KEY = "00000003";
    private static final String CHIP_FUNCTION_IDENTIFICATION_INFO_KEY = "00000016";
    private static final String CHIP_TYPE_KEY = "00000013";
    private static final String DEVICE_IDENTIFICATION_INFO = "00000018";
    private static final String DEVICE_TYPE_KEY = "00000012";
    private static final String FELICA_READER_WRITER_SUPPORT_KEY = "00000010";
    private static final String GP_ESE_READER_NAME_KEY = "00000011";
    private static final String MOBILE_DEVICE_INFORMATION_KEY = "02020001";
    private static final String SKU_KEY_VALUE_KEY = "00000015";
    private static final String SKU_URL_KEY = "00000014";
    private static final String SYSTEM_FILE_CAREER_IDENTIFY_CODE_KEY = "00000002";
    private static final String SYSTEM_FILE_CHIP_ISSUER_ID_KEY = "00000001";

    private AccessConfig() {
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

    static String getFelicaReaderWriterSupported() {
        try {
            return CommonConfig.getInstance().getValue(FELICA_READER_WRITER_SUPPORT_KEY);
        } catch (FileNotFoundException | IOException | ParseException unused) {
            return null;
        }
    }

    static String getGpEseReaderName() {
        try {
            return CommonConfig.getInstance().getValue(GP_ESE_READER_NAME_KEY);
        } catch (FileNotFoundException | IOException | ParseException unused) {
            return null;
        }
    }

    static String getDeviceType() {
        try {
            return CommonConfig.getInstance().getValue(DEVICE_TYPE_KEY);
        } catch (FileNotFoundException | IOException | ParseException unused) {
            return null;
        }
    }

    static String getChipType() {
        try {
            return CommonConfig.getInstance().getValue(CHIP_TYPE_KEY);
        } catch (FileNotFoundException | IOException | ParseException unused) {
            return null;
        }
    }

    static String getMobileDeviceInformation() {
        try {
            return CommonConfig.getInstance().getValue(MOBILE_DEVICE_INFORMATION_KEY);
        } catch (FileNotFoundException | IOException | ParseException unused) {
            return null;
        }
    }

    static String getSkuUrl() {
        try {
            return CommonConfig.getInstance().getValue(SKU_URL_KEY);
        } catch (FileNotFoundException | IOException | ParseException unused) {
            return null;
        }
    }

    static String getSkuKeyValue() {
        try {
            return CommonConfig.getInstance().getValue(SKU_KEY_VALUE_KEY);
        } catch (FileNotFoundException | IOException | ParseException unused) {
            return null;
        }
    }

    static String getChipFunctionIdentificationInfo() {
        try {
            return CommonConfig.getInstance().getValue(CHIP_FUNCTION_IDENTIFICATION_INFO_KEY);
        } catch (FileNotFoundException | IOException | ParseException unused) {
            return null;
        }
    }

    static String getDeviceIdentificationInfo() {
        try {
            return CommonConfig.getInstance().getValue(DEVICE_IDENTIFICATION_INFO);
        } catch (FileNotFoundException | IOException | ParseException unused) {
            return null;
        }
    }
}
