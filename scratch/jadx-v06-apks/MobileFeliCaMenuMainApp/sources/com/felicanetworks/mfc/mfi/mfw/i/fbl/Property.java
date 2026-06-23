package com.felicanetworks.mfc.mfi.mfw.i.fbl;

/* JADX INFO: loaded from: classes.dex */
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

    public static synchronized void setCareerIdentifyCode(String str) {
        sCareerIdentifyCode = str;
    }

    public static synchronized void setFelicaReaderWriterSupported(String str) {
        if (!"0".equals(str) && !"1".equals(str)) {
            sFelicaReaderWriterSupported = "0";
        } else {
            sFelicaReaderWriterSupported = str;
        }
    }

    public static synchronized void setGpEseReaderName(String str) {
        sGpEseReaderName = str;
    }

    public static synchronized void setDeviceType(String str) {
        if (str == null) {
            sDeviceType = "1";
        } else {
            sDeviceType = str;
        }
    }

    public static synchronized boolean setChipType(String str) {
        if (str == null) {
            sChipType = "0";
        } else {
            if (!"0".equals(str) && !"1".equals(str)) {
                return false;
            }
            sChipType = str;
        }
        return true;
    }

    public static synchronized void setMobileDeviceInformation(String str) {
        sMobileDeviceInformation = str;
    }

    public static synchronized void setSkuUrl(String str) {
        sSkuUrl = str;
    }

    public static synchronized void setSkuKeyValue(String str) {
        sSkuKeyValue = str;
    }

    public static synchronized void setChipFunctionIdentificationInfoInfo(String str) {
        sChipFunctionIdentificationInfo = str;
    }

    public static synchronized void setDeviceIdentificationInfo(String str) {
        sDeviceIdentificationInfo = str;
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
        return isChipGP() ? "10" : "00";
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
}
