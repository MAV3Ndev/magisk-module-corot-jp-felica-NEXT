package com.felicanetworks.mfm.main.presenter.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class MfiPreference {
    private static final String JSON_ACCOUNT_NAME = "account_name";
    private static final int MAX_ACCOUNT_NAME_LENGTH = 2048;
    private static final String MFI_ACCOUNT = "account_data";
    private static final String PREFERENCE_NAME = "mfi_login_data";
    private static MfiPreference _self;
    private Context _context;

    private MfiPreference(Context context) {
        this._context = context;
    }

    private void setContext(Context context) {
        this._context = context;
    }

    public static MfiPreference getInstance(Context context) {
        MfiPreference mfiPreference = _self;
        if (mfiPreference == null) {
            _self = new MfiPreference(context);
        } else {
            mfiPreference.setContext(context);
        }
        return _self;
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String loadMfiAccountName() throws java.lang.Throwable {
        /*
            r9 = this;
            r0 = 0
            android.content.Context r1 = r9._context     // Catch: java.lang.Throwable -> L34 java.lang.Exception -> L3e
            android.content.ContentResolver r2 = r1.getContentResolver()     // Catch: java.lang.Throwable -> L34 java.lang.Exception -> L3e
            android.net.Uri r3 = com.felicanetworks.mfm.main.presenter.internal.MfiContentProvider.Accounts.CONTENT_URI     // Catch: java.lang.Throwable -> L34 java.lang.Exception -> L3e
            r4 = 0
            java.lang.String r5 = "name"
            r6 = 0
            r7 = 0
            android.database.Cursor r1 = r2.query(r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L34 java.lang.Exception -> L3e
            if (r1 == 0) goto L25
            boolean r2 = r1.moveToFirst()     // Catch: java.lang.Throwable -> L30 java.lang.Exception -> L32
            if (r2 == 0) goto L25
            java.lang.String r2 = "name"
            int r2 = r1.getColumnIndex(r2)     // Catch: java.lang.Throwable -> L30 java.lang.Exception -> L32
            java.lang.String r2 = r1.getString(r2)     // Catch: java.lang.Throwable -> L30 java.lang.Exception -> L32
            goto L26
        L25:
            r2 = r0
        L26:
            java.lang.String r0 = r9.decode(r2)     // Catch: java.lang.Throwable -> L30 java.lang.Exception -> L32
            if (r1 == 0) goto L42
        L2c:
            r1.close()
            goto L42
        L30:
            r0 = move-exception
            goto L38
        L32:
            goto L3f
        L34:
            r1 = move-exception
            r8 = r1
            r1 = r0
            r0 = r8
        L38:
            if (r1 == 0) goto L3d
            r1.close()
        L3d:
            throw r0
        L3e:
            r1 = r0
        L3f:
            if (r1 == 0) goto L42
            goto L2c
        L42:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.presenter.internal.MfiPreference.loadMfiAccountName():java.lang.String");
    }

    public String directLoadMfiAccountName() {
        try {
            return decode(getMfiPref().getString(MFI_ACCOUNT, null));
        } catch (Exception unused) {
            return null;
        }
    }

    private String decode(String str) {
        if (str == null) {
            return null;
        }
        try {
            if (str.getBytes().length > 2048) {
                return null;
            }
            String str2 = new String(Base64.decode(str, 0), StringUtil.UTF_8);
            if (str2.isEmpty()) {
                return null;
            }
            JSONObject jSONObject = new JSONObject(str2);
            if (jSONObject.length() > 0) {
                return jSONObject.getString("account_name");
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    private SharedPreferences getMfiPref() {
        return this._context.getSharedPreferences(PREFERENCE_NAME, 0);
    }
}
