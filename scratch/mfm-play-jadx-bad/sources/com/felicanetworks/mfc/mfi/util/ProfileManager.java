package com.felicanetworks.mfc.mfi.util;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import androidx.credentials.exceptions.publickeycredential.DomExceptionUtils;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.fws.json.JwsException;
import com.felicanetworks.mfc.mfi.fws.json.JwsObject;
import com.felicanetworks.mfc.util.LogMgr;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes3.dex */
public class ProfileManager {
    private static final String PROFILE_NAME_PATTERN = "^MCPF_.*\\.jws$";
    private static final String STR_MFIC_PROFILE_IS_ENABLED = "MFIC Profile is enabled";
    private static ProfileManager sInstance;
    private final FilenameFilter filter;
    private final Handler mHandlerForToast;
    private ProfileCacheManager mProfileCacheManager;
    private String mProfilePath;

    private enum Mode {
        EXIST_PROFILE_CACHE,
        EXIST_PROFILE,
        NOT_EXIST_PROFILE
    }

    public static ProfileManager getInstance() {
        if (sInstance == null) {
            sInstance = new ProfileManager();
        }
        return sInstance;
    }

    private ProfileManager() {
        this.mHandlerForToast = new Handler(Looper.myLooper() == null ? Looper.getMainLooper() : Looper.myLooper());
        this.filter = new FilenameFilter() { // from class: com.felicanetworks.mfc.mfi.util.ProfileManager.1
            @Override // java.io.FilenameFilter
            public boolean accept(File file, String str) {
                LogMgr.log(7, "000");
                if (Pattern.compile(ProfileManager.PROFILE_NAME_PATTERN).matcher(str).find()) {
                    LogMgr.log(7, "998 ret = true");
                    return true;
                }
                LogMgr.log(7, "999 ret = false");
                return false;
            }
        };
        LogMgr.log(6, "000");
        if (this.mProfileCacheManager == null) {
            this.mProfileCacheManager = new ProfileCacheManager();
        }
        LogMgr.log(6, "999");
    }

    public void loadProfileDataOnMemory(Context context, String callerPackageName) throws FelicaException {
        LogMgr.log(4, "000");
        saveToMemory(checkMode(context), context, callerPackageName);
        LogMgr.log(4, "999");
    }

    private Mode checkMode(Context context) throws FelicaException {
        LogMgr.log(6, "000");
        LogMgr.log(7, "006");
        Mode mode = Mode.NOT_EXIST_PROFILE;
        LogMgr.log(6, "999");
        return mode;
    }

    private boolean existsProfile(Context context) throws FelicaException {
        LogMgr.log(6, "000");
        String externalStoragePath = getExternalStoragePath(context);
        File[] fileArrListFiles = externalStoragePath != null ? new File(externalStoragePath).listFiles(this.filter) : null;
        if (fileArrListFiles == null || fileArrListFiles.length == 0) {
            LogMgr.log(7, "001 profile is not exists.");
            return false;
        }
        if (fileArrListFiles.length > 1) {
            LogMgr.log(1, "800 There are multiple profiles.");
            throw new FelicaException(1, 47);
        }
        this.mProfilePath = fileArrListFiles[0].toString();
        LogMgr.log(6, "999");
        return true;
    }

    private String loadProfileJws(String profilePath) throws FelicaException {
        LogMgr.log(6, "000");
        try {
            FileInputStream fileInputStream = new FileInputStream(profilePath);
            try {
                String uTF8String = StringUtil.toUTF8String(toByteArray(fileInputStream));
                this.mProfileCacheManager.setProfileName(new File(profilePath).getName());
                fileInputStream.close();
                LogMgr.log(6, "999");
                return uTF8String;
            } finally {
            }
        } catch (IOException unused) {
            LogMgr.log(1, "800 IOException occurred in reading profile data.");
            throw new FelicaException(1, 47);
        }
    }

    private byte[] toByteArray(FileInputStream fis) throws IOException {
        LogMgr.log(6, "000");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[4096];
        while (true) {
            int i = fis.read(bArr);
            if (i != -1) {
                byteArrayOutputStream.write(bArr, 0, i);
            } else {
                LogMgr.log(6, "000");
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    private String verifyProfile(String profileData) throws FelicaException {
        LogMgr.log(6, "000");
        try {
            JwsObject jwsObject = new JwsObject(profileData);
            jwsObject.verify(FlavorConst.PUBLIC_KEYS_FOR_PROFILE);
            jwsObject.verifyExp();
            String payload = jwsObject.getPayload();
            LogMgr.log(6, "999");
            return payload;
        } catch (JwsException e) {
            LogMgr.log(1, "800 JwsException occurred in reading profile data. " + e.getMessage());
            throw new FelicaException(1, 47);
        }
    }

    private String getExternalStoragePath(Context context) {
        LogMgr.log(6, "000");
        String str = null;
        if ("mounted".equals(Environment.getExternalStorageState())) {
            try {
                File externalFilesDir = context.getExternalFilesDir(null);
                if (externalFilesDir != null) {
                    str = externalFilesDir.getPath() + DomExceptionUtils.SEPARATOR;
                }
            } catch (Exception unused) {
            }
        }
        LogMgr.log(6, "999 result=" + str);
        return str;
    }

    private void saveToMemory(Mode mode, Context context, String callerPackageName) throws FelicaException {
        LogMgr.log(6, "000 mode = " + mode);
        try {
            int iOrdinal = mode.ordinal();
            if (iOrdinal == 0) {
                LogMgr.log(7, "001");
                ProfileJson profileJsonLoadCacheFile = this.mProfileCacheManager.loadCacheFile(context, callerPackageName);
                toastProfileEnabled(context, this.mProfileCacheManager.getProfileName());
                SettingInfo.setProfileValue(profileJsonLoadCacheFile);
            } else if (iOrdinal == 1) {
                LogMgr.log(7, "002");
                String strVerifyProfile = verifyProfile(loadProfileJws(this.mProfilePath));
                ProfileJson profileJson = new ProfileJson(strVerifyProfile, context, callerPackageName);
                this.mProfileCacheManager.writeCacheFile(strVerifyProfile);
                toastProfileEnabled(context, this.mProfileCacheManager.getProfileName());
                SettingInfo.setProfileValue(profileJson);
            } else if (iOrdinal == 2) {
                LogMgr.log(7, "003");
                SettingInfo.initializeValue();
            }
            LogMgr.log(6, "999");
        } catch (Exception unused) {
            LogMgr.log(1, "800 JSONException occurred in reading profile data.");
            throw new FelicaException(1, 47);
        }
    }

    private void toastProfileEnabled(final Context context, String profileName) throws FelicaException {
        LogMgr.log(6, "000");
        LogMgr.log(7, "001 succeeded to read profile(" + profileName + ").MfiClient will be work with ProfileData");
        final String str = STR_MFIC_PROFILE_IS_ENABLED + System.getProperty("line.separator") + profileName;
        try {
            this.mHandlerForToast.post(new Runnable() { // from class: com.felicanetworks.mfc.mfi.util.ProfileManager$$ExternalSyntheticLambda0
                /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
                @Override // java.lang.Runnable
                public final void run() {
                    Toast.makeText(context, str, 1).show();
                }
            });
            LogMgr.log(7, "999");
        } catch (Exception unused) {
            LogMgr.log(1, "800 Exception occurred in Toast#makeText.");
            throw new FelicaException(1, 47);
        }
    }
}
