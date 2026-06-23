package com.felicanetworks.mfm.main.policy.err;

import android.content.Context;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.policy.err.legacy.DatabaseAccess;

/* JADX INFO: loaded from: classes.dex */
public class ErrorManager {
    private static DatabaseAccess _db;

    public static void setup(Context context) {
        _db = new DatabaseAccess(context);
    }

    public static boolean isExistFatalError() {
        try {
            return !_db.getErrorLogData().isEmpty();
        } catch (Exception unused) {
            return false;
        }
    }

    public static void reportFatalErrorLogs(NetworkExpert networkExpert) {
        DatabaseAccess databaseAccess;
        try {
            if (isExistFatalError()) {
                networkExpert.connect(new ReportFatalErrorProtocol(networkExpert).create(_db.getErrorLogData(), Settings.idm()));
            }
            databaseAccess = _db;
            if (databaseAccess == null) {
                return;
            }
        } catch (Exception unused) {
            databaseAccess = _db;
            if (databaseAccess == null) {
                return;
            }
        } catch (Throwable th) {
            DatabaseAccess databaseAccess2 = _db;
            if (databaseAccess2 != null) {
                databaseAccess2.cleanErrorInfo();
            }
            throw th;
        }
        databaseAccess.cleanErrorInfo();
    }

    public static void storeFatalErrorLog(String str) {
        try {
            _db.addErrorLog(str);
        } catch (Exception unused) {
        }
    }
}
