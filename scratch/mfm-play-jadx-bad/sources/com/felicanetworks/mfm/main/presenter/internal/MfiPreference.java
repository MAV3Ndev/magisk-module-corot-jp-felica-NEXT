package com.felicanetworks.mfm.main.presenter.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Base64;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import com.felicanetworks.mfm.main.presenter.internal.MfiContentProvider;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
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
    */
    public String loadMfiAccountName() throws Throwable {
        Cursor cursorQuery;
        String string;
        Cursor cursor = null;
        try {
            cursorQuery = this._context.getContentResolver().query(MfiContentProvider.Accounts.CONTENT_URI, null, "name", null, null);
            if (cursorQuery != null) {
                try {
                    string = cursorQuery.moveToFirst() ? cursorQuery.getString(cursorQuery.getColumnIndex("name")) : null;
                } catch (Exception unused) {
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return null;
                } catch (Throwable th) {
                    th = th;
                    cursor = cursorQuery;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            String strDecode = decode(string);
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return strDecode;
        } catch (Exception unused2) {
            cursorQuery = null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public String directLoadMfiAccountName() {
        try {
            return decode(getMfiPref().getString(MFI_ACCOUNT, null));
        } catch (Exception unused) {
            return null;
        }
    }

    private String decode(String accountName) {
        if (accountName == null) {
            return null;
        }
        try {
            if (accountName.getBytes().length > 2048) {
                return null;
            }
            String str = new String(Base64.decode(accountName, 0), StringUtil.UTF_8);
            if (str.isEmpty()) {
                return null;
            }
            JSONObject jSONObject = new JSONObject(str);
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
