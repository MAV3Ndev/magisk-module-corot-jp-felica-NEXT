package com.felicanetworks.mfc.mfi;

import android.content.Context;
import android.provider.Settings;
import com.felicanetworks.mfc.mfi.util.CacheUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
class OtherDeviceDataCleaner {
    private static final String DEVICE_JUDGE_DATA_FILE_NAME = "device_judge_value.xml";
    private static final String REGEX_EXT_JSON = "\\.json$";
    private static final String REGEX_EXT_XML = "\\.xml$";
    private static final String REGEX_VERSION = "_\\d{1,2}\\.\\d{1,2}\\.\\d{1,2}";
    private static final String REGEX_VERSION_UP_DATA = "^version_up_data_\\d{1,2}\\.\\d{1,2}\\.\\d{1,2}\\.json$";
    private static final String REGEX_PERMIT_DATA = "^([a-zA-Z][\\w]*\\.)+([a-zA-Z][\\w]*){1}_[\\w-]{43}_\\d{1,2}\\.\\d{1,2}\\.\\d{1,2}$";
    private static final String REGEX_MFI_LOGIN_DATA = "^mfi_login_data\\.xml$";
    private static final String REGEX_MFI_PRE_LOGIN_DATA = "^mfi_pre_login_data.*\\.xml$";
    private static final String REGEX_TOS_DATA = "^openidconnect_google.*\\.xml$";
    private static final String REGEX_CARD_DATAv1 = "^mfi_card_data_\\d{1,2}\\.\\d{1,2}\\.\\d{1,2}$";
    private static final String REGEX_CARD_DATAv2 = "^mfic_db.*$";
    private static final String REGEX_MFI_CONTROL_INFO = "^mfi_control_info_\\d{1,2}\\.\\d{1,2}\\.\\d{1,2}\\.xml$";
    private static final String REGEX_LOG_SENDER = "^log_sender\\.xml$";
    private static final String REGEX_CLOUD_MESSAGING_DATA = "^cloud_messaging_data\\.xml$";
    private static final String REGEX_CLIENT_ID_DATA = "^client_id_data_\\d{1,2}\\.\\d{1,2}\\.\\d{1,2}\\.xml$";
    private static final String REGEX_MIGRATED_SERVICE1_DATA = "^migrated_service1_data\\.xml$";
    private static final String REGEX_MFICLIENT_PROFILE_INFO = "^mfic_profile_info_\\d{1,2}\\.\\d{1,2}\\.\\d{1,2}\\.xml$";
    private static final List<DeleteTargetFileProfile> deleteTargetFileProfiles = Arrays.asList(new DeleteTargetFileProfile(DirType.FILES, REGEX_VERSION_UP_DATA), new DeleteTargetFileProfile(DirType.FILES, REGEX_PERMIT_DATA), new DeleteTargetFileProfile(DirType.PREF, REGEX_MFI_LOGIN_DATA), new DeleteTargetFileProfile(DirType.PREF, REGEX_MFI_PRE_LOGIN_DATA), new DeleteTargetFileProfile(DirType.PREF, REGEX_TOS_DATA), new DeleteTargetFileProfile(DirType.FILES, REGEX_CARD_DATAv1), new DeleteTargetFileProfile(DirType.DB, REGEX_CARD_DATAv2), new DeleteTargetFileProfile(DirType.PREF, REGEX_MFI_CONTROL_INFO), new DeleteTargetFileProfile(DirType.PREF, REGEX_LOG_SENDER), new DeleteTargetFileProfile(DirType.PREF, REGEX_CLOUD_MESSAGING_DATA), new DeleteTargetFileProfile(DirType.PREF, REGEX_CLIENT_ID_DATA), new DeleteTargetFileProfile(DirType.PREF, REGEX_MIGRATED_SERVICE1_DATA), new DeleteTargetFileProfile(DirType.PREF, REGEX_MFICLIENT_PROFILE_INFO));

    private enum DirType {
        FILES,
        PREF,
        DB;

        /* JADX INFO: Access modifiers changed from: private */
        public File getDirName(final Context context) {
            int iOrdinal = ordinal();
            if (iOrdinal == 1) {
                return new File(context.getApplicationInfo().dataDir, "shared_prefs");
            }
            if (iOrdinal == 2) {
                return new File(context.getApplicationInfo().dataDir, "databases");
            }
            return context.getFilesDir();
        }
    }

    private static class DeleteTargetFileProfile {
        private final DirType dirType;
        private final String fileRegex;

        DeleteTargetFileProfile(final DirType dirType, final String fileRegex) {
            this.dirType = dirType;
            this.fileRegex = fileRegex;
        }
    }

    private OtherDeviceDataCleaner() {
    }

    static void doClean(final Context context) throws Throwable {
        File file = new File(context.getFilesDir(), DEVICE_JUDGE_DATA_FILE_NAME);
        if (hasOtherDeviceData(context, file)) {
            LogMgr.log(6, "Delete %s.", file.getName());
            deleteAllData(context);
            createDeviceJudgeDataFile(context, file);
        }
    }

    private static boolean hasOtherDeviceData(final Context context, final File file) throws Throwable {
        if (!file.exists()) {
            LogMgr.log(6, "Device judge file isn't exist.");
            createDeviceJudgeDataFile(context, file);
            return false;
        }
        if (CacheUtil.readFile(file) == null) {
            LogMgr.log(6, "Device judge file is broken.");
            createDeviceJudgeDataFile(context, file);
            return false;
        }
        return !getDeviceJudgeValue(context).equals(r0);
    }

    private static void deleteAllData(final Context context) {
        File[] fileArrListFiles;
        for (DirType dirType : DirType.values()) {
            File dirName = dirType.getDirName(context);
            if (dirName != null && (fileArrListFiles = dirName.listFiles()) != null) {
                for (DeleteTargetFileProfile deleteTargetFileProfile : deleteTargetFileProfiles) {
                    if (dirType.equals(deleteTargetFileProfile.dirType)) {
                        for (File file : fileArrListFiles) {
                            if (file.getName().matches(deleteTargetFileProfile.fileRegex)) {
                                LogMgr.log(2, "Delete %s.", file.getName());
                                CacheUtil.deleteFile(file);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void createDeviceJudgeDataFile(final Context context, final File file) throws Throwable {
        CacheUtil.writeFile(file, getDeviceJudgeValue(context));
    }

    private static String getDeviceJudgeValue(final Context context) {
        String string = Settings.Secure.getString(context.getContentResolver(), "android_id");
        if (string == null) {
            LogMgr.log(1, "ANDROID_ID is null, so treat empty.");
            return "";
        }
        LogMgr.log(6, "ANDROID_ID : %s.", string);
        return string;
    }
}
