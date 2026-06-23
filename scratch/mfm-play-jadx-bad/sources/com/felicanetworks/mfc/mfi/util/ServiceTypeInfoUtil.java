package com.felicanetworks.mfc.mfi.util;

import com.felicanetworks.mfc.mfi.MfiClientConst;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class ServiceTypeInfoUtil {
    private static final String ACCESS = "access";
    private static final String[] CHECK_WORDS = {"MFPLissueCardFullMigrate", "MFCUissueCardFull", "MFCSissueCardFull", "MFPLissueCardFull", "MSCUissueCardSimple", "MSCSissueCardSimple", "MFCUissueCard", "MFCSissueCard", "MFPLissueCard", "MSCUissueCard", "MSCSissueCard", "MFCSenable", "MFCSenableFaver", "MFPLenable", "MSCSenable", "MSCSenableFaver", "MSCSdisable", "MFCUaccess", "MFCSaccess", "MFPLaccess", "MFCUdeleteFull", "MFCSdeleteFull", "MFPLdeleteFull", "MSCUdeleteSimlple", "MSCSdeleteSimlple", "MFPLenableCachedCard"};
    private static final String DELETE_FULL = "deleteFull";
    private static final String DELETE_SIMLPLE = "deleteSimlple";
    private static final String DISABLE = "disable";
    private static final String ENABLE = "enable";
    private static final String ENABLE_CACHED_CARD = "enableCachedCard";
    private static final String ENABLE_CACHED_CARD_FAVER = "enableCachedCardFaver";
    private static final String ENABLE_FAVER = "enableFaver";
    private static final String ISSUECARD = "issueCard";
    private static final String ISSUECARD_FULL = "issueCardFull";
    private static final String ISSUECARD_FULL_MIGRATE = "issueCardFullMigrate";
    private static final String ISSUECARD_SIMPLE = "issueCardSimple";
    private static final int SERVICE_TYPE_LENGTH = 8;

    public static class SupportMfi {
        private static final int CH_LENGTH = 1;
        private static final String MFI_VALUE = "M";
        private static final String NON_MFI_VALUE = "U";
        private static final int START_POS = 0;

        public static boolean isMfiSupported(String serviceType) {
            if (serviceType == null || serviceType.length() < 8) {
                return false;
            }
            return serviceType.substring(0, 1).equals("M");
        }

        public static boolean isMfiUnsupported(String serviceType) {
            if (serviceType == null || serviceType.length() < 8) {
                return false;
            }
            return serviceType.substring(0, 1).equals(NON_MFI_VALUE);
        }
    }

    public static class IfType {
        private static final int CH_LENGTH = 1;
        private static final String FULL_IF_VALUE = "F";
        private static final String NON_MFI_VALUE = "U";
        private static final String SIMPLE_IF_VALUE = "S";
        private static final int START_POS = 1;

        public static boolean isMfiUnsupported(String serviceType) {
            if (serviceType == null || serviceType.length() < 8) {
                return false;
            }
            return serviceType.substring(1, 2).equals(NON_MFI_VALUE);
        }

        public static boolean isFullIf(String serviceType) {
            if (serviceType == null || serviceType.length() < 8) {
                return false;
            }
            return serviceType.substring(1, 2).equals(FULL_IF_VALUE);
        }

        public static boolean isSimpleIf(String serviceType) {
            if (serviceType == null || serviceType.length() < 8) {
                return false;
            }
            return serviceType.substring(1, 2).equals(SIMPLE_IF_VALUE);
        }
    }

    public static class SysType {
        private static final int CH_LENGTH = 1;
        private static final String COMMON_VALUE = "C";
        private static final String PRIVATE_VALUE = "P";
        private static final int START_POS = 2;

        public static boolean isCommon(String serviceType) {
            if (serviceType == null || serviceType.length() < 8) {
                return false;
            }
            return serviceType.substring(2, 3).equals(COMMON_VALUE);
        }

        public static boolean isPrivate(String serviceType) {
            if (serviceType == null || serviceType.length() < 8) {
                return false;
            }
            return serviceType.substring(2, 3).equals(PRIVATE_VALUE);
        }
    }

    public static class MultiCardType {
        private static final int CH_LENGTH = 1;
        private static final String LOCAL_MULTIPLE_VALUE = "L";
        private static final String SERVERS_MULTIPLE_VALUE = "S";
        private static final int START_POS = 3;
        private static final String UNSUPPORTED_VALUE = "U";

        public static boolean isUnsupported(String serviceType) {
            if (serviceType == null || serviceType.length() < 8) {
                return false;
            }
            return serviceType.substring(3, 4).equals(UNSUPPORTED_VALUE);
        }

        public static boolean isServersMultiple(String serviceType) {
            if (serviceType == null || serviceType.length() < 8) {
                return false;
            }
            return serviceType.substring(3, 4).equals(SERVERS_MULTIPLE_VALUE);
        }

        public static boolean isLocalMultiple(String serviceType) {
            if (serviceType == null || serviceType.length() < 8) {
                return false;
            }
            return serviceType.substring(3, 4).equals(LOCAL_MULTIPLE_VALUE);
        }
    }

    public static class TransferType {
        private static final int CH_LENGTH = 1;
        private static final String FN_CUSTODY_VALUE = "F";
        private static final String SP_CUSTODY_VALUE = "S";
        private static final int START_POS = 4;
        private static final String UNSUPPORTED_VALUE = "U";

        public static boolean isUnsupported(String serviceType) {
            if (serviceType == null || serviceType.length() < 8) {
                return false;
            }
            return serviceType.substring(4, 5).equals(UNSUPPORTED_VALUE);
        }

        public static boolean isSpCustody(String serviceType) {
            if (serviceType == null || serviceType.length() < 8) {
                return false;
            }
            return serviceType.substring(4, 5).equals(SP_CUSTODY_VALUE);
        }

        public static boolean isFnCustody(String serviceType) {
            if (serviceType == null || serviceType.length() < 8) {
                return false;
            }
            return serviceType.substring(4, 5).equals(FN_CUSTODY_VALUE);
        }
    }

    public static class IssuableSeType {
        private static final int CH_LENGTH = 1;
        private static final String PATTERN_1_VALUE = "1";
        private static final String PATTERN_2_VALUE = "2";
        private static final String PATTERN_3_VALUE = "3";
        private static final int START_POS = 5;

        public static boolean isPattern1(String serviceType) {
            if (serviceType == null || serviceType.length() < 8) {
                return false;
            }
            return serviceType.substring(5, 6).equals("1");
        }

        public static boolean isPattern2(String serviceType) {
            if (serviceType == null || serviceType.length() < 8) {
                return false;
            }
            return serviceType.substring(5, 6).equals("2");
        }

        public static boolean isPattern3(String serviceType) {
            if (serviceType == null || serviceType.length() < 8) {
                return false;
            }
            return serviceType.substring(5, 6).equals("3");
        }
    }

    public static void issueCardSimpleServiceTypeCheck(String serviceType, String mfcVersion) throws MfiClientException {
        try {
            commonsServiceTypeCheck(serviceType, ISSUECARD_SIMPLE);
            checkCardIssuePossible(serviceType, mfcVersion);
        } catch (IllegalArgumentException unused) {
            LogMgr.log(1, "800 : linkageData is illegal.");
            throw new MfiClientException(100, MfiClientException.TYPE_ILLEGAL_LINKAGE_DATA, null);
        }
    }

    public static void issueCardsFullServiceTypeCheck(String serviceType, String mfcVersion) throws MfiClientException {
        try {
            commonsServiceTypeCheck(serviceType, ISSUECARD_FULL);
            checkCardIssuePossible(serviceType, mfcVersion);
        } catch (IllegalArgumentException unused) {
            LogMgr.log(1, "800 : linkageData is illegal.");
            throw new MfiClientException(100, MfiClientException.TYPE_ILLEGAL_LINKAGE_DATA, null);
        }
    }

    public static void issueCardsServiceTypeCheck(String serviceType, String mfcVersion) throws MfiClientException {
        try {
            commonsServiceTypeCheck(serviceType, ISSUECARD);
            checkCardIssuePossible(serviceType, mfcVersion);
        } catch (IllegalArgumentException unused) {
            LogMgr.log(1, "800 : linkageData is illegal.");
            throw new MfiClientException(100, MfiClientException.TYPE_ILLEGAL_LINKAGE_DATA, null);
        }
    }

    private static void checkCardIssuePossible(String serviceType, String mfcVersion) throws MfiClientException {
        LogMgr.log(6, "000");
        if (!Property.isChipGP()) {
            if (StringUtil.versionCompare(mfcVersion, MfiClientConst.MFC_VERSION_FAVER3) == -1) {
                if (IssuableSeType.isPattern2(serviceType) || IssuableSeType.isPattern3(serviceType)) {
                    LogMgr.log(1, "800 : serviceType is illegal.");
                    throw new MfiClientException(100, MfiClientException.TYPE_NOT_SUPPORTED, null);
                }
            } else if (IssuableSeType.isPattern3(serviceType)) {
                LogMgr.log(1, "801 : serviceType is illegal.");
                throw new MfiClientException(100, MfiClientException.TYPE_NOT_SUPPORTED, null);
            }
        }
        LogMgr.log(6, "999");
    }

    public static void issueCardsMigratedServiceTypeCheck(String serviceType, String mfcVersion) throws MfiClientException {
        try {
            commonsServiceTypeCheck(serviceType, ISSUECARD_FULL_MIGRATE);
            checkCardIssuePossible(serviceType, mfcVersion);
        } catch (IllegalArgumentException unused) {
            LogMgr.log(1, "800 : linkageData is illegal.");
            throw new MfiClientException(100, MfiClientException.TYPE_ILLEGAL_LINKAGE_DATA, null);
        }
    }

    public static void accessCardsServiceTypeCheck(String serviceType) throws MfiClientException {
        commonsServiceTypeCheck(serviceType, ACCESS);
    }

    public static void enablesServiceTypeCheck(String serviceType) throws MfiClientException {
        if (Property.isChipGP()) {
            commonsServiceTypeCheck(serviceType, ENABLE);
        } else {
            commonsServiceTypeCheck(serviceType, ENABLE_FAVER);
        }
    }

    public static void disablesServiceTypeCheck(String serviceType) throws MfiClientException {
        commonsServiceTypeCheck(serviceType, DISABLE);
    }

    public static void deleteCardsSimlpleServiceTypeCheck(String serviceType) throws MfiClientException {
        commonsServiceTypeCheck(serviceType, DELETE_SIMLPLE);
    }

    public static void deleteCardsServiceTypeCheck(String serviceType) throws MfiClientException {
        commonsServiceTypeCheck(serviceType, DELETE_FULL);
    }

    public static void enablesCachedCardServiceTypeCheck(String serviceType) throws MfiClientException {
        if (Property.isChipGP()) {
            commonsServiceTypeCheck(serviceType, ENABLE_CACHED_CARD);
        } else {
            commonsServiceTypeCheck(serviceType, ENABLE_CACHED_CARD_FAVER);
        }
    }

    private static void commonsServiceTypeCheck(String serviceType, String apiType) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(4, "000");
        if (serviceType == null) {
            LogMgr.log(1, "800 : serviceType is null.");
            throw new IllegalArgumentException();
        }
        if (serviceType.length() != 8) {
            LogMgr.log(1, "801 : serviceType length is illegal.");
            throw new IllegalArgumentException();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(serviceType.substring(0, 4));
        sb.append(apiType);
        String str = new String(sb.toString());
        boolean z = false;
        for (String str2 : CHECK_WORDS) {
            if (str.equals(str2)) {
                z = true;
            }
        }
        if (!z) {
            if (apiType.equals(ISSUECARD_FULL) || apiType.equals(ISSUECARD_SIMPLE) || apiType.equals(ISSUECARD) || apiType.equals(ISSUECARD_FULL_MIGRATE)) {
                LogMgr.log(1, "802 : serviceType is illegal.");
                throw new MfiClientException(100, MfiClientException.TYPE_NOT_SUPPORTED, null);
            }
            LogMgr.log(1, "803 : serviceType is illegal. ");
            throw new MfiClientException(102, MfiClientException.TYPE_NOT_SUPPORTED, null);
        }
        LogMgr.log(4, "999");
    }
}
