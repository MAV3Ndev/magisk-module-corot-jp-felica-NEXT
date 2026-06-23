package com.felicanetworks.semc.util;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import com.felicanetworks.semc.SemClientException;
import com.felicanetworks.semc.config.AccessConfig;

/* JADX INFO: loaded from: classes3.dex */
public class ConnectionAppInfoContentProvider extends ContentProvider {
    private static final String COLUMN_CONNECTION_APP_INFO = "connectionAppInfo";
    private static final int ERROR = -1;
    private static final int MENU_APP = 1;
    private static final String PATH_CONNECTION_APP_INFO = "ConnectionAppInfo";
    private static final int SEMC_APP = 2;

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        LogMgr.log(8, "000");
        LogMgr.log(8, "999");
        return true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        LogMgr.log(8, "000");
        try {
            Context applicationContext = getContext().getApplicationContext();
            if (applicationContext == null) {
                LogMgr.log(2, "700 Failed to get application context.");
                return createCursor(-1);
            }
            if (!isCommonCfgExist()) {
                LogMgr.log(8, "001 common.cfg file is not exist. ret SEMC_APP.");
                return createCursor(2);
            }
            try {
                String deviceIdentificationData = AccessConfig.getDeviceIdentificationData(applicationContext);
                LogMgr.log(8, "002 deviceIdentificationValue=" + deviceIdentificationData);
                if (deviceIdentificationData != null && !"1".equals(deviceIdentificationData)) {
                    LogMgr.log(8, "005 deviceIdentificationValue is not domestic. return SEMC_APP.");
                    return createCursor(2);
                }
                LogMgr.log(8, "004 deviceIdentificationValue is null or domestic. return MENU_APP.");
                return createCursor(1);
            } catch (SemClientException e) {
                LogMgr.log(2, "701 Failed to get deviceIdentificationData.");
                LogMgr.log(2, "702 Exception Msg=" + e.getMessage());
                if (isSemcApp(applicationContext)) {
                    LogMgr.log(8, "003 My app is SEMC app. Failed to access Content provider. return MENU_APP.");
                    return createCursor(1);
                }
                LogMgr.log(2, "702 Failed to get deviceIdentificationData in MenuApp.");
                return createCursor(-1);
            }
        } catch (Exception e2) {
            LogMgr.log(2, "703 Exception occurred.");
            LogMgr.log(2, "704 Exception Msg=" + e2.getMessage());
            return createCursor(-1);
        } finally {
            LogMgr.log(8, "999");
        }
    }

    private boolean isSemcApp(Context context) throws SemClientException {
        LogMgr.log(8, "000");
        if (context == null) {
            LogMgr.log(2, "700 Failed to get application context.");
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        }
        String packageName = context.getPackageName();
        if (packageName == null) {
            LogMgr.log(2, "701 Failed to get package name.");
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        }
        if (!"com.felicanetworks.mfm.main".equals(packageName) && !"com.felicanetworks.semcapp".equals(packageName)) {
            LogMgr.log(2, "702 Invalid package name. Do not match to Menu and SEMC app.");
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        }
        boolean zEquals = "com.felicanetworks.semcapp".equals(packageName);
        LogMgr.log(8, "999 isSemcApp=" + zEquals);
        return zEquals;
    }

    private Cursor createCursor(int i) {
        LogMgr.log(8, "000 value=" + i);
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{COLUMN_CONNECTION_APP_INFO});
        matrixCursor.addRow(new Object[]{Integer.valueOf(i)});
        LogMgr.log(8, "999");
        return matrixCursor;
    }

    private boolean isCommonCfgExist() {
        LogMgr.log(8, "000");
        boolean z = CommonConfig.findConfigFile(CommonConfig.FILE_NAME) != null;
        LogMgr.log(8, "999");
        return z;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        LogMgr.log(8, "000");
        LogMgr.log(8, "999");
        return null;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        LogMgr.log(8, "000");
        LogMgr.log(8, "999");
        return null;
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        LogMgr.log(8, "000");
        LogMgr.log(8, "999");
        return 0;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        LogMgr.log(8, "000");
        LogMgr.log(8, "999");
        return 0;
    }
}
