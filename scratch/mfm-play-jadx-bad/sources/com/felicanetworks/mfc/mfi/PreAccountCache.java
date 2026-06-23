package com.felicanetworks.mfc.mfi;

import android.content.SharedPreferences;
import com.felicanetworks.mfc.mfi.util.Base64Util;
import com.felicanetworks.mfc.mfi.util.CacheUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.io.File;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class PreAccountCache {
    private static final String JSON_ACCOUNT_CODE = "account_code";
    private static final String JSON_ACCOUNT_FORCE = "account_force";
    private static final String JSON_ACCOUNT_ISSUER = "account_issuer";
    private static final String JSON_ACCOUNT_NAME = "account_name";
    private static final String JSON_APP_CALLER_INFO = "app_caller_info";
    private static final String JSON_APP_IDENTIFIABLE_INFO = "app_identifiable_info";
    private static final String JSON_LAYOUT_TYPE = "layout_type";
    private static final String JSON_SHOULD_SKIP_AGREEMENT_PAGE = "should_skip_agreement_page";
    private static final String JSON_UUID = "uuid";
    private static final String PREF_FILE_NAME = "mfi_pre_login_data";
    private static final String PREF_KEY_ACCOUNT_DATA = "account_data";
    private static PreAccountCache sInstance;
    private int mAccountCode;
    private boolean mAccountForce;
    private String mAccountIssuer;
    private String mAccountName;
    private String mAppCallerInfo;
    private String mAppIdentifiableInfo;
    private int mLayoutType;
    private boolean mNoNeedLoad;
    private boolean mShouldSkipAgreementPage;
    private String mUUID;

    public static synchronized PreAccountCache getInstance() {
        if (sInstance == null) {
            sInstance = new PreAccountCache();
        }
        return sInstance;
    }

    public synchronized void clearLoginData() throws MfiClientException {
        LogMgr.log(5, "000");
        this.mAccountIssuer = null;
        this.mAccountName = null;
        this.mAccountCode = 0;
        this.mAccountForce = false;
        this.mUUID = null;
        this.mLayoutType = 0;
        this.mAppCallerInfo = null;
        this.mAppIdentifiableInfo = null;
        this.mShouldSkipAgreementPage = false;
        clearCacheFile();
        LogMgr.log(5, "999");
    }

    public synchronized void cacheAccountData(String accountIssuer, String accountName, int accountCode, boolean accountForce, String uuid, int layoutType, String appCallerInfo, String appIdentifiableInfo, boolean shouldSkipAgreementPage) {
        LogMgr.log(5, "000");
        this.mAccountIssuer = accountIssuer;
        this.mAccountName = accountName;
        this.mAccountCode = accountCode;
        this.mAccountForce = accountForce;
        this.mUUID = uuid;
        this.mLayoutType = layoutType;
        this.mAppCallerInfo = appCallerInfo;
        this.mAppIdentifiableInfo = appIdentifiableInfo;
        this.mShouldSkipAgreementPage = shouldSkipAgreementPage;
        writeCacheFile();
        LogMgr.log(5, "999");
    }

    public synchronized void updateAccountInfoCache(String accountIssuer, String accountName) {
        loadCacheFile();
        this.mAccountIssuer = accountIssuer;
        this.mAccountName = accountName;
        writeCacheFile();
    }

    public synchronized void updateUuidCache(String uuid) {
        loadCacheFile();
        this.mUUID = uuid;
        writeCacheFile();
    }

    public synchronized int getAccountCodeCache(String accountIssuer, String accountName) {
        int accountCodeCache;
        LogMgr.log(5, "000");
        loadCacheFile();
        accountCodeCache = (accountIssuer.equals(this.mAccountIssuer) && accountName.equals(this.mAccountName)) ? getAccountCodeCache() : 0;
        LogMgr.log(5, "999");
        return accountCodeCache;
    }

    public synchronized int getAccountCodeCache() {
        LogMgr.log(5, "000");
        loadCacheFile();
        LogMgr.log(5, "999");
        return this.mAccountCode;
    }

    public synchronized String getUUID() {
        LogMgr.log(5, "000");
        loadCacheFile();
        LogMgr.log(5, "999");
        return this.mUUID;
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

    public synchronized boolean getAccountForce() {
        LogMgr.log(5, "000");
        loadCacheFile();
        LogMgr.log(5, "999");
        return this.mAccountForce;
    }

    public synchronized int getLayoutType() {
        LogMgr.log(5, "000");
        loadCacheFile();
        LogMgr.log(5, "999");
        return this.mLayoutType;
    }

    public synchronized String getAppCallerInfo() {
        LogMgr.log(5, "000");
        loadCacheFile();
        LogMgr.log(5, "999");
        return this.mAppCallerInfo;
    }

    public synchronized String getAppIdentifiableInfo() {
        LogMgr.log(5, "000");
        loadCacheFile();
        LogMgr.log(5, "999");
        return this.mAppIdentifiableInfo;
    }

    public synchronized boolean shouldSkipAgreementPage() {
        LogMgr.log(5, "000");
        loadCacheFile();
        LogMgr.log(5, "999 shouldSkipAgreementPage=" + this.mShouldSkipAgreementPage);
        return this.mShouldSkipAgreementPage;
    }

    private void clearCacheFile() throws MfiClientException {
        SharedPreferences.Editor editorEdit;
        LogMgr.log(6, "000");
        try {
            editorEdit = getPrefs().edit();
            editorEdit.remove(PREF_KEY_ACCOUNT_DATA);
        } catch (MfiClientException e) {
            throw e;
        } catch (Exception e2) {
            LogMgr.log(2, "700 " + e2.getClass().getSimpleName() + ":" + e2.getMessage());
            LogMgr.printStackTrace(7, e2);
        }
        if (!editorEdit.commit()) {
            LogMgr.log(1, "800 Fail to SharedPreferences$Editor#commit.");
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
        this.mNoNeedLoad = false;
        LogMgr.log(6, "999");
    }

    private void writeCacheFile() {
        LogMgr.log(6, "000");
        try {
            LogMgr.log(6, "100 issuer=" + this.mAccountIssuer);
            LogMgr.log(6, "101 name=" + this.mAccountName);
            LogMgr.log(6, "102 code=" + this.mAccountCode);
            LogMgr.log(6, "103 force=" + this.mAccountForce);
            LogMgr.log(6, "104 uuid=" + this.mUUID);
            LogMgr.log(6, "105 layoutType=" + this.mLayoutType);
            LogMgr.log(6, "106 appCallerInfo=" + this.mAppCallerInfo);
            LogMgr.log(6, "107 appIdentifiableInfo=" + this.mAppIdentifiableInfo);
            LogMgr.log(6, "108 shouldSkipAgreementPage=" + this.mShouldSkipAgreementPage);
            JSONObject jSONObject = new JSONObject();
            String str = this.mAccountIssuer;
            if (str != null) {
                jSONObject.put("account_issuer", str);
            }
            String str2 = this.mAccountName;
            if (str2 != null) {
                jSONObject.put("account_name", str2);
            }
            jSONObject.put(JSON_ACCOUNT_CODE, this.mAccountCode);
            jSONObject.put(JSON_ACCOUNT_FORCE, this.mAccountForce);
            jSONObject.put(JSON_UUID, this.mUUID);
            jSONObject.put(JSON_LAYOUT_TYPE, this.mLayoutType);
            jSONObject.put(JSON_APP_CALLER_INFO, this.mAppCallerInfo);
            jSONObject.put(JSON_APP_IDENTIFIABLE_INFO, this.mAppIdentifiableInfo);
            jSONObject.put("should_skip_agreement_page", this.mShouldSkipAgreementPage);
            String strEncode = Base64Util.encode(jSONObject.toString());
            SharedPreferences.Editor editorEdit = getPrefs().edit();
            editorEdit.putString(PREF_KEY_ACCOUNT_DATA, strEncode);
            LogMgr.log(6, "109 success commit ? " + editorEdit.commit());
            this.mNoNeedLoad = true;
        } catch (Exception e) {
            LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
        LogMgr.log(6, "999");
    }

    private void loadCacheFile() {
        String str;
        String string;
        LogMgr.log(6, "000 mNoNeedLoad ? " + this.mNoNeedLoad);
        if (this.mNoNeedLoad) {
            LogMgr.log(6, "999");
            return;
        }
        try {
            string = getPrefs().getString(PREF_KEY_ACCOUNT_DATA, null);
        } catch (Exception e) {
            e = e;
            str = "999";
        }
        try {
            if (string != null) {
                JSONObject jSONObject = new JSONObject(Base64Util.decodeToUTF8String(string));
                String string2 = jSONObject.getString("account_issuer");
                String string3 = jSONObject.getString("account_name");
                int i = jSONObject.getInt(JSON_ACCOUNT_CODE);
                boolean z = jSONObject.getBoolean(JSON_ACCOUNT_FORCE);
                str = "999";
                String strOptString = jSONObject.optString(JSON_UUID, null);
                int i2 = jSONObject.getInt(JSON_LAYOUT_TYPE);
                String strOptString2 = jSONObject.optString(JSON_APP_CALLER_INFO);
                String strOptString3 = jSONObject.optString(JSON_APP_IDENTIFIABLE_INFO);
                boolean z2 = jSONObject.getBoolean("should_skip_agreement_page");
                this.mAccountIssuer = string2;
                this.mAccountName = string3;
                this.mAccountCode = i;
                this.mAccountForce = z;
                this.mUUID = strOptString;
                this.mLayoutType = i2;
                this.mAppCallerInfo = strOptString2;
                this.mAppIdentifiableInfo = strOptString3;
                this.mShouldSkipAgreementPage = z2;
                LogMgr.log(6, "100 issuer=" + this.mAccountIssuer);
                LogMgr.log(6, "101 name=" + this.mAccountName);
                LogMgr.log(6, "102 code=" + this.mAccountCode);
                LogMgr.log(6, "103 force=" + this.mAccountForce);
                LogMgr.log(6, "104 uuid=" + this.mUUID);
                LogMgr.log(6, "105 layoutType=" + this.mLayoutType);
                LogMgr.log(6, "106 appCallerInfo=" + this.mAppCallerInfo);
                LogMgr.log(6, "107 appIdentifiableInfo=" + this.mAppIdentifiableInfo);
                LogMgr.log(6, "108 shouldSkipAgreementPage=" + z2);
            } else {
                str = "999";
                LogMgr.log(6, "109 Account data is null.");
                this.mAccountIssuer = null;
                this.mAccountName = null;
                this.mAccountCode = 0;
                this.mAccountForce = false;
                this.mUUID = null;
                this.mLayoutType = 0;
                this.mAppCallerInfo = null;
                this.mAppIdentifiableInfo = null;
                this.mShouldSkipAgreementPage = false;
            }
            this.mNoNeedLoad = true;
            CacheUtil.deleteFiles(new File(FelicaAdapter.getInstance().getApplicationInfo().dataDir, "shared_prefs"), PREF_FILE_NAME, getPrefsName() + ".xml");
        } catch (Exception e2) {
            e = e2;
            LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
        LogMgr.log(6, str);
    }

    private String getPrefsName() {
        return "mfi_pre_login_data_" + FelicaAdapter.getInstance().getString(R.string.mfi_client_version);
    }

    private SharedPreferences getPrefs() {
        return FelicaAdapter.getInstance().getSharedPreferences(getPrefsName(), 0);
    }
}
