package com.felicanetworks.mfc.mfi;

import android.content.SharedPreferences;
import com.felicanetworks.mfc.mfi.fws.json.LoginTokenJws;
import com.felicanetworks.mfc.mfi.util.Base64Util;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.security.MessageDigest;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AccountCache {
    private static final String DIGEST_ALGORITHM = "SHA256";
    private static final String DIGEST_CHARSET = "UTF-8";
    private static final long EXPIRATION_TIME_SPARE_SEC = 60;
    private static final String JSON_ACCOUNT_ID = "account_id";
    private static final String JSON_ACCOUNT_ISSUER = "account_issuer";
    private static final String JSON_ACCOUNT_NAME = "account_name";
    private static final String PREF_FILE_NAME = "mfi_login_data";
    private static final String PREF_KEY_ACCOUNT_DATA = "account_data";
    private static AccountCache sInstance;
    private String mAccountId;
    private String mAccountIssuer;
    private String mAccountName;
    private LoginTokenJws mLoginToken;
    private boolean mNoNeedLoad;

    private AccountCache() {
    }

    public static synchronized AccountCache getInstance() {
        if (sInstance == null) {
            sInstance = new AccountCache();
        }
        return sInstance;
    }

    public synchronized void clearLoginData() throws MfiClientException {
        LogMgr.log(5, "000");
        this.mLoginToken = null;
        this.mAccountIssuer = null;
        this.mAccountName = null;
        this.mAccountId = null;
        clearCacheFile();
        LogMgr.log(5, "999");
    }

    public synchronized void cacheLoginData(LoginTokenJws loginTokenJws, String str, String str2) {
        LogMgr.log(5, "000");
        this.mLoginToken = loginTokenJws;
        this.mAccountIssuer = str;
        this.mAccountName = str2;
        if (loginTokenJws != null) {
            this.mAccountId = loginTokenJws.optAccountId();
        }
        writeCacheFile();
        LogMgr.log(5, "999");
    }

    public synchronized void clearLoginTokenCache() {
        LogMgr.log(5, "000");
        this.mLoginToken = null;
        LogMgr.log(5, "999");
    }

    public synchronized LoginTokenJws getLoginTokenCache(String str, String str2) {
        LogMgr.log(5, "000");
        LoginTokenJws loginTokenCache = null;
        if (str != null && str2 != null) {
            loadCacheFile();
            if (str.equals(this.mAccountIssuer) && str2.equals(this.mAccountName)) {
                loginTokenCache = getLoginTokenCache();
            }
            LogMgr.log(5, "999");
            return loginTokenCache;
        }
        LogMgr.log(2, "700 One of args is null");
        return null;
    }

    public synchronized LoginTokenJws getLoginTokenCache() {
        LoginTokenJws loginTokenJws;
        LogMgr.log(5, "000");
        loginTokenJws = null;
        if (this.mLoginToken != null) {
            if (this.mLoginToken.checkExpirationTime(EXPIRATION_TIME_SPARE_SEC)) {
                loginTokenJws = this.mLoginToken;
            } else {
                clearLoginTokenCache();
            }
        }
        LogMgr.log(5, "999");
        return loginTokenJws;
    }

    public synchronized String getAccountIssuerCache() {
        LogMgr.log(5, "000");
        loadCacheFile();
        LogMgr.log(5, "999");
        return this.mAccountIssuer;
    }

    public synchronized String getAccountNameCache() {
        LogMgr.log(5, "000");
        loadCacheFile();
        LogMgr.log(5, "999");
        return this.mAccountName;
    }

    public synchronized String getAccountId() {
        LogMgr.log(5, "000");
        loadCacheFile();
        LogMgr.log(5, "999");
        return this.mAccountId;
    }

    public synchronized String getAccountHashCache(String str) {
        String strBytesToHexString;
        LogMgr.log(5, "000");
        loadCacheFile();
        LogMgr.log(6, "%s app=%s, id=%s", "001", str, this.mAccountId);
        strBytesToHexString = null;
        try {
        } catch (Exception e) {
            LogMgr.log(2, "%s %s:%s", "700", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
        if (this.mAccountId != null) {
            MessageDigest messageDigest = MessageDigest.getInstance(DIGEST_ALGORITHM);
            messageDigest.update(str.getBytes("UTF-8"));
            strBytesToHexString = StringUtil.bytesToHexString(messageDigest.digest(this.mAccountId.getBytes("UTF-8")));
            LogMgr.log(6, "%s hash=%s", "002", strBytesToHexString);
            LogMgr.log(5, "999");
        } else {
            LogMgr.log(5, "999");
        }
        return strBytesToHexString;
    }

    private void clearCacheFile() throws MfiClientException {
        SharedPreferences.Editor editorEdit;
        LogMgr.log(6, "%s", "000");
        try {
            editorEdit = getPrefs().edit();
            editorEdit.remove(PREF_KEY_ACCOUNT_DATA);
        } catch (MfiClientException e) {
            throw e;
        } catch (Exception e2) {
            LogMgr.log(2, "%s %s:%s", "700", e2.getClass().getSimpleName(), e2.getMessage());
            LogMgr.printStackTrace(7, e2);
        }
        if (!editorEdit.commit()) {
            LogMgr.log(1, "800 Fail to SharedPreferences$Editor#commit.");
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
        this.mNoNeedLoad = false;
        LogMgr.log(6, "%s", "999");
    }

    private void writeCacheFile() {
        LogMgr.log(6, "%s", "000");
        try {
            LogMgr.log(6, "%s issuer=%s", "100", this.mAccountIssuer);
            LogMgr.log(6, "%s name=%s", "101", this.mAccountName);
            LogMgr.log(6, "%s id=%s", "102", this.mAccountId);
            JSONObject jSONObject = new JSONObject();
            if (this.mAccountIssuer != null) {
                jSONObject.put("account_issuer", this.mAccountIssuer);
            }
            if (this.mAccountName != null) {
                jSONObject.put("account_name", this.mAccountName);
            }
            if (this.mAccountId != null) {
                jSONObject.put(JSON_ACCOUNT_ID, this.mAccountId);
            }
            String strEncode = Base64Util.encode(jSONObject.toString());
            SharedPreferences.Editor editorEdit = getPrefs().edit();
            editorEdit.putString(PREF_KEY_ACCOUNT_DATA, strEncode);
            LogMgr.log(6, "%s success commit ? %s", "103", Boolean.valueOf(editorEdit.commit()));
            this.mNoNeedLoad = true;
        } catch (Exception e) {
            LogMgr.log(2, "%s %s:%s", "700", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
        LogMgr.log(6, "%s", "999");
    }

    private void loadCacheFile() {
        LogMgr.log(6, "%s mNoNeedLoad ? %s", "000", Boolean.valueOf(this.mNoNeedLoad));
        if (this.mNoNeedLoad) {
            LogMgr.log(6, "%s", "999");
            return;
        }
        try {
            String string = getPrefs().getString(PREF_KEY_ACCOUNT_DATA, null);
            if (string != null) {
                JSONObject jSONObject = new JSONObject(Base64Util.decodeToUTF8String(string));
                String string2 = jSONObject.getString("account_issuer");
                String string3 = jSONObject.getString("account_name");
                String string4 = jSONObject.getString(JSON_ACCOUNT_ID);
                LogMgr.log(6, "%s issuer=%s", "100", string2);
                LogMgr.log(6, "%s name=%s", "101", string3);
                LogMgr.log(6, "%s id=%s", "102", string4);
                this.mAccountIssuer = string2;
                this.mAccountName = string3;
                this.mAccountId = string4;
            } else {
                LogMgr.log(6, "%s Account data is null.", "103");
                this.mAccountIssuer = null;
                this.mAccountName = null;
                this.mAccountId = null;
            }
            this.mNoNeedLoad = true;
        } catch (Exception e) {
            LogMgr.log(2, "%s %s:%s", "700", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
        LogMgr.log(6, "%s", "999");
    }

    private SharedPreferences getPrefs() {
        return FelicaAdapter.getInstance().getSharedPreferences(PREF_FILE_NAME, 0);
    }
}
