package com.felicanetworks.mfc.mfi.mfw.i.fbl;

import androidx.work.WorkInfo;
import com.felicanetworks.mfc.mfi.util.StringUtil;

/* JADX INFO: loaded from: classes3.dex */
public class Property {
    private static final String CHIP_TYPE_FAVER_OR_GP1 = "0";
    private static final String CHIP_TYPE_GP = "1";
    private static final String DEVICE_TYPE_PHONE = "1";
    private static final String FELICA_READER_WRITER_NOT_SUPPORTED = "1";
    private static final String FELICA_READER_WRITER_SUPPORTED = "0";
    private static final String GET_INSTANCE_STATUS_SUPPORTED = "0001";
    public static final String PLATFORM_TYPE = "02";
    private static final String SE_TYPE_FAVER = "00";
    private static final String SE_TYPE_GP = "10";
    private static final int TOP_LEVEL_SD_AID_BYTE_LENGTH_MAX = 16;
    private static final int TOP_LEVEL_SD_TYPE_DEFAULT = -1;
    private static String sCareerIdentifyCode;
    private static String sChipFunctionIdentificationInfo;
    public static String sChipIssuerId;
    private static String sChipType;
    private static String sDeviceIdentificationInfo;
    private static String sDeviceType;
    private static String sFelicaReaderWriterSupported;
    private static String sGpEseReaderName;
    private static String sMobileDeviceInformation;
    private static String sSkuKeyValue;
    private static String sSkuUrl;
    private static final int TOP_LEVEL_SD_TYPE_MAX = Math.abs(WorkInfo.STOP_REASON_FOREGROUND_SERVICE_TIMEOUT) * 2;
    private static int sTopLevelSdType = -1;
    private static byte[] sTopLevelSdAid = null;

    public static synchronized void setCareerIdentifyCode(String careerIdentifyCode) {
        sCareerIdentifyCode = careerIdentifyCode;
    }

    public static synchronized void setFelicaReaderWriterSupported(String felicaReaderWriterSupported) {
        if (!"0".equals(felicaReaderWriterSupported) && !"1".equals(felicaReaderWriterSupported)) {
            sFelicaReaderWriterSupported = "0";
        } else {
            sFelicaReaderWriterSupported = felicaReaderWriterSupported;
        }
    }

    public static synchronized void setGpEseReaderName(String gpEseReaderName) {
        sGpEseReaderName = gpEseReaderName;
    }

    public static synchronized void setDeviceType(String deviceType) {
        if (deviceType == null) {
            sDeviceType = "1";
        } else {
            sDeviceType = deviceType;
        }
    }

    public static synchronized boolean setChipType(String chipType) {
        if (chipType == null) {
            sChipType = "0";
        } else {
            if (!"0".equals(chipType) && !"1".equals(chipType)) {
                return false;
            }
            sChipType = chipType;
        }
        return true;
    }

    public static synchronized void setMobileDeviceInformation(String mobileDeviceInformation) {
        sMobileDeviceInformation = mobileDeviceInformation;
    }

    public static synchronized void setSkuUrl(String skuUrl) {
        sSkuUrl = skuUrl;
    }

    public static synchronized void setSkuKeyValue(String skuKeyValue) {
        sSkuKeyValue = skuKeyValue;
    }

    public static synchronized void setChipFunctionIdentificationInfoInfo(String chipFunctionIdentificationInfo) {
        sChipFunctionIdentificationInfo = chipFunctionIdentificationInfo;
    }

    public static synchronized void setDeviceIdentificationInfo(String deviceIdentificationInfo) {
        sDeviceIdentificationInfo = deviceIdentificationInfo;
    }

    public static synchronized void setTopLevelSdType(String topLevelSdType) {
        sTopLevelSdType = -1;
        try {
            int i = Integer.parseInt(topLevelSdType);
            if (i > -1 && i < TOP_LEVEL_SD_TYPE_MAX) {
                sTopLevelSdType = i;
            }
        } catch (NumberFormatException unused) {
        }
    }

    public static synchronized void setTopLevelSdAid(String topLevelSdAid) {
        sTopLevelSdAid = null;
        if (topLevelSdAid != null) {
            try {
                if (topLevelSdAid.length() <= 32) {
                    sTopLevelSdAid = StringUtil.hexToByteArray(topLevelSdAid);
                }
            } catch (NumberFormatException unused) {
            }
        }
    }

    public static String getCareerIdentifyCode() {
        return sCareerIdentifyCode;
    }

    public static boolean isFelicaReaderWriterSupported() {
        return "0".equals(sFelicaReaderWriterSupported);
    }

    public static String getGpEseReaderName() {
        return sGpEseReaderName;
    }

    public static String getDeviceType() {
        return sDeviceType;
    }

    public static String getChipType() {
        return sChipType;
    }

    public static boolean isChipGP() {
        return "1".equals(sChipType);
    }

    public static String getSeType() {
        if (isChipGP()) {
            return "10";
        }
        return "00";
    }

    public static String getMobileDeviceInformation() {
        return sMobileDeviceInformation;
    }

    public static String getSkuUrl() {
        return sSkuUrl;
    }

    public static String getSkuKeyValue() {
        return sSkuKeyValue;
    }

    public static boolean isGetInstanceStatusCommandSupported() {
        return GET_INSTANCE_STATUS_SUPPORTED.equals(sChipFunctionIdentificationInfo);
    }

    public static String getDeviceIdentificationInfo() {
        return sDeviceIdentificationInfo;
    }

    public static int getTopLevelSdType() {
        return sTopLevelSdType;
    }

    public static byte[] getTopLevelSdAid() {
        return sTopLevelSdAid;
    }

    public static boolean isValidTopLevelSdInfo() {
        return ((sTopLevelSdType == -1) || (sTopLevelSdAid == null)) ? false : true;
    }
}
