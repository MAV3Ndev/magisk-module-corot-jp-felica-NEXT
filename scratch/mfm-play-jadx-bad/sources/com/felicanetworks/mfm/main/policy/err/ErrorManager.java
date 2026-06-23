package com.felicanetworks.mfm.main.policy.err;

import android.content.Context;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.policy.err.legacy.DatabaseAccess;
import java.util.Iterator;

/* JADX INFO: loaded from: classes3.dex */
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

    /* JADX DEBUG: Another duplicated slice has different insns count: {[SGET]}, finally: {[SGET, INVOKE, IF] complete} */
    public static void reportFatalErrorLogs(NetworkExpert ne) {
        try {
            if (isExistFatalError()) {
                Iterator<NetworkExpert.Request> it = new ReportFatalErrorProtocol(ne).create(_db.getErrorLogData(), Settings.idm()).iterator();
                while (it.hasNext()) {
                    ne.connect(it.next());
                }
            }
            DatabaseAccess databaseAccess = _db;
            if (databaseAccess != null) {
                databaseAccess.cleanErrorInfo();
            }
        } catch (Exception unused) {
            DatabaseAccess databaseAccess2 = _db;
            if (databaseAccess2 != null) {
                databaseAccess2.cleanErrorInfo();
            }
        } catch (Throwable th) {
            DatabaseAccess databaseAccess3 = _db;
            if (databaseAccess3 != null) {
                databaseAccess3.cleanErrorInfo();
            }
            throw th;
        }
    }

    public static void storeFatalErrorLog(String log) {
        try {
            _db.addErrorLog(log);
        } catch (Exception unused) {
        }
    }

    public static void storeInquiryLog(String log) {
        try {
            if (log.isEmpty()) {
                return;
            }
            _db.addErrorLog(log);
        } catch (Exception unused) {
        }
    }
}
